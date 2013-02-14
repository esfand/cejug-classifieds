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
package net.java.dev.cejug.classifieds.config;

import java.util.logging.Logger;

import javax.xml.bind.Unmarshaller.Listener;

import net.java.dev.cejug_classifieds.server.config.Injection;

/**
 * This class is used to check the values read from the config XML. Despite JAXB
 * validates the XML against it schema, the contents of the properties file
 * should be verified following the expected ones.
 * 
 * @author Felipe Gaúcho
 */
public class ConfigUnmarshallerListener extends Listener {
	/**
	 * The Unmarshaller logger.
	 */
	private final static Logger logger = Logger
			.getLogger(ConfigUnmarshallerListener.class.getName());

	@Override
	public void afterUnmarshal(final Object target, final Object parent) {
		if (target instanceof Injection) {
			validateServiceImplementation((Injection) target);
		}
		logger.severe("TODO: reimplement the config unmarshaller.");
		// logger.log(Level.SEVERE, key, params);
	}

	/**
	 * If the name of the service implementation was not injected, we assume to
	 * use the reference implementation class.
	 * 
	 * @param injection
	 */
	private void validateServiceImplementation(final Injection serviceImpl) {
		logger.fine(serviceImpl.getAdminSessionBean());
		// String implementation = injection.getServiceImplementation();
		// TODO: under review (design changed)
		/*
		 * if (implementation == null) { injection
		 * .setServiceImplementation(ClassifiedsReferenceImplementation.class
		 * .getName()); } else { try { Class<?> type =
		 * Class.forName(implementation.trim(), false,
		 * this.getClass().getClassLoader()); type.newInstance(); } catch
		 * (ClassNotFoundException e) { logger.severe(String.format( "The
		 * service implementation cannot be found: {0}", implementation)); throw
		 * new WebServiceException(e); } catch (InstantiationException e) {
		 * logger .severe(String .format( "The service implementation cannot be
		 * instantiated: {0}", e.getMessage())); throw new
		 * WebServiceException(e); } catch (IllegalAccessException e) { logger
		 * .severe(String .format(
		 * "Problems trying to instantiate the service implementation: {0}",
		 * e.getMessage())); throw new WebServiceException(e); } }
		 */
	}
}
