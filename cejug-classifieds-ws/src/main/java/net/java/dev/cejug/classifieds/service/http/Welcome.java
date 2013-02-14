package net.java.dev.cejug.classifieds.service.http;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import net.java.dev.cejug.classifieds.business.interfaces.ClassifiedsAdminLocal;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorQuery;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorResponse;

/**
 * A Servlet used to check if the classifieds were installed successfully.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 671 $ ($Date: 2008-09-15 21:44:06 +0200 (Mon, 15 Sep 2008) $)
 */
public class Welcome extends HttpServlet {

	/** <code>serialVersionUID = {@value}</code>. */
	private final static long serialVersionUID = -6026937020915831338L;

	/**
	 * The reference to the local interface of the Admin Session Bean.
	 */
	@EJB
	private transient ClassifiedsAdminLocal admin;

	/**
	 * Call the Admin's Check Monitor operation and show a welcome JSP page
	 * containing the last startup date of the service.
	 * 
	 * @param request
	 *            the HTTP request - not used for any special purpose.
	 * @param response
	 *            the HTTP response used to print the JSP welcome page in the
	 *            customer browser.
	 * @exception IOException
	 *                used by the superclass request dispatcher to notify
	 *                communication problems.
	 * @exception ServletException
	 *                used by the superclass.
	 */
	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		MonitorQuery query = new MonitorQuery();
		query.setResponseTimeLength(20);
		query.setAlivePeriodsLength(20);
		MonitorResponse monResponse = admin.checkMonitorOperation(query);
		request.setAttribute("monitorResponse", monResponse);
		((HttpServletResponse) response).setHeader("Expires",
				"Thu, 15 Apr 2010 20:00:00 GMT");
		request.getRequestDispatcher("/welcome.jsp").forward(request, response);
	}
}
