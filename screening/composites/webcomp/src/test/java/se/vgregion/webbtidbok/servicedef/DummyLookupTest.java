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


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.State;

public class DummyLookupTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSettingServiceDefinition() {
        State state = new State();
        ServiceDefinition sd = new ServiceDefinition();
        sd.setServiceID("test");
        DummyLookup dummy = new DummyLookup();

        Assert.assertNull(state.getService());
        dummy.setServiceDefinition(sd);
        Assert.assertEquals(sd, dummy.lookup(state));
    }
}
