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

public class PurchaseNoticeOszMapper {
    public static PurchaseProtocolDataType mapOszToDataType(PurchaseProtocolOSZDataType osz) {
        var dataType = new PurchaseProtocolDataType();
        dataType.setGuid(osz.getGuid());
        dataType.setCreateDateTime(osz.getCreateDateTime());
        dataType.setUrlEIS(osz.getUrlEIS());
        dataType.setUrlVSRZ(osz.getUrlVSRZ());
        dataType.setUrlKisRmis(osz.getUrlKisRmis());
        dataType.setPurchaseInfo(osz.getPurchaseInfo());
        dataType.setRegistrationNumber(osz.getRegistrationNumber());
        dataType.setPlacer(osz.getPlacer());
        dataType.setCustomer(osz.getCustomer());
        dataType.setAdditionalInfo(osz.getAdditionalInfo());
        dataType.setMissedContest(osz.isMissedContest());
        dataType.setMissedReason(osz.getMissedReason());
        dataType.setPurchaseCancellationReason(osz.getPurchaseCancellationReason());
        dataType.setPublicationDateTime(osz.getPublicationDateTime());
        dataType.setStatus(osz.getStatus());
        dataType.setVersion(osz.getVersion());
        dataType.setModificationDescription(osz.getModificationDescription());
        dataType.setType(0);
        dataType.setTypeName("Протокол оценки и сопоставления заявок для открытого конкурса");
        dataType.setTargetPhaseCode(null);
        dataType.setProcedureDate(osz.getProcedureDate());
        dataType.setProcedurePlace(osz.getProcedurePlace());
        dataType.setProtocolSignDate(osz.getProtocolSignDate());
        dataType.setTemplateVersion(osz.getProtocolRZVersion()); // Возможны несовпадения
        dataType.setIsLotOriented(null);
        dataType.setCommissionNumber(osz.getCommissionNumber());
        dataType.setCommissionName(osz.getCommissionName());
        dataType.setCommissionResult(osz.getCommissionResult());
        dataType.setLotApplicationsList(mapApplicationList(osz.getLotApplicationsList()));
        return dataType;
    }

    private static ProtocolLotApplicationListType mapApplicationList(ProtocolOSZLotApplicationListType oszListType) {
        if (oszListType == null) return null;
        oszListType.getProtocolLotApplications();
        var listType = new ProtocolLotApplicationListType();
        if (oszListType.getProtocolLotApplications() != null) {
            listType.setProtocolLotApplications(
                oszListType.getProtocolLotApplications().stream()
                    .map(PurchaseNoticeOszMapper::mapLotApplications)
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
                    .map(PurchaseNoticeOszMapper::mapApplication)
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
