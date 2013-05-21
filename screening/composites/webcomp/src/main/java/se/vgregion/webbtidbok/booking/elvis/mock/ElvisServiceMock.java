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

package se.vgregion.webbtidbok.booking.elvis.mock;

import static se.vgregion.webbtidbok.lang.DateHandler.xmlCalendarFor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.ArrayOfCalendar;
import se.vgregion.webbtidbok.ws.BookingPlace;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.BookingTime;
import se.vgregion.webbtidbok.ws.Calendar;
import se.vgregion.webbtidbok.ws.ICentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWSCancelBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSConfirmBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetCalandarICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ObjectFactory;

public class ElvisServiceMock implements ICentralBookingWS {

	private Map<String, Object> elvisMockData;
	private ObjectFactory objectFactory = new ObjectFactory();

	public ElvisServiceMock() {
		try {
			createMockData();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public void createMockData() throws DatatypeConfigurationException {
		elvisMockData = new HashMap<String, Object>();
		// Create bookings.
		Map<String, BookingResponse> bookings = new HashMap<String, BookingResponse>();
		String pNR1 = "19700123-9297";
		String pNR2 = "20100312-2222";
		bookings.put(pNR1, createBookings(pNR1, "KALLE 1", "ADRESS 1", "MOTAGNING 1", xmlCalendarFor(2010, 8, 24, 11, 0, 0), 1));
		bookings.put(pNR2, createBookings(pNR2, "KALLE 2", "ADRESS 2", "MOTAGNING 2", xmlCalendarFor(2010, 8, 26, 12, 0, 0), 2));
		elvisMockData.put("bookings", bookings);

		// Create bookingPlaces.
		Map<String, ArrayOfBookingPlace> bookingPlaces = new HashMap<String, ArrayOfBookingPlace>();
		ArrayOfBookingPlace arrayOfBookingPlace = new ArrayOfBookingPlace();
		Integer centralTidbokID1 = 1;
		Integer centralTidbokID2 = 2;
		arrayOfBookingPlace.getBookingPlace().add(createBookingPlaces("Address 1", "Mottagning 1", centralTidbokID1));
		arrayOfBookingPlace.getBookingPlace().add(createBookingPlaces("Address 2", "Mottagning 2", centralTidbokID2));
		bookingPlaces.put(pNR1, arrayOfBookingPlace);
		bookingPlaces.put(pNR2, arrayOfBookingPlace);
		elvisMockData.put("bookingPlaces", bookingPlaces);

		// Create bookingTimes
		Map<Integer, ArrayOfBookingTime> bookingTimes = new HashMap<Integer, ArrayOfBookingTime>();
		ArrayOfBookingTime arrayOfBookingTime = new ArrayOfBookingTime();
		arrayOfBookingTime.getBookingTime().add(createBookingTime(xmlCalendarFor(2010, 8, 27, 7, 0, 0), "07:00"));
		arrayOfBookingTime.getBookingTime().add(createBookingTime(xmlCalendarFor(2010, 8, 30, 12, 0, 0), "12:00"));
		arrayOfBookingTime.getBookingTime().add(createBookingTime(xmlCalendarFor(2010, 8, 31, 16, 0, 0), "16:00"));
		arrayOfBookingTime.getBookingTime().add(createBookingTime(xmlCalendarFor(2010, 8, 31, 16, 30, 0), "16:30"));
		arrayOfBookingTime.getBookingTime().add(createBookingTime(xmlCalendarFor(2010, 9, 1, 17, 0, 0), "17:00"));
		arrayOfBookingTime.getBookingTime().add(createBookingTime(xmlCalendarFor(2010, 9, 1, 18, 0, 0), "18:00"));
		arrayOfBookingTime.getBookingTime().add(createBookingTime(xmlCalendarFor(2010, 9, 1, 19, 0, 0), "19:00"));
		// Add bookingTimes for bookingPlaces.
		bookingTimes.put(centralTidbokID1, arrayOfBookingTime);
		bookingTimes.put(centralTidbokID2, arrayOfBookingTime);
		elvisMockData.put("bookingTimes", bookingTimes);

		// Create Calendars
		Map<Integer, ArrayOfCalendar> calendars = new HashMap<Integer, ArrayOfCalendar>();
		ArrayOfCalendar arrayOfCalendar = new ArrayOfCalendar();
		arrayOfCalendar.getCalendar().add(createCalendar(xmlCalendarFor(2010, 8, 27, 7, 0, 0)));
		arrayOfCalendar.getCalendar().add(createCalendar(xmlCalendarFor(2010, 8, 30, 12, 0, 0)));
		arrayOfCalendar.getCalendar().add(createCalendar(xmlCalendarFor(2010, 8, 31, 16, 0, 0)));
		arrayOfCalendar.getCalendar().add(createCalendar(xmlCalendarFor(2010, 8, 31, 16, 30, 0)));
		arrayOfCalendar.getCalendar().add(createCalendar(xmlCalendarFor(2010, 9, 1, 17, 0, 0)));
		arrayOfCalendar.getCalendar().add(createCalendar(xmlCalendarFor(2010, 9, 1, 18, 0, 0)));
		arrayOfCalendar.getCalendar().add(createCalendar(xmlCalendarFor(2010, 9, 1, 19, 0, 0)));
		calendars.put(centralTidbokID1, arrayOfCalendar);
		elvisMockData.put("calendars", calendars);

	}

	private BookingResponse createBookings(String PNR, String NAME, String ADDRESS, String MOTTAGNING,
			XMLGregorianCalendar xmlGregorianCalendar, int centralTidbokID) {
		BookingResponse bookingResponse = new BookingResponse();
		bookingResponse.setPnr(objectFactory.createString(PNR));
		bookingResponse.setNamn(objectFactory.createString(NAME));
		bookingResponse.setAddress(objectFactory.createString(ADDRESS));
		bookingResponse.setMottagning(objectFactory.createString(MOTTAGNING));
		bookingResponse.setAntalOmbok(0);
		bookingResponse.setMaxAntalOmbok(4);
		bookingResponse.setCentralTidbokID(centralTidbokID);
		bookingResponse.setBokadTid(xmlGregorianCalendar);
		return bookingResponse;
	}

	private BookingTime createBookingTime(XMLGregorianCalendar date, String time) {
		BookingTime bookingTime = new BookingTime();
		bookingTime.setAntal(2);
		bookingTime.setDatum(date);
		bookingTime.setKlocka(objectFactory.createString(time));
		return bookingTime;
	}

	private BookingPlace createBookingPlaces(String address, String mottagning, int centralTidbokID) {
		BookingPlace bookingPlace = new BookingPlace();
		bookingPlace.setAddress(objectFactory.createString(address));
		bookingPlace.setCentralTidbokID(centralTidbokID);
		bookingPlace.setMottagning(objectFactory.createString(mottagning));
		return bookingPlace;
	}

	private Calendar createCalendar(XMLGregorianCalendar date) {
		Calendar calendar = new Calendar();
		calendar.setDatum(date);
		return calendar;
	}

	private BookingResponse getBookingResponseForBooking(String pnr) {
		Map<String, BookingResponse> bookings = (Map<String, BookingResponse>) elvisMockData.get("bookings");
		return bookings.get(pnr);
	}

	private ArrayOfBookingPlace getBookingPlaces(String pnr) {
		Map<String, ArrayOfBookingPlace> bookingPlaces = (Map<String, ArrayOfBookingPlace>) elvisMockData.get("bookingPlaces");
		return bookingPlaces.get(pnr);
	}

	private ArrayOfBookingTime getBookingTimes(BookingRequest request) throws DatatypeConfigurationException {
		String value = request.getFromDat().getValue().trim();
		// Tue Oct 19 14:25:28 CEST 2010 illegal incoming value format
		// 2000-03-04T20:00:00Z legal format for value
		XMLGregorianCalendar fromDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(value);
		Map<Integer, ArrayOfBookingTime> bookingTimes = (Map<Integer, ArrayOfBookingTime>) elvisMockData.get("bookingTimes");
		XMLGregorianCalendar compareDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		Integer ctid = request.getCentralTidbokID();
		ArrayOfBookingTime arrayOfBookingTime = bookingTimes.get(ctid);
		List<BookingTime> bookingTime = arrayOfBookingTime.getBookingTime();
		Iterator<BookingTime> iterator = bookingTime.iterator();
		ArrayOfBookingTime returnArr = new ArrayOfBookingTime();
		while (iterator.hasNext()) {
			BookingTime bt = iterator.next();
			compareDate.setDay(bt.getDatum().getDay());
			compareDate.setMonth(bt.getDatum().getMonth());
			compareDate.setYear(bt.getDatum().getYear());
			if (fromDate.equals(compareDate)) {
				returnArr.getBookingTime().add(bt);
			}
		}
		return returnArr;
	}

	private boolean cancelBooking(String pnr, String pin) {
		boolean value = false;
		if (!pin.isEmpty() && !pnr.isEmpty()) {
			value = true;
		}
		return value;
	}

	private ArrayOfCalendar getCalendars(Integer id) {
		Map<Integer, ArrayOfCalendar> calendars = (Map<Integer, ArrayOfCalendar>) elvisMockData.get("calendars");
		return calendars.get(id);
	}

	@Override
	public Boolean cancelBooking(BookingRequest request) throws ICentralBookingWSCancelBookingICFaultFaultFaultMessage {
		return cancelBooking(request.getPnr().getValue(), request.getPin().getValue());
	}

	@Override
	public BookingResponse confirmBooking(BookingRequest request) throws ICentralBookingWSConfirmBookingICFaultFaultFaultMessage {
		return null;
	}

	@Override
	public BookingResponse getBooking(BookingRequest request) throws ICentralBookingWSGetBookingICFaultFaultFaultMessage {
		return getBookingResponseForBooking(request.getPnr().getValue());
	}

	@Override
	public ArrayOfBookingPlace getBookingPlace(BookingRequest request)
			throws ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage {
		return getBookingPlaces(request.getPnr().getValue());
	}

	@Override
	public ArrayOfBookingTime getBookingTime(BookingRequest request)
			throws ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage {
		ArrayOfBookingTime bookingTimes = null;
		try {
			bookingTimes = getBookingTimes(request);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return bookingTimes;
	}

	@Override
	public ArrayOfCalendar getCalandar(BookingRequest request) throws ICentralBookingWSGetCalandarICFaultFaultFaultMessage {
		return getCalendars(Integer.valueOf(request.getCentralTidbokID()));
	}

}
