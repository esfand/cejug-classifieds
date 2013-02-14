/**
 * 
 */
package net.java.dev.cejug.classifieds.admin.service;

import java.util.List;
import net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory;
import net.java.dev.cejug.classifieds.server.contract.AdvertisementRef;
import net.java.dev.cejug.classifieds.server.contract.AdvertisementRefBundle;
import net.java.dev.cejug.classifieds.server.contract.AdvertisementType;
import net.java.dev.cejug.classifieds.server.contract.BundleRequest;
import net.java.dev.cejug.classifieds.server.contract.CategoryCollection;
import net.java.dev.cejug.classifieds.server.contract.CejugClassifiedsAdmin;
import net.java.dev.cejug.classifieds.server.contract.CejugClassifiedsServiceAdmin;
import net.java.dev.cejug.classifieds.server.contract.CreateAdvertisementTypeParam;
import net.java.dev.cejug.classifieds.server.contract.CreateCategoryParam;
import net.java.dev.cejug.classifieds.server.contract.CreateDomainParam;
import net.java.dev.cejug.classifieds.server.contract.DeleteAdvertisementTypeParam;
import net.java.dev.cejug.classifieds.server.contract.DeleteCategoryParam;
import net.java.dev.cejug.classifieds.server.contract.DeleteDomainParam;
import net.java.dev.cejug.classifieds.server.contract.Domain;
import net.java.dev.cejug.classifieds.server.contract.DomainCollection;
import net.java.dev.cejug.classifieds.server.contract.ReadAdvertisementReferencesParam;
import net.java.dev.cejug.classifieds.server.contract.ServiceStatus;
import net.java.dev.cejug.classifieds.server.contract.UpdateAdvertisementTypeParam;
import net.java.dev.cejug.classifieds.server.contract.UpdateCategoryParam;
import net.java.dev.cejug.classifieds.server.contract.UpdateDomainParam;

/**
 * @author $Author: rodrigolopes $
 * @version $Rev$ ($Date$)
 */
public class CejugClassifiedsAdminService {

    /*
     * ==========================================================================
     * ADVERTISEMENT
     * ====================================================================
     */
    /**
     * Searches the advertisements.
     * @param param Parameter (categoryId) to search the advertisements
     * @return List of AdvertisementRef
     */
    public List<AdvertisementRef> readAdvertisementReferencesOperation(ReadAdvertisementReferencesParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        AdvertisementRefBundle bundle = admin.readAdvertisementReferencesOperation(param);
        return bundle.getAdvertisementRef();
    }

    /**
     * Update advertisement status.
     * @param advertisement Advertisement bundle.
     */
    public ServiceStatus updateAdvertisementStatusOperation(AdvertisementRefBundle advertisement) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.updateAdvertisementStatusOperation(advertisement);
    }

    /*
     * ==========================================================================
     * SECURITY
     * ==================================================================
     */
    public boolean executeLogin(String username, String password) {

        StringBuilder inverted = new StringBuilder(password);

        return ((username != null) && (username.length() > 4) && (username.equals(inverted.reverse().toString())));
    }

    /*
     * ==========================================================================
     * CATEGORY
     * ==================================================================
     */
    /**
     * Get all advertisement categories.
     * @param param Params to search the categories
     * @return List of categories
     */
    public CategoryCollection readCategoryBundleOperation(BundleRequest param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.readCategoryBundleOperation(param);
    }

    /**
     * Creates a new category.
     * @param param Category to be created.
     * @return The new category.
     */
    public AdvertisementCategory createCategoryOperation(CreateCategoryParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.createCategoryOperation(param);
    }

    /**
     * Updates a category.
     * @param param Category to be updated.
     * @return Status about the update operation.
     */
    public ServiceStatus updateCategoryOperation(UpdateCategoryParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.updateCategoryOperation(param);
    }

    /**
     * delete one category.
     * @param param Information to delete a category
     * @return Status about the update operation.
     */
    public ServiceStatus deleteCategoryOperation(DeleteCategoryParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.deleteCategoryOperation(param);
    }

    /*
     * ==========================================================================
     * ADVERTISEMENT TYPE
     * ========================================================
     */
    /**
     * Get all advertisement types.
     * @param param Params to search the advertisement types
     * @return List of advertisement types
     */
    public List<AdvertisementType> readAdvertisementTypeBundleOperation(BundleRequest param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.readAdvertisementTypeBundleOperation(param).getAdvertisementType();
    }

    /**
     * Creates a new advertisement type.
     * @param param Advertisement type to be created.
     * @return the AdvertisementType created.
     */
    public AdvertisementType createAdvertisementTypeOperation(CreateAdvertisementTypeParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.createAdvertisementTypeOperation(param);
    }

    /**
     * Updates an advertisement type.
     * @param param Advertisement type to be updated.
     * @return Status about the update operation.
     */
    public ServiceStatus updateAdvertisementTypeOperation(UpdateAdvertisementTypeParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.updateAdvertisementTypeOperation(param);
    }

    /**
     * delete one advertisement type.
     * @param param Information to delete an advertisement type
     * @return Status about the delete operation.
     */
    public ServiceStatus deleteAdvertisementTypeOperation(DeleteAdvertisementTypeParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.deleteAdvertisementTypeOperation(param.getPrimaryKey());
    }

    /*
     * ==========================================================================
     * DOMAIN
     * ====================================================================
     */
    /**
     * Get all domains.
     * @param param Params to search the domains
     * @return List of domains
     */
    public List<Domain> readDomainBundleOperation(BundleRequest param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        DomainCollection c = admin.readDomainBundleOperation(param);
        return c.getDomain();
    }

    /**
     * Creates a new domain.
     * @param param Domain to be created.
     * @return The created domain.
     */
    public Domain createDomainOperation(CreateDomainParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.createDomainOperation(param);
    }

    /**
     * Updates a domain.
     * @param param Domain to be updated.
     * @return Status about the update operation.
     */
    public ServiceStatus updateDomainOperation(UpdateDomainParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.updateDomainOperation(param);
    }

    /**
     * delete one domain.
     * @param param Information to delete a domain
     * @return Status about the update operation.
     */
    public ServiceStatus deleteDomainOperation(DeleteDomainParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.deleteDomainOperation(param);
    }
}
