package se.vgregion.webbtidbok.domain;

import java.io.Serializable;
import java.util.Calendar;

public class BookingTime implements Serializable {

	private static final long serialVersionUID = 5023666003372389346L;
	private int bookingTimeId;
	private Calendar time;

	public void setBookingTimeId(int bookingTimeId) {
		this.bookingTimeId = bookingTimeId;
	}

	public int getBookingTimeId() {
		return bookingTimeId;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public Calendar getTime() {
		return time;
	}

}
