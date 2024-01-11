package com.github.brenoepics.at4j.azure.lang;

public enum LanguageDirection {
  LTR,
  RTL;

  public static LanguageDirection fromString(String value) {
    if (value.equalsIgnoreCase("ltr")) {
      return LTR;
    }

    if (value.equalsIgnoreCase("rtl")) {
      return RTL;
    }

    throw new IllegalArgumentException("Invalid value for LanguageDirection: " + value);
  }
}
