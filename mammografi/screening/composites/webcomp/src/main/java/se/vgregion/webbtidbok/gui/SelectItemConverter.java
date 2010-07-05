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
package se.vgregion.webbtidbok.gui;




import javax.faces.model.*;
import java.util.*;
import java.lang.*;
import java.io.*;

import se.vgregion.webbtidbok.*;
import se.vgregion.webbtidbok.booking.elvis.BookingPlaceLocal;
import se.vgregion.webbtidbok.ws.BookingPlace;

public class SelectItemConverter {
	
	List<SelectItem> items;
	
	
	/**
	 * 
	 * Constructor
	 */
	public SelectItemConverter(){
		
	}
	
	
	/**
	 * Constructor
	 * 
	 */
	public SelectItemConverter(List<BookingPlaceLocal> lList){
		items = new ArrayList<SelectItem>();
		for(BookingPlaceLocal p : lList){
			SelectItem s = new SelectItem();
			s.setLabel(p.getClinic());
			s.setValue(p.getCentralTimeBookId());
			
			items.add(s);
		}
		
		
	}
	
	/**
	 * Set SelectedItems from BookingPlaceLocal List
	 * 
	 * @param lList
	 */
	public void setSelectItems(List<BookingPlaceLocal> lList){
		
		items = new ArrayList<SelectItem>();
		for(BookingPlaceLocal p : lList){
			SelectItem s = new SelectItem();
			s.setLabel(p.getClinic());
			s.setValue(p.getCentralTimeBookId());
			
			items.add(s);
		}
		
	}
	
	
	
	/**
	 * get SelectedItems from BookingPlaceLocal List
	 * 
	 * @param lList
	 */
	
	public List<SelectItem> getSelectItems(List<BookingPlaceLocal> lList){
		
		
		items = new ArrayList<SelectItem>();
		
		
		for(BookingPlaceLocal p : lList){
			SelectItem s = new SelectItem();
			s.setLabel(p.getClinic());
			s.setValue(p.getCentralTimeBookId());
			
			items.add(s);
		}
		
		return items;
	}
	
	/**
	 * Get SelectedItems 
	 * 
	 * @param lList
	 */
	public List<SelectItem> getSelectItems(){
		return items;
	}
}
