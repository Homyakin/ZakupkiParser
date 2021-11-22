package ru.homyakin.zakupki.database.purchase_protocol.utils;

import java.util.Optional;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAOAApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAOALotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolPAOALotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolPAOADataType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

public class PurchaseNoticePaoaMapper {
    public static PurchaseProtocolDataType mapPaaeToDataType(PurchaseProtocolPAOADataType paoaDataType) {
        var dataType = new PurchaseProtocolDataType();
        dataType.setGuid(paoaDataType.getGuid());
        dataType.setCreateDateTime(paoaDataType.getCreateDateTime());
        dataType.setUrlEIS(paoaDataType.getUrlEIS());
        dataType.setUrlVSRZ(paoaDataType.getUrlVSRZ());
        dataType.setUrlKisRmis(paoaDataType.getUrlKisRmis());
        dataType.setPurchaseInfo(paoaDataType.getPurchaseInfo());
        dataType.setRegistrationNumber(paoaDataType.getRegistrationNumber());
        dataType.setPlacer(paoaDataType.getPlacer());
        dataType.setCustomer(paoaDataType.getCustomer());
        dataType.setAdditionalInfo(paoaDataType.getAdditionalInfo());
        dataType.setMissedContest(paoaDataType.isMissedContest());
        dataType.setMissedReason(paoaDataType.getMissedReason());
        dataType.setPurchaseCancellationReason(paoaDataType.getPurchaseCancellationReason());
        dataType.setPublicationDateTime(paoaDataType.getPublicationDateTime());
        dataType.setStatus(paoaDataType.getStatus());
        dataType.setVersion(paoaDataType.getVersion());
        dataType.setModificationDescription(paoaDataType.getModificationDescription());
        dataType.setType(0);
        dataType.setTypeName("Протокол проведения аукциона для открытого аукциона");
        dataType.setProcedureDate(paoaDataType.getProcedureDate());
        dataType.setProcedurePlace(paoaDataType.getProcedurePlace());
        dataType.setProtocolSignDate(paoaDataType.getProtocolSignDate());
        dataType.setTemplateVersion(paoaDataType.getProtocolRZVersion());
        dataType.setIsLotOriented(null);
        dataType.setCommissionNumber(paoaDataType.getCommissionNumber());
        dataType.setCommissionName(paoaDataType.getCommissionName());
        dataType.setCommissionResult(paoaDataType.getCommissionResult());
        dataType.setLotApplicationsList(PaoaApplicationsMapper.mapApplicationList(paoaDataType.getLotApplicationsList()));
        return dataType;
    }
}
