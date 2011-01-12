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
package se.vgregion.webbtidbok.sms;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class MobileNumberValidatorTest {

	static String mobileNumberNoSpace;
	static String mobileNumberSpace;
	static String mobileNumberDash;
	static String mobileNumberShort;
	static String mobileNumberLong;
	static String mobileNumberLetter;
	static String mobileNumberNoSecondPos7;
	static MobileNumberValidator validator;
	static se.vgregion.webbtidbok.State state;
	static Map<String, String> nbrList;

	@Before
	public void setUp() throws Exception {
		nbrList = new HashMap<String, String>();
		validator = new MobileNumberValidator();
		state = new se.vgregion.webbtidbok.State();
		state.setMobileNumber("012345678901234569");
		mobileNumberNoSpace = "0735123456";
		mobileNumberSpace = "0735 123456";
		mobileNumberDash = "0735-123456";
		mobileNumberLong = "07351234567890123456789";
		mobileNumberShort = "0735";
		mobileNumberLetter = "A735";
		mobileNumberNoSecondPos7 = "020910100";

		nbrList.put(mobileNumberNoSpace, "0735123456");
		nbrList.put(mobileNumberSpace, "0735 123456");
		nbrList.put(mobileNumberDash, "0735-123456");
		nbrList.put(mobileNumberLong, "07351234567890123456789");
		nbrList.put(mobileNumberShort, "0735");
		nbrList.put(mobileNumberLetter, "A735");
		nbrList.put(mobileNumberNoSecondPos7, "020910100");
	}

	@Test
	public void testValidateMobilePhoneNumberByThrowingSomeCorrectAndIncorrectNumbesAtIt() {

		Collection c = nbrList.values();
		Iterator iter = c.iterator();
		String iterElement;
		String returnNbr;
		while (iter.hasNext()) {
			iterElement = (String) iter.next();
			returnNbr = validator.validateMobilePhoneNumber(iterElement, state.getMobileNumber());
			if (returnNbr != null) {
				// System.out.println("#" + returnNbr + "#");
			} else {
				// System.out.println(validator.validateMobilePhoneNumber("#" + returnNbr + "#"));
				assertTrue(false);
			}
		}
	}
}
