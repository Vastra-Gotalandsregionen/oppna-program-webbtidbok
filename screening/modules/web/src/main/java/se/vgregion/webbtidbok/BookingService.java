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

import java.util.*;
import java.util.Calendar;
import java.io.*;
import java.lang.*;
import se.vgregion.webbtidbok.gui.*;
import se.vgregion.webbtidbok.lang.DateHandler;

import javax.faces.model.*;


public class BookingService
{
	
	BookingResponse response;
	BookingRequest request;
	WebServiceHelper helper = new WebServiceHelper();
	private ObjectFactory objectFactory = new ObjectFactory();
	
	public boolean isFirstPlaces = true;
	
	//private String orderDate;
	 
	//constructor
	public BookingService(){}
	
	
	public void setFirstPlacesBoolean(boolean b){
		
		isFirstPlaces = b;
		System.out.println("BookingServices.setFirstPlacesBoolean: " + isFirstPlaces);
	}
	
	
	
	public boolean isFirstPlaces(){
		System.out.println("BookingServices.isFirstBoolean: " + isFirstPlaces);

		return isFirstPlaces;
	}
	
	
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
			loginCredentials.setCentralTidbokID(response.getCentralTidbokID());
			//set the Place for States used in getCalendar method
			loginCredentials.setCentralTidbokID(responseLocal.getCentralTimeBookId());
			loginCredentials.setSelectedDate(DateHandler.setCalendarFromGregorianCalendar(response.getBokadTid()));
			
			 
			System.out.println("get month: " + response.getBokadTid().getMonth());
			System.out.println("logincredentials.getSelectedDate: " + loginCredentials.getSelectedDate().getTime().toString());
			
