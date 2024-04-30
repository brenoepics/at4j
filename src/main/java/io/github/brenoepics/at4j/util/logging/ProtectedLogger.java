package io.github.brenoepics.at4j.util.logging;

import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.AbstractLogger;

/**
 * This logger is used to wrap another logger and replace configured sensitive data by asterisks.
 */
public class ProtectedLogger extends AbstractLogger {
  private static final long serialVersionUID = 91095837261631L;
  private static final String PRIVATE_DATA_REPLACEMENT = "**********";
  private static final Set<String> privateDataSet = new HashSet<>();

  private final transient Logger delegate;

  /**
   * Class constructor. It's recommended to use {@link LoggerUtil#getLogger(String)}.
   *
   * @param delegate The delegate logger that gets the cleaned messages.
   */
  ProtectedLogger(Logger delegate) {
    this.delegate = delegate;
  }

  /**
   * Adds private data to be asterisked out in log messages. A {@code null} argument is simply
   * ignored.
   *
   * @param privateData The private data.
   */
  public static void addPrivateData(String privateData) {
    if (privateData != null && !privateData.trim().isEmpty()) {
      privateDataSet.add(privateData);
    }
  }

  @Override
  public String getName() {
    return delegate.getName();
  }

  @Override
  public boolean isTraceEnabled() {
    return delegate.isTraceEnabled();
  }

  @Override
  public boolean isTraceEnabled(Marker marker) {
    return delegate.isTraceEnabled(marker);
  }

  @Override
  public boolean isDebugEnabled() {
    return delegate.isDebugEnabled();
  }

  @Override
  public boolean isDebugEnabled(Marker marker) {
    return delegate.isDebugEnabled(marker);
  }

  @Override
  public boolean isInfoEnabled() {
    return delegate.isInfoEnabled();
  }

  @Override
  public boolean isInfoEnabled(Marker marker) {
    return delegate.isInfoEnabled(marker);
  }

  @Override
  public boolean isWarnEnabled() {
    return delegate.isWarnEnabled();
  }

  @Override
  public boolean isWarnEnabled(Marker marker) {
    return delegate.isWarnEnabled(marker);
  }

  @Override
  public boolean isErrorEnabled() {
    return delegate.isErrorEnabled();
  }

  @Override
  public boolean isErrorEnabled(Marker marker) {
    return delegate.isErrorEnabled(marker);
  }

  @Override
  protected String getFullyQualifiedCallerName() {
    return delegate.getName();
  }

  @Override
  protected void handleNormalizedLoggingCall(
      Level level, Marker marker, String message, Object[] objects, Throwable t) {
    if (privateDataSet.stream().noneMatch(message::contains)) {
      log(level, marker, objects, t, message);
      return;
    }

    String replacedMessage = message;
    for (String privateData : privateDataSet) {
      if (message.contains(privateData)) {
        replacedMessage = replacedMessage.replace(privateData, PRIVATE_DATA_REPLACEMENT);
      }
    }

    log(level, marker, objects, t, replacedMessage);
  }

  private void log(Level level, Marker marker, Object[] objects, Throwable t, String message) {
    switch (level) {
      case TRACE:
        delegate.trace(marker, message, objects, t);
        break;
      case DEBUG:
        delegate.debug(marker, message, objects, t);
        break;
      case INFO:
        delegate.info(marker, message, objects, t);
        break;
      case WARN:
        delegate.warn(marker, message, objects, t);
        break;
      case ERROR:
        delegate.error(marker, message, objects, t);
        break;
    }
  }
}
