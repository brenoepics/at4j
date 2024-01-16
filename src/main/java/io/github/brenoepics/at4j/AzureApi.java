package io.github.brenoepics.at4j;

import io.github.brenoepics.at4j.azure.BaseURL;
import io.github.brenoepics.at4j.azure.lang.Language;
import io.github.brenoepics.at4j.core.thread.ThreadPool;
import io.github.brenoepics.at4j.data.DetectedLanguage;
import io.github.brenoepics.at4j.data.request.AvailableLanguagesParams;
import io.github.brenoepics.at4j.data.request.DetectLanguageParams;
import io.github.brenoepics.at4j.data.request.TranslateParams;
import io.github.brenoepics.at4j.data.response.TranslationResponse;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * This class is the most important class of AT4J, as it contains the main methods for translating
 * text.
 */
public interface AzureApi {

  /**
   * The BaseURL of the Azure Translator API.
   *
   * @return BaseURL - The base URL of the Azure Translator API.
   * @see <a
   *     href="https://learn.microsoft.com/en-us/azure/ai-services/translator/reference/v3-0-reference#base-urls">Azure
   *     Domain list</a>
   */
  BaseURL getBaseURL();

  /**
   * Gets the used subscription key.
   *
   * @return String - The used subscription key.
   * @see <a
   *     href="https://learn.microsoft.com/en-us/azure/ai-services/translator/create-translator-resource#authentication-keys-and-endpoint-url">Authentication
   *     keys and endpoint URL</a>
   */
  String getSubscriptionKey();

  /**
   * Gets the used subscription region. <br>
   * Use with multi-service and regional translator resource. The value is the region of the
   * multi-service or regional translator resource. This value is optional when using a global
   * translator resource.
   *
   * @return String - The used subscription region.
   * @see <a href="https://github.com/brenoepics/at4j/main/docs/azure_datacenter_list.json">Azure
   *     Datacenter List</a>
   */
  Optional<String> getSubscriptionRegion();

  /**
   * Gets the ThreadPool.
   *
   * @return ThreadPool - The ThreadPool.
   */
  ThreadPool getThreadPool();

  /**
   * Disconnects the AzureApi. <br>
   * This method should be called when the AzureApi is no longer needed.
   *
   * <p><b>Note:</b> This method will shut down the ThreadPool. <br>
   */
  void disconnect();

  /**
   * Translates the given text from the given source language to the given target language.
   *
   * @param params The {@link TranslateParams} to translate.
   * @return The {@link TranslationResponse} containing the translation.
   */
  CompletableFuture<Optional<TranslationResponse>> translate(TranslateParams params);

  /**
   * Gets the available languages for translation.
   *
   * @param params The {@link AvailableLanguagesParams} to get the available languages.
   * @return Collection of languages - The available languages.
   * @see <a
   *     href="https://learn.microsoft.com/en-us/azure/ai-services/translator/language-support">Language
   *     Support</a>
   */
  CompletableFuture<Optional<Collection<Language>>> getAvailableLanguages(
      AvailableLanguagesParams params);

  /**
   * Detects the language of the given text. <br>
   * <b>Limitations:</b> The array can have at most 100 elements. <br>
   * The entire text included in the request can't exceed 50,000 characters including spaces.
   *
   * @param params The {@link DetectLanguageParams} to detect the language.
   * @return DetectedLanguage - The detected language.
   * @see <a
   *     href="https://learn.microsoft.com/en-us/azure/ai-services/translator/reference/v3-0-detect">Language
   *     Detection</a>
   */
  CompletableFuture<Optional<DetectedLanguage>> detectLanguage(DetectLanguageParams params);
}
