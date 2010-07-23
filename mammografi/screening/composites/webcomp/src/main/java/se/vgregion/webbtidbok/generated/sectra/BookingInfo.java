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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BookingInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BookedTime" type="{http://tempuri.org/}TimeBlock"/>
 *         &lt;element name="ExamNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ExamType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ExamTypeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Laterality" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PatientId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PatientName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingInfo", propOrder = {
    "bookedTime",
    "examNo",
    "examType",
    "examTypeCode",
    "laterality",
    "patientId",
    "patientName"
})
public class BookingInfo {

    @XmlElement(name = "BookedTime", required = true)
    protected TimeBlock bookedTime;
    @XmlElement(name = "ExamNo", required = true)
    protected String examNo;
    @XmlElement(name = "ExamType", required = true)
    protected String examType;
    @XmlElement(name = "ExamTypeCode", required = true)
    protected String examTypeCode;
    @XmlElement(name = "Laterality", required = true)
    protected String laterality;
    @XmlElement(name = "PatientId", required = true)
    protected String patientId;
    @XmlElement(name = "PatientName", required = true)
    protected String patientName;

    /**
     * Gets the value of the bookedTime property.
     * 
     * @return
     *     possible object is
     *     {@link TimeBlock }
     *     
     */
    public TimeBlock getBookedTime() {
        return bookedTime;
    }

    /**
     * Sets the value of the bookedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeBlock }
     *     
     */
    public void setBookedTime(TimeBlock value) {
        this.bookedTime = value;
    }

    /**
     * Gets the value of the examNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExamNo() {
        return examNo;
    }

    /**
     * Sets the value of the examNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExamNo(String value) {
        this.examNo = value;
    }

    /**
     * Gets the value of the examType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExamType() {
        return examType;
    }

    /**
     * Sets the value of the examType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExamType(String value) {
        this.examType = value;
    }

    /**
     * Gets the value of the examTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExamTypeCode() {
        return examTypeCode;
    }

    /**
     * Sets the value of the examTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExamTypeCode(String value) {
        this.examTypeCode = value;
    }

    /**
     * Gets the value of the laterality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLaterality() {
        return laterality;
    }

    /**
     * Sets the value of the laterality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLaterality(String value) {
        this.laterality = value;
    }

    /**
     * Gets the value of the patientId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Sets the value of the patientId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientId(String value) {
        this.patientId = value;
    }

    /**
     * Gets the value of the patientName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * Sets the value of the patientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientName(String value) {
        this.patientName = value;
    }

}
