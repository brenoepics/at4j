package com.github.brenoepics.at4j.core.ratelimit;

import com.github.brenoepics.at4j.AzureApi;
import com.github.brenoepics.at4j.core.AzureApiImpl;
import com.github.brenoepics.at4j.util.rest.RestEndpoint;
import com.github.brenoepics.at4j.util.rest.RestRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class represents a rate limit bucket for Azure API requests. It manages the rate limit for
 * each endpoint and major URL parameter combination.
 */
public class RateLimitBucket<T> {

  private final ConcurrentLinkedQueue<RestRequest<T>> requestQueue = new ConcurrentLinkedQueue<>();

  private final RestEndpoint endpoint;
  private final String majorUrlParameter;

  private volatile long rateLimitResetTimestamp = 0;
  private volatile int rateLimitRemaining = 1;

  /**
   * Creates a RateLimitBucket for the given endpoint / parameter combination.
   *
   * @param endpoint The REST endpoint the rate-limit is tracked for.
   * @param majorUrlParameter The url parameter this bucket is specific for. Maybe null.
   */
  public RateLimitBucket(RestEndpoint endpoint, String majorUrlParameter) {
    this.endpoint = endpoint;
    this.majorUrlParameter = majorUrlParameter;
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
   * Checks if a bucket created with the given parameters would equal this bucket.
   *
   * @param endpoint The endpoint.
   * @param majorUrlParameter The major url parameter.
   * @return Whether a bucket created with the given parameters would equal this bucket or not.
   */
  public boolean equals(RestEndpoint endpoint, String majorUrlParameter) {
    boolean endpointSame = this.endpoint == endpoint;
    boolean majorUrlParameterBothNull = this.majorUrlParameter == null && majorUrlParameter == null;
    boolean majorUrlParameterEqual =
        this.majorUrlParameter != null && this.majorUrlParameter.equals(majorUrlParameter);

    return endpointSame && (majorUrlParameterBothNull || majorUrlParameterEqual);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof RateLimitBucket) {
      RateLimitBucket<T> otherBucket = (RateLimitBucket<T>) obj;
      return equals(otherBucket.endpoint, otherBucket.majorUrlParameter);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 42;
    int urlParamHash = majorUrlParameter == null ? 0 : majorUrlParameter.hashCode();
    int endpointHash = endpoint == null ? 0 : endpoint.hashCode();

    hash = hash * 11 + urlParamHash;
    hash = hash * 17 + endpointHash;
    return hash;
  }

  @Override
  public String toString() {
    String str = "Endpoint: " + (endpoint == null ? "global" : endpoint.getEndpointUrl());
    str += ", Major url parameter:" + (majorUrlParameter == null ? "none" : majorUrlParameter);
    return str;
  }
}
