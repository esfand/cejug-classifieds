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
package net.java.dev.cejug.classifieds.business.interfaces;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.transaction.TransactionRequiredException;

import net.java.dev.cejug_classifieds.metadata.common.AbstractMessageElement;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

/**
 * @author $Author: felipegaucho $
 * @version $Rev $ ($Date: 2009-02-14 13:03:50 +0100 (Sat, 14 Feb 2009) $)
 */
@Local
public interface CRUDLocal<T extends AbstractMessageElement> {
	/**
	 * TODO: to comment.
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws TransactionRequiredException
	 * @throws EntityExistsException
	 */
	T create(final T type) throws EntityExistsException,
			TransactionRequiredException, IllegalStateException,
			IllegalArgumentException;

	/**
	 * TODO: to comment.
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws TransactionRequiredException
	 */
	ServiceStatus update(final T type) throws TransactionRequiredException,
			IllegalStateException, IllegalArgumentException;

	/**
	 * TODO: to comment.
	 */
	List<T> readBundleOperation(BundleRequest bundleRequest);

	/**
	 * TODO: to comment.
	 */
	T read(final long id);

	/**
	 * TODO: to comment.
	 * 
	 * @throws PersistenceException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws TransactionRequiredException
	 */
	ServiceStatus delete(final long id) throws TransactionRequiredException,
			IllegalStateException, IllegalArgumentException,
			PersistenceException;
}
