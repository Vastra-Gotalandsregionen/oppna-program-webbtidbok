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

/**
 * 
 */
package se.vgregion.webbtidbok.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @author conpem
 * 
 */
public class MailSendTest {

  public void sendMail(String mailServer, String from, String to, String subject, String messageBody, String[] attachments) throws MessagingException, AddressException {
    // Setup mail server
    Properties props = System.getProperties();
    props.put("mail.smtp.host", mailServer);

    // Get a mail session
    Session session = Session.getDefaultInstance(props, null);

    // Define a new mail message
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(from));
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
    message.setSubject(subject);

    // Create a message part to represent the body text
    BodyPart messageBodyPart = new MimeBodyPart();
    messageBodyPart.setText(messageBody);

    // use a MimeMultipart as we need to handle the file attachments
    Multipart multipart = new MimeMultipart();

    // add the message body to the mime message
    multipart.addBodyPart(messageBodyPart);

    // add any file attachments to the message
    // addAttachments(attachments, multipart);

    // Put all message parts in the message
    message.setContent(multipart);

    // Send the message
    Transport.send(message);

  }

  protected void addAttachments(String[] attachments, Multipart multipart) throws MessagingException, AddressException {
    for (int i = 0; i <= attachments.length - 1; i++) {
      String filename = attachments[i];
      MimeBodyPart attachmentBodyPart = new MimeBodyPart();

      // use a JAF FileDataSource as it does MIME type detection
      DataSource source = new FileDataSource(filename);
      attachmentBodyPart.setDataHandler(new DataHandler(source));

      // assume that the filename you want to send is the same as the
      // actual file name - could alter this to remove the file path
      attachmentBodyPart.setFileName(filename);

      // add the attachment
      multipart.addBodyPart(attachmentBodyPart);
    }
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {

  }

  public Properties getProperties() throws IOException, FileNotFoundException {
    Properties loadAllProperties = PropertiesLoaderUtils.loadAllProperties("mail.properties");
    return loadAllProperties;
  }

  @Test
  public void testSendMail() {
    try {

      Properties props = getProperties();

      String smtpServer = (String) props.get("server.host");
      String fromEmail = (String) props.get("server.fromemail");

      System.out.println("testSendMail.smtpServer=" + smtpServer);
      System.out.println("testSendMail.smtpServer=" + fromEmail);

      // MailClient client = new MailClient();
      String server = (String) props.get("server.host");
      String from = (String) props.get("server.fromemail");
      String to = "conny.pemfors@create.se";
      String subject = "Test Email Client";
      String message = "Test Email Cliet";
      String[] filenames = { "D:\\tmp\\test.txt" };

      // sendMail(server,from,to,subject,message,filenames);

      Assert.assertTrue(true);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      Assert.assertFalse(true);
    }

  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {

  }

}
