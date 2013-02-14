package net.java.dev.cejug.classifieds.login.resource;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.dev.cejug.classifieds.login.entity.facade.client.RegistrationConstants;
import net.java.dev.cejug.classifieds.login.entity.facade.client.UserFacadeLocal;

public class CheckEmailOrLogin extends HttpServlet {
	/** <code>serialVersionUID = {@value}</code>. */
	private final static long serialVersionUID = -6026937020915831338L;

	@EJB
	private UserFacadeLocal local;

	/** {@inheritDoc} */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String login = request
				.getParameter(RegistrationConstants.LOGIN.value());
		String email = request
				.getParameter(RegistrationConstants.EMAIL.value());
		if (login == null && email == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"parameters are missed.");
		}
		if (login != null && !local.isLoginAvailable(login)) {
			response.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,
					"login " + login + " already in use.");
		}

		if (email != null && !local.isEmailAvailable(email)) {
			response.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,
					"email " + email + " already in use.");
		}
		PrintWriter out = response.getWriter();
		out.print("OK");
	}

	/** {@inheritDoc} */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		super.doPost(req, resp);
	}
}
