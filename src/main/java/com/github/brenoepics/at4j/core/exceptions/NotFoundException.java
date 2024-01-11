package com.github.brenoepics.at4j.core.exceptions;

import com.github.brenoepics.at4j.util.rest.RestRequestResponseInformation;
import com.github.brenoepics.at4j.util.rest.RestRequestInformation;

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
      RestRequestInformation request,
      RestRequestResponseInformation response) {
    super(origin, message, request, response);
  }
}
