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
	
		System.out.println("BookingRequest.getMottagning().getValue(): " + mottagning);
		
		
		return bookingResp;
	}
	public static void main(String[] args){
		testGetBookingPlaceFromWS();
		
		//yes this works... Where value as when you log in thru the gui with 19121212-1212
		testGetBookingResponseFromWS();
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
	
}
