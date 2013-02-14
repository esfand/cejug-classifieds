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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBException;

import net.java.dev.cejug.utils.config.ConfigXmlReader;
import net.java.dev.cejug.utils.config.XmlStreamFactory;
import net.java.dev.cejug_classifieds.server.config.ClassifiedsServerConfig;

import org.xml.sax.SAXException;

/**
 * An utility class to load the configuration file. It keeps the latest loaded
 * configuration. The factory facade allows the consumers to get the last loaded
 * configuration or to force the factory to reload the configuration. The
 * loading of the configuration is thread safe.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 578 $ ($Date: 2008-09-03 19:58:27 +0200 (Wed, 03 Sep 2008) $)
 */
public final class ConfigLoader {
	public static final String CONFIG = "config.xml";
	public static final String CONFIG_SCHEMA = "https://cejug-classifieds.dev.java.net/ws/schema/config/config.xsd";
	public static final String UTF8 = "UTF-8";
	public static final String DEFAULT_CONTEXT = ClassifiedsServerConfig.class
			.getPackage().getName();

	private transient ClassifiedsServerConfig lastLoaded = null;

	private static ConfigLoader instance = new ConfigLoader();

	public static ConfigLoader getInstance() {
		return instance;
	}

	private ConfigLoader() {
	}

	public ClassifiedsServerConfig reload() throws MalformedURLException,
			JAXBException, SAXException {
		return this.load();

	}

	public ClassifiedsServerConfig load() throws MalformedURLException,
			JAXBException, SAXException {
		if (lastLoaded == null) {
			XmlStreamFactory<ClassifiedsServerConfig> factory = new XmlStreamFactory<ClassifiedsServerConfig>();
			ConfigXmlReader<ClassifiedsServerConfig> reader = factory
					.getReader(new ConfigUnmarshallerListener(), null);
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			synchronized (this) {
				InputStream stream = loader.getResourceAsStream(CONFIG);
				InputStreamReader streamReader = new InputStreamReader(stream,
						Charset.forName(UTF8));
				lastLoaded = reader.read(streamReader, DEFAULT_CONTEXT,
						new URL(CONFIG_SCHEMA)).getValue();
			}
		}
		return lastLoaded;
	}
}
