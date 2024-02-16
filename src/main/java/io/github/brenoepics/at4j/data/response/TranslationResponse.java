package io.github.brenoepics.at4j.data.response;

import io.github.brenoepics.at4j.data.TranslationResult;

import java.util.ArrayList;
import java.util.List;

/** This class represents a response that contains a list of translation results. */
public class TranslationResponse {
  // List to store the translation results
  private final List<TranslationResult> resultList;

  /** Default constructor that initializes an empty list of translation results. */
  public TranslationResponse() {
    resultList = new ArrayList<>();
  }

  /**
   * Constructor that initializes the list of translation results with the provided list.
   *
   * @param results The list of translation results to be stored.
   */
  public TranslationResponse(List<TranslationResult> results) {
    resultList = results;
  }

  /**
   * Method to add a translation result to the list.
   *
   * @param result The translation result to be added.
   */
  public void addResult(TranslationResult result) {
    resultList.add(result);
  }

  /**
   * Method to retrieve the list of translation results.
   *
   * @return The list of translation results.
   */
  public List<TranslationResult> getResultList() {
    return resultList;
  }

  /**
   * Method to retrieve the first translation result from the list.
   *
   * @return The first translation result from the list.
   * @throws IndexOutOfBoundsException If the list is empty.
   */
  public TranslationResult getFirstResult() {
    return resultList.get(0);
  }

  /**
   * Static factory method to create a new TranslationResponse with the provided list of results.
   *
   * @param results The list of translation results to be stored.
   * @return A new TranslationResponse instance.
   */
  public static TranslationResponse of(List<TranslationResult> results) {
    return new TranslationResponse(results);
  }

  /**
   * Static factory method to create a new TranslationResponse with the provided result.
   *
   * @param result The translation result to be stored.
   * @return A new TranslationResponse instance.
   */
  public static TranslationResponse of(TranslationResult result) {
    return new TranslationResponse(List.of(result));
  }
}
