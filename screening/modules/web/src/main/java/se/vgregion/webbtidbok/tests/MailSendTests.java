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
/**
 * 
 */
package se.vgregion.webbtidbok.tests;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;

//import org.springframework.mail.MailSender;
//import org.springframework.mail.SimpleMailMessage;

import javax.mail.*;
import javax.mail.*; 
import javax.mail.internet.*; 
import javax.activation.*; 
import java.io.*; 
import java.util.Properties;


/**
 * @author conpem
 *
 */
public class MailSendTests {

	
	public void sendMail(String mailServer, String from, String to,String subject, String messageBody,String[] attachments) throws 
		MessagingException, AddressException 
		{ 
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

			//use a MimeMultipart as we need to handle the file attachments 
			Multipart multipart = new MimeMultipart(); 

			//add the message body to the mime message 
			multipart.addBodyPart(messageBodyPart); 

			// add any file attachments to the message 
			//addAttachments(attachments, multipart); 

			// Put all message parts in the message 
			message.setContent(multipart); 

			// Send the message 
			Transport.send(message); 


		} 

	protected void addAttachments(String[] attachments, Multipart multipart) 
    throws MessagingException, AddressException 
    { 
			for(int i = 0; i<= attachments.length -1; i++) 
			{ 
				String filename = attachments[i]; 
				MimeBodyPart attachmentBodyPart = new MimeBodyPart(); 

				//use a JAF FileDataSource as it does MIME type detection 
				DataSource source = new FileDataSource(filename); 
				attachmentBodyPart.setDataHandler(new DataHandler(source)); 

				//assume that the filename you want to send is the same as the 
				//actual file name - could alter this to remove the file path 
				attachmentBodyPart.setFileName(filename); 

				//add the attachment 
				multipart.addBodyPart(attachmentBodyPart); 
			} 
    } 

	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
	}
	
	
	public Properties getProperties() throws IOException,FileNotFoundException{
		
		Properties props = new Properties();
		//props.load(new FileInputStream("se\\vgregion\\webbtidbok\\config\\mail.properties"));
		//D:\EclipseProjects2\workspace\oppna-program-webbtidbok\screening\modules\web
		//D:\EclipseProjects2\workspace\oppna-program-webbtidbok\screening\modules\web\src\main\java\se\vgregion\webbtidbok\config
		props.load(new FileInputStream("D:\\EclipseProjects2\\workspace\\oppna-program-webbtidbok\\screening\\modules\\web\\src\\main\\java\\se\\vgregion\\webbtidbok\\config\\mail.properties"));
		String smtpServer = (String) props.get("server.host");
		String fromEmail = (String) props.get("server.fromemail");
		
		System.out.println(smtpServer);
		System.out.println(fromEmail);
		return props;

	}
	
	
	 
	@Test
	public void testSendMail(){
		try 
        { 
			
			Properties props = getProperties();
			
			String smtpServer = (String) props.get("server.host");
			String fromEmail = (String) props.get("server.fromemail");
			
			System.out.println("testSendMail.smtpServer=" + smtpServer);
			System.out.println("testSendMail.smtpServer=" + fromEmail);
			
			
            //MailClient client = new MailClient(); 
            String server= (String) props.get("server.host"); 
            String from= (String) props.get("server.fromemail"); 
            String to = "conny.pemfors@create.se"; 
            String subject="Test Email Client"; 
            String message="Test Email Cliet"; 
            String[] filenames = {"D:\\tmp\\test.txt"}; 
         
            sendMail(server,from,to,subject,message,filenames); 
            
            Assert.assertTrue(true);
        } 
        catch(Exception e) 
        { 
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
