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

package se.vgregion.webbtidbok.servicedef;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacadeDummy;

public class LoginLookupServiceTest {

    private class BookingFacadeDummyImpl extends BookingFacadeDummy {

        public boolean loginSuccessSwitch = false;
        
        public boolean login(State state) {
            return loginSuccessSwitch;
        }

    }
    
    private ServiceDefinition serviceDefinition;
    private LoginLookupService lookupService;
    private BookingFacadeDummyImpl booking;

    @Before
    public void setUp() throws Exception {
        booking = new BookingFacadeDummyImpl();
        
        serviceDefinition = new ServiceDefinition();
        serviceDefinition.setServiceID("test");
        serviceDefinition.setBookingService(booking);

        lookupService = new LoginLookupService();
        lookupService.setServiceDefinition(serviceDefinition);
    }

    @Test
    public void testFailingLogin() {
        booking.loginSuccessSwitch = false;
        State state = new State();
        Assert.assertNull(lookupService.lookup(state));
    }
    
    @Test
    public void testSucceedingLogin() {
        booking.loginSuccessSwitch = true;
        State state = new State();
        Assert.assertEquals(serviceDefinition, lookupService.lookup(state));
    }

}
