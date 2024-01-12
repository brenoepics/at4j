package com.github.brenoepics.at4j;

import org.junit.Test;

import java.lang.reflect.Field;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AT4JTest {

    @Test
    public void testVersionFields() throws NoSuchFieldException, IllegalAccessException {
        Field versionField = AT4J.class.getDeclaredField("VERSION");
        versionField.setAccessible(true);
        String version = (String) versionField.get(null);
        assertNotNull(version);

        Field commitIdField = AT4J.class.getDeclaredField("COMMIT_ID");
        commitIdField.setAccessible(true);
        String commitId = (String) commitIdField.get(null);
        assertNotNull(commitId);

        Field displayVersionField = AT4J.class.getDeclaredField("DISPLAY_VERSION");
        displayVersionField.setAccessible(true);
        String displayVersion = (String) displayVersionField.get(null);
        assertNotNull(displayVersion);

    }

    @Test
    public void testAzureTranslatorApiVersion() {
        assertEquals("3.0", AT4J.AZURE_TRANSLATOR_API_VERSION);
    }
}