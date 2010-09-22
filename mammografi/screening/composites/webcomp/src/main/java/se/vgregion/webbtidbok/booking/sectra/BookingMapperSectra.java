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

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;
import se.vgregion.webbtidbok.domain.sectra.BookingTimeSectra;
import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.Section;
import se.vgregion.webbtidbok.ws.sectra.TimeBlock;

public class BookingMapperSectra {

	public Booking bookingMapping(BookingInfo bookingInfo) {
		BookingSectra booking = new BookingSectra();
		booking.setUpdateable(true);
		booking.setPatientName(getStringValue(bookingInfo.getPatientName()));
		booking.setPatientId(getStringValue(bookingInfo.getPatientId()));
		booking.setExamTypeCode(getStringValue(bookingInfo.getExamTypeCode()));
		booking.setExamType(getStringValue(bookingInfo.getExamType()));
		booking.setExaminationId(getStringValue(bookingInfo.getExamNo()));

		if (bookingInfo.getBookedTime() != null) {
			TimeBlock value = bookingInfo.getBookedTime().getValue();
			XMLGregorianCalendar startTime = value.getStartTime();
			booking.setStartTime(startTime.toGregorianCalendar().getTime());
			JAXBElement<Section> section = bookingInfo.getBookedTime()
					.getValue().getSection();
			booking.setSurgery(surgeryMapping(section.getValue()));
		}
		return booking;
	}

	public Surgery surgeryMapping(Section section) {
		Surgery surgery = new Surgery();
		surgery.setSurgeryId(getStringValue(section.getId()));
		surgery.setSurgeryName(parseDescriptionForSurgeryName(getStringValue(section.getDescription())));
		surgery.setSurgeryAddress(getStringValue(section.getAddress()));
        surgery.setSurgeryPhone(getStringValue(section.getPhone()));
		return surgery;
	}
	
	/* Only use this with the getStringValue method below, or null pointers may occur.
	 * 
	 * This gets a usable surgery name from the section description.
	 * Please note that if no #-sign exists in the description, then we
	 * will not provide any name at all for this surgery.
	 */
	private String parseDescriptionForSurgeryName(String description) {
	    String surgeryName = "";
	    if (description.contains("#")) {
	        String[] temp = description.split("#", 2);
	        if (temp.length > 1) {
	            surgeryName = temp[0];
	        } else {
	            // TODO: Log this failure, perhaps?
	        }
	    }
	    
	    return surgeryName;
	}

	private String getStringValue(JAXBElement<String> jaxbElement) {
		String value = "";
		if (jaxbElement != null) {
			value = jaxbElement.getValue();
		}
		return value;
	}

	public XMLGregorianCalendar dateToXmlCalendar(Calendar date) {
	    GregorianCalendar c = new GregorianCalendar();
	    c.setTime(date.getTime());
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not create XMLGregorianCalendar",
					e);
		}
	}

	public Calendar daysMapping(XMLGregorianCalendar time) {
		return time.toGregorianCalendar();
	}

    public BookingTime bookingTimeMapping(TimeBlock time) {
        BookingTimeSectra bookingTime = new BookingTimeSectra();
        bookingTime.setBookingTimeId(getStringValue(time.getId()));
        bookingTime.setDateTime(time.getStartTime().toGregorianCalendar().getTime());
        bookingTime.setLength(time.getLength().intValue());
        bookingTime.setSurgery(surgeryMapping(time.getSection().getValue()));
        return bookingTime;
    }

}
