package com.github.brenoepics.at4j.azure.lang;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Language {

  private String code;
  private String name;
  private String nativeName;
  private LanguageDirection direction;

  public Language(String code, String name, String nativeName, LanguageDirection direction) {
    this.code = code;
    this.name = name;
    this.nativeName = nativeName;
    this.direction = direction;
  }

  public static Language ofJSON(String code, ObjectNode json) {
    return new Language(
        code,
        json.get("name").asText(),
        json.get("nativeName").asText(),
        LanguageDirection.fromString(json.get("dir").asText()));
  }

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

  public String getCode() {
    return code;
  }

  public void setCode(String value) {
    this.code = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String value) {
    this.name = value;
  }

  public String getNativeName() {
    return nativeName;
  }

  public void setNativeName(String value) {
    this.nativeName = value;
  }

  public LanguageDirection getDir() {
    return direction;
  }

  public void setDir(LanguageDirection value) {
    this.direction = value;
  }
}
