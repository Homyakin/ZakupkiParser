package ru.homyakin.documentsinfo.subdocumentsinfo;

import ru.homyakin.exceptions.IllegalXMLDataException;

import java.util.Optional;

public class CurrencyInfo {
    private Optional<String> letterCode;
    private Optional<String> code; //letterCode or code always exists
    private Optional<String> digitalCode;
    private String name;

    public CurrencyInfo(String letterCode, String code, String digitalCode, String name) {
        this.letterCode = Optional.ofNullable(letterCode);
        this.code = Optional.ofNullable(code);
        this.digitalCode = Optional.ofNullable(digitalCode);
        this.name = name;
    }

    public String getLetterCode() {
        return letterCode.orElseGet(() -> code.orElseThrow(() -> new IllegalXMLDataException("currency")));
    }

    public Optional<String> getDigitalCode() {
        return digitalCode;
    }

    public String getName() {
        return name;
    }

}
