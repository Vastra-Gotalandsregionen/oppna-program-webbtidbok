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

import java.io.*;
import javax.faces.model.*;

public class Places implements Serializable {
	private static final long serialVersionUID = 1L;
	private int placesId = 0;
	private String address = "";
	private String clinic = "";
	private String representationString = "";
	
	//private HtmlSelectOneMenu selectItem = new HtmlSelectOneMenu();
	private SelectItem selectedItem = new SelectItem();
	public void setPlacesId(int id){
		placesId = id;
	}
	
	public int getPlacesId(){
		return placesId;
	}
	
	public void setClinic(String value){
		clinic = value;
	}
	
	public void setAddress(String value){
		address = value;
	}
	
	public String getClinic(){
		return clinic;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setSelectItem(SelectItem value){
		selectedItem = value;
		System.out.println("selectItem: " + selectedItem.toString());
	}
	
	public SelectItem getSelectItem(){
		System.out.println("selectItem: " + selectedItem.toString());
		return selectedItem;
	}
	
	
	public String getRepresentationPlace(){
		if(representationString.equals("")){
			setRepresentationPlace();
			return representationString;
		}
		else{
			
			return getClinic() + ", " + getClinic();
			
		}
	}
	
	public void setRepresentationPlace(){
		
		representationString =  getClinic() + ", " + getClinic();
		
	}
	
	public void setRepresentationPlace(String s){
		
		representationString = s;
		
	}
	
	
	
	/*
	public void setSelectItem(HtmlSelectOneMenu value){
		selectItem.setValue(value);
		//System.out.println("selectItem: " + selectItem.getId());
	}
	
	public HtmlSelectOneMenu getSelectItem(){
		return selectItem;
	}
	*/
	
}
