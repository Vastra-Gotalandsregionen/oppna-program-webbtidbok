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

import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfSection;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfdateTime;
import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.IRisReschedule;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.Section;

public class SectraBookingServiceImpl implements SectraBookingServiceInterface {
    
    private IRisReschedule thePort;
    private BookingMapperSectra bookingMapperSectra;

    private String patientNr;
    private String examinationNr;

	public void setPatientNr(String patientNr) {
	    this.patientNr = patientNr;
	}
	
	public void setExaminationNr(String examinationNr) {
	    this.examinationNr = examinationNr;
	}
	
	public void setThePort(IRisReschedule thePort) {
		this.thePort = thePort;
	}

	public void setBookingMapperSectra(BookingMapperSectra bookingMapperSectra) {
		this.bookingMapperSectra = bookingMapperSectra;
	}

	
	@Override
	public Booking getBooking() {
		Booking booking;

		try {
			BookingInfo bi = thePort.getBookingInfo(patientNr, examinationNr);
			booking = bookingMapperSectra.bookingMapping(bi);
		} catch (IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage e) {
		    // TODO: Is this really the proper response if the web service fails?
		    e.printStackTrace();
			booking = new BookingSectra();
		}
		return booking;
	}

	@Override
	public List<Surgery> getSurgeries() {
		List<Surgery> surgeries = new ArrayList<Surgery>();
		try {
			ArrayOfSection sections = thePort.listSections(examinationNr);
			List<Section> sectionList = sections.getSection();
			for (Section section : sectionList) {
				Surgery surgery = bookingMapperSectra.surgeryMapping(section);
				surgeries.add(surgery);
			}
		} catch (IRisRescheduleListSectionsErrorInfoFaultFaultMessage e) {
			e.printStackTrace();
            throw new RuntimeException("Error response from web service when getting surgeries.", e);
		}
		return surgeries;
	}

    @Override
    public List<Date> getFreeDays(Date startDate, Date endDate, String sectionId) {
        List<Date> dates = new ArrayList<Date>();
        
        ArrayOfdateTime times;
        try {
            times = thePort.listFreeDays(bookingMapperSectra.dateToXmlCalendar(startDate),
                    bookingMapperSectra.dateToXmlCalendar(endDate), examinationNr, sectionId);
            List<XMLGregorianCalendar> timesList = times.getDateTime();
            for (XMLGregorianCalendar time : timesList) {
                Date date = bookingMapperSectra.daysMapping(time);
                dates.add(date);
            }
            return dates;
        } catch (IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage e) {
            e.printStackTrace();
            throw new RuntimeException("Error response from web service when getting free days.", e);
        }
    }


}
