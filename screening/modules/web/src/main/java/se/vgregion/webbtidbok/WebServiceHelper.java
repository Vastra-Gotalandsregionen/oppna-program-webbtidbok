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


import se.vgregion.webbtidbok.ws.*;

import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.BookingPlace;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.CentralBookingWS;
import se.vgregion.webbtidbok.ws.GetBooking;
import se.vgregion.webbtidbok.ws.ICentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ObjectFactory;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class WebServiceHelper {
	
	private ObjectFactory objectFactory = new ObjectFactory();
	
	//make web service call
	CentralBookingWS centralBookingWS = new CentralBookingWS();
	ICentralBookingWS ws = centralBookingWS.getBasicHttpBindingICentralBookingWS();
	
	//used to send whatever request, response depends on what ws-method was called
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
	
	public BookingRequest getQueryWSRequest(State loginCredentials, int centralTimeBookingId, String fromDatString, String toDatString){
		//"parameters"
		JAXBElement<String> pnr = objectFactory.createBookingRequestPnr(loginCredentials.getPnr());
		JAXBElement<String> pin = objectFactory.createBookingRequestPin(loginCredentials.getPasswd());
		
		//Zs12JzIW 19 121212-1212
		//JAXBElement<String> pnr = objectFactory.createBookingRequestPnr("19121212-1212");
		//JAXBElement<String> pin = objectFactory.createBookingRequestPin("Zs12JzIW");

		
		JAXBElement<String> key = objectFactory.createBookingRequestKey("asd");
		JAXBElement<String> cryptedKey =objectFactory.createBookingRequestCryptedKey("asd");
		JAXBElement<String> cert = objectFactory.createBookingRequestCert("NO");
		
		JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat(fromDatString);
		JAXBElement<String> toDat = objectFactory.createBookingRequestToDat(toDatString);
		
		
		//create request object
		BookingRequest request = objectFactory.createBookingRequest();
		
		
		
		
		//setup request object
		request.setPnr(pnr);
		request.setPin(pin);
		request.setKey(key);
		request.setCryptedKey(cryptedKey);
		request.setCert(cert);
		//set values for getting calendars
		request.setFromDat(fromDat);
		request.setToDat(toDat);
		request.setCentralTidbokID(centralTimeBookingId);
		
		return request;
		
	}
	

	
	public ArrayOfBookingPlace getQueryWSRequestPlaces(BookingRequest request){
		//"parameters"
		//JAXBElement<String> pnr = objectFactory.createBookingRequestPnr(loginCredentials.getPnr());
		//JAXBElement<String> pin = objectFactory.createBookingRequestPin(loginCredentials.getPasswd());
		
		//Zs12JzIW 19 121212-1212
		//JAXBElement<String> pnr = objectFactory.createBookingRequestPnr("19121212-1212");
		//JAXBElement<String> pin = objectFactory.createBookingRequestPin("Zs12JzIW");

		
		//JAXBElement<String> key = objectFactory.createBookingRequestKey("asd");
		//JAXBElement<String> cryptedKey =objectFactory.createBookingRequestCryptedKey("asd");
		//JAXBElement<String> cert = objectFactory.createBookingRequestCert("NO");
		
		//make web service call
		CentralBookingWS centralBookingWS = new CentralBookingWS();
		ICentralBookingWS ws = centralBookingWS.getBasicHttpBindingICentralBookingWS();
		
		try{
			
			return ws.getBookingPlace(request);
			//loginCredentials.setBookingResponse(response);
			
		}
		catch(ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			return null;
			
		}
		
		
	}
	
	public ArrayOfCalendar getQueryWSRequestCalendar(BookingRequest request){
		//"parameters"
		//JAXBElement<String> pnr = objectFactory.createBookingRequestPnr(loginCredentials.getPnr());
		//JAXBElement<String> pin = objectFactory.createBookingRequestPin(loginCredentials.getPasswd());
		
		//Zs12JzIW 19 121212-1212
		//JAXBElement<String> pnr = objectFactory.createBookingRequestPnr("19121212-1212");
		//JAXBElement<String> pin = objectFactory.createBookingRequestPin("Zs12JzIW");

		
		//JAXBElement<String> key = objectFactory.createBookingRequestKey("asd");
		//JAXBElement<String> cryptedKey =objectFactory.createBookingRequestCryptedKey("asd");
		//JAXBElement<String> cert = objectFactory.createBookingRequestCert("NO");
		
		//make web service call
		CentralBookingWS centralBookingWS = new CentralBookingWS();
		ICentralBookingWS ws = centralBookingWS.getBasicHttpBindingICentralBookingWS();
		
		try{
			
			return ws.getCalandar(request);
			//loginCredentials.setBookingResponse(response);
			
		}
		catch(ICentralBookingWSGetCalandarICFaultFaultFaultMessage ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			return null;
			
		}
		
		
	}
	
	
	public ArrayOfBookingTime getQueryWSRequestTime(BookingRequest request){
		//"parameters"
		//JAXBElement<String> pnr = objectFactory.createBookingRequestPnr(loginCredentials.getPnr());
		//JAXBElement<String> pin = objectFactory.createBookingRequestPin(loginCredentials.getPasswd());
		
		//Zs12JzIW 19 121212-1212
		//JAXBElement<String> pnr = objectFactory.createBookingRequestPnr("19121212-1212");
		//JAXBElement<String> pin = objectFactory.createBookingRequestPin("Zs12JzIW");

		
		//JAXBElement<String> key = objectFactory.createBookingRequestKey("asd");
		//JAXBElement<String> cryptedKey =objectFactory.createBookingRequestCryptedKey("asd");
		//JAXBElement<String> cert = objectFactory.createBookingRequestCert("NO");
		
		//make web service call
		CentralBookingWS centralBookingWS = new CentralBookingWS();
		ICentralBookingWS ws = centralBookingWS.getBasicHttpBindingICentralBookingWS();
		
		try{
			
			return ws.getBookingTime(request);
			//loginCredentials.setBookingResponse(response);
			
		}
		catch(ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			return null;
			
		}
		
		
	}
	
	
	
	
	
	//This to get the place of the visit for Screening, ie: Example-hospital AB
	public ArrayOfBookingPlace getBookingPlaceFromWS(BookingRequest request){
		ArrayOfBookingPlace bookingArr = new ArrayOfBookingPlace();
		try {
			bookingArr = ws.getBookingPlace(request);
			return bookingArr;
		} catch (ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	//Info concerning the booking, time, place, location, pnr, name etc
	public BookingResponse getQueryWS(BookingRequest request){	

		
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
	
	
	/*
	 * method cancel booking 
	 * 
	 * @input parameter request
	 */
	public boolean getQueryWSCancelBooking(BookingRequest request){	

		
		try{
			
			//return ws.getBooking(request);
			//loginCredentials.setBookingResponse(response);
			return ws.cancelBooking(request);
			
		}
		catch(ICentralBookingWSCancelBookingICFaultFaultFaultMessage ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			return false;
			
		}
	}
	

}
