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
package net.java.dev.cejug.classifieds.entity.facade.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import net.java.dev.cejug.classifieds.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.entity.facade.AdvertisementFacadeLocal;

/**
 * @author $Author:felipegaucho $
 * @version $Rev:504 $ ($Date:2008-08-24 11:22:52 +0200 (Sun, 24 Aug 2008) $)
 * @see CRUDEntityFacade
 */
@Stateless
public class AdvertisementFacade extends CRUDEntityFacade<AdvertisementEntity>
		implements AdvertisementFacadeLocal {

	/**
	 * Read all advertisements of a category.
	 * 
	 * @param categoryId
	 *            The ID of the advertisements' category.
	 * @return a list of advertisements of a certain category.
	 */
	public List<AdvertisementEntity> readByCategory(final long categoryId)
			throws IllegalStateException, IllegalArgumentException {

		Query query = manager
				.createNamedQuery(AdvertisementEntity.QUERIES.SELECT_BY_CATEGORY);
		query.setParameter(AdvertisementEntity.QUERIES.PARAM_CATEGORY_ID, Long
				.valueOf(categoryId));
		query.setParameter(AdvertisementEntity.QUERIES.PARAM_STATE,
				AdvertisementEntity.AdvertisementStatus.ARCHIVE);
		return doQuery(query);
	}
}
