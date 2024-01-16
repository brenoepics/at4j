package com.github.brenoepics.at4j.data.request;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetectLanguageParamsTest {

		@Test
		void getText_returnsText() {
				DetectLanguageParams params = new DetectLanguageParams("test text");
				assertEquals("test text", params.getText());
		}

		@Test
		void getBody_returnsNullForEmptyText() {
				DetectLanguageParams params = new DetectLanguageParams("");
				assertNull(params.getBody());
		}

		@Test
		void getBody_returnsNullForNullText() {
				DetectLanguageParams params = new DetectLanguageParams(null);
				assertNull(params.getBody());
		}

		@Test
		void getBody_returnsArrayNodeForNonEmptyText() {
				DetectLanguageParams params = new DetectLanguageParams("test text");
				ArrayNode body = params.getBody();

				assertNotNull(body);
				assertEquals(1, body.size());

				ObjectNode textNode = (ObjectNode) body.get(0);
				assertEquals("test text", textNode.get("Text").asText());
		}
}