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

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import net.java.dev.cejug.classifieds.login.entity.GroupEntity;
import net.java.dev.cejug.classifieds.login.entity.facade.client.GroupFacadeLocal;
import net.java.dev.cejug.classifieds.login.interceptor.ExceptionInterceptor;

/**
 * @author $Author: felipegaucho $
 * @version $Rev$ ($Date: 2008-10-11 13:37:15 +0200 (Sat, 11 Oct 2008) $)
 * @see CRUDEntityFacade
 */
@Stateless
@Interceptors(ExceptionInterceptor.class)
public class GroupFacade extends CRUDEntityFacade<GroupEntity> implements
		GroupFacadeLocal {
}
