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
import se.vgregion.webbtidbok.ws.*;
import java.util.*;
import java.io.*;

import se.vgregion.webbtidbok.gui.*;
import javax.faces.model.*;


public class BookingService
{
	
	BookingResponse response;
	BookingRequest request;
	WebServiceHelper helper = new WebServiceHelper();
	
	
	//private String orderDate;
	
	//constructor
	public BookingService(){}
	
	public BookingResponseLocal getBooking(State loginCredentials){
		
//		Uncomment below for debug, you'll only have to click login, 
//		creds below are hard coded.
//		String pnr = "19960103-2395";
//		String psw = "Y8PBZRUr";
//		loginCredentials.setPnr(pnr);
//		loginCredentials.setPasswd(psw);
//		loginCredentials.setLoggedIn(true);
		
		if(loginCredentials.isLoggedIn()){
			
			request = helper.getQueryWSRequest(loginCredentials);
			response = helper.getQueryWS(request);
			BookingResponseLocal responseLocal = new BookingResponseLocal(response);
			
			return responseLocal;
		}
		
		return null;
	}
	
	
	public List<BookingPlaceLocal> getBookingPlace(State loginCredentials){
		
		//		Uncomment below for debug, you'll only have to click login, 
		//		creds below are hard coded.
		//		String pnr = "19960103-2395";
		//		String psw = "Y8PBZRUr";
		//		loginCredentials.setPnr(pnr);
		//		loginCredentials.setPasswd(psw);
		//		loginCredentials.setLoggedIn(true);
		List<BookingPlaceLocal> placeListLocal = new ArrayList<BookingPlaceLocal>();
		
		if(loginCredentials.isLoggedIn()){
			
			request = helper.getQueryWSRequest(loginCredentials);
			ArrayOfBookingPlace places = helper.getQueryWSRequestPlaces(request);
			//response = helper.getQueryWS(request);g
			List<BookingPlace> placeList = places.getBookingPlace();
			for(BookingPlace p : placeList){
				BookingPlaceLocal pl = new BookingPlaceLocal();
				pl.setCentralTimeBookId(p.getCentralTidbokID());
				pl.setAddress(p.getAddress().getValue());
				pl.setClinic(p.getMottagning().getValue());
				
				placeListLocal.add(pl);
				
				System.out.println(pl.toString());
				
			}
			
			System.out.println("size: " + placeListLocal.size());
			
			return placeListLocal;
		}
		
		return placeListLocal;
	}
	
	/*Method setting default value for BookingResponse, CentralTimeBookingId
	 * 
	 * 
	 */
	
	public int getSelectedDefaultItem(State loginCredentials){
		
		request = helper.getQueryWSRequest(loginCredentials);
		response = helper.getQueryWS(request);
		BookingResponseLocal responseLocal = new BookingResponseLocal(response);
		int centralTimeBookingId =responseLocal.getCentralTimeBookId();
		return centralTimeBookingId;
	}

	public List<SelectItem> getBookingPlaceSelectItems(State loginCredentials){
		
		//		Uncomment below for debug, you'll only have to click login, 
		//		creds below are hard coded.
		//		String pnr = "19960103-2395";
		//		String psw = "Y8PBZRUr";
		//		loginCredentials.setPnr(pnr);
		//		loginCredentials.setPasswd(psw);
		//		loginCredentials.setLoggedIn(true);
		List<BookingPlaceLocal> placeListLocal = new ArrayList<BookingPlaceLocal>();
		
		if(loginCredentials.isLoggedIn()){
			
			request = helper.getQueryWSRequest(loginCredentials);
			ArrayOfBookingPlace places = helper.getQueryWSRequestPlaces(request);
			//response = helper.getQueryWS(request);g
			List<BookingPlace> placeList = places.getBookingPlace();
			for(BookingPlace p : placeList){
				BookingPlaceLocal pl = new BookingPlaceLocal();
				pl.setCentralTimeBookId(p.getCentralTidbokID());
				pl.setAddress(p.getAddress().getValue());
				pl.setClinic(p.getMottagning().getValue());
				
				placeListLocal.add(pl);
				
				System.out.println(pl.toString());
				
			}
			
			System.out.println("size: " + placeListLocal.size());
			SelectItemConverter sc = new SelectItemConverter();
			
			return sc.getSelectItems(placeListLocal);
		}
		
		return null;
	}
	
	
	
}
