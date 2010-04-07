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
package se.vgregion.webbtidbok.tests;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.WebServiceHelper;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.BookingPlace;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;

/**
 * This is only a stub. To be elaborated.
 * This is a sandbox class to explore the WebService and
 * see what we can get from it and how.
 * @author carstm
 *
 */
public class WSTester {
	
	//Open connectioin to the WS, utilize WS helper
	static public void testGetBookingPlaceFromWS(){
		
		BookingRequest request = testGetWSRequest();
		WebServiceHelper wsh = new WebServiceHelper();
//		int tidBokId = request.getCentralTidbokID();
		
		ArrayOfBookingPlace bookingPlArr = new ArrayOfBookingPlace();
		
		bookingPlArr = wsh.getBookingPlaceFromWS(request);

		List<BookingPlace> bpList = new ArrayList<BookingPlace>();
		bpList = bookingPlArr.getBookingPlace();
		
		String mottagning;
		String address;
		for(BookingPlace b : bpList){

			mottagning = b.getMottagning().getValue();
			address = b.getAddress().getValue();
			System.out.println("mottagning: " + mottagning + "\n"
					 + "adress: " + address);
		}
		
	}
	static public BookingRequest testGetWSRequest(){
		String pnr = "19121212-1212";
		String passwd = "Zs12JzIW";
//		String pnr = "19660223-3196";
//		String passwd = "u63MvXTx";
				
		State logincredentials = new State();
		logincredentials.setPnr(pnr);
		logincredentials.setPasswd(passwd);
		logincredentials.setLoggedIn(true);
	
		BookingRequest request = new BookingRequest();
		WebServiceHelper wsh = new WebServiceHelper();
		request = wsh.getQueryWSRequest(logincredentials);
		
		return request;
	}
	
	//Get BookingResponse stuff and get the mottagning from it.
	//see BookingResponseLocal on how to get the BookingResponse
	static public BookingResponse testGetBookingResponseFromWS(){
		String mottagning;
		BookingResponse bookingResp = new BookingResponse();
		
		BookingRequest request = testGetWSRequest();
		WebServiceHelper wsh = new WebServiceHelper();
		bookingResp = wsh.getQueryWS(request);		
		
		mottagning = bookingResp.getMottagning().getValue();
		System.out.println("BookingResponse.getMottagning().getValue(): " + mottagning);
		
		
		return bookingResp;
	}
	
	public static void testGetDateAndTimeForDefaultBooking(){
		int year;
		int monthNr;
		int dayNr;
		int hour;
		int minute;
		int second;
	
		XMLGregorianCalendar XMLcal;
		WebServiceHelper wsh = new WebServiceHelper();
		BookingResponse bookingResp = new BookingResponse();
		BookingRequest request = testGetWSRequest();
		bookingResp = wsh.getQueryWS(request);	
		
		XMLcal = bookingResp.getBokadTid();
		
		year = XMLcal.getYear();
		System.out.println("Year: " + year);
		monthNr = XMLcal.getMonth();
		String strMonthNr = Integer.toString(monthNr);
		if(strMonthNr.length() < 2){
			String zero = "0";
			strMonthNr = zero.concat(strMonthNr);
			System.out.println("strMonthNr: " + strMonthNr);
		}
		System.out.println("MonthNr: " + monthNr);
		dayNr = XMLcal.getDay();
		String strDayNr = Integer.toString(monthNr);
		if(strDayNr.length() < 2){
			String zero = "0";
			strDayNr = zero.concat(strDayNr);
			System.out.println("strDayNr: " + strDayNr);
		}
		System.out.println("DayNr: " + dayNr);
		String date = Integer.toString(year) + " " + strMonthNr + " " + strDayNr;
		System.out.println("String date: " + date);
		
		hour = XMLcal.getHour();
		System.out.println("Hour: " + hour);
		String strHour = Integer.toString(hour);
		if(strHour.length() < 2){
			String zero ="0";
			strHour = zero.concat(strHour);
		}
		minute = XMLcal.getMinute();
		System.out.println("Minute: " + minute);
		String strMinute = Integer.toString(minute);
		if(strMinute.length() < 2 ){
			String zero = "0";
			strMinute = zero.concat(strMinute);
		}
		second = XMLcal.getSecond();
		System.out.println("Second: " + second);
		String time = strHour + ":" + strMinute; 
		System.out.println("String time: " + time);
	}
	
	public static void main(String[] args){
		testGetBookingPlaceFromWS();
		
		//yes this works... Where value as when you log in thru the gui with 19121212-1212
		testGetBookingResponseFromWS();
		
		testGetDateAndTimeForDefaultBooking();
	}
	
	
//	//Test the WS connection is ok
//	@Test
//	public void testWSCalendarObj(){
//		String xmlCal = null;
//		XMLGregorianCalendar gregCal = connectToWS();
//		xmlCal = gregCal.toXMLFormat();
//		System.out.println("xmlCal: " + xmlCal);
//		
//		
//		Assert.assertTrue(false);
//	}
//	
//	//Test getting relevant hospital instance from the WS
//	//What is correct depends on what person you log in as
//	//Use different test persons as reference for true/false
//	@Test
//	public void testWSHospitalIsCorrect(){
//		Assert.assertTrue(false);
//	}
	
	
//	19121212-1212	Zs12JzIW
//	19960103-2395	Y8PBZRUr
//	19910104-2399	bQwkdRrG
//	19910104-2399	fje5rnXG
//	19660223-3196	u63MvXTx
//	9030303-9804	2td3XrGx
	
}
