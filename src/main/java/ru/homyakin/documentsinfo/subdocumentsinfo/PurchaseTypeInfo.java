package ru.homyakin.documentsinfo.subdocumentsinfo;

import java.util.Optional;

public class PurchaseTypeInfo {
    private String code;
    private Optional<String> name;

    public PurchaseTypeInfo(String code, String name) {
        this.code = code;
        this.name = Optional.ofNullable(name);
    }

    public String getCode() {
        return code;
    }

    public Optional<String> getName() {
        return name;
    }

}
