package io.github.brenoepics.at4j.core.ratelimit;

import io.github.brenoepics.at4j.core.AzureApiImpl;
import io.github.brenoepics.at4j.core.exceptions.AzureException;
import io.github.brenoepics.at4j.util.logging.LoggerUtil;
import io.github.brenoepics.at4j.util.rest.RestRequest;
import io.github.brenoepics.at4j.util.rest.RestRequestHandler;
import io.github.brenoepics.at4j.util.rest.RestRequestResponseInformationImpl;
import io.github.brenoepics.at4j.util.rest.RestRequestResult;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.apache.logging.log4j.Logger;

/** This class manages rate-limits and keeps track of them. */
public class RateLimitManager<T> {

  /** The (logger) of this class. */
  private static final Logger logger = LoggerUtil.getLogger(RateLimitManager.class);

  /** The Azure API instance for this rate-limit manager. */
  private final AzureApiImpl<T> api;

  /** All buckets. */
  private final Set<RateLimitBucket<T>> buckets = new HashSet<>();

  /** The header for rate-limit remaining information. */
  public static final String RATE_LIMITED_HEADER = "X-RateLimit-Remaining";

  /** The header name for rate-limit reset information. */
  public static final String RATE_LIMIT_RESET_HEADER = "X-RateLimit-Reset";

  /** The header name for rate-limit reset information. */
  public static final String RATE_LIMITED_HEADER_CLOUDFLARE = "Retry-after";

  /** The body name for rate-limit reset information. */
  public static final String RATE_LIMITED_BODY_CLOUDFLARE = "retry_after";

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

    if (searchBucket.isEmpty()) {
      return;
    }

    api.getThreadPool().getExecutorService().submit(() -> submitRequest(searchBucket.get()));
  }

  /**
   * Submits the request to the given bucket.
   *
   * @param bucket The bucket to submit the request to.
   */
  private void submitRequest(RateLimitBucket<T> bucket) {
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

      // Execute the request and get the result
      result = currentRequest.executeBlocking();
      responseTimestamp = System.currentTimeMillis();

    } catch (Exception e) {
      responseTimestamp = System.currentTimeMillis();
      if (currentRequest.getResult().isDone()) {
        logger.warn("Exception for a already done request. This should not happen!", e);
      }

      if (e instanceof AzureException) {
        result = mapAzureException(e);
      }

      currentRequest.getResult().completeExceptionally(e);
    } finally {
      if (result != null && result.getResponse() != null) {
        handleResponse(currentRequest, result, bucket, responseTimestamp);
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

  /**
   * Maps the given exception to a {@link RestRequestResult}.
   *
   * @param t The exception to map.
   * @return The mapped exception.
   */
  @SuppressWarnings("unchecked")
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
      RateLimitBucket<T> bucket = getMatchingBucket(request);

      // Check if it is already in the queue, send not present
      if (bucket.peekRequestFromQueue() != null) {
        return Optional.empty();
      }

      buckets.add(bucket);
      bucket.addRequestToQueue(request);
      return Optional.of(bucket);
    }
  }

  /**
   * Gets the bucket that matches the given request.
   *
   * @param request The request.
   * @return The bucket that matches the request.
   */
  RateLimitBucket<T> getMatchingBucket(RestRequest<T> request) {
    synchronized (buckets) {
      return buckets.stream()
          .filter(b -> b.endpointMatches(request.getEndpoint()))
          .findAny()
          .orElseGet(() -> new RateLimitBucket<>(request.getEndpoint()));
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
    try {
      HttpResponse<String> response = result.getResponse();

      // Check if we did not receive a rate-limit response
      if (result.getResponse().statusCode() != 429) {
        handleRateLimit(request.getResult(), result, bucket, response.headers());
        return;
      }

      if (response.headers().firstValue("Via").isEmpty()) {
        handleCloudFlare(response.headers(), bucket);
        return;
      }

      long retryAfter = 0;

      if (!result.getJsonBody().isNull()) {
        retryAfter =
            (long) (result.getJsonBody().get(RATE_LIMITED_BODY_CLOUDFLARE).asDouble() * 1000);
      }

      logger.debug("Received a 429 response from Azure! Recalculating time offset...");

      bucket.setRateLimitRemaining(0);
      bucket.setRateLimitResetTimestamp(responseTimestamp + retryAfter);

    } catch (Exception e) {
      logger.warn("Encountered unexpected exception.", e);
    }
  }

  /**
   * Handles the CloudFlare rate-limit.
   *
   * @param headers The headers of the response.
   * @param bucket The bucket the request belongs to.
   */
  private void handleCloudFlare(HttpHeaders headers, RateLimitBucket<T> bucket) {
    logger.warn(
        "Hit a CloudFlare API ban! {}",
        "You were sending a very large amount of invalid requests.");
    int retryAfter =
        Integer.parseInt(getHeader(headers, RATE_LIMITED_HEADER_CLOUDFLARE, "10")) * 1000;
    bucket.setRateLimitRemaining(retryAfter);
    bucket.setRateLimitResetTimestamp(System.currentTimeMillis() + retryAfter);
  }

  /**
   * Handles the rate-limit information.
   *
   * @param request The request.
   * @param result The result of the request.
   * @param bucket The bucket the request belongs to.
   * @param headers The headers of the response.
   */
  private void handleRateLimit(
      CompletableFuture<RestRequestResult<T>> request,
      RestRequestResult<T> result,
      RateLimitBucket<T> bucket,
      HttpHeaders headers) {

    // Check if we didn't already complete it exceptionally.
    if (!request.isDone()) {
      request.complete(result);
    }

    String remaining = getHeader(headers, RATE_LIMITED_HEADER, "1");
    String reset = getHeader(headers, RATE_LIMIT_RESET_HEADER, "0");

    // Update bucket information
    bucket.setRateLimitRemaining(Integer.parseInt(remaining));
    bucket.setRateLimitResetTimestamp((long) (Double.parseDouble(reset) * 1000));
  }

  /**
   * Gets the header value from the given headers.
   *
   * @param headers The headers.
   * @param header The header to get the value for.
   * @param defaultValue The default value if the header is not present.
   * @return The header value.
   */
  public static String getHeader(HttpHeaders headers, String header, String defaultValue) {
    return Objects.requireNonNull(headers.firstValue(header).orElse(defaultValue));
  }
}
