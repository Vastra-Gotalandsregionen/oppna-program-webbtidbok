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

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the se.vgregion.webbtidbok.generated.sectra package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Byte_QNAME = new QName("http://tempuri.org/", "byte");
    private final static QName _ArrayOfTimeBlock_QNAME = new QName("http://tempuri.org/", "ArrayOfTimeBlock");
    private final static QName _Base64Binary_QNAME = new QName("http://tempuri.org/", "base64Binary");
    private final static QName _Short_QNAME = new QName("http://tempuri.org/", "short");
    private final static QName _ErrorInfo_QNAME = new QName("http://tempuri.org/", "ErrorInfo");
    private final static QName _Boolean_QNAME = new QName("http://tempuri.org/", "boolean");
    private final static QName _ArrayOfdateTime_QNAME = new QName("http://tempuri.org/", "ArrayOfdateTime");
    private final static QName _ListFreeTimes_QNAME = new QName("http://tempuri.org/", "listFreeTimes");
    private final static QName _ErrorInfoErrorCodeEnum_QNAME = new QName("http://tempuri.org/", "ErrorInfo.ErrorCodeEnum");
    private final static QName _QName_QNAME = new QName("http://tempuri.org/", "QName");
    private final static QName _Char_QNAME = new QName("http://tempuri.org/", "char");
    private final static QName _DateTime_QNAME = new QName("http://tempuri.org/", "dateTime");
    private final static QName _BookingInfo_QNAME = new QName("http://tempuri.org/", "BookingInfo");
    private final static QName _RescheduleResponse_QNAME = new QName("http://tempuri.org/", "rescheduleResponse");
    private final static QName _AnyURI_QNAME = new QName("http://tempuri.org/", "anyURI");
    private final static QName _GetBookingInfoResponse_QNAME = new QName("http://tempuri.org/", "getBookingInfoResponse");
    private final static QName _ListFreeTimesResponse_QNAME = new QName("http://tempuri.org/", "listFreeTimesResponse");
    private final static QName _Float_QNAME = new QName("http://tempuri.org/", "float");
    private final static QName _Long_QNAME = new QName("http://tempuri.org/", "long");
    private final static QName _ListSections_QNAME = new QName("http://tempuri.org/", "listSections");
    private final static QName _ArrayOfBookingInfo_QNAME = new QName("http://tempuri.org/", "ArrayOfBookingInfo");
    private final static QName _ArrayOfSection_QNAME = new QName("http://tempuri.org/", "ArrayOfSection");
    private final static QName _UnsignedShort_QNAME = new QName("http://tempuri.org/", "unsignedShort");
    private final static QName _String_QNAME = new QName("http://tempuri.org/", "string");
    private final static QName _Duration_QNAME = new QName("http://tempuri.org/", "duration");
    private final static QName _Section_QNAME = new QName("http://tempuri.org/", "Section");
    private final static QName _Guid_QNAME = new QName("http://tempuri.org/", "guid");
    private final static QName _ListSectionsResponse_QNAME = new QName("http://tempuri.org/", "listSectionsResponse");
    private final static QName _GetBookingsResponse_QNAME = new QName("http://tempuri.org/", "getBookingsResponse");
    private final static QName _ListFreeDaysResponse_QNAME = new QName("http://tempuri.org/", "listFreeDaysResponse");
    private final static QName _UnsignedLong_QNAME = new QName("http://tempuri.org/", "unsignedLong");
    private final static QName _UnsignedInt_QNAME = new QName("http://tempuri.org/", "unsignedInt");
    private final static QName _Int_QNAME = new QName("http://tempuri.org/", "int");
    private final static QName _UnsignedByte_QNAME = new QName("http://tempuri.org/", "unsignedByte");
    private final static QName _AnyType_QNAME = new QName("http://tempuri.org/", "anyType");
    private final static QName _Reschedule_QNAME = new QName("http://tempuri.org/", "reschedule");
    private final static QName _GetBookingInfo_QNAME = new QName("http://tempuri.org/", "getBookingInfo");
    private final static QName _TimeBlock_QNAME = new QName("http://tempuri.org/", "TimeBlock");
    private final static QName _Decimal_QNAME = new QName("http://tempuri.org/", "decimal");
    private final static QName _Double_QNAME = new QName("http://tempuri.org/", "double");
    private final static QName _GetBookings_QNAME = new QName("http://tempuri.org/", "getBookings");
    private final static QName _ListFreeDays_QNAME = new QName("http://tempuri.org/", "listFreeDays");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: se.vgregion.webbtidbok.generated.sectra
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListSectionsElement }
     * 
     */
    public ListSectionsElement createListSectionsElement() {
        return new ListSectionsElement();
    }

    /**
     * Create an instance of {@link GetBookingInfo }
     * 
     */
    public GetBookingInfo createGetBookingInfo() {
        return new GetBookingInfo();
    }

    /**
     * Create an instance of {@link ListSections }
     * 
     */
    public ListSections createListSections() {
        return new ListSections();
    }

    /**
     * Create an instance of {@link RescheduleResponse }
     * 
     */
    public RescheduleResponse createRescheduleResponse() {
        return new RescheduleResponse();
    }

    /**
     * Create an instance of {@link GetBookingInfoResponse }
     * 
     */
    public GetBookingInfoResponse createGetBookingInfoResponse() {
        return new GetBookingInfoResponse();
    }

    /**
     * Create an instance of {@link ListFreeTimesResponseElement }
     * 
     */
    public ListFreeTimesResponseElement createListFreeTimesResponseElement() {
        return new ListFreeTimesResponseElement();
    }

    /**
     * Create an instance of {@link ArrayOfSection }
     * 
     */
    public ArrayOfSection createArrayOfSection() {
        return new ArrayOfSection();
    }

    /**
     * Create an instance of {@link ListFreeTimes }
     * 
     */
    public ListFreeTimes createListFreeTimes() {
        return new ListFreeTimes();
    }

    /**
     * Create an instance of {@link GetBookingsResponse }
     * 
     */
    public GetBookingsResponse createGetBookingsResponse() {
        return new GetBookingsResponse();
    }

    /**
     * Create an instance of {@link RescheduleElement }
     * 
     */
    public RescheduleElement createRescheduleElement() {
        return new RescheduleElement();
    }

    /**
     * Create an instance of {@link GetBookings }
     * 
     */
    public GetBookings createGetBookings() {
        return new GetBookings();
    }

    /**
     * Create an instance of {@link ListFreeDaysResponse }
     * 
     */
    public ListFreeDaysResponse createListFreeDaysResponse() {
        return new ListFreeDaysResponse();
    }

    /**
     * Create an instance of {@link GetBookingsResponseElement }
     * 
     */
    public GetBookingsResponseElement createGetBookingsResponseElement() {
        return new GetBookingsResponseElement();
    }

    /**
     * Create an instance of {@link GetBookingInfoElement }
     * 
     */
    public GetBookingInfoElement createGetBookingInfoElement() {
        return new GetBookingInfoElement();
    }

    /**
     * Create an instance of {@link ArrayOfBookingInfo }
     * 
     */
    public ArrayOfBookingInfo createArrayOfBookingInfo() {
        return new ArrayOfBookingInfo();
    }

    /**
     * Create an instance of {@link BookingInfo }
     * 
     */
    public BookingInfo createBookingInfo() {
        return new BookingInfo();
    }

    /**
     * Create an instance of {@link ListSectionsResponse }
     * 
     */
    public ListSectionsResponse createListSectionsResponse() {
        return new ListSectionsResponse();
    }

    /**
     * Create an instance of {@link ListFreeDays }
     * 
     */
    public ListFreeDays createListFreeDays() {
        return new ListFreeDays();
    }

    /**
     * Create an instance of {@link ListFreeTimesResponse }
     * 
     */
    public ListFreeTimesResponse createListFreeTimesResponse() {
        return new ListFreeTimesResponse();
    }

    /**
     * Create an instance of {@link ListSectionsResponseElement }
     * 
     */
    public ListSectionsResponseElement createListSectionsResponseElement() {
        return new ListSectionsResponseElement();
    }

    /**
     * Create an instance of {@link GetBookingsElement }
     * 
     */
    public GetBookingsElement createGetBookingsElement() {
        return new GetBookingsElement();
    }

    /**
     * Create an instance of {@link Reschedule }
     * 
     */
    public Reschedule createReschedule() {
        return new Reschedule();
    }

    /**
     * Create an instance of {@link GetBookingInfoResponseElement }
     * 
     */
    public GetBookingInfoResponseElement createGetBookingInfoResponseElement() {
        return new GetBookingInfoResponseElement();
    }

    /**
     * Create an instance of {@link ListFreeTimesElement }
     * 
     */
    public ListFreeTimesElement createListFreeTimesElement() {
        return new ListFreeTimesElement();
    }

    /**
     * Create an instance of {@link TimeBlock }
     * 
     */
    public TimeBlock createTimeBlock() {
        return new TimeBlock();
    }

    /**
     * Create an instance of {@link Section }
     * 
     */
    public Section createSection() {
        return new Section();
    }

    /**
     * Create an instance of {@link ListFreeDaysElement }
     * 
     */
    public ListFreeDaysElement createListFreeDaysElement() {
        return new ListFreeDaysElement();
    }

    /**
     * Create an instance of {@link ErrorInfo }
     * 
     */
    public ErrorInfo createErrorInfo() {
        return new ErrorInfo();
    }

    /**
     * Create an instance of {@link ArrayOfTimeBlock }
     * 
     */
    public ArrayOfTimeBlock createArrayOfTimeBlock() {
        return new ArrayOfTimeBlock();
    }

    /**
     * Create an instance of {@link ArrayOfdateTime }
     * 
     */
    public ArrayOfdateTime createArrayOfdateTime() {
        return new ArrayOfdateTime();
    }

    /**
     * Create an instance of {@link ListFreeDaysResponseElement }
     * 
     */
    public ListFreeDaysResponseElement createListFreeDaysResponseElement() {
        return new ListFreeDaysResponseElement();
    }

    /**
     * Create an instance of {@link RescheduleResponseElement }
     * 
     */
    public RescheduleResponseElement createRescheduleResponseElement() {
        return new RescheduleResponseElement();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfTimeBlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ArrayOfTimeBlock")
    public JAXBElement<ArrayOfTimeBlock> createArrayOfTimeBlock(ArrayOfTimeBlock value) {
        return new JAXBElement<ArrayOfTimeBlock>(_ArrayOfTimeBlock_QNAME, ArrayOfTimeBlock.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ErrorInfo")
    public JAXBElement<ErrorInfo> createErrorInfo(ErrorInfo value) {
        return new JAXBElement<ErrorInfo>(_ErrorInfo_QNAME, ErrorInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfdateTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ArrayOfdateTime")
    public JAXBElement<ArrayOfdateTime> createArrayOfdateTime(ArrayOfdateTime value) {
        return new JAXBElement<ArrayOfdateTime>(_ArrayOfdateTime_QNAME, ArrayOfdateTime.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFreeTimes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "listFreeTimes")
    public JAXBElement<ListFreeTimes> createListFreeTimes(ListFreeTimes value) {
        return new JAXBElement<ListFreeTimes>(_ListFreeTimes_QNAME, ListFreeTimes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorInfoErrorCodeEnum }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ErrorInfo.ErrorCodeEnum")
    public JAXBElement<ErrorInfoErrorCodeEnum> createErrorInfoErrorCodeEnum(ErrorInfoErrorCodeEnum value) {
        return new JAXBElement<ErrorInfoErrorCodeEnum>(_ErrorInfoErrorCodeEnum_QNAME, ErrorInfoErrorCodeEnum.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dateTime")
    public JAXBElement<Object> createDateTime(Object value) {
        return new JAXBElement<Object>(_DateTime_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookingInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "BookingInfo")
    public JAXBElement<BookingInfo> createBookingInfo(BookingInfo value) {
        return new JAXBElement<BookingInfo>(_BookingInfo_QNAME, BookingInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RescheduleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "rescheduleResponse")
    public JAXBElement<RescheduleResponse> createRescheduleResponse(RescheduleResponse value) {
        return new JAXBElement<RescheduleResponse>(_RescheduleResponse_QNAME, RescheduleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBookingInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "getBookingInfoResponse")
    public JAXBElement<GetBookingInfoResponse> createGetBookingInfoResponse(GetBookingInfoResponse value) {
        return new JAXBElement<GetBookingInfoResponse>(_GetBookingInfoResponse_QNAME, GetBookingInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFreeTimesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "listFreeTimesResponse")
    public JAXBElement<ListFreeTimesResponse> createListFreeTimesResponse(ListFreeTimesResponse value) {
        return new JAXBElement<ListFreeTimesResponse>(_ListFreeTimesResponse_QNAME, ListFreeTimesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListSections }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "listSections")
    public JAXBElement<ListSections> createListSections(ListSections value) {
        return new JAXBElement<ListSections>(_ListSections_QNAME, ListSections.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfBookingInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ArrayOfBookingInfo")
    public JAXBElement<ArrayOfBookingInfo> createArrayOfBookingInfo(ArrayOfBookingInfo value) {
        return new JAXBElement<ArrayOfBookingInfo>(_ArrayOfBookingInfo_QNAME, ArrayOfBookingInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfSection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ArrayOfSection")
    public JAXBElement<ArrayOfSection> createArrayOfSection(ArrayOfSection value) {
        return new JAXBElement<ArrayOfSection>(_ArrayOfSection_QNAME, ArrayOfSection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Section }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "Section")
    public JAXBElement<Section> createSection(Section value) {
        return new JAXBElement<Section>(_Section_QNAME, Section.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListSectionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "listSectionsResponse")
    public JAXBElement<ListSectionsResponse> createListSectionsResponse(ListSectionsResponse value) {
        return new JAXBElement<ListSectionsResponse>(_ListSectionsResponse_QNAME, ListSectionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBookingsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "getBookingsResponse")
    public JAXBElement<GetBookingsResponse> createGetBookingsResponse(GetBookingsResponse value) {
        return new JAXBElement<GetBookingsResponse>(_GetBookingsResponse_QNAME, GetBookingsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFreeDaysResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "listFreeDaysResponse")
    public JAXBElement<ListFreeDaysResponse> createListFreeDaysResponse(ListFreeDaysResponse value) {
        return new JAXBElement<ListFreeDaysResponse>(_ListFreeDaysResponse_QNAME, ListFreeDaysResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reschedule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "reschedule")
    public JAXBElement<Reschedule> createReschedule(Reschedule value) {
        return new JAXBElement<Reschedule>(_Reschedule_QNAME, Reschedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBookingInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "getBookingInfo")
    public JAXBElement<GetBookingInfo> createGetBookingInfo(GetBookingInfo value) {
        return new JAXBElement<GetBookingInfo>(_GetBookingInfo_QNAME, GetBookingInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeBlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "TimeBlock")
    public JAXBElement<TimeBlock> createTimeBlock(TimeBlock value) {
        return new JAXBElement<TimeBlock>(_TimeBlock_QNAME, TimeBlock.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBookings }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "getBookings")
    public JAXBElement<GetBookings> createGetBookings(GetBookings value) {
        return new JAXBElement<GetBookings>(_GetBookings_QNAME, GetBookings.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFreeDays }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "listFreeDays")
    public JAXBElement<ListFreeDays> createListFreeDays(ListFreeDays value) {
        return new JAXBElement<ListFreeDays>(_ListFreeDays_QNAME, ListFreeDays.class, null, value);
    }

}
