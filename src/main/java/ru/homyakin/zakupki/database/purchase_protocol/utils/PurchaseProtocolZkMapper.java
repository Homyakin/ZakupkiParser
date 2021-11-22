package ru.homyakin.zakupki.database.purchase_protocol.utils;

import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZ2AEApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZ2AELotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZ2AELotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolZKApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolZKLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolZKLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZ2AE94FZDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolZKDataType;

public class PurchaseProtocolZkMapper {
    public static PurchaseProtocolDataType mapToDataType(PurchaseProtocolZKDataType zkDataType) {
        var dataType = new PurchaseProtocolDataType();
        dataType.setGuid(zkDataType.getGuid());
        dataType.setCreateDateTime(zkDataType.getCreateDateTime());
        dataType.setUrlEIS(zkDataType.getUrlEIS());
        dataType.setUrlVSRZ(zkDataType.getUrlVSRZ());
        dataType.setUrlKisRmis(zkDataType.getUrlKisRmis());
        dataType.setPurchaseInfo(zkDataType.getPurchaseInfo());
        dataType.setRegistrationNumber(zkDataType.getRegistrationNumber());
        dataType.setPlacer(zkDataType.getPlacer());
        dataType.setCustomer(zkDataType.getCustomer());
        dataType.setAdditionalInfo(zkDataType.getAdditionalInfo());
        dataType.setMissedContest(zkDataType.isMissedContest());
        dataType.setMissedReason(zkDataType.getMissedReason());
        dataType.setPurchaseCancellationReason(zkDataType.getPurchaseCancellationReason());
        dataType.setPublicationDateTime(zkDataType.getPublicationDateTime());
        dataType.setStatus(zkDataType.getStatus());
        dataType.setVersion(zkDataType.getVersion());
        dataType.setModificationDescription(zkDataType.getModificationDescription());
        dataType.setType(0);
        dataType.setTypeName("Протокол запроса котировок");
        dataType.setTargetPhaseCode(null);
        dataType.setProcedureDate(zkDataType.getProcedureDate());
        dataType.setProcedurePlace(zkDataType.getProcedurePlace());
        dataType.setProtocolSignDate(zkDataType.getProtocolSignDate());
        dataType.setIsLotOriented(null);
        dataType.setCommissionNumber(zkDataType.getCommissionNumber());
        dataType.setCommissionName(zkDataType.getCommissionName());
        dataType.setCommissionResult(zkDataType.getCommissionResult());
        dataType.setLotApplicationsList(mapApplicationList(zkDataType.getLotApplicationsList()));
        return dataType;
    }

    private static ProtocolLotApplicationListType mapApplicationList(ProtocolZKLotApplicationListType zkListType) {
        if (zkListType == null) return null;
        var listType = new ProtocolLotApplicationListType();
        if (zkListType.getProtocolLotApplications() != null) {
            listType.setProtocolLotApplications(
                zkListType.getProtocolLotApplications().stream()
                    .map(PurchaseProtocolZkMapper::mapLotApplications)
                    .toList()
            );
        }
        return listType;
    }

    private static ProtocolLotApplications mapLotApplications(ProtocolZKLotApplications zkLotApplications) {
        var lotApplications = new ProtocolLotApplications();
        var lot = new ProtocolLotType();
        lot.setGuid(zkLotApplications.getGuid());
        lot.setOrdinalNumber(zkLotApplications.getOrdinalNumber());
        lot.setSubject(zkLotApplications.getSubject());
        lot.setInitialSum(zkLotApplications.getInitialSum());
        lot.setCurrency(zkLotApplications.getCurrency());
        lot.setPriceFormula(zkLotApplications.getPriceFormula());
        lot.setCommodityPrice(zkLotApplications.getCommodityPrice());
        lot.setPriceFormula(zkLotApplications.getPriceFormula());
        lot.setInitialSumInfo(zkLotApplications.getInitialSumInfo());
        lot.setMaxContractPrice(zkLotApplications.getMaxContractPrice());
        lotApplications.setLot(lot);
        if (zkLotApplications.getApplication() != null) {
            lotApplications.setApplication(
                zkLotApplications.getApplication().stream()
                    .map(PurchaseProtocolZkMapper::mapApplication)
                    .toList()
            );
        }
        return lotApplications;
    }

    private static ProtocolApplicationType mapApplication(ProtocolZKApplicationType zkApplicationType) {
        var application = new ProtocolApplicationType();
        application.setApplicationDate(zkApplicationType.getApplicationDate());
        application.setApplicationNumber(zkApplicationType.getApplicationNumber());
        application.setNotDishonest(zkApplicationType.isNotDishonest());
        application.setProvider(zkApplicationType.isProvider());
        application.setSupplierInfo(zkApplicationType.getSupplierInfo());
        application.setNonResidentInfo(zkApplicationType.getNonResidentInfo());
        application.setPrice(zkApplicationType.getPrice());
        application.setCurrency(zkApplicationType.getCurrency());
        application.setPriceInfo(zkApplicationType.getPriceInfo());
        application.setCommodityAmount(zkApplicationType.getCommodityAmount());
        application.setContractExecutionTerm(zkApplicationType.getContractExecutionTerm());
        application.setConditionProposals(zkApplicationType.getConditionProposals());
        application.setAccepted(CommonMapper.mapAcceptedTypeToAcceptedType2(zkApplicationType.getAccepted()));
        application.setRejectionReasonCode(zkApplicationType.getRejectionReasonCode());
        application.setRejectionReason(zkApplicationType.getRejectionReason());
        application.setWinnerIndication(CommonMapper.mapWinnerIndication3ToWinnerIndication(zkApplicationType.getWinnerIndication()));
        application.setCommissionDecision(null);
        application.setContractSigned(zkApplicationType.isContractSigned());
        application.setCommissionDecisionPlace(zkApplicationType.getCommissionDecisionPlace());
        application.setCriteria(zkApplicationType.getCriteria());
        application.setAdditionalPrice(null);
        application.setAssessmentResult(zkApplicationType.getAssessmentResult());
        application.setRating(null);
        return application;
    }
}
