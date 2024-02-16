package io.github.brenoepics.at4j.core.exceptions;

import io.github.brenoepics.at4j.util.rest.RestRequestInfo;
import io.github.brenoepics.at4j.util.rest.RestRequestResponseInfo;

/**
 * Represents a function that accepts four arguments ({@code Exception}, {@code
 * String}, {@code RestRequest<?>} and {@code RestRequestResult}) and produces
 * an azure exception of type {@code T}.
 *
 * @param <T> The type of the azure exception that is produced.
 */
@FunctionalInterface
public interface AzureExceptionInstantiation<T extends AzureException> {

  /**
   * Creates a new instance of the class {@code T}.
   *
   * @param origin The origin of the exception.
   * @param message The message of the exception.
   * @param request The information about the request.
   * @param response The information about the response.
   * @return The new instance.
   */
  T createInstance(Exception origin, String message, RestRequestInfo request,
                   RestRequestResponseInfo response);
}
