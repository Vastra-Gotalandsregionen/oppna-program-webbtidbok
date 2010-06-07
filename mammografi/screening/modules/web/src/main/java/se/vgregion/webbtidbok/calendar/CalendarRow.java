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
package se.vgregion.webbtidbok.calendar;

import java.io.Serializable;
import java.util.List;

public class CalendarRow implements Serializable {
	private static final long serialVersionUID = 1L;

	private String mon, tue, wed, thu, fri, sat, sun;
	private boolean isLink[] = new boolean[7];
	private boolean isFirst[] = new boolean[7];
	
	private int id = 0;
	public void setLink(int id, boolean value) {
		isLink[id] = value;
	}
	
	public boolean getLink(int id) {
		return isLink[id];
	}
	public void setLinks(boolean[] values) {
		for(int i = 0; i < values.length; i++) {
			isLink[i] = values[i];
			isFirst[i] = true;
		}
	}
		
	public int getIsLink() {
		int ret = 0;
		if(isLink[id]) {
			ret = 1;
		}
		else {
			ret = 0;
			id++;
			if(id == 7) {
				id = 0;
			}
		}
		System.out.println("link status: " + ret);
		return ret;
	}
		
	public void setRowStrings(List<String> row) {
		String[] temp = new String[] {"", "", "", "", "", "", ""};
		
		for(int i = 0; i < row.size(); i++) {
			if(!("".equals(row.get(i)))) {
				temp[i] = "" + row.get(i);
			}
		}
		mon = "" + temp[0];
		tue = "" + temp[1];
		wed = "" + temp[2];
		thu = "" + temp[3];
		fri = "" + temp[4];
		sat = "" + temp[5];
		sun = "" + temp[6];
	}
	
	public void setRow(List<Integer> row) {
		String[] temp = new String[] {"", "", "", "", "", "", ""};
		
		for(int i = 0; i < row.size(); i++) {
			if(row.get(i) != 0) {
				temp[i] = "" + row.get(i);
			}
		}
		mon = "" + temp[0];
		tue = "" + temp[1];
		wed = "" + temp[2];
		thu = "" + temp[3];
		fri = "" + temp[4];
		sat = "" + temp[5];
		sun = "" + temp[6];
	}
	
	public void setById(int day, String s) {
		switch(day) {
		case 0:
			mon = s;
			break;
		case 1:
			tue = s;
			break;
		case 2:
			wed = s;
			break;
		case 3:
			wed = s;
			break;
		case 4:
			wed = s;
			break;
		case 5:
			wed = s;
			break;
		case 6:
			wed = s;
			break;
		default:
			wed = s;
		}
	}
	
	private void print() {
		System.out.println(      mon + " " +       tue + " " +       wed + " " +       thu + " " +       fri + " " +       sat + " " + sun);
		for(boolean b : isLink) {
			if(b)
				System.out.print("1 ");
			else
				System.out.print("0 ");
		}
		System.out.println();
	}
	
	public void setMon(String mon) {
		this.mon = mon;
	}

	public String getMon() {
		print();
		return mon;
	}

	public void setTue(String tue) {
		this.tue = tue;
	}

	public String getTue() {
		return tue;
	}

	public void setWed(String wed) {
		this.wed = wed;
	}

	public String getWed() {
		return wed;
	}

	public void setThu(String thu) {
		this.thu = thu;
	}

	public String getThu() {
		return thu;
	}

	public void setFri(String fri) {
		this.fri = fri;
	}

	public String getFri() {
		return fri;
	}

	public void setSat(String sat) {
		this.sat = sat;
	}

	public String getSat() {
		return sat;
	}

	public void setSun(String sun) {
		this.sun = sun;
	}

	public String getSun() {
		return sun;
	}
}
