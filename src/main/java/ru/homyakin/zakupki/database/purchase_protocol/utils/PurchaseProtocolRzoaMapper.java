package ru.homyakin.zakupki.database.purchase_protocol.utils;

import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZOAApplicationType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZOALotApplicationListType;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolRZOALotApplications;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZAEDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZOADataType;

public class PurchaseProtocolRzoaMapper {
    public static PurchaseProtocolDataType mapToDataType(PurchaseProtocolRZOADataType rzaeDataType) {
        var dataType = new PurchaseProtocolDataType();
        dataType.setGuid(rzaeDataType.getGuid());
        dataType.setCreateDateTime(rzaeDataType.getCreateDateTime());
        dataType.setUrlEIS(rzaeDataType.getUrlEIS());
        dataType.setUrlVSRZ(rzaeDataType.getUrlVSRZ());
        dataType.setUrlKisRmis(rzaeDataType.getUrlKisRmis());
        dataType.setPurchaseInfo(rzaeDataType.getPurchaseInfo());
        dataType.setRegistrationNumber(rzaeDataType.getRegistrationNumber());
        dataType.setPlacer(rzaeDataType.getPlacer());
        dataType.setCustomer(rzaeDataType.getCustomer());
        dataType.setAdditionalInfo(rzaeDataType.getAdditionalInfo());
        dataType.setMissedContest(rzaeDataType.isMissedContest());
        dataType.setMissedReason(rzaeDataType.getMissedReason());
        dataType.setPurchaseCancellationReason(rzaeDataType.getPurchaseCancellationReason());
        dataType.setPublicationDateTime(rzaeDataType.getPublicationDateTime());
        dataType.setStatus(rzaeDataType.getStatus());
        dataType.setVersion(rzaeDataType.getVersion());
        dataType.setModificationDescription(rzaeDataType.getModificationDescription());
        dataType.setType(0);
        dataType.setTypeName("Протокол рассмотрения заявок для открытого аукциона");
        dataType.setTargetPhaseCode(null);
        dataType.setProcedureDate(rzaeDataType.getProcedureDate());
        dataType.setProcedurePlace(rzaeDataType.getProcedurePlace());
        dataType.setProtocolSignDate(rzaeDataType.getProtocolSignDate());
        dataType.setIsLotOriented(null);
        dataType.setCommissionNumber(rzaeDataType.getCommissionNumber());
        dataType.setCommissionName(rzaeDataType.getCommissionName());
        dataType.setCommissionResult(rzaeDataType.getCommissionResult());
        dataType.setLotApplicationsList(RzoaApplicationsMapper.mapApplicationList(rzaeDataType.getLotApplicationsList()));
        return dataType;
    }
}
