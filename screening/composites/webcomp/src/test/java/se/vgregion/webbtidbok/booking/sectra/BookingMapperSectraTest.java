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

package se.vgregion.webbtidbok.booking.sectra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;
import se.vgregion.webbtidbok.lang.DateHandler;
import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.ObjectFactory;
import se.vgregion.webbtidbok.ws.sectra.Section;
import se.vgregion.webbtidbok.ws.sectra.TimeBlock;

public class BookingMapperSectraTest {

	private static final String TIME = "10:00";
	private static final int ANTAL = 2;
	private static final String SECTIONNAME = "Weird section name";
	private static final String REALSECTIONNAME = "Real section name";
	private static final String SECTIONADDRESS = "Section address";
	private static final String TIMEBLOCKID = "Time block id";
	private static final String PATIENTNAME = "David B";
	private static final String PATIENTID = "19121212-1212";
	private static final String LATERALITY = "Laterality";
	private static final String EXAMTYPECODE = "Exam type Code";
	private static final String EXAMTYPE = "Exam type";
	private static final String EXAMNBR = "123";
	private static final String SURGERYADDRESS = "address";
	private static final String SURGERYPHONE = "031-123456";
	private static final String NAME = "name";
	private static final String REALNAME = "realname";
	private static final String ID = EXAMNBR;
	private static final String EMAIL = "section.email@email.com";
	private BookingMapperSectra bookingMapperSectra;
	private ObjectFactory objectFactory;
	private BookingInfo bookingInfo;
	private XMLGregorianCalendar newXMLGregorianCalendar;
	private Section section;

	@Before
	public void setUp() throws Exception {
		bookingMapperSectra = new BookingMapperSectra();
		objectFactory = new ObjectFactory();
		bookingInfo = new BookingInfo();
		TimeBlock timeBlock = new TimeBlock();
		newXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		section = new Section();
		// bookingTime.setKlocka(objectFactory
		// .createString(newXMLGregorianCalendar.toGregorianCalendar()
		// .toString()));
		// bookingTime.setKlocka(objectFactory.createString(TIME));

		section.setAddress(objectFactory.createString(SECTIONADDRESS));
		section.setName(objectFactory.createString(SECTIONNAME));
		section.setDescription(objectFactory.createString(REALSECTIONNAME + "#Irrelevant description content"));
		section.setMail(objectFactory.createString(EMAIL));

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
		Booking booking = new BookingSectra();
		booking = bookingMapperSectra.bookingMapping(bookingInfo);
		assertEquals(PATIENTNAME, booking.getPatientName());
		assertEquals(PATIENTID, booking.getPatientId());
		assertEquals(newXMLGregorianCalendar.toGregorianCalendar().getTime().getTime(), booking.getStartTime().getTime());
		assertEquals(REALSECTIONNAME + ", " + SECTIONADDRESS, booking.getSurgery().getFullAddress());

	}

	@Test
	public void testSurgeryMapping() {
		// Section section = new Section();

		section.setId(objectFactory.createString(ID));
		section.setName(objectFactory.createString(NAME));
		section.setDescription(objectFactory.createString(REALNAME + "#Irrelevant description"));
		section.setAddress(objectFactory.createString(SURGERYADDRESS));
		section.setPhone(objectFactory.createString(SURGERYPHONE));

		Surgery surgery = new Surgery();
		surgery = bookingMapperSectra.surgeryMapping(section);
		assertEquals(ID, surgery.getSurgeryId());
		assertEquals(REALNAME, surgery.getSurgeryName());
		assertEquals(SURGERYADDRESS, surgery.getSurgeryAddress());
		assertEquals(SURGERYPHONE, surgery.getSurgeryPhone());

		// Test with no hash - should be empty
		section.setDescription(objectFactory.createString(REALNAME));
		surgery = bookingMapperSectra.surgeryMapping(section);
		assertEquals("", surgery.getSurgeryName());

		// Test with only hash - should return a surgery name
		section.setDescription(objectFactory.createString(REALNAME + "#"));
		surgery = bookingMapperSectra.surgeryMapping(section);
		assertEquals(REALNAME, surgery.getSurgeryName());
	}

	@Test
	public void testNullPointerForBookingMappingSectra() {
		Booking bookingMapping = bookingMapperSectra.bookingMapping(new BookingInfo());
		assertNotNull(bookingMapping);
	}

	@Test
	public void testDaysMapping() {
		try {
			XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(2010, 1, 1, 12, 30, 0, 0, 0);
			Calendar cal = bookingMapperSectra.daysMapping(xcal);

			assertEquals(2010, cal.get(Calendar.YEAR));
			assertEquals(0, cal.get(Calendar.MONTH)); // January is month 0, not
			// 1 in Calendar class.
			assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
			assertEquals(12, cal.get(Calendar.HOUR_OF_DAY));
			assertEquals(30, cal.get(Calendar.MINUTE));
			assertEquals(0, cal.get(Calendar.SECOND));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			fail("DatatypConfiguration incorrect");
		}

	}

	@Test
	public void testBookingTimeMapping() {
		Section section = new Section();
		section.setId(objectFactory.createString(ID));
		section.setName(objectFactory.createString(NAME));
		section.setAddress(objectFactory.createString(SURGERYADDRESS));

		TimeBlock timeBlock = new TimeBlock();
		timeBlock.setId(objectFactory.createString("blockid"));
		timeBlock.setLength(15);
		timeBlock.setSection(objectFactory.createSection(section));
		timeBlock.setStartTime(DateHandler.xmlCalendarFor(2010, 1, 1, 12, 30, 0));

		BookingTime booking = bookingMapperSectra.bookingTimeMapping(timeBlock);
		assertEquals("blockid", booking.getBookingTimeId());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		assertEquals("2010-01-01 13:30:00 +0100", sdf.format(booking.getDateTime()));
	}
}
