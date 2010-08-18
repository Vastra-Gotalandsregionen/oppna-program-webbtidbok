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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import se.vgregion.webbtidbok.domain.Surgery;

/**
 * Backing bean for the location select menu in the GUI. Holds the current location id, which is
 * updated by the location select menu. Also provides the collection of available locations, converting them
 * from Surgeries to SelectItems the menu can use.
 */
public class LocationHolder implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String locationId;
    private List<Surgery> availableLocations;

    /**
     * Create an empty holder, with no locations and no selected location.
     */
    public LocationHolder() {
        locationId = "";
        availableLocations = new ArrayList<Surgery>();
    }

    /**
     * Setter used by the select menu.
     * @param locationId The surgeryId of the unit the user chose in the menu.
     */
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    /**
     * Getter used by the select menu to choose the correct default value.
     * @return The currently selected surgeryId.
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * Used by the GUI to get information on the currently selected location.
     * @return
     */
    public Surgery getCurrentLocation() {
        if (locationId != null) {
            for (Surgery s : availableLocations) {
                if (locationId.equals(s.getSurgeryId())) {
                    return s;
                }
            }
        }
        
        // No match, just return an empty surgery so the GUI doesn't get null pointer.
        Surgery s = new Surgery();
        s.setSurgeryId("");
        s.setSurgeryName("");
        s.setSurgeryAddress("");
        return s;
    }
    
    /**
     * Set the surgeries available for rescheduling the current examination.
     * @param availableLocations A list of surgeries.
     */
    public void setAvailableLocations(List<Surgery> availableLocations) {
        this.availableLocations = availableLocations;
    }

    /**
     * Used to populate the select menu.
     * @return Select items describing the available surgeries.
     */
    public List<SelectItem> getSelectItems() {
        List<SelectItem> selectedItems = new ArrayList<SelectItem>();
        for (Surgery surgery : availableLocations) {
            SelectItem s = new SelectItem();
            s.setLabel(surgery.getSurgeryName());
            s.setValue(surgery.getSurgeryId());
            selectedItems.add(s);
        }
        return selectedItems;
    }
    
}
