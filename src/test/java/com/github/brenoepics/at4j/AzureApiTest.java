package com.github.brenoepics.at4j;

import static org.junit.Assert.*;

import com.github.brenoepics.at4j.azure.BaseURL;
import com.github.brenoepics.at4j.azure.lang.Language;
import com.github.brenoepics.at4j.data.request.AvailableLanguagesParams;
import com.github.brenoepics.at4j.data.request.TranslateParams;
import com.github.brenoepics.at4j.data.response.TranslationResponse;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.junit.Test;

public class AzureApiTest {
  @Test
  public void BuildNullKey() {
    AzureApiBuilder builder = new AzureApiBuilder();
    assertThrows("Subscription key cannot be null", NullPointerException.class, builder::build);
  }

  @Test
  public void buildApi() {
    AzureApi api = new AzureApiBuilder().setKey("test").region("test").build();
    assertNotNull(api);
    api.disconnect();
  }

  @Test
  public void getLanguages() {
    AzureApi api =
        new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("test").region("test").build();

    CompletableFuture<Optional<Collection<Language>>> languages =
        api.getAvailableLanguages(new AvailableLanguagesParams().setSourceLanguage("en"));
    languages.whenComplete((s, throwable) -> assertNull(throwable));
    assertNotNull(languages.join());
    api.disconnect();
  }

  @Test
  public void getLanguagesEmptyKey() {
    AzureApi api = new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("").region("test").build();

    CompletableFuture<Optional<Collection<Language>>> languages =
        api.getAvailableLanguages(new AvailableLanguagesParams());
    languages.whenComplete((s, throwable) -> assertNull(throwable));
    api.disconnect();
  }

  @Test
  public void getLanguagesEmptySourceLanguage() {
    AzureApi api =
        new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("test").region("test").build();

    CompletableFuture<Optional<Collection<Language>>> languages =
        api.getAvailableLanguages(new AvailableLanguagesParams());
    languages.whenComplete((s, throwable) -> assertNull(throwable));
    api.disconnect();
  }

  @Test
  public void translateEmptyKey() {
    AzureApi api = new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("").region("test").build();

    TranslateParams params = new TranslateParams("test", List.of("pt")).setSourceLanguage("en");
    CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
    assertThrows(CompletionException.class, translation::join);
    api.disconnect();
  }

  @Test
  public void translateEmptyText() {
    AzureApi api = new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("test").build();

    TranslateParams params = new TranslateParams("", List.of("pt")).setSourceLanguage("en");
    CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
    Optional<TranslationResponse> tr = translation.join();
    tr.ifPresent(translations -> assertEquals(0, translations.getTranslations().size()));
    api.disconnect();
  }

  @Test
  public void translateEmptySourceLanguage() {
    AzureApi api = new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("test").build();

    TranslateParams params = new TranslateParams("", List.of("pt")).setTargetLanguages("pt");
    CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
    Optional<TranslationResponse> tr = translation.join();
    tr.ifPresent(translations -> assertEquals(0, translations.getTranslations().size()));
    api.disconnect();
  }
}
