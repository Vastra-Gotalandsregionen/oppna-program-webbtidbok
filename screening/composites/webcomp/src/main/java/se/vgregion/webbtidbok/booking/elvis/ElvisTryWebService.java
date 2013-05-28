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

package se.vgregion.webbtidbok.booking.elvis;

import java.util.ResourceBundle;
import java.util.concurrent.ThreadPoolExecutor;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.apache.log4j.Logger;

import se.vgregion.webbtidbok.errorhandling.UnableToConnectToWSException;
import se.vgregion.webbtidbok.mail.ErrorMail;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.ArrayOfCalendar;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.ICentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWSCancelBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSConfirmBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetCalandarICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.CentralBookingWS;

/**
 * Used as a layer above the Elvis WebService connection in order to keep the application from freezing 
 * on startup. If any errors occur while connecting to the WS an email is sent to a service address and 
 * the service is never instantiated.
 * 
 *   TODO: tryConnection should be running repeatedly in a thread in order to regain
 *   connection when WS is back online.
 * 
 *
 */
public class ElvisTryWebService implements ICentralBookingWS {
	private ThreadPoolExecutor mailQueue; 
	private ResourceBundle errorMailProperties;
	private String serviceName;
	private ICentralBookingWS bookingService;
	private Logger logger;
	
	public ElvisTryWebService(ThreadPoolExecutor mailQueue, String errorMailProperties, String serviceName){
		this.logger = Logger.getLogger("ElvisTryWebService.class");
		this.mailQueue = mailQueue;
		this.errorMailProperties = ResourceBundle.getBundle(errorMailProperties);
		this.serviceName = serviceName;
		
		tryConnection();
	}
	
	public void tryConnection(){
		try{
			this.bookingService = new CentralBookingWS().getBasicHttpBindingICentralBookingWS();
		} catch(Exception e){
			logger.error("could not connect to... " + this.serviceName, e);
			mailQueue.execute(new ErrorMail(this.errorMailProperties, this.serviceName, e));
		}
	}
	
	@Override
	@WebMethod(operationName = "GetBooking", action = "http://tempuri.org/ICentralBookingWS/GetBooking")
	@WebResult(name = "GetBookingResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "GetBooking", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.GetBooking")
	@ResponseWrapper(localName = "GetBookingResponse", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.GetBookingResponse")
	public BookingResponse getBooking(
			@WebParam(name = "request", targetNamespace = "http://tempuri.org/") BookingRequest request)
			throws ICentralBookingWSGetBookingICFaultFaultFaultMessage {
		if(this.bookingService != null){
			return bookingService.getBooking(request);
		}
		else{
			throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
		}
	}

	@Override
	@WebMethod(operationName = "GetBookingPlace", action = "http://tempuri.org/ICentralBookingWS/GetBookingPlace")
	@WebResult(name = "GetBookingPlaceResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "GetBookingPlace", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.GetBookingPlace")
	@ResponseWrapper(localName = "GetBookingPlaceResponse", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.GetBookingPlaceResponse")
	public ArrayOfBookingPlace getBookingPlace(
			@WebParam(name = "request", targetNamespace = "http://tempuri.org/") BookingRequest request)
			throws ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage {
		if(this.bookingService != null){
			return bookingService.getBookingPlace(request);
		}
		else{
			throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
		}
	}

	@Override
	@WebMethod(operationName = "GetCalandar", action = "http://tempuri.org/ICentralBookingWS/GetCalandar")
	@WebResult(name = "GetCalandarResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "GetCalandar", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.GetCalandar")
	@ResponseWrapper(localName = "GetCalandarResponse", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.GetCalandarResponse")
	public ArrayOfCalendar getCalandar(
			@WebParam(name = "request", targetNamespace = "http://tempuri.org/") BookingRequest request)
			throws ICentralBookingWSGetCalandarICFaultFaultFaultMessage {
		if(this.bookingService != null){
			return bookingService.getCalandar(request);
		}
		else{
			throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
		}
	}

	@Override
	@WebMethod(operationName = "GetBookingTime", action = "http://tempuri.org/ICentralBookingWS/GetBookingTime")
	@WebResult(name = "GetBookingTimeResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "GetBookingTime", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.GetBookingTime")
	@ResponseWrapper(localName = "GetBookingTimeResponse", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.GetBookingTimeResponse")
	public ArrayOfBookingTime getBookingTime(
			@WebParam(name = "request", targetNamespace = "http://tempuri.org/") BookingRequest request)
			throws ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage {
		if(this.bookingService != null){
			return bookingService.getBookingTime(request);
		}
		else{
			throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
		}
	}

	@Override
	@WebMethod(operationName = "CancelBooking", action = "http://tempuri.org/ICentralBookingWS/CancelBooking")
	@WebResult(name = "CancelBookingResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "CancelBooking", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.CancelBooking")
	@ResponseWrapper(localName = "CancelBookingResponse", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.CancelBookingResponse")
	public Boolean cancelBooking(
			@WebParam(name = "request", targetNamespace = "http://tempuri.org/") BookingRequest request)
			throws ICentralBookingWSCancelBookingICFaultFaultFaultMessage {
		if(this.bookingService != null){
			return bookingService.cancelBooking(request);
		}
		else{
			throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
		}
	}

	@Override
	@WebMethod(operationName = "ConfirmBooking", action = "http://tempuri.org/ICentralBookingWS/ConfirmBooking")
	@WebResult(name = "ConfirmBookingResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "ConfirmBooking", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.ConfirmBooking")
	@ResponseWrapper(localName = "ConfirmBookingResponse", targetNamespace = "http://tempuri.org/", className = "se.vgregion.webbtidbok.ws.ConfirmBookingResponse")
	public BookingResponse confirmBooking(
			@WebParam(name = "request", targetNamespace = "http://tempuri.org/") BookingRequest request)
			throws ICentralBookingWSConfirmBookingICFaultFaultFaultMessage {
		if(this.bookingService != null){
			return bookingService.confirmBooking(request);
		}
		else{
			throw new UnableToConnectToWSException("Could not connect to WebService: " + this.serviceName);
		}
	}

}
