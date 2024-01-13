package com.github.brenoepics.at4j.data.request.optional;

/**
 * Enum ProfanityMarker is used to specify how profanities should be marked in translations. It has
 * two possible values: Asterisk (default) and Tag.
 *
 * <p>For more information on how to handle profanity, refer to the link provided in the see tag.
 *
 * @see <a
 *     href="https://learn.microsoft.com/en-us/azure/ai-services/translator/reference/v3-0-translate#handle-profanity">Profanity
 *     Handling</a>
 */
public enum ProfanityMarker {
  /** Represents the "Asterisk" option for marking profanities. */
  ASTERISK("Asterisk"),

  /** Represents the "Tag" option for marking profanities. */
  TAG("Tag");

  /** The value of the profanity marker. */
  private final String value;

  /**
   * Constructor for the ProfanityMarker enum.
   *
   * @param value The value of the profanity marker.
   */
  ProfanityMarker(String value) {
    this.value = value;
  }

  /**
   * Gets the value of the profanity marker.
   *
   * @return The value of the profanity marker.
   */
  public String getValue() {
    return value;
  }
}
