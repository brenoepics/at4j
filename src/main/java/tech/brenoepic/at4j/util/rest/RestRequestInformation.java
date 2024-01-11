package tech.brenoepic.at4j.util.rest;

import com.google.common.collect.Multimap;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import tech.brenoepic.at4j.AzureApi;

/** Some information about a rest request. */
public interface RestRequestInformation {

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
  Multimap<String, String> getQueryParameters();

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
