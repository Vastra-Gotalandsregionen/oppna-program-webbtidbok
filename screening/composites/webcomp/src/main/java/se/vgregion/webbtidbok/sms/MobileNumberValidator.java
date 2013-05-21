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

package se.vgregion.webbtidbok.sms;

/**
 * This class validates the form of the mobile phone number (NNNN-NNNNNN, NNNNNNNNNN) The phonenumber must be between 1-15 digits
 * If the first Zero is omitted by the user one will be inserted The second digit must be a 7, otherwise it's not a mobile phone
 * number. It also checks that all positions in the provided number are digits, it removes any "-" or white spaces " ".
 * 
 * @author carstm
 * 
 */
public class MobileNumberValidator {
	boolean mobNbrOk;

	public MobileNumberValidator() {
		super();
		System.out.println("*** MobileNumberValidator constructor");
	}

	public String validateMobilePhoneNumber(String mobileNumber, String stateMobNbr) {

		mobNbrOk = false;
		char[] mob = null;
		String returnNbr = "";

		if (stateMobNbr != null) {
			mobileNumber = stateMobNbr;
		}

		if (mobileNumber.length() >= 9 && mobileNumber.length() < 16) {

			mob = mobileNumber.replace("-", "").trim().toCharArray();
			returnNbr = new String(mob).replace(" ", "");
			mob = returnNbr.toCharArray();

			if (mob[1] == '7') {

				if (mob[0] != '0') {
					mob.toString().concat("0");
				}
				mobNbrOk = true;

				for (char pos : mob) {

					if (!Character.isDigit(pos)) {
						mobNbrOk = false;
						break;
					}

				}

			} else {
				mobNbrOk = false;
				return returnNbr;
			}
		}
		return returnNbr;
	}

	public boolean isValue() {
		return mobNbrOk;
	}

	public void setValue(boolean value) {
		this.mobNbrOk = value;
	}

}
