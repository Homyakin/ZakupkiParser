package ru.homyakin.documentsinfo.subdocumentsinfo;

public class CustomerInfo {
    //TODO Optional
    private String name;
    private String shortName;
    private String INN;
    private String KPP;
    private String OGRN;

    public CustomerInfo(String INN, String KPP, String OGRN) {
        this.setINN(INN);
        this.setKPP(KPP);
        this.setOGRN(OGRN);
    }

    public CustomerInfo(CustomerInfo customer) {
        this.name = customer.name;
        this.shortName = customer.shortName;
        this.INN = customer.INN;
        this.KPP = customer.KPP;
        this.OGRN = customer.OGRN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getINN() {
        return INN;
    }

    public void setINN(String iNN) {
        INN = iNN;
    }

    public String getKPP() {
        return KPP;
    }

    public void setKPP(String kPP) {
        KPP = kPP;
    }

    public String getOGRN() {
        return OGRN;
    }

    public void setOGRN(String oGRN) {
        OGRN = oGRN;
    }

}
