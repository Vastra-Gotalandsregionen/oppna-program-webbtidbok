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

package se.vgregion.webbtidbok.booking.sectra;

import java.util.HashMap;
import java.util.Map;

import se.vgregion.webbtidbok.booking.sectra.BookingMapperSectra;
import se.vgregion.webbtidbok.ws.sectra.IRisReschedule;

/**
 * Creates Sectra web service connections. This class should not know anything about State - all actions here should be strictly
 * WS-specific. If Sectra services should diverge for any reason (versions etc), this is the place to differentiate between
 * services.
 * 
 * @author andhen
 */
public class SectraBookingServiceFactory {
	private Map<String, Map> portMapContainer; // keeps SU and NU portMaps
	private Map<String, IRisReschedule> portMap; // for SU
	// private Map<String, se.vgregion.webbtidbok.ws.sectraNU.IRisReschedule> portMapNU;

	private BookingMapperSectra bookingMapperSectra;
	// private BookingMapperSectraNU bookingMapperSectraNU;

	// public BookingMapperSectraNU getBookingMapperSectraNU() {
	// return bookingMapperSectraNU;
	// }

	// public void setBookingMapperSectraNU(BookingMapperSectraNU bookingMapperSectraNU) {
	// this.bookingMapperSectraNU = bookingMapperSectraNU;
	// }

	private String area; // like NU or SU or NÄL(NAL)

	public void setPortMap(Map<String, IRisReschedule> portMap) {
		Map<String, se.vgregion.webbtidbok.ws.sectra.IRisReschedule> tmpMap = new HashMap<String, se.vgregion.webbtidbok.ws.sectra.IRisReschedule>();
		IRisReschedule tmp;

		if (portMap != null) {
			tmp = (se.vgregion.webbtidbok.ws.sectra.IRisReschedule) portMap.get("MAMMO_SU");
			tmpMap.put("MAMMO_SU", tmp);
			tmp = (se.vgregion.webbtidbok.ws.sectra.IRisReschedule) portMap.get("MAMMO_NU");
			tmpMap.put("MAMMO_NU", tmp);
			tmp = (se.vgregion.webbtidbok.ws.sectra.IRisReschedule) portMap.get("MAMMO_AVE");
			tmpMap.put("MAMMO_AVE", tmp);
		}

		// this.portMap = portMap;
		this.portMap = tmpMap;
	}

	// public void setPortMapNU(Map<String, se.vgregion.webbtidbok.ws.sectraNU.IRisReschedule> portMapNU) {
	// this.portMapNU = portMapNU;
	// }

	public void setPortMapContainer(Map<String, Map> portMapContainer) {
		this.portMapContainer = portMapContainer;
	}

	public void setBookingMapperSectra(BookingMapperSectra bookingMapperSectra) {
		this.bookingMapperSectra = bookingMapperSectra;
	}

	/**
	 * Generate a new WS connection.
	 * 
	 * @param serviceId
	 *            The id of the Sectra service to call.
	 * @param patientNr
	 *            The patient number.
	 * @param examinationNr
	 * @return
	 */
	SectraBookingServiceInterface getServiceInstance(String serviceId, String patientNr, String examinationNr) {
		SectraBookingServiceImpl instance = new SectraBookingServiceImpl();

		// if (portMapContainer != null) {
		// if (portMapContainer.get(portMap).containsKey(serviceId)) {
		// instance.setThePort(portMap.get(serviceId));
		// } else if (portMapContainer.get(portMapNU).containsKey(serviceId)) {
		// instance.setThePort((se.vgregion.webbtidbok.ws.sectra.IRisReschedule) portMapNU.get(serviceId));
		// } else {
		// throw new RuntimeException("Non-existing sectra service requested: " + serviceId);
		// }
		//
		// }

		if (portMap.containsKey(serviceId)) {
			IRisReschedule portInstance = (se.vgregion.webbtidbok.ws.sectra.IRisReschedule) portMap.get(serviceId);
			instance.setThePort(portInstance);
		} else {
			throw new RuntimeException("Non-existing sectra service requested: " + serviceId);
		}
		instance.setPatientNr(patientNr);
		instance.setExaminationNr(examinationNr);
		instance.setBookingMapperSectra(bookingMapperSectra);

		return instance;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea() {
		return area;
	}
}
