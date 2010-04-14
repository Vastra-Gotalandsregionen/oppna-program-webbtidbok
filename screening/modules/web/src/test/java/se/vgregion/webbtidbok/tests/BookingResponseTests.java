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


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.*;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.WebServiceHelper;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingTime;
import se.vgregion.webbtidbok.ws.*;

public class BookingResponseTests {
	
	WebServiceHelper ws;
	
	@Before
	public void setUp() throws Exception {
		ws = new WebServiceHelper();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testBookingResponseObject(){
		
		State credentials = new State();
		credentials.setPasswd("Y8PBZRUr");
		credentials.setPnr("19960103-2395");
		
		BookingRequest request = ws.getQueryWSRequest(credentials);
		BookingResponse bookingResponse= ws.getQueryWS(request);  
		if(bookingResponse == null){
			Assert.assertFalse(true);
		}
		
		if(bookingResponse.getCentralTidbokID().equals(0)){
			Assert.assertFalse(true);
		}
		else{
			System.out.println("CentralTidBokId: " + bookingResponse.getCentralTidbokID() );
			System.out.println("Adress: " + bookingResponse.getAddress().getValue());
			System.out.println("Antal ombokningar: " + bookingResponse.getAntalOmbok());
			System.out.println("Bokad tid: " + bookingResponse.getBokadTid().toString());
			System.out.println("Get Epost: " + bookingResponse.getEpost().getValue());
			System.out.println("External ID: " + bookingResponse.getExternalID().getValue());
			System.out.println("Get Max Antal bokningar: " + bookingResponse.getMaxAntalOmbok());
			System.out.println("Get Mobil Tel: " + bookingResponse.getMobilTel().getValue());
			System.out.println("Get Mottagning: " + bookingResponse.getMottagning().getValue());
			System.out.println("Get Namn: " + bookingResponse.getNamn().getValue());
			System.out.println("PNR: " + bookingResponse.getPnr().getValue());
			System.out.println("Vårdgivare: " + bookingResponse.getVardgivare().getValue());
			
			Assert.assertTrue(true);
		}
		
	}
	
	

}
