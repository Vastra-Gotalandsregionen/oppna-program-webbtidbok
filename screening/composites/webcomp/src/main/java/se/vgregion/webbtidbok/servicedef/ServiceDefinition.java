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

import se.vgregion.webbtidbok.booking.BookingFacade;

public class ServiceDefinition {

    private String serviceID;
    private String messageBundleBase;
    private BookingFacade bookingService;
    /**
     * Stores surgery IDs which should be either included or excluded from the places selection
     * includeClinics = all surgeries with IDs not matching any IDs in includeClinics will be removed
     * 		empty array equals include all.
     * excludeClinics = all surgeries with IDs matching any ID in excludeClinics will be removed
     */
    private String[] includeClinics = {}, excludeClinics = {};
    

    public String[] getIncludeClinics() {
		return includeClinics;
	}

	public void setIncludeClinics(String[] includeClinics) {
		this.includeClinics = includeClinics;
	}

	public String[] getExcludeClinics() {
		return excludeClinics;
	}

	public void setExcludeClinics(String[] excludeClinics) {
		this.excludeClinics = excludeClinics;
	}

	public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getMessageBundleBase() {
        return messageBundleBase;
    }

    public void setMessageBundleBase(String messageBundleBase) {
        this.messageBundleBase = messageBundleBase;
    }

    public BookingFacade getBookingService() {
        return bookingService;
    }

    public void setBookingService(BookingFacade bookingService) {
        this.bookingService = bookingService;
    }

}
