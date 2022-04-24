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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Идентификационные коды юридического лица (ИКЮЛ)
 * 
 * <p>Java class for ikulType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ikulType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ikulCode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ikulName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="assignmentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ikulType", propOrder = {
    "ikulCode",
    "ikulName",
    "assignmentDate"
})
public class IkulType {

    @XmlElement(required = true)
    protected String ikulCode;
    @XmlElement(required = true)
    protected String ikulName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar assignmentDate;

    /**
     * Gets the value of the ikulCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIkulCode() {
        return ikulCode;
    }

    /**
     * Sets the value of the ikulCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIkulCode(String value) {
        this.ikulCode = value;
    }

    /**
     * Gets the value of the ikulName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIkulName() {
        return ikulName;
    }

    /**
     * Sets the value of the ikulName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIkulName(String value) {
        this.ikulName = value;
    }

    /**
     * Gets the value of the assignmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAssignmentDate() {
        return assignmentDate;
    }

    /**
     * Sets the value of the assignmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAssignmentDate(XMLGregorianCalendar value) {
        this.assignmentDate = value;
    }

}
