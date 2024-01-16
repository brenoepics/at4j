package io.github.brenoepics.at4j.azure;

/**
 * The base URL of the Azure Translator API. The list of available domains can be found <a
 * href="https://learn.microsoft.com/en-us/azure/ai-services/translator/reference/v3-0-reference#base-urls">here</a>.
 */
public enum BaseURL {

  /** The default domain of the Azure Translator API. Datacenters: Closest available datacenter. */
  GLOBAL("api.cognitive.microsofttranslator.com"),

  /**
   * The domain of the Azure Translator API for the Asia Pacific. Datacenters: Korea South, Japan
   * East, Southeast Asia, and Australia East.
   */
  ASIA_PACIFIC("api-apc.cognitive.microsofttranslator.com"),

  /**
   * The domain of the Azure Translator API for the Europe. Datacenters: North Europe and West
   * Europe.
   */
  EUROPE("api-eur.cognitive.microsofttranslator.com"),

  /**
   * The domain of the Azure Translator API for the United States. Datacenters: East US, South
   * Central US, West Central US, and West US 2
   */
  UNITED_STATES("api-nam.cognitive.microsofttranslator.com");

  private final String url;

  BaseURL(String url) {
    this.url = url;
  }

  /**
   * Gets the URL of the Azure Translator API.
   *
   * @return The URL of the Azure Translator API.
   */
  public String getUrl() {
    return url;
  }
}
