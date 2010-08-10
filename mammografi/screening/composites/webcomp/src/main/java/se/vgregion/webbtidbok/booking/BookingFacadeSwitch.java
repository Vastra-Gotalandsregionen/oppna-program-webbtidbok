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


import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.sectra.SectraBookingFacadeImpl;
import se.vgregion.webbtidbok.domain.Booking;

/**
 * Wrap elvis and sectra specific booking classes in order to generalize their
 * return. This would enable the BookingFacade to represent both of them and to
 * in turn be utilized by the presentation layer.
 * 
 * @author carstm
 * 
 */

// adapt these var names as needed to fit existing xhtml code and vars there in,
// example: name changed from name to displayName
public class BookingFacadeSwitch implements BookingFacade {

	private SectraBookingFacadeImpl facade;

	public void setFacade(SectraBookingFacadeImpl facade) {
		this.facade = facade;
	}

	public BookingFacadeSwitch() {
	}

	@Override
	public Booking getBookingInfo(State state) {
		if (state.getService().equals("MAMMO_SU")) {
			System.out.println("### getService.equals(\"MAMMO_SU\")");
			Booking booking = null;
			System.out
					.println("### BookingWrapper.getBookingInfo(state). State pnr & passw: "
							+ state.getPnr() + ", " + state.getPasswd());
			booking = facade.getBookingInfo(state);
			System.out
					.println("### returning wrapper after getBookingInfo call");
			return booking;
		}
		if (state.getService().equals("MAMMO_NU")) {
			// TODO: add logic here
		}
		if (state.getService().equals("BUKAORTA")) {
			// TODO: add logic here
		}
		return null;
	}

	@Override
	public boolean login(State state) {
		return false;
	}
}