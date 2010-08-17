package se.vgregion.webbtidbok.domain.elvis;

import se.vgregion.webbtidbok.domain.BookingTime;

public class BookingTimeElvis extends BookingTime {

	private static final long serialVersionUID = 1L;

	private int numbers;
	private String day = "";

	public BookingTimeElvis() {
		// TODO Auto-generated constructor stub
	}

	public void setDay(String s) {
		day = s;
	}

	public String getDay() {
		return day;
	}

	public void setNumbers(int number) {
		numbers = number;
	}

	public int getNumbers() {
		return numbers;
	}

}
