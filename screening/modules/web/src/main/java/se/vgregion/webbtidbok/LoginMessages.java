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
	
	/*
	-1	General	Allmänt fel, ej specificerat
	-1002	Not found	 Posten kunde ej hittas
	-1008	Pwd Error	Pinkoden felaktig
	-2014	Anulled	 Posten ar avbokad
	-1036	CertInvalid	Certifierings fel
	-1119	PnrError	Felaktigt personnummer
	-10175	OmbokMax	Max antal bokning uppnådda
	-2002		Inparamter saknas
	-2001		Invalid paramter 
	*/
	
	public static int exceptionMessage = -1;
	public static String exceptionMessageString = "Allmänt fel, ej specificerat";

	
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
	
	
	public void setExceptionMessage(int id){
		exceptionMessage = id;
		
		switch(exceptionMessage){
		  		case -1 :
		  			exceptionMessageString = "Allmänt fel, ej specificerat";
		  			break;
		  		case -1002 :
		  			exceptionMessageString = "Posten kunde ej hittas";
		  			break;
		  		case -1008 :
		  			exceptionMessageString = "Pinkoden felaktig";
		  			break;
		  		case -2014 :
		  			exceptionMessageString = "Posten ar avbokad";
		  			break;
		  		case -1036 :
		  			exceptionMessageString = "Certifierings fel";
		  			break;	
		  		
		  		case -1119 :
		  			exceptionMessageString = "Felaktigt personnummer";
		  			break;	
		  		case -10175 :
		  			exceptionMessageString = "Max antal bokning uppnådda";
		  			break;
		  		case -2002 :
		  			exceptionMessageString = "Inparameter saknas";
		  			break;
		  		case -2001 :
		  			exceptionMessageString = "Invalid parameter";
		  			break;
		  			
		}		
		
	}
	
	
}