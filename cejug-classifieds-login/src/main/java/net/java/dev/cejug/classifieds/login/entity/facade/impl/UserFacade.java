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

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;
import javax.persistence.TransactionRequiredException;

import net.java.dev.cejug.classifieds.login.entity.UserEntity;
import net.java.dev.cejug.classifieds.login.entity.UserEntity.SQL;
import net.java.dev.cejug.classifieds.login.entity.facade.client.UserFacadeLocal;
import net.java.dev.cejug.classifieds.login.entity.facade.client.UserFacadeRemote;
import net.java.dev.cejug.classifieds.login.interceptor.ExceptionInterceptor;
import net.java.dev.cejug.classifieds.login.security.DESedeStringEncrypter;

/**
 * @author $Author: felipegaucho $
 * @version $Rev$ ($Date: 2008-10-11 13:37:15 +0200 (Sat, 11 Oct 2008) $)
 * @see CRUDEntityFacade
 */
@Stateless
@Interceptors(ExceptionInterceptor.class)
public class UserFacade extends CRUDEntityFacade<UserEntity> implements
		UserFacadeLocal, UserFacadeRemote {

	@Override
	public boolean isEmailAvailable(String email) {
		if (email == null || email.trim().length() == 0) {
			return false;
		} else {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(UserEntity.SQL.PARAM_EMAIL, email);
			try {
				findByCriteria(UserEntity.SQL.FIND_BY_EMAIL, parameters);
				return false; // NOT AVAILABLE
			} catch (NoResultException none) {
				return true; // AVAILABLE
			}
		}
	}

	@Override
	public boolean isLoginAvailable(String login) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(UserEntity.SQL.PARAM_LOGIN, login);
		try {
			findByCriteria(UserEntity.SQL.FIND_BY_LOGIN, parameters);
			return false; // NOT AVAILABLE
		} catch (NoResultException none) {
			return true; // AVAILABLE
		}
	}

	@Override
	public void activate(String login, String email)
			throws IllegalStateException, IllegalArgumentException,
			TransactionRequiredException {
		Map<String, String> criteria = new HashMap<String, String>();
		criteria.put(UserEntity.SQL.PARAM_LOGIN, login);
		criteria.put(UserEntity.SQL.PARAM_EMAIL, email);
		UserEntity entity = findByCriteria(SQL.FIND_BY_LOGIN_AND_EMAIL,
				criteria);
		entity.setStatus(0);
		update(entity);
	}

	@Override
	public void deactivate(String login, String email)
			throws IllegalStateException, IllegalArgumentException,
			TransactionRequiredException {
		Map<String, String> criteria = new HashMap<String, String>();
		criteria.put(UserEntity.SQL.PARAM_LOGIN, login);
		criteria.put(UserEntity.SQL.PARAM_EMAIL, email);
		UserEntity entity = findByCriteria(SQL.FIND_BY_LOGIN_AND_EMAIL,
				criteria);
		entity.setStatus(5);
		update(entity);
	}

	@Override
	public String resetPassword(UserEntity user)
			throws GeneralSecurityException {
		DESedeStringEncrypter encrypter = new DESedeStringEncrypter(Long
				.toString(System.currentTimeMillis()));
		String newPassword;
		try {
			newPassword = encrypter.encrypt(user.getPassword());
		} catch (UnsupportedEncodingException e) {
			newPassword = e.getMessage();
		}
		Random random = new Random();
		int length = 6 + random.nextInt(3);
		if (newPassword.length() > length) {
			newPassword = newPassword.substring(0, length);
		}
		// TODO Auto-generated method stub
		return newPassword;
	}
}
