package com.github.brenoepics.at4j.data.response;

import com.github.brenoepics.at4j.data.DetectedLanguage;
import com.github.brenoepics.at4j.data.Translation;
import java.util.Collection;
import javax.annotation.Nullable;

/**
 * This class represents a response from a translation service.
 * It contains the detected language of the input and a collection of translations.
 */
public class TranslationResponse {

  // The detected language of the input text. It can be null if the language could not be detected.
  @Nullable private DetectedLanguage detectedLanguage = null;

  // A collection of translations for the input text.
  private final Collection<Translation> translations;

  /**
   * Constructs a TranslationResponse with a detected language and a collection of translations.
   *
   * @param detectedLanguage The detected language of the input text.
   * @param translations A collection of translations for the input text.
   */
  public TranslationResponse(
          @Nullable DetectedLanguage detectedLanguage, Collection<Translation> translations) {
    this.detectedLanguage = detectedLanguage;
    this.translations = translations;
  }

  /**
   * Constructs a TranslationResponse with a collection of translations.
   * The detected language is set to null.
   *
   * @param translations A collection of translations for the input text.
   */
  public TranslationResponse(Collection<Translation> translations) {
    this.translations = translations;
  }

  /**
   * Sets the detected language of the input text.
   *
   * @param detectedLanguage The detected language of the input text.
   */
  public void setDetectedLanguage(@Nullable DetectedLanguage detectedLanguage) {
    this.detectedLanguage = detectedLanguage;
  }

  /**
   * Returns the detected language of the input text.
   *
   * @return The detected language of the input text.
   */
  @Nullable
  public DetectedLanguage getDetectedLanguage() {
    return detectedLanguage;
  }

  /**
   * Returns the collection of translations for the input text.
   *
   * @return The collection of translations for the input text.
   */
  public Collection<Translation> getTranslations() {
    return translations;
  }
}