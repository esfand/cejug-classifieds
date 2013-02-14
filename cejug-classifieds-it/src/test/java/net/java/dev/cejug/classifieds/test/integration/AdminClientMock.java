package net.java.dev.cejug.classifieds.test.integration;

import java.util.Random;
import java.util.TimeZone;

import net.java.dev.cejug_classifieds.admin.CejugClassifiedsAdmin;
import net.java.dev.cejug_classifieds.admin.CejugClassifiedsServiceAdmin;
import net.java.dev.cejug_classifieds.metadata.admin.CreateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.CreateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.CreateDomainParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteDomainParam;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.CategoryCollection;
import net.java.dev.cejug_classifieds.metadata.common.Domain;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

/**
 * REVIEW: review (it is only a test)
 * 
 * @author fgaucho
 * 
 */
public class AdminClientMock {
	private transient final Random RANDOM = new Random();
	private transient final CejugClassifiedsAdmin ADMIN = new CejugClassifiedsServiceAdmin()
			.getCejugClassifiedsAdmin();

	public CejugClassifiedsAdmin getAdmin() {
		return ADMIN;
	}

	public ServiceStatus deleteDomain(Domain domain) {
		// DELETE
		// remove or inactive the test advertisement
		DeleteDomainParam deleteParam = new DeleteDomainParam();
		deleteParam.setPrimaryKey(domain.getEntityId());
		return ADMIN.deleteDomainOperation(deleteParam);
	}

	public Domain createDomain() {
		// CREATE
		Domain domain = new Domain();
		String name = "test." + RANDOM.nextInt() + "." + RANDOM.nextInt();
		domain.setUri(name);
		domain.setBrand("Functional Domain");
		domain.setSharedQuota(false);
		domain.setTimezone(TimeZone.getDefault().getDisplayName());
		CreateDomainParam createParam = new CreateDomainParam();
		createParam.setDomain(domain);
		domain = ADMIN.createDomainOperation(createParam);
		return domain;
	}

	public AdvertisementCategory getAnyCategory() {
		CategoryCollection categories = ADMIN
				.readCategoryBundleOperation(new BundleRequest());
		if (categories.getAdvCategory().isEmpty()) {
			return createCategory();
		} else {
			int pos = (int) Math.abs((Math.random()
					* categories.getAdvCategory().size() - 0.000000001));
			return categories.getAdvCategory().get(pos);
		}
	}

	public AdvertisementType createAdvType() {
		AdvertisementType type = new AdvertisementType();
		type.setDescription("oo");
		type.setMaxAttachmentSize(300);
		type.setName("courtesy");
		type.setMaxTextLength(250);
		CreateAdvertisementTypeParam advParam = new CreateAdvertisementTypeParam();
		advParam.setAdvertisementType(type);
		return ADMIN.createAdvertisementTypeOperation(advParam);
	}

	public AdvertisementCategory createCategory() {
		AdvertisementCategory category = new AdvertisementCategory();
		category
				.setName("cat.name" + RANDOM.nextInt() + "." + RANDOM.nextInt());
		category
				.setDescription("Description "
						+ System.currentTimeMillis()
						+ " - this category was created just for testing, feel free to delete it");
		CreateCategoryParam catParam = new CreateCategoryParam();
		catParam.setAdvCategory(category);
		return ADMIN.createCategoryOperation(catParam);
	}

	public void tryToDeleteAdvType(AdvertisementType type) {
		if (type != null) {
			try {
				ADMIN.deleteAdvertisementTypeOperation(type.getEntityId());
			} catch (Exception error) {
				System.out.println("Impossible to delete AdvertisementType #"
						+ type.getEntityId());
				// ignore errors.. it is just a trial.
			}
		}
	}

	public void tryToDeleteDomain(Domain domain) {
		if (domain != null) {
			try {
				DeleteDomainParam param = new DeleteDomainParam();
				param.setPrimaryKey(domain.getEntityId());
				ADMIN.deleteDomainOperation(param);
			} catch (Exception error) {
				System.out.println("Impossible to delete Domain #"
						+ domain.getEntityId());
				// ignore errors.. it is just a trial.
			}
		}
	}

	public void tryToDeleteCategory(AdvertisementCategory category) {
		if (category != null) {
			try {
				DeleteCategoryParam param = new DeleteCategoryParam();
				param.setPrimaryKey(category.getEntityId());
				ADMIN.deleteCategoryOperation(param);
			} catch (Exception error) {
				System.out.println("Impossible to delete Category #"
						+ category.getEntityId());
				// ignore errors.. it is just a trial.
			}
		}
	}

	public void tryToDeleteAdvertisement(Advertisement adv) {
		if (adv != null) {
			try {
				ADMIN.deleteAdvertisementTypeOperation(adv.getEntityId());
			} catch (Exception error) {
				System.out.println("Impossible to delete Advertisement #"
						+ adv.getEntityId());
				// ignore errors.. it is just a trial.
			}
		}
	}
}
