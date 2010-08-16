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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.Places;
import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;

public class SectraBookingFacadeImpl implements BookingFacade {

	private SectraBookingServiceFactory serviceFactory;

	public void setServiceFactory(SectraBookingServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	/*
	 * Use this to get a connection to the proper web service.
	 */
	private SectraBookingServiceInterface getService(State state) {
		return serviceFactory.getServiceInstance(state.getService(), state
				.getPnr(), state.getPasswd());
	}

	@Override
	public boolean login(State state) {
	    boolean isLoggedIn = false;
	    try {
	        getService(state).getBooking();
	        isLoggedIn = true;
	    }
	    catch (RuntimeException e) {
	        // This means we could not log in, probably wrong patient or password.
	        // Just stay not logged in.
	    }
		state.setLoggedIn(isLoggedIn);
		return isLoggedIn;
	}

	@Override
	public Booking getBookingInfo(State state) {
		Booking booking = null;
		if (state.isLoggedIn()) {
			booking = getService(state).getBooking();
		} else {
			booking = new BookingSectra();
		}
		return booking;
	}

	public Booking reschedule(String examinationNr, String newTimeId,
			XMLGregorianCalendar startTime, Boolean printNewNotice,
			String rescheduleComment) {
		return null;
	}

	@Override
	public List<SelectItem> getBookingPlaceSelectItems(State state) {
		List<Surgery> surgeries = getService(state).getSurgeries();
		List<SelectItem> selectedItems = new ArrayList<SelectItem>();
		for (Surgery surgery : surgeries) {
			SelectItem s = new SelectItem();
			s.setLabel(surgery.getSurgeryName());
			s.setValue(surgery.getSurgeryId());
			selectedItems.add(s);
		}
		return selectedItems;

	}

	@Override
	public int getSelectedDefaultItem(State state) {
		// return getService(state).getSelectedDefaultItem(state);
		// TODO: Fix this response.
		return 0;
	}

	@Override
	public Places getSelectedPlace(Places places, State state) {
		// TODO: Add implementation
		return new Places();
	}

	@Override
	public List<Date> getFreeDays(State state, Date startDate, Date endDate) {
		// TODO: Needs to provide the section id from state somehow.
		return getService(state)
				.getFreeDays(startDate, endDate, "BADSECTIONID");
	}

	@Override
	public void setSelectedItem(Places places, State state) {
		// TODO Auto-generated method stub

	}
}
