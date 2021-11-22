package ru.homyakin.zakupki.database.purchase_protocol.utils;

import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZ2AEApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZ2AELotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZ2AELotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZ2AE94FZDataType;

public class PurchaseProtocolRz2ae94fzMapper {
    public static PurchaseProtocolDataType mapToDataType(PurchaseProtocolRZ2AE94FZDataType rz2AE94FZDataType) {
        var dataType = new PurchaseProtocolDataType();
        dataType.setGuid(rz2AE94FZDataType.getGuid());
        dataType.setCreateDateTime(rz2AE94FZDataType.getCreateDateTime());
        dataType.setUrlEIS(rz2AE94FZDataType.getUrlEIS());
        dataType.setUrlVSRZ(rz2AE94FZDataType.getUrlVSRZ());
        dataType.setUrlKisRmis(rz2AE94FZDataType.getUrlKisRmis());
        dataType.setPurchaseInfo(rz2AE94FZDataType.getPurchaseInfo());
        dataType.setRegistrationNumber(rz2AE94FZDataType.getRegistrationNumber());
        dataType.setPlacer(rz2AE94FZDataType.getPlacer());
        dataType.setCustomer(rz2AE94FZDataType.getCustomer());
        dataType.setAdditionalInfo(rz2AE94FZDataType.getAdditionalInfo());
        dataType.setMissedContest(rz2AE94FZDataType.isMissedContest());
        dataType.setMissedReason(rz2AE94FZDataType.getMissedReason());
        dataType.setPurchaseCancellationReason(rz2AE94FZDataType.getPurchaseCancellationReason());
        dataType.setPublicationDateTime(rz2AE94FZDataType.getPublicationDateTime());
        dataType.setStatus(rz2AE94FZDataType.getStatus());
        dataType.setVersion(rz2AE94FZDataType.getVersion());
        dataType.setModificationDescription(rz2AE94FZDataType.getModificationDescription());
        dataType.setType(0);
        dataType.setTypeName("Протокол рассмотрения вторых частей заявок для открытого аукциона в ЭФ (по 94-ФЗ)");
        dataType.setTargetPhaseCode(null);
        dataType.setProcedureDate(rz2AE94FZDataType.getProcedureDate());
        dataType.setProcedurePlace(rz2AE94FZDataType.getProcedurePlace());
        dataType.setProtocolSignDate(rz2AE94FZDataType.getProtocolSignDate());
        dataType.setIsLotOriented(null);
        dataType.setCommissionNumber(rz2AE94FZDataType.getCommissionNumber());
        dataType.setCommissionName(rz2AE94FZDataType.getCommissionName());
        dataType.setCommissionResult(rz2AE94FZDataType.getCommissionResult());
        dataType.setLotApplicationsList(mapApplicationList(rz2AE94FZDataType.getLotApplicationsList()));
        return dataType;
    }

    private static ProtocolLotApplicationListType mapApplicationList(ProtocolRZ2AELotApplicationListType rz2aeListType) {
        if (rz2aeListType == null) return null;
        var listType = new ProtocolLotApplicationListType();
        if (rz2aeListType.getProtocolLotApplications() != null) {
            listType.setProtocolLotApplications(
                rz2aeListType.getProtocolLotApplications().stream()
                    .map(PurchaseProtocolRz2ae94fzMapper::mapLotApplications)
                    .toList()
            );
        }
        return listType;
    }

    private static ProtocolLotApplications mapLotApplications(ProtocolRZ2AELotApplications rz2aeLotApplications) {
        var lotApplications = new ProtocolLotApplications();
        var lot = new ProtocolLotType();
        lot.setGuid(rz2aeLotApplications.getGuid());
        lot.setOrdinalNumber(rz2aeLotApplications.getOrdinalNumber());
        lot.setSubject(rz2aeLotApplications.getSubject());
        lot.setInitialSum(rz2aeLotApplications.getInitialSum());
        lot.setCurrency(rz2aeLotApplications.getCurrency());
        lot.setPriceFormula(rz2aeLotApplications.getPriceFormula());
        lot.setCommodityPrice(rz2aeLotApplications.getCommodityPrice());
        lot.setPriceFormula(rz2aeLotApplications.getPriceFormula());
        lot.setInitialSumInfo(rz2aeLotApplications.getInitialSumInfo());
        lot.setMaxContractPrice(rz2aeLotApplications.getMaxContractPrice());
        lotApplications.setLot(lot);
        if (rz2aeLotApplications.getApplication() != null) {
            lotApplications.setApplication(
                rz2aeLotApplications.getApplication().stream()
                    .map(PurchaseProtocolRz2ae94fzMapper::mapApplication)
                    .toList()
            );
        }
        return lotApplications;
    }

    private static ProtocolApplicationType mapApplication(ProtocolRZ2AEApplicationType rz2aeApplicationType) {
        var application = new ProtocolApplicationType();
        application.setApplicationDate(rz2aeApplicationType.getApplicationDate());
        application.setApplicationNumber(rz2aeApplicationType.getApplicationNumber());
        application.setNotDishonest(rz2aeApplicationType.isNotDishonest());
        application.setProvider(rz2aeApplicationType.isProvider());
        application.setSupplierInfo(rz2aeApplicationType.getSupplierInfo());
        application.setNonResidentInfo(rz2aeApplicationType.getNonResidentInfo());
        application.setPrice(rz2aeApplicationType.getLastPrice());
        application.setCurrency(null);
        application.setPriceInfo(rz2aeApplicationType.getLastPriceInfo());
        application.setCommodityAmount(rz2aeApplicationType.getCommodityAmount());
        application.setContractExecutionTerm(rz2aeApplicationType.getContractExecutionTerm());
        application.setConditionProposals(rz2aeApplicationType.getConditionProposals());
        application.setAccepted(CommonMapper.mapAcceptedTypeToAcceptedType2(rz2aeApplicationType.getAccepted()));
        application.setRejectionReasonCode(rz2aeApplicationType.getRejectionReasonCode());
        application.setRejectionReason(rz2aeApplicationType.getRejectionReason());
        application.setWinnerIndication(null);
        application.setCommissionDecision(null);
        application.setContractSigned(rz2aeApplicationType.isContractSigned());
        application.setCommissionDecisionPlace(rz2aeApplicationType.getCommissionDecisionPlace());
        application.setCriteria(rz2aeApplicationType.getCriteria());
        application.setAdditionalPrice(null);
        application.setAssessmentResult(rz2aeApplicationType.getAssessmentResult());
        application.setRating(null);
        return application;
    }
}
