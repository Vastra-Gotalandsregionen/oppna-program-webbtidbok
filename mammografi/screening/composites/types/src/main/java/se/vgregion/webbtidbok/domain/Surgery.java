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
package se.vgregion.webbtidbok.domain;

import java.io.Serializable;

/**
 * 
 * @author David & Carl. Class used for surgery description.
 * 
 */
public class Surgery implements Serializable {

	private static final long serialVersionUID = 1L;

	private String surgeryId;
	private String surgeryName;
	private String surgeryAddress;
	private String surgeryPhone;

    public String getSurgeryId() {
		return surgeryId;
	}

	public void setSurgeryId(String surgeryId) {
		this.surgeryId = surgeryId;
	}

	public String getSurgeryName() {
		return surgeryName;
	}

	public void setSurgeryName(String surgeryName) {
		this.surgeryName = surgeryName;
	}

	public String getSurgeryAddress() {
		return surgeryAddress;
	}

	public void setSurgeryAddress(String surgeryAddress) {
		this.surgeryAddress = surgeryAddress;
	}

	public String getFullAddress() {
		String returnValue = "";
		if ((surgeryName != null && !"".equals(surgeryName)) && (surgeryAddress != null && !"".equals(surgeryAddress))) {
			returnValue = surgeryName + ", " + surgeryAddress;
		} else {
			returnValue = surgeryName;
		}
		return returnValue;
	}

    public String getSurgeryPhone() {
        return surgeryPhone;
    }

    public void setSurgeryPhone(String surgeryPhone) {
        this.surgeryPhone = surgeryPhone;
    }

}
