package io.github.brenoepics.at4j.util.rest;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.brenoepics.at4j.AT4J;
import io.github.brenoepics.at4j.AzureApi;
import io.github.brenoepics.at4j.core.exceptions.AzureException;
import io.github.brenoepics.at4j.util.logging.LoggerUtil;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/** This class is used to wrap a rest request. */
public class RestRequest {

  /** The (logger) of this class. */
  private static final Logger logger = LoggerUtil.getLogger(RestRequest.class);

  private final AzureApi api;
  private final RestMethod method;
  private final RestEndpoint endpoint;

  private volatile boolean includeAuthorizationHeader = true;
  private final Map<String, Collection<String>> queryParameters = new HashMap<>();
  private final Map<String, String> headers = new HashMap<>();
  private volatile String body = null;

  private final CompletableFuture<RestRequestResult> result = new CompletableFuture<>();

  /** The origin of the rest request. */
  private final Exception origin;

  public static final String ERROR_FIELD = "error";

  /**
   * Creates a new instance of this class.
   *
   * @param api The api which will be used to execute the request.
   * @param method The http method of the request.
   * @param endpoint The endpoint to which the request should be sent.
   */
  public RestRequest(AzureApi api, RestMethod method, RestEndpoint endpoint) {
    this.api = api;
    this.method = method;
    this.endpoint = endpoint;
    addQueryParameter("api-version", AT4J.AZURE_TRANSLATOR_API_VERSION);

    this.origin = new Exception("origin of RestRequest call");
  }

  /**
   * Creates a new instance of this class.
   *
   * @param api The api which will be used to execute the request.
   * @param method The http method of the request.
   * @param endpoint The endpoint to which the request should be sent.
   * @param includeAuthorizationHeader Whether the authorization header should be included or not.
   */
  public RestRequest(
      AzureApi api, RestMethod method, RestEndpoint endpoint, boolean includeAuthorizationHeader) {
    this.api = api;
    this.method = method;
    this.endpoint = endpoint;
    addQueryParameter("api-version", AT4J.AZURE_TRANSLATOR_API_VERSION);
    this.includeAuthorizationHeader = includeAuthorizationHeader;

    this.origin = new Exception("origin of RestRequest call");
  }

  /**
   * Gets the api which is used for this request.
   *
   * @return The api which is used for this request.
   */
  public AzureApi getApi() {
    return api;
  }

  /**
   * Gets the method of this request.
   *
   * @return The method of this request.
   */
  public RestMethod getMethod() {
    return method;
  }

  /**
   * Gets the endpoint of this request.
   *
   * @return The endpoint of this request.
   */
  public RestEndpoint getEndpoint() {
    return endpoint;
  }

  /**
   * Gets the query parameters of this request.
   *
   * @return The query parameters of this request.
   */
  public Map<String, Collection<String>> getQueryParameters() {
    return queryParameters;
  }

  /**
   * Gets the body of this request.
   *
   * @return The body of this request.
   */
  public Optional<String> getBody() {
    return Optional.ofNullable(body);
  }

  /**
   * Gets the origin of the rest request.
   *
   * @return The origin of the rest request.
   */
  public Exception getOrigin() {
    return origin;
  }

  /**
   * Adds a query parameter to the url.
   *
   * @param key The key of the parameter.
   * @param value The value of the parameter.
   */
  public void addQueryParameter(String key, String value) {
    queryParameters.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
  }

  /**
   * Adds multiple query parameters to the url.
   *
   * @param parameters The parameters to add.
   */
  public void addQueryParameters(Map<String, String> parameters) {
    parameters.forEach(this::addQueryParameter);
  }

