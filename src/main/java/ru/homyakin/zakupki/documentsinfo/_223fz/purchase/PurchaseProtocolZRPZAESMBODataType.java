//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.11.29 at 05:25:54 PM MSK 
//


package ru.homyakin.zakupki.documentsinfo._223fz.purchase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Протокол запроса разъяснений положений заявки для аукциона в электронной форме, участниками которого могут быть только субъекты малого и среднего предпринимательства
 * 
 * <p>Java class for purchaseProtocolZRPZAESMBODataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="purchaseProtocolZRPZAESMBODataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://zakupki.gov.ru/223fz/purchase/1}purchaseProtocolDataType">
 *       &lt;sequence>
 *         &lt;element name="protocolCollationRegistrationNumber" type="{http://zakupki.gov.ru/223fz/types/1}registrationNumber11-2Type" minOccurs="0"/>
 *         &lt;element name="protocolRZ1RegistrationNumber" type="{http://zakupki.gov.ru/223fz/types/1}registrationNumber11-2Type" minOccurs="0"/>
 *         &lt;element name="protocolRZ2" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="protocolRZ2RegistrationNumber" type="{http://zakupki.gov.ru/223fz/types/1}registrationNumber11-2Type"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "purchaseProtocolZRPZAESMBODataType", propOrder = {
    "protocolCollationRegistrationNumber",
    "protocolRZ1RegistrationNumber",
    "protocolRZ2"
})
public class PurchaseProtocolZRPZAESMBODataType
    extends PurchaseProtocolDataType
{

    protected String protocolCollationRegistrationNumber;
    protected String protocolRZ1RegistrationNumber;
    protected PurchaseProtocolZRPZAESMBODataType.ProtocolRZ2 protocolRZ2;

    /**
     * Gets the value of the protocolCollationRegistrationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocolCollationRegistrationNumber() {
        return protocolCollationRegistrationNumber;
    }

    /**
     * Sets the value of the protocolCollationRegistrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocolCollationRegistrationNumber(String value) {
        this.protocolCollationRegistrationNumber = value;
    }

    /**
     * Gets the value of the protocolRZ1RegistrationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocolRZ1RegistrationNumber() {
        return protocolRZ1RegistrationNumber;
    }

    /**
     * Sets the value of the protocolRZ1RegistrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocolRZ1RegistrationNumber(String value) {
        this.protocolRZ1RegistrationNumber = value;
    }

    /**
     * Gets the value of the protocolRZ2 property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseProtocolZRPZAESMBODataType.ProtocolRZ2 }
     *     
     */
    public PurchaseProtocolZRPZAESMBODataType.ProtocolRZ2 getProtocolRZ2() {
        return protocolRZ2;
    }

    /**
     * Sets the value of the protocolRZ2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseProtocolZRPZAESMBODataType.ProtocolRZ2 }
     *     
     */
    public void setProtocolRZ2(PurchaseProtocolZRPZAESMBODataType.ProtocolRZ2 value) {
        this.protocolRZ2 = value;
    }


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
     *         &lt;element name="protocolRZ2RegistrationNumber" type="{http://zakupki.gov.ru/223fz/types/1}registrationNumber11-2Type"/>
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
        "protocolRZ2RegistrationNumber"
    })
    public static class ProtocolRZ2 {

        @XmlElement(required = true)
        protected String protocolRZ2RegistrationNumber;

        /**
         * Gets the value of the protocolRZ2RegistrationNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProtocolRZ2RegistrationNumber() {
            return protocolRZ2RegistrationNumber;
        }

        /**
         * Sets the value of the protocolRZ2RegistrationNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProtocolRZ2RegistrationNumber(String value) {
            this.protocolRZ2RegistrationNumber = value;
        }

    }

}