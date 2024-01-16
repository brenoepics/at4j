package com.github.brenoepics.at4j.core.ratelimit;

import com.github.brenoepics.at4j.core.AzureApiImpl;
import com.github.brenoepics.at4j.core.exceptions.AzureException;
import com.github.brenoepics.at4j.util.logging.LoggerUtil;
import com.github.brenoepics.at4j.util.rest.RestRequest;
import com.github.brenoepics.at4j.util.rest.RestRequestHandler;
import com.github.brenoepics.at4j.util.rest.RestRequestResponseInformationImpl;
import com.github.brenoepics.at4j.util.rest.RestRequestResult;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import okhttp3.Response;
import org.apache.logging.log4j.Logger;

/** This class manages rate-limits and keeps track of them. */
public class RateLimitManager<T> {

  /** The (logger) of this class. */
  private static final Logger logger = LoggerUtil.getLogger(RateLimitManager.class);

  /** The Azure API instance for this rate-limit manager. */
  private final AzureApiImpl<T> api;

  /** All buckets. */
  private final Set<RateLimitBucket<T>> buckets = new HashSet<>();

  /**
   * Creates a new rate-limit manager.
   *
   * @param api The azure api instance for this rate-limit manager.
   */
  public RateLimitManager(AzureApiImpl<T> api) {
    this.api = api;
  }

  /**
   * Queues the given request. This method is automatically called when using {@link
   * RestRequest#execute(Function)}!
   *
   * @param request The request to queue.
   */
  public void queueRequest(RestRequest<T> request) {
    Optional<RateLimitBucket<T>> searchBucket = searchBucket(request);

    if (!searchBucket.isPresent()) {
      return;
    }

    final RateLimitBucket<T> bucket = searchBucket.get();

    api.getThreadPool()
        .getExecutorService()
        .submit(
            () -> {
              RestRequest<T> currentRequest = bucket.peekRequestFromQueue();
              RestRequestResult<T> result = null;
              long responseTimestamp = System.currentTimeMillis();
              while (currentRequest != null) {
                RestRequestHandler<T> newResult =
                    handleCurrentRequest(result, currentRequest, bucket, responseTimestamp);
                result = newResult.getResult();
                currentRequest = newResult.getCurrentRequest();
                responseTimestamp = newResult.getResponseTimestamp();
              }
            });
  }

  /**
   * Handles the current request.
   *
   * @param result The result of the previous request.
   * @param currentRequest The current request.
   * @param bucket The bucket the request belongs to.
   * @param responseTimestamp The timestamp directly after the response finished.
   * @return The result of the current request.
   */
  RestRequestHandler<T> handleCurrentRequest(
      RestRequestResult<T> result,
      RestRequest<T> currentRequest,
      RateLimitBucket<T> bucket,
      long responseTimestamp) {
    try {
      waitUntilSpaceGetsAvailable(bucket);
      result = currentRequest.executeBlocking();
      responseTimestamp = System.currentTimeMillis();
    } catch (Exception e) {
      responseTimestamp = System.currentTimeMillis();
      if (currentRequest.getResult().isDone()) {
        logger.warn(
            "Received exception for a request that is already done. This should not be able to"
                + " happen!",
            e);
      }

      if (e instanceof AzureException) {
        result = mapAzureException(e);
      }

      currentRequest.getResult().completeExceptionally(e);
    } finally {
      try {
        // Handle the response
        handleResponse(currentRequest, result, bucket, responseTimestamp);
      } catch (Exception e) {
        logger.warn("Encountered unexpected exception.", e);
      }

      // The request didn't finish, so let's try again
      if (currentRequest.getResult().isDone()) {
        currentRequest = retryRequest(bucket);
      }
    }

    return new RestRequestHandler<>(result, currentRequest, responseTimestamp);
  }

