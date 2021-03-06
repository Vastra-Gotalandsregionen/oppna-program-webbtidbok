/**
 * Copyright 2010 Västra Götalandsregionen
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
 *
 */

package se.vgregion.webbtidbok.domain;

import java.io.Serializable;

/**
 * A BookingPlace is a physical examination unit.
 * 
 * @author carstm
 * 
 */
public class BookingPlace implements Serializable {

	private static final long serialVersionUID = 1L;
	private int centralTimeBookingId = 0;
	private String address = "";
	private String clinic = "";

	public void setCentralTimeBookId(int id) {
		centralTimeBookingId = id;
	}

	/**
	 * The CentralTidBookId identifies the Time Book which the current BookingPlace has. This id is used to retrieve dates and
	 * times available for booking appointments.
	 * 
	 * CentralTidBookId is mainly an Elvis specific concern.
	 * 
	 * @return centralTimeBookingId the id of the Time Book tied to the specific BookingPlace.
	 */
	public int getCentralTimeBookId() {
		return centralTimeBookingId;
	}

	public void setAddress(String s) {
		address = s;
	}

	public String getAddress() {
		return address;
	}

	public void setClinic(String s) {
		clinic = s;
	}

	public String getClinic() {
		return clinic;
	}

	public String toString() {
		return "centralTimeBookingId: " + Integer.toString(this.getCentralTimeBookId()) + " Address: " + this.getAddress()
				+ " Mottagning: " + this.getClinic();
	}
}
