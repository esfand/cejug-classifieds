package net.java.dev.cejug.classifieds.test.integration;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class DefaultJUnitNotifier extends RunNotifier {
	private transient int threadCounter = 0;
	private transient final List<Failure> failures = new ArrayList<Failure>();

	public List<Failure> getFailures() {
		return failures;
	}

	public void incrementThreadsCounter() {
		threadCounter++;
	}

	public int getThreadCounter() {
		return threadCounter;
	}

	@Override
	public void fireTestFailure(final Failure failure) {
		failures.add(failure);
	}

	@Override
	public void fireTestFinished(final Description description) {
		threadCounter--;
	}
}
