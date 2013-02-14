package net.java.dev.cejug.classifieds.entity.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.TransactionRequiredException;

import net.java.dev.cejug.classifieds.entity.AbstractEntity;
import net.java.dev.cejug.classifieds.entity.facade.impl.CRUDEntityFacade;

/**
 * @author $Author: felipegaucho $
 * @version $Rev: 665 $ ($Date: 2008-09-15 12:09:38 +0200 (Mon, 15 Sep 2008) $)
 * 
 * @see CRUDEntityFacade
 * @see <a href=
 *      'http://en.wikipedia.org/wiki/Create%2C_read%2C_update_and_delete'>CRUD @
 *      Wikipedia.</a>
 */
public interface EntityFacade<T extends AbstractEntity<?>> {

	/**
	 * <strong>C</strong><font color='gray'>RUD</font> operation - inserts a new
	 * entity in the database.
	 * 
	 * @param entity
	 *            The entity to be included in the database.
	 * @throws Exception
	 *             database exception, or incompatible entity.
	 */
	void create(T entity) throws EntityExistsException, IllegalStateException,
			IllegalArgumentException, TransactionRequiredException;

	/**
	 * <font color='gray'>C</font><strong>R</strong><font color='gray'>UD</font>
	 * operation - inserts a new entity in the database. Read all records
	 * available on database for a certain type of entity.
	 * 
	 * @return A collection of objects of type 'entityClass'
	 * @throws IllegalStateException
	 *             if called for a Java Persistence query language UPDATE or
	 *             DELETE statement. See:
	 *             http://java.sun.com/j2se/1.5/docs/api/java
	 *             /lang/IllegalStateException.html
	 * @throws IllegalArgumentException
	 *             if query string is not valid.
	 */
	List<T> readAll() throws IllegalStateException, IllegalArgumentException;

	/**
	 * TODO: missed comments.
	 * 
	 * @param primaryKey
	 * @return an object of type T
	 * @throws Exception
	 */
	T read(Serializable primaryKey) throws IllegalStateException,
			IllegalArgumentException;

	/**
	 * <font color='gray'>CRU</font><strong>D</strong> operation - removes an
	 * entity from the database.
	 * 
	 * @param entity
	 *            The entity to be excluded from the database.
	 * @throws Exception
	 *             database exception, or incompatible entity.
	 */
	void delete(T entity) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException,
			PersistenceException;

	void deleteAll(List<T> entities) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException,
			PersistenceException;

	/**
	 * <font color='gray'>CR</font><strong>U</strong><font color='gray'>D</font>
	 * operation - merges the entity with the database one.
	 * 
	 * @param entity
	 *            The entity to be merged in the database.
	 * @throws Exception
	 *             database exception, or incompatible entity.
	 */
	void update(T entity) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException;

	/**
	 * Execute a query against the database and return the result set
	 * (eventually empty).
	 * 
	 * @param query
	 *            the named query.
	 * @return a list of entities (eventually empty).
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	List<T> doQuery(Query query) throws IllegalStateException,
			IllegalArgumentException;
}
