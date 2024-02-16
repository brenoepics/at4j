package io.github.brenoepics.at4j.data.response;

import io.github.brenoepics.at4j.data.DetectedLanguage;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a response from a language detection operation.
 * It contains a list of detected languages.
 */
public class DetectResponse {

  // List to store the detected languages
  private final List<DetectedLanguage> detectedLanguages;

  /**
   * Constructor for DetectResponse.
   * Initializes the list of detected languages.
   */
  public DetectResponse() { this.detectedLanguages = new ArrayList<>(); }

  /**
   * Adds a detected language to the list.
   * @param detectedLanguage The detected language to be added.
   */
  public void addDetectedLanguage(DetectedLanguage detectedLanguage) {
    detectedLanguages.add(detectedLanguage);
  }

  /**
   * Returns the list of detected languages.
   * @return A list of detected languages.
   */
  public List<DetectedLanguage> getDetectedLanguages() {
    return detectedLanguages;
  }

  /**
   * Returns the first detected language from the list.
   * @return The first detected language.
   */
  public DetectedLanguage getFirst() { return detectedLanguages.get(0); }
}