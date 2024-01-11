package tech.brenoepic.at4j.data.request.optional;

/**
 * A comma-separated list of names defining the group of languages to return. <br>
 * <b>Allowed group names are: translation, transliteration, and dictionary.</b> <br>
 * If no scope is given, then all groups are returned, which is equivalent to passing
 * scope=translation,transliteration,dictionary.
 */
public enum LanguageScope {
  TRANSLATION("translation"),
  TRANSLITERATION("transliteration"),
  DICTIONARY("dictionary");

  private final String value;

  LanguageScope(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
