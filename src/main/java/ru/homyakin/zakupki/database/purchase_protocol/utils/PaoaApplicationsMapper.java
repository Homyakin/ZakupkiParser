package ru.homyakin.zakupki.database.purchase_protocol.utils;

import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAOAApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAOALotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAOALotApplications;

public class PaoaApplicationsMapper {
    public static ProtocolLotApplicationListType mapApplicationList(ProtocolPAOALotApplicationListType paoaListType) {
        if (paoaListType == null) return null;
        var listType = new ProtocolLotApplicationListType();
        if (paoaListType.getProtocolLotApplications() != null) {
            listType.setProtocolLotApplications(
                paoaListType.getProtocolLotApplications().stream()
                    .map(PaoaApplicationsMapper::mapLotApplications)
                    .toList()
            );
        }
        return listType;
    }

    private static ProtocolLotApplications mapLotApplications(ProtocolPAOALotApplications paoaLotApplications) {
        var lotApplications = new ProtocolLotApplications();
        lotApplications.setLot(paoaLotApplications.getLot());
        lotApplications.setLotParameters(paoaLotApplications.getLotParameters());
        if (paoaLotApplications.getApplication() != null) {
            lotApplications.setApplication(
                paoaLotApplications.getApplication().stream()
                    .map(PaoaApplicationsMapper::mapApplication)
                    .toList()
            );
        }
        return lotApplications;
    }

    private static ProtocolApplicationType mapApplication(ProtocolPAOAApplicationType paoaApplicationType) {
        var application = new ProtocolApplicationType();
        application.setApplicationDate(paoaApplicationType.getApplicationDate());
        application.setApplicationNumber(paoaApplicationType.getApplicationNumber());
        application.setNotDishonest(paoaApplicationType.isNotDishonest());
        application.setProvider(paoaApplicationType.isProvider());
        application.setSupplierInfo(paoaApplicationType.getSupplierInfo());
        application.setNonResidentInfo(paoaApplicationType.getNonResidentInfo());
        application.setPrice(paoaApplicationType.getLastPrice());
        application.setPriceInfo(paoaApplicationType.getLastPriceInfo());
        application.setContractSigned(paoaApplicationType.isContractSigned());
        application.setCommissionDecisionPlace(paoaApplicationType.getCommissionDecisionPlace());
        application.setCriteria(paoaApplicationType.getCriteria());
        application.setAdditionalPrice(paoaApplicationType.getLastButOnePrice());
        application.setAssessmentResult(paoaApplicationType.getAssessmentResult());
        return application;
    }
}
