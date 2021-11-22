package ru.homyakin.zakupki.database.purchase_protocol.utils;

import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZOAApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZOALotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZOALotApplications;

public class RzoaApplicationsMapper {
    public static ProtocolLotApplicationListType mapApplicationList(ProtocolRZOALotApplicationListType rzoaListType) {
        if (rzoaListType == null) return null;
        var listType = new ProtocolLotApplicationListType();
        if (rzoaListType.getProtocolLotApplications() != null) {
            listType.setProtocolLotApplications(
                rzoaListType.getProtocolLotApplications().stream()
                    .map(RzoaApplicationsMapper::mapLotApplications)
                    .toList()
            );
        }
        return listType;
    }

    private static ProtocolLotApplications mapLotApplications(ProtocolRZOALotApplications rzoaLotApplications) {
        var lotApplications = new ProtocolLotApplications();
        var lot = new ProtocolLotType();
        lot.setGuid(rzoaLotApplications.getGuid());
        lot.setOrdinalNumber(rzoaLotApplications.getOrdinalNumber());
        lot.setSubject(rzoaLotApplications.getSubject());
        lot.setInitialSum(rzoaLotApplications.getInitialSum());
        lot.setCurrency(rzoaLotApplications.getCurrency());
        lot.setPriceFormula(rzoaLotApplications.getPriceFormula());
        lot.setCommodityPrice(rzoaLotApplications.getCommodityPrice());
        lot.setPriceFormula(rzoaLotApplications.getPriceFormula());
        lot.setInitialSumInfo(rzoaLotApplications.getInitialSumInfo());
        lot.setMaxContractPrice(rzoaLotApplications.getMaxContractPrice());
        lotApplications.setLot(lot);
        if (rzoaLotApplications.getApplication() != null) {
            lotApplications.setApplication(
                rzoaLotApplications.getApplication().stream()
                    .map(RzoaApplicationsMapper::mapApplication)
                    .toList()
            );
        }
        return lotApplications;
    }

    private static ProtocolApplicationType mapApplication(ProtocolRZOAApplicationType rzoaApplicationType) {
        var application = new ProtocolApplicationType();
        application.setApplicationDate(rzoaApplicationType.getApplicationDate());
        application.setApplicationNumber(rzoaApplicationType.getApplicationNumber());
        application.setNotDishonest(rzoaApplicationType.isNotDishonest());
        application.setProvider(rzoaApplicationType.isProvider());
        application.setSupplierInfo(rzoaApplicationType.getSupplierInfo());
        application.setNonResidentInfo(rzoaApplicationType.getNonResidentInfo());
        application.setPrice(null);
        application.setCurrency(null);
        application.setPriceInfo(null);
        application.setCommodityAmount(rzoaApplicationType.getCommodityAmount());
        application.setContractExecutionTerm(rzoaApplicationType.getContractExecutionTerm());
        application.setConditionProposals(rzoaApplicationType.getConditionProposals());
        application.setAccepted(CommonMapper.mapAcceptedTypeToAcceptedType2(rzoaApplicationType.getAccepted()));
        application.setRejectionReasonCode(rzoaApplicationType.getRejectionReasonCode());
        application.setRejectionReason(rzoaApplicationType.getRejectionReason());
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
