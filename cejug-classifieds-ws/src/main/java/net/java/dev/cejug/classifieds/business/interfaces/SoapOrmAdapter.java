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
package net.java.dev.cejug.classifieds.business.interfaces;

/**
 * SOAP &lt;-&gt; ORM mapping interface. The model of persistence layer is
 * different from the model used on the service contract. Due to that, it is
 * necessary a transformation layer, the one that should implement this
 * interface.
 * 
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 786 $ ($Date: 2008-10-31 22:34:32 +0100 (Fri, 31 Oct 2008) $)
 */
public interface SoapOrmAdapter<SoapType, EntityType> {

	/**
	 * Copies the attribute values from an Entity to an object that can be
	 * serialized in SOAP messages.
	 * 
	 * @param type
	 *            the JPA entity.
	 * @return a soap object containing the values of the entity.
	 */
	SoapType toSoap(EntityType type) throws IllegalStateException,
			IllegalArgumentException;

	/**
	 * Copies the attribute values from an object that can be serialized in SOAP
	 * messages into an Entity object.
	 * 
	 * @param type
	 *            a soap object containing the values of the entity.
	 * @return the JPA entity.
	 * @throws IllegalArgumentException
	 *             when one of the objects do not match the expected type.
	 * @throws IllegalStateException
	 *             property copying failure.
	 */
	EntityType toEntity(SoapType type) throws IllegalStateException,
			IllegalArgumentException;
}
