package com.github.brenoepics.at4j.data;

import com.fasterxml.jackson.databind.node.ObjectNode;

/** A translation of a text. This class is immutable. */
public class Translation {

  private final String languageCode;
  private final String text;

  public Translation(String languageCode, String value) {
    this.languageCode = languageCode;
    this.text = value;
  }

  public static Translation from(String languageCode, String value) {
    return new Translation(languageCode, value);
  }

  public static Translation ofJSON(ObjectNode node) {
    if (node == null || !node.has("text") || !node.has("to")) return null;

    return new Translation(node.get("to").asText(), node.get("text").asText());
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return "Translation{" + "key='" + languageCode + '\'' + ", value='" + text + '\'' + '}';
  }
}
