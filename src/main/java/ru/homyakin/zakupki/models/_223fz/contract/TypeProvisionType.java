//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.05 at 11:35:30 PM MSK 
//


package ru.homyakin.zakupki.models._223fz.contract;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeProvisionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="typeProvisionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RESOURCES_FZ44"/>
 *     &lt;enumeration value="RESOURCES_FZ44_NATIONAL_PROJECTS"/>
 *     &lt;enumeration value="NATIONAL_PROJECTS_SUBSIDIES"/>
 *     &lt;enumeration value="NATIONAL_FEDERAL_PROJECTS_SUBSIDIES"/>
 *     &lt;enumeration value="NO_BUDGET_RESOURCES_FZ44"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "typeProvisionType")
@XmlEnum
public enum TypeProvisionType {


    /**
     * За счет средств, предусмотренных контрактом 44-ФЗ
     * 
     */
    @XmlEnumValue("RESOURCES_FZ44")
    RESOURCES_FZ_44("RESOURCES_FZ44"),

    /**
     * За счет средств, предусмотренных контрактом 44-ФЗ, выделяемых в рамках национальных проектов
     * 
     */
    @XmlEnumValue("RESOURCES_FZ44_NATIONAL_PROJECTS")
    RESOURCES_FZ_44_NATIONAL_PROJECTS("RESOURCES_FZ44_NATIONAL_PROJECTS"),

    /**
     * За счет средств субсидий, выделяемых в рамках национальных проектов/За счет средств субсидии, предоставляемой в целях реализации национальных и федеральных проектов и (или) комплексного плана модернизации и расширения магистральной инфраструктуры
     * 
     */
    NATIONAL_PROJECTS_SUBSIDIES("NATIONAL_PROJECTS_SUBSIDIES"),

    /**
     * За счет средств субсидии, предоставляемой в целях реализации национальных и федеральных проектов и (или) комплексного плана модернизации и расширения магистральной инфраструктуры
     * 
     */
    NATIONAL_FEDERAL_PROJECTS_SUBSIDIES("NATIONAL_FEDERAL_PROJECTS_SUBSIDIES"),

    /**
     * Нет финансирования за счет бюджетных средств
     * 
     */
    @XmlEnumValue("NO_BUDGET_RESOURCES_FZ44")
    NO_BUDGET_RESOURCES_FZ_44("NO_BUDGET_RESOURCES_FZ44");
    private final String value;

    TypeProvisionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TypeProvisionType fromValue(String v) {
        for (TypeProvisionType c: TypeProvisionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}