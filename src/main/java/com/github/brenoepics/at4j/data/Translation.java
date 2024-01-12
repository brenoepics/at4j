package com.github.brenoepics.at4j.data;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * A translation of a text. This class is immutable.
 */
public class Translation {

  // The language code for the translation
  private final String languageCode;

  // The translated text
  private final String text;

  /**
   * Constructs a new Translation object.
   *
   * @param languageCode the language code for the translation
   * @param value the translated text
   */
  public Translation(String languageCode, String value) {
    this.languageCode = languageCode;
    this.text = value;
  }

  /**
   * Factory method to create a new Translation object.
   *
   * @param languageCode the language code for the translation
   * @param value the translated text
   * @return a new Translation object
   */
  public static Translation from(String languageCode, String value) {
    return new Translation(languageCode, value);
  }

  /**
   * Factory method to create a new Translation object from a JSON node.
   *
   * @param node the JSON node containing the translation data
   * @return a new Translation object, or null if the node is invalid
   */
  public static Translation ofJSON(ObjectNode node) {
    if (node == null || !node.has("text") || !node.has("to")) return null;

    return new Translation(node.get("to").asText(), node.get("text").asText());
  }

  /**
   * Returns the language code for the translation.
   *
   * @return the language code
   */
  public String getLanguageCode() {
    return languageCode;
  }

  /**
   * Returns the translated text.
   *
   * @return the translated text
   */
  public String getText() {
    return text;
  }

  /**
   * Returns a string representation of the Translation object.
   *
   * @return a string representation of the Translation object
   */
  @Override
  public String toString() {
    return "Translation{" + "key='" + languageCode + '\'' + ", value='" + text + '\'' + '}';
  }
}