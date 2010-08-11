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
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import se.vgregion.webbtidbok.domain.sectra.BookingSectra;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;

public class SectraWebServiceHelperInterfaceTest {

	static SectraWebServiceHelperImpl helper = new SectraWebServiceHelperImpl();
	static String patientId="19770707-0020";
	static String examinationNr = "SEMSUS000002";
	
	@Before
	public void setup(){
		FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext("classpath:se/vgregion/webbtidbok/booking/web-application-config.xml");
		SectraWSTestMock bean = (SectraWSTestMock) fileSystemXmlApplicationContext.getBean("sectraMockServiceSU");
		helper.setThePortSU(bean);
		helper.setBookingMapperSectra( new BookingMapperSectra());
	}
	
	@Test
	public void testGetBookingInfo() throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {
		BookingSectra bi = null;
		bi = (BookingSectra) helper.getBookingInfo(patientId, examinationNr);
		
		assertNotNull(bi);

		String pId = bi.getPatientId();
		String exNo = bi.getExaminationId();
		System.out.println("### pId: " + pId);
		System.out.println("### exNo: " + exNo);

		assertEquals(patientId, bi.getPatientId());
		assertEquals(examinationNr, bi.getExaminationId());
	}

	@Test
	/*
	 * just testing so that the method doesn't return an empty or nullified object.
	 * there seems to be no connection between examinationNr and listSection /sections
	 */
	public void testListSections() throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage {
		ArrayOfSectionLocal sectionArray = helper.listSections(examinationNr);
		List<SectionLocal> sectionList = null;
		sectionList = sectionArray.getSectionLocalList();
		
		assertFalse(sectionList.isEmpty());
	}
}
