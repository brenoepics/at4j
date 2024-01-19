package io.github.brenoepics.at4j.core.exceptions;

import io.github.brenoepics.at4j.util.rest.RestRequestInformation;
import io.github.brenoepics.at4j.util.rest.RestRequestResponseInformation;

/** This exception is thrown when the Azure API returns a 403 Forbidden response. */
public class ForbiddenException extends AzureException {

  /**
   * Creates a new instance of this class.
   *
   * @param origin The origin of the exception.
   * @param message The message of the exception.
   * @param request The information about the request.
   * @param response The information about the response.
   */
  public ForbiddenException(
      Exception origin,
      String message,
      RestRequestInformation request,
      RestRequestResponseInformation response) {
    super(origin, message, request, response);
  }
}