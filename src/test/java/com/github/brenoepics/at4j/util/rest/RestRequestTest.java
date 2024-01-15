package com.github.brenoepics.at4j.util.rest;

import com.github.brenoepics.at4j.core.AzureApiImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class RestRequestTest<T> {

  @Mock private AzureApiImpl<T> api;
  @Mock private RestMethod method;
  @Mock private RestEndpoint endpoint;

  private RestRequest<T> restRequest;

  @BeforeEach
  void setUp() {
    restRequest = new RestRequest<>(api, method, endpoint);
  }

  @Test
  @DisplayName("Should add query parameter correctly")
  void shouldAddQueryParameter() {
    // api-version already added by default
    restRequest.addQueryParameter("to", "pt");
    restRequest.addQueryParameter("to", "es");
    restRequest.addQueryParameter("to", "fr");
    Assertions.assertEquals(4, restRequest.getQueryParameters().size());
    Assertions.assertTrue(restRequest.getQueryParameters().containsEntry("to", "pt"));
  }

  @Test
  @DisplayName("Should add header correctly")
  void shouldAddHeader() {
    restRequest.addHeader("headerName", "headerValue");
    Assertions.assertEquals(1, restRequest.getHeaders().size());
    Assertions.assertTrue(restRequest.getHeaders().containsKey("headerName"));
    Assertions.assertEquals("headerValue", restRequest.getHeaders().get("headerName"));
  }

  @Test
  @DisplayName("Should set url parameters correctly")
  void shouldSetUrlParameters() {
    restRequest.setUrlParameters("param1", "param2");
    Assertions.assertEquals(2, restRequest.getUrlParameters().length);
    Assertions.assertEquals("param1", restRequest.getUrlParameters()[0]);
    Assertions.assertEquals("param2", restRequest.getUrlParameters()[1]);
  }

  @Test
  @DisplayName("Should set body correctly")
  void shouldSetBody() {
    restRequest.setBody("bodyContent");
    Assertions.assertTrue(restRequest.getBody().isPresent());
    Assertions.assertEquals("bodyContent", restRequest.getBody().get());
  }
}
