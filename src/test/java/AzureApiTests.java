import okhttp3.OkHttpClient;
import org.junit.Test;
import tech.brenoepic.at4j.AzureApi;
import tech.brenoepic.at4j.AzureApiBuilder;
import tech.brenoepic.at4j.azure.BaseURL;
import tech.brenoepic.at4j.azure.lang.Language;
import tech.brenoepic.at4j.data.request.AvailableLanguagesParams;
import tech.brenoepic.at4j.data.request.TranslateParams;
import tech.brenoepic.at4j.data.response.TranslationResponse;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.Assert.*;


public class AzureApiTests {

    AzureApi api;

    @Test
    public void BuildNullKey() {
        AzureApiBuilder builder = new AzureApiBuilder();
        assertThrows("Subscription key cannot be null", IllegalArgumentException.class, builder::build);
    }

    @Test
    public void BuildNullHttpClient() {
        AzureApiBuilder builder = new AzureApiBuilder().setSubscriptionKey("");
        assertThrows("HTTP client cannot be null", IllegalArgumentException.class, builder::build);
    }

    @Test
    public void buildApi() {
        api = new AzureApiBuilder().setSubscriptionKey("test").setSubscriptionRegion("test").setOkHttpClient(new OkHttpClient()).build();
        assertNotNull(api);
        api.getThreadPool().getExecutorService().shutdown();
    }


    @Test
    public void getLanguages() {
        AzureApi api = new AzureApiBuilder().setBaseURL(BaseURL.GLOBAL).setSubscriptionKey("test").setSubscriptionRegion("test").setOkHttpClient(new OkHttpClient()).build();

        CompletableFuture<Optional<Collection<Language>>> languages = api.getAvailableLanguages( new AvailableLanguagesParams().setSourceLanguage("en"));
        languages.whenComplete((s, throwable) -> assertNull(throwable));
        assertNotNull(languages.join());
        api.getThreadPool().getExecutorService().shutdown();
    }

    @Test
    public void getLanguagesEmptyKey() {
        api = new AzureApiBuilder().setBaseURL(BaseURL.GLOBAL).setSubscriptionKey("").setSubscriptionRegion("test").setOkHttpClient(new OkHttpClient()).build();

        CompletableFuture<Optional<Collection<Language>>> languages = api.getAvailableLanguages(new AvailableLanguagesParams());
        languages.whenComplete((s, throwable) -> assertNull(throwable));
        api.getThreadPool().getExecutorService().shutdown();
    }

    @Test
    public void getLanguagesEmptySourceLanguage() {
        api = new AzureApiBuilder().setBaseURL(BaseURL.GLOBAL).setSubscriptionKey("test").setSubscriptionRegion("test").setOkHttpClient(new OkHttpClient()).build();

        CompletableFuture<Optional<Collection<Language>>> languages = api.getAvailableLanguages(new AvailableLanguagesParams());
        languages.whenComplete((s, throwable) -> assertNull(throwable));
        api.getThreadPool().getExecutorService().shutdown();
    }

    @Test
    public void translateEmptyKey() {
        api = new AzureApiBuilder().setBaseURL(BaseURL.GLOBAL).setSubscriptionKey("").setSubscriptionRegion("test").setOkHttpClient(new OkHttpClient()).build();

        TranslateParams params = new TranslateParams("test").setSourceLanguage("en").setTargetLanguages("pt");
        CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
        assertThrows(CompletionException.class, translation::join);
        api.getThreadPool().getExecutorService().shutdown();
    }

    @Test
    public void translateEmptyText() {
        api = new AzureApiBuilder().setBaseURL(BaseURL.GLOBAL).setSubscriptionKey("test").setOkHttpClient(new OkHttpClient()).build();

        TranslateParams params = new TranslateParams("").setSourceLanguage("en").setTargetLanguages("pt");
        CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
        Optional<TranslationResponse> tr = translation.join();
        tr.ifPresent(translations -> assertEquals(0, translations.getTranslations().size()));
        api.getThreadPool().getExecutorService().shutdown();
    }

    @Test
    public void translateEmptySourceLanguage() {
        api = new AzureApiBuilder().setBaseURL(BaseURL.GLOBAL).setSubscriptionKey("test").setOkHttpClient(new OkHttpClient()).build();

        TranslateParams params = new TranslateParams("").setTargetLanguages("pt");
        CompletableFuture<Optional<TranslationResponse>> translation = api.translate(params);
        Optional<TranslationResponse> tr = translation.join();
        tr.ifPresent(translations -> assertEquals(0, translations.getTranslations().size()));
        api.getThreadPool().getExecutorService().shutdown();
    }

}
