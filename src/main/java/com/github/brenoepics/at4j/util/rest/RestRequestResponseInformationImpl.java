package com.github.brenoepics.at4j.util.rest;

import com.github.brenoepics.at4j.AzureApi;
import java.util.Optional;

/** The implementation of {@link RestRequestResponseInformation}. */
public class RestRequestResponseInformationImpl implements RestRequestResponseInformation {

  private final RestRequestInformation request;
  private final RestRequestResult restRequestResult;

  /**
   * Creates a new rest request response information.
   *
   * @param request The request which this response answered.
   * @param restRequestResult The result of the response.
   */
  public RestRequestResponseInformationImpl(
      RestRequestInformation request, RestRequestResult restRequestResult) {
    this.request = request;
    this.restRequestResult = restRequestResult;
  }

  /**
   * Gets the rest request result.
   *
   * @return The rest request result.
   */
  public RestRequestResult getRestRequestResult() {
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