			return responseLocal;
		}
		
		return null;
	}
	
	
	/**
	 * Method cancelBooking returns true if deleted
	 * 
	 * @param loginCredentials
	 * @return
	 */
	public boolean cancelBooking(State loginCredentials){
		
		request = helper.getQueryWSRequest(loginCredentials);
		boolean cancelledBooking = helper.getQueryWSCancelBooking(request);
		
		return cancelledBooking;
		//return true;
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
	
	
	/***
	 * 
	 * 
	 * @param loginCredentials
	 * @return
	 */
	public List<BookingTimeLocal> getBookingTime(State loginCredentials){
		
		//		Uncomment below for debug, you'll only have to click login, 
		//		creds below are hard coded.
		//		String pnr = "19960103-2395";
		//		String psw = "Y8PBZRUr";
		//		loginCredentials.setPnr(pnr);
		//		loginCredentials.setPasswd(psw);
		//		loginCredentials.setLoggedIn(true);
		List<BookingTimeLocal> timeListLocal = new ArrayList<BookingTimeLocal>();
		
		if(loginCredentials.isLoggedIn()){
			java.util.Calendar selectedDate = loginCredentials.getSelectedDate();
			String fromDate = DateHandler.setCalendarDateFormat(selectedDate);
			JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat(fromDate);
			
			System.out.println("fromDate: " + fromDate);
			System.out.println("JAXB-FROMDATE: " + fromDat.getValue());
			
			System.out.println("CentralTidBokID: " + loginCredentials.getCentralTidbokID());
			
			System.out.println("Before Calendar: " + selectedDate.toString());
			
			request = helper.getQueryWSRequest(loginCredentials);
			request.setCentralTidbokID(loginCredentials.getCentralTidbokID());
			request.setFromDat(fromDat);
			
			//JAXBElement<String> fromDat2 = objectFactory.createBookingRequestFromDat("2010-05-30");
			
			//request.setFromDat(fromDat2);
			System.out.println("XML ELEMENT: " + request.getFromDat().getValue() );
			
			ArrayOfBookingTime times = helper.getQueryWSRequestTime(request);
			if(times == null){
				System.out.println("ArrayOfBookingTime.null");
			}
			List<BookingTime> timeList = times.getBookingTime();
			int id = 1; 
			for(BookingTime b : timeList){
				
				
				System.out.println("bookingTime.MONTH: + " + b.getDatum().getMonth());
				System.out.println("bookingTime.DAY: + " + b.getDatum().getDay());
				System.out.println("bookingTime.YEAR: + " + b.getDatum().getYear());
				
				Calendar dateCal = Calendar.getInstance();
				dateCal.set(Calendar.YEAR, b.getDatum().getYear());
				dateCal.set(Calendar.MONTH, b.getDatum().getMonth());
				dateCal.set(Calendar.DATE, b.getDatum().getDay());
				
				//String day = DateHandler.setCalendarDateFormat(dateCal);
				String day= DateHandler.setCalendarDateFormat(b.getDatum().toString());
				System.out.println("After Calendar: " + dateCal.toString());
				//dateCal.set(Calendar.HOUR, b.getKlocka());
				//dateCal.set(Calendar.MINUTE, b.getDatum().getMinute());
				
				String hourTime = DateHandler.setCalendarTimeFormat(dateCal);
				
				
				BookingTimeLocal pl = new BookingTimeLocal();
				pl.setNumbers(b.getAntal());
				pl.setDay(day);
				pl.setHour(b.getKlocka().getValue());
				pl.setBookingTimeId(id);
				//b.getDatum().getMonth();
				
				timeListLocal.add(pl);
				
				
				
				System.out.println(pl.toString());
				
				id++;
			}
			
			System.out.println("size: " + timeListLocal.size());
			
			return timeListLocal;
		}
		
		return timeListLocal;
	}
	
	
	
	/***
	 * 	method set selected item
	 * 
	 * @param selectedItem
	 */

	public void setSelectedItem(int selectedItem){
		System.out.println("selectitem: " + selectedItem);
	}
	
	
	/***
	 * 
	 * @param places
	 */
	public void setSelectedItem(Places places){
		System.out.println("selectitem: " + places.getPlacesId());
	}
	
	
	/***
	 * 
	 * 
	 * @param places
	 * @param state
	 */
	public void setSelectedItem(Places places, State state){
		System.out.println("BookingServices.setSelectedItem: " + places.getPlacesId());
		state.setCentralTidbokID(places.getPlacesId());
		System.out.println("BookingServices.state.centraltidbokid: " + state.getCentralTidbokID());
	}
	
	
	
	
	/*Method setting default value for BookingResponse, CentralTimeBookingId
	 * 
	 * 
	 */
	
	public int getSelectedDefaultItem(State loginCredentials){
		
		if(this.isFirstPlaces()){
			
			request = helper.getQueryWSRequest(loginCredentials);
			response = helper.getQueryWS(request);
			BookingResponseLocal responseLocal = new BookingResponseLocal(response);
			int centralTimeBookingId =responseLocal.getCentralTimeBookId();
			
			
			this.setFirstPlacesBoolean(false);
			return centralTimeBookingId;
			
		}
		else{
			System.out.println("BookingServices.getSelectedDefaultItem: " + loginCredentials.getCentralTidbokID());
			
			return loginCredentials.getCentralTidbokID();
		}
		
	}
	
	/**
	 * Method finding a selected place in the list of chosen bookingplaces return the object to print the selected place
	 * 
	 * 
	 * 
	 * @param places
	 * @param login
	 * @return
	 */
	
	public Places getSelectedPlace(Places places, State login){
		Places place = new Places();
		List<BookingPlaceLocal> bookingPlaces = getBookingPlace(login);
		for(BookingPlaceLocal bl : bookingPlaces){
			if(bl.getCentralTimeBookId() == places.getPlacesId()){
				
				place.setPlacesId(bl.getCentralTimeBookId());
				place.setClinic(bl.getClinic());
				place.setAddress(bl.getAddress());
				place.setRepresentationPlace();
				
				
			}
		}
		
		
		return place;
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
