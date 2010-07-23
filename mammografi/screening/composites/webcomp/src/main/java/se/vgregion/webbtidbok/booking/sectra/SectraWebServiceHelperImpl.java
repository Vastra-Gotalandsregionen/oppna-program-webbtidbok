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

import javax.xml.datatype.XMLGregorianCalendar;
import se.vgregion.webbtidbok.generated.sectra.BookingInfo;
import se.vgregion.webbtidbok.generated.sectra.IRisReschedule;
import se.vgregion.webbtidbok.generated.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.generated.sectra.IRisRescheduleImplService;
import se.vgregion.webbtidbok.generated.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.generated.sectra.IRisRescheduleRescheduleErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.generated.sectra.TimeBlock;

/*
 * This class was supposed to build request using an objectfactory, seemsl ike it ain't needed though?
 * since IRisReschedule.java seems to declare it doesn't need have JXBEelement params set in a request to respond to calls.
 * 	//objectfactory... creata ws request här fyll med JAXB-params (pat.id, examination.nbr.)
 */
public class SectraWebServiceHelperImpl implements SectraWebServiceHelperInterface{

	IRisRescheduleImplService theService = new IRisRescheduleImplService();
    IRisReschedule thePort = theService.getIRisReschedulePort();
    
	public BookingInfoLocal getBookingInfo(String patientId, String examinationNr)
			throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {

		BookingInfoLocal biL;
		BookingInfo bi = thePort.getBookingInfo(patientId, examinationNr);
		TimeBlock tb = bi.getBookedTime();
		System.out.println("###.... tb." + tb.getStartTime().toString());
		biL = new BookingInfoLocal(thePort.getBookingInfo(patientId, examinationNr));

		return biL;
	}
//TODO fix some crap here to simulate login call
	public boolean login(String patientId, String password){
		
		Boolean isLoggedIn = thePort.login(patientId, password);
		System.out.println("SectraWebServiceHelperImpl.isLoggedIn: " + isLoggedIn);
		return isLoggedIn;
	}
//	public ArrayOfdateTime listFreeDays(XMLGregorianCalendar startDate,
//			XMLGregorianCalendar endDate, String examinationNr, String sectionId)
//			throws IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public ArrayOfTimeBlock listFreeTimes(XMLGregorianCalendar startDate,
//			XMLGregorianCalendar endDate, String examinationNr, String sectionId)
//			throws IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
	public ArrayOfSectionLocal listSections(String examinationNr)
			throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage {
		
		ArrayOfSectionLocal sectionArrayL = new ArrayOfSectionLocal(thePort.listSections(examinationNr));
		
		return sectionArrayL;
	}


	public BookingInfoLocal reschedule(String examinationNr, String newTimeId,
			XMLGregorianCalendar startTime, Boolean printNewNotice,
			String rescheduleComment) throws IRisRescheduleRescheduleErrorInfoFaultFaultMessage {
		System.out.println("startTime in SectraWebServiceHelper.reeschedule(): " + startTime.toString());
		BookingInfoLocal biL = new BookingInfoLocal(thePort.reschedule(examinationNr, newTimeId, startTime, printNewNotice, rescheduleComment));
		TimeBlockLocal tb = biL.getBookedTime();
		System.out.println("### startTime after thePort.reschedule() call: " + tb.getStartTime().toString());
		return biL;
	}
//
//
//	public ArrayOfBookingInfo getBookings(String patientId)
//			throws IRisRescheduleGetBookingsErrorInfoFaultFaultMessage {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
