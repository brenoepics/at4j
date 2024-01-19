package io.github.brenoepics.at4j;

import org.junit.jupiter.api.Test;
import io.github.brenoepics.at4j.azure.BaseURL;

import static org.junit.jupiter.api.Assertions.*;

class AzureApiBuilderTest {

  @Test
  void shouldSetGlobalBaseUrlByDefault() {
    AzureApiBuilder builder = new AzureApiBuilder().setKey("testKey");
    AzureApi api = builder.build();
    assertEquals(BaseURL.GLOBAL, api.getBaseURL());
  }

  @Test
  void shouldSetBaseUrlWhenProvided() {
    AzureApiBuilder builder = new AzureApiBuilder().setKey("testKey");
    AzureApi api = builder.baseURL(BaseURL.EUROPE).build();
    assertEquals(BaseURL.EUROPE, api.getBaseURL());
  }

  @Test
  void shouldThrowExceptionWhenSubscriptionKeyIsNull() {
    AzureApiBuilder builder = new AzureApiBuilder();
    assertThrows(NullPointerException.class, builder::build);
  }

  @Test
  void shouldSetSubscriptionKeyWhenProvided() {
    AzureApiBuilder builder = new AzureApiBuilder();
    AzureApi api = builder.setKey("testKey").build();
    assertEquals("testKey", api.getSubscriptionKey());
  }

  @Test
  void shouldSetSubscriptionRegionWhenProvided() {
    AzureApiBuilder builder = new AzureApiBuilder().setKey("testKey");
    AzureApi api = builder.region("brazilsouth").build();
    assertEquals("brazilsouth", api.getSubscriptionRegion().orElse(null));
  }
}
