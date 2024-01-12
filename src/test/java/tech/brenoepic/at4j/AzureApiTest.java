package tech.brenoepic.at4j;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.junit.Test;
import tech.brenoepic.at4j.azure.BaseURL;
import tech.brenoepic.at4j.azure.lang.Language;
import tech.brenoepic.at4j.data.request.AvailableLanguagesParams;
import tech.brenoepic.at4j.data.request.TranslateParams;
import tech.brenoepic.at4j.data.response.TranslationResponse;

public class AzureApiTest {

  @Test
  public void BuildNullKey() {
    AzureApiBuilder builder = new AzureApiBuilder();
    assertThrows("Subscription key cannot be null", IllegalArgumentException.class, builder::build);
  }

  @Test
<<<<<<< Updated upstream:src/test/java/tech/brenoepic/at4j/AzureApiTest.java
  public void BuildNullHttpClient() {
    AzureApiBuilder builder = new AzureApiBuilder().setSubscriptionKey("");
    assertThrows("HTTP client cannot be null", IllegalArgumentException.class, builder::build);
  }

  @Test
  public void buildApi() {
    AzureApi api =
        new AzureApiBuilder()
            .setSubscriptionKey("test")
            .setSubscriptionRegion("test")
            .setOkHttpClient(new OkHttpClient())
            .build();
=======
  public void buildApi() {
    AzureApi api = new AzureApiBuilder().setKey("test").region("test").build();
>>>>>>> Stashed changes:src/test/java/com/github/brenoepics/at4j/AzureApiTest.java
    assertNotNull(api);
    api.getThreadPool().getExecutorService().shutdown();
  }

  @Test
  public void getLanguages() {
    AzureApi api =
<<<<<<< Updated upstream:src/test/java/tech/brenoepic/at4j/AzureApiTest.java
        new AzureApiBuilder()
            .setBaseURL(BaseURL.GLOBAL)
            .setSubscriptionKey("test")
            .setSubscriptionRegion("test")
            .setOkHttpClient(new OkHttpClient())
            .build();
=======
        new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("test").region("test").build();
>>>>>>> Stashed changes:src/test/java/com/github/brenoepics/at4j/AzureApiTest.java

    CompletableFuture<Optional<Collection<Language>>> languages =
        api.getAvailableLanguages(new AvailableLanguagesParams().setSourceLanguage("en"));
    languages.whenComplete((s, throwable) -> assertNull(throwable));
    assertNotNull(languages.join());
    api.getThreadPool().getExecutorService().shutdown();
  }

  @Test
  public void getLanguagesEmptyKey() {
<<<<<<< Updated upstream:src/test/java/tech/brenoepic/at4j/AzureApiTest.java
    AzureApi api =
        new AzureApiBuilder()
            .setBaseURL(BaseURL.GLOBAL)
            .setSubscriptionKey("")
            .setSubscriptionRegion("test")
            .setOkHttpClient(new OkHttpClient())
            .build();
=======
    AzureApi api = new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("").region("test").build();
>>>>>>> Stashed changes:src/test/java/com/github/brenoepics/at4j/AzureApiTest.java

    CompletableFuture<Optional<Collection<Language>>> languages =
        api.getAvailableLanguages(new AvailableLanguagesParams());
    languages.whenComplete((s, throwable) -> assertNull(throwable));
    api.getThreadPool().getExecutorService().shutdown();
  }

  @Test
  public void getLanguagesEmptySourceLanguage() {
    AzureApi api =
<<<<<<< Updated upstream:src/test/java/tech/brenoepic/at4j/AzureApiTest.java
        new AzureApiBuilder()
            .setBaseURL(BaseURL.GLOBAL)
            .setSubscriptionKey("test")
            .setSubscriptionRegion("test")
            .setOkHttpClient(new OkHttpClient())
            .build();
=======
        new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("test").region("test").build();
>>>>>>> Stashed changes:src/test/java/com/github/brenoepics/at4j/AzureApiTest.java

    CompletableFuture<Optional<Collection<Language>>> languages =
        api.getAvailableLanguages(new AvailableLanguagesParams());
    languages.whenComplete((s, throwable) -> assertNull(throwable));
    api.getThreadPool().getExecutorService().shutdown();
  }

  @Test
  public void translateEmptyKey() {
<<<<<<< Updated upstream:src/test/java/tech/brenoepic/at4j/AzureApiTest.java
    AzureApi api =
        new AzureApiBuilder()
            .setBaseURL(BaseURL.GLOBAL)
            .setSubscriptionKey("")
            .setSubscriptionRegion("test")
            .setOkHttpClient(new OkHttpClient())
            .build();
=======
    AzureApi api = new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("").region("test").build();
>>>>>>> Stashed changes:src/test/java/com/github/brenoepics/at4j/AzureApiTest.java

    TranslateParams params =
        new TranslateParams("test").setSourceLanguage("en").setTargetLanguages("pt");
    CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
    assertThrows(CompletionException.class, translation::join);
    api.getThreadPool().getExecutorService().shutdown();
  }

  @Test
  public void translateEmptyText() {
<<<<<<< Updated upstream:src/test/java/tech/brenoepic/at4j/AzureApiTest.java
    AzureApi api =
        new AzureApiBuilder()
            .setBaseURL(BaseURL.GLOBAL)
            .setSubscriptionKey("test")
            .setOkHttpClient(new OkHttpClient())
            .build();
=======
    AzureApi api = new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("test").build();
>>>>>>> Stashed changes:src/test/java/com/github/brenoepics/at4j/AzureApiTest.java

    TranslateParams params =
        new TranslateParams("").setSourceLanguage("en").setTargetLanguages("pt");
    CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
    Optional<TranslationResponse> tr = translation.join();
    tr.ifPresent(translations -> assertEquals(0, translations.getTranslations().size()));
    api.getThreadPool().getExecutorService().shutdown();
  }

  @Test
  public void translateEmptySourceLanguage() {
<<<<<<< Updated upstream:src/test/java/tech/brenoepic/at4j/AzureApiTest.java
    AzureApi api =
        new AzureApiBuilder()
            .setBaseURL(BaseURL.GLOBAL)
            .setSubscriptionKey("test")
            .setOkHttpClient(new OkHttpClient())
            .build();
=======
    AzureApi api = new AzureApiBuilder().baseURL(BaseURL.GLOBAL).setKey("test").build();
>>>>>>> Stashed changes:src/test/java/com/github/brenoepics/at4j/AzureApiTest.java

    TranslateParams params = new TranslateParams("").setTargetLanguages("pt");
    CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
    Optional<TranslationResponse> tr = translation.join();
    tr.ifPresent(translations -> assertEquals(0, translations.getTranslations().size()));
    api.getThreadPool().getExecutorService().shutdown();
  }
}
