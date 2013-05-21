/**
 * Copyright 2010 Västra Götalandsregionen
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
 *
 */

package se.vgregion.webbtidbok.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import org.apache.log4j.Logger;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;

public class MailSender implements Runnable {

	/**
	 * Fetches the message subject and body from current used message bundle completes the mail subject and mail body with the
	 * users name from the state
	 * 
	 * @param session
	 * @param state
	 * @return
	 */

	private State state;
	private Booking booking;
	private String elvisError;
	boolean mailSentToServer = false;
	Logger log = Logger.getLogger("MailSender.class");

	public MailSender() {

	}

	public MailSender(State state, Booking booking) {
		this.state = state;
		this.booking = booking;
	}

	MailSetup mailSetup = new MailSetup();
	MessageSetup messageSetup = new MessageSetup();

	public Properties getMailProperties(State state) {

		Properties properties = mailSetup.setUpMailProperties(state);
		return properties;
	}

	public void sendElvisErrorMail(String elvisError) {
		this.elvisError = elvisError;
		run();
	}

	public void sendCancellationMail() {
		run();
	}

	/**
	 * If isSwitched is set to true in the Booking object the mail sent is a Switch Location Mail. If isSwitched is set to default
	 * value false in the Booking object corresponding to current user the Cancellation Mail will be sent.
	 */
	@Override
	public void run() {

		boolean debug = true;

		Properties mailProperties = getMailProperties(state);
		Session session = mailSetup.getSession(mailProperties);
		session.setDebug(debug);
		Message messageToSend = null;

		if (!state.getService().equalsIgnoreCase("BUKAORTA")) {
			String patientName = booking.getPatientName();
			if (booking.isSwitchedSurgery()) {
				messageToSend = messageSetup.getSwitchLocationMessage(session, state, patientName, booking);

			} else if (!booking.isSwitchedSurgery()) {
				messageToSend = messageSetup.getCancellationMessage(session, state, patientName, booking);

			}
			try {
				Transport.send(messageToSend);
				mailSentToServer = true;
				mailSentToSMTPLogger("**** SWITCH OR CANCELLATION mail sent to SMTP.");
			} catch (MessagingException e) {
				e.printStackTrace();
				mailSentToServer = false;
				mailSentToSMTPLogger("---- SWITCH OR CANCELLATION mail NOT sent to SMTP.");
			}
		} else if (state.getService().equalsIgnoreCase("BUKAORTA")) {
			messageToSend = messageSetup.getElvisErrorNotificationMessage(session, state, elvisError);
			try {
				Transport.send(messageToSend);
				mailSentToSMTPLogger("**** Elvis ERROR mail sent to SMTP.");
			} catch (MessagingException e) {
				e.printStackTrace();
				mailSentToServer = false;
				mailSentToSMTPLogger("---- Elvis ERROR  mail NOT sent to SMTP.");
			}
			mailSentToServer = true;
		}
	}

	private void mailSentToSMTPLogger(String message) {
		log.info(message);
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
}
