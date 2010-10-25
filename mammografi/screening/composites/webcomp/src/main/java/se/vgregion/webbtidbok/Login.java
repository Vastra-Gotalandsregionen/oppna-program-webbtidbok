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

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.booking.BookingFactory;
import se.vgregion.webbtidbok.servicedef.LookupService;
import se.vgregion.webbtidbok.servicedef.ServiceDefinition;

/**
 * As the name of this class suggests it caters for the logging in of the user. It also provides logging out functionality.
 * 
 * Loggin in is not really what the user does. The user's credentials are sent as a request through a look up service and the
 * first webservice that matches and returns usable information is then used further on. Some of this information is then set into
 * a {@link State} which follows the user.
 * 
 * @author carstm
 * 
 */
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

	/**
	 * Gets the correct {@link BookingFacade} for the current user and if the user is logged in the setMessageBundle method is
	 * called
	 * 
	 * @param stateLoginCredentials
	 *            {@link State}
	 * @return
	 * @throws Exception
	 */
	public boolean login(State stateLoginCredentials) throws Exception {
		BookingFacade bookingService = bookingFactory.getService(stateLoginCredentials);
		if (bookingService.login(stateLoginCredentials)) {
			// TODO: #### getNamn() is stand in for the real future field which will enable us to determine which message bundle
			// to
			// load, Gyn
			// or Bukaorta
			// String serviceType = "";
			// if (loginCredentials.getBookingResponse() != null) {
			// serviceType = loginCredentials.getBookingResponse().getNamn().getValue();
			// }
			// setMessageBundle(loginCredentials, sd);
			// if (serviceType.equalsIgnoreCase("Näf, Björn") || serviceType.equalsIgnoreCase("Kalle 1")) {
			// // gyn
			// Map<String, ServiceDefinition> serviceMap = bookingFactory.getService();
			// ServiceDefinition serviceDefinition = serviceMap.get("GYN");
			// setMessageBundle(loginCredentials, serviceDefinition);
			// } else {
			// bukaorta
			setMessageBundle(stateLoginCredentials, sd);
			// }
			stateLoginCredentials.setLoggedIn(true);
			logger.info("Logging in user {}.", stateLoginCredentials.getPnr());
			return true;
		} else {
			stateLoginCredentials.setLoggedIn(false);
			logger.info("Login failed for user {}.", stateLoginCredentials.getPnr());
			return false;
		}
	}

	/**
	 * Looks up which Service Definition is appropriate for the current user and sets the correct Service's ID into the
	 * {@link State} And unles the service id just set in the state equals "BUKAORTA" the setMessageBundle method is called.
	 * 
	 * @param state
	 *            {@link State}
	 * @param loginMessages
	 *            {@link LoginMessages}
	 * @return
	 */
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

	/**
	 * This method sets the appropriate message bundle for the current user, example: resources/messages/bukaorta/BukAortaMessages
	 * 
	 * @param state
	 *            {@link State}
	 * @param sd
	 *            {@link ServiceDefiniton}
	 */
	public void setMessageBundle(State state, ServiceDefinition sd) {
		state.setMessageBundle(sd.getMessageBundleBase());
	}
}
