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


import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.*;
import java.util.regex.*;

import se.vgregion.webbtidbok.*;

public class ValidationLogin {
	
	public boolean validateLogin(State st, LoginMessages lm){
		
		String pnr  = st.getPnr();
		String password = st.getPasswd();
		
		lm.clear();
		if(pnr.length() == 0){
			lm.setLogMessagePnr("Fyll i ditt personnummer med ÅÅÅÅMMDD-XXXX");
			lm.setBadLogin();
			
		}
		if(password.length() == 0){
			lm.setLogMessagePassword("Du måste fylla i lösenord");
			lm.setBadLogin();
		}
		if(pnr.length() == 0 || password.length() == 0){
			return false;
		}
		System.out.println(" ### in validate login, state pnr is: " + st.getPnr() + ", state passw is: " + st.getPasswd());
		setServiceDefinitionFromPassword(st);
		
		return true;
	}
	
	public String validatePnr(String validateString){
	
		String result = "";
		
		if(validateString.length() == 0){
			result = "Personnummer f�lt tomt, Fyll i Personnummer med ����MMDD-XXXX";
		}
		else if(validateString.length() > 0 && validateString.length() > 13){
			result = "Personnummer f�r m�nga siffror, Fyll i Personnummer med ����MMDD-XXXX";
		}
		else if(validateString.length() == 13){
			//Match pattern = Pattern.
			if(validateString.matches("[0-9]{8}-[0-9]{4}")){
				result = "";
			}
			else{
				result = "Personnummer ifyllt felaktigt, Fyll i Personnummer med ����MMDD-XXXX";
			}
			
		}
		else{
			result = "Personnummer fel siffror, Fyll i Personnummer med ����MMDD-XXXX";
		}
			
			
		return result;	
	}
	
	public String validatePassword(String validateString){
		
		String result = "";
		if(validateString.length() == 0){
			result = "L�senord f�lt tomt, Fyll i L�senord";
		}
		
		
		
		return result;
		
	}
	
	public void setServiceDefinitionFromPassword(State st){
		String password = st.getPasswd();
		System.out.println("### ValidationLogin.setServiceDefinition()");
		if(password.startsWith("SEMSUS")){
			System.out.println("password starts with SEMSUS: " + password);
			st.setService("MAMMO_SU");
		}
		if(password.startsWith("SEMUDD")){
			st.setService("MAMMO_NU");
		} else {
			st.setService("BUKAORTA");
		}
	}
	
}
