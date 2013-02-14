package net.java.dev.cejug.classifieds.test.integration;

import net.java.dev.cejug.classifieds.test.integration.admin.AdminTestSuite;
import net.java.dev.cejug.classifieds.test.integration.business.BusinessTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { AdminTestSuite.class, BusinessTestSuite.class })
public class FullTestSuite {
}
