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
package net.java.dev.cejug.classifieds.richfaces.view;

import java.security.Principal;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1181 $ ($Date: 2009-02-14 11:27:59 +0100 (Sat, 14 Feb 2009) $)
 */
public class SecurityBean {

	private String login;

	public String getLogin() {

		return login;
	}

	public String getPassword() {

		return password;
	}

	private String password;

	public void setLogin(final String login) {

		this.login = login;
	}

	public void setPassword(final String password) {

		this.password = password;
	}

	public static Principal getUserFromContext() {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		return context.getUserPrincipal();
	}

	/*
	 * public boolean isCustomer() { javax.faces.context.ExternalContext context
	 * = FacesContext .getCurrentInstance().getExternalContext(); if(true)
	 * return true; return context.isUserInRole("customer") ||
	 * context.isUserInRole("admin"); }
	 */
}
