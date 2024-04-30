package io.github.brenoepics.at4j.core.thread;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;

class ThreadPoolImplTest {

  private ThreadPoolImpl threadPool;

  @BeforeEach
  void setUp() {
    threadPool = new ThreadPoolImpl(null);
  }

  @AfterEach
  void tearDown() {
    threadPool.shutdown();
  }

  @Test
  void shouldReturnExecutorService() {
    ExecutorService executorService = threadPool.getExecutorService();
    assertNotNull(executorService);
  }

  @Test
  void shouldShutdownExecutorService() {
    ExecutorService executorService = threadPool.getExecutorService();
    assertFalse(executorService.isShutdown());
    threadPool.shutdown();
    assertTrue(executorService.isShutdown());
  }

  @Test
  void shouldCreateNewAt4jDefault() {
    ExecutorService executorService = ThreadPoolImpl.newAt4jDefault();
    assertNotNull(executorService);
    assertInstanceOf(ThreadPoolExecutor.class, executorService);
  }
}
