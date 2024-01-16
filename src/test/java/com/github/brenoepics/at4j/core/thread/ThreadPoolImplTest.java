package com.github.brenoepics.at4j.core.thread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ThreadPoolImplTest {

  private ThreadPoolImpl threadPool;

  @BeforeEach
  void setUp() {
    threadPool = new ThreadPoolImpl();
  }

  @Test
  void shouldReturnExecutorService() {
    ExecutorService executorService = threadPool.getExecutorService();
    assertNotNull(executorService);
  }

  @Test
  void shouldReturnScheduler() {
    ScheduledExecutorService scheduler = threadPool.getScheduler();
    assertNotNull(scheduler);
  }

  @Test
  void shouldReturnDaemonScheduler() {
    ScheduledExecutorService daemonScheduler = threadPool.getDaemonScheduler();
    assertNotNull(daemonScheduler);
  }

  @Test
  void shouldReturnSingleThreadExecutorService() {
    ExecutorService singleThreadExecutorService =
        threadPool.getSingleThreadExecutorService("TestThread");
    assertNotNull(singleThreadExecutorService);
  }

  @Test
  void shouldReturnSingleDaemonThreadExecutorService() {
    ExecutorService singleDaemonThreadExecutorService =
        threadPool.getSingleDaemonThreadExecutorService("TestDaemonThread");
    assertNotNull(singleDaemonThreadExecutorService);
  }

  @Test
  void shouldRemoveAndShutdownSingleThreadExecutorService() {
    threadPool.getSingleThreadExecutorService("TestThread");
    assertTrue(threadPool.removeAndShutdownSingleThreadExecutorService("TestThread").isPresent());
  }

  @Test
  void shouldNotRemoveNonExistentSingleThreadExecutorService() {
    assertFalse(
        threadPool.removeAndShutdownSingleThreadExecutorService("NonExistentThread").isPresent());
  }

  @Test
  void shouldRunAfterGivenDuration() {
    assertDoesNotThrow(() -> threadPool.runAfter(() -> null, 1, TimeUnit.SECONDS));
  }

  @Test
  void shouldShutdownAllServices() {
    threadPool.getSingleThreadExecutorService("TestThread");
    threadPool.getSingleDaemonThreadExecutorService("TestDaemonThread");
    assertDoesNotThrow(() -> threadPool.shutdown());
  }
}