  /**
   * Adds a header to the request.
   *
   * @param name The name of the header.
   * @param value The value of the header.
   */
  public void addHeader(String name, String value) {
    headers.put(name, value);
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  /**
   * Sets the body of the request.
   *
   * @param body The body of the request.
   * @return The current instance to chain call methods.
   */
  public RestRequest setBody(JsonNode body) {
    return setBody(body.toString());
  }

  /**
   * Sets the body of the request.
   *
   * @param body The body of the request.
   * @return The current instance to chain call methods.
   */
  public RestRequest setBody(String body) {
    this.body = body;
    return this;
  }

  /**
   * Sets if an authorization header should be included in this request.
   *
   * @param includeAuthorizationHeader Whether the authorization header should be included or not.
   */
  public void includeAuthorizationHeader(boolean includeAuthorizationHeader) {
    this.includeAuthorizationHeader = includeAuthorizationHeader;
  }

  /**
   * Returns if an authorization header should be included in this request.
   *
   * @return Whether the authorization header should be included or not.
   */
  public boolean isIncludeAuthorizationHeader() {
    return includeAuthorizationHeader;
  }

  /**
   * Executes the request. This will automatically retry if we hit a ratelimit.
   *
   * @param function A function which processes the rest response to the requested object.
   * @return A future which will contain the output of the function.
   */
  public <T> CompletableFuture<T> execute(Function<RestRequestResult, T> function) {
    api.getRatelimitManager().queueRequest(this);
    CompletableFuture<T> future = new CompletableFuture<>();
    result.whenComplete(
        (requestResult, throwable) -> {
          if (throwable != null) {
            future.completeExceptionally(throwable);
            return;
          }
          try {
            future.complete(function.apply(requestResult));
          } catch (Exception t) {
            future.completeExceptionally(t);
          }
        });
    return future;
  }

  /**
   * Gets the result of this request. This will not start executing, return the result!
   *
   * @return Gets the result of this request.
   */
  public CompletableFuture<RestRequestResult> getResult() {
    return result;
  }

  /**
   * Gets the information for this rest request.
   *
   * @return The information for this rest request.
   * @throws AssertionError Thrown if the url is malformed.
   */
  public RestRequestInfo asRestRequestInformation() {
    try {

      return new RestRequestInfoImpl(
          api,
          endpoint.getHttpUrl(api.getBaseURL(), queryParameters).toURL(),
          queryParameters,
          headers,
          body);
    } catch (URISyntaxException | MalformedURLException e) {
      throw new AssertionError(e);
    }
  }

  /**
   * Executes the request blocking.
   *
   * @return The result of the request.
   * @throws AzureException Thrown in case of an error while requesting azure.
   * @throws IOException Thrown if an error occurs while reading the response.
   */
  public RestRequestResult executeBlocking()
      throws AzureException, IOException, URISyntaxException {
    URI fullUrl = endpoint.getHttpUrl(api.getBaseURL(), queryParameters);
    HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(fullUrl);
    request(requestBuilder);

    if (includeAuthorizationHeader) {
      requestBuilder.setHeader("Ocp-Apim-Subscription-Key", api.getSubscriptionKey());
      api.getSubscriptionRegion()
          .ifPresent(region -> requestBuilder.setHeader("Ocp-Apim-Subscription-Region", region));
    }

    headers.forEach(requestBuilder::setHeader);

    logger.debug(
        "Trying to send {} request to {}{}",
        method.name(),
        requestBuilder,
        body != null ? " with body " + body : "");

    CompletableFuture<HttpResponse<String>> response =
        getApi()
            .getHttpClient()
            .sendAsync(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
    RestRequestResult responseResult = handleResponse(fullUrl, response.join());
    result.complete(responseResult);
    return responseResult;
  }

  private RestRequestResult handleResponse(URI fullUrl, HttpResponse<String> response)
      throws IOException, AzureException {
    RestRequestResult requestResult = new RestRequestResult(this, response);
    String bodyString = requestResult.getStringBody().orElse("empty");
    logger.debug(
        "Sent {} request to {} and received status code {} with body {}",
        method.name(),
        fullUrl.toURL(),
        response.statusCode(),
        bodyString);

    if (response.statusCode() >= 300 || response.statusCode() < 200) {
      return handleError(response.statusCode(), requestResult);
    }

    return requestResult;
  }

  private RestRequestResult handleError(int resultCode, RestRequestResult result)
      throws AzureException {
    RestRequestInfo requestInformation = asRestRequestInformation();
    RestRequestResponseInfo responseInformation =
        new RestRequestResponseInfoImpl(requestInformation, result);
    Optional<RestRequestHttpResponseCode> responseCodeOptional =
        RestRequestHttpResponseCode.fromCode(resultCode);

    if (responseCodeOptional.isEmpty()) {
      throw new AzureException(
          origin,
          "Received a response from Azure with a not found resultCode!",
          requestInformation,
          responseInformation);
    }

    RestRequestHttpResponseCode responseCode = responseCodeOptional.get();

    String code = responseCode.getCode() + "000";
    String error = null;
    if (!result.getJsonBody().isNull() && result.getJsonBody().has(ERROR_FIELD)) {
      code = result.getJsonBody().get(ERROR_FIELD).get("code").asText();
      error = result.getJsonBody().get(ERROR_FIELD).get("message").asText();
    }

    Optional<? extends AzureException> azureException =
        handleKnownError(responseCode, code, error, requestInformation, responseInformation);

    if (azureException.isPresent()) {
      throw azureException.get();
    }

    if (resultCode == 429) {
      return result;
    }

    String message = "Received a " + resultCode + " response from Azure, Meaning: %MEANING%";

    azureException =
        responseCode.getAzureException(
            origin,
            message.replace("%MEANING%", responseCode.getMeaning()),
            requestInformation,
            responseInformation);

    if (azureException.isPresent()) {
      throw azureException.get();
    } else {
      throw new AzureException(
          origin, message.replace("%MEANING%", "Unknown"), requestInformation, responseInformation);
    }
  }

  private Optional<? extends AzureException> handleKnownError(
      RestRequestHttpResponseCode responseCode,
      String code,
      String message,
      RestRequestInfo requestInformation,
      RestRequestResponseInfo responseInformation) {

    return RestRequestResultErrorCode.fromCode(code, responseCode)
        .flatMap(
            restRequestResultCode ->
                restRequestResultCode.getAzureException(
                    origin,
                    ((message == null) ? restRequestResultCode.getMeaning() : message)
                        + " (reference: "
                        + restRequestResultCode.getReference()
                        + ")",
                    requestInformation,
                    responseInformation));
  }

  private void request(HttpRequest.Builder requestBuilder) {
    requestBuilder.setHeader("User-Agent", AT4J.USER_AGENT);
    requestBuilder.setHeader("Accept", "application/json");
    requestBuilder.setHeader("Content-Type", "application/json");
    HttpRequest.BodyPublisher bodyPublisher =
        body == null
            ? HttpRequest.BodyPublishers.noBody()
            : HttpRequest.BodyPublishers.ofString(body);
    requestBuilder.method(method.name(), bodyPublisher);
  }
}
