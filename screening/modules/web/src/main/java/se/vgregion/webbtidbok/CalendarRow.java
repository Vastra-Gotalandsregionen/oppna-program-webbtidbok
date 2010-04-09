package se.vgregion.webbtidbok;

import java.io.Serializable;
import java.util.List;

public class CalendarRow implements Serializable {
	private static final long serialVersionUID = 1L;

	private String mon, tue, wed, thu, fri, sat, sun;

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
	public void setMon(String mon) {
		this.mon = mon;
	}

	public String getMon() {
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
