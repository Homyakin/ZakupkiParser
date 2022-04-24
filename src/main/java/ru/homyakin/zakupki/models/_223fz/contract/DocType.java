//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.04.24 at 08:20:03 PM MSK 
//


package ru.homyakin.zakupki.models._223fz.contract;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.homyakin.zakupki.models._223fz.types.CurrencyType;
import ru.homyakin.zakupki.models._223fz.types.OkeiProductType;


/**
 * Документ об исполнении
 * 
 * <p>Java class for docType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="docType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ordinal" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="code">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;enumeration value="01"/>
 *               &lt;enumeration value="02"/>
 *               &lt;enumeration value="03"/>
 *               &lt;enumeration value="04"/>
 *               &lt;enumeration value="05"/>
 *               &lt;enumeration value="06"/>
 *               &lt;enumeration value="07"/>
 *               &lt;enumeration value="08"/>
 *               &lt;enumeration value="09"/>
 *               &lt;enumeration value="10"/>
 *               &lt;enumeration value="11"/>
 *               &lt;enumeration value="12"/>
 *               &lt;enumeration value="13"/>
 *               &lt;enumeration value="18"/>
 *               &lt;enumeration value="19"/>
 *               &lt;enumeration value="20"/>
 *               &lt;enumeration value="21"/>
 *               &lt;enumeration value="22"/>
 *               &lt;enumeration value="23"/>
 *               &lt;enumeration value="24"/>
 *               &lt;enumeration value="25"/>
 *               &lt;enumeration value="26"/>
 *               &lt;enumeration value="31"/>
 *               &lt;enumeration value="37"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="name" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="2000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="number" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://zakupki.gov.ru/223fz/types/1}nonEmptyString">
 *               &lt;maxLength value="2000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="contractPosition" type="{http://zakupki.gov.ru/223fz/contract/1}contractPositionLinkType"/>
 *         &lt;element name="okei" type="{http://zakupki.gov.ru/223fz/types/1}okeiProductType"/>
 *         &lt;element name="qty" type="{http://zakupki.gov.ru/223fz/contract/1}contractPositionQtyType"/>
 *         &lt;element name="unitPrice" type="{http://zakupki.gov.ru/223fz/types/1}decimal20_11" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://zakupki.gov.ru/223fz/types/1}currencyType" minOccurs="0"/>
 *         &lt;element name="exchangeRate" type="{http://zakupki.gov.ru/223fz/types/1}decimal3_4" minOccurs="0"/>
 *         &lt;element name="exchangeRateDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="rubUnitPrice" type="{http://zakupki.gov.ru/223fz/types/1}decimal20_11" minOccurs="0"/>
 *         &lt;element name="prepayment" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="totalRub" type="{http://zakupki.gov.ru/223fz/types/1}money2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "docType", propOrder = {
    "ordinal",
    "date",
    "code",
    "name",
    "number",
    "contractPosition",
    "okei",
    "qty",
    "unitPrice",
    "currency",
    "exchangeRate",
    "exchangeRateDate",
    "rubUnitPrice",
    "prepayment",
    "totalRub"
})
public class DocType {

    protected long ordinal;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    @XmlElement(required = true)
    protected String code;
    protected String name;
    protected String number;
    @XmlElement(required = true)
    protected ContractPositionLinkType contractPosition;
    @XmlElement(required = true)
    protected OkeiProductType okei;
    @XmlElement(required = true)
    protected BigDecimal qty;
    protected BigDecimal unitPrice;
    protected CurrencyType currency;
    protected BigDecimal exchangeRate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar exchangeRateDate;
    protected BigDecimal rubUnitPrice;
    protected boolean prepayment;
    protected BigDecimal totalRub;

    /**
     * Gets the value of the ordinal property.
     * 
     */
    public long getOrdinal() {
        return ordinal;
    }

    /**
     * Sets the value of the ordinal property.
     * 
     */
    public void setOrdinal(long value) {
        this.ordinal = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the contractPosition property.
     * 
     * @return
     *     possible object is
     *     {@link ContractPositionLinkType }
     *     
     */
    public ContractPositionLinkType getContractPosition() {
        return contractPosition;
    }

    /**
     * Sets the value of the contractPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractPositionLinkType }
     *     
     */
    public void setContractPosition(ContractPositionLinkType value) {
        this.contractPosition = value;
    }

    /**
     * Gets the value of the okei property.
     * 
     * @return
     *     possible object is
     *     {@link OkeiProductType }
     *     
     */
    public OkeiProductType getOkei() {
        return okei;
    }

    /**
     * Sets the value of the okei property.
     * 
     * @param value
     *     allowed object is
     *     {@link OkeiProductType }
     *     
     */
    public void setOkei(OkeiProductType value) {
        this.okei = value;
    }

    /**
     * Gets the value of the qty property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQty() {
        return qty;
    }

    /**
     * Sets the value of the qty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQty(BigDecimal value) {
        this.qty = value;
    }

    /**
     * Gets the value of the unitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the value of the unitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUnitPrice(BigDecimal value) {
        this.unitPrice = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyType }
     *     
     */
    public CurrencyType getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyType }
     *     
     */
    public void setCurrency(CurrencyType value) {
        this.currency = value;
    }

    /**
     * Gets the value of the exchangeRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Sets the value of the exchangeRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExchangeRate(BigDecimal value) {
        this.exchangeRate = value;
    }

    /**
     * Gets the value of the exchangeRateDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExchangeRateDate() {
        return exchangeRateDate;
    }

    /**
     * Sets the value of the exchangeRateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExchangeRateDate(XMLGregorianCalendar value) {
        this.exchangeRateDate = value;
    }

    /**
     * Gets the value of the rubUnitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRubUnitPrice() {
        return rubUnitPrice;
    }

    /**
     * Sets the value of the rubUnitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRubUnitPrice(BigDecimal value) {
        this.rubUnitPrice = value;
    }

    /**
     * Gets the value of the prepayment property.
     * 
     */
    public boolean isPrepayment() {
        return prepayment;
    }

    /**
     * Sets the value of the prepayment property.
     * 
     */
    public void setPrepayment(boolean value) {
        this.prepayment = value;
    }

    /**
     * Gets the value of the totalRub property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalRub() {
        return totalRub;
    }

    /**
     * Sets the value of the totalRub property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalRub(BigDecimal value) {
        this.totalRub = value;
    }

}
