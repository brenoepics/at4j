package io.github.brenoepics.at4j.util.rest;

import static org.junit.jupiter.api.Assertions.*;

import io.github.brenoepics.at4j.core.exceptions.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RestRequestHttpResponseCodeTest {

  @Test
  void fromCode() {
    assertEquals(
        RestRequestHttpResponseCode.OK, RestRequestHttpResponseCode.fromCode(200).orElse(null));
    assertEquals(
        RestRequestHttpResponseCode.CREATED,
        RestRequestHttpResponseCode.fromCode(201).orElse(null));
  }

  @Test
  void fromAzureExceptionClass() {
    assertEquals(
        RestRequestHttpResponseCode.BAD_REQUEST,
        RestRequestHttpResponseCode.fromAzureExceptionClass(BadRequestException.class)
            .orElse(null));
    assertEquals(
        RestRequestHttpResponseCode.UNAUTHORIZED,
        RestRequestHttpResponseCode.fromAzureExceptionClass(UnauthorizedException.class)
            .orElse(null));
  }

  @Test
  void getCode() {
    assertEquals(200, RestRequestHttpResponseCode.OK.getCode());
    assertEquals(201, RestRequestHttpResponseCode.CREATED.getCode());
  }

  @Test
  void getMeaning() {
    assertEquals("The request completed successfully", RestRequestHttpResponseCode.OK.getMeaning());
    assertEquals(
        "The entity was created successfully", RestRequestHttpResponseCode.CREATED.getMeaning());
  }

  @Test
  void getAzureBadRequestException() {
    RestRequestInfo request = Mockito.mock(RestRequestInfo.class);
    RestRequestResponseInfo response = Mockito.mock(RestRequestResponseInfo.class);
    assertTrue(
        RestRequestHttpResponseCode.BAD_REQUEST
            .getAzureException(new Exception(), "Test", request, response)
            .isPresent());
  }

  @Test
  void getAzureExceptionForAllCodes() {
    RestRequestInfo request = Mockito.mock(RestRequestInfo.class);
    RestRequestResponseInfo response = Mockito.mock(RestRequestResponseInfo.class);

    var codes =
        Arrays.stream(RestRequestHttpResponseCode.values())
            .filter(c -> c.getAzureExceptionClass().isPresent())
            .collect(Collectors.toList());
    for (RestRequestHttpResponseCode code : codes) {
      assertTrue(
          code.getAzureException(new Exception(), "Test", request, response).isPresent(),
          "Expected an AzureException for code: " + code);
    }
  }

  @Test
  void getAzureExceptionClass() {
    assertEquals(
        BadRequestException.class,
        RestRequestHttpResponseCode.BAD_REQUEST.getAzureExceptionClass().orElse(null));
    assertEquals(
        UnauthorizedException.class,
        RestRequestHttpResponseCode.UNAUTHORIZED.getAzureExceptionClass().orElse(null));
  }

  @Test
  void values() {
    RestRequestHttpResponseCode[] codes = RestRequestHttpResponseCode.values();
    assertTrue(codes.length > 0);
  }

  @Test
  void valueOf() {
    assertEquals(RestRequestHttpResponseCode.OK, RestRequestHttpResponseCode.valueOf("OK"));
    assertEquals(
        RestRequestHttpResponseCode.CREATED, RestRequestHttpResponseCode.valueOf("CREATED"));
  }
}
