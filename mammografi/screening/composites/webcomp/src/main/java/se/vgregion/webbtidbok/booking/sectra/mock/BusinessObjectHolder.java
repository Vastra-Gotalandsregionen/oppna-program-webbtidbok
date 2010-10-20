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
package se.vgregion.webbtidbok.booking.sectra.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.ObjectFactory;
import se.vgregion.webbtidbok.ws.sectra.Section;
import se.vgregion.webbtidbok.ws.sectra.TimeBlock;

public class BusinessObjectHolder {

	protected ObjectFactory objectFactory;
	protected DatatypeFactory datatypeFactory;

	private Map<String, Section> sections;
	private Map<String, Section> staticSections;
	private Map<String, TimeBlock> timeblocks;
	private Map<String, BookingInfo> bookings;

	public BusinessObjectHolder(List<Map<String, String>> sectionList, List<Map<String, String>> timeblockList,
			List<Map<String, String>> bookingList) {
		objectFactory = new ObjectFactory();
		try {
			datatypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}

		sections = buildSections(sectionList);
		timeblocks = buildTimeblocks(timeblockList);
		bookings = buildBookings(bookingList);
	}

	public BusinessObjectHolder(List<Map<String, String>> sectionList) {
		objectFactory = new ObjectFactory();
		try {
			datatypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}

		staticSections = buildStaticSections(sectionList);
	}

	// Methods for accessing the data in the holder.

	/**
	 * Get booking information.
	 * 
	 * @param patientId
	 *            The patient id number.
	 * @param examNo
	 *            The examination number.
	 * @return The booking information, or null if no booking exists or the patient id does not match the examination number.
	 */
	public BookingInfo getBookingInfo(String patientId, String examNo) {
		BookingInfo info = bookings.get(examNo);
		if (info == null) {
			return null;
		} else if (!info.getPatientId().getValue().equals(patientId)) {
			return null;
		} else {
			return info;
		}
	}

	/**
	 * Get bookings for a patient.
	 * 
	 * @param patientId
	 *            The patient id number.
	 * @return The booking information, or null if no booking exists.
	 */
	public List<BookingInfo> getBookings(String patientId) {
		List<BookingInfo> matches = new ArrayList<BookingInfo>();

		for (BookingInfo infoEntry : bookings.values()) {
			if (infoEntry.getPatientId().getValue().equals(patientId)) {
				matches.add(infoEntry);
			}
		}

		return matches;
	}

	public List<XMLGregorianCalendar> listFreeDays(XMLGregorianCalendar startDate, XMLGregorianCalendar endDate, String examNo,
			String sectionId) throws InvalidExamNoException, InvalidSectionIdException {
		List<TimeBlock> freeBlocks = listFreeTimes(startDate, endDate, examNo, sectionId);

		SortedMap<Long, XMLGregorianCalendar> freeDays = new TreeMap<Long, XMLGregorianCalendar>();
		for (TimeBlock block : freeBlocks) {
			XMLGregorianCalendar blockStart = block.getStartTime();
			XMLGregorianCalendar dayStart = datatypeFactory.newXMLGregorianCalendar(blockStart.getYear(), blockStart.getMonth(),
					blockStart.getDay(), 0, 0, 0, 0, blockStart.getTimezone());
			freeDays.put(Long.valueOf(dayStart.toGregorianCalendar().getTimeInMillis()), dayStart);
		}

		return new ArrayList<XMLGregorianCalendar>(freeDays.values());
	}

	public List<TimeBlock> listFreeTimes(XMLGregorianCalendar startDate, XMLGregorianCalendar endDate, String examNo,
			String sectionId) throws InvalidExamNoException, InvalidSectionIdException {
		if (!bookings.containsKey(examNo)) {
			throw new InvalidExamNoException();
		}
		if (!sections.containsKey(sectionId)) {
			throw new InvalidSectionIdException();
		}

		List<TimeBlock> freeBlocks = filterBlocksBySection(sectionId, filterBlocksInSpan(startDate, endDate, getFreeTimes()));

		return freeBlocks;
	}

	public List<Section> listSections(String examNo) throws InvalidExamNoException {
		if (!bookings.containsKey(examNo)) {
			throw new InvalidExamNoException();
		}

		return new ArrayList<Section>(sections.values());
	}

	public BookingInfo reschedule(String examNo, String newTimeId, XMLGregorianCalendar startTime) throws InvalidExamNoException,
			InvalidTimeIdException, TimeAlreadyBookedException, UnknownFailureException {
		if (!bookings.containsKey(examNo)) {
			throw new InvalidExamNoException();
		}
		if (!timeblocks.containsKey(newTimeId)) {
			throw new InvalidTimeIdException();
		}

		TimeBlock newTime = timeblocks.get(newTimeId);
		if (!getFreeTimes().contains(newTime)) {
			throw new TimeAlreadyBookedException();
		}
		if (newTime.getStartTime().compare(startTime) != 0) {
			throw new UnknownFailureException();
		}

		BookingInfo booking = bookings.get(examNo);
		booking.setBookedTime(objectFactory.createBookingInfoBookedTime(newTime));

		return booking;
	}

	// Exception classes.

	public static class InvalidExamNoException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	public static class InvalidSectionIdException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	public static class InvalidTimeIdException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	public static class TimeAlreadyBookedException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	public static class UnknownFailureException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	// Utility methods for object retrieval.

