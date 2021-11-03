package ru.homyakin.zakupki.service.processing;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.homyakin.zakupki.config.AppConfiguration;
import ru.homyakin.zakupki.database.RepositoryRouter;
import ru.homyakin.zakupki.models.ParseFile;
import ru.homyakin.zakupki.service.parser.MainXmlParser;
import ru.homyakin.zakupki.service.storage.ParseFileQueue;
import ru.homyakin.zakupki.service.storage.RegionFilesStorage;

@Service
public class RegionFilesProcessing {
    private final static Logger logger = LoggerFactory.getLogger(RegionFilesProcessing.class);

    private final RepositoryRouter repositoryRouter;
    private final RegionFilesStorage storage;
    private final List<String> regionFilesInProcess = new CopyOnWriteArrayList<>();
    private final ExecutorService executor;

    public RegionFilesProcessing(
        RepositoryRouter repositoryRouter,
        RegionFilesStorage storage,
        AppConfiguration appConfiguration
    ) {
        this.repositoryRouter = repositoryRouter;
        this.storage = storage;
        this.executor = Executors.newFixedThreadPool(appConfiguration.getMaxThreads());
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
                logger.info("Start processing {}; {}", file.folder().getName(), file.filepath());
                MainXmlParser.parse(file.filepath(), file.folder().getModelClass())
                    .ifPresent(parsedObject -> repositoryRouter.route(parsedObject, file));
            } catch (RuntimeException e) {
                logger.error("Internal processing error", e);
            }
        }
        regionFilesInProcess.remove(regionFile);
    }
}
