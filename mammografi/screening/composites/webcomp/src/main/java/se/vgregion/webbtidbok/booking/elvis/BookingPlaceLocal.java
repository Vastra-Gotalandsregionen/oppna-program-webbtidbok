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
package se.vgregion.webbtidbok.booking.elvis;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.stereotype.Service;

import se.vgregion.webbtidbok.lang.DateHandler;
import se.vgregion.webbtidbok.lang.StringHandler;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.CentralBookingWS;
import se.vgregion.webbtidbok.ws.GetBooking;
import se.vgregion.webbtidbok.ws.ICentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ObjectFactory;
import se.vgregion.webbtidbok.ws.*;




public class BookingPlaceLocal implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int centralTimeBookingId = 0;
	private String address = "";
	private String clinic = "";
	
	
	
	public void BookingPlaceLocal(BookingPlace place){
		
		setAddress(place.getAddress().getValue());
		setCentralTimeBookId(place.getCentralTidbokID());
		setClinic(place.getMottagning().getValue());
		
	}
	
	public void setCentralTimeBookId(int id){
		centralTimeBookingId = id;
	}
	
	public int getCentralTimeBookId(){
		
	 return centralTimeBookingId;
		
	}
	
	
	public void setAddress(String s){
		address = s;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setClinic(String s){
		clinic =s;
	}
	
	public String getClinic(){
		return clinic;
	}
	
	
	public String toString(){
		return "centralTimeBookingId: " + Integer.toString(this.getCentralTimeBookId()) + " Address: " + this.getAddress() + " Mottagning: " + this.getClinic();
	}
	
}	

