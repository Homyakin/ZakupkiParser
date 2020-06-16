package ru.homyakin.zakupki.service.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.homyakin.zakupki.database.ContractRepository;
import ru.homyakin.zakupki.database.PurchaseNoticeRepository;
import ru.homyakin.zakupki.database.PurchasePlanRepository;
import ru.homyakin.zakupki.models.FileType;
import ru.homyakin.zakupki.models.ParseFile;
import ru.homyakin.zakupki.models._223fz.contract.Contract;
import ru.homyakin.zakupki.models._223fz.purchaseplan.PurchasePlan;
import ru.homyakin.zakupki.service.parser.ContractParser;
import ru.homyakin.zakupki.service.parser.PurchaseNoticeParser;
import ru.homyakin.zakupki.service.parser.PurchasePlanParser;
import ru.homyakin.zakupki.service.storage.Queue;

@Service
public class ParseFileProcessing {
    private final static Logger logger = LoggerFactory.getLogger(ParseFileProcessing.class);

    private final Queue<ParseFile> queue;
    private final PurchasePlanRepository purchasePlanRepository;
    private final ContractRepository contractRepository;
    private final PurchaseNoticeRepository purchaseNoticeRepository;

    public ParseFileProcessing(
        Queue<ParseFile> queue,
        PurchasePlanRepository purchasePlanRepository,
        ContractRepository contractRepository,
        PurchaseNoticeRepository purchaseNoticeRepository
    ) {
        this.queue = queue;
        this.purchasePlanRepository = purchasePlanRepository;
        this.contractRepository = contractRepository;
        this.purchaseNoticeRepository = purchaseNoticeRepository;
    }

    @Scheduled(fixedDelay = 1000)
    public void processParseFiles() {
        while (!queue.isEmpty()) {
            try {
                ParseFile file = queue.take();
                logger.info("Start processing {}; {}", file.getType().getValue(), file.getFilepath());
                switch (file.getType()) {
                    case CONTRACT -> {
                        var contract = ContractParser.parse(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Contract " + file.getFilepath() + " wasn't parsed"));
                        contractRepository.insert(contract);
                        /*You can't insert contract in parallel, because later contract can update previous ones.*/
                    }
                    case PURCHASE_PLAN -> {
                        var purchasePlan = PurchasePlanParser.parse(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase plan " + file.getFilepath() + " wasn't parsed"));
                        purchasePlanRepository.insert(purchasePlan);
                        /*You can't insert plan data in parallel, because later plans can update previous ones.*/
                    }
                    case PURCHASE_NOTICE, PURCHASE_NOTICE_IS -> {
                        var purchaseNotice = PurchaseNoticeParser.parse(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase notice " + file.getFilepath() + " wasn't parsed"));
                        purchaseNoticeRepository.insert(purchaseNotice.getBody().getItem().getPurchaseNoticeData(), FileType.PURCHASE_NOTICE);
                    }
                    case PURCHASE_NOTICE_AE -> {
                        var purchaseNotice = PurchaseNoticeParser.parseAE(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase notice " + file.getFilepath() + " wasn't parsed"));
                        purchaseNoticeRepository.insert(purchaseNotice.getBody().getItem().getPurchaseNoticeAEData(), FileType.PURCHASE_NOTICE_AE);
                    }
                    case PURCHASE_NOTICE_AE94 -> {
                        var purchaseNotice = PurchaseNoticeParser.parseAE94(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase notice " + file.getFilepath() + " wasn't parsed"));
                        purchaseNoticeRepository.insert(purchaseNotice.getBody().getItem().getPurchaseNoticeAE94FZData(), FileType.PURCHASE_NOTICE_AE94);
                    }
                    case PURCHASE_NOTICE_AESMBO -> {
                        var purchaseNotice = PurchaseNoticeParser.parseAESMBO(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase notice " + file.getFilepath() + " wasn't parsed"));
                        purchaseNoticeRepository.insert(purchaseNotice.getBody().getItem().getPurchaseNoticeAESMBOData(), FileType.PURCHASE_NOTICE_AESMBO);
                    }
                    case PURCHASE_NOTICE_EP -> {
                        var purchaseNotice = PurchaseNoticeParser.parseEP(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase notice " + file.getFilepath() + " wasn't parsed"));
                        purchaseNoticeRepository.insert(purchaseNotice.getBody().getItem().getPurchaseNoticeEPData(), FileType.PURCHASE_NOTICE_EP);
                    }
                    case PURCHASE_NOTICE_KESMBO -> {
                        var purchaseNotice = PurchaseNoticeParser.parseKESMBO(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase notice " + file.getFilepath() + " wasn't parsed"));
                        purchaseNoticeRepository.insert(purchaseNotice.getBody().getItem().getPurchaseNoticeKESMBOData(), FileType.PURCHASE_NOTICE_KESMBO);
                    }
                    case PURCHASE_NOTICE_OA -> {
                        var purchaseNotice = PurchaseNoticeParser.parseOA(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase notice " + file.getFilepath() + " wasn't parsed"));
                        purchaseNoticeRepository.insert(purchaseNotice.getBody().getItem().getPurchaseNoticeOAData(), FileType.PURCHASE_NOTICE_OA);
                    }
                    case PURCHASE_NOTICE_OK -> {
                        var purchaseNotice = PurchaseNoticeParser.parseOK(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase notice " + file.getFilepath() + " wasn't parsed"));
                        purchaseNoticeRepository.insert(purchaseNotice.getBody().getItem().getPurchaseNoticeOKData(), FileType.PURCHASE_NOTICE_OK);
                    }
                    case PURCHASE_NOTICE_ZK -> {
                        var purchaseNotice = PurchaseNoticeParser.parseZK(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase notice " + file.getFilepath() + " wasn't parsed"));
                        purchaseNoticeRepository.insert(purchaseNotice.getBody().getItem().getPurchaseNoticeZKData(), FileType.PURCHASE_NOTICE_ZK);
                    }
                    case PURCHASE_NOTICE_ZKESMBO -> {
                        var purchaseNotice = PurchaseNoticeParser.parseZKESMBO(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase notice " + file.getFilepath() + " wasn't parsed"));
                        purchaseNoticeRepository.insert(purchaseNotice.getBody().getItem().getPurchaseNoticeZKESMBOData(), FileType.PURCHASE_NOTICE_ZKESMBO);
                    }
                    case PURCHASE_NOTICE_ZPESMBO -> {
                        var purchaseNotice = PurchaseNoticeParser.parseZPESMBO(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase notice " + file.getFilepath() + " wasn't parsed"));
                        purchaseNoticeRepository.insert(purchaseNotice.getBody().getItem().getPurchaseNoticeZPESMBOData(), FileType.PURCHASE_NOTICE_ZPESMBO);
                    }
                    default -> logger.error("Unknown file type");
                }
            } catch (RuntimeException e) {
                logger.error("Internal processing error", e);
            }
        }
    }
}
