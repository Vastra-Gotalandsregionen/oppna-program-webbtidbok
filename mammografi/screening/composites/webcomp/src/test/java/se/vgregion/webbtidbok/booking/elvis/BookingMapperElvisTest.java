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

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.ws.BookingPlace;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.ObjectFactory;

public class BookingMapperElvisTest {

	private static final String MOTTAGNING = "Mölndals sjukhus, Klinisk fysologi";
	private static final String ADDRESS = "ingång V, entréplan";
	private static final String NAME = "DAVID BENNEHULT";
	private static final String PNR = "19770220-2222";
	private static final int ANTALOMBOK = 0;
	private BookingMapperElvis bookingMapperElvis;
	private BookingResponse bookingResponse;
	private XMLGregorianCalendar newXMLGregorianCalendar;
	private ObjectFactory objectFactory;
	private BookingPlace bookingPlace;

	@Before
	public void setUp() throws Exception {
		bookingMapperElvis = new BookingMapperElvis();
		objectFactory = new ObjectFactory();
		createBookingResponseObject();
		createBookingPlaceObject();
		
	}

	private void createBookingResponseObject()
			throws DatatypeConfigurationException {
		bookingResponse = new BookingResponse();
		bookingResponse.setPnr(objectFactory.createString(PNR));
		bookingResponse.setNamn(objectFactory.createString(NAME));
		bookingResponse.setAddress(objectFactory.createString(ADDRESS));
		bookingResponse.setMottagning(objectFactory.createString(MOTTAGNING));
		bookingResponse.setAntalOmbok(0);
		bookingResponse.setMaxAntalOmbok(1);
		bookingResponse.setCentralTidbokID(3);
		newXMLGregorianCalendar = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar();
		bookingResponse.setBokadTid(newXMLGregorianCalendar);

	}
	
	private void createBookingPlaceObject(){
		bookingPlace = new BookingPlace();
		bookingPlace.setCentralTidbokID(1);
		bookingPlace.setMottagning(objectFactory.createString("Sahlgrenska"));
		bookingPlace.setAddress(objectFactory.createString("blå stråket 1"));
	}

	@Test
	public void testBookingMapping() {
		Booking bookingMapping = bookingMapperElvis
				.bookingMapping(bookingResponse);
		assertEquals("David Bennehult", bookingMapping.getPatientName());
		assertEquals(MOTTAGNING + ", " + ADDRESS, bookingMapping
				.getSurgery().getFullAddress());
		assertEquals(PNR, bookingMapping.getPatientId());
		assertEquals(newXMLGregorianCalendar.toGregorianCalendar().getTime()
				.toString(), bookingMapping.getStartTime().toString());
		assertTrue(bookingMapping.isUpdateable());
	}
	
	@Test
	public void testBookingPlaceMapping() {
		Surgery bookingPlaceMapping = bookingMapperElvis.bookingPlaceMapping(bookingPlace);
		assertEquals("1", bookingPlaceMapping.getSurgeryId());
		assertEquals("Sahlgrenska", bookingPlaceMapping.getSurgeryName());
		assertEquals("blå stråket 1", bookingPlaceMapping.getSurgeryAddress());
		
	}
	
	@Test
	public void testNullpointerForBooking(){
		bookingResponse = new BookingResponse();
		Booking bookingMapping = bookingMapperElvis.bookingMapping(bookingResponse);
		assertNotNull(bookingMapping);
	}
	
	@Test
	public void testNullpointerForBookingPlace(){
		bookingPlace = new BookingPlace();
		Surgery bookingPlaceMapping = bookingMapperElvis.bookingPlaceMapping(bookingPlace);
		assertNotNull(bookingPlaceMapping);
	}
	

}
