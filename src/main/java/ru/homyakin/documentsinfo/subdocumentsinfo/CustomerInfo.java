package ru.homyakin.documentsinfo.subdocumentsinfo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Optional;

@XmlRootElement(name = "customer", namespace = "http://zakupki.gov.ru/223fz/contract/1")
public class CustomerInfo {
    @XmlElementWrapper(name = "mainInfo")
    @XmlElement(name = "fullName")
    private String fullName;
    @XmlElementWrapper(name = "mainInfo")
    @XmlElement(name = "shortName")
    private String shortName;
    @XmlElementWrapper(name = "mainInfo")
    @XmlElement(name = "inn")
    private String INN;
    @XmlElementWrapper(name = "mainInfo")
    @XmlElement(name = "kpp")
    private String KPP;
    @XmlElementWrapper(name = "mainInfo")
    @XmlElement(name = "ogrn")
    private String OGRN;

    public CustomerInfo(String fullName, String shortName, String INN, String KPP, String OGRN) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.INN = INN;
        this.KPP = KPP;
        this.OGRN = OGRN;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(fullName);
    }

    public Optional<String> getShortName() {
        return Optional.ofNullable(shortName);
    }

    public String getINN() {
        return INN;
    }

    public String getKPP() {
        return KPP;
    }

    public String getOGRN() {
        return OGRN;
    }

}
