package com.github.brenoepics.at4j.data.request;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This class represents the parameters for a language detection request.
 * It contains a single field, 'text', which is the text to be analyzed for language detection.
 */
public class DetectLanguageParams {

  // The text to be analyzed for language detection
  private final String text;

  /**
   * Constructs a new DetectLanguageParams object with the specified text.
   *
   * @param text the text to be analyzed for language detection
   */
  public DetectLanguageParams(String text) {
    this.text = text;
  }

  /**
   * Returns the text to be analyzed for language detection.
   *
   * @return the text to be analyzed
   */
  public String getText() {
    return text;
  }

  /**
   * Returns the body of the request as an ArrayNode.
   * If the text is null or empty, it returns null.
   * Otherwise, it creates an ObjectNode with a single field 'Text' containing the text,
   * and adds this ObjectNode to an ArrayNode.
   *
   * @return the body of the request as an ArrayNode, or null if the text is null or empty
   */
  public ArrayNode getBody() {
    if (text == null || text.isEmpty()) return null;

    ObjectNode textNode = JsonNodeFactory.instance.objectNode();
    textNode.put("Text", text);
    return JsonNodeFactory.instance.arrayNode().add(textNode);
  }
}