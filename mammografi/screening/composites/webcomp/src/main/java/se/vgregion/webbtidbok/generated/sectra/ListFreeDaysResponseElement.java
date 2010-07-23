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
 *         &lt;element name="ListFreeDaysResult" type="{http://tempuri.org/}ArrayOfdateTime"/>
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
    "listFreeDaysResult"
})
@XmlRootElement(name = "ListFreeDaysResponse")
public class ListFreeDaysResponseElement {

    @XmlElement(name = "ListFreeDaysResult", required = true)
    protected ArrayOfdateTime listFreeDaysResult;

    /**
     * Gets the value of the listFreeDaysResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfdateTime }
     *     
     */
    public ArrayOfdateTime getListFreeDaysResult() {
        return listFreeDaysResult;
    }

    /**
     * Sets the value of the listFreeDaysResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfdateTime }
     *     
     */
    public void setListFreeDaysResult(ArrayOfdateTime value) {
        this.listFreeDaysResult = value;
    }

}
