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
package se.vgregion.webbtidbok.booking.sectra;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.ws.sectra.ObjectFactory;
import se.vgregion.webbtidbok.ws.sectra.Section;

public class BookingMapperSectraTest {

  private static final String ADDRESS = "address";
  private static final String NAME = "name";
  private static final String ID = "123";
  private BookingMapperSectra bookingMapperSectra;
  private ObjectFactory objectFactory;

  @Before
  public void setUp() throws Exception {
    bookingMapperSectra = new BookingMapperSectra();
    objectFactory = new ObjectFactory();
  }

  @Test
  public void testBookingMapping() {
    //fail("Not yet implemented");
  }

  @Test
  public void testSurgeryMapping() {
    Section section = new Section();
    
    section.setId(objectFactory.createString(ID));
    section.setName(objectFactory.createString(NAME));
    section.setAddress(objectFactory.createString(ADDRESS));
    		
    Surgery surgeryMapping = bookingMapperSectra.surgeryMapping(section);
    assertEquals(ID, surgeryMapping.getSurgeryId());
    assertEquals(NAME, surgeryMapping.getSurgeryName());
    assertEquals(ADDRESS, surgeryMapping.getSurgeryAddress());
  }

}
