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
package se.vgregion.webbtidbok.servicedef;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.State;

public class ChainLookupServiceTest {

    private ServiceDefinition sd1;
    private ServiceDefinition sd2;
    private ChainLookupService lookupService;

    @Before
    public void setUp() throws Exception {
        sd1 = new ServiceDefinition();
        sd1.setServiceID("testNU");
        sd2 = new ServiceDefinition();
        sd2.setServiceID("testSU");
        
        lookupService = new ChainLookupService();
        List<LookupService> lookupChain = new ArrayList<LookupService>();

        PasswordMatchLookupService lookup1 = new PasswordMatchLookupService();
        lookup1.setServiceDefinition(sd1);
        lookup1.setRegexp("^......NU..$");
        lookupChain.add(lookup1);
        
        PasswordMatchLookupService lookup2 = new PasswordMatchLookupService();
        lookup2.setServiceDefinition(sd2);
        lookup2.setRegexp("^SEMSUS....$");
        lookupChain.add(lookup2);

        lookupService.setLookupChain(lookupChain);
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
        state.setPasswd("SEMSUSpugh");
        Assert.assertEquals(sd2, lookupService.lookup(state));
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
        Assert.assertEquals(sd1, lookupService.lookup(state));
    }

}
