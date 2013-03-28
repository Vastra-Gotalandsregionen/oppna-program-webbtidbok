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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.crypto.StringEncrypter;
import se.vgregion.webbtidbok.mail.MailSender;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.ArrayOfCalendar;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.BookingTime;
import se.vgregion.webbtidbok.ws.ICFault;
import se.vgregion.webbtidbok.ws.ICentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWSCancelBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSConfirmBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetCalandarICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ObjectFactory;

/**
 * This class is used to map java classes to XML which are put into the WS SOAP requests. The methods below are better documented
 * in the "CentralBookingWS Användardokumentation -utkast" specification from Insieme Consulting AB, author Anna Engelin. Creates
 * JAXBelements and request {@link BookingRequest} thru {@link ObjectFactory}.
 * 
 * @author carstm
 * 
 */
public class WebServiceHelper {

	private final Log log = LogFactory.getLog(WebServiceHelper.class);
	private final ObjectFactory objectFactory = new ObjectFactory();
	private ICentralBookingWS elvisWebService;
	private StringEncrypter encrypter;
	private Map<String, Integer> errorMap;

	public void setElvisWebService(ICentralBookingWS elvisWebService) {
		this.elvisWebService = elvisWebService;
	}

	public void setEncrypter(StringEncrypter encrypter) {
		this.encrypter = encrypter;
	}

	// make web service call
	// CentralBookingWS centralBookingWS = new CentralBookingWS();
	// ICentralBookingWS ws = centralBookingWS.getBasicHttpBindingICentralBookingWS();

	public byte[] pinSigner(String passwd) {
		byte[] signedPasswd = encrypter.signString(passwd);
		return signedPasswd;
	}

	public String encoder(byte[] signedPasswd) {
		String encoded = encrypter.encodeToBase64(signedPasswd);
		return encoded;
	}

	/**
	 * Used to send whatever request, response depends on what ws-method was called
	 * 
	 * @param loginCredentials
	 *            {@link State}
	 * @return request {@link BookingRequest}
	 */

	public BookingRequest getQueryWSRequest(State loginCredentials) {

		JAXBElement<String> pnr = objectFactory.createBookingRequestPnr(loginCredentials.getPnr());
		JAXBElement<String> pin = objectFactory.createBookingRequestPin(loginCredentials.getPasswd());
		JAXBElement<String> key = objectFactory.createBookingRequestKey(loginCredentials.getPasswd());
		JAXBElement<String> cryptedKey = objectFactory.createBookingRequestCryptedKey(encoder(pinSigner(loginCredentials
				.getPasswd())));
		JAXBElement<String> cert = objectFactory.createBookingRequestCert("YES");

		// create request object
		BookingRequest request = objectFactory.createBookingRequest();

		// setup request object
		request.setPnr(pnr);
		request.setPin(pin);
		request.setKey(key);
		request.setCryptedKey(cryptedKey);
		request.setCert(cert);

		return request;

	}

	/**
	 * Used make a request for available times to book within a certain date interval and for a certain tidbok (time book).
	 * 
	 * @param loginCredentials
	 *            {@link State}
	 * @param centralTimeBookingId
	 *            - an int denoting the id of a certain tidbok (time book)
	 * @param fromDatString
	 *            - the date from which you're looking for available appointments
	 * @param toDatString
	 *            - the date to which you're looking for available appointments
	 * @return request {@link BookingRequest}
	 */
	public BookingRequest getQueryWSRequest(State loginCredentials, int centralTimeBookingId, String fromDatString,
			String toDatString) {

		JAXBElement<String> pnr = objectFactory.createBookingRequestPnr(loginCredentials.getPnr());
		JAXBElement<String> pin = objectFactory.createBookingRequestPin(loginCredentials.getPasswd());
		JAXBElement<String> key = objectFactory.createBookingRequestKey(loginCredentials.getPasswd());
		JAXBElement<String> cryptedKey = objectFactory.createBookingRequestCryptedKey(encoder(pinSigner(loginCredentials
				.getPasswd())));
		JAXBElement<String> cert = objectFactory.createBookingRequestCert("YES");
		JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat(fromDatString);
		JAXBElement<String> toDat = objectFactory.createBookingRequestToDat(toDatString);

		// create request object
		BookingRequest request = objectFactory.createBookingRequest();

		// setup request object
		request.setPnr(pnr);
		request.setPin(pin);
		request.setKey(key);
		request.setCryptedKey(cryptedKey);
		request.setCert(cert);
		// set values for getting calendars
		request.setFromDat(fromDat);
		request.setToDat(toDat);
		request.setCentralTidbokID(centralTimeBookingId);

		return request;

	}

