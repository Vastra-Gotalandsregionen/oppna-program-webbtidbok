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
import java.util.ResourceBundle;

import javax.mail.Session;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.State;

public class MailSetupTest {

	private MailSetup mailsetup;
	private State state;
	private Properties props;
	final String SMTPHOSTNAME = "mailhost.vgregion.se";
	final String SMTPPORT = "25";
	final String USER = "";
	final String PASS = "";

	final String SOCKETFACTORYCLASS = "javax.net.ssl.SSLSocketFactory";

	@Before
	public void setUp() throws Exception {
		state = new State();
		state.setService("MAMMOGRAFI_NU");
		state.setMessageBundle("messages/mammografi/MammografiMessagesNU");
		mailsetup = new MailSetup();
		props = mailsetup.setUpMailProperties(state);
	}

	@Test
	public void testSetUpMailProperties() {
		assertNotNull(props);
		System.out.println("props.getProperty(\"smtpHostName\"): " + props.getProperty("smtpHostName"));
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
	public void testSetUpMailResourceBunle() {
		ResourceBundle bundle = mailsetup.setUpMailResourceBundle(state);
		assertNotNull(bundle);
	}

	@Test
	public void testGetSession() {
		Session session = mailsetup.getSession(props);
		assertNotNull(session);
	}
}
