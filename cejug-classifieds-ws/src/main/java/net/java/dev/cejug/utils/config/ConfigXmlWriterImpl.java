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
package net.java.dev.cejug.utils.config;

import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationEventHandler;

/**
 * @author $Author: felipegaucho $
 * @version $Rev: 504 $ ($Date: 2008-08-24 11:22:52 +0200 (Sun, 24 Aug 2008) $)
 */
class ConfigXmlWriterImpl<T> implements ConfigXmlWriter<T> {

	private final transient ValidationEventHandler handler;

	ConfigXmlWriterImpl(final ValidationEventHandler handler) {
		this.handler = handler;
	}

	/**
	 * WARNING: it never store passwords.
	 * 
	 * @throws JAXBException
	 */
	public void write(final JAXBElement<T> config, final String context,
			final Writer writer, final String schemaLocation)
			throws JAXBException {
		JAXBContext jaxbContext;
		// try {
		jaxbContext = JAXBContext.newInstance(context, Thread.currentThread()
				.getContextClassLoader());
		Marshaller marshaller;
		marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, schemaLocation);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setEventHandler(handler);
		marshaller.marshal(config, writer);
		/*
		 * } catch (Exception exception) { throw new
		 * Exception(exception.getMessage(), exception); }
		 */
	}
}
