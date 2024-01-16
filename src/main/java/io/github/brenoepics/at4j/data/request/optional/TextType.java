package io.github.brenoepics.at4j.data.request.optional;

/**
 * This enum class represents the type of text that is being translated. It can either be plain text
 * or HTML text. <br>
 * The enum has two constants:<br>
 * 1. PLAIN: Represents plain text. This is the default value.<br>
 * 2. HTML: Represents HTML text. Any HTML needs to be a well-formed, complete element. <br>
 * Each constant has a value which can be retrieved using the getValue method.
 */
public enum TextType {
  /** Represents plain text. This is the default value. */
  PLAIN("plain"),

  /** Represents HTML text. Any HTML needs to be a well-formed, complete element. */
  HTML("html");

  // The value of the text type
  private final String value;

  /**
   * Constructor for the enum. Sets the value of the text type.
   *
   * @param value The value of the text type.
   */
  TextType(String value) {
    this.value = value;
  }

  /**
   * Returns the value of the text type.
   *
   * @return The value of the text type.
   */
  public String getValue() {
    return value;
  }
}
