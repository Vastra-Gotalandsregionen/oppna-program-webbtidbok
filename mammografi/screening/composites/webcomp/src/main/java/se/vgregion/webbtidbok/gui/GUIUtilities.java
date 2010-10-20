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
package se.vgregion.webbtidbok.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadPoolExecutor;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.booking.elvis.BookingService;
import se.vgregion.webbtidbok.booking.elvis.WebServiceHelper;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.mail.MailSender;

/**
 * This class is a temporary home for methods which previously were part of the booking service, but don't belong there. In order
 * not to pollute the BookingFacade they have been moved here. If you find a better home for a method, please move it.
 */
public class GUIUtilities {

	/*
	 * Please refrain from adding private variables to this class unless strictly necessary.
	 */

	private BookingFacade bookingFacade;
	private BookingService bookingService;
	private LocationHolder staticLocationsholder;

	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	public void setBookingFacade(BookingFacade bookingFacade) {
		this.bookingFacade = bookingFacade;
	}

/**
	 * This is called from the flow in order to populate alternative locations available for the current user to choose from when
	 * updating or changing his/hers appointment by switching to another location within the same area of operations.
	 * 
	 * @param booking {@link Booking} 
	 * @param state {@link State
	 * @return holder {@link LocationHolder} contains a list of available surgeries
	 */
	public LocationHolder setupLocations(Booking booking, State state) {
		LocationHolder holder = new LocationHolder();
		holder.setAvailableLocations(bookingFacade.getAvailableSurgeries(state));

		if (booking != null && booking.getSurgery() != null && booking.getSurgery().getSurgeryId() != null) {
			holder.setLocationId(booking.getSurgery().getSurgeryId());
		}
		return holder;
	}

	/**
	 * The method is used to set up "static locations" between which a Mammografi patient can choose when she wishes to switch
	 * locations from one area of operations to another
	 * 
	 * @param booking
	 *            {@link Booking}
	 * @param state
	 *            {@link State}
	 * @return staticLocationsholder {@link LocationHolder}
	 * @throws
	 */
	public LocationHolder setupStaticLocations(Booking booking, State state) {
		String name = "staticLocationName";
		String id = "staticLocationId";

		ResourceBundle bundle = ResourceBundle.getBundle(state.getMessageBundle());
		Properties props = new Properties();

		// load bundle properties into properties object <key, value> style
		for (int i = 1; bundle.containsKey(name + i) && bundle.containsKey(id + i); i++) {
			props.put(name + i, bundle.getString(name + i));
			props.put(id + i, bundle.getString(name + i));
		}

		List<Surgery> tmpStatciSurgeryList = new ArrayList<Surgery>();

		for (int i = 1; !props.isEmpty() && props.containsKey(id + i) && props.containsKey(name + i); i++) {
			Surgery tmpSurgery = new Surgery();
			tmpSurgery.setSurgeryId(props.getProperty(id + i));
			tmpSurgery.setSurgeryName(props.getProperty(name + i));
			tmpStatciSurgeryList.add(tmpSurgery);
		}

		staticLocationsholder = new LocationHolder();
		staticLocationsholder.setAvailableStaticLocations(tmpStatciSurgeryList);

		if (booking != null && booking.getSurgery() != null && booking.getSurgery().getSurgeryId() != null) {
			staticLocationsholder.setLocationId(booking.getSurgery().getSurgeryId());
		}
		return staticLocationsholder;
	}

	/**
	 * This is used by the UI to determine whether to render a time list at the bottom of the Update.xhtml page or not
	 * 
	 * @param timelist
	 * @return {@link boolean}
	 */
	public boolean getIsTimeListEmpty(List<? extends Object> timeList) {
		if (timeList == null) {
			return true;
		} else {
			return timeList.isEmpty();
		}
	}

	/**
	 * Used in the update-flow.xml when the patient choses a new location to book a new appointment to. The centralTidBokID is
	 * then set in the {@link State} that corresponds to that user and is then used to retrieve a time book which corresponds to
	 * the new location the user wishes to book a new appointment to.
	 * 
	 * @param state
	 * @param locationId
	 */
	// TODO: This method is just to make Elvis calls still work. We should remove this.
	public void setCentralTidbokIDfromLocation(State state, String locationId) {
		try {
			int tidboksId = Integer.parseInt(locationId, 10);
			state.setCentralTidbokID(tidboksId);
		} catch (NumberFormatException e) {
			// Don't do anything if we can't get an int. We probably have a Sectra call,
			// and then it won't be needed anyway.
		}
	}

	/**
	 * Sets the "static location" which the mammografi patient has chosen when switching from one area of operation to another.
	 * This is done with the help of the locationId which is used to retrieve the corresponding {@link Surgery} and then inserting
	 * that Surgery into the {@link booking} object.
	 * 
	 * @param booking
	 *            {@link Booking}
	 * @param locationId
	 */
	public void setChosenStaticLocation(Booking booking, String locationId) {
		Surgery staticLocationById = staticLocationsholder.getStaticLocationById(locationId);
		booking.setSwitchToSurgery(staticLocationById);
	}

	public boolean cancelBooking(State state) {
		bookingService = new BookingService();
		bookingService.setHelper(new WebServiceHelper());
		boolean value = bookingService.cancelBooking(state);
		return value;
	}

	/**
	 * Threaded mailsender for sending cancellation emails
	 * 
	 * @param state
	 *            {@link State}
	 * @param booking
	 *            {@link Booking}
	 * @param mailQueue
	 */
	public void sendCancelationMail(State state, Booking booking, ThreadPoolExecutor mailQueue) {
		MailSender mailsender = new MailSender(state, booking);
		mailQueue.execute(mailsender);
	}

	/**
	 * Threaded mailsender for sending switch static location emails
	 * 
	 * @param state
	 *            {@link State}
	 * @param booking
	 *            {@link Booking}
	 * @param mailQueue
	 */
	public void sendSwitchLocationMail(State state, Booking booking, ThreadPoolExecutor mailQueue) {
		MailSender mailsender = new MailSender(state, booking);
		mailQueue.execute(mailsender);
	}

	/**
	 * Used to retrieve a specific {@link Surgery} by the locationId of type String. This is used when the mammografi patient
	 * executes the switch.xml flow while switching to another static location.
	 * 
	 * @param locationId
	 * @return {@link Surgery}
	 */
	public Surgery getStaticLocationByLocationId(String locationId) {
		return staticLocationsholder.getStaticLocationById(locationId);
	}

	/**
	 * Used to set a boolean in the {@link Booking} object to true when the user has entered the switch.xml flow while switching
	 * between static locations. This boolean is then used by the UI to render switch-flow specific UI element(s).
	 * 
	 * @param booking
	 */
	public void setSwitchLocation(Booking booking) {
		booking.setSwitchedSurgery(true);
	}
}
