package io.github.brenoepics.at4j.core.ratelimit;

import io.github.brenoepics.at4j.util.rest.RestEndpoint;
import io.github.brenoepics.at4j.util.rest.RestRequest;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class represents a rate limit bucket for Azure API requests. It manages the rate limit for
 * each endpoint and major URL parameter combination.
 */
public class RateLimitBucket<T, T4, T3> {

  private final ConcurrentLinkedQueue<RestRequest<T>> requestQueue = new ConcurrentLinkedQueue<>();

  private final RestEndpoint endpoint;

  private volatile long rateLimitResetTimestamp = 0;
  private volatile int rateLimitRemaining = 1;

  /**
   * Creates a RateLimitBucket for the given endpoint / parameter combination.
   *
   * @param endpoint The REST endpoint the rate-limit is tracked for.
   */
  public RateLimitBucket(RestEndpoint endpoint) {
    this.endpoint = endpoint;
  }

  /**
   * Adds the given request to the bucket's queue.
   *
   * @param request The request to add.
   */
  public void addRequestToQueue(RestRequest<T> request) {
    requestQueue.add(request);
  }

  /** Polls a request from the bucket's queue. */
  public void pollRequestFromQueue() {
    requestQueue.poll();
  }

  /**
   * Peeks a request from the bucket's queue.
   *
   * @return The peeked request.
   */
  public RestRequest<T> peekRequestFromQueue() {
    return requestQueue.peek();
  }

  /**
   * Sets the remaining requests till rate-limit.
   *
   * @param rateLimitRemaining The remaining requests till rate-limit.
   */
  public void setRateLimitRemaining(int rateLimitRemaining) {
    this.rateLimitRemaining = rateLimitRemaining;
  }

  /**
   * Sets the rate-limit reset timestamp.
   *
   * @param rateLimitResetTimestamp The rate-limit reset timestamp.
   */
  public void setRateLimitResetTimestamp(long rateLimitResetTimestamp) {
    this.rateLimitResetTimestamp = rateLimitResetTimestamp;
  }

  /**
   * Gets the time in seconds how long you have to wait till there's space in the bucket again.
   *
   * @return The time in seconds how long you have to wait till there's space in the bucket again.
   */
  public int getTimeTillSpaceGetsAvailable() {
    long globalRLResetTimestamp = 0L;
    long timestamp = System.currentTimeMillis();
    if (rateLimitRemaining > 0 && (globalRLResetTimestamp - timestamp) <= 0) {
      return 0;
    }
    return (int) (Math.max(rateLimitResetTimestamp, globalRLResetTimestamp) - timestamp);
  }

  /**
   * Gets the remaining RateLimit
   *
   * @return int the remaining RateLimit
   */
  public int getRateLimitRemaining() {
    return rateLimitRemaining;
  }

  /**
   * Checks if a bucket created with the given parameters would equal this bucket.
   *
   * @param endpoint The endpoint.
   * @return Whether a bucket created with the given parameters would equal this bucket or not.
   */
  public boolean endpointMatches(RestEndpoint endpoint) {
    return this.endpoint == endpoint;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof RateLimitBucket) {
      RateLimitBucket<T, T4, T3> otherBucket = (RateLimitBucket) obj;
      return endpointMatches(otherBucket.endpoint);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 42;
    int endpointHash = endpoint == null ? 0 : endpoint.hashCode();
    hash = hash * 17 + endpointHash;
    return hash;
  }

  @Override
  public String toString() {
    return "Endpoint: " + (endpoint == null ? "global" : endpoint.getEndpointUrl());
  }
}
