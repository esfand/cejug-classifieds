package net.java.dev.cejug.classifieds.test.integration;

import java.util.List;

import junit.framework.Assert;
import net.java.dev.cejug.classifieds.test.integration.admin.AdminTestSuite;
import net.java.dev.cejug.classifieds.test.integration.business.BusinessTestSuite;

import org.junit.Test;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

public class ConcurrentTestSuite {
	private transient static final DefaultJUnitNotifier notifier = new DefaultJUnitNotifier();
	private transient static final int CONCURRENCY_FACTOR = 10;

	@Test
	public void admin() {

		Suite.SuiteClasses annos = AdminTestSuite.class
				.getAnnotation(Suite.SuiteClasses.class);
		List<Failure> failures = runAll(annos);

		if (!failures.isEmpty()) {
			Assert.fail(failures.get(0).getTrace());
		}
	}

	@Test
	public void business() {
		Suite.SuiteClasses annos = BusinessTestSuite.class
				.getAnnotation(Suite.SuiteClasses.class);
		for (int fi = 0; fi < 12; fi++) {
			List<Failure> failures = runAll(annos);
			if (!failures.isEmpty()) {
				Assert.fail(failures.get(0).getTrace());
			}
		}
	}

	/** Synchronization flag. */
	private static final Object lock = new Object();

	private List<Failure> runAll(Suite.SuiteClasses annos) {

		try {
			synchronized (lock) {
				Class<?>[] testSuite = annos.value();
				Thread[] processes = new Thread[testSuite.length
						* CONCURRENCY_FACTOR];

				for (int i = 0; i < testSuite.length; i++) {
					for (int j = 0; j < CONCURRENCY_FACTOR; j++) {
						processes[i * CONCURRENCY_FACTOR + j] = new Thread(
								new JUnitRunnable(testSuite[i], notifier));
						notifier.incrementThreadsCounter();
					}
				}

				for (Thread t : processes) {
					t.start();
				}

				// TODO: include a decent timer here instead of this blatant
				// counter.
				while (notifier.getThreadCounter() > 0) {
					Thread.yield();
				}
				return notifier.getFailures();
			}
		} catch (InitializationError e) {
			return null;
		}
	}
}
