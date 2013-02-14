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

import javax.ejb.EJB;
import javax.ejb.Stateless;

import net.java.dev.cejug.classifieds.business.interfaces.CustomerAdapterLocal;
import net.java.dev.cejug.classifieds.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.entity.facade.DomainFacadeLocal;
import net.java.dev.cejug_classifieds.metadata.common.Customer;

/**
 * Adaptation interface between Customer objects representing Soap elements and
 * the CustomerEntity used to persist the domain data in the database.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1130 $ ($Date: 2009-01-25 18:48:02 +0100 (Sun, 25 Jan 2009) $)
 */
@Stateless
public class CustomerAdapter implements CustomerAdapterLocal {

	@EJB
	private transient DomainFacadeLocal domainFacade;

	/** {@inheritDoc} */
	public CustomerEntity toEntity(Customer customer)
			throws IllegalStateException, IllegalArgumentException {

		CustomerEntity entity = new CustomerEntity();
		entity.setDomain(domainFacade.read(customer.getDomainId()));
		entity.setId(customer.getEntityId());
		entity.setLogin(customer.getLogin());
		// entity.setQuotas(type.getEntityId());
		return entity;
	}

	/** {@inheritDoc} */
	public Customer toSoap(CustomerEntity entity) throws IllegalStateException,
			IllegalArgumentException {

		Customer customer = new Customer();
		customer.setDomainId(entity.getDomain().getId());
		customer.setEntityId(entity.getId());
		customer.setLogin(entity.getLogin());
		return customer;
	}
}
