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

package se.vgregion.webbtidbok.booking.elvis;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.domain.elvis.BookingElvis;
import se.vgregion.webbtidbok.lang.StringHandler;
import se.vgregion.webbtidbok.ws.BookingPlace;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.BookingTime;

/**
 * This class is used to map various Elvis specific values into the more general {@link se.vgregion.webbtidbok.Booking} class'
 * elements.
 * 
 * @author carstm
 * 
 */
public class BookingMapperElvis {
	private boolean forTest = false;

	/**
	 * Converts or maps Elvis specific BookingTime {@link se.vgregion.webbtidbok.ws.BookingTime} to this applications domain
	 * object {@link se.vgregion.webbtidbok.domain.BookingTime}
	 * 
	 * @param bookingTime
	 *            {@link se.vgregion.webbtidbok.ws.BookingTime}
	 * @return bookTime {@link se.vgregion.webbtidbok.domain.BookingTime}
	 */
	public se.vgregion.webbtidbok.domain.BookingTime bookingTimeMapping(BookingTime bookingTime) {
		se.vgregion.webbtidbok.domain.BookingTime bookTime = new se.vgregion.webbtidbok.domain.BookingTime();
		XMLGregorianCalendar date = bookingTime.getDatum();
		String time = bookingTime.getKlocka().getValue();
		String hour = time.substring(0, 2);
		String minute = time.substring(3, 5);
		date.setHour(Integer.parseInt(hour));
		date.setMinute(Integer.parseInt(minute));
		GregorianCalendar gregorianCalendar = date.toGregorianCalendar();
		Date dateTime = new Date(gregorianCalendar.getTimeInMillis());
		bookTime.setDateTime(dateTime);
		return bookTime;
	}

	/**
	 * Converts Elvis specific {@link se.vgregion.webbtidbok.ws.BookingResponse} to this applications domain object
	 * {@link se.vgregion.webbtidbok.domain.Booking}
	 * 
	 * @param bookingResponse
	 *            {@link BookingResponse}
	 * @return booking {@link se.vgregion.webbtidbok.Booking}
	 */
	public Booking bookingMapping(BookingResponse bookingResponse) {
		BookingElvis booking = new BookingElvis();
		// // TODO: set null here only for DEV prpose while mockin errors
		// bookingResponse.setNamn(null);
		// booking.setPatientName(changePatientNameStructure(bookingResponse.getNamn().getValue()));
		booking.setPatientName(changePatientNameStructure(getStringValueFromBooking(bookingResponse.getNamn())));

		Surgery surgery = new Surgery();
		if (bookingResponse.getCentralTidbokID() != null) {
			surgery.setSurgeryId(Integer.toString(bookingResponse.getCentralTidbokID()));
		}
		surgery.setSurgeryName(getStringValueFromBooking(bookingResponse.getMottagning()));
		surgery.setSurgeryAddress(getStringValueFromBooking(bookingResponse.getAddress()));
		surgery.setSurgeryPhone("");
		booking.setSurgery(surgery);
		booking.setPatientId(getStringValueFromBooking(bookingResponse.getPnr()));
		booking.setStartTime(getDateFromCalendar(bookingResponse.getBokadTid()));
		booking.setUpdateable(isUpdateable(bookingResponse));
		booking.setElvisExaminationType(getStringValueFromBooking(bookingResponse.getUndersokningsTypKod()));
		return booking;
	}

	private String getStringValueFromBooking(JAXBElement<String> jaxbElement) {
		String value = "";
		if (jaxbElement != null) {
			value = jaxbElement.getValue();
		}
		return value;
	}

	private Date getDateFromCalendar(XMLGregorianCalendar calendar) {
		Date value = null;
		if (calendar != null) {
			value = calendar.toGregorianCalendar().getTime();
		}
		return value;
	}

	/**
	 * This determines whether the patient can reschedule for a new appointment or not. If bookingResponse.getAntalOmbok() <=
	 * bookingResponse.getMaxAntalOmbok() = true, then the patient can till reschedule.
	 * 
	 * @param bookingResponse
	 *            {@link se.vgregion.webbtidbok.ws.BookingResponse}
	 * @return value {@link Boolean} whether the patient can reschedule appointment or not.
	 */
	private boolean isUpdateable(BookingResponse bookingResponse) {
		boolean value = false;
		if (bookingResponse.getAntalOmbok() != null && bookingResponse.getMaxAntalOmbok() != null) {
			if (forTest) {
				value = true;
			} else if (!forTest) {
				value = bookingResponse.getAntalOmbok() <= bookingResponse.getMaxAntalOmbok();
			}
		}
		return value;
	}

	/**
	 * The name retrieved from the Elvis Webservice has the structure "SURNAME, MIDDLE NAME, NAME", this method returns
	 * "Name Middlename Surname"
	 * 
	 * @param name
	 *            "SURNAME, MIDDLE NAME, NAME"
	 * @return name returns "Name Middlename Surname"
	 */
	private String changePatientNameStructure(String name) {
		name = StringHandler.capitalizeName(name);
		String theNames[] = name.split(",");
		if (theNames.length == 2) {
			name = theNames[1].trim() + " " + theNames[0].trim();
		}
		return name;
	}

	/**
	 * Converts an Elvis specific {@link se.vgregion.webbtidbok.ws.BookingPlace} to this application's domain object
	 * {@link Surgery}
	 * 
	 * @param bookingPlace
	 *            {@link se.vgregion.webbtidbok.ws.BookingPlace}
	 * @return surgery {@link Surgery}
	 */
	public Surgery bookingPlaceMapping(BookingPlace bookingPlace) {
		Surgery surgery = new Surgery();
		surgery.setSurgeryAddress(getStringValueFromBooking(bookingPlace.getAddress()));
		if (bookingPlace.getCentralTidbokID() != null) {
			surgery.setSurgeryId(Integer.toString(bookingPlace.getCentralTidbokID()));
		}
		surgery.setSurgeryName(getStringValueFromBooking(bookingPlace.getMottagning()));
		return surgery;
	}

	public Calendar daysMapping(se.vgregion.webbtidbok.ws.Calendar wrapCal) {
		return wrapCal.getDatum().toGregorianCalendar();
	}

	public boolean isForTest() {
		return forTest;
	}

	public void setForTest(boolean forTest) {
		this.forTest = forTest;
	}

}
