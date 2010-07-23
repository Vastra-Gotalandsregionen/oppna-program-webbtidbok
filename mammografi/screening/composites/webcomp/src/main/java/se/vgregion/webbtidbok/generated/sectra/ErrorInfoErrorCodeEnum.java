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

package se.vgregion.webbtidbok.generated.sectra;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErrorInfo.ErrorCodeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ErrorInfo.ErrorCodeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Unknown"/>
 *     &lt;enumeration value="CommunicationWithRisFailed"/>
 *     &lt;enumeration value="NoLicense"/>
 *     &lt;enumeration value="IvalidPatientId"/>
 *     &lt;enumeration value="InvalidExaminationId"/>
 *     &lt;enumeration value="InvalidSectionId"/>
 *     &lt;enumeration value="InvalidTimeId"/>
 *     &lt;enumeration value="MultipleBookingsOnExam"/>
 *     &lt;enumeration value="TimeSpanExceded"/>
 *     &lt;enumeration value="ExaminationLocked"/>
 *     &lt;enumeration value="TimeLocked"/>
 *     &lt;enumeration value="TimeAlreadyBooked"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ErrorInfo.ErrorCodeEnum")
@XmlEnum
public enum ErrorInfoErrorCodeEnum {

    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),
    @XmlEnumValue("CommunicationWithRisFailed")
    COMMUNICATION_WITH_RIS_FAILED("CommunicationWithRisFailed"),
    @XmlEnumValue("NoLicense")
    NO_LICENSE("NoLicense"),
    @XmlEnumValue("IvalidPatientId")
    IVALID_PATIENT_ID("IvalidPatientId"),
    @XmlEnumValue("InvalidExaminationId")
    INVALID_EXAMINATION_ID("InvalidExaminationId"),
    @XmlEnumValue("InvalidSectionId")
    INVALID_SECTION_ID("InvalidSectionId"),
    @XmlEnumValue("InvalidTimeId")
    INVALID_TIME_ID("InvalidTimeId"),
    @XmlEnumValue("MultipleBookingsOnExam")
    MULTIPLE_BOOKINGS_ON_EXAM("MultipleBookingsOnExam"),
    @XmlEnumValue("TimeSpanExceded")
    TIME_SPAN_EXCEDED("TimeSpanExceded"),
    @XmlEnumValue("ExaminationLocked")
    EXAMINATION_LOCKED("ExaminationLocked"),
    @XmlEnumValue("TimeLocked")
    TIME_LOCKED("TimeLocked"),
    @XmlEnumValue("TimeAlreadyBooked")
    TIME_ALREADY_BOOKED("TimeAlreadyBooked");
    private final String value;

    ErrorInfoErrorCodeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ErrorInfoErrorCodeEnum fromValue(String v) {
        for (ErrorInfoErrorCodeEnum c: ErrorInfoErrorCodeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
