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
import java.util.Date;

/**
 * This class aggregates what makes up a booking for any type of examination.
 * 
 * @author carstm
 * 
 */
public abstract class Booking implements Serializable {

	private static final long serialVersionUID = 7686724985905225950L;
	private String patientName;
	private Surgery surgery;
	private String patientId;
	private Date startTime;
	private boolean updateable;
	private boolean switchedSurgery = false;
	private Surgery switchToSurgery;

	/**
	 * Is used to determine whether the patients booking is updateable or not. Specifically used when there is a limit on how many
	 * times a patient may re schedule an appointment.
	 * 
	 * @return {@link boolean} updateable
	 */
	public boolean isUpdateable() {
		return updateable;
	}

	public void setUpdateable(boolean updateable) {
		this.updateable = updateable;
	}

	public Date getStartTime() {
		return startTime != null ? (Date) startTime.clone() : null;
	}

	public void setStartTime(Date startTime) {
		if (startTime != null) {
			this.startTime = (Date) startTime.clone();
		}
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	/**
	 * The firstname and lastname of the patient.
	 * 
	 * @return {@link String} of firstname and lastname.
	 */
	public String getPatientName() {
		return patientName;
	}

	/**
	 * 
	 * @param patientName
	 *            Firstname and lastname.
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * 
	 * @return {@link Surgery} the surgery the patient is booked to.
	 */
	public Surgery getSurgery() {
		return surgery;
	}

	/**
	 * 
	 * @param surgery
	 *            {@link Surgery} the surgery the patient is booked to.
	 */
	public void setSurgery(Surgery surgery) {
		this.surgery = surgery;
	}

	public boolean isSwitchedSurgery() {
		return switchedSurgery;
	}

	public void setSwitchedSurgery(boolean switchedSurgery) {
		this.switchedSurgery = switchedSurgery;
	}

	public Surgery getSwitchToSurgery() {
		return switchToSurgery;
	}

	public void setSwitchToSurgery(Surgery switchToSurgery) {
		this.switchToSurgery = switchToSurgery;
	}

}
