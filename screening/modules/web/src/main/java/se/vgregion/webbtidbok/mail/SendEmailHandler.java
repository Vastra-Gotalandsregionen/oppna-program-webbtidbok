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




//import org.springframework.mail.MailSender;
//import org.springframework.mail.SimpleMailMessage;

import javax.mail.*;
import javax.mail.*; 
import javax.mail.internet.*; 
import javax.activation.*; 
import java.io.*; 
import java.util.Properties;
import java.util.*;
import java.io.Serializable;
import javax.mail.Address;



public class SendEmailHandler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String email = "";
	
	public void setEmail(String s){
		email = s;
	}
	
	public String getEmail(){
		return email;
	}
	
	
	
	public SendEmailHandler(){}
	
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
	
	public void sendMail(String sendMailTo){
		try{
			
			
			 Properties properties = getProperties();
			
			 String server= (String) properties.get("server.host"); 
	         String from= (String) properties.get("server.fromemail"); 
	         
	         String subject="Tiden är avbokad"; 
	         String messageBody="Tiden är avbokad"; 
	             
	        //String mailServer, String from, String to,String subject, String messageBody,String[] attachments
	            
			Properties props = System.getProperties(); 
			props.put("mail.smtp.host", server); 
			
			// Get a mail session 
			Session session = Session.getDefaultInstance(props, null); 

			// Define a new mail message 
			Message message = new MimeMessage(session); 
			message.setFrom(new InternetAddress(from)); 
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendMailTo)); 
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
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	
	
	
	
	
	
}
