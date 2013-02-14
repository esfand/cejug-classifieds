package net.java.dev.cejug.classifieds.richfaces.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * The expires filter adds the expires HTTP header based on the deployment
 * policy. Many sites have a fixed deployment schedule where deployments take
 * place based on timed regular intervals. This filter adds the expires header
 * of the next possible deployment time, to support browser caching.
 * 
 * @author chris.webster@sun.com
 */
public class ExpiresFilter implements Filter {

	private static final String GMT = "GMT";
	private static final String HTTP_EXPIRES_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
	private FilterConfig filterConfig;

	/**
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @param chain
	 *            The filter chain we are processing
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet error occurs
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (response != null) {
			DateFormat httpDateFormat = new SimpleDateFormat(
					HTTP_EXPIRES_FORMAT, Locale.US);
			httpDateFormat.setTimeZone(TimeZone.getTimeZone(GMT));

			int seconds = 3600;

			Calendar cal = new GregorianCalendar();
			cal.roll(Calendar.SECOND, seconds);
			((HttpServletResponse) response).setHeader("Cache-Control",
					"PUBLIC, max-age=" + seconds + ", must-revalidate");
			((HttpServletResponse) response).setHeader("Expires",
					httpDateFormat.format(cal.getTime()));
		}

		chain.doFilter(request, response);
	}

	/**
	 * Return the filter configuration object for this filter.
	 */
	private FilterConfig getFilterConfig() {
		return filterConfig;
	}

	/**
	 * Set the filter configuration object for this filter.
	 * 
	 * @param filterConfig
	 *            The filter configuration object
	 */
	private void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * Destroy method for this filter
	 * 
	 */
	public void destroy() {
	}

	/**
	 * Init method for this filter
	 * 
	 */
	public void init(FilterConfig filterConfig) {
		setFilterConfig(filterConfig);
	}

	/**
	 * Return a String representation of this object.
	 */
	@Override
	public String toString() {
		if (getFilterConfig() == null) {
			return ("ExpiresFilter()");
		}
		StringBuffer sb = new StringBuffer("ExpiresFilter(");
		sb.append(getFilterConfig());
		sb.append(")");
		return (sb.toString());

	}
}
