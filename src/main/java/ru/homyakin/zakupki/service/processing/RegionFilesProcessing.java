package ru.homyakin.zakupki.service.processing;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.homyakin.zakupki.config.AppConfiguration;
import ru.homyakin.zakupki.database.contract.ContractRepository;
import ru.homyakin.zakupki.database.purchase_contract.PurchaseContractRepository;
import ru.homyakin.zakupki.database.purchase_notice.PurchaseNoticeRepository;
import ru.homyakin.zakupki.database.purchase_plan.PurchasePlanRepository;
import ru.homyakin.zakupki.models.FileType;
import ru.homyakin.zakupki.models.ParseFile;
import ru.homyakin.zakupki.service.parser.ContractParser;
import ru.homyakin.zakupki.service.parser.PurchaseContractParser;
import ru.homyakin.zakupki.service.parser.PurchaseNoticeParser;
import ru.homyakin.zakupki.service.parser.PurchasePlanParser;
import ru.homyakin.zakupki.service.storage.ParseFileQueue;
import ru.homyakin.zakupki.service.storage.RegionFilesStorage;
import ru.homyakin.zakupki.utils.CommonUtils;

@Service
public class RegionFilesProcessing {
    private final static Logger logger = LoggerFactory.getLogger(RegionFilesProcessing.class);

    private final PurchasePlanRepository purchasePlanRepository;
    private final ContractRepository contractRepository;
    private final PurchaseNoticeRepository purchaseNoticeRepository;
    private final PurchaseContractRepository purchaseContractRepository;
    private final RegionFilesStorage storage;
    private final List<String> regionFilesInProcess = new CopyOnWriteArrayList<>();
    private final ExecutorService executor;
    private final CommonUtils commonUtils;

    public RegionFilesProcessing(
        PurchasePlanRepository purchasePlanRepository,
        ContractRepository contractRepository,
        PurchaseNoticeRepository purchaseNoticeRepository,
        PurchaseContractRepository purchaseContractRepository,
        RegionFilesStorage storage,
        AppConfiguration appConfiguration,
        CommonUtils commonUtils
    ) {
        this.purchasePlanRepository = purchasePlanRepository;
        this.contractRepository = contractRepository;
        this.purchaseNoticeRepository = purchaseNoticeRepository;
        this.storage = storage;
        this.executor = Executors.newFixedThreadPool(appConfiguration.getMaxThreads());
        this.purchaseContractRepository = purchaseContractRepository;
        this.commonUtils = commonUtils;
    }

    @Scheduled(initialDelay = 10 * 1000, fixedDelay = 60 * 1000)
    public void processFiles() {
        var m = storage.getMap();
        for (var entry : m.entrySet()) {
            if (!regionFilesInProcess.contains(entry.getKey())) {
                executor.submit(() -> this.processParseFiles(entry.getValue(), entry.getKey()));
                regionFilesInProcess.add(entry.getKey());
            }
        }
    }

