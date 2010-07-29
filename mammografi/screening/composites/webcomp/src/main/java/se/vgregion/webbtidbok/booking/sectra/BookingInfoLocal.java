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

import java.io.Serializable;

import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.TimeBlock;


/*
 * Local representation of BookinInfo, the back end one doesn't implement java.io.Serializable 
 * 
 */
public class BookingInfoLocal implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3847375663677494268L;
	private String patientName;
	private String patientId;
	private String examType;
	private String examTypeCode;
	private String laterality;
	private String examinationNr;
	private TimeBlockLocal bookedTime;
	
	public BookingInfoLocal (BookingInfo bi){
		setPatientName(bi.getPatientName().getValue());
		setPatientId(bi.getPatientId().getValue());
		setExamTypeCode(bi.getExamTypeCode().getValue());
		setExamType(bi.getExamType().getValue());
		setLaterality(bi.getLaterality().getValue());
		setExaminationNr(bi.getExamNo().getValue());
		this.bookedTime = getBookedTimeLocal(bi.getBookedTime().getValue());
	}
	
	public BookingInfoLocal() {
		// TODO Auto-generated constructor stub
	}
	
	public TimeBlockLocal getBookedTimeLocal(TimeBlock tb){
		TimeBlockLocal tbL = new TimeBlockLocal(tb);
		return tbL;
	}
	public String getExamTypeCode() {
		return examTypeCode;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}
	
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamTypeCode(String examTypeCode) {
		this.examTypeCode = examTypeCode;
	}

//	public String getExamNo() {
//		return examNo;
//	}
//
//	public void setExamNo(String examCode) {
//		this.examNo = examCode;
//	}

	public String getLaterality() {
		return laterality;
	}

	public void setLaterality(String laterality) {
		this.laterality = laterality;
	}

	public String getExaminationNr() {
		return examinationNr;
	}

	public void setExaminationNr(String examinationNr) {
		this.examinationNr = examinationNr;
	}

	public TimeBlockLocal getBookedTime() {
		return bookedTime;
	}

	public void setBookedTime(TimeBlock bookedTime) {
		System.out.println("### bookedTime.getId(): " + bookedTime.getId());
		String cracker = bookedTime.getId().getValue();
		this.bookedTime.setId(cracker);
//		this.bookedTime.setLength(bookedTime.getLength());
//		this.bookedTime.setSection(bookedTime.getSection());
//		this.bookedTime.setStartTime(bookedTime.getStartTime());
	}


}
