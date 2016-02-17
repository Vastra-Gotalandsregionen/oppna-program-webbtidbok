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


import se.vgregion.webbtidbok.errorhandling.UnableToConnectToWSException;
import se.vgregion.webbtidbok.mail.ErrorMail;
import se.vgregion.webbtidbok.ws.sectra.*;
import se.vgregion.webbtidbok.ws.sectra.RisRescheduleService;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadPoolExecutor;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.apache.log4j.Logger;

/**
 * Used as a layer above the Sectra WebService connection in order to keep the application from freezing
 * on startup. If any errors occur while connecting to the WS an email is sent to a service address and
 * the service is never instantiated.
 *
 *   TODO: tryConnection should be running repeatedly in a thread in order to regain
 *   connection when WS is back online.
 *
 *
 */
public class SectraTryWebService implements IRisReschedule {

    private URL wsURL;
    private QName qName;
    private ThreadPoolExecutor mailQueue;
    private IRisReschedule bookingService;
    private ResourceBundle errorMailProperties;
    private String serviceName;
    private Logger logger;

    public SectraTryWebService(ThreadPoolExecutor mailQueue, String errorMailProperties, String serviceName){
        this(null, null, mailQueue, errorMailProperties, serviceName);
    }

    public SectraTryWebService(URL wsURL, QName qName, ThreadPoolExecutor mailQueue, String errorMailProperties, String serviceName){
        this.wsURL = wsURL;
        this.qName = qName;
        this.mailQueue = mailQueue;
        this.errorMailProperties = ResourceBundle.getBundle(errorMailProperties);
        this.serviceName = serviceName;
        this.logger = Logger.getLogger("BookingRetryService.class");

        this.tryConnection();
    }

    public void tryConnection(){
        try{
            RisRescheduleService risr;
            if(this.wsURL != null && this.qName != null){
                risr = new RisRescheduleService(this.wsURL, this.qName);
            } else {
                risr = new RisRescheduleService();
            }
            this.bookingService = risr.getHttps();
        } catch (Exception e){
            logger.error("could not connect to... " + this.serviceName, e);
            e.printStackTrace();
            if (mailQueue != null)
                mailQueue.execute(new ErrorMail(errorMailProperties, this.serviceName, e));
        }
    }

    static {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    @Override
    public void cancelExamination(@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "cancelComment", targetNamespace = "http://tempuri.org/") String cancelComment, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleCancelExaminationErrorInfoFaultFaultMessage {

    }



    @Override
    public BookingInfo getBookingInfo(@WebParam(name = "patientId", targetNamespace = "http://tempuri.org/") String patientId, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {
        if(bookingService != null){
            return bookingService.getBookingInfo(patientId, examinationNr, allowMultiExamination, allowUrgent);
        }
        else{
            throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
        }
    }

    @Override
    public ArrayOfBookingInfo getBookingInfo2(@WebParam(name = "patientId", targetNamespace = "http://tempuri.org/") String patientId, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleGetBookingInfo2ErrorInfoFaultFaultMessage {
        if(bookingService != null){
            return bookingService.getBookingInfo2(patientId, examinationNr, allowMultiExamination, allowUrgent);
        }
        else{
            throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
        }
    }

    @Override
    public ArrayOfBookingInfo getBookings(@WebParam(name = "patientId", targetNamespace = "http://tempuri.org/") String patientId, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleGetBookingsErrorInfoFaultFaultMessage {
        if(bookingService != null){
            return bookingService.getBookings(patientId, allowMultiExamination, allowUrgent);
        }
        else{
            throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
        }
    }

    @Override
    public ArrayOfSection listSections(@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage {
        if(bookingService != null){
            return bookingService.listSections(examinationNr, allowMultiExamination, allowUrgent);
        }
        else{
            throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
        }
    }

    @Override
    public ArrayOfTimeBlock listFreeTimes(@WebParam(name = "startDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startDate, @WebParam(name = "endDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar endDate, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "sectionId", targetNamespace = "http://tempuri.org/") String sectionId, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage {
        if(bookingService != null){
            return bookingService.listFreeTimes(startDate, endDate, examinationNr, sectionId, allowMultiExamination, allowUrgent);
        }
        else{
            throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
        }
    }

    @Override
    public ArrayOfdateTime listFreeDays(@WebParam(name = "startDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startDate, @WebParam(name = "endDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar endDate, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "sectionId", targetNamespace = "http://tempuri.org/") String sectionId, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage {
        if(bookingService != null){
            return bookingService.listFreeDays(startDate, endDate, examinationNr, sectionId, allowMultiExamination, allowUrgent);
        }
        else{
            throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
        }
    }

    @Override
    public BookingInfo reschedule(@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "newTimeId", targetNamespace = "http://tempuri.org/") String newTimeId, @WebParam(name = "startTime", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startTime, @WebParam(name = "printNewNotice", targetNamespace = "http://tempuri.org/") Boolean printNewNotice, @WebParam(name = "rescheduleComment", targetNamespace = "http://tempuri.org/") String rescheduleComment, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent, @WebParam(name = "updatePerformingSite", targetNamespace = "http://tempuri.org/") Boolean updatePerformingSite) throws IRisRescheduleRescheduleErrorInfoFaultFaultMessage {
        if(bookingService != null){
            return bookingService.reschedule(examinationNr, newTimeId, startTime, printNewNotice, rescheduleComment, allowMultiExamination, allowUrgent, updatePerformingSite);
        }
        else{
            throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
        }
    }
}
