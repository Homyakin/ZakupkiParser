package ru.homyakin.zakupki;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.homyakin.zakupki.service.processing.RegionFilesProcessing;
import ru.homyakin.zakupki.web.exceptions.NetworkException;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {
    private final static Logger logger = LoggerFactory.getLogger(Application.class);
    private final ParserEngine parserEngine;
    private final ExecutorService executorService;
    private final RegionFilesProcessing regionFilesProcessing;

    public Application(
        ParserEngine parserEngine,
        RegionFilesProcessing regionFilesProcessing
    ) {
        this.parserEngine = parserEngine;
        this.regionFilesProcessing = regionFilesProcessing;
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void run(String... args) {
        try {
            executorService.submit(regionFilesProcessing::processFiles);
            parserEngine.launch();
        } catch (NetworkException e) {
            logger.error("Network error: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Internal error", e);
        }
    }

    //TODO add reconnecting to server if something went wrong
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
