//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.04.24 at 08:20:17 PM MSK 
//


package ru.homyakin.zakupki.models._223fz.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for noticeTemplateFieldLevelType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="noticeTemplateFieldLevelType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NOTICE"/>
 *     &lt;enumeration value="LOT"/>
 *     &lt;enumeration value="LOT_CUSTOMER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "noticeTemplateFieldLevelType")
@XmlEnum
public enum NoticeTemplateFieldLevelType {


    /**
     * Извещение
     * 
     */
    NOTICE,

    /**
     * Лот
     * 
     */
    LOT,

    /**
     * Сведения по заказчику
     * 
     */
    LOT_CUSTOMER;

    public String value() {
        return name();
    }

    public static NoticeTemplateFieldLevelType fromValue(String v) {
        return valueOf(v);
    }

}
