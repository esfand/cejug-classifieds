package net.java.dev.cejug.classifieds.test.integration.business;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { LoadAtomIntegrationTest.class,
		LoadRssIntegrationTest.class, PublishIntegrationTest.class,
		ReportSpamIntegrationTest.class })
public final class BusinessTestSuite {
	private BusinessTestSuite() {
	}
}