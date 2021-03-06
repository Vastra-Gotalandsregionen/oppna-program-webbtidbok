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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.domain.elvis.BookingElvis;
import se.vgregion.webbtidbok.lang.DateHandler;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.ArrayOfCalendar;
import se.vgregion.webbtidbok.ws.BookingPlace;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.BookingTime;
import se.vgregion.webbtidbok.ws.ObjectFactory;

public class BookingService implements BookingServiceInterface {

	BookingResponse response;
	BookingRequest request;
	WebServiceHelper helper;
	private BookingMapperElvis mapping;

	// private BookingMapperSectra mappingToSectra;

	public void setMapping(BookingMapperElvis mapping) {
		this.mapping = mapping;
	}

	public void setHelper(WebServiceHelper helper) {
		this.helper = helper;
	}

	private final ObjectFactory objectFactory = new ObjectFactory();

	public boolean isUpdated = false;

	// private String orderDate;

	// constructor
	public BookingService() {
	}

	public void setIsUpdated(boolean b) {
		isUpdated = true;
	}

	public boolean isUpdated() {
		return isUpdated;
	}

	public Booking getBooking(State loginCredentials) {

		if (loginCredentials.isLoggedIn()) {

			request = helper.getQueryWSRequest(loginCredentials);
			response = helper.getQueryWS(request);
			Booking bookingMapping = mapping.bookingMapping(response);
			loginCredentials.setCentralTidbokID(response.getCentralTidbokID());

			return bookingMapping;
		}
		return new BookingElvis();
	}

	/**
	 * Method cancelBooking returns true if deleted
	 * 
	 * @param loginCredentials
	 * @return
	 */
	public boolean cancelBooking(State loginCredentials) {

		request = helper.getQueryWSRequest(loginCredentials);
		boolean cancelledBooking = helper.getQueryWSCancelBooking(request);

		return cancelledBooking;
		// return true;
	}

	public List<Surgery> getBookingPlace(State loginCredentials) {

		List<Surgery> surgeries = new ArrayList<Surgery>();

		if (loginCredentials.isLoggedIn()) {

			request = helper.getQueryWSRequest(loginCredentials);
			ArrayOfBookingPlace places = helper.getQueryWSRequestPlaces(request);
			List<BookingPlace> placeList = places.getBookingPlace();
			for (BookingPlace p : placeList) {
				Surgery bookingPlaceMapping = mapping.bookingPlaceMapping(p);
				surgeries.add(bookingPlaceMapping);
			}

		}
		return surgeries;
	}

	public List<se.vgregion.webbtidbok.domain.BookingTime> getBookingTime(State loginCredentials, String sectionId,
			Calendar selectedDate) {
		List<se.vgregion.webbtidbok.domain.BookingTime> bookingTimeArrayList = new ArrayList<se.vgregion.webbtidbok.domain.BookingTime>();
		boolean theInThePastFlag = selectedDate.before(Calendar.getInstance());

		if (loginCredentials.isLoggedIn() && !theInThePastFlag) {
			String fromDate = DateHandler.setCalendarDateFormat(selectedDate);
			JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat(fromDate);
			request = helper.getQueryWSRequest(loginCredentials);
			request.setCentralTidbokID(Integer.parseInt(sectionId));
			request.setFromDat(fromDat);

			ArrayOfBookingTime times = helper.getQueryWSRequestTime(request);

			List<BookingTime> timeList;

			try {
				timeList = times.getBookingTime();

				int id = 1;
				for (BookingTime b : timeList) {

					Calendar dateCal = Calendar.getInstance();
					dateCal.set(Calendar.YEAR, b.getDatum().getYear());
					dateCal.set(Calendar.MONTH, b.getDatum().getMonth() - 1);
					dateCal.set(Calendar.DATE, b.getDatum().getDay());

					BookingTime pl = new BookingTime();
					pl.setAntal(b.getAntal());
					pl.setDatum(b.getDatum());
					pl.setKlocka(b.getKlocka());
					se.vgregion.webbtidbok.domain.BookingTime bookingTimeMapping = mapping.bookingTimeMapping(pl);
					bookingTimeArrayList.add(bookingTimeMapping);

					id++;
				}
			} catch (Exception e) {
			}
		}
		return bookingTimeArrayList;
	}

	/****
	 * method setting ombokning
	 * 
	 * @param l
	 */
	@Override
	public void reschedule(se.vgregion.webbtidbok.domain.BookingTime bookingTime, State credentials) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(bookingTime.getDateTime());

		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		// update booking
		if (credentials.isLoggedIn()) {

			request = helper.getQueryWSRequest(credentials);

			XMLGregorianCalendar xmlCal;
			try {

				xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
				request.setBokadTid(xmlCal);
			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setCentralTidbokID(credentials.getCentralTidbokID());

			BookingResponse response = helper.setBookingUpdate(request);

			if (response == null) {
				this.setIsUpdated(false);
			} else {
				this.setIsUpdated(true);
			}

		}

	}

	@Override
	public List<Surgery> getSurgeries(State state) {
		List<Surgery> surgeryList = new ArrayList<Surgery>();
		if (state.isLoggedIn()) {
			request = helper.getQueryWSRequest(state);
			ArrayOfBookingPlace places = helper.getQueryWSRequestPlaces(request);
			List<BookingPlace> placeList = places.getBookingPlace();
			for (BookingPlace p : placeList) {
				Surgery surgery = mapping.bookingPlaceMapping(p);
				surgeryList.add(surgery);
			}
		}
		return surgeryList;
	}

	@Override
	public List<Calendar> getFreeDays(State state, int tidbokID, Calendar startDate, Calendar endDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String from = format.format(startDate.getTime());
		JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat(from);
		String to = format.format(endDate.getTime());
		JAXBElement<String> toDat = objectFactory.createBookingRequestToDat(to);

		BookingRequest request = helper.getQueryWSRequest(state);
		request.setCentralTidbokID(tidbokID);
		request.setFromDat(fromDat);
		request.setToDat(toDat);

		ArrayOfCalendar cals = helper.getQueryWSRequestCalendar(request);

		List<Calendar> calendars = new ArrayList<Calendar>();
		if (cals != null) {
			for (se.vgregion.webbtidbok.ws.Calendar wrapCal : cals.getCalendar()) {
				Calendar calendar = mapping.daysMapping(wrapCal);
				calendars.add(calendar);
			}
		}
		return calendars;
	}
}
