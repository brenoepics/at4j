package io.github.brenoepics.at4j;

import io.github.brenoepics.at4j.azure.lang.Language;
import io.github.brenoepics.at4j.core.thread.ThreadPoolImpl;
import io.github.brenoepics.at4j.data.request.AvailableLanguagesParams;
import org.junit.jupiter.api.Test;
import io.github.brenoepics.at4j.azure.BaseURL;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.net.ProxySelector;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

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

  @Test
  void shouldSetThreadPoolWhenProvided() {
    ExecutorService executorService = ThreadPoolImpl.newAt4jDefault();
    AzureApiBuilder builder =
        new AzureApiBuilder().setKey("testKey").executorService(executorService);
    AzureApi api = builder.build();
    assertEquals(executorService, api.getThreadPool().getExecutorService());
  }

  @Test
  void shouldSetAllParameters() throws NoSuchAlgorithmException {
    ProxySelector selector = ProxySelector.getDefault();
    SSLContext sslContext = SSLContext.getDefault();
    SSLParameters parameters = new SSLParameters();
    AzureApi api =
        new AzureApiBuilder()
            .setKey("test")
            .region("test")
            .baseURL(BaseURL.GLOBAL)
            .connectTimeout(Duration.ofSeconds(60))
            .proxy(selector)
            .sslContext(sslContext)
            .sslParameters(parameters)
            .build();
    Optional<Collection<Language>> languages =
        api.getAvailableLanguages(new AvailableLanguagesParams()).join();
    assertTrue(languages.isPresent());
    assertFalse(languages.get().isEmpty());
  }
}
