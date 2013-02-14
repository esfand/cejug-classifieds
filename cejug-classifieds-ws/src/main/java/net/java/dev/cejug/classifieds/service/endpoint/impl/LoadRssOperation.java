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

import generated.Category;
import generated.Guid;
import generated.Image;
import generated.ObjectFactory;
import generated.Rss;
import generated.RssChannel;
import generated.RssItem;
import generated.Source;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.business.interfaces.LoadRssOperationLocal;
import net.java.dev.cejug.classifieds.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.entity.CategoryEntity;
import net.java.dev.cejug.classifieds.entity.facade.AdvertisementFacadeLocal;
import net.java.dev.cejug.classifieds.entity.facade.CategoryFacadeLocal;
import net.java.dev.cejug_classifieds.metadata.business.SyndicationFilter;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1148 $ ($Date: 2009-02-01 15:59:06 +0100 (Sun, 01 Feb 2009) $)
 * @see <a href=
 *      'http://www.rssboard.org/rss-specification'>http://www.rssboard.org/rss-
 *      s p e c i f i c a t i o n < / a >
 */
@Stateless
public class LoadRssOperation implements LoadRssOperationLocal {
	/**
	 * GMT date format, used to print the XML dates in GMT format: {@value} .
	 */
	private static final String GMT_DATE_PATTERN = "EEE, d MMM yyyy HH:mm:ss z";

	/**
	 * Persistence façade of Advertisement entities.
	 */
	@EJB
	private transient AdvertisementFacadeLocal advFacade;

	@EJB
	private transient CategoryFacadeLocal categoryFacade;
	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private static final Logger logger = Logger.getLogger(
			LoadRssOperation.class.getName(), "i18n/log");

