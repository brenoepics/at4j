package io.github.brenoepics.at4j.data.request;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.brenoepics.at4j.core.AzureApiImpl;
import io.github.brenoepics.at4j.data.response.DetectResponse;
import io.github.brenoepics.at4j.util.rest.RestRequest;
import io.github.brenoepics.at4j.util.rest.RestRequestResult;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;

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
    RestRequest<Optional<DetectResponse>> request = mock(RestRequest.class);
    HttpResponse<String> httpResponse = mock(HttpResponse.class);
    RestRequestResult<Optional<DetectResponse>> response = new RestRequestResult<>(request, httpResponse);
    Optional<DetectResponse> result = params.handleResponse(response);
    assertTrue(result.isEmpty());
  }

  @Test
  void handleResponse_returnsDetectResponseForValidJsonBody() throws IOException {
    DetectLanguageParams params = new DetectLanguageParams("test text");
    RestRequest<Optional<DetectResponse>> request = mock(RestRequest.class);
    AzureApiImpl azureApi = mock(AzureApiImpl.class);
    ObjectMapper objectMapper = new ObjectMapper();
    when(azureApi.getObjectMapper()).thenReturn(objectMapper);
    when(request.getApi()).thenReturn(azureApi);
    HttpResponse<String> httpResponse = mock(HttpResponse.class);
    when(httpResponse.body())
        .thenReturn(
            "[{\"isTranslationSupported\":true,\"isTransliterationSupported\":false,\"language\":\"en\",\"score\":0.97}]");
    RestRequestResult<Optional<DetectResponse>> response = new RestRequestResult<>(request, httpResponse);
    Optional<DetectResponse> result = params.handleResponse(response);
    assertTrue(result.isPresent());
    assertEquals("en", result.get().getFirst().getLanguageCode());
  }
}