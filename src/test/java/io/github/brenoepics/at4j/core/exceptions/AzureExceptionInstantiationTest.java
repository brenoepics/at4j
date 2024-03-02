package io.github.brenoepics.at4j.core.exceptions;

import io.github.brenoepics.at4j.util.rest.RestRequestInfo;
import io.github.brenoepics.at4j.util.rest.RestRequestResponseInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AzureExceptionInstantiationTest {

  private final Exception origin = new Exception();
  private final RestRequestInfo request = Mockito.mock(RestRequestInfo.class);
  private final RestRequestResponseInfo response = Mockito.mock(RestRequestResponseInfo.class);
  private final AzureExceptionInstantiation<AzureException> azureExceptionInstantiation =
      AzureException::new;

  @Test
  void createInstance_withValidInputs_returnsNewInstance() {
    String message = "Test message";
    AzureException result =
        azureExceptionInstantiation.createInstance(origin, message, request, response);

    assertNotNull(result);
    assertTrue(result.getRequest().isPresent());
    assertTrue(result.getResponse().isPresent());

    assertSame(origin, result.getCause());
    assertEquals(message, result.getMessage());
    assertEquals(request, result.getRequest().get());
    assertEquals(response, result.getResponse().get());
  }
}
