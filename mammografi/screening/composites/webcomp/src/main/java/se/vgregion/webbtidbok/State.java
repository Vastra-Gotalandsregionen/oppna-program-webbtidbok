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
package se.vgregion.webbtidbok;

import java.io.Serializable;
import java.util.Calendar;

import se.vgregion.webbtidbok.servicedef.ServiceDefinition;
import se.vgregion.webbtidbok.ws.*;


/**
 * This bean stores the user credentials
 */

public class State implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private String pnr = "";
    private String passwd = "";
    private int centralTidbokID = 0;
    transient BookingResponse bookingResponse = null;
    private Calendar selectedDate;
    private boolean isDefaultDate = true;
    private boolean loggedIn = false;
    private boolean isFirst = true;
    private String messageBundle ="";
    private ServiceDefinition service = null;
    
    public State() {
		super();
		System.out.println("*** in State constructor!");
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
    
    public void setFirst(boolean first){
    	this.isFirst = first;
    }
    
    public boolean isFirst(){
    	return isFirst;
    }
    
    public ServiceDefinition getService() {
        return service;
    }
    
    public void setServiceDefinition(ServiceDefinition def) {
        service = def;
    }
    
    public void setLoggedIn(boolean loggedIn){
    	this.loggedIn = loggedIn;
    }
    
    
    public boolean isLoggedIn(){
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

	public void setSelectedDate(Calendar selectedDate) {
		this.selectedDate = selectedDate;
	}

	public Calendar getSelectedDate() {
		return selectedDate;
	}

	public void setDefaultDate(boolean isDefaultDate) {
		this.isDefaultDate = isDefaultDate;
	}

	public boolean isDefaultDate() {
		return isDefaultDate;
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
}