  /**
   * Waits until space gets available in the given bucket.
   *
   * @param bucket The bucket to wait for.
   */
  void waitUntilSpaceGetsAvailable(RateLimitBucket<T> bucket) {
    int sleepTime = bucket.getTimeTillSpaceGetsAvailable();
    if (sleepTime > 0) {
      logger.debug(
          "Delaying requests to {} for {}ms to prevent hitting rate-limits", bucket, sleepTime);
    }

    while (sleepTime > 0) {
      try {
        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {
        logger.warn("We got interrupted while waiting for a rate limit!", e);
        Thread.currentThread().interrupt(); // Re-interrupt the thread
      }
      // Update in case something changed (e.g., because we hit a global rate-limit)
      sleepTime = bucket.getTimeTillSpaceGetsAvailable();
    }
  }

  /**
   * Retries the request of the given bucket.
   *
   * @param bucket The bucket to retry the request for.
   * @return The request that was retried.
   */
  RestRequest<T> retryRequest(RateLimitBucket<T> bucket) {
    synchronized (buckets) {
      bucket.pollRequestFromQueue();
      RestRequest<T> request = bucket.peekRequestFromQueue();
      if (request == null) {
        buckets.remove(bucket);
      }

      return request;
    }
  }

  private RestRequestResult<T> mapAzureException(Throwable t) {
    return ((AzureException) t)
        .getResponse()
        .map(RestRequestResponseInformationImpl.class::cast)
        .map(RestRequestResponseInformationImpl::getRestRequestResult)
        .orElse(null);
  }

  /**
   * Searches for a bucket that fits to the given request and adds it to the queue.
   *
   * @param request The request.
   * @return The bucket that fits to the request.
   */
  Optional<RateLimitBucket<T>> searchBucket(RestRequest<T> request) {
    synchronized (buckets) {
      RateLimitBucket<T> bucket =
          buckets.stream()
              .filter(
                  b -> b.equals(request.getEndpoint(), request.getMajorUrlParameter().orElse(null)))
              .findAny()
              .orElseGet(
                  () ->
                      new RateLimitBucket<>(
                          request.getEndpoint(), request.getMajorUrlParameter().orElse(null)));

      // Check if it is already in the queue, send not present
      if (bucket.peekRequestFromQueue() != null) {
        return Optional.empty();
      }

      // Add the bucket to the set of buckets (does nothing if it's already in the set)
      buckets.add(bucket);

      // Add the request to the bucket's queue
      bucket.addRequestToQueue(request);
      return Optional.of(bucket);
    }
  }

  /**
   * Updates the rate-limit information and sets the result if the request was successful.
   *
   * @param request The request.
   * @param result The result of the request.
   * @param bucket The bucket the request belongs to.
   * @param responseTimestamp The timestamp directly after the response finished.
   */
  void handleResponse(
      RestRequest<T> request,
      RestRequestResult<T> result,
      RateLimitBucket<T> bucket,
      long responseTimestamp) {
    if (result == null || result.getResponse() == null) {
      return;
    }

    Response response = result.getResponse();
    int remaining =
        Integer.parseInt(Objects.requireNonNull(response.header("X-RateLimit-Remaining", "1")));
    long reset =
        (long)
            (Double.parseDouble(Objects.requireNonNull(response.header("X-RateLimit-Reset", "0")))
                * 1000);

    // Check if we received a 429 response
    if (result.getResponse().code() != 429) {
      // Check if we didn't already complete it exceptionally.
      CompletableFuture<RestRequestResult<T>> requestResult = request.getResult();
      if (!requestResult.isDone()) {
        requestResult.complete(result);
      }

      // Update bucket information
      bucket.setRateLimitRemaining(remaining);
      bucket.setRateLimitResetTimestamp(reset);
      return;
    }

    if (response.header("Via") == null) {
      logger.warn(
          "Hit a CloudFlare API ban! This means you were sending a very large amount of invalid"
              + " requests.");
      int retryAfter =
          Integer.parseInt(Objects.requireNonNull(response.header("Retry-after"))) * 1000;
      bucket.setRateLimitRemaining(retryAfter);
      bucket.setRateLimitResetTimestamp(responseTimestamp + retryAfter);
      return;
    }

    long retryAfter =
        result.getJsonBody().isNull()
            ? 0
            : (long) (result.getJsonBody().get("retry_after").asDouble() * 1000);
    logger.debug("Received a 429 response from Azure! Recalculating time offset...");

    // Update the bucket information
    bucket.setRateLimitRemaining(0);
    bucket.setRateLimitResetTimestamp(responseTimestamp + retryAfter);
  }
}
