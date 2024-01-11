package tech.brenoepic.at4j.data.request.optional;

/**
 * Defines whether the text being translated is plain text or HTML text. Any HTML needs to be a
 * well-formed, complete element. <br>
 * Possible values are: <b>plain</b> (default) or <b>html</b>.
 */
public enum TextType {
  PLAIN("plain"),
  HTML("html");

  private final String value;

  TextType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
