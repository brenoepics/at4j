package io.github.brenoepics.at4j.util.rest;

import io.github.brenoepics.at4j.core.exceptions.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An enum with all rest request result codes as defined by <a
 * href="https://learn.microsoft.com/en-us/azure/ai-services/translator/reference/v3-0-reference#errors">Azure</a>.
 */
public enum RestRequestHttpResponseCode {

  /** The request completed successfully. */
  OK(200, "The request completed successfully"),

  /** The entity was created successfully. */
  CREATED(201, "The entity was created successfully"),

  /** The request completed successfully but returned no content. */
  NO_CONTENT(204, "The request completed successfully but returned no content"),

  /** The entity was not modified (no action was taken). */
  NOT_MODIFIED(304, "The entity was not modified (no action was taken)"),

  /** The request was improperly formatted, or the server couldn't understand it. */
  BAD_REQUEST(
      400,
      "The request was improperly formatted, or the server couldn't understand it",
      BadRequestException::new,
      BadRequestException.class),

  /** The Authorization header was missing or invalid. */
  UNAUTHORIZED(
      401,
      "The Authorization header was missing or invalid",
      UnauthorizedException::new,
      UnauthorizedException.class),

  /** The Authorization token you passed did not have permission to the resource. */
  FORBIDDEN(
      403,
      "The Authorization token you passed did not have permission to the resource",
      ForbiddenException::new,
      ForbiddenException.class),

  /** The resource at the location specified doesn't exist. */
  NOT_FOUND(
      404,
      "The resource at the location specified doesn't exist",
      NotFoundException::new,
      NotFoundException.class),

  /** The HTTP method used is not valid for the location specified. */
  METHOD_NOT_ALLOWED(
      405,
      "The HTTP method used is not valid for the location specified",
      MethodNotAllowedException::new,
      MethodNotAllowedException.class),

  /** The request timed out, you can retry it again later with the same parameters. */
  REQUEST_TIMEOUT(
      408,
      "The request timed out, you can retry it again later",
      RequestTimeoutException::new,
      RequestTimeoutException.class),

  /** The request entity has a media type which the server or resource does not support. */
  UNSUPPORTED_MEDIA_TYPE(
      415,
      "The request entity has a media type which the server or resource does not support",
      UnsupportedMediaTypeException::new,
      UnsupportedMediaTypeException.class),
  /** You've made too many requests. */
  TOO_MANY_REQUESTS(429, "You've made too many requests"),

  /** There was not a gateway available to process your request. Wait a bit and retry. */
  GATEWAY_UNAVAILABLE(
      502, "There was not a gateway available to process your request. Wait a bit and retry"),

  /** There was an internal server error while processing your request. */
  INTERNAL_SERVER_ERROR(
      500,
      "There was an internal server error while processing your request",
      InternalServerErrorException::new,
      InternalServerErrorException.class),

  /** There was a service unavailable while processing your request. */
  SERVICE_UNAVAILABLE(
      503,
      "There was a service unavailable while processing your request",
      ServiceUnavailableException::new,
      ServiceUnavailableException.class);

  /** A map for retrieving the enum instances by code. */
  private static final Map<Integer, RestRequestHttpResponseCode> instanceByCode;

  /** A map for retrieving the enum instances by exception class. */
  private static final Map<Class<? extends AzureException>, RestRequestHttpResponseCode>
      instanceByExceptionClass;

  /** The actual numeric result code. */
  private final int code;

  /** The textual meaning. */
  private final String meaning;

  /**
   * The azure exception instantiated that produces instances to throw for this kind of result code.
   */
  private final AzureExceptionInstantiation<?> azureExceptionInstantiation;

  /** The azure exception class to throw for this kind of result code. */
  private final Class<? extends AzureException> azureExceptionClass;

