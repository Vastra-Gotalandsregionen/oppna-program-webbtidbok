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
import se.vgregion.webbtidbok.domain.Booking;


public class SectraBookingFacadeImpl implements BookingFacade {
	
	private SectraBookingServiceImpl service;
	
	public void setService(SectraBookingServiceImpl service) {
		this.service = service;
	}

	@Override
	public boolean login(State state) {

		String patientId = state.getPnr();
		String password = state.getPasswd();
		boolean isLoggedIn = service.login(patientId, password);
		state.setLoggedIn(isLoggedIn);   
		
		return isLoggedIn;
	}
	
	@Override
	public Booking getBookingInfo(State state){
		
		if(state.isLoggedIn()){
			Booking biL = service.getBookingInfo(state);
			return biL;
		}
		
		return null;
	}
	
	public Booking reschedule(String examinationNr, String newTimeId,
			XMLGregorianCalendar startTime, Boolean printNewNotice,
			String rescheduleComment) {
		
		return null;
	}
}
