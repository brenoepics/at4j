package io.github.brenoepics.at4j.data.request.optional;

/**
 * Enum ProfanityAction is used to specify how profanities should be treated in translations. It has
 * three possible values: MARKED, DELETED, and NO_ACTION.
 *
 * <p>MARKED: Profanities will be marked in the translation. DELETED: Profanities will be deleted
 * from the translation. NO_ACTION: Profanities will not be handled, they will remain as is in the
 * translation.
 *
 * <p>Each enum value has an associated string value that can be retrieved using the getValue
 * method.
 *
 * @see <a
 *     href="https://learn.microsoft.com/en-us/azure/ai-services/translator/reference/v3-0-translate#handle-profanity">Handle
 *     profanity</a>
 */
public enum ProfanityAction {
  /** Represents the "Marked" action for profanities. */
  MARKED("Marked"),

  /** Represents the "Deleted" action for profanities. */
  DELETED("Deleted"),

  /** Represents the "NoAction" action for profanities. */
  NO_ACTION("NoAction");

  /** The string value associated with the enum value. */
  private final String value;

  /**
   * Constructor for the enum values.
   *
   * @param value The string value associated with the enum value.
   */
  ProfanityAction(String value) {
    this.value = value;
  }

  /**
   * Retrieves the string value associated with the enum value.
   *
   * @return The string value associated with the enum value.
   */
  public String getValue() {
    return value;
  }
}
