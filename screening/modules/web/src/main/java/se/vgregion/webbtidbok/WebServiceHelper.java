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
package se.vgregion.webbtidbok;

import javax.xml.bind.JAXBElement;

import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.CentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingICFaultFaultFaultMessage;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.stereotype.Service;

import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.CentralBookingWS;
import se.vgregion.webbtidbok.ws.GetBooking;
import se.vgregion.webbtidbok.ws.ICentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ObjectFactory;

import java.io.*;


public class WebServiceHelper {
	
	private ObjectFactory objectFactory = new ObjectFactory();
	
	public BookingRequest getQueryWSRequest(State loginCredentials){
		//"parameters"
		JAXBElement<String> pnr = objectFactory.createBookingRequestPnr(loginCredentials.getPnr());
		JAXBElement<String> pin = objectFactory.createBookingRequestPin(loginCredentials.getPasswd());
		
		//Zs12JzIW 19 121212-1212
		//JAXBElement<String> pnr = objectFactory.createBookingRequestPnr("19121212-1212");
		//JAXBElement<String> pin = objectFactory.createBookingRequestPin("Zs12JzIW");

		
		JAXBElement<String> key = objectFactory.createBookingRequestKey("asd");
		JAXBElement<String> cryptedKey =objectFactory.createBookingRequestCryptedKey("asd");
		JAXBElement<String> cert = objectFactory.createBookingRequestCert("NO");
		
		//create request object
		BookingRequest request = objectFactory.createBookingRequest();

		//setup request object
		request.setPnr(pnr);
		request.setPin(pin);
		request.setKey(key);
		request.setCryptedKey(cryptedKey);
		request.setCert(cert);

		
		return request;
		
	}
	
	public BookingResponse getQueryWS(BookingRequest request){
		
		
		//make web service call
		CentralBookingWS centralBookingWS = new CentralBookingWS();
		ICentralBookingWS ws = centralBookingWS.getBasicHttpBindingICentralBookingWS();
		
		try{
			
			return ws.getBooking(request);
			//loginCredentials.setBookingResponse(response);
			
		}
		catch(ICentralBookingWSGetBookingICFaultFaultFaultMessage ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			return null;
			
		}
	}
	
	
}