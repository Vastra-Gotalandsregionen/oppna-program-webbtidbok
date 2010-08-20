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

import java.util.Calendar;
import java.util.List;

import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;

/**
 * This is the interface to the Sectra web services.
 * Anything using this interface should only use business objects.
 * Anything below this interface should only use web service objects.
 * Use the BookingMapperSectra class to convert them if necessary.
 *
 * @author andhen
 */
public interface SectraBookingServiceInterface {

    public abstract Booking getBooking();

    public abstract List<Surgery> getSurgeries();

    public abstract List<Calendar> getFreeDays(Calendar startDate,
            Calendar endDate, String sectionId);

    public abstract List<BookingTime> getFreeTimes(Calendar startDate,
            Calendar endDate, String sectionId);
    
//	abstract public Booking reschedule(String examinationNr, String newTimeId,
//			XMLGregorianCalendar startTime, Boolean printNewNotice,
//			String rescheduleComment);
	// more booking stuff here as we go
}
