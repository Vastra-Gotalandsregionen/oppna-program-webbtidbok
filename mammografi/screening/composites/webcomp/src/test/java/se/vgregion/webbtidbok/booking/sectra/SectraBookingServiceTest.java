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

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import se.vgregion.webbtidbok.generated.sectra.ArrayOfSection;
import se.vgregion.webbtidbok.generated.sectra.BookingInfo;
import se.vgregion.webbtidbok.generated.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.generated.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.generated.sectra.Section;
import se.vgregion.webbtidbok.generated.sectra.TimeBlock;

public class SectraBookingServiceTest{

	static SectraWebServiceHelperImpl helper = new SectraWebServiceHelperImpl();
	static String patientId = "1912121212";
	static String examinationNr = "SERTEST00012345";
	@Test
	public void testGetBookingInfo() throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {
		
		
		BookingInfoLocal bi = helper.getBookingInfo(patientId, examinationNr);
		TimeBlockLocal bt = bi.getBookedTime();
		SectionLocal section = bt.getSection();
		
	
		
		if (bi == null) {
			assertFalse(true);
		}
		
		if (bi.getExaminationNr() == examinationNr){
			assertTrue(true);
		}
		if (bi.getExamType() == "exam type mammografi"){
			assertTrue(true);
		}
		if (bi.getExamTypeCode() == "exam type code 123"){
			assertTrue(true);
		}
		
		if (bt.getId() == "timeBlockId_1"){
			assertTrue(true);
		}
		if (bt.getLength() == 10){
			assertTrue(true);
		}
		if (bt.getSection() == null){
			assertTrue(true);
		}
		if (bt.getStartTime() == null){
			assertTrue(true);
		}
		
		if (bi.getLaterality() == "S"){
			assertTrue(true);
		}
		if (bi.getPatientId() == patientId){
			System.out.println(bi.getPatientId() + " == " + patientId);
			assertTrue(true);
		}
		if (bi.getPatientName() == "Kerberos"){
			assertTrue(true);
		}
	}

	@Test
	public void testListSections() throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage {
		
		ArrayOfSectionLocal sectionArray = helper.listSections(examinationNr);
		List<SectionLocal> sectionList = sectionArray.getSectionLocalList();
		
		if(sectionArray == null){
			assertFalse(true);
		} else {
			assertFalse(false);
		}
		
		if(sectionArray.getSectionLocalList() == null){
			assertFalse(true);
		} else {
			assertFalse(false);
		}
		
		if(sectionList.size() < 1){
			System.out.println("sectionList.size(): " + sectionList.size() + ", should be no less than 2" );
			assertFalse(true);
		}
		
		if(sectionList.isEmpty()){
			assertFalse(true);
		}

		if(!sectionList.isEmpty()){
			
			SectionLocal section;
			Iterator<SectionLocal> iter = sectionList.iterator();
	
			for(int i = 1; iter.hasNext(); i++){

				section = iter.next();

				if(section.getSecId().equals("secId " + i)){
					assertTrue(true);
				} else {
					System.out.println("section.getId(): " + section.getSecId() + " != secId " + i);
					assertFalse(true);
				}
				
//				if(section.getSecAddress().equals("Vägen " + i)){
//					assertTrue(true);
//				} else {
//					System.out.println(section.getSecAddress() + " != " + "Vägen " + i);	
//					assertFalse(true);
//				}
				
				if(section.getSecDescription().equals("Section description " + i)){
					assertTrue(true);
				} else {
					System.out.println("else description: " + section.getSecDescription() + " != " + "Section description " + i);
					assertFalse(true);
				}
				
				if(section.getSecMail().equals("mottagningen" + i + "@test.se")){
					assertTrue(true);
				} else {
					System.out.println("section.getMail(): " + section.getSecMail() + " != mottagningen" + i + "@test.se");
					assertFalse(true);
				}
				
				if(section.getSecName().equals("Mottagningen " + i)){
					assertTrue(true);
				} else {
					System.out.println("section.getName(): " + section.getSecName() + " != " + "Mottagningen " + i);
					assertFalse(true);
				}
				
				if(section.getSecPhone().equals(Integer.toString(i))){
					assertTrue(true);
				} else {
					System.out.println("section.getPhone(): " + section.getSecPhone() + " != " + i);
					assertFalse(true);
				}
			}			
		}		
	}
}
