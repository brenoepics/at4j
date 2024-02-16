package io.github.brenoepics.at4j.core.exceptions;

import io.github.brenoepics.at4j.util.rest.RestRequestInfo;
import io.github.brenoepics.at4j.util.rest.RestRequestResponseInfo;

/** When we sent a bad request (HTTP response code 400). */
public class UnsupportedMediaTypeException extends AzureException {

  /**
   * Creates a new instance of this class.
   *
   * @param origin The origin of the exception.
   * @param message The message of the exception.
   * @param request The information about the request.
   * @param response The information about the response.
   */
  public UnsupportedMediaTypeException(
      Exception origin,
      String message,
      RestRequestInfo request,
      RestRequestResponseInfo response) {
    super(origin, message, request, response);
  }
}
