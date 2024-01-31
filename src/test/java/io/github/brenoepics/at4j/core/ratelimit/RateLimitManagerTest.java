package io.github.brenoepics.at4j.core.ratelimit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

class RateLimitManagerTest<T> {
		private AzureApiImpl<T> api;
		private RestRequest<T> request;
		private RateLimitManager<T> rateLimitManager;

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
				Optional<RateLimitBucket<T>> bucket = rateLimitManager.searchBucket(request);
				assertTrue(bucket.isPresent());
		}

		@Test
		void testWaitUntilSpaceGetsAvailable() {
				RateLimitBucket<T> bucket = new RateLimitBucket<>(RestEndpoint.LANGUAGES);
				bucket.setRateLimitRemaining(1000);
				bucket.setRateLimitResetTimestamp(System.currentTimeMillis() + 1000);
				assertDoesNotThrow(() -> rateLimitManager.waitUntilSpaceGetsAvailable(bucket));
		}

		@Test
		void testRetryRequest() {
				when(request.getEndpoint()).thenReturn(RestEndpoint.LANGUAGES);
				rateLimitManager.searchBucket(request);
				RestRequest<T> retriedRequest = rateLimitManager.retryRequest(new RateLimitBucket<>(RestEndpoint.LANGUAGES ));
				assertNull(retriedRequest);
		}

		@Test
		void retryRequestWhenBucketIsEmpty() {
				when(request.getEndpoint()).thenReturn(RestEndpoint.LANGUAGES);
				rateLimitManager.searchBucket(request);
				RestRequest<T> retriedRequest = rateLimitManager.retryRequest(new RateLimitBucket<>(RestEndpoint.LANGUAGES));
				assertNull(retriedRequest);
		}

		@Test
		void handleResponseWhenStatusCodeIsNot429() {
				HttpHeaders headers = mock(HttpHeaders.class);
				when(headers.firstValue(RateLimitManager.RATE_LIMITED_HEADER)).thenReturn(Optional.of("1"));
				when(headers.firstValue(RateLimitManager.RATE_LIMIT_RESET_HEADER)).thenReturn(Optional.of("0"));
				RateLimitBucket<T> bucket = new RateLimitBucket<>(RestEndpoint.LANGUAGES);
				RestRequestResult<T> result = mock(RestRequestResult.class);
				when(result.getResponse()).thenReturn(mock(HttpResponse.class));
				when(result.getResponse().statusCode()).thenReturn(200);
				when(result.getResponse().headers()).thenReturn(headers);
				CompletableFuture<RestRequestResult<T>> future = new CompletableFuture<>();
				future.complete(result);
				when(request.getResult()).thenReturn(future);
				rateLimitManager.handleResponse(request, result, bucket, System.currentTimeMillis());
				assertEquals(1, bucket.getRateLimitRemaining());
		}
}