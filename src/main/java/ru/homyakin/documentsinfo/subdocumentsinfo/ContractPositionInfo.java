package ru.homyakin.documentsinfo.subdocumentsinfo;

import java.math.BigDecimal;
import java.util.Optional;

public class ContractPositionInfo {
    private Optional<String> GUID;
    private Optional<String> name;
    private Integer ordinalNumber;
    private Optional<OKInfo> OKDP;
    private Optional<OKInfo> OKPD;
    private Optional<OKInfo> OKPD2;
    private Optional<String> country; // TODO country - complicated structure
    private Optional<String> producerCountry; // TODO boolean - ?
    private Optional<OKInfo> OKEI;
    private Optional<BigDecimal> qty;

    public ContractPositionInfo(String GUID, String name, Integer ordinalNumber, OKInfo OKDP, OKInfo OKPD, OKInfo OKPD2,
                                OKInfo OKEI, BigDecimal qty) {
        this.ordinalNumber = ordinalNumber;
        this.GUID = Optional.ofNullable(GUID);
        this.name = Optional.ofNullable(name);
        this.OKDP = Optional.ofNullable(OKDP);
        this.OKPD = Optional.ofNullable(OKPD);
        this.OKPD2 = Optional.ofNullable(OKPD2);
        this.OKEI = Optional.ofNullable(OKEI);
        this.qty = Optional.ofNullable(qty);

    }

    public Optional<String> getGUID() {
        return GUID;
    }

    public Optional<String> getName() {
        return name;
    }

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    public Optional<OKInfo> getOKDP() {
        return OKDP;
    }

    public Optional<OKInfo> getOKPD() {
        return OKPD;
    }

    public Optional<OKInfo> getOKPD2() {
        return OKPD2;
    }

    public Optional<String> getCountry() {
        return country;
    }

    public Optional<String> getProducerCountry() {
        return producerCountry;
    }

    public Optional<OKInfo> getOKEI() {
        return OKEI;
    }

    public Optional<BigDecimal> getQty() {
        return qty;
    }

}
