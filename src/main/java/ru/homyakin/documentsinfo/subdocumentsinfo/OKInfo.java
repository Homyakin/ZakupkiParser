package ru.homyakin.documentsinfo.subdocumentsinfo;

public class OKInfo {
    //TODO create interface and different impl
    private String code;
    private String name;

    public OKInfo(String code) {
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
