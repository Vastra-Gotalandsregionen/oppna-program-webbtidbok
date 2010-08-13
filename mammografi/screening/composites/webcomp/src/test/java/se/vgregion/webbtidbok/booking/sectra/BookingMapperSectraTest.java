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
package se.vgregion.webbtidbok.booking.sectra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.ObjectFactory;
import se.vgregion.webbtidbok.ws.sectra.Section;
import se.vgregion.webbtidbok.ws.sectra.TimeBlock;

public class BookingMapperSectraTest {

	private static final String SECTIONNAME = "Section name";
	private static final String SECTIONADDRESS = "Section address";
	private static final String TIMEBLOCKID = "Time block id";
	private static final String PATIENTNAME = "David B";
	private static final String PATIENTID = "19121212-1212";
	private static final String LATERALITY = "Laterality";
	private static final String EXAMTYPECODE = "Exam type Code";
	private static final String EXAMTYPE = "Exam type";
	private static final String EXAMNBR = "123";
	private static final String SURGERYADDRESS = "address";
	private static final String NAME = "name";
	private static final String ID = EXAMNBR;
	private BookingMapperSectra bookingMapperSectra;
	private ObjectFactory objectFactory;
	private BookingInfo bookingInfo;
	private XMLGregorianCalendar newXMLGregorianCalendar;

	@Before
	public void setUp() throws Exception {
		bookingMapperSectra = new BookingMapperSectra();
		objectFactory = new ObjectFactory();
		bookingInfo = new BookingInfo();
		TimeBlock timeBlock = new TimeBlock();
		newXMLGregorianCalendar = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar();
		Section section = new Section();
		section.setAddress(objectFactory.createString(SECTIONADDRESS));
		section.setName(objectFactory.createString(SECTIONNAME));

		timeBlock.setId(objectFactory.createString(TIMEBLOCKID));
		timeBlock.setSection(objectFactory.createSection(section));
		timeBlock.setStartTime(newXMLGregorianCalendar);
		bookingInfo.setExamNo(objectFactory.createString(EXAMNBR));
		bookingInfo.setExamType(objectFactory.createString(EXAMTYPE));
		bookingInfo.setExamTypeCode(objectFactory.createString(EXAMTYPECODE));
		bookingInfo.setLaterality(objectFactory.createString(LATERALITY));
		bookingInfo.setPatientId(objectFactory.createString(PATIENTID));
		bookingInfo.setPatientName(objectFactory.createString(PATIENTNAME));
		bookingInfo.setBookedTime(objectFactory.createTimeBlock(timeBlock));

	}

	@Test
	public void testBookingMapping() {
		Booking bookingMapping = bookingMapperSectra
				.bookingMapping(bookingInfo);

		assertEquals(PATIENTNAME, bookingMapping.getPatientName());
		assertEquals(PATIENTID, bookingMapping.getPatientId());
		assertEquals(newXMLGregorianCalendar.toGregorianCalendar().getTime()
				.getTime(), bookingMapping.getStartTime().getTime());
		assertEquals(SECTIONNAME + ", " + SECTIONADDRESS, bookingMapping
				.getSurgeryAddress());

	}

	@Test
	public void testSurgeryMapping() {
		Section section = new Section();

		section.setId(objectFactory.createString(ID));
		section.setName(objectFactory.createString(NAME));
		section.setAddress(objectFactory.createString(SURGERYADDRESS));

		Surgery surgeryMapping = bookingMapperSectra.surgeryMapping(section);
		assertEquals(ID, surgeryMapping.getSurgeryId());
		assertEquals(NAME, surgeryMapping.getSurgeryName());
		assertEquals(SURGERYADDRESS, surgeryMapping.getSurgeryAddress());
	}

	@Test
	public void testNullPointerForBookingMappingSectra() {
		Booking bookingMapping = bookingMapperSectra
				.bookingMapping(new BookingInfo());
		assertNotNull(bookingMapping);
	}

}