	private List<TimeBlock> filterBlocksBySection(String sectionId, List<TimeBlock> blocks) {
		Section section = sections.get(sectionId);
		List<TimeBlock> blocksInSection = new ArrayList<TimeBlock>();

		for (TimeBlock block : blocks) {
			if (section.equals(block.getSection().getValue())) {
				blocksInSection.add(block);
			}
		}

		return blocksInSection;
	}

	private List<TimeBlock> filterBlocksInSpan(XMLGregorianCalendar startDate, XMLGregorianCalendar endDate,
			List<TimeBlock> blocks) {
		List<TimeBlock> blocksInSpan = new ArrayList<TimeBlock>();

		for (TimeBlock block : blocks) {
			XMLGregorianCalendar blockStart = block.getStartTime();
			if (blockStart.compare(startDate) == DatatypeConstants.GREATER
					&& blockStart.compare(endDate) == DatatypeConstants.LESSER) {
				blocksInSpan.add(block);
			}
		}

		return blocksInSpan;
	}

	private List<TimeBlock> getFreeTimes() {
		List<TimeBlock> freeTimes = new ArrayList<TimeBlock>();
		Set<TimeBlock> bookedTimes = new HashSet<TimeBlock>();

		for (BookingInfo bookingEntry : bookings.values()) {
			bookedTimes.add(bookingEntry.getBookedTime().getValue());
		}

		for (TimeBlock block : timeblocks.values()) {
			if (!bookedTimes.contains(block)) {
				freeTimes.add(block);
			}
		}

		return freeTimes;
	}

	// Methods below are for constructing the business objects.

	private Map<String, Section> buildStaticSections(List<Map<String, String>> sectionList) {
		HashMap<String, Section> map = new HashMap<String, Section>();

		for (Map<String, String> entry : sectionList) {
			Section section = buildStaticSectionEntry(entry);
			map.put(section.getId().getValue(), section);
		}

		return map;
	}

	private Section buildStaticSectionEntry(Map<String, String> sectionInfo) {
		Section section = objectFactory.createSection();

		section.setId(objectFactory.createSectionId(sectionInfo.get("id")));
		section.setName(objectFactory.createSectionName(sectionInfo.get("name")));

		return section;
	}

	// Methods below are for constructing the business objects.

	private Map<String, Section> buildSections(List<Map<String, String>> sectionList) {
		HashMap<String, Section> map = new HashMap<String, Section>();

		for (Map<String, String> entry : sectionList) {
			Section section = buildSectionEntry(entry);
			map.put(section.getId().getValue(), section);
		}

		return map;
	}

	private Section buildSectionEntry(Map<String, String> sectionInfo) {
		Section section = objectFactory.createSection();

		section.setId(objectFactory.createSectionId(sectionInfo.get("id")));
		section.setName(objectFactory.createSectionName(sectionInfo.get("name")));
		section.setDescription(objectFactory.createSectionDescription(sectionInfo.get("description")));
		section.setAddress(objectFactory.createSectionAddress(sectionInfo.get("address")));
		section.setMail(objectFactory.createSectionMail(sectionInfo.get("mail")));
		section.setPhone(objectFactory.createSectionPhone(sectionInfo.get("phone")));

		return section;
	}

	private Map<String, TimeBlock> buildTimeblocks(List<Map<String, String>> timeblockList) {
		HashMap<String, TimeBlock> map = new HashMap<String, TimeBlock>();

		for (Map<String, String> entry : timeblockList) {
			TimeBlock timeblock = buildTimeblockEntry(entry);
			map.put(timeblock.getId().getValue(), timeblock);
		}

		return map;
	}

	private TimeBlock buildTimeblockEntry(Map<String, String> entry) {
		TimeBlock timeblock = objectFactory.createTimeBlock();

		timeblock.setId(objectFactory.createTimeBlockId(entry.get("id")));
		timeblock.setSection(objectFactory.createSection(sections.get(entry.get("section"))));
		timeblock.setStartTime(datatypeFactory.newXMLGregorianCalendar(entry.get("time")));
		timeblock.setLength(Integer.valueOf(entry.get("length")));

		return timeblock;
	}

	private Map<String, BookingInfo> buildBookings(List<Map<String, String>> bookingList) {
		HashMap<String, BookingInfo> map = new HashMap<String, BookingInfo>();

		for (Map<String, String> entry : bookingList) {
			BookingInfo info = buildBookingInfoEntry(entry);
			map.put(info.getExamNo().getValue(), info);
		}

		return map;
	}

	private BookingInfo buildBookingInfoEntry(Map<String, String> entry) {
		BookingInfo info = objectFactory.createBookingInfo();

		info.setExamNo(objectFactory.createBookingInfoExamNo(entry.get("examNo")));
		info.setExamType(objectFactory.createBookingInfoExamType(entry.get("type")));
		info.setExamTypeCode(objectFactory.createBookingInfoExamTypeCode(entry.get("code")));
		info.setPatientId(objectFactory.createBookingInfoPatientId(entry.get("patientId")));
		info.setPatientName(objectFactory.createBookingInfoPatientName(entry.get("name")));
		info.setBookedTime(objectFactory.createBookingInfoBookedTime(timeblocks.get(entry.get("time"))));
		info.setLaterality(objectFactory.createBookingInfoLaterality(entry.get("laterality")));

		return info;
	}

}
