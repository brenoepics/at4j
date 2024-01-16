package io.github.brenoepics.at4j.data.request;

import io.github.brenoepics.at4j.data.request.optional.ProfanityAction;
import io.github.brenoepics.at4j.data.request.optional.ProfanityMarker;
import io.github.brenoepics.at4j.data.request.optional.TextType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TranslateParamsTest {

  @Test
  void shouldSetAndGetText() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setText("Bonjour");
    assertEquals("Bonjour", params.getText());
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
  void shouldSetAndGetTargetLanguages() {
    TranslateParams params = new TranslateParams("Hello", List.of("fr"));
    params.setTargetLanguages("de", "it");
    assertTrue(params.getTargetLanguages().containsAll(Arrays.asList("de", "it")));
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
}
