package ru.homyakin.documentsinfo.subdocumentsinfo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Optional;

@XmlRootElement(name = "purchaseTypeInfo", namespace = "http://zakupki.gov.ru/223fz/contract/1")
public class PurchaseTypeInfo {
    @XmlElement(name = "code", namespace = "http://zakupki.gov.ru/223fz/contract/1")
    private String code;
    @XmlElement(name = "name", namespace = "http://zakupki.gov.ru/223fz/contract/1")
    private String name;

    public PurchaseTypeInfo(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

}
