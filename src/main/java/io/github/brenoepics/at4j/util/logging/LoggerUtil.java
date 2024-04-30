package io.github.brenoepics.at4j.util.logging;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.ProviderUtil;

/** This class is used to get a {@link Logger} instance. */
public class LoggerUtil {
  LoggerUtil() {
    throw new UnsupportedOperationException();
  }

  private static final AtomicReference<Boolean> initialized = new AtomicReference<>(false);
  private static final AtomicBoolean noLogger = new AtomicBoolean();
  private static final Map<String, Logger> loggers = new ConcurrentHashMap<>();

  /**
   * Get or create a logger with the given name.
   *
   * @param name The name of the logger.
   * @return The logger with the given name.
   */
  public static Logger getLogger(String name) {
    AtomicBoolean logWarning = new AtomicBoolean(false);
    initialized.updateAndGet(
        initialized -> {
          if (Boolean.TRUE.equals(!initialized) && !ProviderUtil.hasProviders()) {
            noLogger.set(true);
            logWarning.set(true);
          }
          return true;
        });

    if (noLogger.get()) {
      return loggers.computeIfAbsent(
          name,
          key -> {
            Level debugLevel =
                FallbackLoggerConfiguration.isDebugEnabled() ? Level.DEBUG : Level.INFO;
            Level level = FallbackLoggerConfiguration.isTraceEnabled() ? Level.TRACE : debugLevel;
            Logger logger =
                new SimpleLogger(
                    name,
                    level,
                    true,
                    false,
                    true,
                    true,
                    "yyyy-MM-dd HH:mm:ss.SSSZ",
                    null,
                    new PropertiesUtil(new Properties()),
                    System.out);
            if (logWarning.get()) {
              logger.info(
                  "No Log4j2 compatible logger was found. Using default AT4J implementation!");
            }
            return new PrivacyProtectionLogger(logger);
          });
    } else {
      return new PrivacyProtectionLogger(LogManager.getLogger(name));
    }
  }

  /**
   * Gets or creates a logger for the given name.
   *
   * @param clazz The class of the logger.
   * @return A logger for the given class.
   */
  public static Logger getLogger(Class<?> clazz) {
    return getLogger(clazz.getName());
  }
}
