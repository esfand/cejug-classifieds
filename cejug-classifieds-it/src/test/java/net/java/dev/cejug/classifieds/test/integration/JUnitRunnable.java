package net.java.dev.cejug.classifieds.test.integration;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * A JUnit class that can be launch in concurrent mode.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 636 $ ($Date: 2008-09-08 18:25:25 +0200 (Mon, 08 Sep 2008) $)
 * 
 */
public class JUnitRunnable extends BlockJUnit4ClassRunner implements Runnable {
	private transient final RunNotifier notifier;

	public JUnitRunnable(Class<?> testCase, RunNotifier notifier)
			throws InitializationError {
		super(testCase);
		this.notifier = notifier;
	}

	public void run() {
		run(notifier);
	}
}
