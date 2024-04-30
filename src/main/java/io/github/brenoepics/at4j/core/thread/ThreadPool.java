package io.github.brenoepics.at4j.core.thread;

import java.util.concurrent.ExecutorService;

/** This class creates and contains thread pools which are used by AT4J. */
public interface ThreadPool {

  /**
   * Gets the used executor service.
   *
   * @return The used executor service.
   */
  ExecutorService getExecutorService();

  /** Shutdowns the thread pool. */
  void shutdown();
}
