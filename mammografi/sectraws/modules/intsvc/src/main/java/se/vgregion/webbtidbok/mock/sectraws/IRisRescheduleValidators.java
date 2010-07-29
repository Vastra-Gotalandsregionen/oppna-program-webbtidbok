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
package se.vgregion.webbtidbok.mock.sectraws;

public class IRisRescheduleValidators {
	
	protected boolean validateMockLoginCall(String patientId, String password){
		
		boolean valid = false;
			if(password.equals("SEMSUSpugh") || password.equals("SERTEST00012345")){
				valid = true;
			}	
		return valid;
	}
	
	protected boolean validateGetBookingInfoCall(String patientId, String examinationNr){

		boolean valid = false;
		
			if(patientId.equalsIgnoreCase("1912121212") 
					&& examinationNr.equalsIgnoreCase("SERTEST00012345") 
					|| examinationNr.equalsIgnoreCase("SEMSUSpugh")){
				valid = true;
			}
		
		return valid;
	}
	
	protected boolean validateListSectionsCall(String examinationNr){
		
		boolean valid = false;
			if(examinationNr.equalsIgnoreCase("SERTEST00012345"))
				valid = true;
		return valid;
	}
}
