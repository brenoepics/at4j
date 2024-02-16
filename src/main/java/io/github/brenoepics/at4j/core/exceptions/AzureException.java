package io.github.brenoepics.at4j.core.exceptions;

import io.github.brenoepics.at4j.util.rest.RestRequestInfo;
import io.github.brenoepics.at4j.util.rest.RestRequestResponseInfo;
import java.util.Optional;

/** This exception is always thrown whenever a request to azure failed. */
public class AzureException extends Exception {
  private static final long serialVersionUID = 1906192041028451817L;

  /**
   * The request. May be <code>null</code> if the exception was thrown before creating a request.
   */
  private final transient RestRequestInfo request;

  /**
   * The rest request result. May be <code>null</code> if the exception was thrown before sending a
   * request.
   */
  private final transient RestRequestResponseInfo response;
  /**
   * Creates a new instance of this class.
   *
   * @param origin The origin of the exception.
   * @param message The message of the exception.
   * @param request The information about the request.
   * @param response The information about the response.
   */
  public AzureException(
      Exception origin,
      String message,
      RestRequestInfo request,
      RestRequestResponseInfo response) {
    super(message, origin);
    this.request = request;
    this.response = response;
  }

  /**
   * Gets information about the request which caused the exception. May be <code>null</code> if the
   * exception was thrown before creating a request.
   *
   * @return Information about the request which caused the exception.
   */
  public Optional<RestRequestInfo> getRequest() {
    return Optional.ofNullable(request);
  }

  /**
   * Gets information about the response which caused the exception. May not be present if the
   * exception was thrown before sending a request.
   *
   * @return Information about the response which caused the exception.
   */
  public Optional<RestRequestResponseInfo> getResponse() {
    return Optional.ofNullable(response);
  }
}
