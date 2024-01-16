package com.github.brenoepics.at4j.data.response;

import com.github.brenoepics.at4j.data.DetectedLanguage;
import com.github.brenoepics.at4j.data.Translation;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TranslationResponseTest {

		@Test
		void createsTranslationResponseWithDetectedLanguageAndTranslations() {
				DetectedLanguage detectedLanguage = new DetectedLanguage("en", 1.0f);
				Translation translation = new Translation("pt", "Olá, mundo!");
				TranslationResponse response = new TranslationResponse(detectedLanguage, List.of(translation));

				assertEquals(detectedLanguage, response.getDetectedLanguage());
				assertEquals(1, response.getTranslations().size());
				assertEquals(translation, response.getTranslations().iterator().next());
		}

		@Test
		void createsTranslationResponseWithTranslationsOnly() {
				Translation translation = new Translation("pt", "Olá, mundo!");
				TranslationResponse response = new TranslationResponse(List.of(translation));

				assertNull(response.getDetectedLanguage());
				assertEquals(1, response.getTranslations().size());
				assertEquals(translation, response.getTranslations().iterator().next());
		}

		@Test
		void setsDetectedLanguageAfterCreation() {
				DetectedLanguage detectedLanguage = new DetectedLanguage("en", 1.0f);
				TranslationResponse response = new TranslationResponse(Collections.emptyList());

				assertNull(response.getDetectedLanguage());

				response.setDetectedLanguage(detectedLanguage);

				assertEquals(detectedLanguage, response.getDetectedLanguage());
		}

		@Test
		void returnsEmptyTranslationsWhenNoneProvided() {
				TranslationResponse response = new TranslationResponse(Collections.emptyList());
				assertEquals(0, response.getTranslations().size());
		}
}