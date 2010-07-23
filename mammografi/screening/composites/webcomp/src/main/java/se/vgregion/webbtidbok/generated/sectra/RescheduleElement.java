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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="examinationNr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newTimeId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="printNewNotice" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="rescheduleComment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "examinationNr",
    "newTimeId",
    "startTime",
    "printNewNotice",
    "rescheduleComment"
})
@XmlRootElement(name = "Reschedule")
public class RescheduleElement {

    @XmlElement(required = true)
    protected String examinationNr;
    @XmlElement(required = true)
    protected String newTimeId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    protected Boolean printNewNotice;
    @XmlElement(required = true)
    protected String rescheduleComment;

    /**
     * Gets the value of the examinationNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExaminationNr() {
        return examinationNr;
    }

    /**
     * Sets the value of the examinationNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExaminationNr(String value) {
        this.examinationNr = value;
    }

    /**
     * Gets the value of the newTimeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewTimeId() {
        return newTimeId;
    }

    /**
     * Sets the value of the newTimeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewTimeId(String value) {
        this.newTimeId = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the printNewNotice property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPrintNewNotice() {
        return printNewNotice;
    }

    /**
     * Sets the value of the printNewNotice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPrintNewNotice(Boolean value) {
        this.printNewNotice = value;
    }

    /**
     * Gets the value of the rescheduleComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRescheduleComment() {
        return rescheduleComment;
    }

    /**
     * Sets the value of the rescheduleComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRescheduleComment(String value) {
        this.rescheduleComment = value;
    }

}
