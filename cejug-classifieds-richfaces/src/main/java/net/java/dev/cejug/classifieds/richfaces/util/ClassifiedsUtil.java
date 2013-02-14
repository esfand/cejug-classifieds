package net.java.dev.cejug.classifieds.richfaces.util;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class ClassifiedsUtil {

	public static void addMessageFromResourceBundle(String messageKey,
			Severity typeMessage, Exception exception) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle("messages",
				facesContext.getViewRoot().getLocale());
		String message = bundle.getString(messageKey);

		facesContext.addMessage(null, new FacesMessage(typeMessage, message,
				null));

		if (exception != null) {
			facesContext.getExternalContext().log(message, exception);
		}
	}

	public static void addMessage(String messageText, Severity typeMessage,
			Exception exception) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		facesContext.addMessage(null, new FacesMessage(typeMessage,
				messageText, null));

		if (exception != null) {
			facesContext.getExternalContext().log(messageText, exception);
		}
	}
}
