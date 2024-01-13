package com.github.brenoepics.at4j.azure.lang;

/**
 * Enum representing the direction of a language. It can be either LTR (Left-To-Right) or RTL
 * (Right-To-Left).
 */
public enum LanguageDirection {
  // Represents a Left-To-Right direction
  LTR,
  // Represents a Right-To-Left direction
  RTL;

  /**
   * Converts a string value to its corresponding LanguageDirection.
   *
   * @param value The string representation of the language direction. It should be either "ltr" or
   *     "rtl".
   * @return The LanguageDirection corresponding to the provided string.
   * @throws IllegalArgumentException If the provided value is not "ltr" or "rtl".
   */
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
