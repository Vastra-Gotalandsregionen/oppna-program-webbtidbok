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
package se.vgregion.webbtidbok.booking.sectra;

import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.booking.BookingWrapper;


public class SectraBookingFacadeImpl implements BookingFacade {
	
	private SectraWebServiceHelperImpl helper;
	private SectraBookingServiceImpl service = new SectraBookingServiceImpl();

	public SectraBookingFacadeImpl(){
		System.out.println("SectraBookingFacadeImpl() constructor");
	}
	public SectraWebServiceHelperImpl getHelper() {
		return helper;
	}

	public void setHelper(SectraWebServiceHelperImpl helper) {
		this.helper = helper;
	}

	@Override
	public boolean login(State state) {

		String patientId = state.getPnr();
		String password = state.getPasswd();
		boolean isLoggedIn = helper.login(patientId, password);
		state.setLoggedIn(isLoggedIn);   
		
		return isLoggedIn;
	}
	
	public BookingInfoLocal getBooking(State state){
		
		if(state.isLoggedIn()){
			BookingInfoLocal biL = service.getBookingInfo(state);
			return biL;
		}
		
		return null;
	}
	
	public BookingInfoLocal reschedule(String examinationNr, String newTimeId,
			XMLGregorianCalendar startTime, Boolean printNewNotice,
			String rescheduleComment) {
		
		return null;
	}

	@Override
	public BookingWrapper getBookingInfo(State state) {
		if(state.isLoggedIn()){
			System.out.println("### SectraBookingFacadeImpl pnr & password & isLoggedIn: " + state.getPnr() + ", " + state.getPasswd() + ", " + state.isLoggedIn());			
			BookingInfoLocal biL = new BookingInfoLocal();
			biL = service.getBookingInfo(state);
			System.out.println("### SectraBookingFacadeImpl after service call, before converting biL to BookingWrapper");
			BookingWrapper bw = new BookingWrapper(biL);
			System.out.println("###SectraBookingFacadeImpl.getBookingInfo(State) - returning wrapper");
			System.out.println("### bw.getNamn(): " + bw.getDisplayName());
			return bw;
		}
		
		return null;
	}

}
