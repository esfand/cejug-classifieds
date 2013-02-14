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

import javax.ejb.Remote;

/**
 * The persistence facade for Category entities.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev$ ($Date: 2008-09-08 18:17:47 +0200 (Mon, 08 Sep 2008) $)
 * @see EntityFacade
 */
@Remote
public interface UserFacadeRemote {
	/**
	 * Check if an <em>email address</em> can be registered in the database.
	 * 
	 * @param email
	 *            the email of a customer.
	 * @return if the email is available, false otherwise.
	 */
	public boolean isEmailAvailable(final String email);

	/**
	 * Check if an <em>user name</em> can be registered in the database.
	 * 
	 * @param login
	 *            the login of a customer.
	 * @return true if the login is available, false otherwise.
	 */
	public boolean isLoginAvailable(final String login);
}