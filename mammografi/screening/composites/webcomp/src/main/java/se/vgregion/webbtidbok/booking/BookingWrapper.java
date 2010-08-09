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
package se.vgregion.webbtidbok.booking;

import java.io.Serializable;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.elvis.BookingResponseLocal;
import se.vgregion.webbtidbok.booking.sectra.BookingInfoLocal;
import se.vgregion.webbtidbok.booking.sectra.SectionLocal;
import se.vgregion.webbtidbok.booking.sectra.SectraBookingFacadeImpl;
import se.vgregion.webbtidbok.booking.sectra.TimeBlockLocal;
import se.vgregion.webbtidbok.ws.sectra.TimeBlock;
import se.vgregion.webbtidbok.ws.BookingResponse;

/**
 * Wrap elvis and sectra specific booking classes in order to generalize their
 * return. This would enable the BookingFacade to represent both of them and to
 * in turn be utilized by the presentation layer.
 * 
 * @author carstm
 * 
 */

// adapt these var names as needed to fit existing xhtml code and vars there in,
// example: name changed from name to displayName
public class BookingWrapper implements BookingFacade, Serializable {

	private String address;
	private int antOmbok;
	private XMLGregorianCalendar bokadTid;
	private int ctid;
	private String epost;
	private String extId;
	private int maxAntOmbok;
	private String mobil;
	private String mottagning;
	private String displayName;
	private String pnr;
	private String vardgivare;
	private TimeBlockLocal tbl;
	private SectionLocal secl;
	private String examNo;
	private String examType;
	private String examTypeCode;
	private String laterality;
	private String description;
	private String phone;
	private String secId;
	private String timeBlockId;
	private int timeBlockLength;
	private SectraBookingFacadeImpl facade;

	public void setFacade(SectraBookingFacadeImpl facade) {
		this.facade = facade;
	}

	public BookingWrapper() {
		System.out.println("### BookingWrapper() constructor");
	}

	// TODO: This class should be backend agnostic. The backends should create
	// instances
	// of this class filled with the proper content.

	// Sectra specific values
	public BookingWrapper(BookingInfoLocal bl) {
		System.out.println("### BookingWrapper(BookingInfoLocal) constructor");

		this.displayName = bl.getPatientName();

		tbl = bl.getBookedTime();
		this.timeBlockId = tbl.getId();
		this.timeBlockLength = tbl.getLength();
		this.bokadTid = tbl.getStartTime();
		// secl = tbl.getSection();
		// this.address = secl.getSecAddress();
		// this.description = secl.getSecDescription();
		// this.epost = secl.getSecMail();
		// this.displayName = secl.getSecName();
		// this.phone = secl.getSecPhone();
		// this.secId = secl.getSecId();
		// this.examNo = bl.getExaminationNr();
		// this.examType = bl.getExamType();
		// this.examTypeCode = bl.getExamTypeCode();
		// this.laterality = bl.getLaterality();
		// this.pnr = bl.getPatientId();

	}

	// Elvis specifik values
	public BookingWrapper(BookingResponse br) {
		System.out.println("### BookingWrapper(BookingResponse) constructor");
		this.address = br.getAddress().toString();
		this.maxAntOmbok = br.getAntalOmbok();
		this.bokadTid = br.getBokadTid();
		this.ctid = br.getCentralTidbokID();
		this.epost = br.getEpost().toString();
		this.extId = br.getExternalID().toString();
		this.maxAntOmbok = br.getMaxAntalOmbok();
		this.mobil = br.getMobilTel().toString();
		this.mottagning = br.getMottagning().toString();
		this.displayName = br.getNamn().toString();
		this.pnr = br.getPnr().toString();
		this.vardgivare = br.getVardgivare().toString();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAntOmbok() {
		return antOmbok;
	}

	public void setAntOmbok(int antOmbok) {
		this.antOmbok = antOmbok;
	}

	public XMLGregorianCalendar getBokadTid() {
		return bokadTid;
	}

	public void setBokadTid(XMLGregorianCalendar bokadTid) {
		this.bokadTid = bokadTid;
	}

	public int getCtid() {
		return ctid;
	}

	public void setCtid(int ctid) {
		this.ctid = ctid;
	}

	public String getEpost() {
		return epost;
	}

	public void setEpost(String epost) {
		this.epost = epost;
	}

	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	public int getMaxAntOmbok() {
		return maxAntOmbok;
	}

	public void setMaxAntOmbok(int maxAntOmbok) {
		this.maxAntOmbok = maxAntOmbok;
	}

	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
	}

	public String getMottagning() {
		return mottagning;
	}

	public void setMottagning(String mottagning) {
		this.mottagning = mottagning;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String namn) {
		this.displayName = namn;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getVardgivare() {
		return vardgivare;
	}

	public void setVardgivare(String vardgivare) {
		this.vardgivare = vardgivare;
	}

	public TimeBlockLocal getTbl() {
		return tbl;
	}

	public void setTbl(TimeBlockLocal tbl) {
		this.tbl = tbl;
	}

	public SectionLocal getSecl() {
		return secl;
	}

	public void setSecl(SectionLocal secl) {
		this.secl = secl;
	}

	public String getExamNo() {
		return examNo;
	}

	public void setExamNo(String examNo) {
		this.examNo = examNo;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getExamTypeCode() {
		return examTypeCode;
	}

	public void setExamTypeCode(String examTypeCode) {
		this.examTypeCode = examTypeCode;
	}

	public String getLaterality() {
		return laterality;
	}

	public void setLaterality(String laterality) {
		this.laterality = laterality;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}

	public String getTimeBlockId() {
		return timeBlockId;
	}

	public void setTimeBlockId(String timeBlockId) {
		this.timeBlockId = timeBlockId;
	}

	public int getTimeBlockLength() {
		return timeBlockLength;
	}

	public void setTimeBlockLength(int timeBlockLength) {
		this.timeBlockLength = timeBlockLength;
	}

	@Override
	public BookingWrapper getBookingInfo(State state) {
		if (state.getService().equals("MAMMO_SU")) {
			System.out.println("### getService.equals(\"MAMMO_SU\")");
			BookingWrapper wrapper = new BookingWrapper();
			System.out
					.println("### BookingWrapper.getBookingInfo(state). State pnr & passw: "
							+ state.getPnr() + ", " + state.getPasswd());
			wrapper = facade.getBookingInfo(state);
			System.out
					.println("### returning wrapper after getBookingInfo call");
			return wrapper;
		}
		if (state.getService().equals("MAMMO_NU")) {
			// TODO: add logic here
		}
		if (state.getService().equals("BUKAORTA")) {
			// TODO: add logic here
		}
		return null;
	}

	@Override
	public boolean login(State state) {
		// TODO Auto-generated method stub
		return false;
	}

}