package io.github.brenoepics.at4j.util.rest;

import io.github.brenoepics.at4j.AzureApi;
import java.util.Optional;

/** The implementation of {@link RestRequestResponseInformation}. */
public class RestRequestResponseInformationImpl<T> implements RestRequestResponseInformation {

  private final RestRequestInformation request;
  private final RestRequestResult<T> restRequestResult;

  /**
   * Creates a new rest request response information.
   *
   * @param request The request which this response answered.
   * @param restRequestResult The result of the response.
   */
  public RestRequestResponseInformationImpl(
      RestRequestInformation request, RestRequestResult<T> restRequestResult) {
    this.request = request;
    this.restRequestResult = restRequestResult;
  }

  /**
   * Gets the rest request result.
   *
   * @return The rest request result.
   */
  public RestRequestResult<T> getRestRequestResult() {
    return restRequestResult;
  }

  @Override
  public AzureApi getApi() {
    return getRequest().getApi();
  }

  @Override
  public RestRequestInformation getRequest() {
    return request;
  }

  @Override
  public int getCode() {
    return restRequestResult.getResponse().code();
  }

  @Override
  public Optional<String> getBody() {
    return restRequestResult.getStringBody();
  }
}
