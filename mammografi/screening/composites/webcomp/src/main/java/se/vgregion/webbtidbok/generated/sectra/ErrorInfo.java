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
 * <p>Java class for ErrorInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ErrorInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErrorNumber" type="{http://tempuri.org/}ErrorInfo.ErrorCodeEnum" minOccurs="0"/>
 *         &lt;element name="ErrorNumberInt" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorInfo", propOrder = {
    "errorNumber",
    "errorNumberInt"
})
public class ErrorInfo {

    @XmlElement(name = "ErrorNumber")
    protected ErrorInfoErrorCodeEnum errorNumber;
    @XmlElement(name = "ErrorNumberInt")
    protected Integer errorNumberInt;

    /**
     * Gets the value of the errorNumber property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorInfoErrorCodeEnum }
     *     
     */
    public ErrorInfoErrorCodeEnum getErrorNumber() {
        return errorNumber;
    }

    /**
     * Sets the value of the errorNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorInfoErrorCodeEnum }
     *     
     */
    public void setErrorNumber(ErrorInfoErrorCodeEnum value) {
        this.errorNumber = value;
    }

    /**
     * Gets the value of the errorNumberInt property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getErrorNumberInt() {
        return errorNumberInt;
    }

    /**
     * Sets the value of the errorNumberInt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setErrorNumberInt(Integer value) {
        this.errorNumberInt = value;
    }

}
