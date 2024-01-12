package com.github.brenoepics.at4j.data;

import com.fasterxml.jackson.databind.node.ObjectNode;

/** Represents a detected language. */
public class DetectedLanguage {

  // The language code of the detected language
  private final String languageCode;

  // The confidence score of the detected language
  private final float score;

  // A boolean indicating if the language is supported for translation
  private boolean isTranslationSupported = false;

  // A boolean indicating if the language is supported for transliteration
  private boolean isTransliterationSupported = false;

  /**
   * Constructor for DetectedLanguage class.
   *
   * @param languageCode The language code of the detected language.
   * @param score The confidence score of the detected language.
   * @param isTranslationSupported A boolean indicating if the language is supported for
   *     translation.
   * @param isTransliterationSupported A boolean indicating if the language is supported for
   *     transliteration.
   */
  public DetectedLanguage(
      String languageCode,
      float score,
      boolean isTranslationSupported,
      boolean isTransliterationSupported) {
    this.languageCode = languageCode;
    this.score = score;
    this.isTranslationSupported = isTranslationSupported;
    this.isTransliterationSupported = isTransliterationSupported;
  }

  /**
   * Constructor for DetectedLanguage class.
   *
   * @param languageCode The language code of the detected language.
   * @param score The confidence score of the detected language.
   */
  public DetectedLanguage(String languageCode, float score) {
    this.languageCode = languageCode;
    this.score = score;
  }

  /**
   * Creates a new instance of this class from a json node.
   *
   * @param jsonNode The json node.
   * @return The new instance.
   */
  public static DetectedLanguage ofJSON(ObjectNode jsonNode) {
    if (jsonNode == null || !jsonNode.has("language") || !jsonNode.has("score")) return null;

    DetectedLanguage detected =
        new DetectedLanguage(jsonNode.get("language").asText(), jsonNode.get("score").floatValue());

    if (jsonNode.has("isTranslationSupported"))
      detected.isTranslationSupported = jsonNode.get("isTranslationSupported").asBoolean();
    if (jsonNode.has("isTransliterationSupported"))
      detected.isTransliterationSupported = jsonNode.get("isTransliterationSupported").asBoolean();
    return detected;
  }

  /**
   * Gets the detected language code.
   *
   * @return The detected language code.
   */
  public String getLanguageCode() {
    return languageCode;
  }

  /**
   * Gets A float value indicating the confidence in the result. The score is <b>between zero and
   * one</b>, and a low score indicates low confidence.
   *
   * @return The confidence score of the detected language.
   */
  public float getScore() {
    return score;
  }

  /**
   * Gets a boolean indicating if the language is supported for translation.
   *
   * @return A boolean indicating if the language is supported for translation.
   */
  public boolean isTranslationSupported() {
    return isTranslationSupported;
  }

  /**
   * Gets a boolean indicating if the language is supported for transliteration.
   *
   * @return A boolean indicating if the language is supported for transliteration.
   */
  public boolean isTransliterationSupported() {
    return isTransliterationSupported;
  }

  /**
   * Returns a string representation of the DetectedLanguage object.
   *
   * @return A string representation of the DetectedLanguage object.
   */
  @Override
  public String toString() {
    return "DetectedLanguage{"
        + "language='"
        + languageCode
        + '\''
        + ", score="
        + score
        + ", isTranslationSupported="
        + isTranslationSupported
        + ", isTransliterationSupported="
        + isTransliterationSupported
        + '}';
  }
}
