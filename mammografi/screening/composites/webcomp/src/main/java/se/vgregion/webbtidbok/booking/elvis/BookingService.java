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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import se.vgregion.webbtidbok.Places;
import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.sectra.BookingMapperSectra;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.domain.elvis.BookingElvis;
import se.vgregion.webbtidbok.lang.DateHandler;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.BookingPlace;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.BookingTime;
import se.vgregion.webbtidbok.ws.ObjectFactory;

public class BookingService implements BookingServiceInterface {

    int testIndex;
	BookingResponse response;
	BookingRequest request;
	WebServiceHelper helper;
	private BookingMapperElvis mapping;
	private BookingMapperSectra mappingToSectra;

	public void setMapping(BookingMapperElvis mapping) {
		this.mapping = mapping;
	}

	public void setHelper(WebServiceHelper helper) {
		this.helper = helper;
	}

	private final ObjectFactory objectFactory = new ObjectFactory();

	public boolean isFirstPlaces = true;
	private boolean isTimeListEmpty = true;
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

	public void setFirstPlacesBoolean(boolean b) {

		isFirstPlaces = b;
		// System.out.println("BookingServices.setFirstPlacesBoolean: " +
		// isFirstPlaces);
	}

	public boolean isFirstPlaces() {
		// System.out.println("BookingServices.isFirstBoolean: " +
		// isFirstPlaces);

		return isFirstPlaces;
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
			ArrayOfBookingPlace places = helper
					.getQueryWSRequestPlaces(request);
			List<BookingPlace> placeList = places.getBookingPlace();
			for (BookingPlace p : placeList) {
				Surgery bookingPlaceMapping = mapping.bookingPlaceMapping(p);
				surgeries.add(bookingPlaceMapping);
			}

		}
		return surgeries;
	}

	public List<se.vgregion.webbtidbok.domain.BookingTime> getBookingTime(
			State loginCredentials) {
		List<se.vgregion.webbtidbok.domain.BookingTime> bookingTimeArrayList = new ArrayList<se.vgregion.webbtidbok.domain.BookingTime>();
		boolean theInThePastFlag = loginCredentials.getSelectedDate().before(
				Calendar.getInstance());

		if (loginCredentials.isLoggedIn() && !theInThePastFlag) {
			Calendar selectedDate = loginCredentials.getSelectedDate();

			String fromDate = DateHandler.setCalendarDateFormat(selectedDate);
			JAXBElement<String> fromDat = objectFactory
					.createBookingRequestFromDat(fromDate);
			request = helper.getQueryWSRequest(loginCredentials);
			request.setCentralTidbokID(loginCredentials.getCentralTidbokID());
			request.setFromDat(fromDat);

			ArrayOfBookingTime times = helper.getQueryWSRequestTime(request);

			List<BookingTime> timeList;

			try {
				timeList = times.getBookingTime();
				if (timeList.isEmpty()) {
					isTimeListEmpty = true;
				} else {
					isTimeListEmpty = false;
				}

				int id = 1;
				for (BookingTime b : timeList) {

					Calendar dateCal = Calendar.getInstance();
					dateCal.set(Calendar.YEAR, b.getDatum().getYear());
					dateCal.set(Calendar.MONTH, b.getDatum().getMonth() - 1);
					dateCal.set(Calendar.DATE, b.getDatum().getDay());

					String day = DateHandler.setCalendarDateFormat(dateCal);

					BookingTime pl = new BookingTime();
					pl.setAntal(b.getAntal());
					pl.setDatum(b.getDatum());
					pl.setKlocka(b.getKlocka());

					bookingTimeArrayList.add(mappingToSectra
							.bookingTimeMapping(pl));

					id++;
				}
			} catch (Exception e) {
				isTimeListEmpty = true;
			}
		}
		return bookingTimeArrayList;
	}

	/****
	 * method setting ombokning
	 * 
	 * @param l
	 */
	public void setBookingTime(BookingTimeLocal bookingTime, State credentials) {
		String hour = bookingTime.getTime();
		String[] hourMinute = hour.split(":");

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(credentials.getSelectedDate().getTime().getTime());

		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourMinute[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(hourMinute[1]));
		cal.set(Calendar.SECOND, 0);

		// update booking
		if (credentials.isLoggedIn()) {

			request = helper.getQueryWSRequest(credentials);

			XMLGregorianCalendar xmlCal;
			try {

				xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(
						cal);
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
			ArrayOfBookingPlace places = helper
					.getQueryWSRequestPlaces(request);
			List<BookingPlace> placeList = places.getBookingPlace();
			for (BookingPlace p : placeList) {
				Surgery surgery = mapping.bookingPlaceMapping(p);
				surgeryList.add(surgery);
			}
		}
		return surgeryList;
	}

}
