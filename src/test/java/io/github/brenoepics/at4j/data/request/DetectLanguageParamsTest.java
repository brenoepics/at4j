package io.github.brenoepics.at4j.data.request;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.brenoepics.at4j.AzureApi;
import io.github.brenoepics.at4j.core.AzureApiImpl;
import io.github.brenoepics.at4j.data.response.DetectResponse;
import io.github.brenoepics.at4j.util.rest.RestRequest;
import io.github.brenoepics.at4j.util.rest.RestRequestResult;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class DetectLanguageParamsTest {

  @Test
  void getTexts_returnsCorrectTexts() {
    DetectLanguageParams params = new DetectLanguageParams(Arrays.asList("text1", "text2"));
    assertEquals("text1", params.getTexts().get(1));
    assertEquals("text2", params.getTexts().get(2));
  }

  @Test
  void getBody_returnsNullForEmptyTexts() {
    DetectLanguageParams params = new DetectLanguageParams(new ArrayList<>());
    assertNull(params.getBody());
  }

  @Test
  void handleResponse_returnsEmptyOptionalForNullJsonBody() throws IOException {
    DetectLanguageParams params = new DetectLanguageParams("test text");
    RestRequest request = mock(RestRequest.class);
    HttpResponse<String> httpResponse = mock(HttpResponse.class);
    RestRequestResult response = new RestRequestResult(request, httpResponse);
    Optional<DetectResponse> result = params.handleResponse(response);
    assertTrue(result.isEmpty());
  }

  @Test
  void handleResponse_returnsDetectResponseForValidJsonBody() throws IOException {
    DetectLanguageParams params = new DetectLanguageParams("test text");
    RestRequest request = mock(RestRequest.class);
    AzureApi azureApi = mock(AzureApiImpl.class);
    ObjectMapper objectMapper = new ObjectMapper();
    when(azureApi.getObjectMapper()).thenReturn(objectMapper);
    when(request.getApi()).thenReturn(azureApi);
    HttpResponse<String> httpResponse = mock(HttpResponse.class);
    when(httpResponse.body())
        .thenReturn(
            "[{\"isTranslationSupported\":true,\"isTransliterationSupported\":false,\"language\":\"en\",\"score\":0.97}]");
    RestRequestResult response = new RestRequestResult(request, httpResponse);
    Optional<DetectResponse> result = params.handleResponse(response);
    assertTrue(result.isPresent());
    assertEquals("en", result.get().getFirst().getLanguageCode());
  }

  @Test
  void shouldReturnCorrectTextsWhenMultipleTextsAreAdded() {
    DetectLanguageParams params = new DetectLanguageParams("Hello");
    params.addText("Bonjour");
    assertEquals("Hello", params.getTexts().get(1));
    assertEquals("Bonjour", params.getTexts().get(2));
  }

  @Test
  void shouldReturnNullBodyWhenNoTextsAreAdded() {
    DetectLanguageParams params = new DetectLanguageParams(new ArrayList<>());
    assertNull(params.getBody());
  }

  @Test
  void shouldHandleResponseWithEmptyJsonBody() {
    DetectLanguageParams params = new DetectLanguageParams("Hello");
    RestRequestResult mockResponse = mock(RestRequestResult.class);
    JsonNode mockJsonBody = mock(JsonNode.class);

    when(mockResponse.getJsonBody()).thenReturn(mockJsonBody);
    when(mockJsonBody.isEmpty()).thenReturn(true);

    Optional<DetectResponse> result = params.handleResponse(mockResponse);

    assertFalse(result.isPresent());
  }

  @Test
  void shouldHandleResponseWithValidJsonBody() {
    DetectLanguageParams params = new DetectLanguageParams("Hello");
    RestRequestResult mockResponse = mock(RestRequestResult.class);
    JsonNode mockJsonBody = mock(JsonNode.class);
    JsonNode mockDetectionNode = mock(JsonNode.class);

    when(mockResponse.getJsonBody()).thenReturn(mockJsonBody);
    when(mockJsonBody.get(0)).thenReturn(mockDetectionNode);
    when(mockDetectionNode.has("language")).thenReturn(true);

    Optional<DetectResponse> result = params.handleResponse(mockResponse);

    assertTrue(result.isPresent());
  }
}
