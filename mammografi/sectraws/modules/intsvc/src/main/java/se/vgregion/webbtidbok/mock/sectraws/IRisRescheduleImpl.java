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
package se.vgregion.webbtidbok.mock.sectraws;

import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

//import se.vgregion.webbtidbok.mock.sectraws.jaxws.*;


//@WebService(name = "IRisReschedule",
//            serviceName = "RisReschedule",
//            targetNamespace = "http://tempuri.org/",
//            endpointInterface="se.vgregion.webbtidbok.mock.sectraws.jaxws.IRisReschedule")
public class IRisRescheduleImpl /* implements IRisReschedule */ {

//	private final ObjectFactory objectFactory = new ObjectFactory();
//	
//	
//	
//	protected XMLGregorianCalendar setXMLGregorianCalendar(XMLGregorianCalendar inXmlGregCal){
//		
//		DatatypeFactory dtf = null;
//		XMLGregorianCalendar xmlGregCal = inXmlGregCal;
//		
//		//This is the default value when mocking a back end BookingInfo object: 20101010T10:10:10
//		//on reschedule(....) startTime should change to 20111111T11:11:11
//		//TODO create BookingInfo class like BookingTime
//		if (inXmlGregCal == null) {		
//			
//			try {
//				
//				dtf = DatatypeFactory.newInstance();
//				
//			} catch (DatatypeConfigurationException e) {
//				e.printStackTrace();
//			} 
//			xmlGregCal = dtf.newXMLGregorianCalendar();
//			
//			xmlGregCal.setYear(2010);
//			xmlGregCal.setMonth(10);		
//			xmlGregCal.setDay(10);
//			xmlGregCal.setHour(10);
//			xmlGregCal.setMinute(10);
//			xmlGregCal.setSecond(10);
//		} 
//		
//		return xmlGregCal;
//	}
//	
//	protected Section getSection(){
//		
//		Section section = objectFactory.createSection();
//		
//		section.setId(objectFactory.createSectionId("secId 123"));
//		section.setAddress(objectFactory.createSectionAddress("Vägen 1"));
//		section.setDescription(objectFactory.createSectionDescription("Section description xyz"));
//		section.setMail(objectFactory.createSectionMail("mottagningen_1@1234_.se"));
//		section.setName(objectFactory.createSectionName("Mottagningen 1"));
//		
//		return section;
//	}
//	
//	protected TimeBlock setTimeBlock(XMLGregorianCalendar xmlGregCal){
//		
//		TimeBlock timeBlock = objectFactory.createTimeBlock();
//		
//		JAXBElement<String> timeBlockId = objectFactory.createTimeBlockId("timeBlockId_1");
//		
//		timeBlock.setId(timeBlockId);
//		timeBlock.setLength(10);
//		timeBlock.setSection(objectFactory.createSection(getSection()));	
//		timeBlock.setStartTime(setXMLGregorianCalendar(xmlGregCal));
//	
//		return timeBlock;
//	}
//	
//	
///*
// * 
// * start IRisSchedule interface implementations (mocking)
// * 
// */
//	@Override
//	public BookingInfo getBookingInfo(String patientId, String examinationNr)
//			throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {
//		IRisRescheduleValidators validator = new IRisRescheduleValidators();
//		XMLGregorianCalendar xmlGregCal = null;
//		BookingInfo bi = new BookingInfo();	
//		
//		if(validator.validateGetBookingInfoCall(patientId, examinationNr)){
//	
//			//booking info stuff
//			JAXBElement<String> patientName1 = objectFactory.createBookingInfoPatientName("Kerberos");
//			JAXBElement<String> patientId1 = objectFactory.createBookingInfoPatientId("1912121212");
//			JAXBElement<String> examType1 = objectFactory.createBookingInfoExamType("exam type mammografi");
//			JAXBElement<String> examCode1 = objectFactory.createBookingInfoExamTypeCode("exam type code 123");
//			JAXBElement<String> laterality1 = objectFactory.createBookingInfoLaterality("S");		
//			JAXBElement<TimeBlock> timeBlock1 = objectFactory.createBookingInfoBookedTime(setTimeBlock(xmlGregCal));
//			JAXBElement<String> examinationNr1 = objectFactory.createBookingInfoExamNo("SERTEST00012345");
//			
//			//package booking info stuff into a BookingInfo object
//			bi.setPatientName(patientName1);
//			bi.setPatientId(patientId1);	
//			bi.setExamType(examType1);
//			bi.setExamTypeCode(examCode1);
//			bi.setLaterality(laterality1);
//			bi.setBookedTime(timeBlock1);	
//			bi.setExamNo(examinationNr1);
//		}
//		
//		return bi;
//	}
//
//	@Override
//	public ArrayOfBookingInfo getBookings(String patientId)
//			throws IRisRescheduleGetBookingsErrorInfoFaultFaultMessage {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ArrayOfdateTime listFreeDays(XMLGregorianCalendar startDate,
//			XMLGregorianCalendar endDate, String examinationNr, String sectionId)
//			throws IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ArrayOfTimeBlock listFreeTimes(XMLGregorianCalendar startDate,
//			XMLGregorianCalendar endDate, String examinationNr, String sectionId)
//			throws IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	/**
//	 *input parameter String examinationNr doesn't really seem to do anything.
//	 */
//	public ArrayOfSection listSections(String examinationNr)
//			throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage {
//		IRisRescheduleValidators validator = new IRisRescheduleValidators();
//		ArrayOfSection sectionArray = objectFactory.createArrayOfSection();
//		
//		if(validator.validateListSectionsCall(examinationNr)){
//			Section section1 = objectFactory.createSection();
//			section1.setId(objectFactory.createSectionId("secId 1"));
//			section1.setAddress(objectFactory.createSectionAddress("Vägen 1"));
//			section1.setDescription(objectFactory.createSectionDescription("Section description 1"));
//			section1.setMail(objectFactory.createSectionMail("mottagningen1@test.se"));
//			section1.setName(objectFactory.createSectionName("Mottagningen 1"));
//			section1.setPhone(objectFactory.createSectionPhone("1"));
//			sectionArray.getSection().add(section1);
//			
//			Section section2 = objectFactory.createSection();
//			section2.setId(objectFactory.createSectionId("secId 2"));
//			section2.setAddress(objectFactory.createSectionAddress("Vägen 2"));
//			section2.setDescription(objectFactory.createSectionDescription("Section description 2"));
//			section2.setMail(objectFactory.createSectionMail("mottagningen2@test.se"));
//			section2.setName(objectFactory.createSectionName("Mottagningen 2"));
//			section2.setPhone(objectFactory.createSectionPhone("2"));
//			sectionArray.getSection().add(section2);
//		}
//		return sectionArray;
//	}
//
//	@Override
//	public BookingInfo reschedule(String examinationNr, String newTimeId,
//			XMLGregorianCalendar startTime, Boolean printNewNotice,
//			String rescheduleComment)
//			throws IRisRescheduleRescheduleErrorInfoFaultFaultMessage {
//		
//		BookingInfo bi = objectFactory.createBookingInfo();
////		set all new input params in the BookingInfo object bi and return
//		//start with creating a new TimeBlock and set it into the BI
//		JAXBElement<TimeBlock> newTime = objectFactory.createBookingInfoBookedTime(setTimeBlock(startTime));
//		
//		bi.setBookedTime(newTime);
//		
//		return bi;
//	}

