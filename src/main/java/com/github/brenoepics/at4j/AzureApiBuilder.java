package com.github.brenoepics.at4j;

import com.github.brenoepics.at4j.azure.BaseURL;
import com.github.brenoepics.at4j.core.AzureApiImpl;
import com.github.brenoepics.at4j.util.logging.LoggerUtil;
import com.github.brenoepics.at4j.util.logging.PrivacyProtectionLogger;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Builder class for constructing instances of AzureApi.
 *
 * @see AzureApi
 */
public class AzureApiBuilder {

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
   * Sets the base URL for the Azure API.
   *
   * @param baseURL The base URL for the Azure API.
   * @return The current instance of AzureApiBuilder for method chaining.
   * @see BaseURL
   */
  public AzureApiBuilder baseURL(BaseURL baseURL) {
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
  public AzureApiBuilder setKey(String subscriptionKey) {
    this.subscriptionKey = subscriptionKey;
    PrivacyProtectionLogger.addPrivateData(subscriptionKey);
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
  public AzureApiBuilder region(String subscriptionRegion) {
    this.subscriptionRegion = subscriptionRegion;
    return this;
  }

  /**
   * Builds and returns an instance of AzureApi with the configured parameters.
   *
   * @return An instance of AzureApi with the specified configuration.
   * @throws NullPointerException If the subscription key is null.
   * @see AzureApi
   */
  public AzureApi build() {
    if (this.subscriptionKey == null) {
      throw new NullPointerException("Subscription key cannot be null");
    }

    // The HTTP client used by the Azure API.
    OkHttpClient httpClient =
        new OkHttpClient.Builder()
            .addInterceptor(
                chain ->
                    chain.proceed(
                        chain
                            .request()
                            .newBuilder()
                            .addHeader("User-Agent", AT4J.USER_AGENT)
                            .build()))
            .addInterceptor(
                new HttpLoggingInterceptor(LoggerUtil.getLogger(OkHttpClient.class)::trace)
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();
    return new AzureApiImpl(httpClient, baseURL, subscriptionKey, subscriptionRegion);
  }
}
