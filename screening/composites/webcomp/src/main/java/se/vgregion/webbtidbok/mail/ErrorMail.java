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

import java.text.MessageFormat;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * Used to send a mail notification to Insieme when Webbtidboken has problems
 * connecting to any of the web services on startup
 * 
 * @author frelun
 *
 */
public class ErrorMail implements Runnable {
	Logger logger;
	private Properties errorMailProperties;
	private MailSetup mailSetup;
	private ResourceBundle errorMailMessages;
	private String service;
	private String exceptionTrace;
	
	public ErrorMail(ResourceBundle errorMailProperties, String service, Exception e){
		logger = Logger.getLogger("ErrorMail.class");
		this.errorMailMessages = errorMailProperties;
		this.mailSetup = new MailSetup();
		this.errorMailProperties = mailSetup.setUpMailProperties(errorMailProperties);
		this.service = service;
		this.exceptionTrace = e.toString();
	}
	
	private Message buildErrorMessage() throws MessagingException {
		MimeMessage errorMessage = new MimeMessage(mailSetup.getSession(this.errorMailProperties));
		
		errorMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(this.errorMailMessages.getString("serviceFailToEmailAddress")));
		errorMessage.setFrom(new InternetAddress(this.errorMailMessages.getString("serviceFailFromEmailAddress")));
		errorMessage.setSubject(buildErrorMessageSubject(this.service), "utf-8");
		errorMessage.setContent(buildErrorMessageContent(this.service), "text/plain; charset=utf-8");
		
		return errorMessage;
	}
	
	private String buildErrorMessageContent(String service){
		String tmp = MessageFormat.format(this.errorMailMessages.getString("serviceFailContent"), service, exceptionTrace);
		return tmp;
	}
	
	private String buildErrorMessageSubject(String service){
		return MessageFormat.format(this.errorMailMessages.getString("serviceFailSubject"), service);
	}
	
	@Override
	public void run() {
		try {
			Transport.send(buildErrorMessage());
			logger.info("**** SWITCH OR CANCELLATION mail sent to SMTP.");
		} catch (MessagingException e) {
			logger.error("Service unavailable-Mail not sent", e);
		} catch (Exception e){
			logger.error("Service unavailable-Mail not sent, unknown", e);
		}
	}

}
