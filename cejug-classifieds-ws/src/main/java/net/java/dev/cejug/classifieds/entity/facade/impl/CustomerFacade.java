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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.TransactionRequiredException;

import net.java.dev.cejug.classifieds.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.entity.DomainEntity;
import net.java.dev.cejug.classifieds.entity.facade.CustomerFacadeLocal;
import net.java.dev.cejug.classifieds.entity.facade.DomainFacadeLocal;

/**
 * @author $Author: felipegaucho $
 * @version $Rev: 683 $ ($Date: 2008-09-24 20:08:33 +0200 (Wed, 24 Sep 2008) $)
 * @see CRUDEntityFacade
 */
@Stateless
public class CustomerFacade extends CRUDEntityFacade<CustomerEntity> implements
		CustomerFacadeLocal {

	@EJB
	private transient DomainFacadeLocal domainFacade;

	public CustomerEntity findOrCreate(long domainId, String login)
			throws EntityExistsException, IllegalStateException,
			IllegalArgumentException, TransactionRequiredException {
		Query query = manager
				.createNamedQuery(CustomerEntity.QUERIES.SELECT_BY_DOMAIN);
		query.setParameter(CustomerEntity.QUERIES.PARAM_DOMAIN, domainId);
		query.setParameter(CustomerEntity.QUERIES.PARAM_LOGIN, login);

		try {
			return (CustomerEntity) query.getSingleResult();
		} catch (NoResultException error) {
			/*
			 * TODO: if the customer does not exist, insert a new customer
			 * automatically. It should also add the default "welcome quotas",
			 * to be defined by an external rule from database. The below code
			 * is just a test.
			 */

			// loading domain
			DomainEntity domain = domainFacade.read(domainId);

			// insert a new customer
			CustomerEntity customer = new CustomerEntity();

			customer.setDomain(domain);
			customer.setLogin(login);
			// customer.setQuotas(new ArrayList<QuotaEntity>());
			manager.persist(customer);
			manager.flush();
			return customer;
		}
	}
}
