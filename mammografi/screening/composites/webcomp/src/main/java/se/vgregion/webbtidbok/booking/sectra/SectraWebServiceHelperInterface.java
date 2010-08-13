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

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleRescheduleErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.Section;

public interface SectraWebServiceHelperInterface {

	abstract public Booking getBookingInfo(String patientId, String examinationNr) throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
	abstract public ArrayOfSectionLocal listSections(String examinationNr) throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage;
	abstract public Booking reschedule(String examinationNr, String newTimeId, XMLGregorianCalendar startTime, Boolean printNewNotice, String rescheduleComment) throws IRisRescheduleRescheduleErrorInfoFaultFaultMessage;
	boolean login(State state);
	//List<Section> getSectionForExaminationNbr(String examinationNbr);
}
