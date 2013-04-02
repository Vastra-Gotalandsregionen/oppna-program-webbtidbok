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

package se.vgregion.webbtidbok.booking;


import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.servicedef.ServiceDefinition;

public class BookingFactoryTest {

    private BookingFactory factory;
    private State state;
    private ServiceDefinition sd1;
    
    @Before
    public void setUp() {
        factory = new BookingFactoryImpl();

        ArrayList<ServiceDefinition> list = new ArrayList<ServiceDefinition>();
        sd1 = new ServiceDefinition();
        sd1.setServiceID("sd1");
        sd1.setBookingService(new BookingFacadeDummy());
        list.add(sd1);
        ServiceDefinition sd2 = new ServiceDefinition();
        sd2.setServiceID("sd2");
        sd2.setBookingService(new BookingFacadeDummy());
        list.add(sd2);
        ServiceDefinition sd3 = new ServiceDefinition();
        sd3.setServiceID("sd3");
        sd3.setBookingService(new BookingFacadeDummy());
        list.add(sd3);

        ((BookingFactoryImpl)factory).setServiceDefinitions(list);

        state = new State();
    }
    
    @Test
    public void testGettingServiceDefinition() {
        state.setService(sd1.getServiceID());
        Assert.assertEquals(sd1.getBookingService(), factory.getService(state));
    }
    
    @Test(expected = RuntimeException.class)
    public void testGettingNonExistingServiceDefinition() {
        state.setService("nonexisting");
        factory.getService(state);
    }
        
    @Test(expected = RuntimeException.class)
    public void testGettingNullServiceDefinition() {
        state.setService(null);
        Assert.assertNull(factory.getService(state));
    }
}
