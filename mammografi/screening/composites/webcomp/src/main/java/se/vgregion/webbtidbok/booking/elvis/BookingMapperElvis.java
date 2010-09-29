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

public class BookingMapperElvis {

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

	public Booking bookingMapping(BookingResponse bookingResponse) {
		BookingElvis booking = new BookingElvis();
		booking.setPatientName(changePatientNameStructure(getStringValueFromBooking(bookingResponse.getNamn())));

		Surgery surgery = new Surgery();
		if (bookingResponse.getCentralTidbokID() != null) {
			surgery.setSurgeryId(Integer.toString(bookingResponse.getCentralTidbokID()));
		}
		surgery.setSurgeryName(getStringValueFromBooking(bookingResponse.getMottagning()));
		surgery.setSurgeryAddress(getStringValueFromBooking(bookingResponse.getAddress()));
		// TODO: Shall this be used on Elvis bookings? Currently setting blank.
        // surgery.setSurgeryPhone(getStringValueFromBooking(bookingResponse.getMobilTel()));
        surgery.setSurgeryPhone("");
		booking.setSurgery(surgery);
		booking.setPatientId(getStringValueFromBooking(bookingResponse.getPnr()));
		booking.setStartTime(getDateFromCalendar(bookingResponse.getBokadTid()));
		booking.setUpdateable(isUpdateable(bookingResponse));

		// this is only to emulate future bookingResponse.getExaminationType() or similar to cater for distinction between exam
		// types gyn and bukaorta
		booking.setElvisExaminationType(getStringValueFromBooking(bookingResponse.getNamn()));
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

	private boolean isUpdateable(BookingResponse bookingResponse) {
		boolean value = false;
		if (bookingResponse.getAntalOmbok() != null && bookingResponse.getMaxAntalOmbok() != null) {
			value = bookingResponse.getAntalOmbok() <= bookingResponse.getMaxAntalOmbok();
		}
		return value;
	}

	private String changePatientNameStructure(String name) {
		name = StringHandler.capitalizeName(name);
		String theNames[] = name.split(",");
		if (theNames.length == 2) {
			name = theNames[1].trim() + " " + theNames[0].trim();
		}
		return name;
	}

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
}
