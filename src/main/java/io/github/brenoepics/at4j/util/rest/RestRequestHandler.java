package io.github.brenoepics.at4j.util.rest;

/**
 * This class is responsible for handling REST requests. It stores the result of
 * a REST request, the current request being processed, and the timestamp of the
 * response.
 *
 */
public class RestRequestHandler {
  // The result of the REST request
  private final RestRequestResult result;
  // The current request being processed
  private final RestRequest currentRequest;
  // The timestamp of the response
  private final long responseTimestamp;

  /**
   * Constructs a new RestRequestHandler with the given result, current request,
   * and response timestamp.
   *
   * @param result the result of the REST request
   * @param currentRequest the current request being processed
   * @param responseTimestamp the timestamp of the response
   */
  public RestRequestHandler(RestRequestResult result,
                            RestRequest currentRequest,
                            long responseTimestamp) {
    this.result = result;
    this.currentRequest = currentRequest;
    this.responseTimestamp = responseTimestamp;
  }

  /**
   * Returns the result of the REST request.
   *
   * @return the result of the REST request
   */
  public RestRequestResult getResult() { return result; }

  /**
   * Returns the current request being processed.
   *
   * @return the current request being processed
   */
  public RestRequest getCurrentRequest() { return currentRequest; }

  /**
   * Returns the timestamp of the response.
   *
   * @return the timestamp of the response
   */
  public long getResponseTimestamp() { return responseTimestamp; }
}
