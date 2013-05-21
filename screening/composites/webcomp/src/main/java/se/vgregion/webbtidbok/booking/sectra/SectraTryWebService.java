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
import se.vgregion.webbtidbok.ws.sectra.RISReschedule;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadPoolExecutor;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
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
 * @author frelun
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
			RISReschedule risr;
			if(this.wsURL != null && this.qName != null){
				risr = new RISReschedule(this.wsURL, this.qName);
			} else {
				risr = new RISReschedule();
			}
			this.bookingService = risr.getBasicHttpBindingIRisReschedule();
		} catch (Exception e){
			logger.error("could not connect to... " + this.serviceName, e);
			mailQueue.execute(new ErrorMail(errorMailProperties, this.serviceName, e));
		} 
	}

	@Override
	@WebMethod(operationName = "GetBookingInfo", action = "http://tempuri.org/IRisReschedule/GetBookingInfo")
	@WebResult(name = "GetBookingInfoResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "GetBookingInfo", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.sectra.GetBookingInfo")
	@ResponseWrapper(localName = "GetBookingInfoResponse", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.sectra.GetBookingInfoResponse")
	public BookingInfo getBookingInfo(
			@WebParam(name = "patientId", targetNamespace = "http://tempuri.org/") String patientId,
			@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr)
					throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {

		if(bookingService != null){
			return bookingService.getBookingInfo(patientId, examinationNr);
		}
		else{
			throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
		}
	}

	@Override
	@WebMethod(operationName = "GetBookings", action = "http://tempuri.org/IRisReschedule/GetBookings")
	@WebResult(name = "GetBookingsResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "GetBookings", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.sectra.GetBookings")
	@ResponseWrapper(localName = "GetBookingsResponse", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.sectra.GetBookingsResponse")
	public ArrayOfBookingInfo getBookings(
			@WebParam(name = "patientId", targetNamespace = "http://tempuri.org/") String patientId)
					throws IRisRescheduleGetBookingsErrorInfoFaultFaultMessage {

		if(bookingService != null){
			return bookingService.getBookings(patientId);
		}
		else{
			throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
		}
	}

	@Override
	@WebMethod(operationName = "ListSections", action = "http://tempuri.org/IRisReschedule/ListSections")
	@WebResult(name = "ListSectionsResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "ListSections", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.sectra.ListSections")
	@ResponseWrapper(localName = "ListSectionsResponse", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.sectra.ListSectionsResponse")
	public ArrayOfSection listSections(
			@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr)
					throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage {

		if(bookingService != null){
			return bookingService.listSections(examinationNr);
		}
		else{
			throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
		}
	}

	@Override
	@WebMethod(operationName = "ListFreeTimes", action = "http://tempuri.org/IRisReschedule/ListFreeTimes")
	@WebResult(name = "ListFreeTimesResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "ListFreeTimes", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.sectra.ListFreeTimes")
	@ResponseWrapper(localName = "ListFreeTimesResponse", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.sectra.ListFreeTimesResponse")
	public ArrayOfTimeBlock listFreeTimes(
			@WebParam(name = "startDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startDate,
			@WebParam(name = "endDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar endDate,
			@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr,
			@WebParam(name = "sectionId", targetNamespace = "http://tempuri.org/") String sectionId)
					throws IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage {

		if(bookingService != null){
			return bookingService.listFreeTimes(startDate, endDate, examinationNr, sectionId);
		}
		else{
			throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
		}
	}

	@Override
	@WebMethod(operationName = "ListFreeDays", action = "http://tempuri.org/IRisReschedule/ListFreeDays")
	@WebResult(name = "ListFreeDaysResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "ListFreeDays", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.sectra.ListFreeDays")
	@ResponseWrapper(localName = "ListFreeDaysResponse", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.sectra.ListFreeDaysResponse")
	public ArrayOfdateTime listFreeDays(
			@WebParam(name = "startDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startDate,
			@WebParam(name = "endDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar endDate,
			@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr,
			@WebParam(name = "sectionId", targetNamespace = "http://tempuri.org/") String sectionId)
					throws IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage {

		if(bookingService != null){
			return bookingService.listFreeDays(startDate, endDate, examinationNr, sectionId);
		}
		else{
			throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
		}
	}

	@Override
	@WebMethod(operationName = "Reschedule", action = "http://tempuri.org/IRisReschedule/Reschedule")
	@WebResult(name = "RescheduleResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "Reschedule", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.sectra.Reschedule")
	@ResponseWrapper(localName = "RescheduleResponse", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.sectra.RescheduleResponse")
	public BookingInfo reschedule(
			@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr,
			@WebParam(name = "newTimeId", targetNamespace = "http://tempuri.org/") String newTimeId,
			@WebParam(name = "startTime", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startTime,
			@WebParam(name = "printNewNotice", targetNamespace = "http://tempuri.org/") Boolean printNewNotice,
			@WebParam(name = "rescheduleComment", targetNamespace = "http://tempuri.org/") String rescheduleComment)
					throws IRisRescheduleRescheduleErrorInfoFaultFaultMessage {

		if(bookingService != null){
			return bookingService.reschedule(examinationNr, newTimeId, startTime, printNewNotice, rescheduleComment);
		}
		else{
			throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
		}
	}
}
