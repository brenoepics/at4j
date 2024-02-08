package io.github.brenoepics.at4j.core.exceptions;

import io.github.brenoepics.at4j.util.rest.RestRequestInfo;
import io.github.brenoepics.at4j.util.rest.RestRequestResponseInfo;

/** When something was not found (HTTP response code 404). */
public class NotFoundException extends BadRequestException {

  /**
   * Creates a new instance of this class.
   *
   * @param origin The origin of the exception.
   * @param message The message of the exception.
   * @param request The information about the request.
   * @param response The information about the response.
   */
  public NotFoundException(
      Exception origin,
      String message,
      RestRequestInfo request,
      RestRequestResponseInfo response) {
    super(origin, message, request, response);
  }
}
