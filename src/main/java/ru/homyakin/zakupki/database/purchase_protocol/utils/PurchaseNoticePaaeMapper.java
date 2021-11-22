package ru.homyakin.zakupki.database.purchase_protocol.utils;

import java.util.Optional;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAOAApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAOALotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAOALotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolPAAEDataType;

public class PurchaseNoticePaaeMapper {
    public static PurchaseProtocolDataType mapPaaeToDataType(PurchaseProtocolPAAEDataType paaeDataType) {
        var dataType = new PurchaseProtocolDataType();
        dataType.setGuid(paaeDataType.getGuid());
        dataType.setCreateDateTime(paaeDataType.getCreateDateTime());
        dataType.setUrlEIS(paaeDataType.getUrlEIS());
        dataType.setUrlVSRZ(paaeDataType.getUrlVSRZ());
        dataType.setUrlKisRmis(paaeDataType.getUrlKisRmis());
        dataType.setPurchaseInfo(paaeDataType.getPurchaseInfo());
        dataType.setRegistrationNumber(paaeDataType.getRegistrationNumber());
        dataType.setPlacer(paaeDataType.getPlacer());
        dataType.setCustomer(paaeDataType.getCustomer());
        dataType.setAdditionalInfo(paaeDataType.getAdditionalInfo());
        dataType.setMissedContest(paaeDataType.isMissedContest());
        dataType.setMissedReason(paaeDataType.getMissedReason());
        dataType.setPurchaseCancellationReason(paaeDataType.getPurchaseCancellationReason());
        dataType.setPublicationDateTime(paaeDataType.getPublicationDateTime());
        dataType.setStatus(paaeDataType.getStatus());
        dataType.setVersion(paaeDataType.getVersion());
        dataType.setModificationDescription(paaeDataType.getModificationDescription());
        dataType.setType(0);
        dataType.setTypeName("Протокол проведения аукциона для открытого аукциона в ЭФ");
        dataType.setProcedureDate(paaeDataType.getProcedureDate());
        dataType.setProcedurePlace(paaeDataType.getProcedurePlace());
        dataType.setProtocolSignDate(paaeDataType.getProtocolSignDate());
        dataType.setTemplateVersion(paaeDataType.getProtocolRZVersion());
        dataType.setIsLotOriented(null);
        var commissionInfo = Optional.ofNullable(paaeDataType.getCommissionInfo());
        commissionInfo.ifPresent(it -> dataType.setCommissionNumber(it.getCommissionNumber()));
        commissionInfo.ifPresent(it -> dataType.setCommissionName(it.getCommissionName()));
        commissionInfo.ifPresent(it -> dataType.setCommissionResult(it.getCommissionResult()));
        dataType.setLotApplicationsList(PaoaApplicationsMapper.mapApplicationList(paaeDataType.getLotApplicationsList()));
        return dataType;
    }
}
