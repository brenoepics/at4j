package tech.brenoepic.at4j.data.request.optional;

/**
 * Specifies how profanities should be marked in translations. Possible values are: Asterisk
 * (default) or Tag. To understand ways to treat profanity.
 *
 * @see <a
 *     href="https://learn.microsoft.com/en-us/azure/ai-services/translator/reference/v3-0-translate#handle-profanity">Profanity
 *     Handling</a>
 */
public enum ProfanityMarker {
  ASTERISK("Asterisk"),
  TAG("Tag");

  private final String value;

  ProfanityMarker(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
