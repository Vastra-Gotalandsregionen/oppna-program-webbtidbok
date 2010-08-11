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




import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

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
	public SelectItemConverter(List<se.vgregion.webbtidbok.domain.BookingPlace> lList){
		items = new ArrayList<SelectItem>();
		for(se.vgregion.webbtidbok.domain.BookingPlace p : lList){
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
	public void setSelectItems(List<se.vgregion.webbtidbok.domain.BookingPlace> lList){
		
		items = new ArrayList<SelectItem>();
		for(se.vgregion.webbtidbok.domain.BookingPlace p : lList){
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
	
	public List<SelectItem> getSelectItems(List<se.vgregion.webbtidbok.domain.BookingPlace> lList){
		
		
		items = new ArrayList<SelectItem>();
		
		
		for(se.vgregion.webbtidbok.domain.BookingPlace p : lList){
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
