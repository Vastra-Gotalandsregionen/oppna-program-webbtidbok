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

import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.generated.sectra.Section;
import se.vgregion.webbtidbok.generated.sectra.TimeBlock;

public class TimeBlockLocal implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3845360635811218533L;
	private String id;
	private XMLGregorianCalendar startTime;
	private int length;
	SectionLocal section;
	
	
	
	public TimeBlockLocal(TimeBlock tb) {
		super();
		this.id = tb.getId();
		this.startTime = tb.getStartTime(); 
		this.length = tb.getLength();
		setSection(section); 
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public XMLGregorianCalendar getStartTime() {
		return startTime;
	}
	public void setStartTime(XMLGregorianCalendar startTime) {
		this.startTime = startTime;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public SectionLocal getSection() {
		return section;
	}
	public void setSection(SectionLocal section) {
		this.section = section;
	}
	//extracting Section into SectionLocal
	public void setSection(Section section) {
		this.section.setSecAddress(section.getAddress());
		this.section.setSecDescription(section.getDescription());
		this.section.setSecId(section.getId());
		this.section.setSecMail(section.getMail());
		this.section.setSecName(section.getName());
		this.section.setSecPhone(section.getPhone());
	}

}
