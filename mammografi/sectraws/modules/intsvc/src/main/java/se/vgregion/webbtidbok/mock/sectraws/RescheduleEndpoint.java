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
package se.vgregion.webbtidbok.mock.sectraws;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.oxm.Marshaller;
import org.springframework.ws.server.endpoint.AbstractMarshallingPayloadEndpoint;

import se.vgregion.webbtidbok.mock.sectraws.BusinessObjectHolder.InvalidExamNoException;
import se.vgregion.webbtidbok.mock.sectraws.BusinessObjectHolder.InvalidSectionIdException;
import se.vgregion.webbtidbok.mock.sectraws.BusinessObjectHolder.InvalidTimeIdException;
import se.vgregion.webbtidbok.mock.sectraws.BusinessObjectHolder.TimeAlreadyBookedException;
import se.vgregion.webbtidbok.mock.sectraws.BusinessObjectHolder.UnknownFailureException;
import se.vgregion.webbtidbok.mock.sectraws.jaxb.*;

public class RescheduleEndpoint extends AbstractMarshallingPayloadEndpoint {
    
    protected ObjectFactory objectFactory;
    protected BusinessObjectHolder objectHolder;

    public RescheduleEndpoint(BusinessObjectHolder holder, Marshaller marshaller) {
        super(marshaller);
        objectFactory = new ObjectFactory();
        objectHolder = holder;
    }
    
    @Override
    protected Object invokeInternal(Object requestObject) throws Exception {
        
        Object responseObject = null;
        
        if (requestObject instanceof GetBookingInfo) {
            responseObject = getBookingInfo((GetBookingInfo)requestObject);
        } else if (requestObject instanceof GetBookings) {
            responseObject = getBookings((GetBookings)requestObject);
        } else if (requestObject instanceof ListFreeDays) {
            responseObject = listFreeDays((ListFreeDays)requestObject);
        } else if (requestObject instanceof ListFreeTimes) {
            responseObject = listFreeTimes((ListFreeTimes)requestObject);
        } else if (requestObject instanceof ListSections) {
            responseObject = listSections((ListSections)requestObject);
        } else if (requestObject instanceof Reschedule) {
            responseObject = reschedule((Reschedule)requestObject);
        } else {
            responseObject = failure("Unknown");
        }

        return responseObject;
    }

    private Object getBookingInfo(GetBookingInfo request) {
        BookingInfo bi = objectHolder.getBookingInfo(request.getPatientId().getValue(),
                request.getExaminationNr().getValue());

        if (bi != null) {
            GetBookingInfoResponse response = objectFactory.createGetBookingInfoResponse();
            response.setGetBookingInfoResult(objectFactory.createBookingInfo(bi));
            return response;
        } else {
            return failure("IvalidPatientId");
        }
    }
    
    private Object getBookings(GetBookings request) {
        List<BookingInfo> bookings = objectHolder.getBookings(request.getPatientId().getValue());
        ArrayOfBookingInfo bookingArray = objectFactory.createArrayOfBookingInfo();
        bookingArray.getBookingInfo().addAll(bookings);
        
        GetBookingsResponse response = objectFactory.createGetBookingsResponse();
        response.setGetBookingsResult(objectFactory.createArrayOfBookingInfo(bookingArray));

        return response;
    }

    private Object listFreeDays(ListFreeDays request) {
            try {
                List<XMLGregorianCalendar> freeDays = objectHolder.listFreeDays(request.getStartDate(),
                    request.getEndDate(), request.getExaminationNr().getValue(), request.getSectionId().getValue());
                ArrayOfdateTime daysArray = objectFactory.createArrayOfdateTime();
                daysArray.getDateTime().addAll(freeDays);
                
                ListFreeDaysResponse response = objectFactory.createListFreeDaysResponse();
                response.setListFreeDaysResult(objectFactory.createArrayOfdateTime(daysArray));
                return response;
            } catch (InvalidExamNoException e) {
                return failure("InvalidExaminationId");
            } catch (InvalidSectionIdException e) {
                return failure("InvalidSectionId");
            }
    }

    private Object listFreeTimes(ListFreeTimes request) {
        try {
            List<TimeBlock> freeTimes = objectHolder.listFreeTimes(request.getStartDate(),
                request.getEndDate(), request.getExaminationNr().getValue(), request.getSectionId().getValue());
            ArrayOfTimeBlock timesArray = objectFactory.createArrayOfTimeBlock();
            timesArray.getTimeBlock().addAll(freeTimes);
            
            ListFreeTimesResponse response = objectFactory.createListFreeTimesResponse();
            response.setListFreeTimesResult(objectFactory.createArrayOfTimeBlock(timesArray));
            return response;
        } catch (InvalidExamNoException e) {
            return failure("InvalidExaminationId");
        } catch (InvalidSectionIdException e) {
            return failure("InvalidSectionId");
        }
    }

    private Object listSections(ListSections request) {
        try {
            List<Section> sections = objectHolder.listSections(request.getExaminationNr().getValue());
            ArrayOfSection sectionArray = objectFactory.createArrayOfSection();
            sectionArray.getSection().addAll(sections);
            
            ListSectionsResponse response = objectFactory.createListSectionsResponse();
            response.setListSectionsResult(objectFactory.createArrayOfSection(sectionArray));
            return response;
        } catch (InvalidExamNoException e) {
            return failure("InvalidExaminationId");
        }
    }
    
    private Object reschedule(Reschedule request) {
        try {
            BookingInfo newBooking = objectHolder.reschedule(request.getExaminationNr().getValue(),
                    request.getNewTimeId().getValue(), request.getStartTime());
            
            RescheduleResponse response = objectFactory.createRescheduleResponse();
            response.setRescheduleResult(objectFactory.createBookingInfo(newBooking));
            return response;
        } catch (InvalidExamNoException e) {
            return failure("InvalidExaminationId");
        } catch (InvalidTimeIdException e) {
            return failure("InvalidTimeId");
        } catch (TimeAlreadyBookedException e) {
            return failure("TimeAlreadyBooked");
        } catch (UnknownFailureException e) {
            return failure("Unknown");
        }
    }

    /**
     * Produce an error response.
     * @param error The error code, as defined in ErrorInfoErrorCodeEnum.
     * @return An ErrorInfo response object.
     */
    private ErrorInfo failure(String error) {
        ErrorInfo err = objectFactory.createErrorInfo();
        err.setErrorNumber(ErrorInfoErrorCodeEnum.fromValue(error));
        return err;
    }
}
