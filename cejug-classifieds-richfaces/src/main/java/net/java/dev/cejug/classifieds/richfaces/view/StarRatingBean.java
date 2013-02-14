/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008-2009 CEJUG - Ceará Java Users Group
 
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

import java.util.logging.Logger;

/**
 * 
 * Advertisement Ratings.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: $ ($Date: 2008-12-26 17:41:33 +0100 (Fri, 26 Dec 2008) $)
 */
public class StarRatingBean {
	private final static Logger logger = Logger.getLogger(StarRatingBean.class
			.getName());

	private String vote;

	public String getVote() {
		return vote;
	}

	public void setVote(final String vote) {
		logger.fine("VOTE: " + vote + "!!!!!!!!!!!!!");
		this.vote = vote;
	}

}
