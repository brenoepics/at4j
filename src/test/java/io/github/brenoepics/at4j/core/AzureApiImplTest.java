package io.github.brenoepics.at4j.core;

import io.github.brenoepics.at4j.AzureApi;
import io.github.brenoepics.at4j.AzureApiBuilder;
import io.github.brenoepics.at4j.core.exceptions.AzureException;
import io.github.brenoepics.at4j.data.request.TranslateParams;
import io.github.brenoepics.at4j.data.response.TranslationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class AzureApiImplTest {

  @Mock private AzureApi azureApi;
  private TranslateParams translateParams;

  @BeforeEach
  public void setup() {
    azureApi = new AzureApiBuilder().setKey("testKey").build();
    translateParams =
        new TranslateParams("Hello", Collections.singleton("pt")).setSourceLanguage("en");
  }

  @Test
  void returnsEmptyOnInvalidInput() {
    translateParams.setTexts(Collections.emptyList());
    azureApi
        .translate(translateParams)
        .whenComplete(
            (response, throwable) -> {
              if (throwable != null) {
                assertInstanceOf(AzureException.class, throwable);
                assertEquals("Text is required", throwable.getMessage());
              }
            });

    CompletableFuture<Optional<TranslationResponse>> response = azureApi.translate(translateParams);

    assertFalse(response.join().isPresent());
  }
}
