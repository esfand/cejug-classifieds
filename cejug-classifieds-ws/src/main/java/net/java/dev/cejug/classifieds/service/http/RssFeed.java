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
package net.java.dev.cejug.classifieds.service.http;

import generated.Rss;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.java.dev.cejug.classifieds.business.interfaces.ClassifiedsBusinessLocal;
import net.java.dev.cejug_classifieds.metadata.business.SyndicationFilter;

/**
 * REST rss feed, to allow other applications to consume the RSS feed directly
 * on the wire, without the need of soap envelopes. READ-ONLY operations are
 * suitable for REST protocol (this is experimental, eventually we will replace
 * this servlet by a jersey code).
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 944 $ ($Date: 2008-12-03 21:34:02 +0100 (Wed, 03 Dec 2008) $)
 */
public class RssFeed extends HttpServlet {

	/** <code>serialVersionUID = {@value}</code>. */
	private final static long serialVersionUID = -6026937020915831338L;

	@EJB
	private transient ClassifiedsBusinessLocal business;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SyndicationFilter filter = new SyndicationFilter();
		String categoryId = request.getParameter("category");
		try {
			filter.setCategoryId(Integer.valueOf(categoryId));
		} catch (NumberFormatException badNumberString) {
			filter.setCategoryId(0);
		}
		Rss rss = business.loadRssOperation(filter);

		// Little trick: http://www.petefreitag.com/item/381.cfm
		String agent = request.getHeader("User-Agent");
		if (agent != null && agent.toUpperCase(Locale.US).contains("MOZILLA")) {
			response.setContentType("text/xml");
		} else {
			response.setContentType("application/rss+xml");
		}

		PrintWriter out = response.getWriter();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Rss.class
					.getPackage().getName(), Thread.currentThread()
					.getContextClassLoader());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			// marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION
			// ,
			// "https://cejug-classifieds.dev.java.net/files/documents/8128/108281/rss.xsd"
			// );
			marshaller.marshal(rss, out);
		} catch (Exception ee) {
			out.print("<error>" + ee.getMessage() + "</error>");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		super.doPost(req, resp);
	}
}
