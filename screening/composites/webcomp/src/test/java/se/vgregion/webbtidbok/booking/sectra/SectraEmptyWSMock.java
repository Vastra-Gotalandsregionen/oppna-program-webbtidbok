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

import java.util.List;

import javax.jws.WebParam;
import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.ws.sectra.*;

/**
 * This is an empty mock class. Use this if you need a Sectra mock and don't want to implement
 * each and every method yourself.
 */
public class SectraEmptyWSMock implements IRisReschedule {

    @Override
    public void cancelExamination(@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "cancelComment", targetNamespace = "http://tempuri.org/") String cancelComment, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleCancelExaminationErrorInfoFaultFaultMessage {

    }

    @Override
    public BookingInfo getBookingInfo(@WebParam(name = "patientId", targetNamespace = "http://tempuri.org/") String patientId, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {
        return null;
    }

    @Override
    public ArrayOfBookingInfo getBookingInfo2(@WebParam(name = "patientId", targetNamespace = "http://tempuri.org/") String patientId, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleGetBookingInfo2ErrorInfoFaultFaultMessage {
        return null;
    }

    @Override
    public ArrayOfBookingInfo getBookings(@WebParam(name = "patientId", targetNamespace = "http://tempuri.org/") String patientId, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleGetBookingsErrorInfoFaultFaultMessage {
        return null;
    }

    @Override
    public ArrayOfSection listSections(@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage {
        return null;
    }

    @Override
    public ArrayOfTimeBlock listFreeTimes(@WebParam(name = "startDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startDate, @WebParam(name = "endDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar endDate, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "sectionId", targetNamespace = "http://tempuri.org/") String sectionId, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage {
        return null;
    }

    @Override
    public ArrayOfdateTime listFreeDays(@WebParam(name = "startDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startDate, @WebParam(name = "endDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar endDate, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "sectionId", targetNamespace = "http://tempuri.org/") String sectionId, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage {
        return null;
    }

    @Override
    public BookingInfo reschedule(@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "newTimeId", targetNamespace = "http://tempuri.org/") String newTimeId, @WebParam(name = "startTime", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startTime, @WebParam(name = "printNewNotice", targetNamespace = "http://tempuri.org/") Boolean printNewNotice, @WebParam(name = "rescheduleComment", targetNamespace = "http://tempuri.org/") String rescheduleComment, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent, @WebParam(name = "updatePerformingSite", targetNamespace = "http://tempuri.org/") Boolean updatePerformingSite) throws IRisRescheduleRescheduleErrorInfoFaultFaultMessage {
        return null;
    }

    /**
     * Use this class when mocking the getBookings call.
     */
    public class ArrayOfBookingInfoMock extends ArrayOfBookingInfo {
        public ArrayOfBookingInfoMock(List<BookingInfo> list) {
            bookingInfo = list;
        }
    }

    /**
     * Use this class when mocking the listFreeDays call.
     */
    public class ArrayOfdateTimeMock extends ArrayOfdateTime {
        public ArrayOfdateTimeMock(List<XMLGregorianCalendar> list) {
            dateTime = list;
        }
    }


    /**
     * Use this class when mocking the listFreeTimes call.
     */
    public class ArrayOfTimeBlockMock extends ArrayOfTimeBlock {
        public ArrayOfTimeBlockMock(List<TimeBlock> list) {
            timeBlock = list;
        }
    }

    /**
     * Use this class when mocking the listSections call.
     */
    public class ArrayOfSectionMock extends ArrayOfSection {
        public ArrayOfSectionMock(List<Section> list) {
            section = list;
        }
    }

}
