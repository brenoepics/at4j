package tech.brenoepic.at4j;

public class AT4J {
  /** The GitHub url of AT4J. */
  public static final String GITHUB_URL = "https://github.com/brenoepics/at4j";

  private static final String DISPLAY_VERSION = "1.0.0";

  /** The user agent used for requests. */
  public static final String USER_AGENT = "AT4J (" + GITHUB_URL + ", v" + DISPLAY_VERSION + ")";

  /**
   * The API version from Azure Translator which we are using. The reference can be found <a
   * href="https://learn.microsoft.com/en-us/azure/ai-services/translator/reference/v3-0-reference">here</a>.
   */
  public static final String AZURE_TRANSLATOR_API_VERSION = "3.0";

  private AT4J() {
    throw new UnsupportedOperationException();
  }
}
