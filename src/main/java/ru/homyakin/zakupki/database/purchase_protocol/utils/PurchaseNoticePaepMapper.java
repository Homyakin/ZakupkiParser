package ru.homyakin.zakupki.database.purchase_protocol.utils;

import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAEPApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAEPLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAEPLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolPAEPDataType;

public class PurchaseNoticePaepMapper {
    public static PurchaseProtocolDataType mapToDataType(PurchaseProtocolPAEPDataType paepDataType) {
        var dataType = new PurchaseProtocolDataType();
        dataType.setGuid(paepDataType.getGuid());
        dataType.setCreateDateTime(paepDataType.getCreateDateTime());
        dataType.setUrlEIS(paepDataType.getUrlEIS());
        dataType.setUrlVSRZ(paepDataType.getUrlVSRZ());
        dataType.setUrlKisRmis(paepDataType.getUrlKisRmis());
        dataType.setPurchaseInfo(paepDataType.getPurchaseInfo());
        dataType.setRegistrationNumber(paepDataType.getRegistrationNumber());
        dataType.setPlacer(paepDataType.getPlacer());
        dataType.setCustomer(paepDataType.getCustomer());
        dataType.setAdditionalInfo(paepDataType.getAdditionalInfo());
        dataType.setMissedContest(paepDataType.isMissedContest());
        dataType.setMissedReason(paepDataType.getMissedReason());
        dataType.setPurchaseCancellationReason(paepDataType.getPurchaseCancellationReason());
        dataType.setPublicationDateTime(paepDataType.getPublicationDateTime());
        dataType.setStatus(paepDataType.getStatus());
        dataType.setVersion(paepDataType.getVersion());
        dataType.setModificationDescription(paepDataType.getModificationDescription());
        dataType.setType(0);
        dataType.setTypeName("Протокол проведения закупки у единственного поставщика (подрядчика, исполнителя)");
        dataType.setProtocolSignDate(paepDataType.getProtocolSignDate());
        dataType.setIsLotOriented(null);
        dataType.setCommissionNumber(paepDataType.getCommissionNumber());
        dataType.setCommissionName(paepDataType.getCommissionName());
        dataType.setCommissionResult(paepDataType.getCommissionResult());
        dataType.setLotApplicationsList(mapApplicationList(paepDataType.getLotApplicationsList()));
        return dataType;
    }

    private static ProtocolLotApplicationListType mapApplicationList(ProtocolPAEPLotApplicationListType paepListType) {
        if (paepListType == null) return null;
        paepListType.getProtocolLotApplications();
        var listType = new ProtocolLotApplicationListType();
        if (paepListType.getProtocolLotApplications() != null) {
            listType.setProtocolLotApplications(
                paepListType.getProtocolLotApplications().stream()
                    .map(PurchaseNoticePaepMapper::mapLotApplications)
                    .toList()
            );
        }
        return listType;
    }

    private static ProtocolLotApplications mapLotApplications(ProtocolPAEPLotApplications paepLotApplications) {
        var lotApplications = new ProtocolLotApplications();
        var lot = new ProtocolLotType();
        lot.setGuid(paepLotApplications.getGuid());
        lot.setOrdinalNumber(paepLotApplications.getOrdinalNumber());
        lot.setSubject(paepLotApplications.getSubject());
        lot.setInitialSum(paepLotApplications.getInitialSum());
        lot.setCurrency(paepLotApplications.getCurrency());
        lot.setPriceFormula(paepLotApplications.getPriceFormula());
        lot.setCommodityPrice(paepLotApplications.getCommodityPrice());
        lot.setPriceFormula(paepLotApplications.getPriceFormula());
        lot.setMaxContractPrice(paepLotApplications.getMaxContractPrice());
        lot.setInitialSumInfo(paepLotApplications.getInitialSumInfo());
        lotApplications.setLot(lot);
        if (paepLotApplications.getApplication() != null) {
            lotApplications.setApplication(
                paepLotApplications.getApplication().stream()
                    .map(PurchaseNoticePaepMapper::mapApplication)
                    .toList()
            );
        }
        return lotApplications;
    }

    private static ProtocolApplicationType mapApplication(ProtocolPAEPApplicationType paepApplicationType) {
        var application = new ProtocolApplicationType();
        application.setApplicationDate(paepApplicationType.getApplicationDate());
        application.setApplicationNumber(paepApplicationType.getApplicationNumber());
        application.setNotDishonest(paepApplicationType.isNotDishonest());
        application.setProvider(paepApplicationType.isProvider());
        application.setSupplierInfo(paepApplicationType.getSupplierInfo());
        application.setNonResidentInfo(paepApplicationType.getNonResidentInfo());
        application.setPrice(paepApplicationType.getLastPrice());
        application.setCurrency(paepApplicationType.getCurrency());
        application.setPriceInfo(paepApplicationType.getLastPriceInfo());
        application.setContractSigned(paepApplicationType.isContractSigned());
        application.setCommodityAmount(paepApplicationType.getCommodityAmount());
        application.setContractExecutionTerm(paepApplicationType.getContractExecutionTerm());
        application.setConditionProposals(paepApplicationType.getConditionProposals());
        return application;
    }
}
