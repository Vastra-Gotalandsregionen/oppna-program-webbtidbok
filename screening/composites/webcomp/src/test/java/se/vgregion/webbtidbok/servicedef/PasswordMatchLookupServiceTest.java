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

public class PasswordMatchLookupServiceTest {

    private ServiceDefinition serviceDefinition;
    private PasswordMatchLookupService lookupService;

    @Before
    public void setUp() throws Exception {
        serviceDefinition = new ServiceDefinition();
        serviceDefinition.setServiceID("test");
        
        lookupService = new PasswordMatchLookupService();
        lookupService.setServiceDefinition(serviceDefinition);
        lookupService.setRegexp("^......SU..$");
    }

    @Test
    public void testBlankPassword() {
        State state = new State();
        state.setPasswd("");
        Assert.assertNull(lookupService.lookup(state));
    }

    @Test
    public void testSUPassword() {
        State state = new State();
        state.setPasswd("abcdefSUgh");
        Assert.assertEquals(serviceDefinition, lookupService.lookup(state));
    }

    @Test
    public void testBadLengthPasswords() {
        State state = new State();
        state.setPasswd("abcdeSUfg");
        Assert.assertNull(lookupService.lookup(state));
        state.setPasswd("abcdefgSUhi");
        Assert.assertNull(lookupService.lookup(state));
    }

    @Test
    public void testNUPassword() {
        State state = new State();
        state.setPasswd("abcdefNUgh");
        Assert.assertNull(lookupService.lookup(state));
    }

}
