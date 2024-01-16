package com.github.brenoepics.at4j.util.rest;

import com.github.brenoepics.at4j.core.exceptions.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An enum with all rest request result codes as defined by <a
 * href="https://learn.microsoft.com/en-us/azure/ai-services/translator/reference/v3-0-reference#errors">Azure
 * API Errors</a>. <br>
 * The error code is a 6-digit number combining the <b>3-digit HTTP status code</b> followed by a
 * <b>3-digit number to further categorize the error</b>.
 */
public enum RestRequestResultErrorCode {
  TARGET_LANGUAGE_NOT_VALID(
      400036,
      "The target language is not valid or target language field is missing",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  BODY_REQUEST_NOT_VALID_JSON(
      400074,
      "The body of the request is not valid JSON.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  REQUEST_INPUT_INVALID(
      400000,
      "One of the request inputs isn't valid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  SCOPE_PARAMETER_INVALID(
      400001,
      "The \"scope\" parameter is invalid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  CATEGORY_PARAMETER_INVALID(
      400002,
      "The \"category\" parameter is invalid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  LANGUAGE_SPECIFIER_INVALID(
      400003,
      "A language specifier is missing or invalid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  TARGET_SCRIPT_SPECIFIER_INVALID(
      400004,
      "A target script specifier (\"To script\") is missing or invalid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  INPUT_TEXT_INVALID(
      400005,
      "An input text is missing or invalid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  LANGUAGE_SCRIPT_COMBINATION_INVALID(
      400006,
      "The combination of language and script isn't valid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  SOURCE_SCRIPT_SPECIFIER_INVALID(
      400018,
      "A source script specifier (\"From script\") is missing or invalid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  SPECIFIED_LANGUAGE_UNSUPPORTED(
      400019,
      "One of the specified languages isn't supported.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  INPUT_TEXT_ARRAY_ELEMENT_INVALID(
      400020,
      "One of the elements in the array of input text isn't valid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  API_VERSION_PARAMETER_INVALID(
      400021,
      "The API version parameter is missing or invalid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  SPECIFIED_LANGUAGE_PAIR_INVALID(
      400023,
      "One of the specified language pair isn't valid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  SOURCE_LANGUAGE_INVALID(
      400035,
      "The source language (\"From\" field) isn't valid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  OPTIONS_FIELD_INVALID(
      400042,
      "One of the options specified (\"Options\" field) isn't valid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  CLIENT_TRACE_ID_INVALID(
      400043,
      "The client trace ID (ClientTraceId field or X-ClientTranceId header) is missing or invalid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  INPUT_TEXT_TOO_LONG(
      400050,
      "The input text is too long. View request limits.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  TRANSLATION_PARAMETER_INVALID(
      400064,
      "The \"translation\" parameter is missing or invalid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  TARGET_SCRIPTS_NOT_MATCH_TARGET_LANGUAGES(
      400070,
      "The number of target scripts (ToScript parameter) doesn't match the number of target"
          + " languages (To parameter).",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  TEXT_TYPE_INVALID(
      400071,
      "The value isn't valid for TextType.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  INPUT_TEXT_ARRAY_TOO_MANY_ELEMENTS(
      400072,
      "The array of input text has too many elements.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  SCRIPT_PARAMETER_INVALID(
      400073,
      "The script parameter isn't valid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  LANGUAGE_PAIR_CATEGORY_COMBINATION_INVALID(
      400075,
      "The language pair and category combination isn't valid.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  REQUEST_SIZE_EXCEEDED(
      400077,
      "The maximum request size has been exceeded. View request limits.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  CUSTOM_SYSTEM_NOT_EXIST(
      400079,
      "The custom system requested for translation between from and to language doesn't exist.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  TRANSLITERATION_NOT_SUPPORTED(
      400080,
      "Transliteration isn't supported for the language or script.",
      BadRequestException::new,
      RestRequestHttpResponseCode.BAD_REQUEST),
  REQUEST_NOT_AUTHORIZED(
      401000,
      "The request isn't authorized because credentials are missing or invalid.",
      UnauthorizedException::new,
      RestRequestHttpResponseCode.UNAUTHORIZED),
  WRONG_CREDENTIALS_PROVIDED(
      401015,
      "The credentials provided are for the Speech API. This request requires credentials for the"
          + " Text API. Use a subscription to Translator.",
      UnauthorizedException::new,
      RestRequestHttpResponseCode.UNAUTHORIZED),
  OPERATION_NOT_ALLOWED(
      403000,
      "The operation isn't allowed.",
      ForbiddenException::new,
      RestRequestHttpResponseCode.FORBIDDEN),
  FREE_QUOTA_EXCEEDED(
      403001,
      "The operation isn't allowed because the subscription has exceeded its free quota.",
      ForbiddenException::new,
      RestRequestHttpResponseCode.FORBIDDEN),
  REQUEST_METHOD_NOT_SUPPORTED(
      405000,
      "The request method isn't supported for the requested resource.",
      MethodNotAllowedException::new,
      RestRequestHttpResponseCode.METHOD_NOT_ALLOWED),
  TRANSLATION_SYSTEM_BEING_PREPARED(
      408001,
      "The translation system requested is being prepared. Retry in a few minutes.",
      RequestTimeoutException::new,
      RestRequestHttpResponseCode.REQUEST_TIMEOUT),
  REQUEST_TIMED_OUT(
      408002,
      "Request timed out waiting on incoming stream. The client didn't produce a request within the"
          + " time that the server was prepared to wait. The client may repeat the request without"
          + " modifications at any later time.",
      RequestTimeoutException::new,
      RestRequestHttpResponseCode.REQUEST_TIMEOUT),
  CONTENT_TYPE_HEADER_INVALID(
      415000,
      "The Content-Type header is missing or invalid.",
      UnsupportedMediaTypeException::new,
      RestRequestHttpResponseCode.UNSUPPORTED_MEDIA_TYPE),
  REQUEST_LIMITS_EXCEEDED(
      429000, "The server rejected the request because the client has exceeded request limits."),
  UNEXPECTED_ERROR_OCCURRED(
      500000,
      "An unexpected error occurred. If the error persists, report it with date/time of error,"
          + " request identifier from response header X-RequestId, and client identifier from"
          + " request header X-ClientTraceId.",
      InternalServerErrorException::new,
      RestRequestHttpResponseCode.INTERNAL_SERVER_ERROR),
  SERVICE_TEMPORARILY_UNAVAILABLE(
      503000,
      "Service is temporarily unavailable. Retry. If the error persists, report it with date/time"
          + " of error, request identifier from response header X-RequestId, and client identifier"
          + " from request header X-ClientTraceId.",
      ServiceUnavailableException::new,
      RestRequestHttpResponseCode.SERVICE_UNAVAILABLE);
  ;

  /** A map for retrieving the enum instances by code. */
  private static final Map<Integer, RestRequestResultErrorCode> instanceByCode;

  /** The actual numeric result code. */
  private final int code;

  /** The textual meaning. */
  private final String meaning;

  /**
   * The azure exception instantiate that produces instances to throw for this kind of result code.
   */
  private final AzureExceptionInstantiator<AzureException> azureExceptionInstantiator;

  /** The response code for which the given instantiating should be used. */
  private final RestRequestHttpResponseCode responseCode;

  static {
    instanceByCode =
        Collections.unmodifiableMap(
            Arrays.stream(values())
                .collect(
                    Collectors.toMap(RestRequestResultErrorCode::getCode, Function.identity())));
  }

  /**
   * Creates a new rest request result error code.
   *
   * @param code The actual numeric close code.
   * @param meaning The textual meaning.
   */
  RestRequestResultErrorCode(int code, String meaning) {
    this(code, meaning, null, null);
  }

  /**
   * Creates a new rest request result error code.
   *
   * @param code The actual numeric close code.
   * @param meaning The textual meaning.
   * @param azureExceptionInstantiator The azure exception instantiator that produces instances to
   *     throw for this kind of result code.
   * @param responseCode The response code for which the given instantiator should be used.
   */
  RestRequestResultErrorCode(
      int code,
      String meaning,
      AzureExceptionInstantiator<AzureException> azureExceptionInstantiator,
      RestRequestHttpResponseCode responseCode) {
    this.code = code;
    this.meaning = meaning;
    this.azureExceptionInstantiator = azureExceptionInstantiator;
    this.responseCode = responseCode;
  }

  /**
   * Gets the rest request result error code by actual numeric result code.
   *
   * @param code The actual numeric close code.
   * @param responseCode The response code.
   * @return The web socket close code with the actual numeric result code.
   */
  public static Optional<RestRequestResultErrorCode> fromCode(
      int code, RestRequestHttpResponseCode responseCode) {
    return Optional.ofNullable(instanceByCode.get(code))
        .filter(errorCode -> errorCode.responseCode == responseCode);
  }

  /**
   * Gets the actual numeric result code.
   *
   * @return The actual numeric result code.
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
          Exception origin,
          String message,
          RestRequestInformation request,
          RestRequestResponseInformation response) {
    return Optional.ofNullable(azureExceptionInstantiator)
            .map(instantiate -> instantiate.createInstance(origin, message, request, response))
            .filter(
                    exception ->
                            RestRequestHttpResponseCode.fromAzureExceptionClass(exception.getClass())
                                    .map(RestRequestHttpResponseCode::getCode)
                                    .map(mapCode -> mapCode == response.getCode())
                                    .orElse(true));
  }
}
