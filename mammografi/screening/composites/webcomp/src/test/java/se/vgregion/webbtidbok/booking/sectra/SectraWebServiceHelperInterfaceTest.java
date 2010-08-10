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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"sectra-test-context.xml"})
public class SectraWebServiceHelperInterfaceTest {

    @Autowired
    SectraWSMock wsMock;
    
    SectraWebServiceHelperImpl helper;
    
    String patientId = "19770707-0004";
    String examinationNr = "SEMSUS000001";
    
    @Before
    public void setupWebservice() {
        helper = new SectraWebServiceHelperImpl();
        helper.setThePortSU(wsMock);
    }

	@Test
	public void testGetBookingInfo() throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {
		BookingInfoLocal bi = null;
		bi = helper.getBookingInfo(patientId, examinationNr);
		
		assertNotNull(bi);
		assertEquals(patientId, bi.getPatientId());
		assertEquals(examinationNr, bi.getExaminationNr());
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
