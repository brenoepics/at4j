import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.brenoepic.at4j.AzureApi;
import tech.brenoepic.at4j.AzureApiBuilder;
import tech.brenoepic.at4j.azure.BaseURL;
import tech.brenoepic.at4j.azure.lang.Language;
import tech.brenoepic.at4j.data.request.AvailableLanguagesParams;
import tech.brenoepic.at4j.data.request.TranslateParams;
import tech.brenoepic.at4j.data.Translation;
import tech.brenoepic.at4j.data.response.TranslationResponse;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;


public class AzureApiTests {
    @Mock
    AzureApi api;

    @Test
    @DisplayName("Build with null key")
    void BuildNullKey() {
        AzureApiBuilder builder = new AzureApiBuilder();
        Assertions.assertThrows(IllegalArgumentException.class, builder::build, "Subscription key cannot be null");
    }

    @Test
    @DisplayName("Build with HTTP client")
    void BuildNullHttpClient() {
        AzureApiBuilder builder = new AzureApiBuilder().setSubscriptionKey("");
        Assertions.assertThrowsExactly(IllegalArgumentException.class, builder::build, "HTTP client cannot be null");
    }

    @Test
    @DisplayName("Build the api")
    void buildApi() {
        api = new AzureApiBuilder().setSubscriptionKey("test").setSubscriptionRegion("test").setOkHttpClient(new OkHttpClient()).build();
        Assertions.assertNotNull(api);
        api.getThreadPool().getExecutorService().shutdown();
    }


    @Test
    @DisplayName("Get languages")
    void getLanguages() {
        AzureApi api = new AzureApiBuilder().setBaseURL(BaseURL.GLOBAL).setSubscriptionKey("test").setSubscriptionRegion("test").setOkHttpClient(new OkHttpClient()).build();

        CompletableFuture<Optional<Collection<Language>>> languages = api.getAvailableLanguages( new AvailableLanguagesParams().setSourceLanguage("en"));
        languages.whenComplete((s, throwable) -> Assertions.assertNull(throwable));
        Assertions.assertNotNull(languages.join());
        api.getThreadPool().getExecutorService().shutdown();
    }

    @Test
    @DisplayName("Get languages with empty key")
    void getLanguagesEmptyKey() {
        api = new AzureApiBuilder().setBaseURL(BaseURL.GLOBAL).setSubscriptionKey("").setSubscriptionRegion("test").setOkHttpClient(new OkHttpClient()).build();

        CompletableFuture<Optional<Collection<Language>>> languages = api.getAvailableLanguages(new AvailableLanguagesParams());
        Assertions.assertDoesNotThrow(languages::join);
        api.getThreadPool().getExecutorService().shutdown();
    }

    @Test
    @DisplayName("Get languages with empty source language")
    void getLanguagesEmptySourceLanguage() {
        api = new AzureApiBuilder().setBaseURL(BaseURL.GLOBAL).setSubscriptionKey("test").setSubscriptionRegion("test").setOkHttpClient(new OkHttpClient()).build();

        CompletableFuture<Optional<Collection<Language>>> languages = api.getAvailableLanguages(new AvailableLanguagesParams());
        Assertions.assertDoesNotThrow(languages::join);
        api.getThreadPool().getExecutorService().shutdown();
    }

    @Test
    @DisplayName("Translate with empty key")
    void translateEmptyKey() {
        api = new AzureApiBuilder().setBaseURL(BaseURL.GLOBAL).setSubscriptionKey("").setSubscriptionRegion("test").setOkHttpClient(new OkHttpClient()).build();

        TranslateParams params = new TranslateParams("test").setSourceLanguage("en").setTargetLanguages("pt");
        CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
        Assertions.assertThrows(CompletionException.class, translation::join);
        api.getThreadPool().getExecutorService().shutdown();
    }

    @Test
    @DisplayName("Translate with empty text")
    void translateEmptyText() {
        api = new AzureApiBuilder().setBaseURL(BaseURL.GLOBAL).setSubscriptionKey("test").setOkHttpClient(new OkHttpClient()).build();

        TranslateParams params = new TranslateParams("").setSourceLanguage("en").setTargetLanguages("pt");
        CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
        Optional<TranslationResponse> tr = translation.join();
        tr.ifPresent(translations -> Assertions.assertEquals(0, translations.getTranslations().size()));
        api.getThreadPool().getExecutorService().shutdown();
    }

    @Test
    @DisplayName("Translate with empty source language")
    void translateEmptySourceLanguage() {
        api = new AzureApiBuilder().setBaseURL(BaseURL.GLOBAL).setSubscriptionKey("test").setOkHttpClient(new OkHttpClient()).build();

        TranslateParams params = new TranslateParams("").setTargetLanguages("pt");
        CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
        Optional<TranslationResponse> tr = translation.join();
        tr.ifPresent(translations -> Assertions.assertEquals(0, translations.getTranslations().size()));
        api.getThreadPool().getExecutorService().shutdown();
    }

}
