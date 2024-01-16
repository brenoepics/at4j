package io.github.brenoepics.at4j.azure.lang;

import com.fasterxml.jackson.databind.node.ObjectNode;

/** Represents a language with its code, name, native name and direction. */
public class Language {

  private String code;
  private String name;
  private String nativeName;
  private LanguageDirection direction;

  /**
   * Constructs a new Language object.
   *
   * @param code the language code
   * @param name the language name
   * @param nativeName the native name of the language
   * @param direction the direction of the language
   */
  public Language(String code, String name, String nativeName, LanguageDirection direction) {
    this.code = code;
    this.name = name;
    this.nativeName = nativeName;
    this.direction = direction;
  }

  /**
   * Constructs a new Language object from a JSON object.
   *
   * @param code the language code
   * @param json the JSON object containing language details
   * @return a new Language object
   */
  public static Language ofJSON(String code, ObjectNode json) {
    return new Language(
        code,
        json.get("name").asText(),
        json.get("nativeName").asText(),
        LanguageDirection.fromString(json.get("dir").asText()));
  }

  /**
   * Returns a string representation of the Language object.
   *
   * @return a string representation of the Language object
   */
  @Override
  public String toString() {
    return "Language{"
        + "code='"
        + code
        + '\''
        + ", name='"
        + name
        + '\''
        + ", nativeName='"
        + nativeName
        + '\''
        + ", direction="
        + direction
        + '}';
  }

  /**
   * Returns the language code.
   *
   * @return the language code
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets the language code.
   *
   * @param value the new language code
   */
  public void setCode(String value) {
    this.code = value;
  }

  /**
   * Returns the language name.
   *
   * @return the language name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the language name.
   *
   * @param value the new language name
   */
  public void setName(String value) {
    this.name = value;
  }

  /**
   * Returns the native name of the language.
   *
   * @return the native name of the language
   */
  public String getNativeName() {
    return nativeName;
  }

  /**
   * Sets the native name of the language.
   *
   * @param value the new native name of the language
   */
  public void setNativeName(String value) {
    this.nativeName = value;
  }

  /**
   * Returns the direction of the language.
   *
   * @return the direction of the language
   */
  public LanguageDirection getDir() {
    return direction;
  }

  /**
   * Sets the direction of the language.
   *
   * @param value the new direction of the language
   */
  public void setDir(LanguageDirection value) {
    this.direction = value;
  }
}
