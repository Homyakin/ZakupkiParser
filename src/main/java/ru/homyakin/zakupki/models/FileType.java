package ru.homyakin.zakupki.models;

import java.util.List;
import java.util.Optional;

public enum FileType {
    CONTRACT("contract", List.of(Folder.CONTRACT)),
    PURCHASE_CONTRACT("purchaseContract", List.of(Folder.PURCHASE_CONTRACT)),
    PURCHASE_PLAN("purchasePlan", List.of(Folder.PURCHASE_PLAN)),
    PURCHASE_NOTICE("purchaseNotice", List.of(
        Folder.PURCHASE_NOTICE,
        Folder.PURCHASE_NOTICE_AE,
        Folder.PURCHASE_NOTICE_AE94,
        Folder.PURCHASE_NOTICE_AESMBO,
        Folder.PURCHASE_NOTICE_EP,
        Folder.PURCHASE_NOTICE_IS,
        Folder.PURCHASE_NOTICE_KESMBO,
        Folder.PURCHASE_NOTICE_OA,
        Folder.PURCHASE_NOTICE_OK,
        Folder.PURCHASE_NOTICE_ZK,
        Folder.PURCHASE_NOTICE_ZKESMBO,
        Folder.PURCHASE_NOTICE_ZPESMBO
    )),
    PURCHASE_PROTOCOL("purchaseProtocol", List.of(
        Folder.PURCHASE_PROTOCOL,
        Folder.PURCHASE_PROTOCOL_CCAESMBO,
        Folder.PURCHASE_PROTOCOL_CCKESMBO,
        Folder.PURCHASE_PROTOCOL_CCZKESMBO,
        Folder.PURCHASE_PROTOCOL_CCZPESMBO,
        //Folder.PURCHASE_PROTOCOL_CANCELLATION,
        Folder.PURCHASE_PROTOCOL_COLLACATION_AESMBO,
        Folder.PURCHASE_PROTOCOL_EVASION_AESMBO,
        Folder.PURCHASE_PROTOCOL_EVASION_KESMBO,
        Folder.PURCHASE_PROTOCOL_EVASION_ZKESMBO,
        Folder.PURCHASE_PROTOCOL_EVASION_ZPESMBO,
        Folder.PURCHASE_PROTOCOL_FCDKESMBO,
        Folder.PURCHASE_PROTOCOL_FKVOKESMBO,
        Folder.PURCHASE_PROTOCOL_IP,
        Folder.PURCHASE_PROTOCOL_OSZ,
        Folder.PURCHASE_PROTOCOL_PAAE,
        Folder.PURCHASE_PROTOCOL_PAAE94,
        Folder.PURCHASE_PROTOCOL_PAEP,
        Folder.PURCHASE_PROTOCOL_PAOA,
        Folder.PURCHASE_PROTOCOL_PA_AE,
        Folder.PURCHASE_PROTOCOL_PA_OA,
        Folder.PURCHASE_PROTOCOL_RKZ,
        Folder.PURCHASE_PROTOCOL_RZ1AE,
        Folder.PURCHASE_PROTOCOL_RZ1AE94FZ,
        Folder.PURCHASE_PROTOCOL_RZ1AESMBO,
        Folder.PURCHASE_PROTOCOL_RZ1KESMBO,
        Folder.PURCHASE_PROTOCOL_RZ1ZPESMBO,
        Folder.PURCHASE_PROTOCOL_RZ2AE,
        Folder.PURCHASE_PROTOCOL_RZ2AE94FZ,
        Folder.PURCHASE_PROTOCOL_RZ2AESMBO,
        Folder.PURCHASE_PROTOCOL_RZ2KESMBO,
        Folder.PURCHASE_PROTOCOL_RZ2ZPESMBO,
        Folder.PURCHASE_PROTOCOL_RZAE,
        Folder.PURCHASE_PROTOCOL_RZOA,
        Folder.PURCHASE_PROTOCOL_RZOK,
        Folder.PURCHASE_PROTOCOL_RZZKESMBO,
        Folder.PURCHASE_PROTOCOL_RZ_AE,
        Folder.PURCHASE_PROTOCOL_RZ_OA,
        Folder.PURCHASE_PROTOCOL_RZ_OK,
        Folder.PURCHASE_PROTOCOL_REJECTION_AESMBO,
        Folder.PURCHASE_PROTOCOL_REJECTION_KESMBO,
        Folder.PURCHASE_PROTOCOL_REJECTION_ZKESMBO,
        Folder.PURCHASE_PROTOCOL_REJECTION_ZPESMBO,
        Folder.PURCHASE_PROTOCOL_SUMMINGUP_AESMBO,
        Folder.PURCHASE_PROTOCOL_SUMMINGUP_KESMBO,
        Folder.PURCHASE_PROTOCOL_SUMMINGUP_ZKESMBO,
        Folder.PURCHASE_PROTOCOL_SUMMINGUP_ZPESMBO,
        Folder.PURCHASE_PROTOCOL_VK,
        Folder.PURCHASE_PROTOCOL_ZK,
        Folder.PURCHASE_PROTOCOL_ZRPZAESMBO,
        Folder.PURCHASE_PROTOCOL_ZRPZKESMBO,
        Folder.PURCHASE_PROTOCOL_ZRPZZKESMBO,
        Folder.PURCHASE_PROTOCOL_ZRPZZPESMBO
    )),
    CONTRACT_PERFORMANCE("Исполнение договора", List.of(
        Folder.CONTRACT_CANCELLATION_INFORMATION,
        Folder.PERFORMANCE_CONTRACT_INFORMATION
    )),
    ;

    private final String name;
    private final List<Folder> folders;

    FileType(String name, List<Folder> folders) {
        this.name = name;
        this.folders = folders;
    }

    public String getName() {
        return name;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public static Optional<FileType> fromString(String text) {
        for (FileType i : FileType.values()) {
            if (i.name.equalsIgnoreCase(text)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public static Optional<FileType> fromFolder(Folder folder) {
        for (FileType i : FileType.values()) {
            if (i.folders.contains(folder)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }
}
