package io.github.brenoepics.at4j.util.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrivacyProtectionLoggerTest {
  private Logger delegate;
  private PrivacyProtectionLogger privacyProtectionLogger;

  @BeforeEach
  public void setup() {
    delegate = Mockito.mock(Logger.class);
    privacyProtectionLogger = new PrivacyProtectionLogger(delegate);
  }

  @Test
  void logMessageTest() {
    String privateData = "secret";
    PrivacyProtectionLogger.addPrivateData(privateData);
    Message message = new SimpleMessage("This is a secret message");
    Marker marker = MarkerManager.getMarker("TEST");
    privacyProtectionLogger.logMessage(
        PrivacyProtectionLoggerTest.class.getName(), Level.INFO, marker, message, null);
    verify(delegate)
        .log(eq(Level.INFO), eq(marker), eq("This is a ********** message"), (Throwable) isNull());
  }

  @Test
  void isEnabledTest() {
    Marker marker = MarkerManager.getMarker("TEST");
    when(delegate.isEnabled(Level.INFO, marker)).thenReturn(true);
    assertTrue(privacyProtectionLogger.isEnabled(Level.INFO, marker, "Test message"));
  }
}
