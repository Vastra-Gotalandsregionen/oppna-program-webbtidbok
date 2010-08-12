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

import java.util.Date;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import org.aspectj.weaver.tools.ISupportsMessageContext;

import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.elvis.BookingElvis;
import se.vgregion.webbtidbok.lang.StringHandler;
import se.vgregion.webbtidbok.ws.BookingPlace;
import se.vgregion.webbtidbok.ws.BookingResponse;

public class BookingMapperElvis {

	public Booking bookingMapping(BookingResponse bookingResponse) {
		BookingElvis booking = new BookingElvis();
		booking
				.setPatientName(changePatientNameStructure(getStringValueFromBooking(bookingResponse
						.getNamn())));
		booking.setSurgeryAddress(getStringValueFromBooking(bookingResponse
				.getMottagning())
				+ ", "
				+ getStringValueFromBooking(bookingResponse.getAddress()));
		booking
				.setPatientId(getStringValueFromBooking(bookingResponse
						.getPnr()));
		booking
				.setStartTime(getDateFromCalendar(bookingResponse.getBokadTid()));
		booking.setUpdateable(isUpdateable(bookingResponse));

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
		if (bookingResponse.getAntalOmbok() != null
				&& bookingResponse.getMaxAntalOmbok() != null) {
			value = bookingResponse.getAntalOmbok() <= bookingResponse
					.getMaxAntalOmbok();
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

	public se.vgregion.webbtidbok.domain.BookingPlace bookingPlaceMapping(
			BookingPlace bookingPlace) {
		se.vgregion.webbtidbok.domain.BookingPlace bookingPlaceTemp = new se.vgregion.webbtidbok.domain.BookingPlace();
		bookingPlaceTemp.setAddress(getStringValueFromBooking(bookingPlace.getAddress()));
		if (bookingPlace.getCentralTidbokID() != null){
			bookingPlaceTemp
			.setCentralTimeBookId(bookingPlace.getCentralTidbokID());
		}
		bookingPlaceTemp.setClinic(getStringValueFromBooking(bookingPlace.getMottagning()));
		return bookingPlaceTemp;
	}
}