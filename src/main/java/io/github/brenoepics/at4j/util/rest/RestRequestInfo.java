package io.github.brenoepics.at4j.util.rest;

import io.github.brenoepics.at4j.AzureApi;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/** Some information about a rest request. */
public interface RestRequestInfo {

  /**
   * Gets the azure api instance which created the request.
   *
   * @return The responsible azure api instance.
   */
  AzureApi getApi();

  /**
   * Gets the url, the request should be sent to.
   *
   * @return The url, the request should be sent to.
   */
  URL getUrl();

  /**
   * Gets the query parameters of the rest request.
   *
   * @return The query parameters of the rest request.
   */
  Map<String, Collection<String>> getQueryParameters();

  /**
   * Gets the headers of the rest request.
   *
   * @return The headers of the rest request.
   */
  Map<String, String> getHeaders();

  /**
   * Gets the body of the rest request as string.
   *
   * @return The body of the rest request.
   */
  Optional<String> getBody();
}
