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


import org.junit.*;

import se.vgregion.webbtidbok.*;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.*;

import se.vgregion.webbtidbok.ws.*;


public class BookingPlacesTests {

	WebServiceHelper ws;
	
	/*
	 * Test logging in with blank pid and pwd, should return false if able to login with blank
	 * 
	 */
	@Test
	public void testBookingPlaces(){
		State credentials = new State();
		credentials.setPasswd("Y8PBZRUr");
		credentials.setPnr("19960103-2395");
		
		
		BookingRequest request = ws.getQueryWSRequest(credentials);
		ArrayOfBookingPlace places= ws.getQueryWSRequestPlaces(request);
		List<BookingPlace> placeList = places.getBookingPlace();
		if(placeList == null){
			Assert.assertFalse(true);
			
		}
		else{
			
			if(placeList.isEmpty()){
				Assert.assertFalse(true);
			}
			else{
				
				for(BookingPlace bp : placeList){
					System.out.println(bp.getAddress().getValue());
					System.out.println(bp.getCentralTidbokID());
					System.out.println(bp.getMottagning().getValue());
					
				}
			}
			
			
			
		}
	}
	
	@Before
	public void setUp() throws Exception {
		ws = new WebServiceHelper();
		
	}

	@After
	public void tearDown() throws Exception {
	}

}
