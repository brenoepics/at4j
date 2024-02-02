package io.github.brenoepics.at4j;

import static org.junit.jupiter.api.Assertions.*;

import io.github.brenoepics.at4j.azure.BaseURL;
import io.github.brenoepics.at4j.azure.lang.Language;
import io.github.brenoepics.at4j.data.DetectedLanguage;
import io.github.brenoepics.at4j.data.request.AvailableLanguagesParams;
import io.github.brenoepics.at4j.data.request.DetectLanguageParams;
import io.github.brenoepics.at4j.data.request.TranslateParams;
import io.github.brenoepics.at4j.data.response.TranslationResponse;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

class AzureApiTest {
  @Test
  void BuildNullKey() {
    AzureApiBuilder builder = new AzureApiBuilder();
    assertThrows(NullPointerException.class, builder::build, "Subscription key cannot be null");
  }

  @Test
  void buildApi() {
    AzureApi api = new AzureApiBuilder().setKey("test").region("test").build();
    assertNotNull(api);
    api.disconnect();
  }

  @Test
  void getLanguages() {
    AzureApi api =
        new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("test").region("test").build();

    CompletableFuture<Optional<Collection<Language>>> languages =
        api.getAvailableLanguages(new AvailableLanguagesParams().setSourceLanguage("en"));
    languages.whenComplete((s, throwable) -> assertNull(throwable));
    assertNotNull(languages.join());
    api.disconnect();
  }

  @Test
  void getLanguagesEmptyKey() {
    AzureApi api = new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("").region("test").build();

    CompletableFuture<Optional<Collection<Language>>> languages =
        api.getAvailableLanguages(new AvailableLanguagesParams());
    languages.whenComplete((s, throwable) -> assertNull(throwable));
    api.disconnect();
  }

  @Test
  void getLanguagesEmptySourceLanguage() {
    AzureApi api =
        new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("test").region("test").build();

    CompletableFuture<Optional<Collection<Language>>> languages =
        api.getAvailableLanguages(new AvailableLanguagesParams());
    languages.whenComplete((s, throwable) -> assertNull(throwable));
    api.disconnect();
  }

  @Test
  void translateEmptyKey() {
    AzureApi api = new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("").region("test").build();

    TranslateParams params = new TranslateParams("test", List.of("pt")).setSourceLanguage("en");
    CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
    assertThrows(CompletionException.class, translation::join);
    api.disconnect();
  }

  @Test
  void translateHelloWorld() {
    String azureKey = System.getenv("AZURE_KEY");
    String region = System.getenv("AZURE_REGION");
    Assumptions.assumeTrue(
        azureKey != null && region != null, "Azure Credentials are null, skipping the test");
    Assumptions.assumeTrue(
        !azureKey.isEmpty() && !region.isEmpty(), "Azure Credentials are empty, skipping the test");

    AzureApiBuilder builder = new AzureApiBuilder().setKey(azureKey).region(region);
    AzureApi api = builder.build();

    TranslateParams params = new TranslateParams("Hello World!", List.of("pt", "es"));
    Optional<TranslationResponse> translate = api.translate(params).join();
    assertTrue(translate.isPresent());
    assertEquals(2, translate.get().getResultList().get(0).getTranslations().size());
  }

  @Test
  void translateMultiText() {
    String azureKey = System.getenv("AZURE_KEY");
    String region = System.getenv("AZURE_REGION");
    Assumptions.assumeTrue(
        azureKey != null && region != null, "Azure Credentials are null, skipping the test");
    Assumptions.assumeTrue(
        !azureKey.isEmpty() && !region.isEmpty(), "Azure Credentials are empty, skipping the test");

    AzureApiBuilder builder = new AzureApiBuilder().setKey(azureKey).region(region);
    AzureApi api = builder.build();

    TranslateParams params =
        new TranslateParams(List.of("Hello World!", "How are you?"), List.of("pt", "es"));
    Optional<TranslationResponse> translate = api.translate(params).join();
    assertTrue(translate.isPresent());

    assertEquals(2, translate.get().getResultList().size());
  }

  @Test
  void detectHelloWorldLanguage() {
    String azureKey = System.getenv("AZURE_KEY");
    String region = System.getenv("AZURE_REGION");
    Assumptions.assumeTrue(
        azureKey != null && region != null, "Azure Credentials are null, skipping the test");
    Assumptions.assumeTrue(
        !azureKey.isEmpty() && !region.isEmpty(), "Azure Credentials are empty, skipping the test");

    AzureApiBuilder builder = new AzureApiBuilder().setKey(azureKey).region(region);
    AzureApi api = builder.build();

    Optional<DetectedLanguage> detect =
        api.detectLanguage(new DetectLanguageParams("Hello World!")).join();

    assertTrue(detect.isPresent());
    assertEquals("en", detect.get().getLanguageCode());
  }
}
