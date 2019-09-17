package ru.homyakin.documentsinfo.subdocumentsinfo;

import java.util.Optional;

public class CustomerInfo {
    private Optional<String> fullName;
    private Optional<String> shortName;
    private String INN;
    private String KPP;
    private String OGRN;

    public CustomerInfo(String fullName, String shortName, String INN, String KPP, String OGRN) {
        this.fullName = Optional.ofNullable(fullName);
        this.shortName = Optional.ofNullable(shortName);
        this.INN = INN;
        this.KPP = KPP;
        this.OGRN = OGRN;
    }

    public Optional<String> getName() {
        return fullName;
    }

    public Optional<String> getShortName() {
        return shortName;
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
