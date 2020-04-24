package ru.homyakin.zakupki.service.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.homyakin.zakupki.database.ContractRepository;
import ru.homyakin.zakupki.database.PurchaseNoticeRepository;
import ru.homyakin.zakupki.database.PurchasePlanRepository;
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
                    case PURCHASE_NOTICE -> {
                        var purchaseNotice = PurchaseNoticeParser.parse(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase notice " + file.getFilepath() + " wasn't parsed"));
                        purchaseNoticeRepository.insert(purchaseNotice);
                    }
                    default -> logger.error("Unknown file type");
                }
            } catch (RuntimeException e) {
                logger.error("Internal processing error", e);
            }
        }
    }
}
