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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * It will most likely become a utility class later on.
 * @author carstm
 *
 */
public class WSTester {
	
	//Open connectioin to the WS, utilize WS helper
	static public ArrayList<String> getBookingPlaceFromWS(){
		
		BookingRequest request = getWSRequest();
		WebServiceHelper wsh = new WebServiceHelper();
//		int tidBokId = request.getCentralTidbokID();
		
		ArrayOfBookingPlace bookingPlArr = new ArrayOfBookingPlace();
		
		bookingPlArr = wsh.getBookingPlaceFromWS(request);

		List<BookingPlace> bpList = new ArrayList<BookingPlace>();
		bpList = bookingPlArr.getBookingPlace();
		
		System.out.println("\n*** getBookingPlaceFromWS(): ");
		
		String bookingPlace;
		ArrayList <String> bookingPlaceArr = new ArrayList<String>();
		String address;
		int i = 0;
		for(BookingPlace b : bpList){

			bookingPlace = b.getMottagning().getValue();
			address = b.getAddress().getValue();
			bookingPlaceArr.add(bookingPlace);
			System.out.println("bookingPlace: " + bookingPlace + "\n"
					 + "adress: " + address);
			i++;
		}
		
		return bookingPlaceArr;
		
	}
	static public BookingRequest getWSRequest(){
		//Klaus K
		String pnr = "19121212-1212";
		String passwd = "Zs12JzIW";
		//Bengt M
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
	static public BookingResponse getBookingResponseFromWS(){
		String mottagning;
		BookingResponse bookingResp = new BookingResponse();
		
		BookingRequest request = getWSRequest();
		WebServiceHelper wsh = new WebServiceHelper();
		bookingResp = wsh.getQueryWS(request);		
		
		mottagning = bookingResp.getMottagning().getValue();
		System.out.println("\n*** getBookingResponseFromWS(): ");
		System.out.println("BookingResponse.getMottagning().getValue(): " + mottagning);	
		
		return bookingResp;
	}
	
	//gets XMLcal via request
	public static String getTimeForDefaultBookingViaRequest(){
		int hour;
		int minute;
		int second;
		String time;
		String zero = "0";
		XMLGregorianCalendar XMLcal;

		BookingRequest bookingReq = getWSRequest();
		
		System.out.println("\n*** getTimeForDefaultBookingViaRequest(): ");
		
		//This renders a NullPointer because the Request does not have a bokad tid other than null
		//Since the request probably doesn't have a bokad tid this method should not exist as public.
		//What you can do is probably to use setBokadTid() on a request if you want to make a new appointment.
		XMLcal = bookingReq.getBokadTid();
	
		hour = XMLcal.getHour();
		String strHour = Integer.toString(hour);
		if(strHour.length() < 2 ){		
			zero.concat(strHour);
		}
		minute = XMLcal.getMinute();
		String strMinute = Integer.toString(minute);
		if(strMinute.length() < 2 ){
			zero.concat(strMinute);
		}
		second = XMLcal.getSecond();
		String strSecond = Integer.toString(second);
		time = strHour + ":" + strMinute;
		
		System.out.println("String time from request: " + time);
		
		return time;
	}
	
	
	//gets XMLcal via response
	public static String getTimeForDefaultBookingViaResponse(){
		int hour;
		int minute;
		int second;
		String zero = "0";
		XMLGregorianCalendar XMLcal;
		WebServiceHelper wsh = new WebServiceHelper();
		BookingResponse bookingResp = new BookingResponse();
		BookingRequest request = getWSRequest();
		bookingResp = wsh.getQueryWS(request);	
		
		System.out.println("\n*** getTimeForDefaultBookingViaResponse: ");
		
		XMLcal = bookingResp.getBokadTid();
		
		hour = XMLcal.getHour();
		String strHour = Integer.toString(hour);
		if(strHour.length() < 2){
			strHour = zero.concat(strHour);
		}
		minute = XMLcal.getMinute();
		String strMinute = Integer.toString(minute);
		if(strMinute.length() < 2 ){
			strMinute = zero.concat(strMinute);
		}
		second = XMLcal.getSecond();
		String time = strHour + ":" + strMinute; 
		System.out.println("String time from response: " + time);
		
		return time;
	}
	
	//gets XMLcal via response
	public static String getDateForDefaultBookingViaResponse(){
		int year;
		int monthNr;
		int dayNr;
		String zero = "0";
		XMLGregorianCalendar XMLcal;
		WebServiceHelper wsh = new WebServiceHelper();
		BookingResponse bookingResp = new BookingResponse();
		BookingRequest request = getWSRequest();
		bookingResp = wsh.getQueryWS(request);	
		
		XMLcal = bookingResp.getBokadTid();
		
		System.out.println("\n*** getDateForDefaultBookingViaResponse: ");
		
		year = XMLcal.getYear();
//		System.out.println("Year: " + year);
		monthNr = XMLcal.getMonth();
		String strMonthNr = Integer.toString(monthNr);
		if(strMonthNr.length() < 2){
			strMonthNr = zero.concat(strMonthNr);
//			System.out.println("strMonthNr: " + strMonthNr);
		}
//		System.out.println("MonthNr: " + monthNr);
		dayNr = XMLcal.getDay();
		String strDayNr = Integer.toString(monthNr);
		if(strDayNr.length() < 2){
			strDayNr = zero.concat(strDayNr);
//			System.out.println("strDayNr: " + strDayNr);
		}
//		System.out.println("DayNr: " + dayNr);
		String date = Integer.toString(year) + " " + strMonthNr + " " + strDayNr;
		System.out.println("String date: " + date);
		
		return date;
		
	}
	

	public static Map<Integer, String> testGetDateAndTimeForOtherAlternatives(){
		//Maybe get tid for every mottagning thru getCentralTidBokId()
		Map<Integer, String> map = new HashMap<Integer, String>();
		WebServiceHelper wsh = new WebServiceHelper();
		BookingRequest request = getWSRequest();
		
		ArrayOfBookingPlace bookingPlArr = wsh.getBookingPlaceFromWS(request);
		List<BookingPlace> bpList = new ArrayList<BookingPlace>();
		bpList = bookingPlArr.getBookingPlace();
		
		int centralTidBokId;
		int index = 0;
		
		for(BookingPlace b : bpList){
			index++;
			centralTidBokId = b.getCentralTidbokID();
//			System.out.println("CentralTidBokId: " + centralTidBokId);
			String mottagning = b.getMottagning().getValue();
//			System.out.println("Mottagning: " + mottagning);
			map.put(centralTidBokId, mottagning);
		}
		
		return map;		
	}
	
	public static void readFromMap(Map<Integer, String> map){
		Set<Integer> keys = map.keySet();
		Iterator<Integer> iter = keys.iterator();
//		System.out.println("keys.size(): " + keys.size());
		int keyValue = 0;
		System.out.println("\n*** readFromMap(): ");
		while(iter.hasNext()){
			keyValue = Integer.parseInt(iter.next().toString());
			System.out.println("key/CID: " + keyValue + " value/PLACE: " + map.get(keyValue));
		}
	}
	
	public static ArrayList<Integer> returnCentralTidBokIdFromMap(Map<Integer, String> map){
		ArrayList<Integer> list = new ArrayList<Integer>();
		Set<Integer> keys = map.keySet();
		Iterator<Integer> iter = keys.iterator();
//		System.out.println("keys.size(): " + keys.size());
		int keyValue = 0;
		while(iter.hasNext()){
			keyValue = Integer.parseInt(iter.next().toString());
//			System.out.println("key: " + keyValue + " value: " + map.get(keyValue));
			list.add(keyValue);
		}
		return list;
	}
	
	public static void exploreWS(ArrayList<Integer> centralTidBoksIdList){
		//what do i want to do here?
		//thru ctbId I want to get available times for the plats or mottagning associated with the id.
		WebServiceHelper wsh = new WebServiceHelper(); 
		
		//what can a request provide?
		BookingRequest request = getWSRequest();
		
		
		BookingResponse response = wsh.getQueryWS(request);
		
		//what can a getQueryWS(request) return?
		response.getAddress().getValue();
		int antOmbok = response.getAntalOmbok();
		int centTidBokId = response.getCentralTidbokID();
		String mottagning = response.getMottagning().getValue();
		String patientNamn = response.getNamn().getValue();
		String vargivare = response.getVardgivare().getValue();
		String date = getDateForDefaultBookingViaResponse();
		String time = getTimeForDefaultBookingViaResponse();
		String address = response.getAddress().getValue();
		
		System.out.println("\n*** exploreWS()\nStuff from a response object: ");
		System.out.println("antOmbok: " + antOmbok + "\ncentTidBokId: " + centTidBokId + "\nmottagning: " + mottagning +
				"\nnamn: " + patientNamn + "\nvårgivare: " + vargivare + "\ndate: " + date + "\ntime: " + time + "\naddress: " + address);
		
		//what can a BookingPlace return?
		ArrayOfBookingPlace bookingPlArr = wsh.getBookingPlaceFromWS(request);
		List<BookingPlace> bookingPlList = bookingPlArr.getBookingPlace();
		
		BookingPlace bookingPl = bookingPlList.get(0);
		String bookingPlAddress = bookingPl.getAddress().getValue();
		int bookingPlCTID = bookingPl.getCentralTidbokID();
		String bookingPlMottagning = bookingPl.getMottagning().getValue();
		System.out.println("\nstuff from a BookingPlace object: ");
		System.out.println("bookingPlCTID: " + bookingPlCTID + "\nbookingPlMottagning: " + bookingPlMottagning + "\nbookingPlAddress: " + bookingPlAddress);

	}
	
	public static void main(String[] args){
		Map<Integer, String> map;
		ArrayList<Integer> centralTidBoksIdList;

		ArrayList<String> bookingPlaceArr = getBookingPlaceFromWS();
		
//		System.out.println("\n*** main: ");
//		for(String s : bookingPlaceArr){
//			System.out.println("bookingPlaceArr: " + s);
//		}
		
		getBookingResponseFromWS();
		
//		testGetTimeForDefaultBookingViaRequest(); It looks like you can do this - you can't. See method for further info.
		
		getDateForDefaultBookingViaResponse();
		
		getTimeForDefaultBookingViaResponse();
		
		map = testGetDateAndTimeForOtherAlternatives();

		readFromMap(map);
		
		centralTidBoksIdList = returnCentralTidBokIdFromMap(map);

		exploreWS(centralTidBoksIdList);
		
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
