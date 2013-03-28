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
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.booking.sectra.mock.BusinessObjectHolder;
import se.vgregion.webbtidbok.booking.sectra.mock.BusinessObjectHolder.InvalidExamNoException;
import se.vgregion.webbtidbok.booking.sectra.mock.BusinessObjectHolder.InvalidSectionIdException;
import se.vgregion.webbtidbok.booking.sectra.mock.BusinessObjectHolder.InvalidTimeIdException;
import se.vgregion.webbtidbok.booking.sectra.mock.BusinessObjectHolder.TimeAlreadyBookedException;
import se.vgregion.webbtidbok.booking.sectra.mock.BusinessObjectHolder.UnknownFailureException;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfBookingInfo;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfSection;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfTimeBlock;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfdateTime;
import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.IRisReschedule;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleRescheduleErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.Section;
import se.vgregion.webbtidbok.ws.sectra.TimeBlock;

public class SectraWSTestMock implements IRisReschedule {

	private BusinessObjectHolder businessObjectHolder;
	
	public void setBusinessObjectHolder(BusinessObjectHolder businessObjectHolder) {
		this.businessObjectHolder = businessObjectHolder;
	}

	@Override
	public BookingInfo getBookingInfo(String patientId, String examinationNr)
			throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {
		return businessObjectHolder.getBookingInfo(patientId, examinationNr);
	}

	@Override
	public ArrayOfBookingInfo getBookings(String patientId)
			throws IRisRescheduleGetBookingsErrorInfoFaultFaultMessage {
		 List<BookingInfo> bookings = businessObjectHolder.getBookings(patientId);
		 ArrayOfBookingInfo arrayOfBookingInfo = new ArrayOfBookingInfo();
		 arrayOfBookingInfo.getBookingInfo().addAll(bookings);
		 return arrayOfBookingInfo;
	}

	@Override
	public ArrayOfdateTime listFreeDays(XMLGregorianCalendar startDate,
			XMLGregorianCalendar endDate, String examinationNr, String sectionId)
			throws IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage {
		 List<XMLGregorianCalendar> listFreeDays = new ArrayList<XMLGregorianCalendar>();
		try {
			listFreeDays = businessObjectHolder.listFreeDays(startDate, endDate, examinationNr, sectionId);
		} catch (InvalidExamNoException e) {
			e.printStackTrace();
		} catch (InvalidSectionIdException e) {
			e.printStackTrace();
		}
		 ArrayOfdateTime arrayOfdateTime = new ArrayOfdateTime();
		 arrayOfdateTime.getDateTime().addAll(listFreeDays);
		 return arrayOfdateTime;
	}

	@Override
	public ArrayOfTimeBlock listFreeTimes(XMLGregorianCalendar startDate,
			XMLGregorianCalendar endDate, String examinationNr, String sectionId)
			throws IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage {
		List<TimeBlock> listFreeTimes = new ArrayList<TimeBlock>();
		try {
			listFreeTimes = businessObjectHolder.listFreeTimes(startDate, endDate, examinationNr, sectionId);
		} catch (InvalidExamNoException e) {
			e.printStackTrace();
		} catch (InvalidSectionIdException e) {
			e.printStackTrace();
		}
		ArrayOfTimeBlock arrayOfTimeBlock = new ArrayOfTimeBlock();
		arrayOfTimeBlock.getTimeBlock().addAll(listFreeTimes);
		
		return arrayOfTimeBlock;
	}

	@Override
	public ArrayOfSection listSections(String examinationNr)
			throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage {
		List<Section> listSections = new ArrayList<Section>();
		try {
			listSections = businessObjectHolder.listSections(examinationNr);
		} catch (InvalidExamNoException e) {
			e.printStackTrace();
		}
		ArrayOfSection arrayOfSection = new ArrayOfSection();
		arrayOfSection.getSection().addAll(listSections);
		return arrayOfSection;
	}

	@Override
	public BookingInfo reschedule(String examinationNr, String newTimeId,
			XMLGregorianCalendar startTime, Boolean printNewNotice,
			String rescheduleComment)
			throws IRisRescheduleRescheduleErrorInfoFaultFaultMessage {
		BookingInfo reschedule = null;
		try {
			reschedule = businessObjectHolder.reschedule(examinationNr, newTimeId, startTime);
		} catch (InvalidExamNoException e) {
			e.printStackTrace();
		} catch (InvalidTimeIdException e) {
			e.printStackTrace();
		} catch (TimeAlreadyBookedException e) {
			e.printStackTrace();
		} catch (UnknownFailureException e) {
			e.printStackTrace();
		}
		return reschedule;
	}



}
