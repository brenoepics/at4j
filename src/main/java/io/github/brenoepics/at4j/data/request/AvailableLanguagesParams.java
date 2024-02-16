package io.github.brenoepics.at4j.data.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.brenoepics.at4j.azure.lang.Language;
import io.github.brenoepics.at4j.data.request.optional.LanguageScope;
import io.github.brenoepics.at4j.util.rest.RestRequestResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/** This class represents the parameters for available languages. */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class AvailableLanguagesParams {
  // List of scopes for the languages
  private List<LanguageScope> scope = List.of(LanguageScope.TRANSLATION);
  // Source language
  private String sourceLanguage;

  /**
   * Returns the scope as a comma-separated string.
   *
   * @return A string representation of the scope.
   */
  public String getScope() {
    return String.join(
        ",",
        scope.stream().map(LanguageScope::getValue).toArray(String[] ::new));
  }

  /**
   * Sets the scope of the languages.
   *
   * @param scopes The scopes to set.
   * @return The current instance of AvailableLanguagesParams.
   */
  public AvailableLanguagesParams setScope(LanguageScope... scopes) {
    this.scope = List.of(scopes);
    return this;
  }

  /**
   * Returns the source language.
   *
   * @return The source language.
   */
  public String getSourceLanguage() { return sourceLanguage; }

  /**
   * Sets the source language.
   *
   * @param sourceLanguage The source language to set.
   * @return The current instance of AvailableLanguagesParams.
   */
  public AvailableLanguagesParams setSourceLanguage(String sourceLanguage) {
    this.sourceLanguage = sourceLanguage;
    return this;
  }

  /**
   * Method to handle the response from the API.
   *
   * @param response The response to handle.
   * @return An optional containing the collection of languages if the response
   *     was successful.
   */
  public Optional<Collection<Language>>
  handleResponse(RestRequestResult response) {
    if (response.getJsonBody().isNull() ||
        !response.getJsonBody().has("translation"))
      return Optional.empty();

    Collection<Language> languages = new ArrayList<>();
    JsonNode jsonNode = response.getJsonBody().get("translation");
    jsonNode.fieldNames().forEachRemaining(key -> {
      Language language = Language.ofJSON(key, (ObjectNode)jsonNode.get(key));
      languages.add(language);
    });

    return Optional.of(languages);
  }
}
