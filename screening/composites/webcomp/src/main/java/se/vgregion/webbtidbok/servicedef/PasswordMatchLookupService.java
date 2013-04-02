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

import java.util.regex.Pattern;

import se.vgregion.webbtidbok.State;

public class PasswordMatchLookupService implements LookupService {

    private Pattern regexp;
    private ServiceDefinition serviceDefinition;
    
    public void setRegexp(String regexpString) {
        this.regexp = Pattern.compile(regexpString);
    }

    public void setServiceDefinition(ServiceDefinition serviceDefinition) {
        this.serviceDefinition = serviceDefinition;
    }

    @Override
    public ServiceDefinition lookup(State state) {
        return regexp.matcher(state.getPasswd()).matches() ? serviceDefinition : null;
    }

}
