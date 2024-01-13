package com.github.brenoepics.at4j.data.request.optional;

/**
 * Enum representing the different language scopes that can be used.
 *
 * <p>A language scope is a group of languages that can be returned by the system. The allowed group
 * names are: translation, transliteration, and dictionary.
 *
 * <p>If no scope is given, then all groups are returned, which is equivalent to passing
 * scope=translation, transliteration, dictionary.
 */
public enum LanguageScope {
  /** Represents the "translation" language scope. */
  TRANSLATION("translation"),

  /** Represents the "transliteration" language scope. */
  TRANSLITERATION("transliteration"),

  /** Represents the "dictionary" language scope. */
  DICTIONARY("dictionary");

  /** The string value of the language scope. */
  private final String value;

  /**
   * Constructs a new LanguageScope with the given string value.
   *
   * @param value the string value of the language scope
   */
  LanguageScope(String value) {
    this.value = value;
  }

  /**
   * Returns the string value of the language scope.
   *
   * @return the string value of the language scope
   */
  public String getValue() {
    return value;
  }
}
