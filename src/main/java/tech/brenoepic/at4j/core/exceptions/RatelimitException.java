package tech.brenoepic.at4j.core.exceptions;

import tech.brenoepic.at4j.util.rest.RestRequestInformation;

/** When we encounter a rate limit and run out of retiring. */
public class RatelimitException extends AzureException {

  /**
   * Creates a new instance of this class.
   *
   * @param origin The origin of the exception.
   * @param message The message of the exception.
   * @param request The information about the request.
   */
  public RatelimitException(Exception origin, String message, RestRequestInformation request) {
    super(origin, message, request, null);
  }
}
