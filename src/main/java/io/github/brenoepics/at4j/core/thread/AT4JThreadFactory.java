package io.github.brenoepics.at4j.core.thread;

import java.util.concurrent.atomic.AtomicInteger;

/** A thread factory that creates optionally numbered threads as daemon or non-daemon. */
public class AT4JThreadFactory implements java.util.concurrent.ThreadFactory {

  /** The numbering counter. */
  private final AtomicInteger counter = new AtomicInteger();

  /** The name pattern. */
  private final String namePattern;

  /** Whether to create daemon threads. */
  private final boolean daemon;

  /**
   * Creates a new thread factory.
   *
   * @param namePattern The name pattern may contain a {@code %d} wildcard where the counter gets
   *     filled in.
   * @param daemon Whether to create daemon or non-daemon threads.
   */
  public AT4JThreadFactory(String namePattern, boolean daemon) {
    this.namePattern = namePattern;
    this.daemon = daemon;
  }

  @Override
  public Thread newThread(Runnable r) {
    Thread thread = new Thread(r, String.format(namePattern, counter.incrementAndGet()));
    thread.setDaemon(daemon);
    return thread;
  }
}
