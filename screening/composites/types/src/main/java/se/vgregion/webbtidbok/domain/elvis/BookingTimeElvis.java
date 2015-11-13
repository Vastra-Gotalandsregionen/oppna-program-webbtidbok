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

package se.vgregion.webbtidbok.domain.elvis;

import se.vgregion.webbtidbok.domain.BookingTime;

/**
 * This constitutes the term Booking Time for a Surgery that has it's time book held within Elvis. See {@link se.vgregion.webbtidbok.booking.elvis.BookingMapperElvis}
 * for further information on how and what data are aggregated into a {@link se.vgregion.webbtidbok.domain.BookingTime}.
 * 
 * @author carstm
 * 
 */
public class BookingTimeElvis extends BookingTime {

	private static final long serialVersionUID = 1L;

	private int numbers;
	private String day = "";

	public BookingTimeElvis() {
	}

	public void setDay(String s) {
		day = s;
	}

	public String getDay() {
		return day;
	}

	public void setNumbers(int number) {
		numbers = number;
	}

	public int getNumbers() {
		return numbers;
	}

}
