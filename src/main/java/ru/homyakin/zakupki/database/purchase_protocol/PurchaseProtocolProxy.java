package ru.homyakin.zakupki.database.purchase_protocol;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.purchase_protocol.utils.PurchaseProtocolOszMapper;
import ru.homyakin.zakupki.database.purchase_protocol.utils.PurchaseProtocolPaae94fzMapper;
import ru.homyakin.zakupki.database.purchase_protocol.utils.PurchaseProtocolPaaeMapper;
import ru.homyakin.zakupki.database.purchase_protocol.utils.PurchaseProtocolPaepMapper;
import ru.homyakin.zakupki.database.purchase_protocol.utils.PurchaseProtocolPaoaMapper;
import ru.homyakin.zakupki.database.purchase_protocol.utils.PurchaseProtocolRz1ae94fzMapper;
import ru.homyakin.zakupki.database.purchase_protocol.utils.PurchaseProtocolRz2ae94fzMapper;
import ru.homyakin.zakupki.database.purchase_protocol.utils.PurchaseProtocolRzaeMapper;
import ru.homyakin.zakupki.database.purchase_protocol.utils.PurchaseProtocolRzoaMapper;
import ru.homyakin.zakupki.database.purchase_protocol.utils.PurchaseProtocolRzokMapper;
import ru.homyakin.zakupki.database.purchase_protocol.utils.PurchaseProtocolVkMapper;
import ru.homyakin.zakupki.database.purchase_protocol.utils.PurchaseProtocolZkMapper;
import ru.homyakin.zakupki.models.Folder;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolCancellation;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocol;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolCCAESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolCCKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolCCZKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolCCZPESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolCollationAESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolEvasionAESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolEvasionKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolEvasionZKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolEvasionZPESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolFCDKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolFKVOKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolOSZ;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolPAAE;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolPAAE94FZ;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolPAEP;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolPAOA;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZ1AE94FZ;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZ1AESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZ1KESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZ1ZPESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZ2AE94FZ;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZ2AESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZ2KESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZ2ZPESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZAE;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZOA;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZOK;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRZZKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRejectionAESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRejectionKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRejectionZKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolRejectionZPESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolSummingupAESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolSummingupKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolSummingupZKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolSummingupZPESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolVK;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolZK;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolZRPZAESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolZRPZKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolZRPZZKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolZRPZZPESMBO;

@Component
public class PurchaseProtocolProxy {
    private static Long countNotProcessed = 0L;
    private static Long countProcessed = 0L;
    private final static Logger logger = LoggerFactory.getLogger(PurchaseProtocolProxy.class);

    private final PurchaseProtocolRepository purchaseProtocolRepository;

    public PurchaseProtocolProxy(PurchaseProtocolRepository purchaseProtocolRepository) {
        this.purchaseProtocolRepository = purchaseProtocolRepository;
    }
    
