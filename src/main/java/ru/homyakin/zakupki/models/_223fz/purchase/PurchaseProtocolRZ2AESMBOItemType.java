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
import ru.homyakin.zakupki.models._223fz.types.ItemType;


/**
 * Данные позиции в пакете
 * 
 * <p>Java class for purchaseProtocolRZ2AESMBOItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="purchaseProtocolRZ2AESMBOItemType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://zakupki.gov.ru/223fz/types/1}itemType">
 *       &lt;sequence>
 *         &lt;element name="purchaseProtocolRZ2AESMBOData" type="{http://zakupki.gov.ru/223fz/purchase/1}purchaseProtocolRZ2AESMBODataType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "purchaseProtocolRZ2AESMBOItemType", propOrder = {
    "purchaseProtocolRZ2AESMBOData"
})
public class PurchaseProtocolRZ2AESMBOItemType
    extends ItemType
{

    @XmlElement(required = true)
    protected PurchaseProtocolRZ2AESMBODataType purchaseProtocolRZ2AESMBOData;

    /**
     * Gets the value of the purchaseProtocolRZ2AESMBOData property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseProtocolRZ2AESMBODataType }
     *     
     */
    public PurchaseProtocolRZ2AESMBODataType getPurchaseProtocolRZ2AESMBOData() {
        return purchaseProtocolRZ2AESMBOData;
    }

    /**
     * Sets the value of the purchaseProtocolRZ2AESMBOData property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseProtocolRZ2AESMBODataType }
     *     
     */
    public void setPurchaseProtocolRZ2AESMBOData(PurchaseProtocolRZ2AESMBODataType value) {
        this.purchaseProtocolRZ2AESMBOData = value;
    }

}
