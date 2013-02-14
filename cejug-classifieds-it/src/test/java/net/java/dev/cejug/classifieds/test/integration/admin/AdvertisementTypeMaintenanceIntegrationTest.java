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
import net.java.dev.cejug_classifieds.admin.CejugClassifiedsAdmin;
import net.java.dev.cejug_classifieds.metadata.admin.CreateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the advertisement type maintenance CRUD operations through the following
 * steps:
 * <ul>
 * <li><strong>@Before</strong> the tests, we store the number of already
 * available advertisement types. After all tests we check this number again, to
 * be sure our tests didn't changed the state of the database.
 * <ol>
 * <li>CREATE a new Advertisement Type, named
 * <em>FunctionalTest + System.currentTimeMillis()</em></li>
 * <li>READ the bundle of available advertisement types and check if our newly
 * created adv type is there. At this moment, we read the ID of the test adv
 * type.</li>
 * <li>UPDATE the test adv type, modifying its name or other attribute.</li>
 * <li>READ the adv type by ID and check if the updated data is correct.</li>
 * <li>DELETE the test created adv type.</li>
 * </ol>
 * </li>
 * <li><strong>@After</strong> the tests, we and check the number of remained
 * advertisement types in the server side to be sure the state of the database
 * haven't changed.</li>
 * </ul>
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 562 $ ($Date: 2008-09-01 18:57:28 +0200 (Mon, 01 Sep 2008) $)
 */
public class AdvertisementTypeMaintenanceIntegrationTest extends
		AbstractServiceTestCase {
	private static int idCounter = 0;

	@Test
	public void crudCategory() {
		CejugClassifiedsAdmin admin = getAdminService()
				.getCejugClassifiedsAdmin();

		// CREATE
		AdvertisementType advType = new AdvertisementType();
		advType.setName("FunctionalTest" + idCounter++);
		advType.setDescription("Functional ADV Type Test.");
		advType.setMaxAttachmentSize(987L);
		advType.setMaxTextLength(5642L);
		CreateAdvertisementTypeParam createParam = new CreateAdvertisementTypeParam();
		createParam.setAdvertisementType(advType);
		AdvertisementType status = admin
				.createAdvertisementTypeOperation(createParam);
		Assert.assertTrue(status.getEntityId() != 0);

		// READ
		BundleRequest readParam = new BundleRequest();
		List<AdvertisementType> availableTypes = admin
				.readAdvertisementTypeBundleOperation(readParam)
				.getAdvertisementType();
		// We created a adv type on the setup method, so we assume there is at
		// least 1 adv type.
		Assert.assertFalse(availableTypes.isEmpty());

		for (AdvertisementType advertisementType : availableTypes) {
			if (advertisementType.getName().equals(advType.getName())) {
				// The just created adv type has no ID, so we need to lookup for
				// its name in the received list in order to know its ID.
				advType = advertisementType;
				break;
			}
		}

		// UPDATE
		String newName = "NewName" + idCounter++;
		advType.setName(newName);
		UpdateAdvertisementTypeParam updateParam = new UpdateAdvertisementTypeParam();
		updateParam.setAdvertisementType(advType);
		admin.updateAdvertisementTypeOperation(updateParam);
		List<AdvertisementType> updatedAdvTypes = admin
				.readAdvertisementTypeBundleOperation(new BundleRequest())
				.getAdvertisementType();

		boolean updateOk = false;
		for (AdvertisementType advertisementType : updatedAdvTypes) {
			if (advertisementType.getEntityId() == advType.getEntityId()) {
				// Check if the received adv type has the newly create name.
				Assert.assertEquals(advertisementType.getName(), newName);
				updateOk = true;
				break;
			}
		}
		Assert.assertTrue(updateOk);

		// DELETE
		// remove or inactive the test advertisement type
		DeleteAdvertisementTypeParam deleteParam = new DeleteAdvertisementTypeParam();
		deleteParam.setPrimaryKey(advType.getEntityId());
		ServiceStatus deleteStatus = admin
				.deleteAdvertisementTypeOperation(deleteParam.getPrimaryKey());
		Assert.assertEquals(deleteStatus.getStatusCode(), 200);
	}
}
