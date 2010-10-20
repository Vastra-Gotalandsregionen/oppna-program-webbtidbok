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

import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;

/**
 * This class puts together mail subjects and mail messages for cancellation and switch location emails. It uses the message
 * bundle mail resources which are defined in the state.
 * 
 */
public class MessageSetup {

	/**
	 * This method puts together the cancellation mail subject and mail body when a user cancels the appointment all together
	 * 
	 * @param session
	 * @param state
	 *            {@link State}
	 * @param patientName
	 * @param booking
	 *            {@link Booking}
	 * @return msg {@link Message}
	 **/
	public Message getCancellationMessage(Session session, State state, String patientName, Booking booking) {

		String bookedTime = booking.getStartTime().toString();
		MailSetup mailSetup = new MailSetup();
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
			msg.setSubject(bundle.getString("cancelationMailSubject") + patientName + ", " + state.getPasswd());
			msg.setContent(bundle.getString("cancelationMailMailBodyPart1") + " " + patientName
					+ "\n\n Undersökningsnummer: \n\n" + state.getPasswd() + "\n\n Tid som avbokats: \n\n" + bookedTime
					+ "\n\n\n" + bundle.getString("cancelationMailMailBodyPart2"), "text/plain");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return msg;
	}

	/**
	 * This method puts together the switch mail subject and mail body when a user has chosen to switch between areas of operation
	 * (NU to SU or vice versa)
	 * 
	 * @param session
	 * @param state
	 *            {@link State}
	 * @param patientName
	 * @param booking
	 *            {@link Booking}
	 * @return msg {@link Message}
	 **/
	public Message getSwitchLocationMessage(Session session, State state, String patientName, Booking booking) {

		String bookedTime = booking.getStartTime().toString();
		MailSetup mailSetup = new MailSetup();
		ResourceBundle bundle = mailSetup.setUpMailResourceBunle(state);
		Message msg = new MimeMessage(session);
		String switchToSurgery = booking.getSwitchToSurgery().getSurgeryName();
		String switchFromSurgery = booking.getSurgery().getSurgeryName();

		try {
			// This retrieves the current surgery's email. May be used in the future.
			// msg.setRecipients(Message.RecipientType.TO, getToAddress(booking.getSurgery().getSurgeryEmail()));

			// This retrieves and uses the email address which is set in the currently used Message Bundle.
			msg.setRecipients(Message.RecipientType.TO, getToAddress(bundle.getString("switchlocationToEmailAddress")));
			msg.setFrom(new InternetAddress(bundle.getString("switchlocationFromEmailAddress")));
			msg.setSubject(bundle.getString("switchlocationMailSubject") + patientName + ", " + state.getPasswd());
			msg.setContent(bundle.getString("switchlocationMailMailBodyPart1") + " " + patientName
					+ "\n\n Undersökningsnummer: \n\n" + state.getPasswd() + "\n\n Vill omboka sin tid till: " + switchToSurgery
					+ "\n\n Avbokning av tid på: " + switchFromSurgery + "\n\n Tid som avbokats: " + bookedTime
					+ "\n\n Meddela hennes uppgifter till önskad enhet för ny kallelse därifrån." + "\n\n\n"
					+ bundle.getString("cancelationMailMailBodyPart2"), "text/plain");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return msg;
	}

	// TODO: remove this if it isn't used as the probability of only ONE recipient is more likely than two or more recpients
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

	/**
	 * Retrieves the recipients email address for cancellation or switch location emails
	 * 
	 * @param emailaddress
	 * @return toAddress {@link InternetAddress}
	 */
	public InternetAddress[] getToAddress(String emailaddress) {
		InternetAddress[] toAddress = new InternetAddress[1];
		try {
			toAddress[0] = new InternetAddress(emailaddress);
		} catch (AddressException e) {
			e.printStackTrace();
		}
		return toAddress;
	}
}
