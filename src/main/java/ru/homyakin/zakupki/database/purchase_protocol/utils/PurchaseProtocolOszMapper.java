package ru.homyakin.zakupki.database.purchase_protocol.utils;

import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolOSZApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolOSZLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolOSZLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolOSZDataType;

public class PurchaseProtocolOszMapper {
    public static PurchaseProtocolDataType mapToDataType(PurchaseProtocolOSZDataType oszDataType) {
        var dataType = new PurchaseProtocolDataType();
        dataType.setGuid(oszDataType.getGuid());
        dataType.setCreateDateTime(oszDataType.getCreateDateTime());
        dataType.setUrlEIS(oszDataType.getUrlEIS());
        dataType.setUrlVSRZ(oszDataType.getUrlVSRZ());
        dataType.setUrlKisRmis(oszDataType.getUrlKisRmis());
        dataType.setPurchaseInfo(oszDataType.getPurchaseInfo());
        dataType.setRegistrationNumber(oszDataType.getRegistrationNumber());
        dataType.setPlacer(oszDataType.getPlacer());
        dataType.setCustomer(oszDataType.getCustomer());
        dataType.setAdditionalInfo(oszDataType.getAdditionalInfo());
        dataType.setMissedContest(oszDataType.isMissedContest());
        dataType.setMissedReason(oszDataType.getMissedReason());
        dataType.setPurchaseCancellationReason(oszDataType.getPurchaseCancellationReason());
        dataType.setPublicationDateTime(oszDataType.getPublicationDateTime());
        dataType.setStatus(oszDataType.getStatus());
        dataType.setVersion(oszDataType.getVersion());
        dataType.setModificationDescription(oszDataType.getModificationDescription());
        dataType.setType(0);
        dataType.setTypeName("Протокол оценки и сопоставления заявок для открытого конкурса");
        dataType.setTargetPhaseCode(null);
        dataType.setProcedureDate(oszDataType.getProcedureDate());
        dataType.setProcedurePlace(oszDataType.getProcedurePlace());
        dataType.setProtocolSignDate(oszDataType.getProtocolSignDate());
        dataType.setTemplateVersion(oszDataType.getProtocolRZVersion()); // Возможны несовпадения
        dataType.setIsLotOriented(null);
        dataType.setCommissionNumber(oszDataType.getCommissionNumber());
        dataType.setCommissionName(oszDataType.getCommissionName());
        dataType.setCommissionResult(oszDataType.getCommissionResult());
        dataType.setLotApplicationsList(mapApplicationList(oszDataType.getLotApplicationsList()));
        return dataType;
    }

    private static ProtocolLotApplicationListType mapApplicationList(ProtocolOSZLotApplicationListType oszListType) {
        if (oszListType == null) return null;
        var listType = new ProtocolLotApplicationListType();
        if (oszListType.getProtocolLotApplications() != null) {
            listType.setProtocolLotApplications(
                oszListType.getProtocolLotApplications().stream()
                    .map(PurchaseProtocolOszMapper::mapLotApplications)
                    .toList()
            );
        }
        return listType;
    }

    private static ProtocolLotApplications mapLotApplications(ProtocolOSZLotApplications oszLotApplications) {
        var lotApplications = new ProtocolLotApplications();
        var lot = new ProtocolLotType();
        lot.setGuid(oszLotApplications.getGuid());
        lot.setOrdinalNumber(oszLotApplications.getOrdinalNumber());
        lot.setSubject(oszLotApplications.getSubject());
        lotApplications.setLot(lot);
        lotApplications.setCriteria(oszLotApplications.getCriteria());
        if (oszLotApplications.getApplication() != null) {
            lotApplications.setApplication(
                oszLotApplications.getApplication().stream()
                    .map(PurchaseProtocolOszMapper::mapApplication)
                    .toList()
            );
        }
        return lotApplications;
    }

    private static ProtocolApplicationType mapApplication(ProtocolOSZApplicationType oszApplicationType) {
        var application = new ProtocolApplicationType();
        application.setApplicationDate(oszApplicationType.getApplicationDate());
        application.setApplicationNumber(oszApplicationType.getApplicationNumber());
        application.setNotDishonest(oszApplicationType.isNotDishonest());
        application.setProvider(oszApplicationType.isProvider());
        application.setSupplierInfo(oszApplicationType.getSupplierInfo());
        application.setNonResidentInfo(oszApplicationType.getNonResidentInfo());
        application.setCommodityAmount(oszApplicationType.getCommodityAmount());
        application.setContractExecutionTerm(oszApplicationType.getContractExecutionTerm());
        application.setConditionProposals(oszApplicationType.getConditionProposals());
        application.setContractSigned(oszApplicationType.isContractSigned());
        application.setCommissionDecisionPlace(oszApplicationType.getCommissionDecisionPlace());
        application.setCriteria(oszApplicationType.getCriteria());
        return application;
    }
}
