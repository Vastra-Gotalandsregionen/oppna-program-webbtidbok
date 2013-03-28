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
/**
 * 
 */
package se.vgregion.webbtidbok.tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.elvis.WebServiceHelper;
import se.vgregion.webbtidbok.lang.DateHandler;
import se.vgregion.webbtidbok.ws.ArrayOfCalendar;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.ObjectFactory;

/**
 * @author conpem
 * 
 */
public class CalendarTest {

	WebServiceHelper ws;
	private final ObjectFactory objectFactory = new ObjectFactory();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ws = new WebServiceHelper();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Ignore
	@Test
	public void testCalendar() {
		State credentials = new State();
		credentials.setPasswd("NC1dqBZa");
		credentials.setPnr("19030311-9804");
		// NC1dqBZa 19030311-9804
		JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat("2010-03-31");
		JAXBElement<String> toDat = objectFactory.createBookingRequestToDat("2010-04-30");

		// BookingRequest request = ws.getQueryWSRequest(credentials);
		BookingRequest request = ws.getQueryWSRequest(credentials, 1, "2010-03-31", "2010-04-30");

		System.out.println("request from dat " + request.getFromDat());
		System.out.println("request to dat " + request.getToDat());

		// JAXBElement<String> fromDat = request.getFromDat();
		// JAXBElement<String> toDat = request.getToDat();

		if (fromDat == null) {
			System.out.println("fromdat is null");
			// fromDat = request.
		} else {
			System.out.println(fromDat.getValue());
			// fromDat.setValue("2010-03-31");
		}

		if (toDat == null) {
			System.out.println("todat is null");
		} else {
			System.out.println(toDat.getValue());
			// toDat.setValue("2010-05-31");
		}

		// set values for getting calendars
		// request.setFromDat(fromDat);
		// request.setToDat(toDat);
		// request.setCentralTidbokID(1);

		ArrayOfCalendar calendars = ws.getQueryWSRequestCalendar(request);
		if (calendars == null) {
			System.out.println("calendars = null");
			if (calendars.equals(null)) {
				Assert.assertFalse(true);
			}
		}
		List<se.vgregion.webbtidbok.ws.Calendar> calendarList = calendars.getCalendar();
		if (calendarList == null) {
			Assert.assertFalse(true);

		} else {

			if (calendarList.isEmpty()) {
				Assert.assertFalse(true);
			} else {

				for (se.vgregion.webbtidbok.ws.Calendar c : calendarList) {
					// System.out.println(bp.getAddress().getValue());
					// System.out.println(bp.getCentralTidbokID());
					// System.out.println(bp.getMottagning().getValue());
					System.out.println("Datum: " + c.getDatum().toString());
					System.out.println("Status: " + c.getStatus().getValue());
					System.out.println("Spärrad: " + c.isSparr());
					// System.out.println("Klocka: " + bt.);

				}

			}

			Assert.assertTrue(true);
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Ignore
	@Test
	public void testCalendarEmptyFromToDate() {
		State credentials = new State();
		credentials.setPasswd("fje5rnXG");
		credentials.setPnr("19910104-2399");

		JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat("2010-07-01");
		JAXBElement<String> toDat = objectFactory.createBookingRequestToDat("2010-07-31");

		// BookingRequest request = ws.getQueryWSRequest(credentials);
		BookingRequest request = ws.getQueryWSRequest(credentials, 1, "", "");
		ArrayOfCalendar calArr = ws.getQueryWSRequestCalendar(request);
		List<se.vgregion.webbtidbok.ws.Calendar> calList = new ArrayList<se.vgregion.webbtidbok.ws.Calendar>();
		try {
			calList = calArr.getCalendar();

			System.out.println("CalList.size: " + calList.size());
			System.out.println(calArr.getCalendar().size());

			for (se.vgregion.webbtidbok.ws.Calendar c : calList) {
				// System.out.println("DDDD Datum: " + c.getDatum().getDay());
				// //System.out.println(".toString: " + c.getDatum().toString());

				java.util.Calendar tCal = Calendar.getInstance();
				tCal.set(Calendar.YEAR, c.getDatum().getYear());
				tCal.set(Calendar.MONTH, c.getDatum().getMonth());
				tCal.set(Calendar.DATE, c.getDatum().getDay());
				System.out.println(DateHandler.setCalendarDateFormat(tCal));

			}

			if (calList.isEmpty()) {
				// this.setEmptyCalendar(true);
			} else {
				// this.setEmptyCalendar(false);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			// this.setEmptyCalendar(true);
		}
		List<Calendar> returnCal = new ArrayList<Calendar>();
		Set<Calendar> dateSet = new HashSet<Calendar>();
		for (se.vgregion.webbtidbok.ws.Calendar c : calList) {
			Calendar tCal = Calendar.getInstance();
			tCal.set(Calendar.YEAR, c.getDatum().getYear());
			tCal.set(Calendar.MONTH, c.getDatum().getMonth());
			tCal.set(Calendar.DATE, c.getDatum().getDay());
			dateSet.add(tCal);
			returnCal.add(tCal);
			System.out.println("CalendarUtil.getAvailableDates.ListCalendar: " + tCal.get(Calendar.DAY_OF_MONTH));
		}

		for (Calendar c : returnCal) {
			System.out.println("returnCal.toString: " + c.get(Calendar.DAY_OF_MONTH));
		}
		System.out.println("ret.size() - amount of bookable dates within fromDat & toDat: " + returnCal.size());

		System.out.println("request from dat " + request.getFromDat());
		// aSystem.out.println("request to dat " + request.getBokadTid().getDay());

		// JAXBElement<String> fromDat = request.getFromDat();
		// JAXBElement<String> toDat = request.getToDat();

		if (fromDat == null) {
			System.out.println("fromdat is null");
			// fromDat = request.
		} else {
			System.out.println(fromDat.getValue());
			// fromDat.setValue("2010-03-31");
		}

		if (toDat == null) {
			System.out.println("todat is null");
		} else {
			System.out.println(toDat.getValue());
			// toDat.setValue("2010-05-31");
		}

		// set values for getting calendars
		// request.setFromDat(fromDat);
		// request.setToDat(toDat);
		// request.setCentralTidbokID(1);

		ArrayOfCalendar calendars = ws.getQueryWSRequestCalendar(request);
		if (calendars == null) {
			Assert.assertTrue(true);
			return;

		}
		List<se.vgregion.webbtidbok.ws.Calendar> calendarList = calendars.getCalendar();
		if (calendarList == null) {
			Assert.assertTrue(true);

		} else {

			if (calendarList.isEmpty()) {
				Assert.assertTrue(true);
			} else {

				for (se.vgregion.webbtidbok.ws.Calendar c : calendarList) {
					// System.out.println(bp.getAddress().getValue());
					// System.out.println(bp.getCentralTidbokID());
					// System.out.println(bp.getMottagning().getValue());
					System.out.println("Datum: " + c.getDatum().toString());
					System.out.println("Status: " + c.getStatus().getValue());
					System.out.println("Spärrad: " + c.isSparr());
					// System.out.println("Klocka: " + bt.);

				}

			}

			Assert.assertFalse(true);
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Ignore
	@Test
	public void testCalendarStrangeFromToDate() {
		State credentials = new State();
		credentials.setPasswd("Y8PBZRUr");
		credentials.setPnr("19960103-2395");

		JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat("10-03-31");
		JAXBElement<String> toDat = objectFactory.createBookingRequestToDat("10-05-31");

		// BookingRequest request = ws.getQueryWSRequest(credentials);
		BookingRequest request = ws.getQueryWSRequest(credentials, 1, "", "");

		System.out.println("request from dat " + request.getFromDat());
		System.out.println("request to dat " + request.getToDat());

		// JAXBElement<String> fromDat = request.getFromDat();
		// JAXBElement<String> toDat = request.getToDat();

		if (fromDat == null) {
			System.out.println("fromdat is null");
			// fromDat = request.
		} else {
			System.out.println(fromDat.getValue());
			// fromDat.setValue("2010-03-31");
		}

		if (toDat == null) {
			System.out.println("todat is null");
		} else {
			System.out.println(toDat.getValue());
			// toDat.setValue("2010-05-31");
		}

		// set values for getting calendars
		// request.setFromDat(fromDat);
		// request.setToDat(toDat);
		// request.setCentralTidbokID(1);

		ArrayOfCalendar calendars = ws.getQueryWSRequestCalendar(request);
		if (calendars == null) {
			Assert.assertTrue(true);
			return;

		}
		List<se.vgregion.webbtidbok.ws.Calendar> calendarList = calendars.getCalendar();
		if (calendarList == null) {
			Assert.assertTrue(true);

		} else {

			if (calendarList.isEmpty()) {
				Assert.assertTrue(true);
			} else {

				for (se.vgregion.webbtidbok.ws.Calendar c : calendarList) {
					// System.out.println(bp.getAddress().getValue());
					// System.out.println(bp.getCentralTidbokID());
					// System.out.println(bp.getMottagning().getValue());
					System.out.println("Datum: " + c.getDatum().toString());
					System.out.println("Status: " + c.getStatus().getValue());
					System.out.println("Spärrad: " + c.isSparr());
					// System.out.println("Klocka: " + bt.);

				}

			}

			Assert.assertFalse(true);
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Ignore
	@Test
	public void testCalendarCentralTimeBookingId() {
		State credentials = new State();
		credentials.setPasswd("Y8PBZRUr");
		credentials.setPnr("19960103-2395");

		JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat("2010-03-31");
		JAXBElement<String> toDat = objectFactory.createBookingRequestToDat("2010-05-31");

		// BookingRequest request = ws.getQueryWSRequest(credentials);
		BookingRequest request = ws.getQueryWSRequest(credentials, 0, "2010-03-31", "2010-05-31");

		System.out.println("request from dat " + request.getFromDat());
		System.out.println("request to dat " + request.getToDat());

		// JAXBElement<String> fromDat = request.getFromDat();
		// JAXBElement<String> toDat = request.getToDat();

		if (fromDat == null) {
			System.out.println("fromdat is null");
			// fromDat = request.
		} else {
			System.out.println(fromDat.getValue());
			// fromDat.setValue("2010-03-31");
		}

		if (toDat == null) {
			System.out.println("todat is null");
		} else {
			System.out.println(toDat.getValue());
			// toDat.setValue("2010-05-31");
		}

		// set values for getting calendars
		// request.setFromDat(fromDat);
		// request.setToDat(toDat);
		// request.setCentralTidbokID(1);

		ArrayOfCalendar calendars = ws.getQueryWSRequestCalendar(request);
		if (calendars == null) {
			Assert.assertTrue(true);
			return;

		}
		List<se.vgregion.webbtidbok.ws.Calendar> calendarList = calendars.getCalendar();
		if (calendarList == null) {
			Assert.assertTrue(true);

		} else {

			if (calendarList.isEmpty()) {
				Assert.assertTrue(true);
			} else {

				for (se.vgregion.webbtidbok.ws.Calendar c : calendarList) {
					// System.out.println(bp.getAddress().getValue());
					// System.out.println(bp.getCentralTidbokID());
					// System.out.println(bp.getMottagning().getValue());
					System.out.println("Datum: " + c.getDatum().toString());
					System.out.println("Status: " + c.getStatus().getValue());
					System.out.println("Spärrad: " + c.isSparr());
					// System.out.println("Klocka: " + bt.);

				}

			}

			Assert.assertFalse(true);
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
