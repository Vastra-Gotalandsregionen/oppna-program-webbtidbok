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
package se.vgregion.webbtidbok.booking.sectra;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.vgregion.webbtidbok.ws.sectra.ArrayOfSection;
import se.vgregion.webbtidbok.ws.sectra.Section;

public class ArrayOfSectionLocal implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3376262673005919622L;
	private List<SectionLocal> sectionLocalList;

	
	public ArrayOfSectionLocal(ArrayOfSection secArray){
		this.sectionLocalList = getSectionLocalList(secArray);
		
	}
	
	
	public List<SectionLocal> getSectionLocalList() {
		return sectionLocalList;
	}
	public List<SectionLocal> getSectionLocalList(ArrayOfSection secArray) {
		List<SectionLocal> secListL = new ArrayList<SectionLocal>();
		List<Section> secList = secArray.getSection();
		Iterator<Section> iter = secList.iterator();
		
		while(iter.hasNext()){
			SectionLocal secL = new SectionLocal(iter.next());
			secListL.add(secL);
		}
		return secListL;
	}

	public void setSectionLocalList(List<SectionLocal> sectionLocalList) {
		this.sectionLocalList = sectionLocalList;
	}
	
	
	
	
}
