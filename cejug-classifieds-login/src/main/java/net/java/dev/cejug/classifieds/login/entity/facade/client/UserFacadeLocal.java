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

import java.security.GeneralSecurityException;

import javax.ejb.Local;
import javax.persistence.TransactionRequiredException;

import net.java.dev.cejug.classifieds.login.entity.UserEntity;

/**
 * The persistence facade for Category entities.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev$ ($Date: 2008-09-08 18:17:47 +0200 (Mon, 08 Sep 2008) $)
 * @see EntityFacade
 */
@Local
public interface UserFacadeLocal extends EntityFacade<UserEntity>,
		UserFacadeRemote {
	/**
	 * Activate an account, copying the value of the unconfirmed column to the
	 * password column.
	 * 
	 * @param user
	 *            the owner of the account to be activated
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws TransactionRequiredException
	 */
	void activate(String login, String email) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException;

	/**
	 * Deactivate an account, copying the value of the password column to the
	 * unconfirmed column.
	 * 
	 * @param user
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws TransactionRequiredException
	 */
	void deactivate(String login, String email) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException;

	/**
	 * Reset the password - creates a random new password. The implementation is
	 * not supposed to generate strong passwords, and the user should be
	 * notified to change his password asap.
	 * 
	 * @param user
	 * @return the plain password to be sent to the customer.
	 * @throws GeneralSecurityException
	 *             The underneath implementation may use an encryption
	 *             algorithm, and any error should be thrown to the caller
	 *             method.
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws TransactionRequiredException
	 */
	String resetPassword(UserEntity user) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException,
			GeneralSecurityException;
}