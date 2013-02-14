/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 Felipe GaÃºcho
 
 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 
 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.
 
 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 
 This file is part of the CEJUG-CLASSIFIEDS Project - an  open source classifieds system
 originally used by CEJUG - Cearï¿½ Java Users Group.
 The project is hosted https://cejug-classifieds.dev.java.net/
 
 You can contact us through the mail dev@cejug-classifieds.dev.java.net
 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package net.java.dev.cejug.classifieds.test.integration.business;

import junit.framework.Assert;
import net.java.dev.cejug.classifieds.test.integration.AbstractServiceTestCase;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test the diploma validation operation.
 * 
 * @author $Author:felipegaucho $
 * @version $Rev:504 $ ($Date:2008-08-24 11:22:52 +0200 (Sun, 24 Aug 2008) $)
 */
public class ReportSpamIntegrationTest extends AbstractServiceTestCase {

	@Before
	public void setUp() throws Exception {
		// include or activate a new advertisement (submit via service or direct
		// into database)
	}

	@After
	public void tearDown() throws Exception {
		// remove or inactive the test advertisement
	}

	@Ignore
	@Test
	public void reportSpamOperation() {
		/*
		 * check if the test advertisement comes with the RSS
		 */
		CejugClassifiedsBusiness service = new CejugClassifiedsServiceBusiness()
				.getCejugClassifiedsBusiness();

		/**
		 * TODO: to create an advertisement to be reported as Spam, otherwise
		 * the advertisement ID 1 cannot be available.
		 */
		ServiceStatus status = service.reportSpamOperation(1);
		Assert.assertEquals(status.getDescription().toUpperCase(), "OK");
	}

	@Test
	public void testReportSpamOperationFail() {
		// TODO: simulates an invalid report spam request and check if it fails.
	}
}
