//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.11.29 at 05:25:38 PM MSK 
//


package ru.homyakin.zakupki.documentsinfo._223fz.dishonestsupplier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.homyakin.zakupki.documentsinfo._223fz.types.PurchaseInfoType;


/**
 * Информация о закупке
 * 
 * <p>Java class for purchaseInfoDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="purchaseInfoDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://zakupki.gov.ru/223fz/types/1}purchaseInfoType">
 *       &lt;sequence>
 *         &lt;element name="lotInfo" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="2000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="purchaseDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="summingupDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="cancellationDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="documentRequisites" type="{http://zakupki.gov.ru/223fz/dishonestSupplier/1}documentRequisitesType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "purchaseInfoDataType", propOrder = {
    "lotInfo",
    "purchaseDate",
    "summingupDate",
    "cancellationDate",
    "documentRequisites"
})
public class PurchaseInfoDataType
    extends PurchaseInfoType
{

    protected String lotInfo;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar purchaseDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar summingupDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar cancellationDate;
    @XmlElement(required = true)
    protected DocumentRequisitesType documentRequisites;

    /**
     * Gets the value of the lotInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotInfo() {
        return lotInfo;
    }

    /**
     * Sets the value of the lotInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotInfo(String value) {
        this.lotInfo = value;
    }

    /**
     * Gets the value of the purchaseDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets the value of the purchaseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPurchaseDate(XMLGregorianCalendar value) {
        this.purchaseDate = value;
    }

    /**
     * Gets the value of the summingupDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSummingupDate() {
        return summingupDate;
    }

    /**
     * Sets the value of the summingupDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSummingupDate(XMLGregorianCalendar value) {
        this.summingupDate = value;
    }

    /**
     * Gets the value of the cancellationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCancellationDate() {
        return cancellationDate;
    }

    /**
     * Sets the value of the cancellationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCancellationDate(XMLGregorianCalendar value) {
        this.cancellationDate = value;
    }

    /**
     * Gets the value of the documentRequisites property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentRequisitesType }
     *     
     */
    public DocumentRequisitesType getDocumentRequisites() {
        return documentRequisites;
    }

    /**
     * Sets the value of the documentRequisites property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentRequisitesType }
     *     
     */
    public void setDocumentRequisites(DocumentRequisitesType value) {
        this.documentRequisites = value;
    }

}