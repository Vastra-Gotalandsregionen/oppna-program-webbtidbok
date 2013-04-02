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
import java.util.Date;

import se.vgregion.webbtidbok.domain.elvis.BookingTimeElvis;
import se.vgregion.webbtidbok.domain.sectra.BookingTimeSectra;

/**
 * This class is the application's domain object for booking time. {@link BookingTimeElvis} and {@link BookingTimeSectra} are both
 * mapped into this domain class through {@link BookingMapperElvis} and {@BookingMapperSectra}.
 * 
 * @author carstm
 * 
 */
public class BookingTime implements Serializable {

	private static final long serialVersionUID = 5023666003372389346L;
	private String bookingTimeId;
	private Date dateTime;

	public void setBookingTimeId(String bookingTimeId) {
		this.bookingTimeId = bookingTimeId;
	}

	public String getBookingTimeId() {
		return bookingTimeId;
	}

	public void setDateTime(Date dateTime) {
		if (dateTime != null) {
			this.dateTime = (Date) dateTime.clone();
		}
	}

	public Date getDateTime() {
		return dateTime != null ? (Date) dateTime.clone() : null;
	}

}
