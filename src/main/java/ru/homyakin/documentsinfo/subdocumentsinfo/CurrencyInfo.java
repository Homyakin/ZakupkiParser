package ru.homyakin.documentsinfo.subdocumentsinfo;

public class CurrencyInfo {
    //TODO add Optional
    //TODO make get code only 1 (return code or letter code)
    private String letterCode;
    private String code;
    private String digitalCode;
    private String name;

    public CurrencyInfo(String name) {
        this.setName(name);
    }

    public String getLetterCode() {
        return letterCode;
    }

    public void setLetterCode(String letterCode) {
        this.letterCode = letterCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDigitalCode() {
        return digitalCode;
    }

    public void setDigitalCode(String digitalCode) {
        this.digitalCode = digitalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
