package com.github.brenoepics.at4j.core.ratelimit;

import static org.junit.jupiter.api.Assertions.*;

import com.github.brenoepics.at4j.core.AzureApiImpl;
import com.github.brenoepics.at4j.core.exceptions.AzureException;
import com.github.brenoepics.at4j.core.thread.ThreadPool;
import com.github.brenoepics.at4j.util.rest.RestRequest;
import com.github.brenoepics.at4j.util.rest.RestRequestResult;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.*;

class RateLimitManagerTest<T> {

  @Mock private AzureApiImpl<T> api;
  @Mock private RestRequest<T> request;
  @Mock private RestRequestResult<T> result;
  @Mock private Response response;
  @Mock private RateLimitBucket<T> bucket;

  private RateLimitManager<T> rateLimitManager;

  @Mock private ThreadPool threadPool;
  @Mock private ExecutorService executorService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    rateLimitManager = new RateLimitManager<>(api);

    when(api.getThreadPool()).thenReturn(threadPool);
    when(threadPool.getExecutorService()).thenReturn(executorService);

    when(response.header("X-RateLimit-Remaining", "1")).thenReturn("10");
    when(response.header("X-RateLimit-Reset", "0")).thenReturn("1000");
  }

  @Test
  void queuesRequestWhenBucketIsPresent() {
    when(request.getMajorUrlParameter()).thenReturn(Optional.empty());
    when(bucket.peekRequestFromQueue()).thenReturn(null);

    rateLimitManager.queueRequest(request);

    verify(executorService, times(1)).submit(any(Runnable.class));
  }

  @Test
  void retriesRequestWhenBucketIsNotEmpty() {
    when(bucket.peekRequestFromQueue()).thenReturn(request);

    RestRequest<T> retriedRequest = rateLimitManager.retryRequest(bucket);

    assertEquals(request, retriedRequest);
  }

  @Test
  void doesNotRetryRequestWhenBucketIsEmpty() {
    when(bucket.peekRequestFromQueue()).thenReturn(null);

    RestRequest<T> retriedRequest = rateLimitManager.retryRequest(bucket);

    assertNull(retriedRequest);
  }

  @Test
  void doesNotHandleResponseWhenResultIsNull() {
    assertDoesNotThrow(
        () -> rateLimitManager.handleResponse(request, null, bucket, System.currentTimeMillis()));
  }

  @Test
  void queuesRequestWhenBucketIsNotPresent() {
    when(request.getMajorUrlParameter()).thenReturn(Optional.empty());
    when(bucket.peekRequestFromQueue()).thenReturn(request);

    rateLimitManager.queueRequest(request);
    assertDoesNotThrow(
        () -> rateLimitManager.handleResponse(request, result, bucket, System.currentTimeMillis()));
  }

  @Test
  void handleResponseUpdatesBucketInformationWhenResponseCodeIsNot429() {
    when(response.header("X-RateLimit-Remaining", "1")).thenReturn("10");
    when(response.header("X-RateLimit-Reset", "0")).thenReturn("1000");
    when(result.getResponse()).thenReturn(response);
    when(response.code()).thenReturn(200);
    when(request.getResult()).thenReturn(CompletableFuture.completedFuture(result));

    rateLimitManager.handleResponse(request, result, bucket, System.currentTimeMillis());

    verify(bucket, times(1)).setRateLimitRemaining(10);
    verify(bucket, times(1)).setRateLimitResetTimestamp(anyLong());
  }

  @Test
  void handleResponseDoesNotUpdateBucketInformationWhenResponseCodeIs429AndViaHeaderIsNull() {
    when(result.getResponse()).thenReturn(response);
    when(response.code()).thenReturn(429);
    when(response.header("Via")).thenReturn(null);
    when(response.header("Retry-after")).thenReturn("1000");
    when(response.header("X-RateLimit-Remaining", "1")).thenReturn("10");
    when(response.header("X-RateLimit-Reset", "0")).thenReturn("1000");

    assertDoesNotThrow(
        () -> rateLimitManager.handleResponse(request, result, bucket, System.currentTimeMillis()));
  }

  @Test
  void handleCurrentRequestThrowsException() throws AzureException, IOException {
    // Arrange
    RuntimeException expectedException = new RuntimeException();
    when(request.executeBlocking()).thenThrow(expectedException);

    // Act
    Executable executable =
        () ->
            rateLimitManager.handleCurrentRequest(
                result, request, bucket, System.currentTimeMillis());

    // Assert
    assertThrows(RuntimeException.class, executable);
  }

  @Test
  void
      handleResponseDoesNotUpdateBucketInformationWhenResponseCodeIsNot429AndRequestResultIsDone() {
    when(result.getResponse()).thenReturn(response);
    when(response.code()).thenReturn(200);
    when(request.getResult()).thenReturn(CompletableFuture.completedFuture(result));

    assertDoesNotThrow(
        () -> rateLimitManager.handleResponse(request, result, bucket, System.currentTimeMillis()));
  }

  @Test
  void searchBucketReturnsBucketWhenBucketIsPresentAndRequestQueueIsEmpty() {
    when(request.getMajorUrlParameter()).thenReturn(Optional.empty());
    when(bucket.peekRequestFromQueue()).thenReturn(null);

    Optional<RateLimitBucket<T>> searchBucket = rateLimitManager.searchBucket(request);

    assertTrue(searchBucket.isPresent());
  }
}
