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
/**
 * 
 */
package se.vgregion.webbtidbok;


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

/**
 * @author conpem
 *
 */
public class BookingTimeLocal implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int numbers = 0;
	private String day = "";
	private String time = "";
	private int bookingTimeId = 0;
	
	public BookingTimeLocal() {
		//TODO  Auto-generated constructor stub
	}
	
	public void setDay(String s){
		day = s;
	}
	
	public String getDay(){
		return day;
	}
	
	public void setTime(String s){
		time = s;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setNumbers(int number){
		numbers = number;
	}
	
	public int getNumbers(){
		return numbers;
	}
	
	public void setBookingTimeId(int id){
		bookingTimeId = id;
	}
	
	public int getBookingTimeId(){
		return bookingTimeId;
	}
	
	public String toString(){
		return "ID:" + this.getBookingTimeId() +
			   "Numbers: " + this.getNumbers() +
			   "Day: " + this.getDay() +
			   "Hour:" + this.getTime();
	}
}
