package net.java.dev.cejug.classifieds.login.entity.facade.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for registration.constants.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name=&quot;registration.constants&quot;&gt;
 *   &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}string&quot;&gt;
 *     &lt;enumeration value=&quot;name&quot;/&gt;
 *     &lt;enumeration value=&quot;email&quot;/&gt;
 *     &lt;enumeration value=&quot;login&quot;/&gt;
 *     &lt;enumeration value=&quot;password&quot;/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "registration.constants")
@XmlEnum
public enum RegistrationConstants {
	@XmlEnumValue("name")
	NAME("name"), @XmlEnumValue("registrationMessage")
	REGISTRATION_MSG("registrationMessage"), @XmlEnumValue("email")
	EMAIL("email"), @XmlEnumValue("login")
	LOGIN("login"), @XmlEnumValue("password")
	PASSWORD("password"), @XmlEnumValue("registrationSubject")
	REGISTRATION_SUBJECT("registrationSubject"), @XmlEnumValue("confirmationUrl")
	CONFIRMATION_BASE_URL("confirmationUrl"), @XmlEnumValue("key")
	CONFIRMATION_KEY("key");
	private final String value;

	RegistrationConstants(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static RegistrationConstants fromValue(String v) {
		for (RegistrationConstants c : RegistrationConstants.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
