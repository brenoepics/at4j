package io.github.brenoepics.at4j.core.thread;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/** This class creates and contains thread pools which are used by AT4J. */
public interface ThreadPool {

  /**
   * Gets the used executor service.
   *
   * @return The used executor service.
   */
  ExecutorService getExecutorService();

  /**
   * Shutdowns the thread pool.
   *
   */
  void shutdown();
}
