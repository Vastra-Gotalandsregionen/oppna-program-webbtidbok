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
/**
 * 
 */
package se.vgregion.webbtidbok.tests;


import org.junit.*;


import se.vgregion.webbtidbok.*;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.BookingPlace;
import se.vgregion.webbtidbok.ws.BookingRequest;

import java.util.*;



import se.vgregion.webbtidbok.ws.*;

/**
 * @author conpem
 *
 */
public class BookingTimeTests {
	
	WebServiceHelper ws;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ws = new WebServiceHelper();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testBookingPlaces(){
		State credentials = new State();
		credentials.setPasswd("Zs12JzIW");
		credentials.setPnr("19121212-1212");
		
		BookingRequest request = ws.getQueryWSRequest(credentials);
		ArrayOfBookingTime time= ws.getQueryWSRequestTime(request);  
		List<BookingTime> timeList = time.getBookingTime();
		if(timeList == null){
			Assert.assertFalse(true);
			
		}
		else{
			
			if(timeList.isEmpty()){
				Assert.assertFalse(true);
			}
			else{
				
				for(BookingTime bt : timeList){
					//System.out.println(bp.getAddress().getValue());
					//System.out.println(bp.getCentralTidbokID());
					//System.out.println(bp.getMottagning().getValue());
					System.out.println("Antal: "  + bt.getAntal());
					System.out.println("Datum: "  + bt.getDatum().toString());
					System.out.println("Klocka: "  + bt.getKlocka().toString());
					//System.out.println("Klocka: "  + bt.);
					
				}
				
				
				
			}
			
			
			Assert.assertTrue(true);
		}
		
	}
		
}
