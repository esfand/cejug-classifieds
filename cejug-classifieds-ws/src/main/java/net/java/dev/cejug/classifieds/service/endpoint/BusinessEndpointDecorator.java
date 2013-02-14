/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 CEJUG - Ceará Java Users Group
 
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
package net.java.dev.cejug.classifieds.service.endpoint;

import generated.Rss;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.business.interfaces.AdvertisementOperationsLocal;
import net.java.dev.cejug.classifieds.business.interfaces.AdvertisementTypeOperationsLocal;
import net.java.dev.cejug.classifieds.business.interfaces.CategoryOperationsLocal;
import net.java.dev.cejug.classifieds.business.interfaces.ClassifiedsBusinessLocal;
import net.java.dev.cejug.classifieds.business.interfaces.ClassifiedsBusinessRemote;
import net.java.dev.cejug.classifieds.business.interfaces.LoadAtomOperationLocal;
import net.java.dev.cejug.classifieds.business.interfaces.LoadRssOperationLocal;
import net.java.dev.cejug.classifieds.service.interceptor.TimerInterceptor;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollection;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollectionFilter;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.business.SyndicationFilter;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementTypeCollection;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.CategoryCollection;
import net.java.dev.cejug_classifieds.metadata.common.CreateCustomerParam;
import net.java.dev.cejug_classifieds.metadata.common.CustomerCollection;
import net.java.dev.cejug_classifieds.metadata.common.DeleteCustomerParam;
import net.java.dev.cejug_classifieds.metadata.common.ReadCustomerBundleParam;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;
import net.java.dev.cejug_classifieds.metadata.common.UpdateCustomerParam;

import org.w3._2005.atom.Feed;

/**
 * Business Service implementation of the interface defined in the
 * cejug-classifieds-business.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 884 $ ($Date: 2008-11-10 20:47:19 +0100 (Mon, 10 Nov 2008) $)
 * @see net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness
 */
@Interceptors(TimerInterceptor.class)
@Stateless
@WebService(endpointInterface = "net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness", serviceName = "CejugClassifiedsServiceBusiness", portName = "CejugClassifiedsBusiness", targetNamespace = "http://cejug-classifieds.dev.java.net/business")
public class BusinessEndpointDecorator implements ClassifiedsBusinessLocal,
		ClassifiedsBusinessRemote {
	/**
	 * BusinessEndpointDecorator logger.
	 */
	private final static Logger logger = Logger.getLogger(
			BusinessEndpointDecorator.class.getName(), "i18n/log");
	/**
	 * Used by not yet implemented operations: {@value} .
	 */
	private static final String NOT_IMPLEMENTED = "operation not yet implemented";

	@EJB
	private transient LoadRssOperationLocal loadRssImpl;

	@EJB
	private transient LoadAtomOperationLocal loadAtomImpl;

	@EJB
	private transient AdvertisementOperationsLocal crudAdvertisement;

	@EJB
	private transient AdvertisementTypeOperationsLocal crudAdvType;

	@EJB
	private transient CategoryOperationsLocal crudCategory;

	/**
	 * @return an <a href=
	 *         "http://en.wikipedia.org/wiki/Atom_(standard)#Example_of_an_Atom_1.0_Feed"
	 *         >ATOM 1.0 document </a>
	 * @param filter
	 *            a set of constraint on the advertisement's search in the
	 *            database.
	 */
	public Feed loadAtomOperation(SyndicationFilter filter) {
		return loadAtomImpl.loadAtomOperation(filter);

	}

	/**
	 * @return an <a href= "http://en.wikipedia.org/wiki/RSS_(file_format)" >RSS
	 *         2.0 document </a>
	 * @param filter
	 *            a set of constraint on the advertisement's search in the
	 *            database.
	 */
	public Rss loadRssOperation(SyndicationFilter filter) {
		return loadRssImpl.loadRssOperation(filter);
	}

	public Advertisement publishOperation(final Advertisement advertisement,
			final PublishingHeader header) {
		try {
			return crudAdvertisement.publishOperation(advertisement, header);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public AdvertisementCollection loadAdvertisementOperation(
			final AdvertisementCollectionFilter filter) {
		return crudAdvertisement.loadAdvertisementOperation(filter);
	}

	/**
	 * 
	 */
	public CategoryCollection readCategoryBundleOperation(
			final BundleRequest bundleRequest) {
		CategoryCollection collection = new CategoryCollection();
		collection.getAdvCategory().addAll(
				crudCategory.readBundleOperation(bundleRequest));
		return collection;
	}

	public ServiceStatus reportSpamOperation(final long advId) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	public ServiceStatus createCustomerOperation(
			final CreateCustomerParam newCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	public ServiceStatus deleteCustomerOperation(
			final DeleteCustomerParam obsoleteCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	public CustomerCollection readCustomerBundleOperation(
			final ReadCustomerBundleParam getCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	public ServiceStatus updateCustomerOperation(
			final UpdateCustomerParam partialCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	public AdvertisementTypeCollection readAdvertisementTypeBundleOperation(
			BundleRequest bundleRequest) {
		try {
			AdvertisementTypeCollection collection = new AdvertisementTypeCollection();
			collection.getAdvertisementType().addAll(
					crudAdvType.readBundleOperation(bundleRequest));
			return collection;

		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}
}
