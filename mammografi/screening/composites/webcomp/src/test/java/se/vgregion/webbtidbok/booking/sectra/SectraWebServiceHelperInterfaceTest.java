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

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import se.vgregion.webbtidbok.generated.sectra.ArrayOfSection;
import se.vgregion.webbtidbok.generated.sectra.BookingInfo;
import se.vgregion.webbtidbok.generated.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.generated.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.generated.sectra.Section;

public class SectraWebServiceHelperInterfaceTest {

	static SectraWebServiceHelperImpl helper = new SectraWebServiceHelperImpl();
	static String patientId="1912121212";
	static String examinationNr = "SERTEST00012345";
	@Test
	public void testGetBookingInfo() throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {
		BookingInfoLocal bi = null;
		bi = helper.getBookingInfo(patientId, examinationNr);
		
		if(bi == null){
			assertFalse(true);
		}
		String pId = bi.getPatientId();
		String exNo = bi.getExaminationNr();
		System.out.println("### pId: " + pId);
		System.out.println("### exNo: " + exNo);
		if(bi.getPatientId().equals(patientId) && bi.getExaminationNr().equals(examinationNr)){
			assertTrue(true);
		} else {
			System.out.println(bi.getPatientId() + " != " + patientId );
			System.out.println(bi.getExaminationNr() + " != " + examinationNr);
			assertFalse(true);
		}
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
		
		if(sectionList.isEmpty()){
			assertFalse(true);
		} else {
			assertTrue(true);
		}
	}
}
