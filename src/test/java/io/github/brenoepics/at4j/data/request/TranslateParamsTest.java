package io.github.brenoepics.at4j.data.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.brenoepics.at4j.azure.lang.Language;
import io.github.brenoepics.at4j.azure.lang.LanguageDirection;
import io.github.brenoepics.at4j.data.Translation;
import io.github.brenoepics.at4j.data.request.optional.ProfanityAction;
import io.github.brenoepics.at4j.data.request.optional.ProfanityMarker;
import io.github.brenoepics.at4j.data.request.optional.TextType;
import io.github.brenoepics.at4j.data.response.TranslationResponse;
import io.github.brenoepics.at4j.util.rest.RestRequestResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TranslateParamsTest {

  @Test
  void shouldSetAndGetText() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setTexts(Collections.singleton("Bonjour"));
    assertEquals("Bonjour", params.getTexts().get(1));
  }

  @Test
  void shouldSetAndGetTextType() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setTextType(TextType.HTML);
    Assertions.assertEquals(TextType.HTML, params.getTextType());
  }

  @Test
  void shouldSetAndGetProfanityAction() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setProfanityAction(ProfanityAction.MARKED);
    Assertions.assertEquals(ProfanityAction.MARKED, params.getProfanityAction());
  }

  @Test
  void shouldSetAndGetProfanityMarker() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setProfanityMarker(ProfanityMarker.TAG);
    Assertions.assertEquals(ProfanityMarker.TAG, params.getProfanityMarker());
  }

  @Test
  void shouldSetAndGetIncludeAlignment() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setIncludeAlignment(true);
    assertTrue(params.getIncludeAlignment());
  }

  @Test
  void shouldSetAndGetIncludeSentenceLength() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setIncludeSentenceLength(true);
    assertTrue(params.getIncludeSentenceLength());
  }

  @Test
  void shouldSetAndGetSourceLanguage() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setSourceLanguage("en");
    assertEquals("en", params.getSourceLanguage());
  }

  @Test
  void shouldSetAndGetSourceLanguage2() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setSourceLanguage(new Language("en", "English", "English", LanguageDirection.LTR));
    assertEquals("en", params.getSourceLanguage());
  }

  @Test
  void shouldSetAndGetTargetLanguages() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setTargetLanguages("de", "it");
    assertTrue(params.getTargetLanguages().containsAll(Arrays.asList("de", "it")));
  }

  @Test
  void shouldSetAndGetTargetLanguages2() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setTargetLanguages(
        Collections.singleton(new Language("en", "English", "English", LanguageDirection.LTR)));
    assertTrue(params.getTargetLanguages().contains("en"));
  }

  @Test
  void shouldSetAndGetSuggestedFromLanguage() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setSuggestedFromLanguage("en");
    assertEquals("en", params.getSuggestedFromLanguage());
  }

  @Test
  void shouldGetQueryParameters() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setSourceLanguage("en");
    params.setTextType(TextType.HTML);
    params.setProfanityAction(ProfanityAction.MARKED);
    params.setProfanityMarker(ProfanityMarker.TAG);
    params.setIncludeAlignment(true);
    params.setIncludeSentenceLength(true);
    params.setSuggestedFromLanguage("en");

    Map<String, String> expectedParams = new HashMap<>();
    expectedParams.put("from", "en");
    expectedParams.put("textType", "html");
    expectedParams.put("profanityAction", "Marked");
    expectedParams.put("profanityMarker", "Tag");
    expectedParams.put("includeAlignment", "true");
    expectedParams.put("includeSentenceLength", "true");
    expectedParams.put("suggestedFrom", "en");

    assertEquals(expectedParams, params.getQueryParameters());
  }

  @Test
  void whenTextsAreSet_thenTextsShouldBeCorrectlyMapped() {
    TranslateParams params = new TranslateParams(List.of("Hello", "Bonjour"), List.of("fr", "de"));
    assertEquals("Hello", params.getTexts().get(1));
    assertEquals("Bonjour", params.getTexts().get(2));
  }

  @Test
  void whenNoTextTypeIsSet_thenDefaultShouldBeNull() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    assertNull(params.getTextType());
  }

  @Test
  void whenNoProfanityActionIsSet_thenDefaultShouldBeNull() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    assertNull(params.getProfanityAction());
  }

  @Test
  void whenNoProfanityMarkerIsSet_thenDefaultShouldBeNull() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    assertNull(params.getProfanityMarker());
  }

  @Test
  void whenNoIncludeAlignmentIsSet_thenDefaultShouldBeNull() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    assertNull(params.getIncludeAlignment());
  }

  @Test
  void whenNoIncludeSentenceLengthIsSet_thenDefaultShouldBeNull() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    assertNull(params.getIncludeSentenceLength());
  }

  @Test
  void whenNoSourceLanguageIsSet_thenDefaultShouldBeNull() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    assertNull(params.getSourceLanguage());
  }

  @Test
  void whenNoTargetLanguagesAreSet_thenDefaultShouldBeNull() {
    TranslateParams params = new TranslateParams("Hello", null);
    assertNull(params.getTargetLanguages());
  }

  @Test
  void whenNoSuggestedFromLanguageIsSet_thenDefaultShouldBeNull() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    assertNull(params.getSuggestedFromLanguage());
  }

  @Test
  void whenQueryParametersAreEmpty_thenShouldReturnEmptyMap() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    assertTrue(params.getQueryParameters().isEmpty());
  }

  @Test
  void shouldHandleResponseCorrectly() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    RestRequestResult mockResponse = mock(RestRequestResult.class);
    JsonNode mockJsonBody = mock(JsonNode.class);
    JsonNode mockTranslationNode = mock(JsonNode.class);
    JsonNode mockDetectedLanguageNode = mock(JsonNode.class);

    when(mockResponse.getJsonBody()).thenReturn(mockJsonBody);
    when(mockJsonBody.get(0)).thenReturn(mockTranslationNode);
    when(mockTranslationNode.has("translations")).thenReturn(true);
    when(mockTranslationNode.get("detectedLanguage")).thenReturn(mockDetectedLanguageNode);

    Assertions.assertThrows(NullPointerException.class, () -> params.handleResponse(mockResponse));
  }

  @Test
  void shouldHandleNullResponse() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    RestRequestResult mockResponse = mock(RestRequestResult.class);

    when(mockResponse.getJsonBody()).thenReturn(null);

    Assertions.assertThrows(NullPointerException.class, () -> params.handleResponse(mockResponse));
  }

  @Test
  void shouldHandleEmptyJsonBody() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    RestRequestResult mockResponse = mock(RestRequestResult.class);
    JsonNode mockJsonBody = mock(JsonNode.class);

    when(mockResponse.getJsonBody()).thenReturn(mockJsonBody);
    when(mockJsonBody.isEmpty()).thenReturn(true);

    Optional<TranslationResponse> result = params.handleResponse(mockResponse);

    assertFalse(result.isPresent());
  }

  @Test
  void shouldHandleJsonBodyWithoutTranslations() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    RestRequestResult mockResponse = mock(RestRequestResult.class);
    JsonNode mockJsonBody = mock(JsonNode.class);
    JsonNode mockTranslationNode = mock(JsonNode.class);

    when(mockResponse.getJsonBody()).thenReturn(mockJsonBody);
    when(mockJsonBody.get(0)).thenReturn(mockTranslationNode);
    when(mockTranslationNode.has("translations")).thenReturn(false);

    Optional<TranslationResponse> result = params.handleResponse(mockResponse);

    assertTrue(result.isPresent());
    assertNotNull(result.get());
    assertThrows(IndexOutOfBoundsException.class, result.get()::getFirstResult);
  }

  @Test
  void shouldHandleJsonBodyNullTranslations() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    RestRequestResult mockResponse = mock(RestRequestResult.class);
    JsonNode mockJsonBody = mock(JsonNode.class);
    when(mockResponse.getJsonBody()).thenReturn(mockJsonBody);
    when(mockResponse.getJsonBody().isNull()).thenReturn(true);

    Optional<TranslationResponse> result = params.handleResponse(mockResponse);

    assertFalse(result.isPresent());
  }

  @Test
  void shouldHandleJsonBodyWithValidTranslations() throws JsonProcessingException {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    String json =
        "[{\"detectedLanguage\":{\"language\":\"en\",\"score\":0.98},\"translations\":[{\"text\":\"Olá,"
            + " mundo\",\"to\":\"pt\"}]}]";
    RestRequestResult mockResponse = mock(RestRequestResult.class);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode mockJsonBody = mapper.readTree(json);

    when(mockResponse.getJsonBody()).thenReturn(mockJsonBody);

    Optional<TranslationResponse> result = params.handleResponse(mockResponse);

    assertTrue(result.isPresent());
    assertNotNull(result.get());
    assertEquals(
        "Olá, mundo",
        result
            .get()
            .getFirstResult()
            .getFirstTranslation()
            .orElse(new Translation("en", "err"))
            .getText());
  }

  @Test
  void shouldHandleJsonBodyWithNoValidTranslations() throws JsonProcessingException {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    String json =
        "[{\"detectedLanguage\":{\"language\":\"en\",\"score\":0.98},\"translations\":[]}]";
    RestRequestResult mockResponse = mock(RestRequestResult.class);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode mockJsonBody = mapper.readTree(json);

    when(mockResponse.getJsonBody()).thenReturn(mockJsonBody);

    Optional<TranslationResponse> result = params.handleResponse(mockResponse);

    assertTrue(result.isPresent());
    assertNotNull(result.get());
    assertTrue(result.get().getFirstResult().getTranslations().isEmpty());
  }
}
