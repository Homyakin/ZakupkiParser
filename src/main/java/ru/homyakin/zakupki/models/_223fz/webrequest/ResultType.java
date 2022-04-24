//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.04.24 at 08:20:07 PM MSK 
//


package ru.homyakin.zakupki.models._223fz.webrequest;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resultType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="resultType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="ERR_EMPTY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "resultType")
@XmlEnum
public enum ResultType {


    /**
     * Сведения найдены и присутствуют в выгрузке
     * 
     */
    OK,

    /**
     * Сведения для электронной площадки не были найдены в системе
     * 
     */
    ERR_EMPTY;

    public String value() {
        return name();
    }

    public static ResultType fromValue(String v) {
        return valueOf(v);
    }

}
