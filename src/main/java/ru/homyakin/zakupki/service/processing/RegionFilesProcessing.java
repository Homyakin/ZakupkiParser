package ru.homyakin.zakupki.service.processing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final ExecutorService executor;
    private final int threads;

    public RegionFilesProcessing(
        RepositoryRouter repositoryRouter,
        RegionFilesStorage storage,
        AppConfiguration appConfiguration
    ) {
        this.repositoryRouter = repositoryRouter;
        this.storage = storage;
        this.executor = Executors.newFixedThreadPool(appConfiguration.getMaxThreads());
        threads = appConfiguration.getMaxThreads();
    }

    public void processFiles() {
        for (int i = 0; i < threads; ++i) {
            try {
                var queue = storage.getQueue();
                executor.submit(() -> this.processParseFiles(queue));
            } catch (Exception e) {
                logger.error("Error during main processing", e);
            }
        }
    }

    public void processParseFiles(ParseFileQueue queue) {
        while (true) {
            try {
                ParseFile file = queue.take();
                logger.info("Start processing {}; {}", file.folder().getName(), file.filepath());
                MainXmlParser.parse(file.filepath(), file.folder().getModelClass())
                    .ifPresent(parsedObject -> repositoryRouter.route(parsedObject, file));
            } catch (Exception e) {
                logger.error("Internal processing error", e);
            }
        }
    }
}
