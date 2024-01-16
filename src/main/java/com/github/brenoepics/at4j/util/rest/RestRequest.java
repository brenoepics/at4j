package com.github.brenoepics.at4j.util.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.brenoepics.at4j.AT4J;
import com.github.brenoepics.at4j.AzureApi;
import com.github.brenoepics.at4j.core.AzureApiImpl;
import com.github.brenoepics.at4j.core.exceptions.AzureException;
import com.github.brenoepics.at4j.util.logging.LoggerUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.Function;
import okhttp3.*;
import org.apache.logging.log4j.Logger;

/** This class is used to wrap a rest request. */
public class RestRequest<T> {

  /** The (logger) of this class. */
  private static final Logger logger = LoggerUtil.getLogger(RestRequest.class);

  private final AzureApiImpl<T> api;
  private final RestMethod method;
  private final RestEndpoint endpoint;

  private volatile boolean includeAuthorizationHeader = true;
  private AtomicReferenceArray<String> urlParameters = new AtomicReferenceArray<>(new String[0]);
  private final Multimap<String, String> queryParameters = ArrayListMultimap.create();
  private final Map<String, String> headers = new HashMap<>();
  private volatile String body = null;

  private final CompletableFuture<RestRequestResult<T>> result = new CompletableFuture<>();

  /** The multipart body of the request. */
  private MultipartBody multipartBody;

  /** The custom major parameter if it's not included in the url (e.g., for reactions) */
  private String customMajorParam = null;

  /** The origin of the rest request. */
  private final Exception origin;

  /**
   * Creates a new instance of this class.
   *
   * @param api The api which will be used to execute the request.
   * @param method The http method of the request.
   * @param endpoint The endpoint to which the request should be sent.
   */
  public RestRequest(AzureApi api, RestMethod method, RestEndpoint endpoint) {
    this.api = (AzureApiImpl) api;
    this.method = method;
    this.endpoint = endpoint;
    this.queryParameters.put("api-version", AT4J.AZURE_TRANSLATOR_API_VERSION);

    this.origin = new Exception("origin of RestRequest call");
  }

