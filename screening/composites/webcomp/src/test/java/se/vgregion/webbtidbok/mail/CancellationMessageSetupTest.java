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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;

public class CancellationMessageSetupTest {
	private static MailSetup mailsetup;
	private static MailSender mailsender;
	private static MessageSetup messagesetup;;
	private static Booking booking;
	private static State state;
	private static Properties props;
	private Session session;
	private String[] emailaddresses = { "test1@email.com", "test2@email.com" };

	final String SMTPHOSTNAME = "smtp.gmail.com";
	final String SMTPPORT = "465";
	final String USER = "test01.knowit@gmail.com";
	final String PASS = "1234Pass";

	final String CANCELATIONMAILSUBJECT = "this is the cancellation subject for NU ";
	final String CANCELATIONMAILBODY = "this is the cancellation mail body for NU";
	final String FROMEMAILADDRESS = "doNotReply@doNotReplyNU.se";
	final String CANCELATIONTOEMAILADDRESS = "carl.stromhielm@knowit.se";
	final String SWITCHTOEMAILADDRESS = "newBooking@SU.se";
	final String SOCKETFACTORYCLASS = "javax.net.ssl.SSLSocketFactory";

	@Before
	public void setUp() throws Exception {

		state = new State();
		state.setService("MAMMOGRAFI_NU");
		state.setMessageBundle("messages/mammografi/MammografiMessagesNU");
		state.setPasswd("SEMUDD000004");
		booking = new BookingSectra();
		booking.setPatientName("Patient X");
		mailsetup = new MailSetup();
		mailsender = new MailSender();
		messagesetup = new MessageSetup();
		props = mailsetup.setUpMailProperties(state);
		session = mailsetup.getSession(props);
	}

	@Ignore
	@Test
	public void testGetMessage() throws MessagingException, IOException {
		Message message = messagesetup.getCancellationMessage(session, state, booking.getPatientName(), booking);
		assertNotNull(message);
		assertTrue((CANCELATIONMAILSUBJECT + booking.getPatientName() + ", " + state.getPasswd()).equals(message.getSubject()));
		assertTrue(FROMEMAILADDRESS.equals(message.getFrom()[0].toString()));
		assertTrue((CANCELATIONMAILBODY + " " + booking.getPatientName() + ", " + state.getPasswd()).equals(message.getContent()
				.toString()));
	}

	/*
	 * This is IGNORED, numerous to addresses will not be used.
	 */
	@Ignore
	@Test
	public void testGetToAddresses() {
		InternetAddress[] toAddresses = messagesetup.getToAddresses(emailaddresses);
	}

	@Test
	public void testGetToAddress() {
		InternetAddress[] toAddress = messagesetup.getToAddress(emailaddresses[0]);
		assertNotNull(toAddress);
		assertEquals(emailaddresses[0], toAddress[0].toString());
	}

}
