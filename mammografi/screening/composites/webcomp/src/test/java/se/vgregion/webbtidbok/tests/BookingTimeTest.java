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

import java.util.Calendar;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.elvis.BookingService;
import se.vgregion.webbtidbok.booking.elvis.BookingServiceInterface;
import se.vgregion.webbtidbok.booking.elvis.WebServiceHelper;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.lang.DateHandler;
import se.vgregion.webbtidbok.lang.StringHandler;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingTime;
import se.vgregion.webbtidbok.ws.ObjectFactory;

/**
 * @author conpem
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/se/vgregion/webbtidbok/booking/web-application-config.xml"})
public class BookingTimeTest {

  @Autowired
	private WebServiceHelper ws;
	private final ObjectFactory objectFactory = new ObjectFactory();

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Ignore
	@Test
	public void testBookingTimeLocals() {

		Calendar tCal = Calendar.getInstance();
		tCal.set(Calendar.MONTH, 4);
		tCal.set(Calendar.DATE, 12);

		System.out.println(tCal.getTime().toString());

		BookingServiceInterface service = new BookingService();

		State credentials = new State();
		// credentials.setPasswd("Zs12JzIW");
		// credentials.setPnr("19121212-1212");
		credentials.setPasswd("4YL7CXnp");
		credentials.setPnr("19121212-1212");

		credentials.setCentralTidbokID(1);
		credentials.setLoggedIn(true);

		JAXBElement<String> fromDat = objectFactory
				.createBookingRequestFromDat("2010-05-12");

		BookingRequest request = ws.getQueryWSRequest(credentials);
		request.setFromDat(fromDat);
		request.setCentralTidbokID(1);

		// List<BookingTime> timeList = time.getBookingTime();
		List<se.vgregion.webbtidbok.domain.BookingTime> timeList = service
				.getBookingTime(credentials, "1", tCal);

		Assert.assertNotNull(timeList);
		Assert.assertFalse(timeList.isEmpty());

		for (se.vgregion.webbtidbok.domain.BookingTime bt : timeList) {
			System.out.println("Antal: " + bt.toString());
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Ignore
	@Test
	public void testMultipleBookingTimeCalendars() {
		/*
		 * fje5rnXG 19910104-2399 NC1dqBZa 19030311-9804 nDbUMxTL 19700123-9297
		 * 4YL7CXnp 19121212-1212 6wHRDtKa 19910104-2399 kzxpQlLb 19420213-8014
		 */

		State credentials = new State();
		// credentials.setPasswd("Zs12JzIW");
		// credentials.setPnr("19121212-1212");
		credentials.setPasswd("fje5rnXG");
		credentials.setPnr("19910104-2399");

		testBookingTimeCalendar2();

		credentials.setPasswd("NC1dqBZa");
		credentials.setPnr("19030311-9804");

		testBookingTimeCalendar2();

		credentials.setPasswd("nDbUMxTL");
		credentials.setPnr("19700123-9297");

		testBookingTimeCalendar2();

		credentials.setPasswd("4YL7CXnp");
		credentials.setPnr("19121212-1212");

		testBookingTimeCalendar2();

		credentials.setPasswd("6wHRDtKa");
		credentials.setPnr("19910104-2399");

		testBookingTimeCalendar2();

		credentials.setPasswd("kzxpQlLb");
		credentials.setPnr("19420213-8014");

		testBookingTimeCalendar2();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Ignore
	@Test
	public void testBookingTimeCalendar2() {

		Calendar tCal = Calendar.getInstance();
		tCal.set(Calendar.MONTH, 4);
		tCal.set(Calendar.DATE, 14);

		System.out.println(tCal.getTime().toString());

		BookingServiceInterface service = new BookingService();
		((BookingService) service).setHelper(BookingPlacesTest
				.getWebServiceHelper());

		State credentials = new State();
		// credentials.setPasswd("Zs12JzIW");
		// credentials.setPnr("19121212-1212");
		credentials.setPasswd("kzxpQlLb");
		credentials.setPnr("19420213-8014");
		credentials.setLoggedIn(true);
		credentials.setCentralTidbokID(1);
		JAXBElement<String> fromDat = objectFactory
				.createBookingRequestFromDat("2010-05-30");

		// request.setFromDat(fromDat);
		// request.setCentralTidbokID(1);

		Booking bookingResponseLocal = service.getBooking(credentials);
		System.out.println("bookingResponseLocal.startTime: "
				+ StringHandler.formatDate("yyyy-MM-dd", bookingResponseLocal.getStartTime()));
		System.out.println("CentralTidBokId: "
				+ credentials.getCentralTidbokID());
		// List<BookingTime> timeList = time.getBookingTime();
		List<se.vgregion.webbtidbok.domain.BookingTime> timeList = service
				.getBookingTime(credentials, "1", tCal);

		if (timeList == null) {
			Assert.assertFalse(true);

		} else {

			if (timeList.isEmpty()) {
				Assert.assertFalse(true);

			} else {

				for (se.vgregion.webbtidbok.domain.BookingTime bt : timeList) {
					// System.out.println(bp.getAddress().getValue());
					// System.out.println(bp.getCentralTidbokID());
					// System.out.println(bp.getMottagning().getValue());
					System.out.println("Antal: " + bt.toString());

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
	public void testBookingTimeCalendar() {

		Calendar tCal = Calendar.getInstance();
		tCal.set(Calendar.MONTH, 4);
		tCal.set(Calendar.DATE, 14);

		System.out.println(tCal.getTime().toString());

		BookingServiceInterface service = new BookingService();

		State credentials = new State();
		// credentials.setPasswd("Zs12JzIW");
		// credentials.setPnr("19121212-1212");
		credentials.setPasswd("kzxpQlLb");
		credentials.setPnr("19420213-8014");

		credentials.setLoggedIn(true);

		JAXBElement<String> fromDat = objectFactory
				.createBookingRequestFromDat("2010-05-30");

		// request.setFromDat(fromDat);
		// request.setCentralTidbokID(1);

		Booking bookingResponseLocal = service.getBooking(credentials);
        System.out.println("bookingResponseLocal.startTime: "
                + StringHandler.formatDate("yyyy-MM-dd", bookingResponseLocal.getStartTime()));
		System.out.println("CentralTidBokId: "
				+ credentials.getCentralTidbokID());
		// List<BookingTime> timeList = time.getBookingTime();
		List<se.vgregion.webbtidbok.domain.BookingTime> timeList = service
				.getBookingTime(credentials, Integer.toString(credentials.getCentralTidbokID()),
				        tCal);

		if (timeList == null) {
			Assert.assertFalse(true);

		} else {

			if (timeList.isEmpty()) {
				Assert.assertFalse(true);
			} else {

				for (se.vgregion.webbtidbok.domain.BookingTime bt : timeList) {
					// System.out.println(bp.getAddress().getValue());
					// System.out.println(bp.getCentralTidbokID());
					// System.out.println(bp.getMottagning().getValue());
					System.out.println("Antal: " + bt.toString());

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
	public void testBookingTime() {
		State credentials = new State();
		// credentials.setPasswd("Zs12JzIW");
		// credentials.setPnr("19121212-1212");
		credentials.setPasswd("6wHRDtKa");
		credentials.setPnr("19910104-2399");

		JAXBElement<String> fromDat = objectFactory
				.createBookingRequestFromDat("2010-05-14");
		JAXBElement<String> toDat = objectFactory
				.createBookingRequestToDat("2010-05-31");

		BookingRequest request = ws.getQueryWSRequest(credentials);
		request.setFromDat(fromDat);
		request.setCentralTidbokID(1);

		ArrayOfBookingTime time = ws.getQueryWSRequestTime(request);
		List<BookingTime> timeList = time.getBookingTime();
		if (timeList == null) {
			Assert.assertFalse(true);

		} else {

			if (timeList.isEmpty()) {
				Assert.assertFalse(true);
			} else {

				for (BookingTime bt : timeList) {
					// System.out.println(bp.getAddress().getValue());
					// System.out.println(bp.getCentralTidbokID());
					// System.out.println(bp.getMottagning().getValue());
					System.out.println("Antal: " + bt.getAntal());
					System.out.println("Datum: " + bt.getDatum().toString());
					System.out.println("Klocka: " + bt.getKlocka().getValue());
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
	public void testBookingTimeIncorrectDate() {
		State credentials = new State();
		// credentials.setPasswd("Zs12JzIW");
		// credentials.setPnr("19121212-1212");

		credentials.setPasswd("Y8PBZRUr");
		credentials.setPnr("19960103-2395");

		JAXBElement<String> fromDat = objectFactory
				.createBookingRequestFromDat("2010/03/31");
		JAXBElement<String> toDat = objectFactory
				.createBookingRequestToDat("2010/05/31");

		BookingRequest request = ws.getQueryWSRequest(credentials);
		request.setFromDat(fromDat);
		request.setCentralTidbokID(1);

		ArrayOfBookingTime time = ws.getQueryWSRequestTime(request);
		List<BookingTime> timeList = time.getBookingTime();
		if (timeList == null) {
			Assert.assertFalse(true);

		} else {

			if (timeList.isEmpty()) {
				Assert.assertFalse(true);
			} else {

				for (BookingTime bt : timeList) {
					// System.out.println(bp.getAddress().getValue());
					// System.out.println(bp.getCentralTidbokID());
					// System.out.println(bp.getMottagning().getValue());
					System.out.println("Antal: " + bt.getAntal());
					System.out.println("Datum: " + bt.getDatum().toString());
					System.out.println("Klocka: " + bt.getKlocka().getValue());
					// System.out.println("Klocka: " + bt.);

				}

			}

			Assert.assertTrue(true);
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testBookingTimeFunkyDate() {
		State credentials = new State();
		// credentials.setPasswd("Zs12JzIW");
		// credentials.setPnr("19121212-1212");
		credentials.setPasswd("Y8PBZRUr");
		credentials.setPnr("19960103-2395");

		JAXBElement<String> fromDat = objectFactory
				.createBookingRequestFromDat("20100331");
		JAXBElement<String> toDat = objectFactory
				.createBookingRequestToDat("20100531");

		BookingRequest request = ws.getQueryWSRequest(credentials);
		request.setFromDat(fromDat);
		request.setCentralTidbokID(1);

		ArrayOfBookingTime time = ws.getQueryWSRequestTime(request);
		if (time == null) {
			Assert.assertTrue(true);
			return;
		}
		List<BookingTime> timeList = time.getBookingTime();
		if (timeList == null) {
			Assert.assertTrue(true);

		} else {

			if (timeList.isEmpty()) {
				Assert.assertFalse(true);
			} else {

				for (BookingTime bt : timeList) {
					// System.out.println(bp.getAddress().getValue());
					// System.out.println(bp.getCentralTidbokID());
					// System.out.println(bp.getMottagning().getValue());
					System.out.println("Antal: " + bt.getAntal());
					System.out.println("Datum: " + bt.getDatum().toString());
					System.out.println("Klocka: " + bt.getKlocka().getValue());
					// System.out.println("Klocka: " + bt.);

				}

			}

			Assert.assertTrue(false);
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testBookingTimeNoDate() {
		State credentials = new State();
		// credentials.setPasswd("Zs12JzIW");
		// credentials.setPnr("19121212-1212");
		credentials.setPasswd("Y8PBZRUr");
		credentials.setPnr("19960103-2395");

		JAXBElement<String> fromDat = objectFactory
				.createBookingRequestFromDat("20100331");
		JAXBElement<String> toDat = objectFactory
				.createBookingRequestToDat("20100531");

		BookingRequest request = ws.getQueryWSRequest(credentials);
		// request.setFromDat(fromDat);
		request.setCentralTidbokID(1);

		ArrayOfBookingTime time = ws.getQueryWSRequestTime(request);
		if (time == null) {
			Assert.assertTrue(true);
			return;
		}
		List<BookingTime> timeList = time.getBookingTime();
		if (timeList == null) {
			Assert.assertTrue(true);

		} else {

			if (timeList.isEmpty()) {
				Assert.assertTrue(true);
			} else {

				for (BookingTime bt : timeList) {
					// System.out.println(bp.getAddress().getValue());
					// System.out.println(bp.getCentralTidbokID());
					// System.out.println(bp.getMottagning().getValue());
					System.out.println("Antal: " + bt.getAntal());
					System.out.println("Datum: " + bt.getDatum().toString());
					System.out.println("Klocka: " + bt.getKlocka().getValue());
					// System.out.println("Klocka: " + bt.);

				}

			}

			Assert.assertFalse(true);
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testBookingTimeStringDate() {
		State credentials = new State();

		// credentials.setPasswd("Zs12JzIW");
		// credentials.setPnr("19121212-1212");
		credentials.setPasswd("Y8PBZRUr");
		credentials.setPnr("19960103-2395");

		JAXBElement<String> fromDat = objectFactory
				.createBookingRequestFromDat("nullepullebullekullemulle");
		JAXBElement<String> toDat = objectFactory
				.createBookingRequestToDat("20100531");

		BookingRequest request = ws.getQueryWSRequest(credentials);
		request.setFromDat(fromDat);
		request.setCentralTidbokID(1);

		ArrayOfBookingTime time = ws.getQueryWSRequestTime(request);
		if (time == null) {
			Assert.assertTrue(true);
			return;
		}
		List<BookingTime> timeList = time.getBookingTime();
		if (timeList == null) {
			Assert.assertTrue(true);

		} else {

			if (timeList.isEmpty()) {
				Assert.assertTrue(true);
			} else {

				for (BookingTime bt : timeList) {
					// System.out.println(bp.getAddress().getValue());
					// System.out.println(bp.getCentralTidbokID());
					// System.out.println(bp.getMottagning().getValue());
					System.out.println("Antal: " + bt.getAntal());
					System.out.println("Datum: " + bt.getDatum().toString());
					System.out.println("Klocka: " + bt.getKlocka().getValue());
					// System.out.println("Klocka: " + bt.);

				}

			}

			Assert.assertFalse(true);
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testBookingTimeStringNullDate() {
		State credentials = new State();
		// credentials.setPasswd("Zs12JzIW");
		// credentials.setPnr("19121212-1212");
		credentials.setPasswd("Y8PBZRUr");
		credentials.setPnr("19960103-2395");

		JAXBElement<String> fromDat = objectFactory
				.createBookingRequestFromDat(null);
		JAXBElement<String> toDat = objectFactory
				.createBookingRequestToDat("20100531");

		BookingRequest request = ws.getQueryWSRequest(credentials);
		request.setFromDat(fromDat);
		request.setCentralTidbokID(1);

		ArrayOfBookingTime time = ws.getQueryWSRequestTime(request);
		if (time == null) {
			Assert.assertTrue(true);
			return;
		}
		List<BookingTime> timeList = time.getBookingTime();
		if (timeList == null) {
			Assert.assertTrue(true);

		} else {

			if (timeList.isEmpty()) {
				Assert.assertFalse(true);
			} else {

				for (BookingTime bt : timeList) {
					// System.out.println(bp.getAddress().getValue());
					// System.out.println(bp.getCentralTidbokID());
					// System.out.println(bp.getMottagning().getValue());
					System.out.println("Antal: " + bt.getAntal());
					System.out.println("Datum: " + bt.getDatum().toString());
					System.out.println("Klocka: " + bt.getKlocka().getValue());
					// System.out.println("Klocka: " + bt.);

				}

			}

			Assert.assertFalse(true);
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testBookingTimeInAccurateStringDate() {
		State credentials = new State();

		// credentials.setPasswd("Zs12JzIW");
		// credentials.setPnr("19121212-1212");
		credentials.setPasswd("Y8PBZRUr");
		credentials.setPnr("19960103-2395");

		JAXBElement<String> fromDat = objectFactory
				.createBookingRequestFromDat("05/01/2001");
		JAXBElement<String> toDat = objectFactory
				.createBookingRequestToDat("20100531");

		BookingRequest request = ws.getQueryWSRequest(credentials);
		request.setFromDat(fromDat);
		request.setCentralTidbokID(1);

		ArrayOfBookingTime time = ws.getQueryWSRequestTime(request);
		if (time == null) {
			Assert.assertTrue(true);
			return;
		}
		List<BookingTime> timeList = time.getBookingTime();
		if (timeList == null) {
			Assert.assertTrue(true);

		} else {

			if (timeList.isEmpty()) {
				Assert.assertTrue(true);
			} else {

				for (BookingTime bt : timeList) {
					// System.out.println(bp.getAddress().getValue());
					// System.out.println(bp.getCentralTidbokID());
					// System.out.println(bp.getMottagning().getValue());
					System.out.println("Antal: " + bt.getAntal());
					System.out.println("Datum: " + bt.getDatum().toString());
					System.out.println("Klocka: " + bt.getKlocka().getValue());
					// System.out.println("Klocka: " + bt.);

				}

			}

			Assert.assertFalse(true);
		}

	}

}
