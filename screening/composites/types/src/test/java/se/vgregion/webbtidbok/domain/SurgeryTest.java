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
package se.vgregion.webbtidbok.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SurgeryTest {

    private Surgery surgery;
    
    @Before
    public void setUp() throws Exception {
        surgery = new Surgery();
    }

    @Test
    public void testSetSurgeryId() {
        surgery.setSurgeryId("test");
        assertEquals("test", surgery.getSurgeryId());
        surgery.setSurgeryId("annat");
        assertEquals("annat", surgery.getSurgeryId());
    }

    @Test
    public void testSetSurgeryName() {
        surgery.setSurgeryName("Mammo");
        assertEquals("Mammo", surgery.getSurgeryName());
        surgery.setSurgeryName("Bukaorta");
        assertEquals("Bukaorta", surgery.getSurgeryName());
    }

    @Test
    public void testSetSurgeryAddress() {
        surgery.setSurgeryAddress("Mammogatan");
        assertEquals("Mammogatan", surgery.getSurgeryAddress());
        surgery.setSurgeryAddress("Bukaortagatan");
        assertEquals("Bukaortagatan", surgery.getSurgeryAddress());
    }

    @Test
    public void testGetFullAddress() {
        surgery.setSurgeryName("Mammo");
        surgery.setSurgeryAddress("Mammogatan");
        assertEquals("Mammo, Mammogatan", surgery.getFullAddress());
        surgery.setSurgeryAddress("Mammostigen");
        assertEquals("Mammo, Mammostigen", surgery.getFullAddress());
    }

}
