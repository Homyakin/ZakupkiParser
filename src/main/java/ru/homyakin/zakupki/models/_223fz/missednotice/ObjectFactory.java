//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.04.24 at 08:20:00 PM MSK 
//


package ru.homyakin.zakupki.models._223fz.missednotice;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.homyakin.zakupki.models._223fz.missednotice package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.homyakin.zakupki.models._223fz.missednotice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MissedNotice }
     * 
     */
    public MissedNotice createMissedNotice() {
        return new MissedNotice();
    }

    /**
     * Create an instance of {@link MissedNotice.Body }
     * 
     */
    public MissedNotice.Body createMissedNoticeBody() {
        return new MissedNotice.Body();
    }

    /**
     * Create an instance of {@link MissedNoticeItemType }
     * 
     */
    public MissedNoticeItemType createMissedNoticeItemType() {
        return new MissedNoticeItemType();
    }

    /**
     * Create an instance of {@link MissedNoticeData }
     * 
     */
    public MissedNoticeData createMissedNoticeData() {
        return new MissedNoticeData();
    }

}
