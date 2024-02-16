package io.github.brenoepics.at4j.data;

import java.util.Collection;
import java.util.Optional;

/**
 * This class represents a response from a translation service. It contains the
 * detected language of the input and a collection of translations.
 */
public class TranslationResult {

  /**
   * The detected language of the input text. It can be null if the language
   * could not be detected.
   *
   * @see DetectedLanguage
   */
  private DetectedLanguage detectedLanguage = null;

  private final String baseText;

  // A collection of translations for the input text.
  private final Collection<Translation> translations;

  /**
   * Constructs a TranslationResponse with a detected language and a collection
   * of translations.
   *
   * @param detectedLanguage The detected language of the input text.
   * @param translations A collection of translations for the input text.
   */
  public TranslationResult(String baseText, DetectedLanguage detectedLanguage,
                           Collection<Translation> translations) {
    this.baseText = baseText;
    this.detectedLanguage = detectedLanguage;
    this.translations = translations;
  }

  /**
   * Constructs a TranslationResponse with a collection of translations. The
   * detected language is set to null.
   *
   * @param translations A collection of translations for the input text.
   */
  public TranslationResult(String baseText,
                           Collection<Translation> translations) {
    this.baseText = baseText;
    this.translations = translations;
  }

  /**
   * Returns the detected language of the input text.
   *
   * @return The detected language of the input text.
   */
  public DetectedLanguage getDetectedLanguage() { return detectedLanguage; }

  /**
   * Returns the collection of translations for the input text.
   *
   * @return The collection of translations for the input text.
   */
  public Collection<Translation> getTranslations() { return translations; }

  /**
   * Returns the first translation in the collection. If the collection is
   * empty, it returns null.
   *
   * @return The first translation in the collection.
   * @see Translation
   */
  public Optional<Translation> getFirstTranslation() {
    return translations.stream().findFirst();
  }

  /**
   * Returns the base texts that were translated
   *
   * @return the base text
   */
  public String getBaseText() { return baseText; }
}
