package io.github.brenoepics.at4j.util.rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import io.github.brenoepics.at4j.util.logging.LoggerUtil;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Optional;
import org.apache.logging.log4j.Logger;

/** The result of a {@link RestRequest}. */
public class RestRequestResult<T> {

  /** The (logger) of this class. */
  private static final Logger logger = LoggerUtil.getLogger(RestRequestResult.class);

  private final RestRequest<T> request;
  private final HttpResponse<String> response;
  private final String stringBody;
  private final JsonNode jsonBody;

  /**
   * Creates a new RestRequestResult.
   *
   * @param request The request of the result.
   * @param response The response of the RestRequest.
   * @throws IOException Passed on from {@link HttpResponse#body()}.
   */
  public RestRequestResult(RestRequest<T> request, HttpResponse<String> response) throws IOException {
    this.request = request;
    this.response = response;
    this.stringBody = response.body();
    if (stringBody == null) {
      jsonBody = NullNode.getInstance();
      return;
    }

      ObjectMapper mapper = request.getApi().getObjectMapper();
      JsonNode jsonNode;
      try {
        jsonNode = mapper.readTree(stringBody);
      } catch (JsonParseException e) {
        // This can happen if Azure sends garbage
        logger.debug("Failed to parse json response", e);
        jsonNode = null;
      }
      this.jsonBody = jsonNode == null ? NullNode.getInstance() : jsonNode;
  }

  /**
   * Gets the {@link RestRequest} which belongs to this result.
   *
   * @return The Request which belongs to this result.
   */
  public RestRequest<T> getRequest() {
    return request;
  }

  /**
   * Gets the response of the {@link RestRequest}.
   *
   * @return The response of the RestRequest.
   */
  public HttpResponse<String> getResponse() {
    return response;
  }


  /**
   * Gets the string body of the response.
   *
   * @return The string body of the response.
   */
  public Optional<String> getStringBody() {
    return Optional.ofNullable(stringBody);
  }

  /**
   * Gets the json body of the response. Returns a {@link NullNode} if the response had none body or
   * the body is not in a valid json format.
   *
   * @return The json body of the response.
   */
  public JsonNode getJsonBody() {
    return jsonBody;
  }
}
