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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;

public class MailSender {
	MailSetup mailSetup = new MailSetup();

	public Properties getMailProperties(State state) {

		Properties properties = mailSetup.setUpMailProperties(state);

		return properties;
	}

	/**
	 * Fetches the message subjuect and body from current used message bundle completes the mail subject and mail body with the
	 * users name from the state
	 * 
	 * @param session
	 * @param state
	 * @return
	 */
	public Message getMessage(Session session, State state, String patientName) {

		ResourceBundle bundle = mailSetup.setUpMailResourceBunle(state);
		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = null;

		try {

			addressFrom = new InternetAddress(bundle.getString("fromEmailAddress"));

		} catch (AddressException e) {
			e.printStackTrace();
		}

		try {
			String cancelationAddress = bundle.getString("cancelationToEmailAddress");

			msg.setRecipients(Message.RecipientType.TO, getToAddress(cancelationAddress));
			msg.setFrom(addressFrom);
			msg.setSubject(bundle.getString("cancelationMailSubject") + " " + patientName + ", " + state.getPasswd());
			msg.setContent(bundle.getString("cancelationMailMailBody") + " " + patientName + ", " + state.getPasswd(),
					"text/plain");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return msg;
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

	public InternetAddress[] getToAddresses(String[] emailaddresses) {
		InternetAddress[] toAddresses = new InternetAddress[emailaddresses.length];
		for (int i = 0; i < emailaddresses.length; i++) {
			try {
				toAddresses[i] = new InternetAddress(emailaddresses[i]);
			} catch (AddressException e) {
				e.printStackTrace();
			}
		}
		return toAddresses;
	}

	public InternetAddress[] getToAddress(String emailaddress) {
		InternetAddress[] toAddress = new InternetAddress[1];
		try {
			toAddress[0] = new InternetAddress(emailaddress);
		} catch (AddressException e) {
			e.printStackTrace();
		}
		return toAddress;
	}

	public void sendCancelationMail(State state, Booking booking) throws AddressException {

		boolean debug = true;
		// boolean mailSentToSmtpServer = true;
		String patientName = booking.getPatientName();

		if (!state.getService().equalsIgnoreCase("BUKAORTA")) {
			Properties mailProperties = getMailProperties(state);
			Session session = getSession(mailProperties);
			session.setDebug(debug);
			Message messageToSend = getMessage(session, state, patientName);

			try {

				Transport.send(messageToSend);
				// mailSentToSmtpServer = true;

			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		// return mailSentToSmtpServer;
	}

}
