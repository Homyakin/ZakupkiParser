package ru.homyakin.zakupki.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.contract.ContractRepository;
import ru.homyakin.zakupki.database.purchase_contract.PurchaseContractRepository;
import ru.homyakin.zakupki.database.purchase_notice.PurchaseNoticeProxy;
import ru.homyakin.zakupki.database.purchase_notice.PurchaseNoticeRepository;
import ru.homyakin.zakupki.database.purchase_plan.PurchasePlanRepository;
import ru.homyakin.zakupki.database.purchase_protocol.PurchaseProtocolProxy;
import ru.homyakin.zakupki.models.FileType;
import ru.homyakin.zakupki.models.Folder;
import ru.homyakin.zakupki.models.ParseFile;
import ru.homyakin.zakupki.models._223fz.contract.Contract;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseContract;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNotice;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeAE;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeAE94FZ;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeAESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeEP;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeKESMBO;
import ru.homyakin.zakupki.models._223fz.purchaseplan.PurchasePlan;
import ru.homyakin.zakupki.utils.CommonUtils;

@Component
public class RepositoryRouter {
    private final static Logger logger = LoggerFactory.getLogger(RepositoryRouter.class);

    private final PurchasePlanRepository purchasePlanRepository;
    private final ContractRepository contractRepository;
    private final PurchaseNoticeProxy purchaseNoticeProxy;
    private final PurchaseContractRepository purchaseContractRepository;
    private final PurchaseProtocolProxy purchaseProtocolProxy;

    public RepositoryRouter(
        PurchasePlanRepository purchasePlanRepository,
        ContractRepository contractRepository,
        PurchaseNoticeProxy purchaseNoticeProxy,
        PurchaseContractRepository purchaseContractRepository,
        PurchaseProtocolProxy purchaseProtocolProxy
    ) {
        this.purchasePlanRepository = purchasePlanRepository;
        this.contractRepository = contractRepository;
        this.purchaseNoticeProxy = purchaseNoticeProxy;
        this.purchaseContractRepository = purchaseContractRepository;
        this.purchaseProtocolProxy = purchaseProtocolProxy;
    }

    public void route(Object parsedObject, ParseFile file) {
        if (parsedObject instanceof Contract contract) {
            contractRepository.insert(contract, CommonUtils.extractRegionFromFilePath(file.getFilepath()));
        } else if (parsedObject instanceof PurchaseContract purchaseContract) {
            purchaseContractRepository.insert(purchaseContract);
        } else if (parsedObject instanceof PurchasePlan purchasePlan) {
            purchasePlanRepository.insert(purchasePlan);
        }

        var fileType = FileType
            .fromFolder(file.getFolder())
            .orElseThrow(() -> new IllegalStateException("Unknown file type"));

        switch (fileType) {
            case PURCHASE_NOTICE -> purchaseNoticeProxy.insert(parsedObject, file.getFolder());
            case PURCHASE_PROTOCOL -> purchaseProtocolProxy.insert(parsedObject, file.getFolder(), CommonUtils.extractRegionFromFilePath(file.getFilepath()));
            default -> logger.error("Unknown file type");
        }
    }
}
