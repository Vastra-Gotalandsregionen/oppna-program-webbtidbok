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

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.booking.BookingWrapper;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;

public class ElvisBookingFacadeImpl implements BookingFacade {

    private WebServiceHelper helper;

    public void setHelper(WebServiceHelper webServiceHelper) {
        this.helper = webServiceHelper;
    }

    @Override
    public boolean login(State state) {
    
    	BookingRequest request = helper.getQueryWSRequest(state);
        BookingResponse response = helper.getQueryWS(request);
        state.setBookingResponse(response);

        return response != null;
    }
    
    public BookingWrapper getBookingInfo(State state){
    	
    	BookingRequest request =  helper.getQueryWSRequest(state);
    	BookingResponse response = helper.getQueryWS(request);
    	BookingWrapper bw = new BookingWrapper(response);

    	return bw;
    }


}
