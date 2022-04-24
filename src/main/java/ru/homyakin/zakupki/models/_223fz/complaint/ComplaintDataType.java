//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.04.24 at 08:20:15 PM MSK 
//


package ru.homyakin.zakupki.models._223fz.complaint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.homyakin.zakupki.models._223fz.types.CustomerInfoType;
import ru.homyakin.zakupki.models._223fz.types.CustomerMainInfoType;
import ru.homyakin.zakupki.models._223fz.types.DocumentListType;
import ru.homyakin.zakupki.models._223fz.types.PurchaseInfo2Type;


/**
 * Сведения о поданной жалобе
 * 
 * <p>Java class for complaintDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complaintDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="guid" type="{http://zakupki.gov.ru/223fz/types/1}guidType"/>
 *         &lt;element name="urlEIS" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="3000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="urlVSRZ" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="3000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="urlKisRmis" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="3000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="registrationNumber" type="{http://zakupki.gov.ru/223fz/complaint/1}complaintNumberType" minOccurs="0"/>
 *         &lt;element name="complaintPlacer" type="{http://zakupki.gov.ru/223fz/complaint/1}complaintPlacerType"/>
 *         &lt;element name="complaintContent">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="2000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="complaintDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="complaintStatus" type="{http://zakupki.gov.ru/223fz/complaint/1}complaintStatusType"/>
 *         &lt;element name="publishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="modificationDescription" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="1000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="version" type="{http://zakupki.gov.ru/223fz/types/1}versionType" minOccurs="0"/>
 *         &lt;element name="customer" type="{http://zakupki.gov.ru/223fz/types/1}customerInfoType"/>
 *         &lt;element name="placer" type="{http://zakupki.gov.ru/223fz/types/1}customerInfoType"/>
 *         &lt;element name="createDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="complaintPurchaseInfo" type="{http://zakupki.gov.ru/223fz/types/1}purchaseInfo2Type"/>
 *         &lt;element name="controlAgency" type="{http://zakupki.gov.ru/223fz/types/1}customerMainInfoType"/>
 *         &lt;element name="attachments" type="{http://zakupki.gov.ru/223fz/types/1}documentListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complaintDataType", propOrder = {
    "guid",
    "urlEIS",
    "urlVSRZ",
    "urlKisRmis",
    "registrationNumber",
    "complaintPlacer",
    "complaintContent",
    "complaintDate",
    "complaintStatus",
    "publishDate",
    "modificationDescription",
    "version",
    "customer",
    "placer",
    "createDate",
    "complaintPurchaseInfo",
    "controlAgency",
    "attachments"
})
public class ComplaintDataType {

    @XmlElement(required = true)
    protected String guid;
    protected String urlEIS;
    protected String urlVSRZ;
    protected String urlKisRmis;
    protected String registrationNumber;
    @XmlElement(required = true)
    protected ComplaintPlacerType complaintPlacer;
    @XmlElement(required = true)
    protected String complaintContent;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar complaintDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ComplaintStatusType complaintStatus;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar publishDate;
    protected String modificationDescription;
    protected Long version;
    @XmlElement(required = true)
    protected CustomerInfoType customer;
    @XmlElement(required = true)
    protected CustomerInfoType placer;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDate;
    @XmlElement(required = true)
    protected PurchaseInfo2Type complaintPurchaseInfo;
    @XmlElement(required = true)
    protected CustomerMainInfoType controlAgency;
    protected DocumentListType attachments;

    /**
     * Gets the value of the guid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuid(String value) {
        this.guid = value;
    }

    /**
     * Gets the value of the urlEIS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlEIS() {
        return urlEIS;
    }

    /**
     * Sets the value of the urlEIS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlEIS(String value) {
        this.urlEIS = value;
    }

    /**
     * Gets the value of the urlVSRZ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlVSRZ() {
        return urlVSRZ;
    }

    /**
     * Sets the value of the urlVSRZ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlVSRZ(String value) {
        this.urlVSRZ = value;
    }

    /**
     * Gets the value of the urlKisRmis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlKisRmis() {
        return urlKisRmis;
    }

    /**
     * Sets the value of the urlKisRmis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlKisRmis(String value) {
        this.urlKisRmis = value;
    }

    /**
     * Gets the value of the registrationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the value of the registrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrationNumber(String value) {
        this.registrationNumber = value;
    }

    /**
     * Gets the value of the complaintPlacer property.
     * 
     * @return
     *     possible object is
     *     {@link ComplaintPlacerType }
     *     
     */
    public ComplaintPlacerType getComplaintPlacer() {
        return complaintPlacer;
    }

    /**
     * Sets the value of the complaintPlacer property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplaintPlacerType }
     *     
     */
    public void setComplaintPlacer(ComplaintPlacerType value) {
        this.complaintPlacer = value;
    }

    /**
     * Gets the value of the complaintContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplaintContent() {
        return complaintContent;
    }

    /**
     * Sets the value of the complaintContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplaintContent(String value) {
        this.complaintContent = value;
    }

    /**
     * Gets the value of the complaintDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getComplaintDate() {
        return complaintDate;
    }

    /**
     * Sets the value of the complaintDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setComplaintDate(XMLGregorianCalendar value) {
        this.complaintDate = value;
    }

    /**
     * Gets the value of the complaintStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ComplaintStatusType }
     *     
     */
    public ComplaintStatusType getComplaintStatus() {
        return complaintStatus;
    }

    /**
     * Sets the value of the complaintStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplaintStatusType }
     *     
     */
    public void setComplaintStatus(ComplaintStatusType value) {
        this.complaintStatus = value;
    }

    /**
     * Gets the value of the publishDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPublishDate() {
        return publishDate;
    }

    /**
     * Sets the value of the publishDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPublishDate(XMLGregorianCalendar value) {
        this.publishDate = value;
    }

    /**
     * Gets the value of the modificationDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModificationDescription() {
        return modificationDescription;
    }

    /**
     * Sets the value of the modificationDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModificationDescription(String value) {
        this.modificationDescription = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVersion(Long value) {
        this.version = value;
    }

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerInfoType }
     *     
     */
    public CustomerInfoType getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerInfoType }
     *     
     */
    public void setCustomer(CustomerInfoType value) {
        this.customer = value;
    }

    /**
     * Gets the value of the placer property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerInfoType }
     *     
     */
    public CustomerInfoType getPlacer() {
        return placer;
    }

    /**
     * Sets the value of the placer property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerInfoType }
     *     
     */
    public void setPlacer(CustomerInfoType value) {
        this.placer = value;
    }

    /**
     * Gets the value of the createDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDate(XMLGregorianCalendar value) {
        this.createDate = value;
    }

    /**
     * Gets the value of the complaintPurchaseInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseInfo2Type }
     *     
     */
    public PurchaseInfo2Type getComplaintPurchaseInfo() {
        return complaintPurchaseInfo;
    }

    /**
     * Sets the value of the complaintPurchaseInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseInfo2Type }
     *     
     */
    public void setComplaintPurchaseInfo(PurchaseInfo2Type value) {
        this.complaintPurchaseInfo = value;
    }

    /**
     * Gets the value of the controlAgency property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerMainInfoType }
     *     
     */
    public CustomerMainInfoType getControlAgency() {
        return controlAgency;
    }

    /**
     * Sets the value of the controlAgency property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerMainInfoType }
     *     
     */
    public void setControlAgency(CustomerMainInfoType value) {
        this.controlAgency = value;
    }

    /**
     * Gets the value of the attachments property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentListType }
     *     
     */
    public DocumentListType getAttachments() {
        return attachments;
    }

    /**
     * Sets the value of the attachments property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentListType }
     *     
     */
    public void setAttachments(DocumentListType value) {
        this.attachments = value;
    }

}
