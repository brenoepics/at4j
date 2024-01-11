package com.github.brenoepics.at4j.core.exceptions;

import com.github.brenoepics.at4j.util.rest.RestRequestInformation;
import com.github.brenoepics.at4j.util.rest.RestRequestResponseInformation;

/** When we sent a bad request (HTTP response code 400). */
public class UnauthorizedException extends AzureException {

  /**
   * Creates a new instance of this class.
   *
   * @param origin The origin of the exception.
   * @param message The message of the exception.
   * @param request The information about the request.
   * @param response The information about the response.
   */
  public UnauthorizedException(
      Exception origin,
      String message,
      RestRequestInformation request,
      RestRequestResponseInformation response) {
    super(origin, message, request, response);
  }
}
