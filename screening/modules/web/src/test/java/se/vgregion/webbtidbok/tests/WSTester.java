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

import javax.security.auth.login.LoginContext;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.WebServiceHelper;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.ArrayOfCalendar;
import se.vgregion.webbtidbok.ws.BookingPlace;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.BookingTime;
import se.vgregion.webbtidbok.ws.Calendar;
import se.vgregion.webbtidbok.ws.GetCalandar;
import se.vgregion.webbtidbok.ws.ObjectFactory;

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
			System.out.println("BookingPlace.getMottagning(): " + bookingPlace + "\n"
					 + "adress: " + address);
			i++;
		}
		
		return bookingPlaceArr;
		
	}
	//Normal stuff... only pnr and pin is provided
	static public BookingRequest getWSRequest(){
		//Klaus K
//		String pnr = "19121212-1212";
//		String passwd = "nEoZMD7G";
		//Bengt M
		String pnr = "19660223-3196";
		String passwd = "u63MvXTx";
				
		State requestParameters = new State();
		requestParameters.setPnr(pnr);
		requestParameters.setPasswd(passwd);
		requestParameters.setLoggedIn(true);
	
		BookingRequest request = new BookingRequest();
		WebServiceHelper wsh = new WebServiceHelper();
		request = wsh.getQueryWSRequest(requestParameters);
		
		return request;
	}
	
	
	//the response provided thru getQueryWSRequest with pnr and pin
	static public BookingResponse getBookingResponseFromWS(){
		String mottagning;
		BookingResponse bookingResp = new BookingResponse();
		
		BookingRequest request = getWSRequest();
		WebServiceHelper wsh = new WebServiceHelper();
		bookingResp = wsh.getQueryWS(request);		
		
		mottagning = bookingResp.getMottagning().getValue();
		System.out.println("\n*** getBookingResponseFromWS(pnr, passwd");
		System.out.println("BookingResponse.getMottagning().getValue(): " + mottagning);	
		
		return bookingResp;
	}
	
	//gets XMLcal via request. does NOT WORK to get this from a request.
	public static String getTimeForDefaultBookingViaRequest(){

		String time;
		XMLGregorianCalendar XMLcal;
		BookingRequest bookingReq = getWSRequest();
		
		System.out.println("\n*** getTimeForDefaultBookingViaRequest(): ");
		
		//This renders a NullPointer because the Request does not have a bokad tid other than null
		//Since the request probably doesn't have a bokad tid this method should not exist as public.
		//What you can do is probably to use setBokadTid() on a request if you want to make a new appointment.
		XMLcal = bookingReq.getBokadTid();
		time = timeFormatter(XMLcal);	
		System.out.println("String time from request: " + time);
		
		return time;
	}
	
	public static String timeFormatter(XMLGregorianCalendar XMLcal){
		
		int hour;
		int minute;
		int second;
		String zero = "0";
		String time;
		
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
		
		time = strHour + ":" + strMinute; 
		
		return time;
	}
	
	//gets XMLcal via response
	public static String getTimeForDefaultBookingViaResponse(){

		XMLGregorianCalendar XMLcal;
		WebServiceHelper wsh = new WebServiceHelper();
		BookingResponse bookingResp = new BookingResponse();
		BookingRequest request = getWSRequest();
		bookingResp = wsh.getQueryWS(request);	
		
		System.out.println("\n*** getTimeForDefaultBookingViaResponse: ");
		
		XMLcal = bookingResp.getBokadTid();
		String time = timeFormatter(XMLcal);
		
		System.out.println("String time from response: " + time);
		
		return time;
	}
	
	//gets XMLcal via response
	public static String getDateForDefaultBookingViaResponse(){
		
		String date;
		XMLGregorianCalendar XMLcal;
		WebServiceHelper wsh = new WebServiceHelper();
		BookingResponse bookingResp = new BookingResponse();
		BookingRequest request = getWSRequest();
		bookingResp = wsh.getQueryWS(request);	
		
		XMLcal = bookingResp.getBokadTid();
		date = dateFormatter(XMLcal);
		System.out.println("\n*** getDateForDefaultBookingViaResponse: ");

		System.out.println("String date: " + date);
		
		return date;
		
	}
	

	public static Map<Integer, String> getCTIDAndNameForPlaces(){
		//Maybe get tid for every mottagning thru getCentralTidBokId()
		Map<Integer, String> ctidAndNameMap = new HashMap<Integer, String>();
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
			String mottagning = b.getMottagning().getValue();
			ctidAndNameMap.put(centralTidBokId, mottagning);
		}
		
		return ctidAndNameMap;		
	}
	
	public static void readFromMap(Map<Integer, String> map){
		Set<Integer> keys = map.keySet();
		Iterator<Integer> iter = keys.iterator();
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
		int keyValue = 0;
		while(iter.hasNext()){
			keyValue = Integer.parseInt(iter.next().toString());
			list.add(keyValue);
		}
		return list;
	}
	
	public static void exploreWSResponseAndRequest(ArrayList<Integer> centralTidBoksIdList){
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
	public static List<BookingTime> getBookingTime(){

		BookingRequest request = getWSRequest();
		ObjectFactory objectFactory = new ObjectFactory();

		JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat("2010-03-03");
		JAXBElement<String> toDat = objectFactory.createBookingRequestToDat("2010-04-15");
		
		request.setCentralTidbokID(1);
		request.setFromDat(fromDat);
		request.setToDat(toDat);
		
		WebServiceHelper wsh = new WebServiceHelper();
		ArrayOfBookingTime bookingTimeArr = wsh.getQueryWSRequestTime(request);
		List<BookingTime> bookingTimeList = bookingTimeArr.getBookingTime();
		XMLGregorianCalendar XMLcal;
		System.out.println("\n*** getBookingTime: ");
	
		for(BookingTime b : bookingTimeList){
		
			int nBookings = b.getAntal();
			String time = b.getKlocka().getValue();	
			XMLcal = b.getDatum();
			String date = dateFormatter(XMLcal);
			System.out.println("time: " + time + ", date: " + date + ", no. bookings: " + nBookings);
		}
		
		return bookingTimeList;
	}
	
	//Test what properties can be set on a request
	//Test specific how to get a Calendar (Tidbok) thru CTID
	//This emulates a user picking a Mottagning thru the drop down menu
	//That would generate a new request with new properties set on it
	public static List<Calendar> getPlaceCalendarThruCTID(int CTID){
		ObjectFactory objectFactory = new ObjectFactory();
	
		BookingRequest request = getWSRequest();
	
		JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat("2010-03-30");
		JAXBElement<String> toDat = objectFactory.createBookingRequestToDat("2010-04-03");		
		request.setCentralTidbokID(CTID);
		request.setFromDat(fromDat);
		request.setToDat(toDat);
		WebServiceHelper wsh = new WebServiceHelper();		
		ArrayOfCalendar calArr = wsh.getQueryWSRequestCalendar(request);
		List<Calendar> calList = calArr.getCalendar();
		System.out.println("\n**** testGetValidCalThruCTID: ");
		System.out.println("calList.size() - ammount of bookable dates within fromDat & toDat: " + calList.size());
		//CalendarObj is about the same as one day
		if(!calList.isEmpty()){		
			System.out.println("fromDat: " + fromDat.getValue() + ", toDat: " + toDat.getValue());
			int availTimeToBook = 1;
			for(Calendar c: calList ){

				System.out.println("c.getDatum(): " + dateFormatter(c.getDatum()) + ", availTimeToBook: " + availTimeToBook);
				availTimeToBook++;
			}
		}
		
		return calList;
	}
	
	public static String dateFormatter(XMLGregorianCalendar XMLcal){
		
		String date;
		String zero = "0";
		int year = XMLcal.getYear();
		int monthNr = XMLcal.getMonth();
		String strMonthNr = Integer.toString(monthNr);
		if(strMonthNr.length() < 2){
			strMonthNr = zero.concat(strMonthNr);
		}
		int dayNr = XMLcal.getDay();
		String strDayNr = Integer.toString(monthNr);
		if(strDayNr.length() < 2){
			strDayNr = zero.concat(strDayNr);
		}
		date = Integer.toString(year) + " " + strMonthNr + " " + strDayNr;
		
		return date;
	}

	public static void main(String[] args){
		Map<Integer, String> map;
		ArrayList<Integer> centralTidBoksIdList;

		ArrayList<String> bookingPlaceArr = getBookingPlaceFromWS();
		
		getBookingResponseFromWS();
		
//		testGetTimeForDefaultBookingViaRequest(); It looks like you can do this - you can't. See method for further info.
		
		getDateForDefaultBookingViaResponse();
		
		getTimeForDefaultBookingViaResponse();
		
		map = getCTIDAndNameForPlaces();

		readFromMap(map);
		
		centralTidBoksIdList = returnCentralTidBokIdFromMap(map);

		exploreWSResponseAndRequest(centralTidBoksIdList);
		
		getBookingTime();
	
//		key/CID: 1 value/PLACE: IC-Sjukhuset
//		key/CID: 2 value/PLACE: Surf-Sjukhuset
//		key/CID: 3 value/PLACE: Elvis-Sjukhuset
		getPlaceCalendarThruCTID(1);

		
	}
	
//	19121212-1212	nEoZMD7G
//	19960103-2395	Y8PBZRUr
//	19910104-2399	bQwkdRrG
//	19910104-2399	fje5rnXG
//	19660223-3196	u63MvXTx
//	9030303-9804	2td3XrGx
	
}
