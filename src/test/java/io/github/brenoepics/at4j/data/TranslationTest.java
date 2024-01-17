package io.github.brenoepics.at4j.data;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslationTest {

		@Test
		void from_createsTranslationWithGivenParameters() {
				Translation translation = Translation.from("en", "Hello");

				assertEquals("en", translation.getLanguageCode());
				assertEquals("Hello", translation.getText());
		}

		@Test
		void ofJSON_createsTranslationFromValidNode() {
				ObjectNode node = JsonNodeFactory.instance.objectNode();
				node.put("to", "en");
				node.put("text", "Hello");

				Translation translation = Translation.ofJSON(node);

				assertNotNull(translation);
				assertEquals("en", translation.getLanguageCode());
				assertEquals("Hello", translation.getText());
		}

		@Test
		void ofJSON_returnsNullForInvalidNode() {
				ObjectNode node = JsonNodeFactory.instance.objectNode();
				node.put("invalid", "data");

				Translation translation = Translation.ofJSON(node);

				assertNull(translation);
		}

		@Test
		void toString_returnsCorrectFormat() {
				Translation translation = Translation.from("en", "Hello");

				String expected = "Translation{key='en', value='Hello'}";
				assertEquals(expected, translation.toString());
		}
}