package io.github.brenoepics.at4j.data.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.brenoepics.at4j.data.DetectedLanguage;
import io.github.brenoepics.at4j.data.response.DetectResponse;
import io.github.brenoepics.at4j.util.rest.RestRequestResult;
import java.util.*;

/**
 * This class represents the parameters for a language detection request. It
 * contains a single field, 'text', which is the text to be analyzed for
 * language detection.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class DetectLanguageParams {

  // The text to be analyzed for language detection
  private final LinkedHashMap<Integer, String> textList = new LinkedHashMap<>();

  /**
   * Constructs a new DetectLanguageParams object with the specified text.
   *
   * @param text the text to be analyzed for language detection
   */
  public DetectLanguageParams(String text) { this.textList.put(1, text); }

  /**
   * Constructs a new DetectLanguageParams object with the specified list of
   * texts.
   *
   * @param textList the list of texts to be analyzed for language detection
   */
  public DetectLanguageParams(List<String> textList) {
    textList.forEach(this::addText);
  }

  /**
   * Adds a text to the list of texts to be analyzed for language detection.
   *
   * @param text the text to be added
   * @return this DetectLanguageParams object
   */
  public DetectLanguageParams addText(String text) {
    this.textList.put(this.textList.size() + 1, text);
    return this;
  }

  /**
   * Returns the text to be analyzed for language detection.
   *
   * @return the text to be analyzed
   */
  public Map<Integer, String> getTexts() { return textList; }

  /**
   * Returns the body of the request as an ArrayNode. If the text is null or
   * empty, it returns null. Otherwise, it creates an ObjectNode with a single
   * field 'Text' containing the text, and adds this ObjectNode to an ArrayNode.
   *
   * @return the body of the request as an ArrayNode, or null if the text is
   *     null or empty
   */
  public ArrayNode getBody() {
    if (getTexts() == null || getTexts().isEmpty())
      return null;

    ArrayNode body = JsonNodeFactory.instance.arrayNode();

    for (String text : getTexts().values()) {
      ObjectNode textNode = JsonNodeFactory.instance.objectNode();
      textNode.put("Text", text);
      body.add(textNode);
    }
    return body;
  }

  /**
   * Handles the response from the API. If the response is null, or the JSON
   * body is null, or the JSON body does not contain a field 'language', it
   * returns an empty Optional. Otherwise, it creates a DetectedLanguage object
   * from the JSON body and returns it as an Optional.
   *
   * @param response the response from the API
   * @return an Optional containing the DetectedLanguage object, or an empty
   *     Optional if the response is null, or the JSON body is null, or the JSON
   *     body does not contain a field 'language'
   */
  public Optional<DetectResponse> handleResponse(RestRequestResult response) {
    if (response.getJsonBody().isNull() || response.getJsonBody().isEmpty())
      return Optional.empty();
    JsonNode jsonBody = response.getJsonBody();
    DetectResponse responses = new DetectResponse();
    getTexts().forEach((index, text) -> {
      DetectedLanguage result =
          getDetectionResult(text, jsonBody.get(index - 1));
      if (result != null)
        responses.addDetectedLanguage(result);
    });

    return Optional.of(responses);
  }

  /**
   * Creates a new instance of this class from a json node.
   *
   * @param jsonNode The json node.
   * @return The new instance.
   */
  private DetectedLanguage getDetectionResult(String baseText,
                                              JsonNode jsonNode) {
    if (jsonNode.isNull() || !jsonNode.isObject())
      return null;

    return DetectedLanguage.ofJSON(baseText, (ObjectNode)jsonNode);
  }
}
