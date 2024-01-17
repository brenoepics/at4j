package io.github.brenoepics.at4j;

import io.github.brenoepics.at4j.azure.BaseURL;
import io.github.brenoepics.at4j.azure.lang.Language;
import io.github.brenoepics.at4j.data.request.AvailableLanguagesParams;
import io.github.brenoepics.at4j.data.request.TranslateParams;
import io.github.brenoepics.at4j.data.response.TranslationResponse;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


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
void translateEmptyText() {
    AzureApi api = new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("test").build();

    TranslateParams params = new TranslateParams("", List.of("pt")).setSourceLanguage("en");
    CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
    Optional<TranslationResponse> tr = translation.join();
    tr.ifPresent(translations -> assertEquals(0, translations.getTranslations().size()));
    api.disconnect();
  }

  @Test
void translateEmptySourceLanguage() {
    AzureApi api = new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("test").build();

    TranslateParams params = new TranslateParams("", List.of("pt")).setTargetLanguages("pt");
    CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
    Optional<TranslationResponse> tr = translation.join();
    tr.ifPresent(translations -> assertEquals(0, translations.getTranslations().size()));
    api.disconnect();
  }
}
