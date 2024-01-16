package io.github.brenoepics.at4j.data.request;

import io.github.brenoepics.at4j.data.request.optional.LanguageScope;

import java.util.List;

/** This class represents the parameters for available languages. */
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
    return String.join(",", scope.stream().map(LanguageScope::getValue).toArray(String[]::new));
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
  public String getSourceLanguage() {
    return sourceLanguage;
  }

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
}
