/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2007-2009 CEJUG - Ceará Java Users Group
 
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
package net.java.dev.cejug.classifieds.service.http;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.entity.ServiceLifeCycleEntity;
import net.java.dev.cejug.classifieds.entity.facade.ServiceLifeCycleFacadeLocal;

/**
 * A Servlet to return the service life cycle information through HTTP request.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1059 $ ($Date: 2009-01-04 17:55:04 +0100 (Sun, 04 Jan 2009) $)
 */
public class ServletLifeCycle implements ServletContextListener {
	@EJB
	private transient ServiceLifeCycleFacadeLocal lifeObserver;
	private transient long id = -1;

	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			ServiceLifeCycleEntity lifeCycle = lifeObserver.read(id);
			lifeCycle.setFinish(Calendar.getInstance());
			lifeObserver.update(lifeCycle);
		} catch (Exception e) {
			throw new WebServiceException(e);
		}
	}

	public void contextInitialized(ServletContextEvent arg0) {
		try {
			ServiceLifeCycleEntity lifeCycle = new ServiceLifeCycleEntity();
			lifeCycle.setName("test");
			lifeCycle.setStart(Calendar.getInstance());
			lifeObserver.create(lifeCycle);
			id = lifeCycle.getId();
		} catch (Exception e) {
			throw new WebServiceException(e);
		}
	}
}
