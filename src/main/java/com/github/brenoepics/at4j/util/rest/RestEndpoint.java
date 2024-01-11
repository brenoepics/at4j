package com.github.brenoepics.at4j.util.rest;

import com.github.brenoepics.at4j.azure.BaseURL;
import java.util.Optional;
import okhttp3.HttpUrl;

/** This enum contains all endpoints that we may use. */
public enum RestEndpoint {
  TRANSLATE("/translate"),
  LANGUAGES("/languages"),
  DETECT("/detect");

  /** The endpoint url (only including the base, not the BaseURL). */
  private final String endpointUrl;

  /**
   * The position of the major parameter starting with <code>0</code> or <code>-1</code> if no major
   * parameter exists.
   */
  private final int majorParameterPosition;

  RestEndpoint(String endpointUrl) {
    this(endpointUrl, -1);
  }

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
   * Gets the full url of the endpoint. Parameters which are "too much" are added to the end.
   *
   * @param parameters The parameters of the url. E.g., for channel ids.
   * @return The full url of the endpoint.
   */
  public String getFullUrl(BaseURL baseURL, String... parameters) {
    StringBuilder url = new StringBuilder("https://" + baseURL.getUrl() + getEndpointUrl());

    url = new StringBuilder(String.format(url.toString(), (Object[]) parameters));
    int parameterAmount =
        getEndpointUrl().split("%s").length - (getEndpointUrl().endsWith("%s") ? 0 : 1);

    if (parameters.length > parameterAmount) {
      for (int i = parameterAmount; i < parameters.length; i++) {
        url.append("/").append(parameters[i]);
      }
    }
    return url.toString();
  }

  /**
   * Gets the full {@link HttpUrl http url} of the endpoint. Parameters which are "too much" are
   * added to the end.
   *
   * @param parameters The parameters of the url. E.g., for channel ids.
   * @return The full http url of the endpoint.
   */
  public HttpUrl getOkHttpUrl(BaseURL baseURL, String... parameters) {
    return HttpUrl.parse(getFullUrl(baseURL, parameters));
  }
}
