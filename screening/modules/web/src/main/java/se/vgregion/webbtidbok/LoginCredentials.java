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

/**
 * This bean stores the user credentials
 */

public class LoginCredentials implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pnr = "";
    private String passwd = "";
    
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

    public String toString() {
    	return "[User Credentials: Personnummer = " + pnr + ", Password = " + passwd + "]";
    }
}