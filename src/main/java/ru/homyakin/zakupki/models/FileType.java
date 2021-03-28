package ru.homyakin.zakupki.models;

import java.util.Optional;

public enum FileType {
    CONTRACT("contract"),
    PURCHASE_CONTRACT("purchaseContract"),
    PURCHASE_PLAN("purchasePlan"),
    PURCHASE_NOTICE("purchaseNotice"),
    PURCHASE_NOTICE_AE("purchaseNoticeAE"),
    PURCHASE_NOTICE_AE94("purchaseNoticeAE94"),
    PURCHASE_NOTICE_AESMBO("purchaseNoticeAESMBO"),
    PURCHASE_NOTICE_EP("purchaseNoticeEP"),
    PURCHASE_NOTICE_IS("purchaseNoticeIS"),
    PURCHASE_NOTICE_KESMBO("purchaseNoticeKESMBO"),
    PURCHASE_NOTICE_OA("purchaseNoticeOA"),
    PURCHASE_NOTICE_OK("purchaseNoticeOK"),
    PURCHASE_NOTICE_ZK("purchaseNoticeZK"),
    PURCHASE_NOTICE_ZKESMBO("purchaseNoticeZKESMBO"),
    PURCHASE_NOTICE_ZPESMBO("purchaseNoticeZPESMBO");

    private final String value;

    FileType(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }

    public static Optional<FileType> fromString(String text) {
        for (FileType i : FileType.values()) {
            if (i.value.equalsIgnoreCase(text)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }
}
