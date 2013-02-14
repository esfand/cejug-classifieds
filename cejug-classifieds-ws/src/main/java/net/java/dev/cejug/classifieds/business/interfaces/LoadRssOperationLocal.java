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

import generated.Rss;

import javax.ejb.Local;

import net.java.dev.cejug_classifieds.metadata.business.SyndicationFilter;

/**
 * @author $Author: felipegaucho $
 * @version $Rev $ ($Date: 2008-09-09 15:54:34 +0200 (Tue, 09 Sep 2008) $)
 */
@Local
public interface LoadRssOperationLocal {
	/**
	 * <pre>
	 * &lt;?xml version=&quot;1.0&quot;?&gt;
	 * &lt;rss version=&quot;2.0&quot;&gt;
	 *    &lt;channel&gt;
	 *       &lt;title&gt;Liftoff News&lt;/title&gt;
	 *       &lt;link&gt;http://liftoff.msfc.nasa.gov/&lt;/link&gt;
	 *       &lt;description&gt;Liftoff to Space Exploration.&lt;/description&gt;
	 *       &lt;language&gt;en-us&lt;/language&gt;
	 *       &lt;pubDate&gt;Tue, 10 Jun 2003 04:00:00 GMT&lt;/pubDate&gt;
	 * 
	 *       &lt;lastBuildDate&gt;Tue, 10 Jun 2003 09:41:01 GMT&lt;/lastBuildDate&gt;
	 *       &lt;docs&gt;http://blogs.law.harvard.edu/tech/rss&lt;/docs&gt;
	 *       &lt;generator&gt;Weblog Editor 2.0&lt;/generator&gt;
	 *       &lt;managingEditor&gt;editor@example.com&lt;/managingEditor&gt;
	 *       &lt;webMaster&gt;webmaster@example.com&lt;/webMaster&gt;
	 *       &lt;item&gt;
	 * 
	 *          &lt;title&gt;Star City&lt;/title&gt;
	 *          &lt;link&gt;http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp&lt;/link&gt;
	 *          &lt;description&gt;How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's &lt;a href=&quot;http://howe.iki.rssi.ru/GCTC/gctc_e.htm&quot;&gt;Star City&lt;/a&gt;.&lt;/description&gt;
	 *          &lt;pubDate&gt;Tue, 03 Jun 2003 09:39:21 GMT&lt;/pubDate&gt;
	 *          &lt;guid&gt;http://liftoff.msfc.nasa.gov/2003/06/03.html#item573&lt;/guid&gt;
	 * 
	 *       &lt;/item&gt;
	 *    &lt;/channel&gt;
	 * &lt;/rss&gt;
	 * </pre>
	 */
	Rss loadRssOperation(SyndicationFilter filter);
}
