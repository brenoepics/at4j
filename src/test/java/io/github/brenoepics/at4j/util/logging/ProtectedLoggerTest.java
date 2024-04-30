package io.github.brenoepics.at4j.util.logging;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

class ProtectedLoggerTest {
  private Logger delegate;
  private ProtectedLogger protectedLogger;

  @BeforeEach
  public void setup() {
    delegate = Mockito.mock(Logger.class);
    protectedLogger = new ProtectedLogger(delegate);
  }

  @Test
  void logMessageTest() {
    String privateData = "secret";
    ProtectedLogger.addPrivateData(privateData);
    String message = "This is a secret message";
    Marker marker = Mockito.mock(Marker.class);
    protectedLogger.handleNormalizedLoggingCall(Level.INFO, marker, message, null, null);
    verify(delegate).info(eq(marker), eq("This is a ********** message"), isNull(), isNull());
  }

  @Test
  void logTraceMessageTest() {
    String privateData = "secret";
    ProtectedLogger.addPrivateData(privateData);
    String message = "This is a secret trace message";
    Marker marker = Mockito.mock(Marker.class);
    protectedLogger.handleNormalizedLoggingCall(Level.TRACE, marker, message, null, null);
    verify(delegate).trace(eq(marker), eq("This is a ********** trace message"), isNull(), isNull());
  }

  @Test
  void logDebugMessageTest() {
    String privateData = "secret";
    ProtectedLogger.addPrivateData(privateData);
    String message = "This is a secret debug message";
    Marker marker = Mockito.mock(Marker.class);
    protectedLogger.handleNormalizedLoggingCall(Level.DEBUG, marker, message, null, null);
    verify(delegate).debug(eq(marker), eq("This is a ********** debug message"), isNull(), isNull());
  }

  @Test
  void logWarnMessageTest() {
    String privateData = "secret";
    ProtectedLogger.addPrivateData(privateData);
    String message = "This is a secret warn message";
    Marker marker = Mockito.mock(Marker.class);
    protectedLogger.handleNormalizedLoggingCall(Level.WARN, marker, message, null, null);
    verify(delegate).warn(eq(marker), eq("This is a ********** warn message"), isNull(), isNull());
  }

  @Test
  void logErrorMessageTest() {
    String privateData = "secret";
    ProtectedLogger.addPrivateData(privateData);
    String message = "This is a secret error message";
    Marker marker = Mockito.mock(Marker.class);
    protectedLogger.handleNormalizedLoggingCall(Level.ERROR, marker, message, null, null);
    verify(delegate).error(eq(marker), eq("This is a ********** error message"), isNull(), isNull());
  }

  @Test
  void logMessageWithoutPrivateDataTest() {
    String message = "This is a regular message";
    Marker marker = Mockito.mock(Marker.class);
    protectedLogger.handleNormalizedLoggingCall(Level.INFO, marker, message, null, null);
    verify(delegate).info(eq(marker), eq("This is a regular message"), isNull(), isNull());
  }
}
