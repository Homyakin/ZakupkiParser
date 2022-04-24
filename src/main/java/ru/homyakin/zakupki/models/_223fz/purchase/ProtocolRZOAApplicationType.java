//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.04.24 at 08:20:08 PM MSK 
//


package ru.homyakin.zakupki.models._223fz.purchase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.homyakin.zakupki.models._223fz.types.AcceptedType;
import ru.homyakin.zakupki.models._223fz.types.NonResidentInfoType;
import ru.homyakin.zakupki.models._223fz.types.SupplierMainInfoType;


/**
 * Заявка открытого аукциона
 * 
 * <p>Java class for protocolRZOAApplicationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="protocolRZOAApplicationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applicationNumber">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="30"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="notDishonest" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="supplierInfo" type="{http://zakupki.gov.ru/223fz/types/1}supplierMainInfoType"/>
 *           &lt;element name="nonResidentInfo" type="{http://zakupki.gov.ru/223fz/types/1}nonResidentInfoType"/>
 *         &lt;/choice>
 *         &lt;element name="applicationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="commodityAmount" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="2000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="contractExecutionTerm" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="2000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="conditionProposals" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="2000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="accepted" type="{http://zakupki.gov.ru/223fz/types/1}acceptedType" minOccurs="0"/>
 *         &lt;element name="rejectionReasonCode" type="{http://zakupki.gov.ru/223fz/purchase/1}rejectionReasonCodeType" minOccurs="0"/>
 *         &lt;element name="rejectionReason" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="1000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="appliedDocumentsList" type="{http://zakupki.gov.ru/223fz/purchase/1}appliedDocumentsListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "protocolRZOAApplicationType", propOrder = {
    "applicationNumber",
    "notDishonest",
    "provider",
    "supplierInfo",
    "nonResidentInfo",
    "applicationDate",
    "commodityAmount",
    "contractExecutionTerm",
    "conditionProposals",
    "accepted",
    "rejectionReasonCode",
    "rejectionReason",
    "appliedDocumentsList"
})
public class ProtocolRZOAApplicationType {

    @XmlElement(required = true)
    protected String applicationNumber;
    protected Boolean notDishonest;
    protected Boolean provider;
    protected SupplierMainInfoType supplierInfo;
    protected NonResidentInfoType nonResidentInfo;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar applicationDate;
    protected String commodityAmount;
    protected String contractExecutionTerm;
    protected String conditionProposals;
    @XmlSchemaType(name = "string")
    protected AcceptedType accepted;
    @XmlSchemaType(name = "string")
    protected RejectionReasonCodeType rejectionReasonCode;
    protected String rejectionReason;
    protected AppliedDocumentsListType appliedDocumentsList;

    /**
     * Gets the value of the applicationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationNumber() {
        return applicationNumber;
    }

    /**
     * Sets the value of the applicationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationNumber(String value) {
        this.applicationNumber = value;
    }

    /**
     * Gets the value of the notDishonest property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNotDishonest() {
        return notDishonest;
    }

    /**
     * Sets the value of the notDishonest property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNotDishonest(Boolean value) {
        this.notDishonest = value;
    }

    /**
     * Gets the value of the provider property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProvider() {
        return provider;
    }

    /**
     * Sets the value of the provider property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProvider(Boolean value) {
        this.provider = value;
    }

    /**
     * Gets the value of the supplierInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierMainInfoType }
     *     
     */
    public SupplierMainInfoType getSupplierInfo() {
        return supplierInfo;
    }

    /**
     * Sets the value of the supplierInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierMainInfoType }
     *     
     */
    public void setSupplierInfo(SupplierMainInfoType value) {
        this.supplierInfo = value;
    }

    /**
     * Gets the value of the nonResidentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link NonResidentInfoType }
     *     
     */
    public NonResidentInfoType getNonResidentInfo() {
        return nonResidentInfo;
    }

    /**
     * Sets the value of the nonResidentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link NonResidentInfoType }
     *     
     */
    public void setNonResidentInfo(NonResidentInfoType value) {
        this.nonResidentInfo = value;
    }

    /**
     * Gets the value of the applicationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getApplicationDate() {
        return applicationDate;
    }

    /**
     * Sets the value of the applicationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setApplicationDate(XMLGregorianCalendar value) {
        this.applicationDate = value;
    }

    /**
     * Gets the value of the commodityAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommodityAmount() {
        return commodityAmount;
    }

    /**
     * Sets the value of the commodityAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommodityAmount(String value) {
        this.commodityAmount = value;
    }

    /**
     * Gets the value of the contractExecutionTerm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractExecutionTerm() {
        return contractExecutionTerm;
    }

    /**
     * Sets the value of the contractExecutionTerm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractExecutionTerm(String value) {
        this.contractExecutionTerm = value;
    }

    /**
     * Gets the value of the conditionProposals property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionProposals() {
        return conditionProposals;
    }

    /**
     * Sets the value of the conditionProposals property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionProposals(String value) {
        this.conditionProposals = value;
    }

    /**
     * Gets the value of the accepted property.
     * 
     * @return
     *     possible object is
     *     {@link AcceptedType }
     *     
     */
    public AcceptedType getAccepted() {
        return accepted;
    }

    /**
     * Sets the value of the accepted property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcceptedType }
     *     
     */
    public void setAccepted(AcceptedType value) {
        this.accepted = value;
    }

    /**
     * Gets the value of the rejectionReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link RejectionReasonCodeType }
     *     
     */
    public RejectionReasonCodeType getRejectionReasonCode() {
        return rejectionReasonCode;
    }

    /**
     * Sets the value of the rejectionReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RejectionReasonCodeType }
     *     
     */
    public void setRejectionReasonCode(RejectionReasonCodeType value) {
        this.rejectionReasonCode = value;
    }

    /**
     * Gets the value of the rejectionReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRejectionReason() {
        return rejectionReason;
    }

    /**
     * Sets the value of the rejectionReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRejectionReason(String value) {
        this.rejectionReason = value;
    }

    /**
     * Gets the value of the appliedDocumentsList property.
     * 
     * @return
     *     possible object is
     *     {@link AppliedDocumentsListType }
     *     
     */
    public AppliedDocumentsListType getAppliedDocumentsList() {
        return appliedDocumentsList;
    }

    /**
     * Sets the value of the appliedDocumentsList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppliedDocumentsListType }
     *     
     */
    public void setAppliedDocumentsList(AppliedDocumentsListType value) {
        this.appliedDocumentsList = value;
    }

}
