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
package se.vgregion.webbtidbok.booking;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import se.vgregion.webbtidbok.Places;
import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.BookingTime;

/**
 * This class is just empty stubs. Use it as a base class for your test case
 * classes, so you don't have to implement all methods in every testcase.
 * 
 * @author anders.henriksson@knowit.se
 */
public class BookingFacadeDummy implements BookingFacade {

	@Override
	public boolean login(State state) {
		return false;
	}

	@Override
	public se.vgregion.webbtidbok.domain.Booking getBookingInfo(State state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SelectItem> getBookingPlaceSelectItems(State state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSelectedDefaultItem(State state) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Places getSelectedPlace(Places places, State state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Date> getFreeDays(State state, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelectedItem(Places places, State state) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BookingTime> getBookingTime(State state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getIsTimeListEmpty(State state) {
		// TODO Auto-generated method stub
		return false;
	}

}
