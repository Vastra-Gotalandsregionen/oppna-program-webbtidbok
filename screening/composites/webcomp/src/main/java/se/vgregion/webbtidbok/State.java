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

package se.vgregion.webbtidbok;

import java.io.Serializable;

import se.vgregion.webbtidbok.sms.MobileNumberValidator;
import se.vgregion.webbtidbok.ws.BookingResponse;

/**
 * This bean stores the user's credentials.
 */

public class State implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pnr = "";
	private String passwd = "";
	/**
	 * centralTidBokID is used in Elvis as an identifier for a specific time book
	 */
	private int centralTidbokID = 0;
	transient BookingResponse bookingResponse = null;
	private boolean loggedIn = false;
	private boolean isFirst = true;
	private String messageBundle = "";
	private String service = null;
	private String mobileNumber = null;
	//Passed on from ServiceDefinition
	private String[] includeClinics, excludeClinics;

	/**
	 * elvisExaminationType is used to determine the type of examination since Elvis may cater for time books for several
	 * different types of examinations.
	 */
	private String elvisExaminationType;

	public State() {
		super();
		System.out.println("*** in State constructor!");
	}

	public String getElvisExaminationType() {
		return elvisExaminationType;
	}

	public void setElvisExaminationType(String elvisExaminationType) {
		this.elvisExaminationType = elvisExaminationType;
	}

	public String getPnr() {
		return pnr;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setFirst(boolean first) {
		this.isFirst = first;
	}

	public String[] getIncludeClinics() {
		return includeClinics;
	}
	
	public void setIncludeClinics(String[] includeClinics) {
		this.includeClinics = includeClinics;
	}
	
	public String[] getExcludeClinics() {
		return excludeClinics;
	}
	
	public void setExcludeClinics(String[] excludeClinics) {
		this.excludeClinics = excludeClinics;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public String getService() {
		return service;
	}

	public void setService(String def) {
		service = def;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setCentralTidbokID(int centralTidbokID) {
		this.centralTidbokID = centralTidbokID;
	}

	public int getCentralTidbokID() {
		return centralTidbokID;
	}

	public void setBookingResponse(BookingResponse bookingResponse) {
		this.bookingResponse = bookingResponse;
	}

	public BookingResponse getBookingResponse() {
		return bookingResponse;
	}

	public String toString() {
		return "[User Credentials: Personnummer = " + pnr + ", Password = " + passwd + "]";
	}

	public void setMessageBundle(String messageBundle) {
		this.messageBundle = messageBundle;
		System.out.println("*** state.setMessageBundle(String messageBundle). this.messageBundle: " + this.messageBundle);
	}

	public String getMessageBundle() {
		System.out.println("*** state.getMessageBundle(). messageBundle: " + messageBundle);
		return messageBundle;
	}

	/**
	 * As the user press OK-button to set the mobile phone number this method sends it thru validation. IT returns true if it was
	 * set ok and false if it didn't pass validation.
	 * 
	 * @param mobilenumber
	 * @param stateMobNbr
	 * @return
	 */
	public boolean setMobileNumber(String mobilenumber, String stateMobNbr) {
		boolean value = false;
		MobileNumberValidator validator = new MobileNumberValidator();
		this.mobileNumber = validator.validateMobilePhoneNumber(mobilenumber, stateMobNbr);
		if (!mobileNumber.isEmpty()) {
			value = true;
		}
		return value;
	}

	public String getMobilePhoneNumber() {
		return mobileNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * Method used by the UI form to set a mobile number into the state This is only to catch the input parameter from the form.
	 * 
	 * @param mobileNumber
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
}