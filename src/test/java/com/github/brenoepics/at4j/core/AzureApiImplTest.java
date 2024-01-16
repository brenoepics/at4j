package com.github.brenoepics.at4j.core;

import com.github.brenoepics.at4j.AzureApiBuilder;
import com.github.brenoepics.at4j.core.exceptions.AzureException;
import com.github.brenoepics.at4j.data.request.TranslateParams;
import com.github.brenoepics.at4j.data.response.TranslationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class AzureApiImplTest<T> {

  @Mock private AzureApiImpl<T> azureApi;
  private TranslateParams translateParams;

  @BeforeEach
  public void setup() {
    azureApi = (AzureApiImpl<T>) new AzureApiBuilder().setKey("testKey").build();
    translateParams =
        new TranslateParams("Hello", Collections.singleton("pt")).setSourceLanguage("en");
  }


  @Test
  void returnsEmptyOnInvalidInput() {
    translateParams.setText(null);
    azureApi.translate(translateParams).whenComplete((response, throwable) -> {
      if (throwable != null) {
        assertTrue(throwable instanceof AzureException);
        assertEquals("Text is required", throwable.getMessage());
      }
    });

    CompletableFuture<Optional<TranslationResponse>> response = azureApi.translate(translateParams);

    assertFalse(response.join().isPresent());
  }
}