  /**
   * Gets the api which is used for this request.
   *
   * @return The api which is used for this request.
   */
  public AzureApiImpl<T> getApi() {
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
  public Multimap<String, String> getQueryParameters() {
    return queryParameters;
  }

  /**
   * Gets an array with all used url parameters.
   *
   * @return An array with all used url parameters.
   */
  public String[] getUrlParameters() {
    String[] parameters = new String[urlParameters.length()];
    for (int i = 0; i < urlParameters.length(); i++) {
      parameters[i] = urlParameters.get(i);
    }
    return parameters;
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
   * Gets the major url parameter of this request. If a request has a major parameter, it means that
   * the rate-limits for this request are based on this parameter.
   *
   * @return The major url parameter used for rate-limits.
   */
  public Optional<String> getMajorUrlParameter() {
    if (customMajorParam != null) {
      return Optional.of(customMajorParam);
    }

    Optional<Integer> majorParameterPosition = endpoint.getMajorParameterPosition();
    if (!majorParameterPosition.isPresent()) {
      return Optional.empty();
    }

    if (majorParameterPosition.get() >= urlParameters.length()) {
      return Optional.empty();
    }

    return Optional.of(urlParameters.get(majorParameterPosition.get()));
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
   * @return The current instance to chain call methods.
   */
  public RestRequest<T> addQueryParameter(String key, String value) {
    queryParameters.put(key, value);
    return this;
  }

  /**
   * Adds a header to the request.
   *
   * @param name The name of the header.
   * @param value The value of the header.
   * @return The current instance to chain call methods.
   */
  public RestRequest<T> addHeader(String name, String value) {
    headers.put(name, value);
    return this;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  /**
   * Sets the url parameters, e.g., a language parameter.
   *
   * @param parameters The parameters.
   * @return The current instance to chain call methods.
   */
  public RestRequest<T> setUrlParameters(String... parameters) {
    this.urlParameters = new AtomicReferenceArray<>(parameters);
    return this;
  }

  /**
   * Sets the multipart body of the request. If a multipart body is set, the {@link
   * #setBody(String)} method is ignored!
   *
   * @param multipartBody The multipart body of the request.
   * @return The current instance to chain call methods.
   */
  public RestRequest<T> setMultipartBody(MultipartBody multipartBody) {
    this.multipartBody = multipartBody;
    return this;
  }

  /**
   * Sets a custom major parameter.
   *
   * @param customMajorParam The custom parameter to set.
   * @return The current instance to chain call methods.
   */
  public RestRequest<T> setCustomMajorParam(String customMajorParam) {
    this.customMajorParam = customMajorParam;
    return this;
  }

  /**
   * Sets the body of the request.
   *
   * @param body The body of the request.
   * @return The current instance to chain call methods.
   */
  public RestRequest<T> setBody(JsonNode body) {
    return setBody(body.toString());
  }

  /**
   * Sets the body of the request.
   *
   * @param body The body of the request.
   * @return The current instance to chain call methods.
   */
  public RestRequest<T> setBody(String body) {
    this.body = body;
    return this;
  }

  /**
   * Sets if an authorization header should be included in this request.
   *
   * @param includeAuthorizationHeader Whether the authorization header should be included or not.
   * @return The current instance to chain call methods.
   */
  public RestRequest<T> includeAuthorizationHeader(boolean includeAuthorizationHeader) {
    this.includeAuthorizationHeader = includeAuthorizationHeader;
    return this;
  }

  /**
   * Executes the request. This will automatically retry if we hit a ratelimit.
   *
   * @param function A function which processes the rest response to the requested object.
   * @return A future which will contain the output of the function.
   */
  public CompletableFuture<T> execute(Function<RestRequestResult<T>, T> function) {
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
  public CompletableFuture<RestRequestResult<T>> getResult() {
    return result;
  }

  /**
   * Gets the information for this rest request.
   *
   * @return The information for this rest request.
   */
  public RestRequestInformation asRestRequestInformation() {
    try {
      String[] parameters = new String[urlParameters.length()];
      for (int i = 0; i < urlParameters.length(); i++) {
        parameters[i] = urlParameters.get(i);
      }
      return new RestRequestInformationImpl(
          api,
          new URL(endpoint.getFullUrl(api.getBaseURL(), parameters)),
          queryParameters,
          headers,
          body);
    } catch (MalformedURLException e) {
      throw new AssertionError(e);
    }
  }

  /**
   * Executes the request blocking.
   *
   * @return The result of the request.
   * @throws AzureException Thrown in case of an error while requesting azure.
   * @throws IOException Thrown if OkHttp {@link OkHttpClient#newCall(Request)} throws an {@link
   *     IOException}.
   */
  public RestRequestResult<T> executeBlocking() throws AzureException, IOException {
    Request.Builder requestBuilder = new Request.Builder();
    String[] parameters = new String[urlParameters.length()];
    for (int i = 0; i < urlParameters.length(); i++) {
      parameters[i] = urlParameters.get(i);
    }
    HttpUrl.Builder httpUrlBuilder =
        endpoint.getOkHttpUrl(api.getBaseURL(), parameters).newBuilder();
    queryParameters.forEach(httpUrlBuilder::addQueryParameter);
    requestBuilder.url(httpUrlBuilder.build());
    request(requestBuilder);

    if (includeAuthorizationHeader) {
      requestBuilder.addHeader("Ocp-Apim-Subscription-Key", api.getSubscriptionKey());
      api.getSubscriptionRegion()
          .ifPresent(region -> requestBuilder.addHeader("Ocp-Apim-Subscription-Region", region));
    }

    String fullUrl = endpoint.getFullUrl(api.getBaseURL(), parameters);
    headers.forEach(requestBuilder::addHeader);
    logger.debug(
        "Trying to send {} request to {}{}",
        method.name(),
        fullUrl,
        body != null ? " with body " + body : "");

    try (Response response = getApi().getHttpClient().newCall(requestBuilder.build()).execute()) {
      RestRequestResult<T> requestResult = new RestRequestResult<>(this, response);

      String bodyPresent = requestResult.getBody().map(b -> "").orElse("empty");
      String bodyString = requestResult.getStringBody().orElse("");
      logger.debug(
          "Sent {} request to {} and received status code {} with {} body {}",
          method.name(),
          fullUrl,
          response.code(),
          bodyPresent,
          bodyString);

      if (response.code() >= 300 || response.code() < 200) {
        return handleError(response.code(), requestResult);
      }

      return requestResult;
    }
  }

  private RestRequestResult<T> handleError(int resultCode, RestRequestResult<T> result)
      throws AzureException {
    RestRequestInformation requestInformation = asRestRequestInformation();
    RestRequestResponseInformation responseInformation =
        new RestRequestResponseInformationImpl(requestInformation, result);
    Optional<RestRequestHttpResponseCode> responseCode =
        RestRequestHttpResponseCode.fromCode(resultCode);

    if (!result.getJsonBody().isNull() && result.getJsonBody().has("error")) {
      handleKnownError(result, responseCode.orElse(null), requestInformation, responseInformation);
    }

    if (resultCode == 429) {
      return result;
    }

    Optional<? extends AzureException> azureException =
        responseCode.flatMap(
            restRequestHttpResponseCode ->
                restRequestHttpResponseCode.getAzureException(
                    origin,
                    "Received a "
                        + resultCode
                        + " response from Azure with"
                        + (result.getBody().isPresent() ? "" : " empty")
                        + " body"
                        + result.getStringBody().map(s -> " " + s).orElse("")
                        + "!",
                    requestInformation,
                    responseInformation));

    String bodyPresent = result.getBody().isPresent() ? "" : " empty";
    throw azureException.isPresent()
        ? azureException.get()
        : new AzureException(
            origin,
            "Received a "
                + resultCode
                + " response from Azure with"
                + bodyPresent
                + " body"
                + result.getStringBody().map(s -> " " + s).orElse("")
                + "!",
            requestInformation,
            responseInformation);
  }

  private void handleKnownError(
      RestRequestResult<T> result,
      RestRequestHttpResponseCode responseCode,
      RestRequestInformation requestInformation,
      RestRequestResponseInformation responseInformation)
      throws AzureException {
    JsonNode errorBody = result.getJsonBody().get("error");
    int code = errorBody.get("code").asInt();
    String message = errorBody.has("message") ? errorBody.get("message").asText() : null;
    Optional<? extends AzureException> azureException =
        RestRequestResultErrorCode.fromCode(code, responseCode)
            .flatMap(
                restRequestResultCode ->
                    restRequestResultCode.getAzureException(
                        origin,
                        (message == null) ? restRequestResultCode.getMeaning() : message,
                        requestInformation,
                        responseInformation));

    if (azureException.isPresent()) {
      throw azureException.get();
    }
  }

  private RequestBody createMultipartBody() {
    if (multipartBody != null) {
      return multipartBody;
    }

    if (body != null) {
      return RequestBody.create(body, MediaType.parse("application/json"));
    }
    return RequestBody.create(new byte[0], null);
  }

  private void request(okhttp3.Request.Builder requestBuilder) {
    RequestBody requestBody = createMultipartBody();

    switch (method) {
      case GET:
        requestBuilder.get();
        break;
      case POST:
        requestBuilder.post(requestBody);
        break;
      case PUT:
        requestBuilder.put(requestBody);
        break;
      case DELETE:
        requestBuilder.delete(requestBody);
        break;
      case PATCH:
        requestBuilder.patch(requestBody);
        break;
      default:
        throw new IllegalArgumentException("Unsupported http method!");
    }
  }
}
