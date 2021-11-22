package ru.homyakin.zakupki.database.purchase_protocol.utils;

import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZ1AEApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZ1AELotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZ1AELotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZ1AE94FZDataType;

public class PurchaseProtocolRz1ae94fzMapper {
    public static PurchaseProtocolDataType mapToDataType(PurchaseProtocolRZ1AE94FZDataType rz1AE94FZDataType) {
        var dataType = new PurchaseProtocolDataType();
        dataType.setGuid(rz1AE94FZDataType.getGuid());
        dataType.setCreateDateTime(rz1AE94FZDataType.getCreateDateTime());
        dataType.setUrlEIS(rz1AE94FZDataType.getUrlEIS());
        dataType.setUrlVSRZ(rz1AE94FZDataType.getUrlVSRZ());
        dataType.setUrlKisRmis(rz1AE94FZDataType.getUrlKisRmis());
        dataType.setPurchaseInfo(rz1AE94FZDataType.getPurchaseInfo());
        dataType.setRegistrationNumber(rz1AE94FZDataType.getRegistrationNumber());
        dataType.setPlacer(rz1AE94FZDataType.getPlacer());
        dataType.setCustomer(rz1AE94FZDataType.getCustomer());
        dataType.setAdditionalInfo(rz1AE94FZDataType.getAdditionalInfo());
        dataType.setMissedContest(rz1AE94FZDataType.isMissedContest());
        dataType.setMissedReason(rz1AE94FZDataType.getMissedReason());
        dataType.setPurchaseCancellationReason(rz1AE94FZDataType.getPurchaseCancellationReason());
        dataType.setPublicationDateTime(rz1AE94FZDataType.getPublicationDateTime());
        dataType.setStatus(rz1AE94FZDataType.getStatus());
        dataType.setVersion(rz1AE94FZDataType.getVersion());
        dataType.setModificationDescription(rz1AE94FZDataType.getModificationDescription());
        dataType.setType(0);
        dataType.setTypeName("Протокол рассмотрения первых частей заявок для открытого аукциона в ЭФ (по 94-ФЗ)");
        dataType.setTargetPhaseCode(null);
        dataType.setProcedureDate(rz1AE94FZDataType.getProcedureDate());
        dataType.setProcedurePlace(rz1AE94FZDataType.getProcedurePlace());
        dataType.setProtocolSignDate(rz1AE94FZDataType.getProtocolSignDate());
        dataType.setIsLotOriented(null);
        dataType.setCommissionNumber(rz1AE94FZDataType.getCommissionNumber());
        dataType.setCommissionName(rz1AE94FZDataType.getCommissionName());
        dataType.setCommissionResult(rz1AE94FZDataType.getCommissionResult());
        dataType.setLotApplicationsList(mapApplicationList(rz1AE94FZDataType.getLotApplicationsList()));
        return dataType;
    }

    private static ProtocolLotApplicationListType mapApplicationList(ProtocolRZ1AELotApplicationListType rz1aeListType) {
        if (rz1aeListType == null) return null;
        var listType = new ProtocolLotApplicationListType();
        if (rz1aeListType.getProtocolLotApplications() != null) {
            listType.setProtocolLotApplications(
                rz1aeListType.getProtocolLotApplications().stream()
                    .map(PurchaseProtocolRz1ae94fzMapper::mapLotApplications)
                    .toList()
            );
        }
        return listType;
    }

    private static ProtocolLotApplications mapLotApplications(ProtocolRZ1AELotApplications rz1aeLotApplications) {
        var lotApplications = new ProtocolLotApplications();
        var lot = new ProtocolLotType();
        lot.setGuid(rz1aeLotApplications.getGuid());
        lot.setOrdinalNumber(rz1aeLotApplications.getOrdinalNumber());
        lot.setSubject(rz1aeLotApplications.getSubject());
        lot.setInitialSum(rz1aeLotApplications.getInitialSum());
        lot.setCurrency(rz1aeLotApplications.getCurrency());
        lot.setPriceFormula(rz1aeLotApplications.getPriceFormula());
        lot.setCommodityPrice(rz1aeLotApplications.getCommodityPrice());
        lot.setPriceFormula(rz1aeLotApplications.getPriceFormula());
        lot.setMaxContractPrice(rz1aeLotApplications.getMaxContractPrice());
        lot.setInitialSumInfo(rz1aeLotApplications.getInitialSumInfo());
        lotApplications.setLot(lot);
        if (rz1aeLotApplications.getApplication() != null) {
            lotApplications.setApplication(
                rz1aeLotApplications.getApplication().stream()
                    .map(PurchaseProtocolRz1ae94fzMapper::mapApplication)
                    .toList()
            );
        }
        return lotApplications;
    }

    private static ProtocolApplicationType mapApplication(ProtocolRZ1AEApplicationType rz1aeApplicationType) {
        var application = new ProtocolApplicationType();
        application.setApplicationDate(rz1aeApplicationType.getApplicationDate());
        application.setApplicationNumber(rz1aeApplicationType.getApplicationNumber());
        application.setAccepted(CommonMapper.mapAcceptedTypeToAcceptedType2(rz1aeApplicationType.getAccepted()));
        application.setRejectionReason(rz1aeApplicationType.getRejectionReason());
        application.setRejectionReasonCode(rz1aeApplicationType.getRejectionReasonCode());
        return application;
    }
}
