//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.04.24 at 08:20:17 PM MSK 
//


package ru.homyakin.zakupki.models._223fz.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Данные организации заказчика
 * 
 * <p>Java class for customerInfo4Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="customerInfo4Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mainInfo" type="{http://zakupki.gov.ru/223fz/types/1}customerMainInfo4Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customerInfo4Type", propOrder = {
    "mainInfo"
})
public class CustomerInfo4Type {

    @XmlElement(required = true)
    protected CustomerMainInfo4Type mainInfo;

    /**
     * Gets the value of the mainInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerMainInfo4Type }
     *     
     */
    public CustomerMainInfo4Type getMainInfo() {
        return mainInfo;
    }

    /**
     * Sets the value of the mainInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerMainInfo4Type }
     *     
     */
    public void setMainInfo(CustomerMainInfo4Type value) {
        this.mainInfo = value;
    }

}
