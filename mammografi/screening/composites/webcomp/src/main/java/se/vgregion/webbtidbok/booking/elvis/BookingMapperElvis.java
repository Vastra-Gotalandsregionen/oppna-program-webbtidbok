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
package se.vgregion.webbtidbok.booking.elvis;

import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.elvis.BookingElvis;
import se.vgregion.webbtidbok.ws.BookingResponse;

public class BookingMapperElvis {
	
	public Booking bookingMapping(BookingResponse bookingResponse) {
		BookingElvis booking = new BookingElvis();
		booking.setPatientName(bookingResponse.getNamn().getValue());
		booking.setSurgeryAddress(bookingResponse.getAddress().getValue());
		return booking;
	}
	
//	this.booking.setSurgeryAddress(br.getAddress().toString());
//	this.antOmbok = br.getAntalOmbok();
//	this.bokadTid = br.getBokadTid();
//	this.ctid = br.getCentralTidbokID();
//	this.epost = br.getEpost().toString();
//	this.extId = br.getExternalID().toString();
//	this.maxAntOmbok = br.getMaxAntalOmbok();
//	this.mobil = br.getMobilTel().toString();
//	this.mottagning = br.getMottagning().toString();
//	this.patientName = br.getNamn().toString();
//	this.pnr = br.getPnr().toString();
//	this.vardgivare = br.getVardgivare().toString();

}
