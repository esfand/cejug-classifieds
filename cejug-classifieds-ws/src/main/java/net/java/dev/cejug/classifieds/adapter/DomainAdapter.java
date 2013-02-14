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
package net.java.dev.cejug.classifieds.adapter;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;

import net.java.dev.cejug.classifieds.business.interfaces.DomainAdapterLocal;
import net.java.dev.cejug.classifieds.entity.CategoryEntity;
import net.java.dev.cejug.classifieds.entity.DomainEntity;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.Domain;

/**
 * Adaptation interface between Domain objects representing Soap elements and
 * the DomainEntity used to persist the domain data in the database.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1193 $ ($Date: 2009-02-14 12:32:12 +0100 (Sat, 14 Feb 2009) $)
 */
@Stateless
public class DomainAdapter implements DomainAdapterLocal {

	/** {@inheritDoc} */
	public DomainEntity toEntity(final Domain domain)
			throws IllegalStateException, IllegalArgumentException {
		DomainEntity domainEntity = new DomainEntity();
		domainEntity.setDomainName(domain.getUri());
		domainEntity.setSharedQuota(domain.isSharedQuota());
		domainEntity.setBrand(domain.getBrand());
		domainEntity.setId(domain.getEntityId());
		Collection<CategoryEntity> categories = new ArrayList<CategoryEntity>();
		if (domain.getAdvCategory() != null) {
			CategoryAdapter categoryAdapter = new CategoryAdapter();
			for (AdvertisementCategory category : domain.getAdvCategory()) {
				CategoryEntity categoryEntity = categoryAdapter
						.toEntity(category);
				categories.add(categoryEntity);
			}
		}
		domainEntity.setCategories(categories);
		return domainEntity;
	}

	/** {@inheritDoc} */
	public Domain toSoap(final DomainEntity domainEntity)
			throws IllegalStateException, IllegalArgumentException {
		Domain domain = new Domain();
		domain.setEntityId(domainEntity.getId());
		domain.setBrand(domainEntity.getBrand());
		domain.setUri(domainEntity.getDomainName());
		domain.setSharedQuota(domainEntity.getSharedQuota());

		if (domainEntity.getCategories() != null) {
			CategoryAdapter categoryAdapter = new CategoryAdapter();
			for (CategoryEntity categoryEntity : domainEntity.getCategories()) {
				AdvertisementCategory category = categoryAdapter
						.toSoap(categoryEntity);
				domain.getAdvCategory().add(category);
			}
		}
		return domain;
	}
}
