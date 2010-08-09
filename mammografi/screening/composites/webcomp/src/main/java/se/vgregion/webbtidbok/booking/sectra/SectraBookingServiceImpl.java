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

import se.vgregion.webbtidbok.State;

import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleRescheduleErrorInfoFaultFaultMessage;

public class SectraBookingServiceImpl implements SectraBookingServiceInterface{

	String displayName;
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	private SectraWebServiceHelperImpl helper;

	public void setHelper(SectraWebServiceHelperImpl helper) {
		this.helper = helper;
	}
		
	public SectraWebServiceHelperImpl getHelper() {
		return helper;
	}
	public BookingInfoLocal getBookingInfo(State state){
		System.out.println("### BookingInfoLocal");
		String patientId = state.getPnr();
		String examinationNo = state.getPasswd();
		System.out.println("### SectraBookingServiceImpl.getBookingInfo(State)");
		BookingInfoLocal biL = null;
		
		try {
			System.out.println("### before bi 1 = helper.getBookingInfo()");
			biL = helper.getBookingInfo(patientId, examinationNo);
			setDisplayName(biL.getPatientName());
			System.out.println("### bi shouldn't be null: biL.getPatientId() " + biL.getPatientId() + ", state.getPnr(): " + state.getPnr() + ", state.getPasswd():" + state.getPasswd() + ", biL.getPatientName(): " + biL.getPatientName());
		} catch (IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage e) {
			System.out.println("### something 1 went wrong in getBookingInfo(state): " + e.getMessage());
			e.printStackTrace();
		}
		return biL;
	}

	
	@Override
	public BookingInfoLocal getBookingInfo(String patientId, String examinationNo){

		BookingInfoLocal biL = null;
		try {
			
			System.out.println("### before biL = helper.getBookingInfo()");
			biL = helper.getBookingInfo(patientId, examinationNo);
			System.out.println("### biL shouldn't be null" + biL.getPatientId());
			
		} catch (IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage e) {
			System.out.println("### Something went wrong in SectraBookingService.getBookingInfo(...)." + e.getMessage());
			e.printStackTrace();
		}
		
		
		return biL;
	}
	
	public boolean login(String patientId, String password){
		
		boolean isLoggedIn = helper.login(patientId, password);
		return isLoggedIn;
	}
	
	public ArrayOfSectionLocal listSections(String examinationNr){

		ArrayOfSectionLocal sectionArrayL = null;
		try {
			
			sectionArrayL = helper.listSections(examinationNr);
		
		} catch (IRisRescheduleListSectionsErrorInfoFaultFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sectionArrayL;
	}

	@Override
	public BookingInfoLocal reschedule(String examinationNr, String newTimeId,
			XMLGregorianCalendar startTime, Boolean printNewNotice,
			String rescheduleComment) {
		
		BookingInfoLocal biL = null;

		
		try {
		
			biL = helper.reschedule(examinationNr, newTimeId, startTime, printNewNotice, rescheduleComment);
		
		} catch (IRisRescheduleRescheduleErrorInfoFaultFaultMessage e) {
			e.printStackTrace();
		}
		
		return biL;
	}
	
	
}
