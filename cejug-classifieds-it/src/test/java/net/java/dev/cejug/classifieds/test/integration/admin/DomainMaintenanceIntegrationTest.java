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
package net.java.dev.cejug.classifieds.test.integration.admin;

import java.util.List;

import net.java.dev.cejug.classifieds.test.integration.AbstractServiceTestCase;
import net.java.dev.cejug.classifieds.test.integration.AdminClientMock;
import net.java.dev.cejug_classifieds.admin.CejugClassifiedsAdmin;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateDomainParam;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.Domain;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the domain maintenance CRUD operations through the following steps:
 * <ul>
 * <li><strong>@Before</strong> the tests, we store the number of already
 * available categories. After all tests we check this number again, to be sure
 * our tests didn't changed the state of the database.
 * <ol>
 * <li>CREATE a new Domain, named
 * <em>FunctionalTest + System.currentTimeMillis()</em></li>
 * <li>READ the bundle of available domains and check if our newly created
 * domain is there. At this moment, we read the ID of the test domain.</li>
 * <li>UPDATE the test domain, modifying its name or other attribute.</li>
 * <li>READ the domain by ID and check if the updated data is correct.</li>
 * <li>DELETE the test created domain.</li>
 * </ol>
 * </li>
 * <li><strong>@After</strong> the tests, we and check the number of remained
 * domains in the server side to be sure the state of the database haven't
 * changed.</li>
 * </ul>
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 669 $ ($Date: 2008-09-15 17:43:04 +0200 (Mon, 15 Sep 2008) $)
 */
public class DomainMaintenanceIntegrationTest extends AbstractServiceTestCase {
	@Test
	public void crudDomain() {
		AdminClientMock client = new AdminClientMock();
		CejugClassifiedsAdmin admin = getAdminService()
				.getCejugClassifiedsAdmin();
		Domain domain = client.createDomain();
		Assert.assertNotNull(domain.getEntityId());

		// AdvertisementCategory category = client.createCategory();
		// Assert.assertNotNull(category.getEntityId());

		// READ
		List<Domain> domains = admin.readDomainBundleOperation(
				new BundleRequest()).getDomain();

		boolean createdOk = false;
		for (Domain readDomain : domains) {
			if (readDomain.getEntityId() == domain.getEntityId()) {
				// The just created domain has no ID, so we need to lookup for
				// its domain name in the received list in order to know its ID.
				domain = readDomain;
				createdOk = true;
				break;
			}
		}
		Assert.assertTrue(createdOk);
		// domain.getAdvertisementCategory().add(category);

		domain.setBrand("New Brand on the block " + System.currentTimeMillis());

		UpdateDomainParam updateParam = new UpdateDomainParam();
		updateParam.setDomain(domain);
		ServiceStatus updateStatus = admin.updateDomainOperation(updateParam);
		Assert.assertEquals(updateStatus.getStatusCode(), 200);

		List<Domain> updatedDomains = admin.readDomainBundleOperation(
				new BundleRequest()).getDomain();

		boolean updateOk = false;
		for (Domain updatedDomain : updatedDomains) {
			if (updatedDomain.getEntityId() == domain.getEntityId()) {
				// Check if the received domain has the newly create name.
				Assert
						.assertEquals(updatedDomain.getBrand(), domain
								.getBrand());
				Assert.assertEquals(updatedDomain.getAdvCategory().size(),
						domain.getAdvCategory().size());

				updateOk = true;
				break;
			}
		}
		Assert.assertTrue(updateOk);

		ServiceStatus deleteStatus = client.deleteDomain(domain);
		Assert.assertEquals(deleteStatus.getStatusCode(), 200);
	}
}
