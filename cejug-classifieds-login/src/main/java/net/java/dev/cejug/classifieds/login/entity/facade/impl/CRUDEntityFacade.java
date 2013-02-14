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
package net.java.dev.cejug.classifieds.login.entity.facade.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import net.java.dev.cejug.classifieds.login.entity.AbstractEntity;
import net.java.dev.cejug.classifieds.login.entity.facade.client.EntityFacade;

/**
 * CRUD operations shared by the Entity Facades.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1017 $ ($Date: 2008-12-30 19:32:40 +0100 (Tue, 30 Dec 2008) $)
 * @see <a
 *      href='http://en.wikipedia.org/wiki/Create,_read,_update_and_delete'>Crea
 *      t e , read, update and delete (CRUD)</a>
 */
@Stateless
public class CRUDEntityFacade<T extends AbstractEntity<?>> implements
		EntityFacade<T> {
	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private final static Logger logger = Logger
			.getLogger(CRUDEntityFacade.class.getName());

	private transient final Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public CRUDEntityFacade() {
		entityClass = (Class<T>) ((java.lang.reflect.ParameterizedType) this
				.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * The entity manager is injected by the container JEE 5+.
	 */
	@PersistenceContext
	protected transient EntityManager manager;

	/**
	 * {@inheritDoc}
	 */
	public void create(final T entity) throws EntityExistsException,
			IllegalStateException, IllegalArgumentException,
			TransactionRequiredException {
		try {
			manager.persist(entity);
			manager.flush();
		} catch (Exception e) {
			logger.severe("error-> " + e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> readAll() throws IllegalStateException,
			IllegalArgumentException {
		Query query;
		query = manager.createQuery("select e from "
				+ entityClass.getSimpleName() + " e");
		logger.finest("readAll: " + query.toString());
		return doQuery(query);
	}

	/**
	 * {@inheritDoc}
	 */
	public T read(final Serializable primaryKey) throws IllegalStateException,
			IllegalArgumentException {
		return manager.find(entityClass, primaryKey);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(final T entity) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException,
			PersistenceException {
		manager.remove(entity);
		manager.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteAll(final List<T> entities) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException,
			PersistenceException {
		for (T t : entities) {
			manager.remove(t);
		}
		manager.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	public void update(final T entity) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException {
		manager.merge(entity);
		manager.flush();
	}

	@SuppressWarnings("unchecked")
	public List<T> doQuery(Query query) throws IllegalStateException,
			IllegalArgumentException {
		List<T> response = query.getResultList();
		if (response == null) {
			response = new ArrayList<T>();
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	public T findByCriteria(final String query, final Map<String, ?> parameters)
			throws NoResultException {
		Query namedQuery = manager.createNamedQuery(query);
		Iterator entries = parameters.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, ?> entry = (Map.Entry<String, ?>) entries.next();
			namedQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return (T) namedQuery.getSingleResult();
	}
}
