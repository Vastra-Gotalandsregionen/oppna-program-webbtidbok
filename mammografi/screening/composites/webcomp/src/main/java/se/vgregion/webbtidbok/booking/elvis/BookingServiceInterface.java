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
package se.vgregion.webbtidbok.booking.elvis;

import java.util.List;

import javax.faces.model.SelectItem;

import se.vgregion.webbtidbok.Places;
import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingPlace;
import se.vgregion.webbtidbok.domain.Surgery;

public interface BookingServiceInterface {

  public abstract boolean getIsTimeListEmpty();

  public abstract void setTimeListEmpty(boolean isTimeListEmpty);

  public abstract void setIsUpdated(boolean b);

  public abstract boolean isUpdated();

  public abstract void setFirstPlacesBoolean(boolean b);

  public abstract boolean isFirstPlaces();

  public abstract Booking getBooking(State loginCredentials);

  /**
   * Method cancelBooking returns true if deleted
   * 
   * @param loginCredentials
   * @return
   */
  public abstract boolean cancelBooking(State loginCredentials);

 // public abstract List<BookingPlace> getBookingPlace(State loginCredentials);

  public abstract List<BookingTimeLocal> getBookingTime(State loginCredentials);

  /***
   * method set selected item
   * 
   * @param selectedItem
   */

  public abstract void setSelectedItem(int selectedItem);

  /***
   * 
   * @param places
   */
  public abstract void setSelectedItem(Places places);

  /***
   * 
   * 
   * @param places
   * @param state
   */
  public abstract void setSelectedItem(Places places, State state);

  public abstract int getSelectedDefaultItem(State loginCredentials);

  /**
   * Method finding a selected place in the list of chosen bookingplaces return the object to print the selected place
   * 
   * 
   * @param places
   * @param login
   * @return
   */

  public abstract Places getSelectedPlace(Places places, State login);

  public abstract List<Surgery> getSurgeries(State state);

  /****
   * method setting ombokning
   * 
   * @param l
   */
  public abstract void setBookingTime(BookingTimeLocal bookingTime, State credentials);

}