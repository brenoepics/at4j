package tech.brenoepic.at4j.core.thread;

import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Supplier;

/** The implementation of {@link ThreadPool}. */
public class ThreadPoolImpl implements ThreadPool {

  private static final int CORE_POOL_SIZE = 1;
  private static final int MAXIMUM_POOL_SIZE = Integer.MAX_VALUE;
  private static final int KEEP_ALIVE_TIME = 60;
  private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

  private final ExecutorService executorService =
      new ThreadPoolExecutor(
          CORE_POOL_SIZE,
          MAXIMUM_POOL_SIZE,
          KEEP_ALIVE_TIME,
          TIME_UNIT,
          new SynchronousQueue<>(),
          new AT4JThreadFactory("AT4J - Central ExecutorService - %d", false));
  private final ScheduledExecutorService scheduler =
      Executors.newScheduledThreadPool(
          CORE_POOL_SIZE, new AT4JThreadFactory("AT4J - Central Scheduler - %d", false));
  private final ScheduledExecutorService daemonScheduler =
      Executors.newScheduledThreadPool(
          CORE_POOL_SIZE, new AT4JThreadFactory("AT4J - Central Daemon Scheduler - %d", true));
  private final ConcurrentHashMap<String, ExecutorService> executorServiceSingleThreads =
      new ConcurrentHashMap<>();

  /** Shutdowns the thread pool. */
  public void shutdown() {
    executorService.shutdown();
    scheduler.shutdown();
    daemonScheduler.shutdown();
    executorServiceSingleThreads.values().forEach(ExecutorService::shutdown);
  }

  @Override
  public ExecutorService getExecutorService() {
    return executorService;
  }

  @Override
  public ScheduledExecutorService getScheduler() {
    return scheduler;
  }

  @Override
  public ScheduledExecutorService getDaemonScheduler() {
    return daemonScheduler;
  }

  @Override
  public ExecutorService getSingleThreadExecutorService(String threadName) {
    return executorServiceSingleThreads.computeIfAbsent(
        threadName,
        key ->
            new ThreadPoolExecutor(
                0,
                1,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                new LinkedBlockingQueue<>(),
                new AT4JThreadFactory("AT4J - " + threadName, false)));
  }

  @Override
  public ExecutorService getSingleDaemonThreadExecutorService(String threadName) {
    return executorServiceSingleThreads.computeIfAbsent(
        threadName,
        key ->
            new ThreadPoolExecutor(
                0,
                1,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                new LinkedBlockingQueue<>(),
                new AT4JThreadFactory("AT4J - " + threadName, true)));
  }

  @Override
  public Optional<ExecutorService> removeAndShutdownSingleThreadExecutorService(String threadName) {
    ExecutorService takenExecutorService = executorServiceSingleThreads.remove(threadName);
    if (takenExecutorService != null) {
      takenExecutorService.shutdown();
    }
    return Optional.ofNullable(takenExecutorService);
  }

  @Override
  public <T> CompletableFuture<T> runAfter(
      Supplier<CompletableFuture<T>> task, long duration, TimeUnit unit) {
    CompletableFuture<T> future = new CompletableFuture<>();
    getDaemonScheduler()
        .schedule(
            () ->
                task.get()
                    .whenComplete(
                        (result, ex) -> {
                          if (ex != null) {
                            future.completeExceptionally(ex);
                          } else {
                            future.complete(result);
                          }
                        }),
            duration,
            unit);
    return future;
  }
}
