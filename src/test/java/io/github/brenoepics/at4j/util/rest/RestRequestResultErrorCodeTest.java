package io.github.brenoepics.at4j.util.rest;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RestRequestResultErrorCodeTest<T> {

  @Test
  void test_all_enum_values() {
    for (RestRequestResultErrorCode errorCode : RestRequestResultErrorCode.values()) {
      Optional<RestRequestResultErrorCode> retrievedErrorCode =
          RestRequestResultErrorCode.fromCode(errorCode.getCode(), errorCode.getResponseCode());
      assertTrue(retrievedErrorCode.isPresent());
      assertEquals(errorCode, retrievedErrorCode.get());
      assertEquals(errorCode.getCode(), retrievedErrorCode.get().getCode());
      assertEquals(errorCode.getMeaning(), retrievedErrorCode.get().getMeaning());
    }
  }

  @Test
  void test_getCode() {
    Map<RestRequestResultErrorCode, Integer> expectedCodes = new HashMap<>();
    expectedCodes.putAll(
        Arrays.stream(RestRequestResultErrorCode.values())
            .collect(
                HashMap::new,
                (map, errorCode) -> map.put(errorCode, errorCode.getCode()),
                HashMap::putAll));

    for (RestRequestResultErrorCode errorCode : RestRequestResultErrorCode.values()) {
      int expectedCode = expectedCodes.get(errorCode);
      int actualCode = errorCode.getCode();
      assertEquals(expectedCode, actualCode);
    }
  }

  @Test
  void test_getMeaning() {
    Map<RestRequestResultErrorCode, String> expectedMeanings = new HashMap<>();
    expectedMeanings.putAll(
        Arrays.stream(RestRequestResultErrorCode.values())
            .collect(
                HashMap::new,
                (map, errorCode) -> map.put(errorCode, errorCode.getMeaning()),
                HashMap::putAll));

    for (RestRequestResultErrorCode errorCode : RestRequestResultErrorCode.values()) {
      String expectedMeaning = expectedMeanings.get(errorCode);
      String actualMeaning = errorCode.getMeaning();
      assertEquals(expectedMeaning, actualMeaning);
    }
  }
}
