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
import javax.xml.datatype.XMLGregorianCalendar;

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

import javax.xml.datatype.*;

public class BookingService
{
	
	BookingResponse response;
	BookingRequest request;
	WebServiceHelper helper = new WebServiceHelper();
	private ObjectFactory objectFactory = new ObjectFactory();
	
	public boolean isFirstPlaces = true;
	public boolean isUpdated = false;
	
	//private String orderDate;
	 
	//constructor
	public BookingService(){}
	
	
	public void setIsUpdated(boolean b){
		isUpdated = true;
	}
	
	public boolean isUpdated(){
		return isUpdated;
	}
	
	
	public void setFirstPlacesBoolean(boolean b){
		
		isFirstPlaces = b;
//		System.out.println("BookingServices.setFirstPlacesBoolean: " + isFirstPlaces);
	}
	
	
	
	public boolean isFirstPlaces(){
//		System.out.println("BookingServices.isFirstBoolean: " + isFirstPlaces);

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
				
//				System.out.println(pl.toString());
				
			}
			
//			System.out.println("size: " + placeListLocal.size());
			
			return placeListLocal;
		}
		
		return placeListLocal;
	}
	
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
			Calendar selectedDate = loginCredentials.getSelectedDate();
			
//			if(selectedDate == null){
//				loginCredentials.setDefaultDate(true);
//				return null;
//			}
			
//			System.out.println("SELECTED DATE YEAR: " + selectedDate.get(Calendar.YEAR));
//			System.out.println("SELECTED DATE MONTH: " + selectedDate.get(Calendar.MONTH));
//			System.out.println("SELECTED DATE DAY: " + selectedDate.get(Calendar.DAY_OF_MONTH));
			String fromDate = DateHandler.setCalendarDateFormat(selectedDate);
			JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat(fromDate);
//			
//			System.out.println("fromDate: " + fromDate);
//			System.out.println("JAXB-FROMDATE: " + fromDat.getValue());
//			
//			System.out.println("CentralTidBokID: " + loginCredentials.getCentralTidbokID());
			
			request = helper.getQueryWSRequest(loginCredentials);
			request.setCentralTidbokID(loginCredentials.getCentralTidbokID());
			request.setFromDat(fromDat);
			
			ArrayOfBookingTime times = helper.getQueryWSRequestTime(request);
			
			List<BookingTime> timeList;
			try {
				timeList = times.getBookingTime();
			} catch (Exception e) {
				return null;
			}
			
			int id = 1; 
			for(BookingTime b : timeList){
				
				
				Calendar dateCal = Calendar.getInstance();
				dateCal.set(Calendar.YEAR, b.getDatum().getYear());
				dateCal.set(Calendar.MONTH, b.getDatum().getMonth() - 1);
				dateCal.set(Calendar.DATE, b.getDatum().getDay());
				
				String day = DateHandler.setCalendarDateFormat(dateCal);

//				String hourTime = DateHandler.setCalendarTimeFormat(dateCal);
				
				
				BookingTimeLocal pl = new BookingTimeLocal();
				pl.setNumbers(b.getAntal());
				pl.setDay(day);
				pl.setTime(b.getKlocka().getValue());
				pl.setBookingTimeId(id);			
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
			//response = helper.getQueryWS(request);
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
	
	
	/****
	 * method setting ombokning
	 * 
	 * @param l
	 */
	public void setBookingTime(BookingTimeLocal bookingTime, State credentials){
		System.out.println("-----------set BookingTime------------");
		System.out.println("-----------------------");
		System.out.println("-----------------------");
		System.out.println("-----------------------");
		System.out.println("-----------------------");
		
		System.out.println("--date, selectedday" + credentials.getSelectedDate().getTime().toString());
		System.out.println("-----------------------");
		System.out.println("-----------------------");
		System.out.println("-----------------------");
		System.out.println("-----------------------");
		System.out.println("-----------------------");
		
		int id = bookingTime.getBookingTimeId();
		String day = bookingTime.getDay();
		String hour = bookingTime.getTime();
		int numbers = bookingTime.getNumbers();
		String[] hourMinute = hour.split(":");
		 
		Date time = DateHandler.setCalendarTimeFormat(hour);
		Calendar cal =Calendar.getInstance(); 
		//credentials.getSelectedDate();
		cal.set(Calendar.YEAR, credentials.getSelectedDate().get(Calendar.YEAR));
	    cal.set(Calendar.MONTH, credentials.getSelectedDate().get(Calendar.MONTH));
	    //tempCal.set(Calendar.DATE, cal.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.DAY_OF_MONTH, credentials.getSelectedDate().get(Calendar.DAY_OF_MONTH) );
		
		cal.set(Calendar.HOUR, Integer.parseInt(hourMinute[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(hourMinute[1]));
		cal.set(Calendar.SECOND, 0);
		
		System.out.println("Calendar.Date: " + cal.getTime().toString());
		System.out.println("Calendar.Date: " + hourMinute[0]);
		System.out.println("Calendar.Date: " + hourMinute[1]);
		
		System.out.println("Calendar.Date: " + cal.get(Calendar.HOUR));
		System.out.println("Calendar.Date: " + cal.get(Calendar.MINUTE));
		
		//update booking
		 
		
		if(credentials.isLoggedIn()){
			
			request = helper.getQueryWSRequest(credentials);
			
			XMLGregorianCalendar xmlCal;
			try{
				
				xmlCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar();
			
				//XMLGregorianCalendar xmlCal = credentials.getBookingResponse().getBokadTid();
				xmlCal.setDay(cal.get(Calendar.DATE)  );
				xmlCal.setMonth(cal.get(Calendar.MONTH) + 1 );
				xmlCal.setYear(cal.get(Calendar.YEAR));
			
				//set time
				xmlCal.setHour(Integer.parseInt(hourMinute[0]));
				xmlCal.setMinute(cal.get(Calendar.MINUTE));
				xmlCal.setSecond(cal.get(Calendar.SECOND));
				
				System.out.println("XMLCalendar.Date: " + xmlCal.toString());
				
				
				request.setBokadTid(xmlCal);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			
			
			request.setCentralTidbokID(credentials.getCentralTidbokID());
			
			BookingResponse response = helper.setBookingUpdate(request);
			
			if(response == null){
				this.setIsUpdated(false);
			}
			else{
				this.setIsUpdated(true);
				System.out.println("CentraltidBokiD: " + response.getCentralTidbokID());
				System.out.println("DATUM: " + response.getBokadTid().toString());
				System.out.println("PNR: " + response.getPnr().getValue());
				System.out.println("ToString: " + response.toString());
			}
			
	
			
			
		}
		
	}
	
	
}
