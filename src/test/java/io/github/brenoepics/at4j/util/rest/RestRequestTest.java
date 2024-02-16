package io.github.brenoepics.at4j.util.rest;

import io.github.brenoepics.at4j.AzureApi;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class RestRequestTest {

  @Mock private AzureApi api;
  @Mock private RestMethod method;
  @Mock private RestEndpoint endpoint;

  private RestRequest restRequest;

  @BeforeEach
  void setUp() {
    restRequest = new RestRequest(api, method, endpoint);
  }

  @Test
  @DisplayName("Should add query parameter correctly")
  void shouldAddQueryParameter() {
    // api-version already added by default
    restRequest.addQueryParameter("to", "pt");
    restRequest.addQueryParameter("to", "es");
    restRequest.addQueryParameter("to", "fr");
    restRequest.includeAuthorizationHeader(false);
    Assertions.assertTrue(restRequest.getQueryParameters().containsKey("to"));
    Assertions.assertTrue(
        restRequest.getQueryParameters().get("to").containsAll(
            Arrays.asList("pt", "es", "fr")));
  }

  @Test
  @DisplayName("Should add header correctly")
  void shouldAddHeader() {
    restRequest.addHeader("headerName", "headerValue");
    Assertions.assertEquals(1, restRequest.getHeaders().size());
    Assertions.assertTrue(restRequest.getHeaders().containsKey("headerName"));
    Assertions.assertEquals("headerValue",
                            restRequest.getHeaders().get("headerName"));
  }

  @Test
  @DisplayName("Should set body correctly")
  void shouldSetBody() {
    restRequest.setBody("bodyContent");
    Assertions.assertTrue(restRequest.getBody().isPresent());
    Assertions.assertEquals("bodyContent", restRequest.getBody().get());
  }

  @Test
  @DisplayName("Should include authorization header correctly")
  void shouldIncludeAuthorizationHeader() {
    restRequest.includeAuthorizationHeader(true);
    Assertions.assertTrue(restRequest.isIncludeAuthorizationHeader());
  }

  @Test
  @DisplayName("Should handle empty query parameters correctly")
  void shouldHandleEmptyQueryParameters() {
    restRequest.getQueryParameters().clear();
    Assertions.assertTrue(restRequest.getQueryParameters().isEmpty());
  }

  @Test
  @DisplayName("Should handle multiple query parameters correctly")
  void shouldHandleMultipleQueryParameters() {
    restRequest.addQueryParameter("param1", "value1");
    restRequest.addQueryParameter("param2", "value2");
    Assertions.assertTrue(
        restRequest.getQueryParameters().containsKey("param1"));
    Assertions.assertTrue(
        restRequest.getQueryParameters().containsKey("param2"));
  }

  @Test
  @DisplayName("Should handle empty headers correctly")
  void shouldHandleEmptyHeaders() {
    restRequest.getHeaders().clear();
    Assertions.assertTrue(restRequest.getHeaders().isEmpty());
  }

  @Test
  @DisplayName("Should handle multiple headers correctly")
  void shouldHandleMultipleHeaders() {
    restRequest.addHeader("header1", "value1");
    restRequest.addHeader("header2", "value2");
    Assertions.assertEquals(2, restRequest.getHeaders().size());
  }
}