  static {
    instanceByCode =
        Collections.unmodifiableMap(
            Arrays.stream(values())
                .collect(
                    Collectors.toMap(RestRequestHttpResponseCode::getCode, Function.identity())));

    instanceByExceptionClass =
        Collections.unmodifiableMap(
            Arrays.stream(values())
                .filter(
                    restRequestHttpResponseCode ->
                        restRequestHttpResponseCode.getAzureExceptionClass().isPresent())
                .collect(
                    Collectors.toMap(
                        restRequestHttpResponseCode ->
                            restRequestHttpResponseCode.getAzureExceptionClass().orElse(null),
                        Function.identity())));
  }

  /**
   * Creates a new rest request http response code.
   *
   * @param code The actual numeric response code.
   * @param meaning The textual meaning.
   */
  RestRequestHttpResponseCode(int code, String meaning) {
    this(code, meaning, null, null);
  }

  /**
   * Creates a new rest request http response code.
   *
   * @param code The actual numeric response code.
   * @param meaning The textual meaning.
   * @param exceptionInstantiator The azure exception instantiator that produces instances to throw
   *     for this kind of result code.
   */
  <T extends AzureException> RestRequestHttpResponseCode(
      int code,
      String meaning,
      AzureExceptionInstantiation<T> exceptionInstantiator,
      Class<T> azureExceptionClass) {
    this.code = code;
    this.meaning = meaning;
    this.azureExceptionInstantiation = exceptionInstantiator;
    this.azureExceptionClass = azureExceptionClass;

    if ((exceptionInstantiator == null) && (azureExceptionClass != null)
        || (exceptionInstantiator != null) && (azureExceptionClass == null)) {

      throw new IllegalArgumentException(
          "exceptionInstantiator and azureExceptionClass do not match");
    }
  }

  /**
   * Gets the rest request http response code by actual numeric response code.
   *
   * @param code The actual numeric response code.
   * @return The rest request http response code with the actual numeric response code.
   */
  public static Optional<RestRequestHttpResponseCode> fromCode(int code) {
    return Optional.ofNullable(instanceByCode.get(code));
  }

  /**
   * Gets the rest request http response code by azure exception class. If no entry for the given
   * class is found, the parents are checked until match is found or {@code AzureException} is
   * reached.
   *
   * @param azureExceptionClass The azure exception class.
   * @return The rest request http response code with the azure exception class.
   */
  @SuppressWarnings("unchecked")
  public static Optional<RestRequestHttpResponseCode> fromAzureExceptionClass(
      Class<? extends AzureException> azureExceptionClass) {
    Class<? extends AzureException> clazz = azureExceptionClass;
    while (instanceByExceptionClass.get(clazz) == null) {
      if (clazz == AzureException.class) {
        return Optional.empty();
      }
      clazz = (Class<? extends AzureException>) clazz.getSuperclass();
    }
    return Optional.of(instanceByExceptionClass.get(clazz));
  }

  /**
   * Gets the actual numeric response code.
   *
   * @return The actual numeric response code.
   */
  public int getCode() {
    return code;
  }

  /**
   * Gets the textual meaning.
   *
   * @return The textual meaning.
   */
  public String getMeaning() {
    return meaning;
  }

  /**
   * Gets the azure exception to throw for this kind of result code.
   *
   * @param origin The origin of the exception.
   * @param message The message of the exception.
   * @param request The information about the request.
   * @param response The information about the response.
   * @return The azure exception to throw for this kind of result code.
   */
  public Optional<AzureException> getAzureException(
          Exception origin, String message, RestRequestInfo request, RestRequestResponseInfo response) {
    return Optional.ofNullable(azureExceptionInstantiation)
            .map(instantiation -> instantiation.createInstance(origin, message, request, response));
  }

  /**
   * Gets the azure exception class to throw for this kind of result code.
   *
   * @return The azure exception class to throw for this kind of result code.
   */
  public Optional<Class<? extends AzureException>> getAzureExceptionClass() {
    return Optional.ofNullable(azureExceptionClass);
  }
}
