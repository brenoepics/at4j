package com.github.brenoepics.at4j.azure.lang;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {

		private Language language;

		@BeforeEach
		void setUp() {
				language = new Language("en", "English", "English", LanguageDirection.LTR);
		}

		@Test
		void ofJSON_createsLanguageFromJson() {
				ObjectNode json = JsonNodeFactory.instance.objectNode();
				json.put("name", "English");
				json.put("nativeName", "English");
				json.put("dir", "LTR");

				Language result = Language.ofJSON("en", json);

				assertEquals("en", result.getCode());
				assertEquals("English", result.getName());
				assertEquals("English", result.getNativeName());
				assertEquals(LanguageDirection.LTR, result.getDir());
		}

		@Test
		void toString_returnsCorrectFormat() {
				String expected = "Language{code='en', name='English', nativeName='English', direction=LTR}";
				assertEquals(expected, language.toString());
		}

		@Test
		void getCode_returnsCorrectCode() {
				assertEquals("en", language.getCode());
		}

		@Test
		void setCode_updatesCode() {
				language.setCode("fr");
				assertEquals("fr", language.getCode());
		}

		@Test
		void getName_returnsCorrectName() {
				assertEquals("English", language.getName());
		}

		@Test
		void setName_updatesName() {
				language.setName("French");
				assertEquals("French", language.getName());
		}

		@Test
		void getNativeName_returnsCorrectNativeName() {
				assertEquals("English", language.getNativeName());
		}

		@Test
		void setNativeName_updatesNativeName() {
				language.setNativeName("Anglais");
				assertEquals("Anglais", language.getNativeName());
		}

		@Test
		void getDir_returnsCorrectDirection() {
				assertEquals(LanguageDirection.LTR, language.getDir());
		}

		@Test
		void setDir_updatesDirection() {
				language.setDir(LanguageDirection.RTL);
				assertEquals(LanguageDirection.RTL, language.getDir());
		}
}