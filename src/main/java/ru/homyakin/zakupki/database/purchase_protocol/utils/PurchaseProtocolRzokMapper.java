package ru.homyakin.zakupki.database.purchase_protocol.utils;

import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZOKApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZOKLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZOKLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZOKDataType;

public class PurchaseProtocolRzokMapper {
    public static PurchaseProtocolDataType mapToDataType(PurchaseProtocolRZOKDataType rzokDataType) {
        var dataType = new PurchaseProtocolDataType();
        dataType.setGuid(rzokDataType.getGuid());
        dataType.setCreateDateTime(rzokDataType.getCreateDateTime());
        dataType.setUrlEIS(rzokDataType.getUrlEIS());
        dataType.setUrlVSRZ(rzokDataType.getUrlVSRZ());
        dataType.setUrlKisRmis(rzokDataType.getUrlKisRmis());
        dataType.setPurchaseInfo(rzokDataType.getPurchaseInfo());
        dataType.setRegistrationNumber(rzokDataType.getRegistrationNumber());
        dataType.setPlacer(rzokDataType.getPlacer());
        dataType.setCustomer(rzokDataType.getCustomer());
        dataType.setAdditionalInfo(rzokDataType.getAdditionalInfo());
        dataType.setMissedContest(rzokDataType.isMissedContest());
        dataType.setMissedReason(rzokDataType.getMissedReason());
        dataType.setPurchaseCancellationReason(rzokDataType.getPurchaseCancellationReason());
        dataType.setPublicationDateTime(rzokDataType.getPublicationDateTime());
        dataType.setStatus(rzokDataType.getStatus());
        dataType.setVersion(rzokDataType.getVersion());
        dataType.setModificationDescription(rzokDataType.getModificationDescription());
        dataType.setType(0);
        dataType.setTypeName("Протокол рассмотрения заявок для открытого конкурса");
        dataType.setTargetPhaseCode(null);
        dataType.setProcedureDate(rzokDataType.getProcedureDate());
        dataType.setProcedurePlace(rzokDataType.getProcedurePlace());
        dataType.setProtocolSignDate(rzokDataType.getProtocolSignDate());
        dataType.setIsLotOriented(null);
        dataType.setCommissionNumber(rzokDataType.getCommissionNumber());
        dataType.setCommissionName(rzokDataType.getCommissionName());
        dataType.setCommissionResult(rzokDataType.getCommissionResult());
        dataType.setLotApplicationsList(mapApplicationList(rzokDataType.getLotApplicationsList()));
        return dataType;
    }

    public static ProtocolLotApplicationListType mapApplicationList(ProtocolRZOKLotApplicationListType rzokListType) {
        if (rzokListType == null) return null;
        var listType = new ProtocolLotApplicationListType();
        if (rzokListType.getProtocolLotApplications() != null) {
            listType.setProtocolLotApplications(
                rzokListType.getProtocolLotApplications().stream()
                    .map(PurchaseProtocolRzokMapper::mapLotApplications)
                    .toList()
            );
        }
        return listType;
    }

    private static ProtocolLotApplications mapLotApplications(ProtocolRZOKLotApplications rzokLotApplications) {
        var lotApplications = new ProtocolLotApplications();
        var lot = new ProtocolLotType();
        lot.setGuid(rzokLotApplications.getGuid());
        lot.setOrdinalNumber(rzokLotApplications.getOrdinalNumber());
        lot.setSubject(rzokLotApplications.getSubject());
        lotApplications.setLot(lot);
        if (rzokLotApplications.getApplication() != null) {
            lotApplications.setApplication(
                rzokLotApplications.getApplication().stream()
                    .map(PurchaseProtocolRzokMapper::mapApplication)
                    .toList()
            );
        }
        return lotApplications;
    }

    private static ProtocolApplicationType mapApplication(ProtocolRZOKApplicationType rzokApplicationType) {
        var application = new ProtocolApplicationType();
        application.setApplicationDate(rzokApplicationType.getApplicationDate());
        application.setApplicationNumber(rzokApplicationType.getApplicationNumber());
        application.setNotDishonest(rzokApplicationType.isNotDishonest());
        application.setProvider(rzokApplicationType.isProvider());
        application.setSupplierInfo(rzokApplicationType.getSupplierInfo());
        application.setNonResidentInfo(rzokApplicationType.getNonResidentInfo());
        application.setPrice(null);
        application.setCurrency(null);
        application.setPriceInfo(null);
        application.setCommodityAmount(rzokApplicationType.getCommodityAmount());
        application.setContractExecutionTerm(rzokApplicationType.getContractExecutionTerm());
        application.setConditionProposals(rzokApplicationType.getConditionProposals());
        application.setAccepted(CommonMapper.mapAcceptedTypeToAcceptedType2(rzokApplicationType.getAccepted()));
        application.setRejectionReasonCode(rzokApplicationType.getRejectionReasonCode());
        application.setRejectionReason(rzokApplicationType.getRejectionReason());
        application.setWinnerIndication(null);
        application.setCommissionDecision(null);
        application.setContractSigned(null);
        application.setCommissionDecisionPlace(null);
        application.setCriteria(null);
        application.setAdditionalPrice(null);
        application.setAssessmentResult(null);
        application.setRating(null);
        return application;
    }
}
