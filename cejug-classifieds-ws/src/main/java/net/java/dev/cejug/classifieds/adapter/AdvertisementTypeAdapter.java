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

import javax.ejb.Stateless;

import net.java.dev.cejug.classifieds.business.interfaces.AdvertisementTypeAdapterLocal;
import net.java.dev.cejug.classifieds.entity.AdvertisementTypeEntity;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;

/**
 * Adaptation interface between Advertisement Type objects representing Soap
 * elements and the AdvertisementTypeEntity used to persist the domain data in
 * the database.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1191 $ ($Date: 2009-02-14 12:29:16 +0100 (Sat, 14 Feb 2009) $)
 */
@Stateless
public class AdvertisementTypeAdapter implements AdvertisementTypeAdapterLocal {

	/** {@inheritDoc} */
	public AdvertisementTypeEntity toEntity(final AdvertisementType type)
			throws IllegalStateException, IllegalArgumentException {

		AdvertisementTypeEntity advTypeEntity = new AdvertisementTypeEntity();
		advTypeEntity.setId(type.getEntityId());
		advTypeEntity.setDescription(type.getDescription());
		advTypeEntity.setMaxAttachmentSize(type.getMaxAttachmentSize());
		advTypeEntity.setName(type.getName());
		advTypeEntity.setTextLength(type.getMaxTextLength());
		return advTypeEntity;
	}

	/** {@inheritDoc} */
	public AdvertisementType toSoap(final AdvertisementTypeEntity advTypeEntity)
			throws IllegalStateException, IllegalArgumentException {

		AdvertisementType advType = new AdvertisementType();
		advType.setEntityId((int) advTypeEntity.getId());
		advType.setDescription(advTypeEntity.getDescription());
		advType.setName(advTypeEntity.getName());
		advType.setMaxAttachmentSize(advTypeEntity.getMaxAttachmentSize());
		advType.setMaxTextLength(advTypeEntity.getTextLength());
		return advType;
	}
}
