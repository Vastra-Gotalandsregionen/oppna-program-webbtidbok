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

package se.vgregion.webbtidbok.servicedef;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacade;

/**
 * This class is used for look up of what Service Definition a user should be matched to. The Service Definition is a package of
 * information which declares what Message Bundle etc. that is associated with a certain Service (like Bukaorta, Mamografi etc.)
 * 
 * @author carstm
 * 
 */
public class LoginLookupService implements LookupService {

	private ServiceDefinition serviceDefinition = null;

	public void setServiceDefinition(ServiceDefinition def) {
		serviceDefinition = def;
	}

	/**
	 * tmpState only used during lookup phase. A new tmpState is created each service mapping attempt so that it won't be
	 * "tainted" with unnecessary data from previous attempts.
	 */
	@Override
	public ServiceDefinition lookup(State state) {
		BookingFacade booking = serviceDefinition.getBookingService();
		State tmpState = copyStateLoginProps(state);

		return booking.login(tmpState) ? serviceDefinition : null;
	}

	private State copyStateLoginProps(State state) {
		State newState = new State();
		newState.setPnr(state.getPnr());
		newState.setPasswd(state.getPasswd());

		return newState;
	}
}
