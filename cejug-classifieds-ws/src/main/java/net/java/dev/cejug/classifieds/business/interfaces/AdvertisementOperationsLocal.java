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
package net.java.dev.cejug.classifieds.business.interfaces;

import javax.ejb.Local;
import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollection;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollectionFilter;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;

/**
 * Local interface to the Advertisement Operations stateless EJB.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 984 $ ($Date: 2008-12-07 20:58:43 +0100 (Sun, 07 Dec 2008) $)
 */
@Local
public interface AdvertisementOperationsLocal extends CRUDLocal<Advertisement> {

	/**
	 * TODO: to comment.
	 */
	AdvertisementCollection loadAdvertisementOperation(
			final AdvertisementCollectionFilter filter);

	Advertisement publishOperation(final Advertisement advertisement,
			final PublishingHeader header) throws EntityExistsException,
			TransactionRequiredException, IllegalStateException,
			IllegalArgumentException;
}
