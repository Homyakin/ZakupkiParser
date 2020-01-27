package ru.homyakin.zakupki.service.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.homyakin.zakupki.database.PurchasePlanRepository;
import ru.homyakin.zakupki.models.ContractInfo;
import ru.homyakin.zakupki.models.ParseFile;
import ru.homyakin.zakupki.models._223fz.purchaseplan.PurchasePlan;
import ru.homyakin.zakupki.service.parser.ContractParser;
import ru.homyakin.zakupki.service.parser.PurchasePlanParser;
import ru.homyakin.zakupki.service.storage.Queue;

@Service
public class ParseFileProcessing {
    private final static Logger logger = LoggerFactory.getLogger(ParseFileProcessing.class);

    private final Queue<ParseFile> queue;
    private final PurchasePlanRepository purchasePlanRepository;
    private final DatabasePoolTaskExecutor executor;

    public ParseFileProcessing(
        Queue<ParseFile> queue,
        PurchasePlanRepository purchasePlanRepository,
        DatabasePoolTaskExecutor executor
    ) {
        this.queue = queue;
        this.purchasePlanRepository = purchasePlanRepository;
        this.executor = executor;
    }

    @Scheduled(fixedRate = 1000)
    public void processParseFiles() {
        while (!queue.isEmpty()) {
            try {
                ParseFile file = queue.take();
                logger.info("Start processing {}; {}", file.getType().getValue(), file.getFilepath());
                switch (file.getType()) {
                    case CONTRACT:
                        ContractInfo contract = new ContractInfo(ContractParser.parse(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Contract " + file.getFilepath() + " wasn't parsed")));
                        break;
                    case PURCHASE_PLAN:
                        PurchasePlan purchasePlan = PurchasePlanParser.parse(file.getFilepath())
                            .orElseThrow(() -> new IllegalArgumentException("Contract " + file.getFilepath() + " wasn't parsed"));
                        purchasePlanRepository.insert(purchasePlan);
                        /*You can't insert plan data in parallel, because later plans can update previous ones.*/
                        break;
                }
            } catch (RuntimeException e) {
                logger.error("Internal processing error");
            }
        }
    }
}
