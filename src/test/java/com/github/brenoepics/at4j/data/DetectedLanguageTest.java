package com.github.brenoepics.at4j.data;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

class DetectedLanguageTest {

  @Test
  void ofJSON_withValidJson_returnsDetectedLanguage() throws JsonProcessingException {
    String json =
        "{\"language\":\"en\",\"score\":0.9,\"isTranslationSupported\":true,\"isTransliterationSupported\":false}";
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode jsonNode = mapper.convertValue(mapper.readTree(json), ObjectNode.class);

    DetectedLanguage detectedLanguage = DetectedLanguage.ofJSON(jsonNode);

    assertEquals("en", detectedLanguage.getLanguageCode());
    assertEquals(0.9, detectedLanguage.getScore(), 0.0001);
    assertTrue(detectedLanguage.isTranslationSupported());
    assertFalse(detectedLanguage.isTransliterationSupported());
  }

  @Test
  void ofJSON_withMissingFields_returnsDetectedLanguageWithDefaults()
      throws JsonProcessingException {
    String json = "{\"language\":\"en\",\"score\":0.9}";
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode jsonNode = mapper.convertValue(mapper.readTree(json), ObjectNode.class);

    DetectedLanguage detectedLanguage = DetectedLanguage.ofJSON(jsonNode);

    assertEquals("en", detectedLanguage.getLanguageCode());
    assertEquals(0.9, detectedLanguage.getScore(), 0.0001);
    assertFalse(detectedLanguage.isTranslationSupported());
    assertFalse(detectedLanguage.isTransliterationSupported());
  }

  @Test
  void ofJSON_withInvalidJson_returnsNull() throws JsonProcessingException {
    String json = "{\"invalid\":\"json\"}";
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode jsonNode = mapper.convertValue(mapper.readTree(json), ObjectNode.class);

    DetectedLanguage detectedLanguage = DetectedLanguage.ofJSON(jsonNode);

    assertNull(detectedLanguage);
  }

  @Test
  void toString_returnsCorrectFormat() {
    DetectedLanguage detectedLanguage = new DetectedLanguage("en", 0.9f, true, false);

    String expected =
        "DetectedLanguage{language='en', score=0.9, isTranslationSupported=true,"
            + " isTransliterationSupported=false}";
    assertEquals(expected, detectedLanguage.toString());
  }
}
