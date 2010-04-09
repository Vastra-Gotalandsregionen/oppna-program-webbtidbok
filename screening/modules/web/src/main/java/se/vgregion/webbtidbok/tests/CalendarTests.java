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


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.WebServiceHelper;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingTime;
import se.vgregion.webbtidbok.ws.*;

/**
 * @author conpem
 *
 */
public class CalendarTests {
	
	
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
	@Test
	public void testCalendar(){
		State credentials = new State();
		credentials.setPasswd("Zs12JzIW");
		credentials.setPnr("19121212-1212");
		
		BookingRequest request = ws.getQueryWSRequest(credentials);
		ArrayOfCalendar calendars= ws.getQueryWSRequestCalendar(request);  
		List<Calendar> calendarList = calendars.getCalendar();
		if(calendarList == null){
			Assert.assertFalse(true);
			
		}
		else{
			
			if(calendarList.isEmpty()){
				Assert.assertFalse(true);
			}
			else{
				
				for(Calendar c : calendarList){
					//System.out.println(bp.getAddress().getValue());
					//System.out.println(bp.getCentralTidbokID());
					//System.out.println(bp.getMottagning().getValue());
					System.out.println("Datum: "  + c.getDatum().toString());
					System.out.println("Status: "  + c.getStatus().getValue());
					System.out.println("Spärrad: "  + c.isSparr());
					//System.out.println("Klocka: "  + bt.);
					
				}
				
				
				
			}
			
			
			Assert.assertTrue(true);
		}
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
