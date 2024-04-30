package io.github.brenoepics.at4j.util.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** This class is used to get a {@link Logger} instance. */
public class LoggerUtil {
    LoggerUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get or create a logger with the given name.
     *
     * @param name The name of the logger.
     * @return The logger with the given name.
     */
    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    /**
     * Gets or creates a logger for the given class.
     *
     * @param clazz The class of the logger.
     * @return A logger for the given class.
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}