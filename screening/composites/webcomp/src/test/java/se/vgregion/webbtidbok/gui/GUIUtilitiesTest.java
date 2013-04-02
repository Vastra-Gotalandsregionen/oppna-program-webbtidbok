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

package se.vgregion.webbtidbok.gui;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacadeDummy;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;

public class GUIUtilitiesTest {
    
    private GUIUtilities guiUtility; 

    @Before
    public void setUp() throws Exception {
        guiUtility = new GUIUtilities();
    }

    @Test
    public void testGetIsTimeListEmpty() {
        List<Integer> list = null;
        assertTrue(guiUtility.getIsTimeListEmpty(list));
        list = new ArrayList<Integer>();
        assertTrue(guiUtility.getIsTimeListEmpty(list));
        list.add(new Integer(1));
        assertFalse(guiUtility.getIsTimeListEmpty(list));
        list.add(Integer.valueOf(2));
        assertFalse(guiUtility.getIsTimeListEmpty(list));
    }

    @Test
    public void testSetCentralTidbokIDfromLocation() {
        State state = new State();
        state.setCentralTidbokID(0);
        
        guiUtility.setCentralTidbokIDfromLocation(state, "4");
        assertEquals(4, state.getCentralTidbokID());

        // Not numeric - should not update tidbokID.
        guiUtility.setCentralTidbokIDfromLocation(state, "test 3");
        assertEquals(4, state.getCentralTidbokID());
    }

    @Test
    public void testSetupLocations() {
        guiUtility.setBookingFacade(new TestFacade());
        
        State state = new State();
        Booking booking = new BookingSectra();

        Surgery surgery = new Surgery();
        surgery.setSurgeryId("test 2");
        surgery.setSurgeryName("Testmammo 2");
        surgery.setSurgeryAddress("Testaddress 2");
        booking.setSurgery(surgery);
        
        LocationHolder holder = guiUtility.setupLocations(booking, state);
        
        assertEquals("test 2", holder.getLocationId());
        assertEquals("Testmammo 2", holder.getCurrentLocation().getSurgeryName());
        assertEquals("Testaddress 2", holder.getCurrentLocation().getSurgeryAddress());
        
        assertEquals(2, holder.getSelectItems().size());
    }

    @Test
    public void testSetupLocationsNullBooking() {
        guiUtility.setBookingFacade(new TestFacade());
        State state = new State();

        LocationHolder holder = guiUtility.setupLocations(null, state);
        assertEquals("", holder.getLocationId());
        assertEquals("", holder.getCurrentLocation().getSurgeryName());
        assertEquals("", holder.getCurrentLocation().getSurgeryAddress());

    }
    
    private class TestFacade extends BookingFacadeDummy {

        @Override
        public List<Surgery> getAvailableSurgeries(State state) {
            List<Surgery> surgeries = new ArrayList<Surgery>();
            
            Surgery s1 = new Surgery();
            s1.setSurgeryId("test 1");
            s1.setSurgeryName("Testmammo 1");
            s1.setSurgeryAddress("Testaddress 1");
            surgeries.add(s1);
                
            Surgery s2 = new Surgery();
            s2.setSurgeryId("test 2");
            s2.setSurgeryName("Testmammo 2");
            s2.setSurgeryAddress("Testaddress 2");
            surgeries.add(s2);

            return surgeries;
        }
    }
    
}
