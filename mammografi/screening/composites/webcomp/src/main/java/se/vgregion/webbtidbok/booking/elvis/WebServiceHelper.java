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

import java.util.List;

import javax.xml.bind.JAXBElement;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.crypto.StringEncrypter;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.ArrayOfCalendar;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.BookingTime;
import se.vgregion.webbtidbok.ws.CentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWSCancelBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSConfirmBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetCalandarICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ObjectFactory;

public class WebServiceHelper {

	private final ObjectFactory objectFactory = new ObjectFactory();
	private StringEncrypter encrypter;

	public void setEncrypter(StringEncrypter encrypter) {
		this.encrypter = encrypter;
	}

	// make web service call
	CentralBookingWS centralBookingWS = new CentralBookingWS();
	ICentralBookingWS ws = centralBookingWS
			.getBasicHttpBindingICentralBookingWS();

	public byte[] pinSigner(String passwd) {
		byte[] signedPasswd = encrypter.signString(passwd);
		return signedPasswd;
	}

	public String encoder(byte[] signedPasswd) {
		String encoded = encrypter.encodeToBase64(signedPasswd);
		return encoded;
	}

	// used to send whatever request, response depends on what ws-method was
	// called
	public BookingRequest getQueryWSRequest(State loginCredentials) {

		JAXBElement<String> pnr = objectFactory
				.createBookingRequestPnr(loginCredentials.getPnr());
		JAXBElement<String> pin = objectFactory
				.createBookingRequestPin(loginCredentials.getPasswd());
		JAXBElement<String> key = objectFactory
				.createBookingRequestKey(loginCredentials.getPasswd());
		JAXBElement<String> cryptedKey = objectFactory
				.createBookingRequestCryptedKey(encoder(pinSigner(loginCredentials
						.getPasswd())));
		JAXBElement<String> cert = objectFactory
				.createBookingRequestCert("YES");

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

	public BookingRequest getQueryWSRequest(State loginCredentials,
			int centralTimeBookingId, String fromDatString, String toDatString) {

		JAXBElement<String> pnr = objectFactory
				.createBookingRequestPnr(loginCredentials.getPnr());
		JAXBElement<String> pin = objectFactory
				.createBookingRequestPin(loginCredentials.getPasswd());
		JAXBElement<String> key = objectFactory
				.createBookingRequestKey(loginCredentials.getPasswd());
		JAXBElement<String> cryptedKey = objectFactory
				.createBookingRequestCryptedKey(encoder(pinSigner(loginCredentials
						.getPasswd())));
		JAXBElement<String> cert = objectFactory
				.createBookingRequestCert("YES");
		JAXBElement<String> fromDat = objectFactory
				.createBookingRequestFromDat(fromDatString);
		JAXBElement<String> toDat = objectFactory
				.createBookingRequestToDat(toDatString);

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

	public ArrayOfBookingTime getQueryWSRequestBookingTime(
			BookingRequest request) {

		CentralBookingWS centralBookingWS = new CentralBookingWS();
		ICentralBookingWS ws = centralBookingWS
				.getBasicHttpBindingICentralBookingWS();

		try {

			return ws.getBookingTime(request);

		} catch (ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public ArrayOfBookingPlace getQueryWSRequestPlaces(BookingRequest request) {

		// make web service call
		CentralBookingWS centralBookingWS = new CentralBookingWS();
		ICentralBookingWS ws = centralBookingWS
				.getBasicHttpBindingICentralBookingWS();

		try {

			return ws.getBookingPlace(request);

		} catch (ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			return null;

		}

	}

	public ArrayOfCalendar getQueryWSRequestCalendar(BookingRequest request) {

		// make web service call
		CentralBookingWS centralBookingWS = new CentralBookingWS();
		ICentralBookingWS ws = centralBookingWS
				.getBasicHttpBindingICentralBookingWS();

		try {

			return ws.getCalandar(request);

		} catch (ICentralBookingWSGetCalandarICFaultFaultFaultMessage ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());

			return null;

		}
	}

	public ArrayOfBookingTime getQueryWSRequestTime(BookingRequest request) {

		// make web service call
		CentralBookingWS centralBookingWS = new CentralBookingWS();
		ICentralBookingWS ws = centralBookingWS
				.getBasicHttpBindingICentralBookingWS();

		try {

			ArrayOfBookingTime arrays = ws.getBookingTime(request);
			// loginCredentials.setBookingResponse(response);
			List<BookingTime> list = arrays.getBookingTime();
			for (BookingTime bt : list) {

				System.out.println("BookingTime Month: "
						+ bt.getDatum().getMonth());

			}

			return arrays;

		} catch (ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());

			return null;

		}
	}

	// This to get the place of the visit for Screening, ie: Example-hospital AB
	public ArrayOfBookingPlace getBookingPlaceFromWS(BookingRequest request) {
		ArrayOfBookingPlace bookingArr = null;
		try {
			bookingArr = ws.getBookingPlace(request);
		} catch (ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage e) {
			e.printStackTrace();
			bookingArr = new ArrayOfBookingPlace();
		}
		return bookingArr;
	}

	// Info concerning the booking, time, place, location, pnr, name etc
	public BookingResponse getQueryWS(BookingRequest request) {

		try {

			return ws.getBooking(request);
			// loginCredentials.setBookingResponse(response);

		} catch (ICentralBookingWSGetBookingICFaultFaultFaultMessage ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			return null;

		}
	}

	// Info concerning the booking, time, place, location, pnr, name etc
	/***
	 * method update booking
	 * 
	 */
	public BookingResponse setBookingUpdate(BookingRequest request) {

		try {
			System.out.println("---------XXXXXXXXXXXXXX-----------");

			System.out.println(request.getBokadTid().getDay() + " "
					+ request.getBokadTid().getMonth() + " "
					+ request.getBokadTid().getYear() + " "
					+ request.getBokadTid().getHour()
					+ request.getBokadTid().getMinute());
			System.out.println(request.getCentralTidbokID());
			System.out.println(request.getPin());
			System.out.println(request.getPnr());
			// System.out.println(request.getFromDat().getValue());
			// System.out.println(request.getToDat().getValue());

			return ws.confirmBooking(request);
			// loginCredentials.setBookingResponse(response);

		} catch (ICentralBookingWSConfirmBookingICFaultFaultFaultMessage ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			return null;

		}
	}

	/*
	 * method cancel booking
	 * 
	 * @input parameter request
	 */
	public boolean getQueryWSCancelBooking(BookingRequest request) {

		try {

			// return ws.getBooking(request);
			// loginCredentials.setBookingResponse(response);
			return ws.cancelBooking(request);

		} catch (ICentralBookingWSCancelBookingICFaultFaultFaultMessage ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			return false;

		}
	}

}
