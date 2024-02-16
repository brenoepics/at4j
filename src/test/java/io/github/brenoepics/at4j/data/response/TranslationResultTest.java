package io.github.brenoepics.at4j.data.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.github.brenoepics.at4j.data.DetectedLanguage;
import io.github.brenoepics.at4j.data.Translation;
import io.github.brenoepics.at4j.data.TranslationResult;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class TranslationResultTest {

  @Test
  void createsTranslationResponseWithDetectedLanguageAndTranslations() {
    DetectedLanguage detectedLanguage =
        new DetectedLanguage("Hello World!", "en", 1.0f);
    Translation translation = new Translation("pt", "Olá, mundo!");
    TranslationResult response = new TranslationResult(
        translation.getText(), detectedLanguage, List.of(translation));

    assertEquals(detectedLanguage, response.getDetectedLanguage());
    assertEquals(1, response.getTranslations().size());
    assertEquals(translation, response.getTranslations().iterator().next());
  }

  @Test
  void createsTranslationResponseWithTranslationsOnly() {
    Translation translation = new Translation("pt", "Olá, mundo!");
    TranslationResult response =
        new TranslationResult(translation.getText(), List.of(translation));

    assertNull(response.getDetectedLanguage());
    assertEquals(1, response.getTranslations().size());
    assertEquals(translation, response.getTranslations().iterator().next());
  }

  @Test
  void returnsEmptyTranslationsWhenNoneProvided() {
    TranslationResult response =
        new TranslationResult("", Collections.emptyList());
    assertEquals(0, response.getTranslations().size());
  }
}
