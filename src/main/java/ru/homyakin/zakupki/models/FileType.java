package ru.homyakin.zakupki.models;

public enum FileType {
    CONTRACT("contract"),
    PURCHASE_PLAN("purchasePlan");

    private String value;

    FileType(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }

    public static FileType fromString(String text) {
        for (FileType i : FileType.values()) {
            if (i.value.equalsIgnoreCase(text)) {
                return i;
            }
        }
        return null;
    }

}
