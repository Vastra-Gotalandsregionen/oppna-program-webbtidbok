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

import java.util.List;

import se.vgregion.webbtidbok.State;

/**
 * This class chains several lookup services, returning the first one matching.
 * 
 * @author anders.henriksson@knowit.se
 */
public class ChainLookupService implements LookupService {

    private List<LookupService> lookupChain;
    
    public void setLookupChain(List<LookupService> lookupChain) {
        this.lookupChain = lookupChain;
    }

    @Override
    public ServiceDefinition lookup(State state) {
        for (LookupService ls : lookupChain) {
            ServiceDefinition sd = ls.lookup(state);
            if (sd != null) {
                return sd;
            }
        }
        return null;
    }

}
