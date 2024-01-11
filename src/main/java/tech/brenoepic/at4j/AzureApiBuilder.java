package tech.brenoepic.at4j;

import okhttp3.OkHttpClient;
import tech.brenoepic.at4j.azure.BaseURL;
import tech.brenoepic.at4j.core.AzureApiImpl;

/**
 * Builder class for constructing instances of AzureApi.
 *
 * @see AzureApi
 */
public class AzureApiBuilder {

  // The HTTP client used by the Azure API.
  private OkHttpClient httpClient;

  // The base URL for the Azure API.
  private BaseURL baseURL;

  // The subscription key for accessing the Azure API.
  private String subscriptionKey;

  // The subscription region for the Azure API.
  private String subscriptionRegion;

  /** Default constructor initializes the base URL to the global endpoint. */
  public AzureApiBuilder() {
    this.baseURL = BaseURL.GLOBAL;
  }

  /**
   * Sets the HTTP client used by the Azure API.
   *
   * @param httpClient The HTTP client used by the Azure API.
   * @return The current instance of AzureApiBuilder for method chaining.
   * @see <a href="https://square.github.io/okhttp/">OkHttp</a>
   */
  public AzureApiBuilder setOkHttpClient(OkHttpClient httpClient) {
    this.httpClient = httpClient;
    return this;
  }

  /**
   * Sets the base URL for the Azure API.
   *
   * @param baseURL The base URL for the Azure API.
   * @return The current instance of AzureApiBuilder for method chaining.
   * @see BaseURL
   */
  public AzureApiBuilder setBaseURL(BaseURL baseURL) {
    this.baseURL = baseURL;
    return this;
  }

  /**
   * Sets the subscription key for accessing the Azure API.
   *
   * @param subscriptionKey The subscription key for the Azure API.
   * @return The current instance of AzureApiBuilder for method chaining.
   * @see <a
   *     href="https://learn.microsoft.com/en-us/azure/ai-services/translator/create-translator-resource#authentication-keys-and-endpoint-url">Authentication
   *     keys and endpoint URL</a>
   */
  public AzureApiBuilder setSubscriptionKey(String subscriptionKey) {
    this.subscriptionKey = subscriptionKey;
    return this;
  }

  /**
   * Sets the subscription region for the Azure API.
   *
   * @param subscriptionRegion The subscription region for the Azure API.
   * @return The current instance of AzureApiBuilder for method chaining.
   * @see <a href="https://github.com/brenoepics/at4j/main/docs/azure_datacenter_list.json">Azure
   *     Datacenter List</a>
   */
  public AzureApiBuilder setSubscriptionRegion(String subscriptionRegion) {
    this.subscriptionRegion = subscriptionRegion;
    return this;
  }

  /**
   * Builds and returns an instance of AzureApi with the configured parameters.
   *
   * @return An instance of AzureApi with the specified configuration.
   * @see AzureApi
   */
  public AzureApi build() throws IllegalArgumentException {
    if (subscriptionKey == null) {
      throw new IllegalArgumentException("Subscription key cannot be null");
    }

    if (httpClient == null) {
      throw new IllegalArgumentException("HTTP client cannot be null");
    }

    return new AzureApiImpl(httpClient, baseURL, subscriptionKey, subscriptionRegion);
  }
}
