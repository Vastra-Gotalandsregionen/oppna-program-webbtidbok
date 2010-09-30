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

import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.booking.BookingFactory;
import se.vgregion.webbtidbok.servicedef.LookupService;
import se.vgregion.webbtidbok.servicedef.ServiceDefinition;

@Service
public class Login {
	private BookingFactory bookingFactory;
	private LookupService lookupService;
	private ResourceBundle resourceBundle;

	private Logger logger;

	public Login() {
		logger = LoggerFactory.getLogger("se.vgregion.webbtidbok");
	}

	ServiceDefinition sd = new ServiceDefinition();

	public void setResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	public void setBookingFactory(BookingFactory bookingFactory) {
		this.bookingFactory = bookingFactory;
	}

	public void setLookupService(LookupService lookupService) {
		this.lookupService = lookupService;
	}

	public void logout(State loginCredentials) {
		logger.info("Logging out user {}.", loginCredentials.getPnr());
		loginCredentials.setPnr("");
		loginCredentials.setPasswd("");
		loginCredentials.setLoggedIn(false);
	}

	public boolean login(State loginCredentials) throws Exception {
		BookingFacade bookingService = bookingFactory.getService(loginCredentials);
		if (bookingService.login(loginCredentials)) {
			// #### getNamn() is stand in for the real future field which will enable us to determine which message bundle to
			// load, Gyn
			// or Bukaorta
			String value = "";
			if (loginCredentials.getBookingResponse() != null) {
				value = loginCredentials.getBookingResponse().getNamn().getValue();
			}
			if (value.equalsIgnoreCase("Kalle 1")) {
				// gyn
				Map<String, ServiceDefinition> serviceMap = bookingFactory.getService();
				ServiceDefinition serviceDefinition = serviceMap.get("GYN");
				setMessageBundle(loginCredentials, serviceDefinition);
			} else {
				// bukaorta
				setMessageBundle(loginCredentials, sd);
			}
			loginCredentials.setLoggedIn(true);
			logger.info("Logging in user {}.", loginCredentials.getPnr());
			return true;
		} else {
			loginCredentials.setLoggedIn(false);
			logger.info("Login failed for user {}.", loginCredentials.getPnr());
			return false;
		}
	}

	public boolean lookup(State state, LoginMessages loginMessages) {
		sd = lookupService.lookup(state);
		if (sd != null) {
			state.setService(sd.getServiceID());
			if (!sd.getServiceID().equalsIgnoreCase("BUKAORTA")) {
				setMessageBundle(state, sd);
			}
			logger.debug("Service lookup succeeded for user {}: service is {}.", state.getPnr(), state.getService());
			return true;
		} else {
			logger.debug("Service lookup failed for user {}.", state.getPnr());
			String[] split = resourceBundle.getString("loginpageErrorMessage").split("\\|");
			loginMessages.setErrorMessages(split);
			return false;
		}
	}

	public void setMessageBundle(State state, ServiceDefinition sd) {
		state.setMessageBundle(sd.getMessageBundleBase());
	}
}
