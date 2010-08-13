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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfBookingInfo;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfSection;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfTimeBlock;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfdateTime;
import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.IRisReschedule;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleRescheduleErrorInfoFaultFaultMessage;

public class SectraWebServiceHelperImplTest {

	private static final String PATIENTID = "19121212-1212";
	private static final String PASSWORD = "123";
	private SectraWebServiceHelperImpl sectraWebServiceHelperImpl;
	private IRisRescheduleMock mockSUWS;
	private IRisRescheduleMock mockNUWS;
	private State state;

	@Before
	public void setUp() throws Exception {
		sectraWebServiceHelperImpl = new SectraWebServiceHelperImpl();
		mockSUWS = new IRisRescheduleMock();
		mockNUWS = new IRisRescheduleMock();
		sectraWebServiceHelperImpl.setThePortNU(mockNUWS);
		sectraWebServiceHelperImpl.setThePortSU(mockSUWS);
		state = new State();
		state.setPasswd(PASSWORD);
		state.setPnr(PATIENTID);
		state.setService("MAMMO_SU");
	}

	@Test
	public void testLogin() {
		sectraWebServiceHelperImpl.login(state);
		assertTrue(mockSUWS.wasCalled);
		assertFalse(mockNUWS.wasCalled);
		mockSUWS.wasCalled = false;
		state.setService("MAMMO_NU");
		sectraWebServiceHelperImpl.login(state);
		assertFalse(mockSUWS.wasCalled);
		assertTrue(mockNUWS.wasCalled);
		assertEquals(mockNUWS.patientId, state.getPnr());
		assertEquals(mockNUWS.examinationNbr, state.getPasswd());
	}

	class IRisRescheduleMock implements IRisReschedule {
		boolean wasCalled;
		String patientId;
		String examinationNbr;

		@Override
		public BookingInfo getBookingInfo(String patientId, String examinationNr)
				throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {
			wasCalled = true;
			this.patientId = patientId;
			this.examinationNbr = examinationNr;
			return new BookingInfo();
		}

		@Override
		public ArrayOfBookingInfo getBookings(String patientId)
				throws IRisRescheduleGetBookingsErrorInfoFaultFaultMessage {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ArrayOfdateTime listFreeDays(XMLGregorianCalendar startDate,
				XMLGregorianCalendar endDate, String examinationNr,
				String sectionId)
				throws IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ArrayOfTimeBlock listFreeTimes(XMLGregorianCalendar startDate,
				XMLGregorianCalendar endDate, String examinationNr,
				String sectionId)
				throws IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ArrayOfSection listSections(String examinationNr)
				throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public BookingInfo reschedule(String examinationNr, String newTimeId,
				XMLGregorianCalendar startTime, Boolean printNewNotice,
				String rescheduleComment)
				throws IRisRescheduleRescheduleErrorInfoFaultFaultMessage {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
