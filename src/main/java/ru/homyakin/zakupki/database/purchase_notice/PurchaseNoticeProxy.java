package ru.homyakin.zakupki.database.purchase_notice;

import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models.Folder;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNotice;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeAE;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeAE94FZ;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeAESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeEP;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeOA;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeOK;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeZK;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeZKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeZPESMBO;

@Component
public class PurchaseNoticeProxy {
    
    private final PurchaseNoticeRepository purchaseNoticeRepository;
    
    public PurchaseNoticeProxy(PurchaseNoticeRepository purchaseNoticeRepository) {
        this.purchaseNoticeRepository = purchaseNoticeRepository;
    }
    
    public void insert(Object parsedObject, Folder folder, String region) {
        if (parsedObject instanceof PurchaseNotice purchaseNotice) {
            purchaseNoticeRepository.insert(purchaseNotice.getBody().getItem().getPurchaseNoticeData(), folder, region);
        } else if (parsedObject instanceof PurchaseNoticeAE purchaseNoticeAE) {
            purchaseNoticeRepository.insert(purchaseNoticeAE.getBody().getItem().getPurchaseNoticeAEData(), folder, region);
        } else if (parsedObject instanceof PurchaseNoticeAE94FZ purchaseNoticeAE94FZ) {
            purchaseNoticeRepository.insert(purchaseNoticeAE94FZ.getBody().getItem().getPurchaseNoticeAE94FZData(), folder, region);
        } else if (parsedObject instanceof PurchaseNoticeAESMBO purchaseNoticeAESMBO) {
            purchaseNoticeRepository.insert(purchaseNoticeAESMBO.getBody().getItem().getPurchaseNoticeAESMBOData(), folder, region);
        } else if (parsedObject instanceof PurchaseNoticeEP purchaseNoticeEP) {
            purchaseNoticeRepository.insert(purchaseNoticeEP.getBody().getItem().getPurchaseNoticeEPData(), folder, region);
        } else if (parsedObject instanceof PurchaseNoticeKESMBO purchaseNoticeKESMBO) {
            purchaseNoticeRepository.insert(purchaseNoticeKESMBO.getBody().getItem().getPurchaseNoticeKESMBOData(), folder, region);
        } else if (parsedObject instanceof PurchaseNoticeOA purchaseNoticeOA) {
            purchaseNoticeRepository.insert(purchaseNoticeOA.getBody().getItem().getPurchaseNoticeOAData(), folder, region);
        } else if (parsedObject instanceof PurchaseNoticeOK purchaseNoticeOK) {
            purchaseNoticeRepository.insert(purchaseNoticeOK.getBody().getItem().getPurchaseNoticeOKData(), folder, region);
        } else if (parsedObject instanceof PurchaseNoticeZK purchaseNoticeZK) {
            purchaseNoticeRepository.insert(purchaseNoticeZK.getBody().getItem().getPurchaseNoticeZKData(), folder, region);
        } else if (parsedObject instanceof PurchaseNoticeZKESMBO purchaseNoticeZKESMBO) {
            purchaseNoticeRepository.insert(purchaseNoticeZKESMBO.getBody().getItem().getPurchaseNoticeZKESMBOData(), folder, region);
        } else if (parsedObject instanceof PurchaseNoticeZPESMBO purchaseNoticeZPESMBO) {
            purchaseNoticeRepository.insert(purchaseNoticeZPESMBO.getBody().getItem().getPurchaseNoticeZPESMBOData(), folder, region);
        }
    }
}
