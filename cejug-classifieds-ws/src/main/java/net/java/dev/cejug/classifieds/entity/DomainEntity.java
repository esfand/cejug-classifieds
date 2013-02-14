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

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.java.dev.cejug_classifieds.metadata.common.Domain;

/**
 * A domain is company or a group of people. The domain should be registered in
 * the Cejug-Classifieds system, and a domain has a unique domain name.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 578 $ ($Date: 2008-09-03 19:58:27 +0200 (Wed, 03 Sep 2008) $)
 */
@Entity
@Table(name = "DOMAIN")
@NamedQuery(name = "selectDomainByName", query = "SELECT d FROM DomainEntity d WHERE d.domainName= :domain")
public class DomainEntity extends AbstractEntity<Domain> {

	@Column(name = "NAME", nullable = false, unique = true)
	private String domainName;

	@Column(name = "SHARED_COTA", nullable = false)
	private Boolean sharedQuota;

	@Column(name = "BRAND", nullable = false)
	private String brand;

	@OneToMany(mappedBy = "domain")
	private Collection<QuotaEntity> quotas;

	@ManyToMany
	@JoinTable(name = "DOMAIN_CATEGORY", joinColumns = @JoinColumn(name = "DOMAIN_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID"))
	private Collection<CategoryEntity> categories;

	public Boolean getSharedQuota() {

		return sharedQuota;
	}

	public void setSharedQuota(final Boolean sharedQuota) {

		this.sharedQuota = sharedQuota;
	}

	public String getBrand() {

		return brand;
	}

	public void setBrand(final String brand) {

		this.brand = brand;
	}

	public Collection<QuotaEntity> getQuotas() {

		return quotas;
	}

	public void setQuotas(final Collection<QuotaEntity> quotas) {

		this.quotas = quotas;
	}

	public String getDomainName() {

		return domainName;
	}

	public void setDomainName(final String domainName) {

		this.domainName = domainName;
	}

	/**
	 * @return the categories
	 */
	public Collection<CategoryEntity> getCategories() {

		return categories;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public void setCategories(final Collection<CategoryEntity> categories) {

		this.categories = categories;
	}
}
