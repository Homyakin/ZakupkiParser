//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.04.24 at 08:20:08 PM MSK 
//


package ru.homyakin.zakupki.models._223fz.purchase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Протокол рассмотрения первых частей заявок конкурса в электронной форме, участниками которого могут являться только субъекты малого и среднего предпринимательства
 * 
 * <p>Java class for purchaseProtocolRZ1KESMBODataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="purchaseProtocolRZ1KESMBODataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://zakupki.gov.ru/223fz/purchase/1}purchaseProtocolDataType">
 *       &lt;sequence>
 *         &lt;element name="protocolFCODRegistryNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="protocolFCDRegistryNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="protocolZRPZKESMBORegistrationNumber" type="{http://zakupki.gov.ru/223fz/types/1}registrationNumber11-2Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "purchaseProtocolRZ1KESMBODataType", propOrder = {
    "protocolFCODRegistryNumber",
    "protocolFCDRegistryNumber",
    "protocolZRPZKESMBORegistrationNumber"
})
public class PurchaseProtocolRZ1KESMBODataType
    extends PurchaseProtocolDataType
{

    protected String protocolFCODRegistryNumber;
    protected String protocolFCDRegistryNumber;
    protected String protocolZRPZKESMBORegistrationNumber;

    /**
     * Gets the value of the protocolFCODRegistryNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocolFCODRegistryNumber() {
        return protocolFCODRegistryNumber;
    }

    /**
     * Sets the value of the protocolFCODRegistryNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocolFCODRegistryNumber(String value) {
        this.protocolFCODRegistryNumber = value;
    }

    /**
     * Gets the value of the protocolFCDRegistryNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocolFCDRegistryNumber() {
        return protocolFCDRegistryNumber;
    }

    /**
     * Sets the value of the protocolFCDRegistryNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocolFCDRegistryNumber(String value) {
        this.protocolFCDRegistryNumber = value;
    }

    /**
     * Gets the value of the protocolZRPZKESMBORegistrationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocolZRPZKESMBORegistrationNumber() {
        return protocolZRPZKESMBORegistrationNumber;
    }

    /**
     * Sets the value of the protocolZRPZKESMBORegistrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocolZRPZKESMBORegistrationNumber(String value) {
        this.protocolZRPZKESMBORegistrationNumber = value;
    }

}
