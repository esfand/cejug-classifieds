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

import net.java.dev.cejug.utils.config.Obvious;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;

/**
 * An attchment can be an image, a MP3 sound or a Flash animation. Please check
 * the classifieds contract about the supported media types.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1133 $ ($Date: 2009-01-26 20:49:07 +0100 (Mon, 26 Jan 2009) $)
 */
@Entity
@Table(name = "ATTACHMENT")
public class AttachmentEntity extends AbstractEntity<AdvertisementCategory> {
	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "DESCRIPTION", nullable = true)
	private String description;

	@Column(name = "CONTENT_TYPE", nullable = false)
	private String contentType;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	/*
	 * @Basic(fetch = FetchType.LAZY)
	 * 
	 * @Lob
	 * 
	 * @Column(name = "BYTES", nullable = true) private byte[] content;
	 */
	@Column(name = "URL", nullable = true)
	private String reference;

	@Obvious
	public String getName() {
		return name;
	}

	@Obvious
	public void setName(final String name) {
		this.name = name;
	}

	@Obvious
	public String getDescription() {
		return description;
	}

	@Obvious
	public void setDescription(final String description) {
		this.description = description;
	}

	@Obvious
	public String getReference() {
		return reference;
	}

	@Obvious
	public void setReference(final String reference) {
		this.reference = reference;
	}
}
