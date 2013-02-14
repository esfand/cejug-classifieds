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
package net.java.dev.cejug.classifieds.service.endpoint.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.business.interfaces.AdvertisementAdapterLocal;
import net.java.dev.cejug.classifieds.business.interfaces.AdvertisementOperationsLocal;
import net.java.dev.cejug.classifieds.business.interfaces.SoapOrmAdapter;
import net.java.dev.cejug.classifieds.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.entity.facade.AdvertisementFacadeLocal;
import net.java.dev.cejug.classifieds.entity.facade.EntityFacade;
import net.java.dev.cejug.classifieds.exception.RepositoryAccessException;
import net.java.dev.cejug_classifieds.metadata.attachments.AtavarImage;
import net.java.dev.cejug_classifieds.metadata.attachments.AvatarImageOrUrl;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollection;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollectionFilter;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.common.Customer;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1148 $ ($Date: 2009-02-01 15:59:06 +0100 (Sun, 01 Feb 2009) $)
 */
@Stateless
public class AdvertisementOperations extends
		AbstractCrudImpl<AdvertisementEntity, Advertisement> implements
		AdvertisementOperationsLocal {
	/**
	 * Persistence façade of Advertisement entities.
	 */
	@EJB
	private transient AdvertisementFacadeLocal advFacade;

	@EJB
	private transient AdvertisementAdapterLocal advAdapter;

	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private static final Logger logger = Logger.getLogger(
			AdvertisementOperations.class.getName(), "i18n/log");

	@Override
	protected SoapOrmAdapter<Advertisement, AdvertisementEntity> getAdapter() {
		return advAdapter;
	}

	@Override
	protected EntityFacade<AdvertisementEntity> getFacade() {

		return advFacade;
	}

	public AdvertisementCollection loadAdvertisementOperation(
			final AdvertisementCollectionFilter filter) {

		// TODO: load advertisements from timetable.... filtering with periods,
		// etc..

		try {
			AdvertisementCollection collection = new AdvertisementCollection();
			List<AdvertisementEntity> entities = advFacade.readByCategory(Long
					.parseLong(filter.getCategory()));
			for (AdvertisementEntity entity : entities) {
				collection.getAdvertisement().add(advAdapter.toSoap(entity));
			}
			return collection;
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public Advertisement publishOperation(final Advertisement advertisement,
			final PublishingHeader header) throws EntityExistsException,
			TransactionRequiredException, IllegalStateException,
			IllegalArgumentException {

		// TODO: to implement the real code.
		try {
			// TODO: re-think a factory to reuse adapters...
			Customer customer = new Customer();
			customer.setLogin(header.getCustomerLogin());
			customer.setDomainId(header.getCustomerDomainId());
			advertisement.setCustomer(customer);
			AvatarImageOrUrl avatar = advertisement.getAvatarImageOrUrl();
			AtavarImage img = avatar.getImage();

			AdvertisementEntity entity = advAdapter.toEntity(advertisement);
			advFacade.create(entity);

			if (null != avatar.getGravatarEmail()) {
				avatar
						.setUrl("http://www.gravatar.com/avatar/"
								+ hashGravatarEmail(avatar.getGravatarEmail())
								+ ".jpg");
				AtavarImage temp = new AtavarImage();
				temp.setContentType("image/jpg");
				avatar.setImage(temp);
				String tmp = avatar.getUrl();
				avatar.setName(tmp.substring(tmp.lastIndexOf('/') + 1));
			} else if (null != avatar.getUrl()) {
				AtavarImage temp = new AtavarImage();
				temp.setContentType(img.getContentType());
				avatar.setImage(temp);
				avatar.setName("img" + System.currentTimeMillis());
			} else if (img != null && img.getValue() != null
					&& img.getValue().length > 0) {
				String reference = copyResourcesToRepository(avatar.getName(),
						img.getValue(), entity.getId(), header
								.getCustomerDomainId());
				entity.getAvatar().setReference(reference);
				advFacade.update(entity);
			}

			logger.finest("Advertisement #" + entity.getId() + " published ("
					+ entity.getTitle() + ")");
			return advAdapter.toSoap(entity);
		} catch (NoSuchAlgorithmException e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	/**
	 * A customer can submit a resource URL or a resource content, more common
	 * in case of images (PNG/JPG). In this case, the server first store the
	 * image contents in the file system and refer its location in the database.
	 * This method receives an Avatar object, check it size and store it
	 * contents in the file system.
	 * 
	 * @param l
	 * @param advertisement
	 *            the advertisement containing attachments.
	 * @return the resource address.
	 * @throws RepositoryAccessException
	 *             problems accessing the content repository.
	 */
	private String copyResourcesToRepository(String name, byte[] contents,
			long customerId, long domainId) throws RepositoryAccessException {

		if (contents == null || contents.length < 1) {
			return null;
		} else if (customerId < 1 || domainId < 1) {
			throw new RepositoryAccessException("Unaccepted ID (customer id:"
					+ customerId + ", domain: " + domainId);
		} else {
			String glassfishHome = System.getenv("AS_HOME");
			String path = glassfishHome
					+ "/domains/domain1/applications/j2ee-apps/cejug-classifieds-server/cejug-classifieds-server_war/resource/"
					+ domainId + '/' + customerId;

			String file = path + "/" + name;
			File pathF = new File(path);
			File fileF = new File(file);

			try {
				if (pathF.mkdirs() && fileF.createNewFile()) {
					FileOutputStream out;
					out = new FileOutputStream(file);
					out.write(contents);
					out.close();
					String resourcePath = "http://fgaucho.dyndns.org:8080/cejug-classifieds-server/resource/"
							+ domainId + "/" + customerId + "/" + name;
					logger.info("resource created: " + resourcePath);
					return resourcePath;

				} else {
					throw new RepositoryAccessException(
							"error trying tocreate the resource path '" + file
									+ "'");
				}
			} catch (IOException e) {
				logger.severe(e.getMessage());
				throw new RepositoryAccessException(e);
			}
		}
	}

	private static final char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static String hashGravatarEmail(String email)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();

		byte[] bytes = md.digest(email.getBytes());
		StringBuilder sb = new StringBuilder(2 * bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			int low = (int) (bytes[i] & 0x0f);
			int high = (int) ((bytes[i] & 0xf0) >> 4);
			sb.append(HEXADECIMAL[high]);
			sb.append(HEXADECIMAL[low]);
		}
		return sb.toString();
	}

}
