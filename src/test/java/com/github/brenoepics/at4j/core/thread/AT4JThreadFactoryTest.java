package com.github.brenoepics.at4j.core.thread;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AT4JThreadFactoryTest {

		@Test
		void shouldCreateDaemonThreadWhenDaemonIsTrue() {
				// Given
				AT4JThreadFactory factory = new AT4JThreadFactory("test-%d", true);

				// When
				Thread thread = factory.newThread(() -> {});

				// Then
				assertTrue(thread.isDaemon());
				assertEquals("test-1", thread.getName());
		}

		@Test
		void shouldCreateNonDaemonThreadWhenDaemonIsFalse() {
				// Given
				AT4JThreadFactory factory = new AT4JThreadFactory("test-%d", false);

				// When
				Thread thread = factory.newThread(() -> {});

				// Then
				assertFalse(thread.isDaemon());
				assertEquals("test-1", thread.getName());
		}

		@Test
		void shouldIncrementThreadNameCounter() {
				// Given
				AT4JThreadFactory factory = new AT4JThreadFactory("test-%d", true);

				// When
				Thread thread1 = factory.newThread(() -> {});
				Thread thread2 = factory.newThread(() -> {});

				// Then
				assertEquals("test-1", thread1.getName());
				assertEquals("test-2", thread2.getName());
		}

		@Test
		void shouldHandleNamePatternWithoutCounter() {
				// Given
				AT4JThreadFactory factory = new AT4JThreadFactory("test", true);

				// When
				Thread thread = factory.newThread(() -> {});

				// Then
				assertEquals("test", thread.getName());
		}
}