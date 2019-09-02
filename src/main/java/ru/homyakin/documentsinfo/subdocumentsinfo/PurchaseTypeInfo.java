package ru.homyakin.documentsinfo.subdocumentsinfo;

public class PurchaseTypeInfo {
    private String code;
    private String name;

    public PurchaseTypeInfo(String code) {
        this.setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
