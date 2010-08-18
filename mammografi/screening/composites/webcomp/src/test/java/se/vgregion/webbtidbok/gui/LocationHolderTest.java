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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.domain.Surgery;

public class LocationHolderTest {

    private LocationHolder holder;
    
    @Before
    public void setUp() throws Exception {
        holder = new LocationHolder();
    }

    @Test
    public void testSetLocationId() {
        assertEquals("", holder.getLocationId());
        holder.setLocationId("TEST");
        assertEquals("TEST", holder.getLocationId());
    }

    @Test
    public void testSetAvailableLocations() {
        assertTrue(holder.getSelectItems().isEmpty());
        
        holder.setAvailableLocations(createTestSurgeries());
        List<SelectItem> items = holder.getSelectItems();
        
        assertEquals(2, items.size());
        assertEquals("Testmammo 1", items.get(0).getLabel());
        assertEquals("test 1", items.get(0).getValue());

        assertEquals("Testmammo 2", items.get(1).getLabel());
        assertEquals("test 2", items.get(1).getValue());
    }
    
    @Test
    public void testGetCurrentLocation() {
        holder.setAvailableLocations(createTestSurgeries());
        
        Surgery s1 = holder.getCurrentLocation();
        assertEquals("", s1.getSurgeryId());
        assertEquals("", s1.getSurgeryName());
        assertEquals("", s1.getSurgeryAddress());
        
        holder.setLocationId("TEST");
        Surgery s2 = holder.getCurrentLocation();
        assertEquals("", s2.getSurgeryId());
        assertEquals("", s2.getSurgeryName());
        assertEquals("", s2.getSurgeryAddress());
        
        holder.setLocationId("test 1");
        Surgery s3 = holder.getCurrentLocation();
        assertEquals("test 1", s3.getSurgeryId());
        assertEquals("Testmammo 1", s3.getSurgeryName());
        assertEquals("Testaddress 1", s3.getSurgeryAddress());
    }

    private List<Surgery> createTestSurgeries() {
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
