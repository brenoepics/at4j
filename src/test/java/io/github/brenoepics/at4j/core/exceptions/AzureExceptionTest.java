package io.github.brenoepics.at4j.core.exceptions;

import io.github.brenoepics.at4j.azure.BaseURL;
import io.github.brenoepics.at4j.core.AzureApiImpl;
import io.github.brenoepics.at4j.util.rest.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AzureExceptionTest<T> {

  @Mock private AzureApiImpl<T> api;
  @Mock private RestRequestResult<T> result;
  private RestRequestInformation request;

  @BeforeEach
  public void setup() throws MalformedURLException {
    URL url = new URL("https://" + BaseURL.GLOBAL.getUrl());
    Map<String, Collection<String>> queryParameter = new HashMap<>();
    HashMap<String, String> headers = new HashMap<>();
    String body = "Test body";
    request = new RestRequestInformationImpl(api, url, queryParameter, headers, body);
  }

  @Test
  void exceptionRetainsOriginAndMessage() {
    Exception origin = new Exception();
    String message = "Test message";
    AzureException exception = new AzureException(origin, message, null, null);

    assertSame(origin, exception.getCause());
    assertEquals(message, exception.getMessage());
  }

  @Test
  void requestInformationIsRetained() {
    AzureException exception = new AzureException(null, null, request, null);
    assertEquals(Optional.of(request), exception.getRequest());
  }

  @Test
  void responseInformationIsRetained() {
    RestRequestResponseInformation response =
        new RestRequestResponseInformationImpl<>(request, result);
    AzureException exception = new AzureException(null, null, null, response);

    assertEquals(Optional.of(response), exception.getResponse());
  }

  @Test
  void requestInformationIsNullWhenNotProvided() {
    AzureException exception = new AzureException(null, null, null, null);

    assertEquals(Optional.empty(), exception.getRequest());
  }

  @Test
  void responseInformationIsNullWhenNotProvided() {
    AzureException exception = new AzureException(null, null, null, null);

    assertEquals(Optional.empty(), exception.getResponse());
  }
}
