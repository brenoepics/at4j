package io.github.brenoepics.at4j.util.rest;

import io.github.brenoepics.at4j.AzureApi;
import java.util.Optional;

/** The implementation of {@link RestRequestResponseInfo}. */
public class RestRequestResponseInfoImpl implements RestRequestResponseInfo {

  private final RestRequestInfo request;
  private final RestRequestResult restRequestResult;

  /**
   * Creates a new rest request response information.
   *
   * @param request The request which this response answered.
   * @param restRequestResult The result of the response.
   */
  public RestRequestResponseInfoImpl(
          RestRequestInfo request, RestRequestResult restRequestResult) {
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
  public RestRequestInfo getRequest() {
    return request;
  }

  @Override
  public int getCode() {
    return restRequestResult.getResponse().statusCode();
  }

  @Override
  public Optional<String> getBody() {
    return restRequestResult.getStringBody();
  }
}
