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

import org.apache.log4j.Logger;

import se.vgregion.webbtidbok.*;

public class ValidationLogin {

    public static final String ENTER_PATIENT_ID = "Fyll i ditt personnummer med ÅÅÅÅMMDD-XXXX";
    public static final String ENTER_PASSWORD = "Du måste fylla i lösenord";
    public static final String MALFORMED_PATIENT_ID = "Fyll i ditt personnummer med ÅÅÅÅMMDD-XXXX";
    
    private Logger logger;
    
    private Pattern pnrFullPattern;
    private Pattern pnrWithDashPattern;
    private Pattern pnrWithoutDashPattern;
    
    public ValidationLogin() {
        logger = Logger.getLogger("se.vgregion.webbtidbok.services");

        // Matches full numbers - eg. yyyymmdd-xxxx and yyyymmddxxxx.
        pnrFullPattern = Pattern.compile("^([0-9]{8})-?([0-9]{4})$");
        // Matches short numbers with dash - eg. yymmdd-xxxx and yymmdd+xxxx.
        pnrWithDashPattern = Pattern.compile("^([0-9]{2})([0-9]{4})([+-])([0-9]{4})$");
        // Matches short numbers without dash - eg. yymmddxxxx. Assumed to be less than 100 years old.
        pnrWithoutDashPattern = Pattern.compile("^([0-9]{2})([0-9]{4})([0-9]{4})$");
    }
    
	public boolean validateLogin(State st, LoginMessages lm){
		
        logger.debug("Validated login start, state pnr is: " + st.getPnr() + ", state passw is: " + st.getPasswd());
		String pnr  = st.getPnr();
		String password = st.getPasswd();
		
		lm.clear();
		if(pnr.length() == 0){
			lm.setLogMessagePnr(ENTER_PATIENT_ID);
		}
		if(password.length() == 0){
			lm.setLogMessagePassword(ENTER_PASSWORD);
		}
		if(pnr.length() == 0 || password.length() == 0){
			return false;
		}
		
		String canonicalPnr = getCanonicalPnr(pnr, Calendar.getInstance());
		if (canonicalPnr == null) {
            lm.setLogMessagePnr(MALFORMED_PATIENT_ID);
            return false;
		} else {
		    st.setPnr(canonicalPnr);
		}

		logger.debug("Validated login end, state pnr is: " + st.getPnr() + ", state passw is: " + st.getPasswd());
		return true;
	}
	
	String getCanonicalPnr(String validateString, Calendar refDate){
        String trimmed = validateString.trim();

        Matcher pnr = pnrFullPattern.matcher(trimmed);
	    if (pnr.matches() && pnr.groupCount() == 2) {
	        return pnr.group(1) + "-" + pnr.group(2);
	    }
	    pnr = pnrWithDashPattern.matcher(trimmed);
	    if (pnr.matches() && pnr.groupCount() == 4) {
	        int year = getYearBefore(refDate, pnr.group(1), pnr.group(3).equals("+"));
	        return Integer.toString(year) + pnr.group(2) + "-" + pnr.group(4);
	    }
        pnr = pnrWithoutDashPattern.matcher(trimmed);
	    if (pnr.matches() && pnr.groupCount() == 3) {
            int year = getYearBefore(refDate, pnr.group(1), false);
            return Integer.toString(year) + pnr.group(2) + "-" + pnr.group(3);
	    }
		return null;	
	}

	/**
	 * Gets the latest year which preceeds or matches the reference date.
	 * @param refDate The reference date. Only year part is used.
	 * @param yeardigits The year digits to match against the reference date.
	 * @param hundredYearsOld If this is true, year should preceed refDate with at least one hundred years.
	 * @return The year the year digits should be interpreted as.
	 */
	int getYearBefore(Calendar refDate, String yeardigits, boolean hundredYearsOld) {
	    int refYear = refDate.get(Calendar.YEAR) - (hundredYearsOld ? 100 : 0);

	    // This parse should always succeed, as we have verified the digits using a regexp above.
	    int year = 1800 + Integer.parseInt(yeardigits, 10);

        while (year + 100 <= refYear) {
            year += 100;
        }

        return year;
    }

}
