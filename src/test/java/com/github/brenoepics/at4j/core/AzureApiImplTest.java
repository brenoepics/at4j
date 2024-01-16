package com.github.brenoepics.at4j.core;

import com.github.brenoepics.at4j.data.Translation;
import com.github.brenoepics.at4j.data.request.TranslateParams;
import com.github.brenoepics.at4j.data.response.TranslationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AzureApiImplTest<T> {

  @Mock private AzureApiImpl<T> azureApi;
  private TranslateParams translateParams;

  @BeforeEach
  public void setup() {
    translateParams =
        new TranslateParams("Hello", Collections.singleton("pt")).setSourceLanguage("en");
  }

  @Test
  void returnsTranslationOnValidInput() {
    TranslationResponse expectedResponse =
        new TranslationResponse(Collections.singleton(new Translation("pt", "Olá")));
    when(azureApi.translate(any(TranslateParams.class)))
        .thenReturn(CompletableFuture.completedFuture(Optional.of(expectedResponse)));

    CompletableFuture<Optional<TranslationResponse>> response = azureApi.translate(translateParams);

    assertTrue(response.join().isPresent());
    assertEquals(expectedResponse, response.join().get());
  }

  @Test
  void returnsEmptyOnInvalidInput() {
    translateParams.setText(null);
    when(azureApi.translate(any(TranslateParams.class)))
        .thenReturn(CompletableFuture.completedFuture(Optional.empty()));

    CompletableFuture<Optional<TranslationResponse>> response = azureApi.translate(translateParams);

    assertFalse(response.join().isPresent());
  }
}
