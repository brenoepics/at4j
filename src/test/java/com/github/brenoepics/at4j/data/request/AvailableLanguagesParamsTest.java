package com.github.brenoepics.at4j.data.request;

import com.github.brenoepics.at4j.data.request.optional.LanguageScope;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AvailableLanguagesParamsTest {

  @Test
  void shouldReturnScopeAsString() {
    AvailableLanguagesParams params = new AvailableLanguagesParams();
    params.setScope(LanguageScope.TRANSLATION, LanguageScope.DICTIONARY);
    String expected = "translation,dictionary";
    assertEquals(expected, params.getScope());
  }

  @Test
  void shouldReturnTranslationStringWhenNoScope() {
    AvailableLanguagesParams params = new AvailableLanguagesParams();
    String expected = "translation";
    assertEquals(expected, params.getScope());
  }

  @Test
  void shouldSetAndReturnSourceLanguage() {
    AvailableLanguagesParams params = new AvailableLanguagesParams();
    String expected = "English";
    params.setSourceLanguage(expected);
    assertEquals(expected, params.getSourceLanguage());
  }

  @Test
  void shouldReturnNullWhenNoSourceLanguageSet() {
    AvailableLanguagesParams params = new AvailableLanguagesParams();
    assertNull(params.getSourceLanguage());
  }
}
