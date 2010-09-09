/**
 * Copyright 2009 Vastra Gotalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 */
package se.vgregion.webbtidbok.mail;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import se.vgregion.webbtidbok.State;

/**
 * This class picks up mail settings from the respective service's messagebundle and pairs them back into the property again under
 * relevan mail.smtp.* keys these properties are then used in the MaileSender class as it provides property as parameter when
 * creating a session
 * 
 * @author carstm
 * 
 */
public class MailSetup {

	public Properties setUpMailProperties(State state) {
		String SMTP_HOST_NAME;
		String SMTP_PORT;
		String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		String user;
		String pass;

		ResourceBundle bundle = ResourceBundle.getBundle(state.getMessageBundle());

		Properties properties = new Properties();

		SMTP_HOST_NAME = bundle.getString("smtpHostName");
		SMTP_PORT = bundle.getString("smtpPort");
		user = bundle.getString("user");
		pass = bundle.getString("pass");

		properties.put("mail.smtp.host", SMTP_HOST_NAME);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.debug", "true");
		properties.put("mail.smtp.port", SMTP_PORT);
		properties.put("mail.smtp.socketFactory.port", SMTP_PORT);
		properties.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		properties.put("mail.smtp.socketFactory", "false");
		properties.put("userName", user);
		properties.put("userKey", pass);

		return properties;
	}

	public ResourceBundle setUpMailResourceBunle(State state) {
		ResourceBundle bundle = ResourceBundle.getBundle(state.getMessageBundle());
		return bundle;
	}

	public Session getSession(Properties mailproperties) {
		final String user = mailproperties.getProperty("userName");
		final String userKey = mailproperties.getProperty("userKey");

		Session session = Session.getDefaultInstance(mailproperties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, userKey);
			}
		});

		return session;
	}

	public static void main(String[] args) {
		State state = new State();
		state.setMessageBundle("MammografiMessagesNU");
		MailSetup ms = new MailSetup();
		ms.setUpMailResourceBunle(state);
	}
}
