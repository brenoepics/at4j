package io.github.brenoepics.at4j.util.rest;

import io.github.brenoepics.at4j.AzureApi;
import java.net.URL;
import java.util.*;

/** The implementation of {@link RestRequestInformation}. */
public class RestRequestInformationImpl implements RestRequestInformation {

  private final AzureApi api;
  private final URL url;
  private final Map<String, Collection<String>> queryParameters;
  private final Map<String, String> headers;
  private final String body;

  /**
   * Creates new rest request information.
   *
   * @param api The responsible azure api instance.
   * @param url The url, the request should be sent to.
   * @param queryParameter The query parameters of the rest request.
   * @param headers The headers of the rest request.
   * @param body The body of the rest request.
   */
  public RestRequestInformationImpl(
      AzureApi api,
      URL url,
      Map<String, Collection<String>> queryParameter,
      Map<String, String> headers,
      String body) {
    this.api = api;
    this.url = url;
    this.queryParameters = queryParameter;
    this.headers = new HashMap<>(headers);
    this.body = body;
  }

  @Override
  public AzureApi getApi() {
    return api;
  }

  @Override
  public URL getUrl() {
    return url;
  }

  @Override
  public Map<String, Collection<String>> getQueryParameters() {
    return Collections.unmodifiableMap(queryParameters);
  }

  @Override
  public Map<String, String> getHeaders() {
    return Collections.unmodifiableMap(headers);
  }

  @Override
  public Optional<String> getBody() {
    return Optional.ofNullable(body);
  }
}