	/**
	 * Produces an RSS document with the latest active advertisements in the
	 * database. The response XML document should have the same format as the <a
	 * href='http://cyber.law.harvard.edu/rss/examples/rss2sample.xml'> sample
	 * RSS</a> below:
	 * 
	 * <pre>
	 * &lt;?xml version=&quot;1.0&quot;?&gt;
	 * &lt;rss version=&quot;2.0&quot;&gt;
	 *    &lt;RssChannel&gt;
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
	 *       &lt;RssItem&gt;
	 * 
	 *          &lt;title&gt;Star City&lt;/title&gt;
	 *          &lt;link&gt;http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp&lt;/link&gt;
	 *          &lt;description&gt;How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's &lt;a href=&quot;http://howe.iki.rssi.ru/GCTC/gctc_e.htm&quot;&gt;Star City&lt;/a&gt;.&lt;/description&gt;
	 *          &lt;pubDate&gt;Tue, 03 Jun 2003 09:39:21 GMT&lt;/pubDate&gt;
	 *          &lt;guid&gt;http://liftoff.msfc.nasa.gov/2003/06/03.html#RssItem573&lt;/guid&gt;
	 * 
	 *       &lt;/RssItem&gt;
	 *    &lt;/RssChannel&gt;
	 * &lt;/rss&gt;
	 * </pre>
	 * 
	 * @return an XML document containing the advetisements RSS.
	 */
	public Rss loadRssOperation(SyndicationFilter filter) {
		try {
			ObjectFactory factory = new ObjectFactory();
			Rss rssFeed = factory.createRss();
			rssFeed.getOtherAttributes().put(new QName("", "version"), "2.0");
			RssChannel channel = new RssChannel();
			List<Object> channelAttributes = channel
					.getTitleOrLinkOrDescription();
			channelAttributes.add(factory
					.createRssChannelCopyright("2008 @ CEJUG Classifieds"));
			channelAttributes
					.add(factory
							.createRssChannelLink("http://fgaucho.dyndns.org:8080/cejug-classifieds-server/rss"));
			channelAttributes
					.add(factory
							.createRssChannelDocs("http://www.codeplex.com/rss2schema"));

			DateFormat gmtDateFormatter = new SimpleDateFormat(
					GMT_DATE_PATTERN, Locale.getDefault());

			gmtDateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
			long categoryId = filter.getCategoryId();
			if (categoryId == 0) {
				// TODO: i18n of error messages
				channelAttributes.add(factory
						.createRssChannelTitle("Incorrect request"));
				channelAttributes
						.add(factory
								.createRssChannelDescription("In order to retrieve the "
										+ "advertisements RSS you should provide its Category ID. Below are the available categories."));
				List<CategoryEntity> categories = categoryFacade.readAll();

				List<RssItem> itens = channel.getItem();
				for (CategoryEntity entity : categories) {
					RssItem item = new RssItem();
					List<Object> itemAttributes = item
							.getTitleOrDescriptionOrLink();
					itemAttributes
							.add(factory
									.createRssItemAuthor("users@cejug-classifieds.dev.java.net (user)"));
					itemAttributes.add(factory.createRssItemTitle(entity
							.getName()));
					itemAttributes.add(factory.createRssItemDescription(entity
							.getDescription()));
					itemAttributes.add(factory
							.createRssItemLink("./rss?category="
									+ entity.getId()));
					itens.add(item);
				}

			} else {
				CategoryEntity category = categoryFacade.read(categoryId);
				if (category == null) {
					// TODO: i18n of error messages
					channelAttributes.add(factory
							.createRssChannelTitle("Unavailable resource"));
					channelAttributes
							.add(factory
									.createRssChannelDescription("Sorry, there is no category #"
											+ filter.getCategoryId() + "."));

				} else {

					// TODO: converter filter in a map of parameters...
					List<AdvertisementEntity> result = advFacade
							.readByCategory(category.getId());

					channelAttributes.add(factory
							.createRssChannelTitle(category.getName()));
					Category chCategory = factory.createCategory();
					chCategory.setDomain(category.getName());
					chCategory.setValue(category.getDescription());
					channelAttributes.add(factory
							.createRssChannelCategory(chCategory));
					channelAttributes.add(factory
							.createRssChannelDescription(category
									.getDescription()));
					channelAttributes.add(factory
							.createRssChannelGenerator("Cejug-Classifieds"));
					channelAttributes
							.add(factory
									.createRssChannelWebMaster("dev@cejug-classifieds.dev.java.net"));
					channelAttributes
							.add(factory
									.createRssChannelManagingEditor("users@cejug-classifieds.dev.java.net"));

					Image image = factory.createImage();
					image.setDescription("Cejug-Classifieds");
					image.setLink("https://cejug-classifieds.dev.java.net/");
					image.setHeight(150);
					image.setWidth(150);
					image.setTitle("Cejug-Classifieds");
					image
							.setUrl("https://cejug-classifieds.dev.java.net/images/logo.jpg");

					channelAttributes.add(image);

					for (AdvertisementEntity adv : result) {
						RssItem item = new RssItem();
						List<Object> itemAttributes = item
								.getTitleOrDescriptionOrLink();

						itemAttributes
								.add(factory
										.createRssItemAuthor("dev@cejug-classifieds.dev.java.net ("
												+ adv.getCustomer().getLogin()
												+ ")"));
						itemAttributes.add(factory.createRssItemTitle(adv
								.getTitle()));
						Source s = factory.createSource();
						s.setValue(adv.getText());
						itemAttributes.add(factory.createRssItemDescription(adv
								.getSummary()));
						itemAttributes
								.add(factory
										.createRssItemLink("http://fgaucho.dyndns.org:8080/cejug-classifieds-server/rss?adv=23"));

						itemAttributes
								.add(factory
										.createRssItemComments("https://cejug-classifieds.dev.java.net/"));

						itemAttributes.add(factory
								.createRssItemPubDate(gmtDateFormatter
										.format(adv.getStart().getTime())));

						Guid guid = new Guid();
						guid.setIsPermaLink(Boolean.FALSE);
						guid
								.setValue("http://fgaucho.dyndns.org:8080/cejug-classifieds-server/rss#"
										+ adv.getId());
						itemAttributes.add(factory.createRssItemGuid(guid));
						channel.getItem().add(item);
					}
				}
			}
			rssFeed.setChannel(channel);
			return rssFeed;
		} catch (Exception e) {
			// TODO: log
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}
}
