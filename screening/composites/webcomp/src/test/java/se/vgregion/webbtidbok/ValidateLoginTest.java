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


import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class ValidateLoginTest {

	@Test
	public void testGetYearBefore(){
		ValidationLogin val = new ValidationLogin();
		Calendar refDate = calendarForDate(2010,8,1);
		
		assertEquals(2008, val.getYearBefore(refDate, "08", false));
        assertEquals(2010, val.getYearBefore(refDate, "10", false));
        assertEquals(1911, val.getYearBefore(refDate, "11", false));

        assertEquals(1908, val.getYearBefore(refDate, "08", true));
        assertEquals(1910, val.getYearBefore(refDate, "10", true));
        assertEquals(1811, val.getYearBefore(refDate, "11", true));

        assertEquals(2525, val.getYearBefore(calendarForDate(2550,1,1), "25", false));
	}
	
	@Test
	public void testGetCanonicalPnr(){
		ValidationLogin val = new ValidationLogin();
        Calendar refDate = calendarForDate(2010,8,1);
		
        assertEquals("19421212-9876", val.getCanonicalPnr("194212129876", refDate));
        assertEquals("19421212-9876", val.getCanonicalPnr("19421212-9876", refDate));
        assertEquals("19421212-9876", val.getCanonicalPnr("421212-9876", refDate));
        assertEquals("19421212-9876", val.getCanonicalPnr("4212129876", refDate));

        assertEquals("20080101-5555", val.getCanonicalPnr("0801015555", refDate));
        assertEquals("20080101-5555", val.getCanonicalPnr("080101-5555", refDate));
        assertEquals("19080101-5555", val.getCanonicalPnr("080101+5555", refDate));

        assertNull(val.getCanonicalPnr("080101555", refDate));
        assertNull(val.getCanonicalPnr("20080101555", refDate));
        assertNull(val.getCanonicalPnr("2008010-5555", refDate));

        assertEquals("19421212-K876", val.getCanonicalPnr("19421212K876", refDate));
        assertNull(val.getCanonicalPnr("19421212-9K76", refDate));
	}

	@Test
	public void testValidateLogin() {
        ValidationLogin val = new ValidationLogin();

        validationAsserts(val, "194212124321", "", "194212124321", false, "", ValidationLogin.ENTER_PASSWORD);
        validationAsserts(val, "194212124321", "apa", "19421212-4321", true, "", "");
        validationAsserts(val, "", "apa", "", false, ValidationLogin.ENTER_PATIENT_ID, "");
        validationAsserts(val, "", "", "", false, ValidationLogin.ENTER_PATIENT_ID, ValidationLogin.ENTER_PASSWORD);
        validationAsserts(val, "0801015555", "apa", "20080101-5555", true, "", "");
        validationAsserts(val, "080101-5555", "apa", "20080101-5555", true, "", "");
        validationAsserts(val, "080101+5555", "apa", "19080101-5555", true, "", "");
        validationAsserts(val, "080101-555", "apa", "080101-555", false, ValidationLogin.MALFORMED_PATIENT_ID, "");
        validationAsserts(val, "19421212K321", "apa", "19421212-K321", true, "", "");
	}

	// This is a helper method to assert a bunch of facts about logins.
	private void validationAsserts(ValidationLogin val, String pnr, String password,
	        String canonPnr, boolean passed, String failReasonPnr, String failReasonPw) {
        State st = new State();
        st.setPnr(pnr);
        st.setPasswd(password);
        LoginMessages lm = new LoginMessages();

        assertEquals(passed, val.validateLogin(st, lm));
        assertEquals(canonPnr, st.getPnr());

        assertEquals(failReasonPnr, lm.getLogMessagePnr());
        assertEquals(failReasonPw, lm.getLogMessagePassword());
	}
	
    private Calendar calendarForDate(int year, int month, int day) {
        Calendar refDate = Calendar.getInstance();
        refDate.clear();
        refDate.set(year, month, day);
        return refDate;
    }
}
