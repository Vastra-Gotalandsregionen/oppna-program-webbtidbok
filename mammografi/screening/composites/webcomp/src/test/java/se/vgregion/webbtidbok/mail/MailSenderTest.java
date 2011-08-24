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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;

public class MailSenderTest {
	private static MailSetup mailsetup;
	private static MailSender mailsender;
	private static MessageSetup messagesetup;
	private static Booking booking;
	private static State state;
	private static Properties props;
	private Session session;
	private String[] emailaddresses = { "carl.stromhielm@knowit.se", "yes@no.com" };

	final String SMTPHOSTNAME = "mailhost.vgregion.se";
	final String SMTPPORT = "25";
	final String USER = "";
	final String PASS = "";

	final String CANCELATIONMAILSUBJECT = "this is the subject for NU ";
	final String CANCELATIONMAILBODY = "this is the mailbody for NU";
	final String FROMEMAILADDRESS = "doNotReply@doNotReplyNU.se";
	final String CANCELATIONTOEMAILADDRESS = "carl.stromhielm@knowit.se";
	final String SWITCHTOEMAILADDRESS = "newBooking@SU.se";
	final String SOCKETFACTORYCLASS = "javax.net.ssl.SSLSocketFactory";

	@Before
	public void setUp() throws Exception {
		state = new State();
		state.setService("MAMMOGRAFI_NU");
		state.setMessageBundle("messages/mammografi/MammografiMessagesNU");
		state.setPasswd("webbtidbokspassw0rd");
		booking = new BookingSectra();
		booking.setPatientName("Patient X");
		mailsetup = new MailSetup();
		mailsender = new MailSender();
		messagesetup = new MessageSetup();
		props = mailsetup.setUpMailProperties(state);
	}

	@Test
	public void testGetMailProperties() {
		Properties props = new Properties();
		props = mailsender.getMailProperties(state);
		assertNotNull(props);
	}

	@Test
	public void testSetUpMailProperties() {
		props = mailsetup.setUpMailProperties(state);

		assertNotNull(props);

		assertTrue(props.getProperty("mail.smtp.host").equals(SMTPHOSTNAME));
		assertTrue(props.getProperty("mail.smtp.port").equals(SMTPPORT));
		assertTrue(props.getProperty("userName").equals(USER));
		assertTrue(props.getProperty("userKey").equals(PASS));
		assertTrue(props.getProperty("mail.debug").equals("true"));
		assertTrue(props.getProperty("mail.smtp.socketFactory.port").equals(SMTPPORT));
		assertTrue(props.getProperty("mail.smtp.socketFactory.class").equals(SOCKETFACTORYCLASS));
		assertTrue(props.getProperty("mail.smtp.socketFactory").equals("false"));
	}

	@Test
	public void testGetToAddresses() {
		InternetAddress[] ia = new InternetAddress[emailaddresses.length];
		ia = messagesetup.getToAddresses(emailaddresses);
		for (int i = 0; i < ia.length; i++) {

			assertTrue(ia[i].toString().equals(emailaddresses[i].toString()));

		}
	}

	/**
	 * don't really want to try and send mail to smtp server. it's tested implicitly through other tests if however you want to
	 * increase test coverage remove @Ignore annotaion
	 * 
	 * @throws AddressException
	 */
	@Ignore
	@Test
	public void testSendMail() throws AddressException {
		mailsender.setState(state);
		mailsender.setBooking(booking);
		mailsender.sendCancellationMail();
		assertTrue(mailsender.mailSentToServer);
	}
}
