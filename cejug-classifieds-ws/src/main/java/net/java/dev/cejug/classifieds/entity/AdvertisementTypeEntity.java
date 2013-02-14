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
package net.java.dev.cejug.classifieds.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;

/**
 * @author $Author: felipegaucho $
 * @version $Rev: 794 $ ($Date: 2008-11-02 17:00:54 +0100 (Sun, 02 Nov 2008) $)
 */
@Entity
@Table(name = "ADVERTISEMENT_TYPE")
public class AdvertisementTypeEntity extends AbstractEntity<AdvertisementType>
		implements Comparable<AdvertisementTypeEntity> {
	private String name;
	private String description;

	@Column(name = "TEXT_LENGTH", nullable = false)
	private Long textLength;

	@Column(name = "MAX_ATTACHMENT_SIZE", nullable = false)
	private Long maxAttachmentSize;

	@Column(name = "NAME", nullable = false)
	public String getName() {

		return name;
	}

	public void setName(final String name) {

		this.name = name;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(final String description) {

		this.description = description;
	}

	public Long getTextLength() {
		return textLength;
	}

	public void setTextLength(final Long textLength) {

		this.textLength = textLength;
	}

	public Long getMaxAttachmentSize() {

		return maxAttachmentSize;
	}

	public void setMaxAttachmentSize(final Long maxAttachmentSize) {

		this.maxAttachmentSize = maxAttachmentSize;
	}

	public int compareTo(final AdvertisementTypeEntity other) {
		return (int) (getId() - other.getId());
	}

	public boolean equals(final Object obj) {

		return (obj instanceof AdvertisementTypeEntity)
				&& compareTo((AdvertisementTypeEntity) obj) == 0;
	}

	public int hashCode() {
		return (int) (getId());
	}
}
