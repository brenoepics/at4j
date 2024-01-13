package com.github.brenoepics.at4j.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.brenoepics.at4j.AzureApi;
import com.github.brenoepics.at4j.azure.BaseURL;
import com.github.brenoepics.at4j.azure.lang.Language;
import com.github.brenoepics.at4j.core.ratelimit.RateLimitManager;
import com.github.brenoepics.at4j.core.thread.ThreadPool;
import com.github.brenoepics.at4j.core.thread.ThreadPoolImpl;
import com.github.brenoepics.at4j.data.DetectedLanguage;
import com.github.brenoepics.at4j.data.Translation;
import com.github.brenoepics.at4j.data.request.AvailableLanguagesParams;
import com.github.brenoepics.at4j.data.request.DetectLanguageParams;
import com.github.brenoepics.at4j.data.request.TranslateParams;
import com.github.brenoepics.at4j.data.response.TranslationResponse;
import com.github.brenoepics.at4j.util.rest.RestEndpoint;
import com.github.brenoepics.at4j.util.rest.RestMethod;
import com.github.brenoepics.at4j.util.rest.RestRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import okhttp3.OkHttpClient;

/**
 * This class is an implementation of the AzureApi interface. It provides methods to interact with
 * Azure's translation API.
 */
public class AzureApiImpl implements AzureApi {

  /** The Http Client for this instance. */
  private final OkHttpClient httpClient;

  /** The BaseURL for this instance. */
  private final BaseURL baseURL;

  /** The subscription key for this instance. */
  private final String subscriptionKey;

  /** The subscription region for this instance. */
  private final String subscriptionRegion;

  /** The object mapper for this instance. */
  private final ObjectMapper objectMapper = new ObjectMapper();

  /** The ratelimit manager for this resource. */
  private final RateLimitManager ratelimitManager = new RateLimitManager(this);

  /** The thread pool which is used internally. */
  private final ThreadPoolImpl threadPool = new ThreadPoolImpl();

  /**
   * Constructor for AzureApiImpl.
   *
   * @param httpClient The Http Client for this instance.
   * @param baseURL The BaseURL for this instance.
   * @param subscriptionKey The subscription key for this instance.
   * @param subscriptionRegion The subscription region for this instance.
   */
  public AzureApiImpl(
      OkHttpClient httpClient, BaseURL baseURL, String subscriptionKey, String subscriptionRegion) {
    this.httpClient = httpClient;
    this.baseURL = baseURL;
    this.subscriptionKey = subscriptionKey;
    this.subscriptionRegion = subscriptionRegion;
  }

  @Override
  public BaseURL getBaseURL() {
    return this.baseURL;
  }

  @Override
  public String getSubscriptionKey() {
    return this.subscriptionKey;
  }

  @Override
  public Optional<String> getSubscriptionRegion() {
    return Optional.ofNullable(this.subscriptionRegion);
  }

  @Override
  public ThreadPool getThreadPool() {
    return threadPool;
  }

  @Override
  public CompletableFuture<Optional<TranslationResponse>> translate(TranslateParams params) {
    if (params.getText() == null || params.getText().isEmpty()) {
      return CompletableFuture.completedFuture(Optional.empty());
    }

    RestRequest<Optional<TranslationResponse>> request =
        new RestRequest<Optional<TranslationResponse>>(
                this, RestMethod.POST, RestEndpoint.TRANSLATE)
            .setBody(params.getBody());
    params.getQueryParameters().forEach(request::addQueryParameter);
    params.getTargetLanguages().forEach(lang -> request.addQueryParameter("to", lang));

    return request.execute(
        response -> {
          if (response.getJsonBody().isNull()
              || !response.getJsonBody().has(0)
              || !response.getJsonBody().get(0).has("translations")) return Optional.empty();

          JsonNode jsonNode = response.getJsonBody().get(0);
          Collection<Translation> translations = new ArrayList<>();
          jsonNode
              .get("translations")
              .forEach(node -> translations.add(Translation.ofJSON((ObjectNode) node)));

          TranslationResponse translationResponse = new TranslationResponse(translations);
          if (jsonNode.has("detectedLanguage")) {
            JsonNode detectedLanguage = jsonNode.get("detectedLanguage");
            translationResponse.setDetectedLanguage(
                DetectedLanguage.ofJSON((ObjectNode) detectedLanguage));
          }

          return Optional.of(translationResponse);
        });
  }

  @Override
  public CompletableFuture<Optional<DetectedLanguage>> detectLanguage(DetectLanguageParams params) {
    if (params.getText() == null || params.getText().isEmpty()) {
      return CompletableFuture.completedFuture(Optional.empty());
    }

    RestRequest<Optional<DetectedLanguage>> request =
        new RestRequest<Optional<DetectedLanguage>>(this, RestMethod.POST, RestEndpoint.DETECT)
            .setBody(params.getBody());

    return request.execute(
        response -> {
          if (response.getJsonBody().isNull()
              || !response.getJsonBody().has(0)
              || !response.getJsonBody().get(0).has("language")) return Optional.empty();

          JsonNode jsonNode = response.getJsonBody().get(0);
          if (!jsonNode.isObject()) return Optional.empty();

          return Optional.of(DetectedLanguage.ofJSON((ObjectNode) jsonNode));
        });
  }

  @Override
  public CompletableFuture<Optional<Collection<Language>>> getAvailableLanguages(
      AvailableLanguagesParams params) {
    RestRequest<Optional<Collection<Language>>> request =
        new RestRequest<Optional<Collection<Language>>>(
                this, RestMethod.GET, RestEndpoint.LANGUAGES)
            .addQueryParameter("scope", params.getScope())
            .includeAuthorizationHeader(false);

    if (params.getSourceLanguage() != null) {
      request.addHeader("Accept-Language", params.getSourceLanguage());
    }

    return request.execute(
        response -> {
          if (response.getJsonBody().isNull() || !response.getJsonBody().has("translation"))
            return Optional.empty();

          Collection<Language> languages = new ArrayList<>();
          JsonNode jsonNode = response.getJsonBody().get("translation");
          jsonNode
              .fieldNames()
              .forEachRemaining(
                  key -> {
                    Language language = Language.ofJSON(key, (ObjectNode) jsonNode.get(key));
                    languages.add(language);
                  });

          return Optional.of(languages);
        });
  }

  /**
   * Gets the used OkHttpClient.
   *
   * @return OkHttpClient - The used OkHttpClient.
   */
  public OkHttpClient getHttpClient() {
    return this.httpClient;
  }

  /**
   * Gets the used ObjectMapper.
   *
   * @return ObjectMapper - The used ObjectMapper.
   */
  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  /**
   * Gets the used RateLimitManager.
   *
   * @return RateLimitManager - The used RateLimitManager.
   */
  public RateLimitManager getRatelimitManager() {
    return ratelimitManager;
  }
}
