package ru.homyakin.zakupki.database.purchase_protocol.utils;

import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAAE94FZApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAAE94FZLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAAE94FZLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolPAAE94FZDataType;

public class PurchaseProtocolPaae94fzMapper {
    public static PurchaseProtocolDataType mapToDataType(PurchaseProtocolPAAE94FZDataType paae94fzDataType) {
        var dataType = new PurchaseProtocolDataType();
        dataType.setGuid(paae94fzDataType.getGuid());
        dataType.setCreateDateTime(paae94fzDataType.getCreateDateTime());
        dataType.setUrlEIS(paae94fzDataType.getUrlEIS());
        dataType.setUrlVSRZ(paae94fzDataType.getUrlVSRZ());
        dataType.setUrlKisRmis(paae94fzDataType.getUrlKisRmis());
        dataType.setPurchaseInfo(paae94fzDataType.getPurchaseInfo());
        dataType.setRegistrationNumber(paae94fzDataType.getRegistrationNumber());
        dataType.setPlacer(paae94fzDataType.getPlacer());
        dataType.setCustomer(paae94fzDataType.getCustomer());
        dataType.setAdditionalInfo(paae94fzDataType.getAdditionalInfo());
        dataType.setMissedContest(paae94fzDataType.isMissedContest());
        dataType.setMissedReason(paae94fzDataType.getMissedReason());
        dataType.setPurchaseCancellationReason(paae94fzDataType.getPurchaseCancellationReason());
        dataType.setPublicationDateTime(paae94fzDataType.getPublicationDateTime());
        dataType.setStatus(paae94fzDataType.getStatus());
        dataType.setVersion(paae94fzDataType.getVersion());
        dataType.setModificationDescription(paae94fzDataType.getModificationDescription());
        dataType.setType(0);
        dataType.setTypeName("Протокол проведения аукциона для открытого аукциона в ЭФ (по 94-ФЗ)");
        dataType.setProcedureDate(paae94fzDataType.getProcedureDate());
        dataType.setProcedurePlace(paae94fzDataType.getProcedurePlace());
        dataType.setProtocolSignDate(paae94fzDataType.getProtocolSignDate());
        dataType.setIsLotOriented(null);
        dataType.setCommissionNumber(paae94fzDataType.getCommissionNumber());
        dataType.setCommissionName(paae94fzDataType.getCommissionName());
        dataType.setCommissionResult(paae94fzDataType.getCommissionResult());
        dataType.setLotApplicationsList(mapApplicationList(paae94fzDataType.getLotApplicationsList()));
        return dataType;
    }

    private static ProtocolLotApplicationListType mapApplicationList(ProtocolPAAE94FZLotApplicationListType paae94fzListType) {
        if (paae94fzListType == null) return null;
        var listType = new ProtocolLotApplicationListType();
        if (paae94fzListType.getProtocolLotApplications() != null) {
            listType.setProtocolLotApplications(
                paae94fzListType.getProtocolLotApplications().stream()
                    .map(PurchaseProtocolPaae94fzMapper::mapLotApplications)
                    .toList()
            );
        }
        return listType;
    }

    private static ProtocolLotApplications mapLotApplications(ProtocolPAAE94FZLotApplications paae94fzLotApplications) {
        var lotApplications = new ProtocolLotApplications();
        lotApplications.setLot(paae94fzLotApplications.getLot());
        lotApplications.setLotParameters(paae94fzLotApplications.getLotParameters());
        if (paae94fzLotApplications.getApplication() != null) {
            lotApplications.setApplication(
                paae94fzLotApplications.getApplication().stream()
                    .map(PurchaseProtocolPaae94fzMapper::mapApplication)
                    .toList()
            );
        }
        return lotApplications;
    }

    private static ProtocolApplicationType mapApplication(ProtocolPAAE94FZApplicationType paae94fzApplicationType) {
        var application = new ProtocolApplicationType();
        application.setApplicationDate(paae94fzApplicationType.getApplicationDate());
        application.setApplicationNumber(paae94fzApplicationType.getApplicationNumber());
        application.setPrice(paae94fzApplicationType.getLastPrice());
        application.setPriceInfo(paae94fzApplicationType.getLastPriceInfo());
        return application;
    }
}