    public void insert(Object parsedObject, Folder folder, String region) {
        Optional<PurchaseProtocolDataType> data = Optional.empty();
        if (parsedObject instanceof PurchaseProtocol purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolData());
        } else if (parsedObject instanceof PurchaseProtocolCCAESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolCCAESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolCCKESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolCCKESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolCCZKESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolCCZKESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolCCZPESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolCCZPESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolCollationAESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolCollationAESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolEvasionAESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolEvasionAESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolEvasionKESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolEvasionKESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolEvasionZKESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolEvasionZKESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolEvasionZPESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolEvasionZPESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolFCDKESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolFCDKESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolFKVOKESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolFKVOKESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolOSZ purchaseProtocolOSZ) {
            data = Optional.of(PurchaseProtocolOszMapper.mapToDataType(purchaseProtocolOSZ.getBody().getItem().getPurchaseProtocolOSZData()));
        } else if (parsedObject instanceof PurchaseProtocolPAAE purchaseProtocol) {
            data = Optional.of(PurchaseProtocolPaaeMapper.mapToDataType(purchaseProtocol.getBody().getItem().getPurchaseProtocolPAAEData()));
        } else if (parsedObject instanceof PurchaseProtocolPAAE94FZ purchaseProtocol) {
            data = Optional.of(PurchaseProtocolPaae94fzMapper.mapToDataType(purchaseProtocol.getBody().getItem().getPurchaseProtocolPAAE94FZData()));
        } else if (parsedObject instanceof PurchaseProtocolPAEP purchaseProtocol) {
            data = Optional.of(PurchaseProtocolPaepMapper.mapToDataType(purchaseProtocol.getBody().getItem().getPurchaseProtocolPAEPData()));
        } else if (parsedObject instanceof PurchaseProtocolPAOA purchaseProtocol) {
            data = Optional.of(PurchaseProtocolPaoaMapper.mapToDataType(purchaseProtocol.getBody().getItem().getPurchaseProtocolPAOAData()));
        } else if (parsedObject instanceof PurchaseProtocolRZ1AE94FZ purchaseProtocol) {
            data = Optional.of(PurchaseProtocolRz1ae94fzMapper.mapToDataType(purchaseProtocol.getBody().getItem().getPurchaseProtocolRZ1AE94FZData()));
        } else if (parsedObject instanceof PurchaseProtocolRZ1AESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolRZ1AESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolRZ1KESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolRZ1KESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolRZ1ZPESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolRZ1ZPESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolRZ2AE94FZ purchaseProtocol) {
            data = Optional.of(PurchaseProtocolRz2ae94fzMapper.mapToDataType(purchaseProtocol.getBody().getItem().getPurchaseProtocolRZ2AE94FZData()));
        } else if (parsedObject instanceof PurchaseProtocolRZ2AESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolRZ2AESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolRZ2KESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolRZ2KESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolRZ2ZPESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolRZ2ZPESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolRZAE purchaseProtocol) {
            data = Optional.of(PurchaseProtocolRzaeMapper.mapToDataType(purchaseProtocol.getBody().getItem().getPurchaseProtocolRZAEData()));
        } else if (parsedObject instanceof PurchaseProtocolRZOA purchaseProtocol) {
            data = Optional.of(PurchaseProtocolRzoaMapper.mapToDataType(purchaseProtocol.getBody().getItem().getPurchaseProtocolRZOAData()));
        } else if (parsedObject instanceof PurchaseProtocolRZOK purchaseProtocol) {
            data = Optional.of(PurchaseProtocolRzokMapper.mapToDataType(purchaseProtocol.getBody().getItem().getPurchaseProtocolRZOKData()));
        } else if (parsedObject instanceof PurchaseProtocolRZZKESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolRZZKESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolRejectionAESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolRejectionAESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolRejectionKESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolRejectionKESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolRejectionZKESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolRejectionZKESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolRejectionZPESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolRejectionZPESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolSummingupAESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolSummingupAESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolSummingupKESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolSummingupKESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolSummingupZKESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolSummingupZKESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolSummingupZPESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolSummingupZPESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolVK purchaseProtocol) {
            data = Optional.of(PurchaseProtocolVkMapper.mapToDataType(purchaseProtocol.getBody().getItem().getPurchaseProtocolVKData()));
        } else if (parsedObject instanceof PurchaseProtocolZRPZAESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolZRPZAESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolZRPZKESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolZRPZKESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolZRPZZKESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolZRPZZKESMBOData());
        } else if (parsedObject instanceof PurchaseProtocolZRPZZPESMBO purchaseProtocol) {
            data = Optional.of(purchaseProtocol.getBody().getItem().getPurchaseProtocolZRPZZPESMBOData());
        } else if (parsedObject instanceof ProtocolCancellation purchaseProtocol) {
            logger.warn("ProtocolCancellation");
            countNotProcessed++;
        } else if (parsedObject instanceof PurchaseProtocolZK purchaseProtocol) {
            data = Optional.of(PurchaseProtocolZkMapper.mapToDataType(purchaseProtocol.getBody().getItem().getPurchaseProtocolZKData()));
        } else {
            logger.error("Unknown class {}", parsedObject.getClass().getSimpleName());
        }
        data.ifPresent(it -> purchaseProtocolRepository.insert(it, folder, region));
        countProcessed++;
    }
}
