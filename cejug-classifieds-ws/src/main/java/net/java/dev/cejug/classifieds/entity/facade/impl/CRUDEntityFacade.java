package net.java.dev.cejug.classifieds.entity.facade.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.TransactionRequiredException;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.entity.AbstractEntity;
import net.java.dev.cejug.classifieds.entity.facade.EntityFacade;

/**
 * CRUD operations shared by the Entity Facades.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 786 $ ($Date: 2008-10-31 22:34:32 +0100 (Fri, 31 Oct 2008) $)
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
	private final static Logger logger = Logger.getLogger(
			CRUDEntityFacade.class.getName(), "i18n/log");

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
		manager.persist(entity);
		manager.flush();
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
		if (entity.getId() < 1) {
			// TODO: i18n of error messages.
			logger.severe("Entity with ID " + entity.getId()
					+ " cannot be updated!");
			throw new WebServiceException("Entity with ID " + entity.getId()
					+ " cannot be updated!");
		}
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
}
