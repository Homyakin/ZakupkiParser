package ru.homyakin.zakupki.database.purchase_protocol.utils;

import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolPAOADataType;

public class PurchaseNoticePaoaMapper {
    public static PurchaseProtocolDataType mapToDataType(PurchaseProtocolPAOADataType paoaDataType) {
        var dataType = new PurchaseProtocolDataType();
        dataType.setGuid(paoaDataType.getGuid());
        dataType.setCreateDateTime(paoaDataType.getCreateDateTime());
        dataType.setUrlEIS(paoaDataType.getUrlEIS());
        dataType.setUrlVSRZ(paoaDataType.getUrlVSRZ());
        dataType.setUrlKisRmis(paoaDataType.getUrlKisRmis());
        dataType.setPurchaseInfo(paoaDataType.getPurchaseInfo());
        dataType.setRegistrationNumber(paoaDataType.getRegistrationNumber());
        dataType.setPlacer(paoaDataType.getPlacer());
        dataType.setCustomer(paoaDataType.getCustomer());
        dataType.setAdditionalInfo(paoaDataType.getAdditionalInfo());
        dataType.setMissedContest(paoaDataType.isMissedContest());
        dataType.setMissedReason(paoaDataType.getMissedReason());
        dataType.setPurchaseCancellationReason(paoaDataType.getPurchaseCancellationReason());
        dataType.setPublicationDateTime(paoaDataType.getPublicationDateTime());
        dataType.setStatus(paoaDataType.getStatus());
        dataType.setVersion(paoaDataType.getVersion());
        dataType.setModificationDescription(paoaDataType.getModificationDescription());
        dataType.setType(0);
        dataType.setTypeName("Протокол проведения аукциона для открытого аукциона");
        dataType.setProcedureDate(paoaDataType.getProcedureDate());
        dataType.setProcedurePlace(paoaDataType.getProcedurePlace());
        dataType.setProtocolSignDate(paoaDataType.getProtocolSignDate());
        dataType.setTemplateVersion(paoaDataType.getProtocolRZVersion());
        dataType.setIsLotOriented(null);
        dataType.setCommissionNumber(paoaDataType.getCommissionNumber());
        dataType.setCommissionName(paoaDataType.getCommissionName());
        dataType.setCommissionResult(paoaDataType.getCommissionResult());
        dataType.setLotApplicationsList(PaoaApplicationsMapper.mapApplicationList(paoaDataType.getLotApplicationsList()));
        return dataType;
    }
}