	/**
	 * Used to retrieve a certain {@link BookingTime} by the help of a ready made request which is provided as method parameter.
	 * 
	 * @param request
	 *            {@link BookingRequest}
	 * @return {@link ArrayOfBookingTime}
	 */
	public ArrayOfBookingTime getQueryWSRequestBookingTime(BookingRequest request) {

		// CentralBookingWS centralBookingWS = new CentralBookingWS();
		// ICentralBookingWS ws = centralBookingWS.getBasicHttpBindingICentralBookingWS();

		try {

			return elvisWebService.getBookingTime(request);
			// return ws.getBookingTime(request);

		} catch (ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * Used to retrieve a certain {@link BookingPlace} by the help of a ready made request which is provided as method parameter.
	 * 
	 * @param request
	 *            {@link BookingRequest}
	 * @return {@link ArrayOfBookingPlace}
	 */
	public ArrayOfBookingPlace getQueryWSRequestPlaces(BookingRequest request) {

		// make web service call
		// CentralBookingWS centralBookingWS = new CentralBookingWS();
		// ICentralBookingWS ws = centralBookingWS.getBasicHttpBindingICentralBookingWS();

		try {
			return elvisWebService.getBookingPlace(request);
			// return ws.getBookingPlace(request);

		} catch (ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage ex) {
			log.error(ex.getMessage(), ex);
			return null;

		}

	}

	/**
	 * Retrieves the calendar using a ready made request {@link BookingRequest}
	 * 
	 * @param request
	 *            {@link BookingRequest}
	 * @return {@ArrayOfCalendar}
	 */
	public ArrayOfCalendar getQueryWSRequestCalendar(BookingRequest request) {

		// make web service call
		// CentralBookingWS centralBookingWS = new CentralBookingWS();
		// ICentralBookingWS ws = centralBookingWS.getBasicHttpBindingICentralBookingWS();

		try {

			return elvisWebService.getCalandar(request);
			// return ws.getCalandar(request);

		} catch (ICentralBookingWSGetCalandarICFaultFaultFaultMessage ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * Retrieves an {@link ArrayOfBookingTime} by providing a {@link BookingRequest} as a parameter.
	 * 
	 * @param request
	 *            {@link BookingRequest}
	 * @return {@link ArrayOfBookingTime}
	 */
	public ArrayOfBookingTime getQueryWSRequestTime(BookingRequest request) {

		// make web service call
		// CentralBookingWS centralBookingWS = new CentralBookingWS();
		// ICentralBookingWS ws = centralBookingWS.getBasicHttpBindingICentralBookingWS();

		try {

			// ArrayOfBookingTime arrays = ws.getBookingTime(request);
			// loginCredentials.setBookingResponse(response);
			ArrayOfBookingTime arrays = elvisWebService.getBookingTime(request);
			List<BookingTime> list = arrays.getBookingTime();
			for (BookingTime bt : list) {
				log.debug("BookingTime Month: " + bt.getDatum().getMonth());
			}

			return arrays;

		} catch (ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage ex) {
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);

			return null;

		}
	}

	/**
	 * This to get the place of the visit for Screening, ie: Example-hospital AB
	 * 
	 * @param request
	 *            {@link BookingRequest}
	 * @return bookingArr {@link ArrayOfBookingPlace}
	 */
	public ArrayOfBookingPlace getBookingPlaceFromWS(BookingRequest request) {
		ArrayOfBookingPlace bookingArr = null;
		try {

			bookingArr = elvisWebService.getBookingPlace(request);
			// bookingArr = ws.getBookingPlace(request);
		} catch (ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage e) {
			log.error(e.getMessage(), e);
			bookingArr = new ArrayOfBookingPlace();
		}
		return bookingArr;
	}

	/**
	 * Info concerning the booking, time, place, location, pnr, name etc If any error is sent back from the Elvis WebService this
	 * is, within the Catch() clause, sent as a mail to insieme
	 * 
	 * @param request
	 *            {@link BookingRequest}
	 * @return {@link BookingResponse}
	 */
	public BookingResponse getQueryWS(BookingRequest request) {
		try {
			return elvisWebService.getBooking(request);
		} catch (ICentralBookingWSGetBookingICFaultFaultFaultMessage ex) {
			log.error(ex.getMessage(), ex);
			ICFault faultInfo = ex.getFaultInfo();

			errorMap = new HashMap<String, Integer>();
			errorMap.put("general error", -1);
			errorMap.put("-1036", -1036);
			errorMap.put("-2001", -2001);
			errorMap.put("-2002", -2002);
			errorMap.put("faulty value", -10088);

			for (Entry<String, Integer> entry : errorMap.entrySet()) {
				if (faultInfo.getRetcode() == entry.getValue()) {
					errorMailer("Error code: " + faultInfo.getRetcode() + ". " + getStackTrace(ex));
				}
			}
			return null;
		}
	}

	public String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	/**
	 * Method used to initiate and send errors about elvis to insieme. State created here is ONLY used to retrieve some mail
	 * properties from the message bundle. The mail properties are such as SMTP URL.
	 * 
	 * @param insiemeWSErrorMessage
	 *            - the error message from the server
	 */
	private void errorMailer(String insiemeWSErrorMessage) {
		State tempMailState = new State();
		tempMailState.setMessageBundle("messages/bukaorta/BukAortaMessages");
		tempMailState.setService("BUKAORTA");
		MailSender mailer = new MailSender(tempMailState, null);
		mailer.sendElvisErrorMail(insiemeWSErrorMessage);
	}

	/***
	 * 
	 * Used to update a booking by providing a ready made {@link BookingRequest}
	 * 
	 * @parameter request {@link BookingRequest}
	 * @return {@link BookingResponse}
	 */
	public BookingResponse setBookingUpdate(BookingRequest request) {

		try {

			log.debug("---------XXXXXXXXXXXXXX-----------");

			log.debug(request.getBokadTid().getDay() + " " + request.getBokadTid().getMonth() + " "
					+ request.getBokadTid().getYear() + " " + request.getBokadTid().getHour() + request.getBokadTid().getMinute());
			log.debug(request.getCentralTidbokID());
			log.debug(request.getPin());
			log.debug(request.getPnr());
			// System.out.println(request.getFromDat().getValue());
			// System.out.println(request.getToDat().getValue());

			return elvisWebService.confirmBooking(request);
			// return ws.confirmBooking(request);
			// loginCredentials.setBookingResponse(response);

		} catch (ICentralBookingWSConfirmBookingICFaultFaultFaultMessage ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * Used to cancel a booking by sending a ready made {@link BookingRequest}. Returns true or false depending on how the
	 * cancellation went.
	 * 
	 * @param request
	 *            {@link BookingRequest}
	 * @return {@link Boolean}
	 */
	public boolean getQueryWSCancelBooking(BookingRequest request) {

		try {

			// return ws.getBooking(request);
			// loginCredentials.setBookingResponse(response);
			return elvisWebService.cancelBooking(request);
			// return ws.cancelBooking(request);

		} catch (ICentralBookingWSCancelBookingICFaultFaultFaultMessage ex) {
			log.error(ex.getMessage(), ex);
			return false;
		}
	}

}
