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
public class ScreeningBooking {
	private ObjectFactory objectFactory = new ObjectFactory();
	BookingResponse bookingRequest;
	BookingRequest b;
	BookingResponse debugResponse = new BookingResponse();
	BookingResponse response;
	
	public String message = "";
	public String getPnr() {
		return debugResponse.getPnr().toString();
	}

	public String getBesDat() {
		return debugResponse.getBesDat().toString();
	}

	public boolean getDebugBooking(LoginCredentials loginCredentials) {
		String _pnr = loginCredentials.getPnr();
		String _passwd = loginCredentials.getPasswd();
		JAXBElement<String> pnr = objectFactory.createBookingRequestPnr(_pnr);
		JAXBElement<String> besDat = objectFactory.createBookingRequestBesDat("debug");

		if("123456-1234".equals(_pnr) && "qwerty".equals(_passwd)) {
			pnr = objectFactory.createBookingRequestPnr(_pnr);
			besDat = objectFactory.createBookingRequestBesDat("debug");
			debugResponse.setPnr(pnr);
			debugResponse.setBesDat(besDat);
			return true;
		}
		else {
			pnr = objectFactory.createBookingRequestPnr("nologin");
			besDat = objectFactory.createBookingRequestBesDat("debug");
			debugResponse.setPnr(pnr);
			debugResponse.setBesDat(besDat);
			return false;
		}
	}

	public	boolean getBookingBoolean(LoginCredentials loginCredentials) {
		//"parameters"
		JAXBElement<String> pnr = objectFactory.createBookingRequestPnr(loginCredentials.getPnr());
		JAXBElement<String> pin = objectFactory.createBookingRequestPin(loginCredentials.getPasswd());
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
			return true;
		}

		catch(ICentralBookingWSGetBookingICFaultFaultFaultMessage ex){
			ex.printStackTrace();		
			return false;
		}
	}

	private void debug(BookingRequest r) {
		Log4JLogger log = new Log4JLogger();

		log.debug(r.toString());
	}
}
