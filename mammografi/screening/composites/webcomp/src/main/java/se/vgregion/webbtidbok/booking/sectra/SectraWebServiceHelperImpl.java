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

import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.IRisReschedule;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleRescheduleErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.RISReschedule;
import se.vgregion.webbtidbok.ws.sectra.TimeBlock;

/*
 * This class was supposed to build request using an objectfactory, seemsl ike it ain't needed though?
 * since IRisReschedule.java seems to declare it doesn't need have JXBEelement params set in a request to respond to calls.
 * 	//objectfactory... creata ws request här fyll med JAXB-params (pat.id, examination.nbr.)
 */
public class SectraWebServiceHelperImpl implements SectraWebServiceHelperInterface{

    private IRisReschedule thePortSU;
    private IRisReschedule thePortNU;
    

	public void setThePortSU(IRisReschedule thePortSU) {
		this.thePortSU = thePortSU;
	}
	public void setThePortNU(IRisReschedule thePortNU) {
		this.thePortNU = thePortNU;
	}
	
	public BookingInfoLocal getBookingInfo(String patientId, String examinationNr)
			throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {

		BookingInfoLocal biL;
		BookingInfo bi = thePortSU.getBookingInfo(patientId, examinationNr);
		TimeBlock tb = bi.getBookedTime().getValue();
		System.out.println("###.... tb." + tb.getStartTime().toString());
		biL = new BookingInfoLocal(bi);

		return biL;
	}
//TODO fix some crap here to simulate login call
	public boolean login(String patientId, String password){
		
		try {
		    thePortSU.getBookingInfo(patientId, password);
		    return true;
		} catch (IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage e) {
            return false;
        }
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
		
		ArrayOfSectionLocal sectionArrayL = new ArrayOfSectionLocal(thePortSU.listSections(examinationNr));
		
		return sectionArrayL;
	}


	public BookingInfoLocal reschedule(String examinationNr, String newTimeId,
			XMLGregorianCalendar startTime, Boolean printNewNotice,
			String rescheduleComment) throws IRisRescheduleRescheduleErrorInfoFaultFaultMessage {
		System.out.println("startTime in SectraWebServiceHelper.reeschedule(): " + startTime.toString());
		BookingInfoLocal biL = new BookingInfoLocal(thePortSU.reschedule(examinationNr, newTimeId, startTime, printNewNotice, rescheduleComment));
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