	//@Override
	//there is no method supporting a log in call so I just made one up here
	//password is assumed to be 10 positions in length, examinationNr is 15 pos in length
	//to get a booking to display we need the examinationNr, according to SectraRIS WS manual
	//how do we provide that or is it the same as password that for SU which starts with SEMSUS???
//	public boolean login(String patientId, String password){
//		IRisRescheduleValidators validator = new IRisRescheduleValidators();
//		boolean isLoggedIn = validator.validateMockLoginCall(patientId, password);
//		
//		return isLoggedIn;
//	}

}


/*
 * Taken from example server code stubs... complements the Interface Specification
 */
//public class RISReschedule:IRisReschedule  {
//    
//	public BookingInfo GetBookingInfo(string patientId, string examinationNr) {
//        if (examinationNr.Length <= 1) {
//            ThrowServiceException(new IlligalExamNrException("Illigal examination nr"));
//        }
//        
//        return GetBookingInfo(DateTime.Parse("2007-12-24 08:00"),"Mammografi","ABC123");
//	}
//    
//    public List<BookingInfo> GetBookings(string patientId) {
//        List<BookingInfo> retval = new List<BookingInfo>();
//        retval.Add(GetBookingInfo(DateTime.Parse("2007-12-24 08:00"), "Mammografi","TIME123"));
//        retval.Add(GetBookingInfo(DateTime.Parse("2007-12-25 08:00"), "Lårben", "TIME124"));
//        return retval;
//    }
//
//    public List<Section> ListSections(string examinationNr) {
//        List<Section> retval = new List<Section>();
//        Section sec = new Section();
//        sec.Id = "123";
//        sec.Address  = "Testgatan 3";
//        sec.Mail = "test@hotmail.com";
//        sec.Name = "Testsektionen";
//        sec.Phone = "019-121314";
//        retval.Add(sec);
//        sec = new Section();
//        sec.Id = "SEC123";
//        sec.Mail = "drhibbert@springfield.com";
//        sec.Name = "Dr. Hibbert Mammography department";
//        sec.Phone = "555-121212";
//        sec.Address = "Springfield road 12";
//        retval.Add(sec);
//        return retval;
//    }
//
//    
//    public List<TimeBlock> ListFreeTimes(DateTime startDate, DateTime endDate, string examinationNr, string sectionId) {
//        
//        List<TimeBlock> retval = new List<TimeBlock>();
//        retval.Add(GetFreeTime(DateTime.Parse("2007-12-24 08:00"), "TIME01"));
//        retval.Add(GetFreeTime(DateTime.Parse("2007-12-24 08:10"), "TIME021"));
//        retval.Add(GetFreeTime(DateTime.Parse("2007-12-24 08:10"), "TIME031"));
//        retval.Add(GetFreeTime(DateTime.Parse("2007-12-25 08:00"), "TIME032"));
//        retval.Add(GetFreeTime(DateTime.Parse("2007-12-25 08:00"), "TIME033"));
//        return retval;
//    }
//
//        
//    public List<DateTime> ListFreeDays(DateTime startDate, DateTime endDate, string examinationNr, string sectionId) {
//        
//        List<DateTime> retval = new List<DateTime>();
//        retval.Add(DateTime.Parse("2007-12-24"));
//        retval.Add(DateTime.Parse("2007-12-25"));
//        return retval;
//    }
//
//        
//    public BookingInfo Reschedule(string examinationNr, string newTimeId,DateTime startTime,bool printNewNotice,string rescheduleComment) {
//        return GetBookingInfo(DateTime.Parse("2007-12-25 08:00"), "Mammografi", "TIME124");  
//    }
//
//
//    private TimeBlock GetFreeTime(DateTime date, string id) {
//        TimeBlock retval = new TimeBlock();
//        retval.Id = id;
//        retval.StartTime = date;
//        Section sec = new Section();
//        sec.Id = "123";
//        sec.Address = "Testgatan 3";
//        sec.Mail = "test@hotmail.com";
//        sec.Name = "Testsektionen";
//        sec.Phone = "019-121314";
//        retval.Section = sec;
//        retval.Length = 10;
//        return retval;
//    }
//
//
//    private BookingInfo GetBookingInfo(DateTime date,string Examtype,string TimeId) {
//        BookingInfo retval = new BookingInfo();
//        TimeBlock time = new TimeBlock();
//        Section section = new Section();
//
//        time.Id = TimeId;
//        time.Length = 10;
//        time.StartTime = date;
//        section.Id = "SEC123";
//        section.Mail = "drhibbert@springfield.com";
//        section.Name  = "Dr. Hibbert Mammography department";
//        section.Phone  = "555-121212";
//        section.Address = "Springfield road 12";
//        time.Section = section;
//        retval.BookedTime = time;
//        
//        retval.ExamType = Examtype;
//        retval.ExamTypeCode = "12345";
//        retval.Laterality = "S";
//        retval.PatientId  = "195512121212";
//        retval.PatientName = "Marge Simpson";
//    
//        return retval;
//    }
//
//    private void ThrowServiceException(Exception ex) {
//
//        
//        //if (ex is ApplicationException) {
//        //}
//
//
//
//        //If no other type of errror. Throw unknown error occured
//        throw new FaultException<ErrorInfo>(new ErrorInfo(ErrorInfo.ErrorCodeEnum.Unknown),new FaultReason(ex.Message),new FaultCode("Sender"));
//    }
//
//    /// <summary>
//    /// Do the actuall throw of exception from service
//    /// </summary>
//    /// <param name="errorInfo"></param>
//    /// <param name="message"></param>
//    private void DoThrow(ErrorInfo errorInfo,string message) {
//        throw new FaultException<ErrorInfo>(errorInfo, new FaultReason(message), new FaultCode("Sender"));
//    }
//
//}
//
//
//public class IlligalExamNrException : ApplicationException {
//    public IlligalExamNrException(string desc) :base(desc){
//    }
//}