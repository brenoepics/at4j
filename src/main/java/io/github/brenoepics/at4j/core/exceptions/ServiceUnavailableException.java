package io.github.brenoepics.at4j.core.exceptions;

import io.github.brenoepics.at4j.util.rest.RestRequestInfo;
import io.github.brenoepics.at4j.util.rest.RestRequestResponseInfo;

/** This exception is thrown when the Azure API returns a 408 Request Timeout response. */
public class ServiceUnavailableException extends AzureException {

  /**
   * Creates a new instance of this class.
   *
   * @param origin The origin of the exception.
   * @param message The message of the exception.
   * @param request The information about the request.
   * @param response The information about the response.
   */
  public ServiceUnavailableException(
      Exception origin,
      String message,
      RestRequestInfo request,
      RestRequestResponseInfo response) {
    super(origin, message, request, response);
  }
}
