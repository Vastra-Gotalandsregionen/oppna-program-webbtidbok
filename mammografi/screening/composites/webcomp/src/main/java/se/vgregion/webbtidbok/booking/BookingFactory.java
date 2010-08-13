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
package se.vgregion.webbtidbok.booking;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.servicedef.ServiceDefinition;

public class BookingFactory {

    private Map<String, ServiceDefinition> serviceDefinitions;
    
    public void setServiceDefinitions(List<ServiceDefinition> serviceDefinitionsList) {
        serviceDefinitions = new HashMap<String, ServiceDefinition>();
        for(ServiceDefinition sd : serviceDefinitionsList) {
            serviceDefinitions.put(sd.getServiceID(), sd);
        }
    }

    public BookingFacade getService(State loginCredentials) {
        return getService(loginCredentials.getService());
    }
    
    public BookingFacade getService(String serviceId) {
        if(serviceDefinitions.containsKey(serviceId)) {
            return serviceDefinitions.get(serviceId).getBookingService();
        } else {
            throw new RuntimeException("Trying to acquire unknown service definition: " + serviceId);
        }
    }

}
