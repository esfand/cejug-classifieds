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

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.java.dev.cejug.classifieds.business.interfaces.ClassifiedsAdminRemote;
import net.java.dev.cejug.classifieds.test.integration.AbstractServiceTestCase;
import net.java.dev.cejug_classifieds.admin.CejugClassifiedsAdmin;
import net.java.dev.cejug_classifieds.metadata.admin.CreateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the category maintenance CRUD operations through the following steps:
 * <ul>
 * <li><strong>@Before</strong> the tests, we store the number of already
 * available categories. After all tests we check this number again, to be sure
 * our tests didn't changed the state of the database.
 * <ol>
 * <li>CREATE a new Category, named
 * <em>FunctionalTest + System.currentTimeMillis()</em></li>
 * <li>READ the bundle of available categories and check if our newly created
 * category is there. At this moment, we read the ID of the test category.</li>
 * <li>UPDATE the test category, modifying its name or other attribute.</li>
 * <li>READ the category by ID and check if the updated data is correct.</li>
 * <li>DELETE the test created category.</li>
 * </ol>
 * </li>
 * <li><strong>@After</strong> the tests, we and check the number of remained
 * categories in the server side to be sure the state of the database haven't
 * changed.</li>
 * </ul>
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1183 $ ($Date: 2009-02-14 11:45:02 +0100 (Sat, 14 Feb 2009) $)
 */
public class CategoryMaintenanceIntegrationTest extends AbstractServiceTestCase {
	private static Random random = new Random();

	@Test
	public void testingSoapWebService() {
		try {
			CejugClassifiedsAdmin admin = getAdminService()
					.getCejugClassifiedsAdmin();
			// CREATE
			AdvertisementCategory category = new AdvertisementCategory();
			category.setName("test.cat." + random.nextInt() + "."
					+ random.nextInt());
			category
					.setDescription("This category was created just for testing, you are free to delete it");
			category.setDescription("Functional Category Test.");
			CreateCategoryParam catParam = new CreateCategoryParam();
			catParam.setAdvCategory(category);

			AdvertisementCategory newCategory = admin
					.createCategoryOperation(catParam);
			// Assert.assertEquals(status.getStatusCode(), 200);

			// READ
			BundleRequest param = new BundleRequest();
			List<AdvertisementCategory> categories = admin
					.readCategoryBundleOperation(param).getAdvCategory();
			// We created a category on the setup method, so we assume there is
			// at
			// least 1 category.
			Assert.assertFalse(categories.isEmpty());

			boolean greenBar = false;
			for (AdvertisementCategory advertisementCategory : categories) {
				if (advertisementCategory.getEntityId() == newCategory
						.getEntityId()) {
					// We found the just created category :) Green bar !
					greenBar = true;
					break;
				}
			}
			Assert.assertTrue("Couldn't find the just created category (ID="
					+ newCategory.getEntityId(), greenBar);

			// UPDATE
			String newName = "test." + random.nextInt() + "."
					+ random.nextInt();
			newCategory.setName(newName);
			UpdateCategoryParam updateParam = new UpdateCategoryParam();
			updateParam.setAdvCategory(newCategory);
			admin.updateCategoryOperation(updateParam);
			List<AdvertisementCategory> updatedCategories = admin
					.readCategoryBundleOperation(param).getAdvCategory();

			greenBar = false;
			for (AdvertisementCategory advertisementCategory : updatedCategories) {
				if (advertisementCategory.getEntityId() == newCategory
						.getEntityId()) {
					// Check if the received category has the newly create name.
					Assert.assertEquals(advertisementCategory.getName(),
							newName);
					greenBar = true;
					break;
				}
			}
			Assert.assertTrue("Couldn't update the name of a category (ID="
					+ newCategory.getEntityId() + ")", greenBar);

			// DELETE
			// remove or inactive the test advertisement
			DeleteCategoryParam deleteParam = new DeleteCategoryParam();
			deleteParam.setPrimaryKey(newCategory.getEntityId());
			ServiceStatus deleteStatus = admin
					.deleteCategoryOperation(deleteParam);
			Assert.assertEquals(deleteStatus.getStatusCode(), 200);

		} catch (Exception n) {
			fail(n.getMessage());
		}
	}

	// @Test
	public void testingRemoteEjb() {
		try {
			Properties props = new Properties();
			props.setProperty("java.naming.factory.initial",
					"com.sun.enterprise.naming.SerialInitContextFactory");
			props.setProperty("java.naming.factory.url.pkgs",
					"com.sun.enterprise.naming");
			props
					.setProperty("java.naming.factory.state",
							"com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
			InitialContext ctx = new InitialContext(props);
			CejugClassifiedsAdmin adminSessionBean = (ClassifiedsAdminRemote) ctx
					.lookup(ClassifiedsAdminRemote.class.getCanonicalName());
			crudCategory(adminSessionBean);
		} catch (NamingException n) {
			fail(n.getMessage());
		}
	}

	private void crudCategory(CejugClassifiedsAdmin admin) {
		// CREATE
		AdvertisementCategory category = new AdvertisementCategory();
		category.setName("test.cat." + random.nextInt() + "."
				+ random.nextInt());
		category
				.setDescription("This category was created just for testing, you are free to delete it");
		category.setDescription("Functional Category Test.");
		CreateCategoryParam catParam = new CreateCategoryParam();
		catParam.setAdvCategory(category);

		AdvertisementCategory newCategory = admin
				.createCategoryOperation(catParam);
		// Assert.assertEquals(status.getStatusCode(), 200);

		// READ
		BundleRequest param = new BundleRequest();
		List<AdvertisementCategory> categories = admin
				.readCategoryBundleOperation(param).getAdvCategory();
		// We created a category on the setup method, so we assume there is at
		// least 1 category.
		Assert.assertFalse(categories.isEmpty());

		boolean greenBar = false;
		for (AdvertisementCategory advertisementCategory : categories) {
			if (advertisementCategory.getEntityId() == newCategory
					.getEntityId()) {
				// We found the just created category :) Green bar !
				greenBar = true;
				break;
			}
		}
		Assert.assertTrue("Couldn't find the just created category (ID="
				+ newCategory.getEntityId(), greenBar);

		// UPDATE
		String newName = "test." + random.nextInt() + "." + random.nextInt();
		newCategory.setName(newName);
		UpdateCategoryParam updateParam = new UpdateCategoryParam();
		updateParam.setAdvCategory(newCategory);
		admin.updateCategoryOperation(updateParam);
		List<AdvertisementCategory> updatedCategories = admin
				.readCategoryBundleOperation(param).getAdvCategory();

		greenBar = false;
		for (AdvertisementCategory advertisementCategory : updatedCategories) {
			if (advertisementCategory.getEntityId() == newCategory
					.getEntityId()) {
				// Check if the received category has the newly create name.
				Assert.assertEquals(advertisementCategory.getName(), newName);
				greenBar = true;
				break;
			}
		}
		Assert.assertTrue("Couldn't update the name of a category (ID="
				+ newCategory.getEntityId() + ")", greenBar);

		// DELETE
		// remove or inactive the test advertisement
		DeleteCategoryParam deleteParam = new DeleteCategoryParam();
		deleteParam.setPrimaryKey(newCategory.getEntityId());
		ServiceStatus deleteStatus = admin.deleteCategoryOperation(deleteParam);
		Assert.assertEquals(deleteStatus.getStatusCode(), 200);
	}
}
