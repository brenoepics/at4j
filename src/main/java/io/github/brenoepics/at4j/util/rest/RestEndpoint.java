package io.github.brenoepics.at4j.util.rest;

import io.github.brenoepics.at4j.azure.BaseURL;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This enum contains all endpoints that we may use. Each endpoint is represented as an enum
 * constant with its associated URL.
 */
public enum RestEndpoint {

  /** The translation endpoint. */
  TRANSLATE("/translate"),
  /** The language list endpoint. */
  LANGUAGES("/languages"),
  /** The dictionary endpoint. */
  DETECT("/detect");

  /** The endpoint url (only including the base, not the BaseURL). */
  private final String endpointUrl;

  /**
   * The position of the major parameter starting with <code>0</code> or <code>-1</code> if no major
   * parameter exists.
   */
  private final int majorParameterPosition;

  /**
   * Constructor for creating an endpoint with a URL and no major parameter.
   *
   * @param endpointUrl The URL of the endpoint.
   */
  RestEndpoint(String endpointUrl) {
    this(endpointUrl, -1);
  }

  /**
   * Constructor for creating an endpoint with a URL and a major parameter.
   *
   * @param endpointUrl The URL of the endpoint.
   * @param majorParameterPosition The position of the major parameter.
   */
  RestEndpoint(String endpointUrl, int majorParameterPosition) {
    this.endpointUrl = endpointUrl;
    this.majorParameterPosition = majorParameterPosition;
  }

  /**
   * Gets the major parameter position of the endpoint. The position starts counting at <code>0
   * </code>!
   *
   * @return An optional which is present if the endpoint has a major parameter.
   */
  public Optional<Integer> getMajorParameterPosition() {
    if (majorParameterPosition >= 0) {
      return Optional.of(majorParameterPosition);
    }
    return Optional.empty();
  }

  /**
   * Gets the endpoint url (only including the base, not the BaseURL).
   *
   * @return The gateway url.
   */
  public String getEndpointUrl() {
    return endpointUrl;
  }


  /**
   * Gets the full {@link URI http url} of the endpoint.
   * Parameters which are "too much" are
   * added to the end.
   *
   * @param baseURL The base url of the endpoint.
   * @return The full http url of the endpoint.
   */
  public URI getHttpUrl(BaseURL baseURL, Map<String, Collection<String>> queryParams) throws URISyntaxException {
    String query = getQuery(queryParams);

    return new URI(
            "https",
            baseURL.getUrl(),
            endpointUrl,
            query,
            null);
  }

  private String getQuery(Map<String, Collection<String>> queryParams) {
    return queryParams.entrySet().stream()
            .map(entry -> entry.getValue().stream()
                    .map(value -> entry.getKey() + "=" + value)
                    .collect(Collectors.joining("&")))
            .collect(Collectors.joining("&"));
  }
}
