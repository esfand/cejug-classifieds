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
package net.java.dev.cejug.classifieds.richfaces.view;

import java.util.List;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollectionFilter;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1129 $ ($Date: 2009-01-25 18:45:55 +0100 (Sun, 25 Jan 2009) $)
 */
public class ClassifiedsServiceProxy {
	private transient final CejugClassifiedsBusiness SERVICE;

	public ClassifiedsServiceProxy() {
		SERVICE = new CejugClassifiedsServiceBusiness()
				.getCejugClassifiedsBusiness();

	}

	public List<Advertisement> getAdvertisements() {
		AdvertisementCollectionFilter filter = new AdvertisementCollectionFilter();
		/*
		 * if (getSelectedCategory() != null && getSelectedCategory().getId() !=
		 * null) { // To take the selectedCategory just do this: //
		 * getSelectedCategory().getId().toString() // We can set a default
		 * value for the advertisements show when the // user access the first
		 * time // so this 'if' will be unnecessary :) //
		 * filter.setCategory(getSelectedCategory().getId().toString()); }
		 */

		filter.setCategory(getSelectedCategory().getId().toString());

		return SERVICE.loadAdvertisementOperation(filter).getAdvertisement();
	}

	// Set default category
	private AdvertisementCategoryWrapper selectedCategory = new AdvertisementCategoryWrapper(
			3L);

	public AdvertisementCategoryWrapper getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(
			AdvertisementCategoryWrapper selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

}