    public void processParseFiles(ParseFileQueue queue, String regionFile) {
        while (!queue.isEmpty()) {
            try {
                ParseFile file = queue.take();
                logger.info("Start processing {}; {}", file.getType().getValue(), file.getFilepath());
                switch (file.getType()) {
                    case CONTRACT -> ContractParser.parse(file.getFilepath()).ifPresent(
                        it -> contractRepository.insert(it, commonUtils.extractRegionFromFilePath(file.getFilepath()))
                    );
                    case PURCHASE_CONTRACT -> PurchaseContractParser.parse(file.getFilepath()).ifPresent(purchaseContractRepository::insert);
                    case PURCHASE_PLAN -> PurchasePlanParser.parse(file.getFilepath()).ifPresent(purchasePlanRepository::insert);
                    case PURCHASE_NOTICE, PURCHASE_NOTICE_IS -> {
                        var purchaseNotice = PurchaseNoticeParser.parse(file.getFilepath());
                        purchaseNotice.ifPresent(
                            it -> purchaseNoticeRepository.insert(it.getBody().getItem().getPurchaseNoticeData(), FileType.PURCHASE_NOTICE)
                        );
                    }
                    case PURCHASE_NOTICE_AE -> {
                        var purchaseNotice = PurchaseNoticeParser.parseAE(file.getFilepath());
                        purchaseNotice.ifPresent(
                            it -> purchaseNoticeRepository.insert(it.getBody().getItem().getPurchaseNoticeAEData(), FileType.PURCHASE_NOTICE_AE)
                        );
                    }
                    case PURCHASE_NOTICE_AE94 -> {
                        var purchaseNotice = PurchaseNoticeParser.parseAE94(file.getFilepath());
                        purchaseNotice.ifPresent(
                            it -> purchaseNoticeRepository.insert(it.getBody().getItem().getPurchaseNoticeAE94FZData(), FileType.PURCHASE_NOTICE_AE94)
                        );
                    }
                    case PURCHASE_NOTICE_AESMBO -> {
                        var purchaseNotice = PurchaseNoticeParser.parseAESMBO(file.getFilepath());
                        purchaseNotice.ifPresent(
                            it -> purchaseNoticeRepository.insert(it.getBody().getItem().getPurchaseNoticeAESMBOData(), FileType.PURCHASE_NOTICE_AESMBO)
                        );
                    }
                    case PURCHASE_NOTICE_EP -> {
                        var purchaseNotice = PurchaseNoticeParser.parseEP(file.getFilepath());
                        purchaseNotice.ifPresent(
                            it -> purchaseNoticeRepository.insert(it.getBody().getItem().getPurchaseNoticeEPData(), FileType.PURCHASE_NOTICE_EP)
                        );
                    }
                    case PURCHASE_NOTICE_KESMBO -> {
                        var purchaseNotice = PurchaseNoticeParser.parseKESMBO(file.getFilepath());
                        purchaseNotice.ifPresent(
                            it -> purchaseNoticeRepository.insert(it.getBody().getItem().getPurchaseNoticeKESMBOData(), FileType.PURCHASE_NOTICE_KESMBO)
                        );
                    }
                    case PURCHASE_NOTICE_OA -> {
                        var purchaseNotice = PurchaseNoticeParser.parseOA(file.getFilepath());
                        purchaseNotice.ifPresent(
                            it -> purchaseNoticeRepository.insert(it.getBody().getItem().getPurchaseNoticeOAData(), FileType.PURCHASE_NOTICE_OA)
                        );
                    }
                    case PURCHASE_NOTICE_OK -> {
                        var purchaseNotice = PurchaseNoticeParser.parseOK(file.getFilepath());
                        purchaseNotice.ifPresent(
                            it -> purchaseNoticeRepository.insert(it.getBody().getItem().getPurchaseNoticeOKData(), FileType.PURCHASE_NOTICE_OK)
                        );
                    }
                    case PURCHASE_NOTICE_ZK -> {
                        var purchaseNotice = PurchaseNoticeParser.parseZK(file.getFilepath());
                        purchaseNotice.ifPresent(
                            it -> purchaseNoticeRepository.insert(it.getBody().getItem().getPurchaseNoticeZKData(), FileType.PURCHASE_NOTICE_ZK)
                        );
                    }
                    case PURCHASE_NOTICE_ZKESMBO -> {
                        var purchaseNotice = PurchaseNoticeParser.parseZKESMBO(file.getFilepath());
                        purchaseNotice.ifPresent(
                            it -> purchaseNoticeRepository.insert(it.getBody().getItem().getPurchaseNoticeZKESMBOData(), FileType.PURCHASE_NOTICE_ZKESMBO)
                        );
                    }
                    case PURCHASE_NOTICE_ZPESMBO -> {
                        var purchaseNotice = PurchaseNoticeParser.parseZPESMBO(file.getFilepath());
                        purchaseNotice.ifPresent(
                            it -> purchaseNoticeRepository.insert(it.getBody().getItem().getPurchaseNoticeZPESMBOData(), FileType.PURCHASE_NOTICE_ZPESMBO)
                        );
                    }
                    default -> logger.error("Unknown file type {}", file.getType());
                }
            } catch (RuntimeException e) {
                logger.error("Internal processing error", e);
            }
        }
        regionFilesInProcess.remove(regionFile);
    }
}
