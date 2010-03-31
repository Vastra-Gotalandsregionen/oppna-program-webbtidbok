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


import java.io.*;


public class LoginMessages implements Serializable{
	
	private String logMessagePnr = "";
	private String logMessagePassword = "";
	
	
	public String getLogMessagePnr(){
		
		return logMessagePnr;
	}
	

	public void setLogMessagePnr(String s){
		
		logMessagePnr = s;
	}
	
	public String getLogMessagePassword(){
		return logMessagePassword;
	}
	
	public void setLogMessagePassword(String s){
		logMessagePassword =s;
	}
	
	public void setBadLogin(){
		logMessagePnr = "Fyll i ditt personnummer med ÅÅÅÅMMDD-XXXX";
		logMessagePassword = "Lösenord som det står i kallelsen, tänk på att skilja mellan små och stora bokstäver";
	}
	
	public void clear() {
		logMessagePnr = logMessagePassword = "";
	}
	
	
	
	
}