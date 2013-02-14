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

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1216 $ ($Date: 2009-02-17 20:29:09 +0100 (Tue, 17 Feb 2009) $)
 */
public class AdvertisementCategoryConverter implements Converter, Serializable {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public AdvertisementCategoryWrapper getAsObject(final FacesContext arg0,
			final UIComponent arg1, final String value) {
		try {
			String[] values = value.split(":");
			AdvertisementCategoryWrapper categoryWrapper = new AdvertisementCategoryWrapper();
			categoryWrapper.setId(Long.parseLong(values[0]));
			categoryWrapper.setName(values[1]);
			return categoryWrapper;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String getAsString(final FacesContext arg0, final UIComponent arg1,
			final Object value) {
		String response = null;
		if (value instanceof AdvertisementCategoryWrapper) {
			AdvertisementCategoryWrapper wrapper = (AdvertisementCategoryWrapper) value;
			response = wrapper.getId() + ":" + wrapper.getName();
		}
		return response;
	}

}
