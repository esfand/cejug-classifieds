/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 Felipe Gaúcho
 
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
 originally used by CEJUG - Cear� Java Users Group.
 The project is hosted https://cejug-classifieds.dev.java.net/
 
 You can contact us through the mail dev@cejug-classifieds.dev.java.net
 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package net.java.dev.cejug.classifieds.test.integration.business;

import java.util.Calendar;
import java.util.GregorianCalendar;

import net.java.dev.cejug.classifieds.test.integration.AbstractServiceTestCase;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.metadata.business.SyndicationFilter;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.w3._2005.atom.Feed;

/**
 * Test the diploma validation operation.
 * 
 * @author $Author:felipegaucho $
 * @version $Rev:504 $ ($Date:2008-08-24 11:22:52 +0200 (Sun, 24 Aug 2008) $)
 */
public class LoadAtomIntegrationTest extends AbstractServiceTestCase {
	@Ignore(value = "not yet implemented")
	@Test
	public void testAtomOperation() {
		/*
		 * check if the test advertisement comes with the RSS
		 */
		CejugClassifiedsBusiness service = getBusinessService()
				.getCejugClassifiedsBusiness();
		SyndicationFilter filter = new SyndicationFilter();

		// retrieve the advertisement RSS since yesterday to today.
		GregorianCalendar today = new GregorianCalendar();
		GregorianCalendar yesterday = new GregorianCalendar();
		yesterday.roll(Calendar.DAY_OF_YEAR, false);

		filter.setDateInitial(yesterday);
		filter.setDateFinal(today);

		Feed feed = service.loadAtomOperation(filter);
		Assert.assertNotNull(feed);
	}

	@Test
	public void testLoadAtomOperationFail() {
		// TODO: simulates an invalid atom request and check if it fails.
	}
}
