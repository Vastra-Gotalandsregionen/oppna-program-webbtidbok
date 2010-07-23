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
 *         &lt;element name="ListSectionsResult" type="{http://tempuri.org/}ArrayOfSection"/>
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
    "listSectionsResult"
})
@XmlRootElement(name = "ListSectionsResponse")
public class ListSectionsResponseElement {

    @XmlElement(name = "ListSectionsResult", required = true)
    protected ArrayOfSection listSectionsResult;

    /**
     * Gets the value of the listSectionsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSection }
     *     
     */
    public ArrayOfSection getListSectionsResult() {
        return listSectionsResult;
    }

    /**
     * Sets the value of the listSectionsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSection }
     *     
     */
    public void setListSectionsResult(ArrayOfSection value) {
        this.listSectionsResult = value;
    }

}
