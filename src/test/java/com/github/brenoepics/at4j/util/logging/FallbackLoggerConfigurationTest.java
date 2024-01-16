package com.github.brenoepics.at4j.util.logging;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FallbackLoggerConfigurationTest {

  @Test
  void debugInitiallyEnabled() {
    assertTrue(FallbackLoggerConfiguration.isDebugEnabled());
  }

  @Test
  void traceInitiallyDisabled() {
    assertFalse(FallbackLoggerConfiguration.isTraceEnabled());
  }

  @Test
  void enablingDebugEnablesDebugLogging() {
    FallbackLoggerConfiguration.setDebug(true);
    assertTrue(FallbackLoggerConfiguration.isDebugEnabled());
    FallbackLoggerConfiguration.setDebug(false); // reset for other tests
  }

  @Test
  void disablingDebugDisablesTraceLogging() {
    FallbackLoggerConfiguration.setTrace(true);
    FallbackLoggerConfiguration.setDebug(false);
    assertFalse(FallbackLoggerConfiguration.isTraceEnabled());
    FallbackLoggerConfiguration.setTrace(false); // reset for other tests
  }

  @Test
  void enablingTraceEnablesDebugLogging() {
    FallbackLoggerConfiguration.setTrace(true);
    assertTrue(FallbackLoggerConfiguration.isDebugEnabled());
    FallbackLoggerConfiguration.setTrace(false); // reset for other tests
  }

  @Test
  void disablingTraceDoesNotDisableDebugLogging() {
    FallbackLoggerConfiguration.setDebug(true);
    FallbackLoggerConfiguration.setTrace(false);
    assertTrue(FallbackLoggerConfiguration.isDebugEnabled());
    FallbackLoggerConfiguration.setDebug(false); // reset for other tests
  }
}
