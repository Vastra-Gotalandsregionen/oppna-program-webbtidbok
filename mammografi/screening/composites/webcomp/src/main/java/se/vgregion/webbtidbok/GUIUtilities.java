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

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.domain.Surgery;

/**
 * This class is a temporary home for methods which previously were part of the
 * booking service, but don't belong there. In order not to pollute the BookingFacade
 * they have been moved here. If you find a better home for a method, please move it.
 */
public class GUIUtilities {

    /* Please refrain from adding private variables to this class
     * unless strictly necessary. */
    
    private static final Logger sLogger = Logger.getLogger("se.vgregion.webbtidbok.logging");
    
    private BookingFacade bookingFacade;
    
    public void setBookingFacade(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }


    public List<SelectItem> getBookingPlaceSelectItems(State state) {
        List<Surgery> surgeries = bookingFacade.getAvailableSurgeries(state);
        return getBookingPlaceSelectItems(surgeries);
    }

    private List<SelectItem> getBookingPlaceSelectItems(List<Surgery> surgeries) {
        List<SelectItem> selectedItems = new ArrayList<SelectItem>();
        for (Surgery surgery : surgeries) {
            SelectItem s = new SelectItem();
            s.setLabel(surgery.getSurgeryName());
            s.setValue(surgery.getSurgeryId());
            selectedItems.add(s);
        }
        return selectedItems;
    }

    
    public int getSelectedDefaultItem(State state) {
        if (state.getCentralTidbokID() != 0) {
            return state.getCentralTidbokID();
        } else {
            // TODO: Get booking and retrieve id.
            return 0;
        }
    }

// TODO: This is the corresponding code removed from booking.elvis.BookingService
//
//    public int getSelectedDefaultItem(State loginCredentials) {
//
//        if (this.isFirstPlaces()) {
//
//            request = helper.getQueryWSRequest(loginCredentials);
//            response = helper.getQueryWS(request);
//            int centralTimeBookingId = response.getCentralTidbokID();
//
//            this.setFirstPlacesBoolean(false);
//            return centralTimeBookingId;
//
//        } else {
//            System.out.println("BookingServices.getSelectedDefaultItem: "
//                    + loginCredentials.getCentralTidbokID());
//
//            return loginCredentials.getCentralTidbokID();
//        }
//
//    }

    
    
    // TODO: This method should be replaced with something using the
    //       Surgery object from the booking.
    public Places getSelectedPlace(Places places, State state) {
        Places place = new Places();
        List<Surgery> surgeries = bookingFacade.getAvailableSurgeries(state);
        for (Surgery bl : surgeries) {
            if ((bl.getSurgeryId() != null) &&
                    bl.getSurgeryId().equals(Integer.toString(places.getPlacesId()))) {
                place.setPlacesId(new Integer(bl.getSurgeryId()));
                place.setClinic(bl.getSurgeryName());
                place.setAddress(bl.getSurgeryAddress());
                place.setRepresentationPlace();
                break;
            }
        }

        if (place != null) {
            sLogger.debug("Selected place clinic: " + place.getClinic());
            sLogger.debug("Selected place address: " + place.getAddress());
            sLogger.debug("Selected place id: " + place.getPlacesId());
            sLogger.debug("Selected place representation place: "
                    + place.getRepresentationPlace());
        }
        return place;
    }

    public void setSelectedItem(Places places, State state) {
        System.out.println("BookingServices.setSelectedItem: "
                + places.getPlacesId());
        state.setCentralTidbokID(places.getPlacesId());
        System.out.println("BookingServices.state.centraltidbokid: "
                + state.getCentralTidbokID());
    }

    public boolean getIsTimeListEmpty(State state) {
        // TODO: Check how this is best implemented.
        //       Should return true if we have bookable times.
        //       Maybe we need a refactoring of some flow logic,
        //       i.e. store the list of bookable times in a flow variable.
        return false;
    }

}
