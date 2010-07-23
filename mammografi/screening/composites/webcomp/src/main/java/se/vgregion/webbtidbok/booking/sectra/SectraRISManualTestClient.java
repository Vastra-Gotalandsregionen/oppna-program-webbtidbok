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



import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;



public class SectraRISManualTestClient {
	
	static SectraBookingServiceImpl service = new SectraBookingServiceImpl();
//	static BookingInfo staticBi;
	static BookingInfoLocal staticBiL;
	
	public static void printGetBookingInfoResult(BookingInfoLocal inputBiL){
	
		if(inputBiL == null){
			
			staticBiL = service.getBookingInfo("1912121212", "SERTEST00012345");
			TimeBlockLocal tb = staticBiL.getBookedTime();
			System.out.println("TimeBlock.getId: " + tb.getId());
			tb.getSection();
			XMLGregorianCalendar xmlCal = tb.getStartTime();
			System.out.println("TimeBlock.getStartTime(): " + tb.getStartTime());
			System.out.println("TimeBlock.getLength() : " + tb.getLength());
			
		} 
		
		System.out.println("staticBi.getBookedTime(): " + staticBiL.getBookedTime().getStartTime().toString());

		System.out.println("-------");
		System.out.println("BookingInfo bi.getExaminationNr(): " + staticBiL.getExaminationNr());
		System.out.println("BookingInfo bi.getExamType(): " + staticBiL.getExamType());
		System.out.println("BookingInfo bi.getExamTypeCode()" + staticBiL.getExamType());
		System.out.println("BookingInfo bi.getLaterality(): " + staticBiL.getLaterality());
		System.out.println("BookingInfo bi.getPatientId(): " + staticBiL.getPatientId());
		System.out.println("BookingInfo bi.getPatientName(): " + staticBiL.getPatientName());
		System.out.println("-------");
		
	}
	
	public static void printListSections(){
		
		ArrayOfSectionLocal sectionArrayL = service.listSections("SERTEST00012345");

		List<SectionLocal> sectionList = sectionArrayL.getSectionLocalList();
		
		Iterator<SectionLocal> iter = sectionList.iterator();
		
		while(iter.hasNext()){
			SectionLocal iterSection = iter.next();
			System.out.println("iterSection.getName() :" + iterSection.getSecName());
			System.out.println("iterSection.getAddress() :" + iterSection.getSecAddress());
			System.out.println("iterSection.getDescription() :" + iterSection.getSecDescription());
			System.out.println("iterSection.getId() :" + iterSection.getSecId());
			System.out.println("iterSection.getMail() :" + iterSection.getSecMail());			
			System.out.println("iterSection.getPhone() :" + iterSection.getSecPhone());
			System.out.println("-------");
		}
		
	}
	
	public static void printReschedule() throws DatatypeConfigurationException{
	
		DatatypeFactory df = DatatypeFactory.newInstance();
		XMLGregorianCalendar xmlGregCal = df.newXMLGregorianCalendar();
		xmlGregCal.setYear(2011);
		xmlGregCal.setMonth(11);
		xmlGregCal.setDay(11);
		xmlGregCal.setHour(11);
		xmlGregCal.setMinute(11);
		xmlGregCal.setSecond(11);
		boolean printNewNotice = false;
		String rescheduleComment = "reschedule comment.";
		
		System.out.println("xmlGregCal:" +xmlGregCal.toString());
		//HOW are the setters for this? doesn't seem to take uh?
		staticBiL = service.reschedule("SERTEST00012345", "newTimeId_1", xmlGregCal, printNewNotice, rescheduleComment);
		TimeBlockLocal tb = staticBiL.getBookedTime();
		//check that what goes in comes back out again
		System.out.println("printing sttartTime after reschedule call: " + tb.getStartTime().toString());
		printGetBookingInfoResult(staticBiL);

	}
	
	public static void login(String patientId, String password){
		
		boolean isLoggedIn = service.login(patientId, password);
		
		System.out.println("Login.isLoggedIn: " + isLoggedIn);
	}
	
	public static void main (String[] args) throws DatatypeConfigurationException{
		
		login("1912121212", "SEMSUSpugh");

		printGetBookingInfoResult(staticBiL);
//		
		printListSections();
//		
		printReschedule();
		
	}

}

//public static void printGetBookingInfoResult(BookingInfo... inputBi){
//
//	
//	BookingInfo bi = service.getBookingInfo("1912121212", "SERTEST00012345");
//	System.out.println("bi.getBookedTime(): " + bi.getBookedTime());
//	TimeBlock tb = bi.getBookedTime();
//	
//	System.out.println("TimeBlock.getId: " + tb.getId());
//	tb.getSection();
//	XMLGregorianCalendar xmlCal = tb.getStartTime();
//	System.out.println("TimeBlock.getStartTime(): " + tb.getStartTime());
//	System.out.println("TimeBlock.getLength() : " + tb.getLength());
//	System.out.println("-------");
//	System.out.println("BookingInfo bi.getExamNo(): " + bi.getExamNo());
//	System.out.println("BookingInfo bi.getExamType(): " + bi.getExamType());
//	System.out.println("BookingInfo bi.getExamTypeCode()" + bi.getExamTypeCode());
//	System.out.println("BookingInfo bi.getLaterality(): " + bi.getLaterality());
//	System.out.println("BookingInfo bi.getPatientId(): " + bi.getPatientId());
//	System.out.println("BookingInfo bi.getPatientName(): " + bi.getPatientName());
//	System.out.println("-------");
//	
//}
//
//public static void printListSections(){
//	
//	ArrayOfSection sectionArray = service.listSections("SERTEST00012345");
//
//	List<Section> sectionList = sectionArray.getSection();
//	
//	Iterator<Section> iter = sectionList.iterator();
//	
//	while(iter.hasNext()){
//		Section iterSection = iter.next();
//		System.out.println("iterSection.getName() :" + iterSection.getName());
//		System.out.println("iterSection.getAddress() :" + iterSection.getAddress());
//		System.out.println("iterSection.getDescription() :" + iterSection.getDescription());
//		System.out.println("iterSection.getId() :" + iterSection.getId());
//		System.out.println("iterSection.getMail() :" + iterSection.getMail());			
//		System.out.println("iterSection.getPhone() :" + iterSection.getPhone());
//		System.out.println("-------");
//	}
//	
//}
//
//public static void printReschedule() throws DatatypeConfigurationException{
//	Calendar cal = Calendar.getInstance();
//	DatatypeFactory df = DatatypeFactory.newInstance();
//	XMLGregorianCalendar xmlGregCal = df.newXMLGregorianCalendar();
//	xmlGregCal.setYear(2011);
//	xmlGregCal.setMonth(11);
//	xmlGregCal.setDay(11);
//	xmlGregCal.setHour(11);
//	xmlGregCal.setMinute(11);
//	xmlGregCal.setSecond(11);
//	boolean printNewNotice = false;
//	String rescheduleComment = "reschedule comment.";
//	BookingInfo bi = service.reschedule("SERTEST00012345", "newTimeId_1", xmlGregCal, printNewNotice, rescheduleComment);
//	
//	//check that what goes in comes back out again
//	System.out.println("printing after reschedule call: ");
//	printGetBookingInfoResult(bi);
//
//}
