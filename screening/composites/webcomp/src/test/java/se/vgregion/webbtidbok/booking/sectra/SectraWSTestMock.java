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

package se.vgregion.webbtidbok.booking.sectra;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebParam;
import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.booking.sectra.mock.BusinessObjectHolder;
import se.vgregion.webbtidbok.booking.sectra.mock.BusinessObjectHolder.InvalidExamNoException;
import se.vgregion.webbtidbok.booking.sectra.mock.BusinessObjectHolder.InvalidSectionIdException;
import se.vgregion.webbtidbok.booking.sectra.mock.BusinessObjectHolder.InvalidTimeIdException;
import se.vgregion.webbtidbok.booking.sectra.mock.BusinessObjectHolder.TimeAlreadyBookedException;
import se.vgregion.webbtidbok.booking.sectra.mock.BusinessObjectHolder.UnknownFailureException;
import se.vgregion.webbtidbok.ws.sectra.*;

public class SectraWSTestMock implements IRisReschedule {

	private BusinessObjectHolder businessObjectHolder;

	public void setBusinessObjectHolder(BusinessObjectHolder businessObjectHolder) {
		this.businessObjectHolder = businessObjectHolder;
	}

	@Override
	public void cancelExamination(@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "cancelComment", targetNamespace = "http://tempuri.org/") String cancelComment, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleCancelExaminationErrorInfoFaultFaultMessage {

	}

	@Override
	public BookingInfo getBookingInfo(@WebParam(name = "patientId", targetNamespace = "http://tempuri.org/") String patientId, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {
		return businessObjectHolder.getBookingInfo(patientId, examinationNr);	}

	@Override
	public ArrayOfBookingInfo getBookingInfo2(@WebParam(name = "patientId", targetNamespace = "http://tempuri.org/") String patientId, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleGetBookingInfo2ErrorInfoFaultFaultMessage {
		return null;
	}

	@Override
	public ArrayOfBookingInfo getBookings(@WebParam(name = "patientId", targetNamespace = "http://tempuri.org/") String patientId, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleGetBookingsErrorInfoFaultFaultMessage {
		List<BookingInfo> bookings = businessObjectHolder.getBookings(patientId);
		ArrayOfBookingInfo arrayOfBookingInfo = new ArrayOfBookingInfo();
		arrayOfBookingInfo.getBookingInfo().addAll(bookings);
		return arrayOfBookingInfo;
	}

	@Override
	public ArrayOfSection listSections(@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage {
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
	public ArrayOfTimeBlock listFreeTimes(@WebParam(name = "startDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startDate, @WebParam(name = "endDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar endDate, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "sectionId", targetNamespace = "http://tempuri.org/") String sectionId, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage {
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
	public ArrayOfdateTime listFreeDays(@WebParam(name = "startDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startDate, @WebParam(name = "endDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar endDate, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "sectionId", targetNamespace = "http://tempuri.org/") String sectionId, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage {
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
	public BookingInfo reschedule(@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "newTimeId", targetNamespace = "http://tempuri.org/") String newTimeId, @WebParam(name = "startTime", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startTime, @WebParam(name = "printNewNotice", targetNamespace = "http://tempuri.org/") Boolean printNewNotice, @WebParam(name = "rescheduleComment", targetNamespace = "http://tempuri.org/") String rescheduleComment, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent, @WebParam(name = "updatePerformingSite", targetNamespace = "http://tempuri.org/") Boolean updatePerformingSite) throws IRisRescheduleRescheduleErrorInfoFaultFaultMessage {
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
