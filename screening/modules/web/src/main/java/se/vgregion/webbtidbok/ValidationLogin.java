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

public class ValidationLogin {
	
	
	public String validatePnr(String validateString){
		
		String result = "";
		
		if(validateString.length() == 0){
			result = "Personnummer fält tomt, Fyll i Personnummer med ÅÅÅÅMMDD-XXXX";
		}
		else if(validateString.length() > 0 && validateString.length() > 13){
			result = "Personnummer för många siffror, Fyll i Personnummer med ÅÅÅÅMMDD-XXXX";
		}
		else if(validateString.length() == 13){
			//Match pattern = Pattern.
			if(validateString.matches("[0-9]{8}-[0-9]{4}")){
				result = "";
			}
			else{
				result = "Personnummer ifyllt felaktigt, Fyll i Personnummer med ÅÅÅÅMMDD-XXXX";
			}
			
		}
		else{
			result = "Personnummer fel siffror, Fyll i Personnummer med ÅÅÅÅMMDD-XXXX";
		}
			
			
		return result;	
	}
	
	public String validatePassword(String validateString){
		
		String result = "";
		if(validateString.length() == 0){
			result = "Lösenord fält tomt, Fyll i Lösenord";
		}
		
		
		
		return result;
		
	}
	
	
}
