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

import java.util.Map;

import se.vgregion.webbtidbok.ws.sectra.IRisReschedule;

/**
 * Creates Sectra web service connections.
 * This class should not know anything about State - all actions here
 * should be strictly WS-specific.
 * If Sectra services should diverge for any reason (versions etc), this is
 * the place to differentiate between services.
 *
 * @author andhen
 */
public class SectraBookingServiceFactory {

    private Map<String, IRisReschedule> portMap;
    private BookingMapperSectra bookingMapperSectra;
    
    public void setPortMap(Map<String, IRisReschedule> portMap) {
        this.portMap = portMap;
    }

    public void setBookingMapperSectra(BookingMapperSectra bookingMapperSectra) {
        this.bookingMapperSectra = bookingMapperSectra;
    }

    /**
     * Generate a new WS connection.
     * @param serviceId The id of the Sectra service to call.
     * @param patientNr The patient number.
     * @param examinationNr
     * @return
     */
    SectraBookingServiceInterface getServiceInstance(String serviceId, String patientNr, String examinationNr) {
        SectraBookingServiceImpl instance = new SectraBookingServiceImpl();

        if (portMap.containsKey(serviceId)) {
            instance.setThePort(portMap.get(serviceId));
        } else {
            throw new RuntimeException("Non-existing sectra service requested: " + serviceId);
        }
        instance.setPatientNr(patientNr);
        instance.setExaminationNr(examinationNr);
        instance.setBookingMapperSectra(bookingMapperSectra);

        return instance;
    }
}
