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

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.stereotype.Service;

import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.CentralBookingWS;
import se.vgregion.webbtidbok.ws.GetBooking;
import se.vgregion.webbtidbok.ws.ICentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ObjectFactory;


public class Booking 
{
	
	BookingResponse response;
	BookingRequest request;
	WebServiceHelper helper = new WebServiceHelper();
	
	
	//private String orderDate;
	
	//constructor
	public Booking(){}
	
	public void getBooking(State loginCredentials){
		
		if(loginCredentials.isLoggedIn()){
			
			request = helper.getQueryWSRequest(loginCredentials);
			response = helper.getQueryWS(request);
			
			System.out.println(this.toString());
			
			
			//return this.toString();
		}
		
		//return this.toString();
	}
	
	
	public String getOrderDate(){
		
		return response.getBesDat().getValue();
		
	}
	
	public int getHour(){
		
		return response.getBokadTid().getHour();
	}
	
	public int getMinute(){
		
		return response.getBokadTid().getMinute();
	}
	
	public String getName(){
		
		return response.getNamn().getValue();
	}
	
	public String getReception(){
		return response.getHuvudVdg().getValue();
	}
	
	public String getSecondaryReception(){
		
		return response.getVardgivare().getValue();
	}
	
	
	
	public String toString(){
		return "BestDatum: " + getOrderDate() + " , Timma: " + getHour() + " , " + getMinute() + " , name: " +  getName() + " , reception: " + getReception() + " , secondary reception: " + getSecondaryReception(); 
	}
	
	
	
	
	
}
