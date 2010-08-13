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
package se.vgregion.webbtidbok.booking;

import java.util.List;

import javax.faces.model.SelectItem;

import se.vgregion.webbtidbok.Places;
import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;

/**
 * This class is used as a switch to choose between different {@link BookingFacade} depending on witch service that is used by the user.
 */
public class BookingFacadeSwitch implements BookingFacade {

  private BookingFacade sectraSUfacade;
  private BookingFacade elvisBookingFacade;

  public void setElvisBookingFacade(BookingFacade elvisBookingFacade) {
    this.elvisBookingFacade = elvisBookingFacade;
  }

  public void setSectraSUfacade(BookingFacade sectraSUfacade) {
    this.sectraSUfacade = sectraSUfacade;
  }

  @Override
  public Booking getBookingInfo(State state) {
    BookingFacade bookingFacadeForCurrentRequest = getBookingFacadeForCurrentRequest(state);
    return bookingFacadeForCurrentRequest.getBookingInfo(state);

  }

  private BookingFacade getBookingFacadeForCurrentRequest(State state) {
    BookingFacade facade = null;
    if (state.getService().equals("MAMMO_SU")) {
      facade = sectraSUfacade;
    }
    if (state.getService().equals("MAMMO_NU")) {
      // TODO: add logic here
    }
    if (state.getService().equals("BUKAORTA")) {
      facade = elvisBookingFacade;
    }
    return facade;
  }

  @Override
  public List<SelectItem> getBookingPlaceSelectItems(State state) {
    return getBookingFacadeForCurrentRequest(state).getBookingPlaceSelectItems(state);
  }

  @Override
  public boolean login(State state) {
    return false;
  }

  @Override
  public int getSelectedDefaultItem(State state) {
    return getBookingFacadeForCurrentRequest(state).getSelectedDefaultItem(state);
  }

  @Override
  public Places getSelectedPlace(Places places, State state) {
    return getBookingFacadeForCurrentRequest(state).getSelectedPlace(places, state);
  }

}