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

import javax.xml.bind.JAXBElement;

import se.vgregion.webbtidbok.ws.sectra.Section;

public class SectionLocal implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1700221184799679025L;
	private String secId;
	private String secName;
	private String secDescription;
	private String secAddress;
	private String secPhone;
	private String secMail;
	
	public SectionLocal(Section sec){
		this.secId = getValueOrNull(sec.getId());
		this.secName = getValueOrNull(sec.getName());
		this.secDescription = getValueOrNull(sec.getDescription());
		this.secAddress = getValueOrNull(sec.getAddress());
		this.secPhone = getValueOrNull(sec.getPhone());
		this.secMail = getValueOrNull(sec.getMail());
	}
	
	// TODO: This should be an utility method
	private <T> T getValueOrNull(JAXBElement<T> elem) {
	    return elem != null ? elem.getValue() : null; 
	}
	
	
	public SectionLocal() {
		// TODO Auto-generated constructor stub
	}


	public String getSecId() {
		return secId;
	}
	public void setSecId(String secId) {
		this.secId = secId;
	}
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public String getSecDescription() {
		return secDescription;
	}
	public void setSecDescription(String secDescription) {
		this.secDescription = secDescription;
	}
	public String getSecAddress() {
		return secAddress;
	}
	public void setSecAddress(String secAddress) {
		this.secAddress = secAddress;
	}
	public String getSecPhone() {
		return secPhone;
	}
	public void setSecPhone(String secPhone) {
		this.secPhone = secPhone;
	}
	public String getSecMail() {
		return secMail;
	}
	public void setSecMail(String secMail) {
		this.secMail = secMail;
	}
	
	
}
