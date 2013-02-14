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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.Query;

import net.java.dev.cejug.classifieds.entity.CategoryEntity;
import net.java.dev.cejug.classifieds.entity.facade.CategoryFacadeLocal;
import net.java.dev.cejug.classifieds.exception.ExceptionInterceptor;

/**
 * @author $Author: felipegaucho $
 * @version $Rev: 728 $ ($Date: 2008-10-11 13:37:15 +0200 (Sat, 11 Oct 2008) $)
 * @see CRUDEntityFacade
 */
@Stateless
@Interceptors(ExceptionInterceptor.class)
public class CategoryFacade extends CRUDEntityFacade<CategoryEntity> implements
		CategoryFacadeLocal {

	@Override
	public List<CategoryEntity> readAll() throws IllegalStateException,
			IllegalArgumentException {

		List<CategoryEntity> bundle = super.readAll();
		for (CategoryEntity entity : bundle) {
			entity.setAvailable(countAdvertisements(entity));
		}
		return bundle;

	}

	/**
	 * @see <a * href=
	 *      "http://weblogs.java.net/blog/maxpoon/archive/2007/06/extending_the_n_3.html"
	 *      >Extending * the NetBeans Tutorial JSF-JPA-Hibernate Application,
	 *      Part 3 - * Enabling JMX Monitoring on Hibernate v3 and Ehcache 1.3.0
	 *      on * SimpleJpaHibernateApp< /a>
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public int countAdvertisements(CategoryEntity category) {

		Query query = manager
				.createQuery(
						"select COUNT(a) from AdvertisementEntity a WHERE a.category = :cat")
				.setParameter("cat", category);
		try {
			return ((Long) query.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;

		}
	}
}
