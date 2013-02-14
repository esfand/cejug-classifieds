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
package net.java.dev.cejug.classifieds.login.entity;

import java.util.Enumeration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * An user of the GUI. This table is also used by the container to the
 * authentication.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1014 $ ($Date: 2008-12-26 17:41:33 +0100 (Fri, 26 Dec 2008) $)
 */
@Entity
@Table(name = "USERTABLE")
@NamedQueries( {
		@NamedQuery(name = UserEntity.SQL.FIND_BY_EMAIL, query = "SELECT user FROM UserEntity user WHERE user.email= :email"),
		@NamedQuery(name = UserEntity.SQL.FIND_BY_LOGIN, query = "SELECT user FROM UserEntity user WHERE user.login= :login"),
		@NamedQuery(name = UserEntity.SQL.FIND_BY_LOGIN_OR_EMAIL, query = "SELECT user FROM UserEntity user WHERE user.email= :email OR user.login= :login"),
		@NamedQuery(name = UserEntity.SQL.FIND_BY_LOGIN_AND_EMAIL, query = "SELECT user FROM UserEntity user WHERE user.email= :email AND user.login= :login"),
		@NamedQuery(name = UserEntity.SQL.ACTIVATE_LOGIN, query = "UPDATE UserEntity user SET user.status=0 WHERE user.login= :login AND user.email= :email"),
		@NamedQuery(name = UserEntity.SQL.DEACTIVATE_LOGIN, query = "UPDATE UserEntity user SET user.status=0 WHERE user.login= :login AND user.email= :email")

})
public class UserEntity extends AbstractEntity<UserEntity> {
	/** Constants used in named query and its parameters. */
	public static final class SQL {
		/**
		 * Searches for an user by his email. Parameters:
		 * <ul>
		 * <li><code>UserEntity.SQL.PARAM_EMAIL</code>: the email of a customer</li>
		 * </ul>
		 */
		public static final String FIND_BY_EMAIL = "findByEmail";

		/**
		 * Searches for an user by his email. Parameters:
		 * <ul>
		 * <li><code>UserEntity.SQL.PARAM_EMAIL</code>: the email of a customer</li>
		 * </ul>
		 */
		public static final String ACTIVATE_LOGIN = "activateLogin";

		/**
		 * Searches for an user by his email. Parameters:
		 * <ul>
		 * <li><code>UserEntity.SQL.PARAM_EMAIL</code>: the email of a customer</li>
		 * </ul>
		 */
		public static final String DEACTIVATE_LOGIN = "deactivateLogin";

		/**
		 * Searches for an user by his login. Parameter:
		 * <ul>
		 * <li><code>UserEntity.SQL.PARAM_LOGIN</code>: the login of a customer</li>
		 * </ul>
		 */
		public static final String FIND_BY_LOGIN = "findByLogin";
		/**
		 * Searches for an user by his email or by his login. Parameters:
		 * <ul>
		 * <li><code>UserEntity.SQL.PARAM_LOGIN</code>: the login of a customer</li>
		 * <li><code>UserEntity.SQL.PARAM_EMAIL</code>: the email of a customer</li>
		 * </ul>
		 */
		public static final String FIND_BY_LOGIN_OR_EMAIL = "findByLoginOrEmail";
		/**
		 * Searches for an user by his email or by his login. Parameters:
		 * <ul>
		 * <li><code>UserEntity.SQL.PARAM_LOGIN</code>: the login of a customer</li>
		 * <li><code>UserEntity.SQL.PARAM_EMAIL</code>: the email of a customer</li>
		 * </ul>
		 */
		public static final String FIND_BY_LOGIN_AND_EMAIL = "findByLoginAndEmail";
		/** The user's login. */
		public static final String PARAM_LOGIN = "login";
		/** The user's email. */
		public static final String PARAM_EMAIL = "email";

		/** THIS IS UNDER EVALUATION */
		public static enum ALLOWED_QUERIES {
			FIND_BY_EMAIL, FIND_BY_LOGIN, FIND_BY_LOGIN_OR_EMAIL
		};
	}

	@Id
	private String login;

	@Column(nullable = true)
	private String password;

	@Column(nullable = false)
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "EMAIL", nullable = true)
	private String email;

	@Column(name = "NAME", nullable = true)
	private String name;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getDomain() {
		return password;
	}

	public void setDomain(String domain) {
		this.password = domain;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	@Override
	protected Enumeration<UserEntity> getAllowedQueries() {
		return null;
	}
}
