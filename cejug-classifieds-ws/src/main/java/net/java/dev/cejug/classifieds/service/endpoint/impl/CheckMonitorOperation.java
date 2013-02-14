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
package net.java.dev.cejug.classifieds.service.endpoint.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.business.interfaces.CheckMonitorOperationLocal;
import net.java.dev.cejug.classifieds.entity.ServiceLifeCycleEntity;
import net.java.dev.cejug.classifieds.entity.facade.ServiceLifeCycleFacadeLocal;
import net.java.dev.cejug_classifieds.metadata.admin.AlivePeriod;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorQuery;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorResponse;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1161 $ ($Date: 2009-02-07 19:49:09 +0100 (Sat, 07 Feb 2009) $)
 */
@Stateless
public class CheckMonitorOperation implements CheckMonitorOperationLocal {

	@EJB
	private transient ServiceLifeCycleFacadeLocal lifeCycleFacade;

	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private static final Logger logger = Logger.getLogger(
			CheckMonitorOperation.class.getName(), "i18n/log");

	public MonitorResponse checkMonitorOperation(final MonitorQuery monitor) {

		// TODO: implement the real database call and response assembly.
		MonitorResponse response;
		response = new MonitorResponse();
		response.setServiceName("Cejug-Classifieds");

		try {
			List<ServiceLifeCycleEntity> alivePeriods = lifeCycleFacade
					.readAll();
			List<AlivePeriod> periods = response.getAlivePeriods();
			for (ServiceLifeCycleEntity lifeCycle : alivePeriods) {
				AlivePeriod period = new AlivePeriod();
				period.setStart(lifeCycle.getStart());
				period.setFinish(lifeCycle.getFinish());
				period.setNote(lifeCycle.getName());
				periods.add(period);
			}
			logger.finest(periods.size() + "periods returned successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
		return response;
	}
}
