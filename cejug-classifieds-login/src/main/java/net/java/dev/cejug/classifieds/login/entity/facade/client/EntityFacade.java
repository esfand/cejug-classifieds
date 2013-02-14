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
package net.java.dev.cejug.classifieds.login.entity.facade.client;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import net.java.dev.cejug.classifieds.login.entity.AbstractEntity;
import net.java.dev.cejug.classifieds.login.entity.facade.impl.CRUDEntityFacade;

/**
 * @author $Author: felipegaucho $
 * @version $Rev: 1017 $ ($Date: 2008-12-30 19:32:40 +0100 (Tue, 30 Dec 2008) $)
 * 
 * @see CRUDEntityFacade
 * @see <a href=
 *      'http://en.wikipedia.org/wiki/Create%2C_read%2C_update_and_delete'>CRUD @
 *      Wikipedia.</a>
 */
public interface EntityFacade<T extends AbstractEntity<?>> {
	/**
	 * Allow local interfaces to do queries in the database.
	 * 
	 * @param criteria
	 *            a map between column names and its expected values. In order
	 *            to avoid <a href=
	 *            'http://www.owasp.org/index.php/Preventing_SQL_Injection_in_Ja
	 *            v a ' > S Q l injection</a>, the implementation should of this
	 *            method should apply <em>SQL escaped</em> before to submit the
	 *            query.
	 * @param queryName
	 *            the name of the query.
	 * @return the entity that matches the search criteria. If there is no
	 *         matches, a NoResultException is thrown.
	 */
	T findByCriteria(final String queryName, final Map<String, ?> criteria)
			throws NoResultException;

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
