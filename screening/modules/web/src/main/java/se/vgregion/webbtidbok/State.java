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
package se.vgregion.webbtidbok;

import java.io.Serializable;
import se.vgregion.webbtidbok.ws.*;


/**
 * This bean stores the user credentials
 */

public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pnr = "";
    private String passwd = "";
    private int centralTidbokID = 0;
    //private BookingResponse bookingResponse;
    //private BookingRequest bookingRequest;
    
    private static boolean loggedIn = false;
    
    
    public String getPnr() {
    	return pnr;
    }
    
    public String getPasswd() {
    	return passwd;
    }
    
    public void setPnr(String pnr) {
    	this.pnr = pnr;
    }
    
    public void setPasswd(String passwd) {
    	this.passwd = passwd;
    }

    public void setLoggedIn(boolean loggedIn){
    	this.loggedIn = loggedIn;
    }
    
    public boolean isLoggedIn(){
    	return loggedIn;
    }
 
	public void setCentralTidbokID(int centralTidbokID) {
		this.centralTidbokID = centralTidbokID;
	}

	public int getCentralTidbokID() {
		return centralTidbokID;
	}
    
    public String toString() {
    	return "[User Credentials: Personnummer = " + pnr + ", Password = " + passwd + "]";
    }
}