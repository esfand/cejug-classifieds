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
 originally used by CEJUG - Ceará Java Users Group.
 The project is hosted https://cejug-classifieds.dev.java.net/
 
 You can contact us through the mail dev@cejug-classifieds.dev.java.net
 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package net.java.dev.cejug.classifieds.test.integration.business;

import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;

import net.java.dev.cejug.classifieds.test.integration.AbstractServiceTestCase;
import net.java.dev.cejug.classifieds.test.integration.AdminClientMock;
import net.java.dev.cejug.classifieds.test.integration.BusinessClientMock;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;
import net.java.dev.cejug_classifieds.metadata.common.Domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the diploma validation operation.
 * 
 * @author $Author:felipegaucho $
 * @version $Rev:504 $ ($Date:2008-08-24 11:22:52 +0200 (Sun, 24 Aug 2008) $)
 */
public class PublishIntegrationTest extends AbstractServiceTestCase {
	private final static Logger logger = Logger
			.getLogger(PublishIntegrationTest.class.getName());

	@Test
	public void testPublishOperation() throws DatatypeConfigurationException,
			MalformedURLException {
		AdminClientMock admin = null;
		BusinessClientMock business = null;
		Domain domain = null;
		AdvertisementType type = null;
		AdvertisementCategory category = null;
		Advertisement adv = null;
		try {
			admin = new AdminClientMock();
			business = new BusinessClientMock();

			domain = admin.createDomain();
			type = admin.createAdvType();
			category = admin.getAnyCategory();
			logger.info("Publishing on Category #" + category.getEntityId());
			adv = business.createAdvertisement(domain, type, category, true);
			Assert.assertTrue(adv.getEntityId() != 0);
			adv = business.createAdvertisement(domain, type, category, false);
			Assert.assertTrue(adv.getEntityId() != 0);
		} catch (Exception ee) {
			logger.severe(ee.getMessage());
			fail(ee.getMessage());
		}
		/*
		 * finally { // TODO: to decide if
		 * 
		 * admin.tryToDeleteAdvertisement(adv); admin.tryToDeleteAdvType(type);
		 * admin.tryToDeleteCategory(category); admin.tryToDeleteDomain(domain);
		 * 
		 * }
		 */
	}

}