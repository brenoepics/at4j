package io.github.brenoepics.at4j.core.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/** The implementation of {@link ThreadPool}. */
public class ThreadPoolImpl implements ThreadPool {

  private static final int CORE_POOL_SIZE = 1;
  private static final int MAXIMUM_POOL_SIZE = Integer.MAX_VALUE;
  private static final int KEEP_ALIVE_TIME = 60;
  private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
  private final Logger logger = LoggerFactory.getLogger(ThreadPoolImpl.class);

  private final ExecutorService executorService;

  public ThreadPoolImpl(ExecutorService executorService) {
    if (executorService == null) {
      logger.debug("Starting with default AT4J executor service.");
      this.executorService = newAt4jDefault();
      return;
    }

    logger.debug("Starting with custom AT4J executor service.");
    this.executorService = executorService;
  }

  @Override
  public void shutdown() {
    logger.debug("Shutting down AT4J executor service.");
    executorService.shutdown();
  }

  @Override
  public ExecutorService getExecutorService() {
    return executorService;
  }

  public static ExecutorService newAt4jDefault() {
    return new ThreadPoolExecutor(
        CORE_POOL_SIZE,
        MAXIMUM_POOL_SIZE,
        KEEP_ALIVE_TIME,
        TIME_UNIT,
        new SynchronousQueue<>(),
        new AT4JThreadFactory("AT4J - Central ExecutorService - %d", false));
  }
}
