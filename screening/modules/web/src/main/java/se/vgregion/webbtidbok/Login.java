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

import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.stereotype.Service;

import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.CentralBookingWS;
import se.vgregion.webbtidbok.ws.GetBooking;
import se.vgregion.webbtidbok.ws.ICentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ObjectFactory;


@Service
public class Login {
	private ObjectFactory objectFactory = new ObjectFactory();
	BookingResponse bookingRequest;
	BookingRequest b;
	BookingResponse debugResponse = new BookingResponse();
	BookingResponse response;
	
	public String message = "";
	public String getPnr() {
		return response.getPnr().toString();
	}

	public String getBesDat() {
		return response.getBokadTid().toString();
	}

	public void logout(State loginCredentials) {
		loginCredentials.setPnr("");
		loginCredentials.setPasswd("");
		loginCredentials.setLoggedIn(false);
	}
	
	public	boolean login(State loginCredentials) {
		//"parameters"
		JAXBElement<String> pnr = objectFactory.createBookingRequestPnr(loginCredentials.getPnr());
		JAXBElement<String> pin = objectFactory.createBookingRequestPin(loginCredentials.getPasswd());
		
//		Zs12JzIW 19 121212-1212
//		JAXBElement<String> pnr = objectFactory.createBookingRequestPnr("19960103-2395");
//		JAXBElement<String> pin = objectFactory.createBookingRequestPin("Y8PBZRUr");

		
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

		//make web service call
		CentralBookingWS centralBookingWS = new CentralBookingWS();
		ICentralBookingWS ws = centralBookingWS.getBasicHttpBindingICentralBookingWS();
		
		try{
			
			response = ws.getBooking(request);
			//loginCredentials.setBookingResponse(response);
			loginCredentials.setLoggedIn(true);
			
			return true;
		}

		catch(ICentralBookingWSGetBookingICFaultFaultFaultMessage ex){
			ex.printStackTrace();
			
			//loginCredentials.setBookingResponse(null);
			int returnCode = ex.getFaultInfo().getRetcode();
			loginCredentials.setLoggedIn(false);
			
			return false;
			
		}
	}

	private void debug(BookingRequest r) {
		Log4JLogger log = new Log4JLogger();

		log.debug(r.toString());
	}
}
