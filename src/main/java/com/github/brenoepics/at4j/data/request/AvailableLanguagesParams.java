package com.github.brenoepics.at4j.data.request;

import com.github.brenoepics.at4j.data.request.optional.LanguageScope;
import java.util.Collections;
import java.util.List;

public class AvailableLanguagesParams {
  private List<LanguageScope> scope = List.of(LanguageScope.TRANSLATION);
  private String sourceLanguage;

  public String getScope() {
    return String.join(",", scope.stream().map(LanguageScope::getValue).toArray(String[]::new));
  }

  public AvailableLanguagesParams setScope(LanguageScope... scopes) {
    this.scope.clear();
    Collections.addAll(this.scope, scopes);
    return this;
  }

  public String getSourceLanguage() {
    return sourceLanguage;
  }

  public AvailableLanguagesParams setSourceLanguage(String sourceLanguage) {
    this.sourceLanguage = sourceLanguage;
    return this;
  }
}
