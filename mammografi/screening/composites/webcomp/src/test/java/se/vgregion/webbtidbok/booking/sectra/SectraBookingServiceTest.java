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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;

public class SectraBookingServiceTest{

    static SectraWebServiceHelperImpl helper = new SectraWebServiceHelperImpl();
    static String patientId = "1912121212";
    static String examinationNr = "SERTEST00012345";
    @Test
    public void testGetBookingInfo() throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {
        
        
        BookingInfoLocal bi = helper.getBookingInfo(patientId, examinationNr);
        assertNotNull(bi);

        assertEquals(examinationNr, bi.getExaminationNr());
        assertEquals("exam type mammografi", bi.getExamType());
        assertEquals("exam type code 123", bi.getExamTypeCode());
        assertEquals("S", bi.getLaterality());
        assertEquals(patientId, bi.getPatientId());
        assertEquals("Kerberos", bi.getPatientName());

        TimeBlockLocal bt = bi.getBookedTime();
        assertNotNull(bt);
    
        assertEquals("timeBlockId_1", bt.getId());
        assertEquals(10, bt.getLength());
        assertNotNull(bt.getSection());
        assertNotNull(bt.getStartTime());
    }

    @Test
    public void testListSections() throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage {
        
        ArrayOfSectionLocal sectionArray = helper.listSections(examinationNr);
        List<SectionLocal> sectionList = sectionArray.getSectionLocalList();
        
        assertNotNull(sectionArray);
        assertNotNull(sectionArray.getSectionLocalList());
        assertFalse(sectionList.isEmpty());
        assertTrue(sectionList.size() >= 2);

        SectionLocal section;
        Iterator<SectionLocal> iter = sectionList.iterator();
    
        for(int i = 1; iter.hasNext(); i++){
            section = iter.next();

            assertEquals("secId " + i, section.getSecId());
//            assertEquals("Vägen " + i, section.getSecAddress());
            assertEquals("Section description " + i, section.getSecDescription());
            assertEquals("mottagningen" + i + "@test.se", section.getSecMail());;
            assertEquals("Mottagningen " + i, section.getSecName());
            assertEquals(Integer.toString(i), section.getSecPhone());
        }           
    }
}
