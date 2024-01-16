package io.github.brenoepics.at4j.util.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PrivacyProtectionLoggerTest {
  private AbstractLogger delegate;
  private PrivacyProtectionLogger privacyProtectionLogger;

  @BeforeEach
  public void setup() {
    delegate = Mockito.mock(AbstractLogger.class);
    privacyProtectionLogger = new PrivacyProtectionLogger(delegate);
  }

  @Test
  void shouldLogMessageWithPrivateDataReplaced() {
    Marker marker = Mockito.mock(Marker.class);
    String privateData = "privateData";
    String message = "This is a test message with " + privateData;
    Exception throwable = new NullPointerException("");
    PrivacyProtectionLogger.addPrivateData(privateData);
    privacyProtectionLogger.logMessage(
        null, Level.INFO, marker, new SimpleMessage(message), throwable);
    verify(delegate).log(Level.INFO, marker, "This is a test message with **********", throwable);
  }

  @Test
  void shouldDelegateIsEnabledCalls() {
    Marker marker = Mockito.mock(Marker.class);
    when(delegate.isEnabled(Level.INFO, marker)).thenReturn(true);
    assertTrue(privacyProtectionLogger.isEnabled(Level.INFO, marker, "message"));
  }
}
