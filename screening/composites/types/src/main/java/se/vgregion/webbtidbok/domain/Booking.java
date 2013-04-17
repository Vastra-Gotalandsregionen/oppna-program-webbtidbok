/**
 * Copyright 2010 Västra Götalandsregionen
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
 *
 */

package se.vgregion.webbtidbok.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	/**
	 * Only used for google analytics
	 * 
	 * Calculates the patient's age in year in relation to the server's local time.
	 * 
	 * @return the patient's age
	 */
	public int getPatientAge(){
		int age;
		try{
			Calendar today = Calendar.getInstance();
			Calendar dateOfBirth = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			dateOfBirth.setTime(sdf.parse(this.getPatientId().substring(0, 8)));
			age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
			int monthDiff = today.get(Calendar.MONTH) - dateOfBirth.get(Calendar.MONTH);
			if (monthDiff < 0 || (monthDiff == 0 && today.get(Calendar.DAY_OF_MONTH) < dateOfBirth.get(Calendar.DAY_OF_MONTH))){
				age--;
			}
		} catch (Exception e){
			return 0;
		}
		return age;
	}
	/**
	 * Only used for google analytics
	 * 
	 * Calculates the number of days until the following booking in relation to the server's
	 * local time. Days are calculated disregarding the time of day.
	 * 
	 * @return the difference between the booking date and todays date
	 */
	public int getDaysBeforeBooking(){
		if(this.getStartTime() == null)
			return Integer.MIN_VALUE;
		int dayInMillis = 24*60*60*1000;
		Calendar currTime = Calendar.getInstance();
		Calendar startTime = Calendar.getInstance();
		startTime.setTime(this.getStartTime());
		
		int startDays = (int) (clearTimePart(startTime).getTimeInMillis() / dayInMillis);
		int currDays = (int) (clearTimePart(currTime).getTimeInMillis() / dayInMillis);	
		
		return startDays - currDays;
	}
	private Calendar clearTimePart(Calendar date){
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		return date;
	}
}
