package com.github.brenoepics.at4j.core.ratelimit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.github.brenoepics.at4j.AzureApi;
import com.github.brenoepics.at4j.core.AzureApiImpl;
import com.github.brenoepics.at4j.util.rest.RestEndpoint;
import com.github.brenoepics.at4j.util.rest.RestRequest;

public class RatelimitBucket {

  // The key is the subscription key, as global ratelimits are shared across the same account.
  private static final Map<String, Long> globalRatelimitResetTimestamp = new ConcurrentHashMap<>();

  private final AzureApiImpl api;

  private final ConcurrentLinkedQueue<RestRequest<?>> requestQueue = new ConcurrentLinkedQueue<>();

  private final RestEndpoint endpoint;
  private final String majorUrlParameter;

  private volatile long ratelimitResetTimestamp = 0;
  private volatile int ratelimitRemaining = 1;

  /**
   * Creates a RatelimitBucket for the given endpoint / parameter combination.
   *
   * @param api The api/shard to use.
   * @param endpoint The REST endpoint the ratelimit is tracked for.
   */
  public RatelimitBucket(AzureApi api, RestEndpoint endpoint) {
    this(api, endpoint, null);
  }

  /**
   * Creates a RatelimitBucket for the given endpoint / parameter combination.
   *
   * @param api The api/shard to use.
   * @param endpoint The REST endpoint the ratelimit is tracked for.
   * @param majorUrlParameter The url parameter this bucket is specific for. May be null.
   */
  public RatelimitBucket(AzureApi api, RestEndpoint endpoint, String majorUrlParameter) {
    this.api = (AzureApiImpl) api;
    this.endpoint = endpoint;
    this.majorUrlParameter = majorUrlParameter;
  }

  /**
   * Sets a global ratelimit.
   *
   * @param api A azure api instance.
   * @param resetTimestamp The reset timestamp of the global ratelimit.
   */
  public static void setGlobalRatelimitResetTimestamp(AzureApi api, long resetTimestamp) {
    globalRatelimitResetTimestamp.put(api.getSubscriptionKey(), resetTimestamp);
  }

  /**
   * Adds the given request to the bucket's queue.
   *
   * @param request The request to add.
   */
  public void addRequestToQueue(RestRequest<?> request) {
    requestQueue.add(request);
  }

  /**
   * Polls a request from the bucket's queue.
   *
   * @return The polled request.
   */
  public RestRequest<?> pollRequestFromQueue() {
    return requestQueue.poll();
  }

  /**
   * Peeks a request from the bucket's queue.
   *
   * @return The peeked request.
   */
  public RestRequest<?> peekRequestFromQueue() {
    return requestQueue.peek();
  }

  /**
   * Sets the remaining requests till ratelimit.
   *
   * @param ratelimitRemaining The remaining requests till ratelimit.
   */
  public void setRatelimitRemaining(int ratelimitRemaining) {
    this.ratelimitRemaining = ratelimitRemaining;
  }

  /**
   * Sets the ratelimit reset timestamp.
   *
   * @param ratelimitResetTimestamp The ratelimit reset timestamp.
   */
  public void setRatelimitResetTimestamp(long ratelimitResetTimestamp) {
    this.ratelimitResetTimestamp = ratelimitResetTimestamp;
  }

  /**
   * Gets the time in seconds how long you have to wait till there's space in the bucket again.
   *
   * @return The time in seconds how long you have to wait till there's space in the bucket again.
   */
  public int getTimeTillSpaceGetsAvailable() {
    long globalRLResetTimestamp =
        RatelimitBucket.globalRatelimitResetTimestamp.getOrDefault(api.getSubscriptionKey(), 0L);
    long timestamp = System.currentTimeMillis();
    if (ratelimitRemaining > 0 && (globalRLResetTimestamp - timestamp) <= 0) {
      return 0;
    }
    return (int) (Math.max(ratelimitResetTimestamp, globalRLResetTimestamp) - timestamp);
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
    if (!(obj instanceof RatelimitBucket)) {
      return false;
    }
    RatelimitBucket otherBucket = (RatelimitBucket) obj;
    return equals(otherBucket.endpoint, otherBucket.majorUrlParameter);
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
