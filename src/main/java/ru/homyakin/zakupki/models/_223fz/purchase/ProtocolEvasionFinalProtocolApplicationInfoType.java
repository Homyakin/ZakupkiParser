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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for protocolEvasionFinalProtocolApplicationInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="protocolEvasionFinalProtocolApplicationInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lot" type="{http://zakupki.gov.ru/223fz/purchase/1}protocolEvasionLotType"/>
 *         &lt;element name="application" type="{http://zakupki.gov.ru/223fz/purchase/1}protocolEvasionApplicationType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "protocolEvasionFinalProtocolApplicationInfoType", propOrder = {
    "lot",
    "application"
})
public class ProtocolEvasionFinalProtocolApplicationInfoType {

    @XmlElement(required = true)
    protected ProtocolEvasionLotType lot;
    @XmlElement(required = true)
    protected ProtocolEvasionApplicationType application;

    /**
     * Gets the value of the lot property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolEvasionLotType }
     *     
     */
    public ProtocolEvasionLotType getLot() {
        return lot;
    }

    /**
     * Sets the value of the lot property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolEvasionLotType }
     *     
     */
    public void setLot(ProtocolEvasionLotType value) {
        this.lot = value;
    }

    /**
     * Gets the value of the application property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolEvasionApplicationType }
     *     
     */
    public ProtocolEvasionApplicationType getApplication() {
        return application;
    }

    /**
     * Sets the value of the application property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolEvasionApplicationType }
     *     
     */
    public void setApplication(ProtocolEvasionApplicationType value) {
        this.application = value;
    }

}
