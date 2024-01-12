package com.github.brenoepics.at4j.core;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.brenoepics.at4j.data.Translation;
import com.github.brenoepics.at4j.data.request.TranslateParams;
import com.github.brenoepics.at4j.data.response.TranslationResponse;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AzureApiImplTest {

  private AzureApiImpl azureApi;
  private TranslateParams translateParams;

  @BeforeEach
  public void setup() {
    azureApi = Mockito.mock(AzureApiImpl.class);
    translateParams = new TranslateParams("Hello").setSourceLanguage("en").setTargetLanguages("pt");
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
