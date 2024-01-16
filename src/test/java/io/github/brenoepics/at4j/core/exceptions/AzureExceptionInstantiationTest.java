package io.github.brenoepics.at4j.core.exceptions;

import io.github.brenoepics.at4j.util.rest.RestRequestInformation;
import io.github.brenoepics.at4j.util.rest.RestRequestResponseInformation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AzureExceptionInstantiationTest {

  private final Exception origin = new Exception();
  private final RestRequestInformation request = Mockito.mock(RestRequestInformation.class);
  private final RestRequestResponseInformation response =
      Mockito.mock(RestRequestResponseInformation.class);
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
