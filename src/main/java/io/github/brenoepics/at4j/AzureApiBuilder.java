package io.github.brenoepics.at4j;

import io.github.brenoepics.at4j.azure.BaseURL;
import io.github.brenoepics.at4j.core.AzureApiImpl;
import io.github.brenoepics.at4j.util.logging.PrivacyProtectionLogger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.time.Duration;

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

  private ProxySelector proxySelector = null;

  private SSLContext sslContext = null;

  private SSLParameters sslParameters = null;

  private Duration connectTimeout = null;

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
   * Sets the proxy selector for the Azure API.
   *
   * @param proxySelector The proxy selector for the Azure API.
   * @return The current instance of AzureApiBuilder for method chaining.
   * @see <a
   *     href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/ProxySelector.html"
   *     >ProxySelector</a>
   */
  public AzureApiBuilder proxy(ProxySelector proxySelector) {
    this.proxySelector = proxySelector;
    return this;
  }

  /**
   * Sets the connect timeout for the Azure API.
   *
   * @param connectTimeout The connect timeout for the Azure API.
   * @return The current instance of AzureApiBuilder for method chaining.
   * @see <a
   *     href="https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.Builder.html#connectTimeout(java.time.Duration)"
   *     >Connection Timeout</a>
   */
  public AzureApiBuilder connectTimeout(Duration connectTimeout) {
    this.connectTimeout = connectTimeout;
    return this;
  }

  /**
   * Sets the SSL context for the Azure API.
   *
   * @param sslContext The SSL context for the Azure API.
   * @return The current instance of AzureApiBuilder for method chaining.
   * @see <a
   *     href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/net/ssl/SSLContext.html"
   *     >SSLContext</a>
   */
  public AzureApiBuilder sslContext(SSLContext sslContext) {
    this.sslContext = sslContext;
    return this;
  }

  /**
   * Sets the SSL parameters for the Azure API.
   *
   * @param sslParameters The SSL parameters for the Azure API.
   * @return The current instance of AzureApiBuilder for method chaining.
   * @see <a
   *     href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/net/ssl/SSLParameters.html"
   *     >SSLParameters</a>
   */
  public AzureApiBuilder sslParameters(SSLParameters sslParameters) {
    this.sslParameters = sslParameters;
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
    HttpClient.Builder httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1);

    if (proxySelector != null) {
      httpClient.proxy(proxySelector);
    }

    if (sslContext != null) {
      httpClient.sslContext(sslContext);
    }

    if (sslParameters != null) {
      httpClient.sslParameters(sslParameters);
    }


    if (connectTimeout != null) {
      httpClient.connectTimeout(connectTimeout);
    }

    return new AzureApiImpl<>(httpClient.build(), baseURL, subscriptionKey, subscriptionRegion);
  }
}
