package io.github.brenoepics.at4j.util.rest;

import io.github.brenoepics.at4j.AzureApi;
import java.util.Optional;

/** Some information about a rest request response. */
public interface RestRequestResponseInformation {

  /**
   * Gets the azure api instance which created the request.
   *
   * @return The responsible azure api instance.
   */
  AzureApi getApi();

  /**
   * Gets the request which this response answered.
   *
   * @return The request which this response answered.
   */
  RestRequestInformation getRequest();

  /**
   * Gets the response code of the response.
   *
   * @return The response code of the response.
   */
  int getCode();

  /**
   * Gets the body of the response as string.
   *
   * @return The body of the response.
   */
  Optional<String> getBody();
}
