package io.github.brenoepics.at4j.core.ratelimit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.brenoepics.at4j.AzureApi;
import io.github.brenoepics.at4j.core.AzureApiImpl;
import io.github.brenoepics.at4j.util.rest.RestEndpoint;
import io.github.brenoepics.at4j.util.rest.RestRequest;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import io.github.brenoepics.at4j.util.rest.RestRequestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RateLimitManagerTest<T, T3, T4> {
  private AzureApi api;
  private RestRequest request;
  private RateLimitManager<T, T3, T4> rateLimitManager;

  @BeforeEach
  public void setUp() {
    api = mock(AzureApiImpl.class, RETURNS_DEEP_STUBS);
    request = mock(RestRequest.class);
    rateLimitManager = new RateLimitManager<>(api);
  }

  @Test
  void testQueueRequest() {
    when(request.getEndpoint()).thenReturn(RestEndpoint.LANGUAGES);
    rateLimitManager.queueRequest(request);
    verify(api.getThreadPool().getExecutorService()).submit(any(Runnable.class));
  }

  @Test
  void testSearchBucket() {
    when(request.getEndpoint()).thenReturn(RestEndpoint.LANGUAGES);
    Optional<RateLimitBucket<T, T4, T3>> bucket = rateLimitManager.searchBucket(request);
    assertTrue(bucket.isPresent());
  }

  @Test
  void testWaitUntilSpaceGetsAvailable() {
    RateLimitBucket<T, T4, T3> bucket = new RateLimitBucket<>(RestEndpoint.LANGUAGES);
    bucket.setRateLimitRemaining(1000);
    bucket.setRateLimitResetTimestamp(System.currentTimeMillis() + 1000);
    assertDoesNotThrow(() -> rateLimitManager.waitUntilSpaceGetsAvailable(bucket));
  }

  @Test
  void testRetryRequest() {
    when(request.getEndpoint()).thenReturn(RestEndpoint.LANGUAGES);
    rateLimitManager.searchBucket(request);
    RestRequest retriedRequest =
        rateLimitManager.retryRequest(new RateLimitBucket<>(RestEndpoint.LANGUAGES));
    assertNull(retriedRequest);
  }

  @Test
  void retryRequestWhenBucketIsEmpty() {
    when(request.getEndpoint()).thenReturn(RestEndpoint.LANGUAGES);
    rateLimitManager.searchBucket(request);
    RestRequest retriedRequest =
        rateLimitManager.retryRequest(new RateLimitBucket<>(RestEndpoint.LANGUAGES));
    assertNull(retriedRequest);
  }

  @Test
  void handleResponseWhenStatusCodeIsNot429() {
    HttpHeaders headers = mock(HttpHeaders.class);
    when(headers.firstValue(RateLimitManager.RATE_LIMITED_HEADER)).thenReturn(Optional.of("1"));
    when(headers.firstValue(RateLimitManager.RATE_LIMIT_RESET_HEADER)).thenReturn(Optional.of("0"));
    RateLimitBucket<T, T4, T3> bucket = new RateLimitBucket<>(RestEndpoint.LANGUAGES);
    RestRequestResult result = mock(RestRequestResult.class);
    when(result.getResponse()).thenReturn(mock(HttpResponse.class));
    when(result.getResponse().statusCode()).thenReturn(200);
    when(result.getResponse().headers()).thenReturn(headers);
    CompletableFuture<RestRequestResult> future = new CompletableFuture<>();
    future.complete(result);
    when(request.getResult()).thenReturn(future);
    rateLimitManager.handleResponse(request, result, bucket, System.currentTimeMillis());
    assertEquals(1, bucket.getRateLimitRemaining());
  }

  @Test
  void queueRequestShouldSubmitToExecutorServiceWhenBucketIsPresent() {
    when(request.getEndpoint()).thenReturn(RestEndpoint.LANGUAGES);
    rateLimitManager.queueRequest(request);
    verify(api.getThreadPool().getExecutorService()).submit(any(Runnable.class));
  }

  @Test
  void searchBucketShouldReturnBucketWhenBucketMatchesRequest() {
    when(request.getEndpoint()).thenReturn(RestEndpoint.LANGUAGES);
    Optional<RateLimitBucket<T, T4, T3>> bucket = rateLimitManager.searchBucket(request);
    assertTrue(bucket.isPresent());
  }

  @Test
  void handleResponseShouldUpdateBucketWhenStatusCodeIsNot429() {
    HttpHeaders headers = mock(HttpHeaders.class);
    when(headers.firstValue(RateLimitManager.RATE_LIMITED_HEADER)).thenReturn(Optional.of("1"));
    when(headers.firstValue(RateLimitManager.RATE_LIMIT_RESET_HEADER)).thenReturn(Optional.of("0"));
    RateLimitBucket<T, T4, T3> bucket = new RateLimitBucket<>(RestEndpoint.LANGUAGES);
    RestRequestResult result = mock(RestRequestResult.class);
    when(result.getResponse()).thenReturn(mock(HttpResponse.class));
    when(result.getResponse().statusCode()).thenReturn(200);
    when(result.getResponse().headers()).thenReturn(headers);
    CompletableFuture<RestRequestResult> future = new CompletableFuture<>();
    future.complete(result);
    when(request.getResult()).thenReturn(future);
    rateLimitManager.handleResponse(request, result, bucket, System.currentTimeMillis());
    assertEquals(1, bucket.getRateLimitRemaining());
  }

  @Test
  void handleResponseShouldHandleCloudFlareWhenStatusCodeIs429AndViaHeaderIsNotPresent() {
    HttpHeaders headers = mock(HttpHeaders.class);
    when(headers.firstValue("Via")).thenReturn(Optional.empty());
    when(headers.firstValue(RateLimitManager.RATE_LIMITED_HEADER_CLOUDFLARE))
        .thenReturn(Optional.of("10"));
    RateLimitBucket<T, T4, T3> bucket = new RateLimitBucket<>(RestEndpoint.LANGUAGES);
    RestRequestResult result = mock(RestRequestResult.class);
    when(result.getResponse()).thenReturn(mock(HttpResponse.class));
    when(result.getResponse().statusCode()).thenReturn(429);
    when(result.getResponse().headers()).thenReturn(headers);
    rateLimitManager.handleResponse(request, result, bucket, System.currentTimeMillis());
    assertEquals(10000, bucket.getRateLimitRemaining());
  }
}
