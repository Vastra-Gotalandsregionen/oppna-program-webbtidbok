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
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="GetBookingsResult" type="{http://tempuri.org/}ArrayOfBookingInfo"/>
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
    "getBookingsResult"
})
@XmlRootElement(name = "GetBookingsResponse")
public class GetBookingsResponseElement {

    @XmlElement(name = "GetBookingsResult", required = true)
    protected ArrayOfBookingInfo getBookingsResult;

    /**
     * Gets the value of the getBookingsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfBookingInfo }
     *     
     */
    public ArrayOfBookingInfo getGetBookingsResult() {
        return getBookingsResult;
    }

    /**
     * Sets the value of the getBookingsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfBookingInfo }
     *     
     */
    public void setGetBookingsResult(ArrayOfBookingInfo value) {
        this.getBookingsResult = value;
    }

}
