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
package se.vgregion.webbtidbok.booking.sectra;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.ws.sectra.IRisReschedule;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;

/**
 * Used for login functionality.
 * 
 * @author carstm
 * 
 */
public class SectraWebServiceHelperImpl implements
		SectraWebServiceHelperInterface {

	private IRisReschedule thePortSU;
	private IRisReschedule thePortNU;

	public void setThePortSU(IRisReschedule thePortSU) {
		this.thePortSU = thePortSU;
	}

	public void setThePortNU(IRisReschedule thePortNU) {
		this.thePortNU = thePortNU;
	}

	// TODO fix some crap here to simulate login call
	@Override
	public boolean login(State state) {
		boolean returnValue;
		if (state.getPnr() == null || state.getPasswd() == null) {
			returnValue = false;
		}
		try {
			IRisReschedule wsInterface = getWSInterface(state);
			wsInterface.getBookingInfo(state.getPnr(), state.getPasswd());
			returnValue = true;
		} catch (IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage e) {
			returnValue = false;
		}
		return returnValue;
	}

	private IRisReschedule getWSInterface(State state) {

		if (state.getService().equals("MAMMO_NU")) {
			return this.thePortNU;
		} else {
			return this.thePortSU;
		}
	}

}
