package ru.homyakin.zakupki;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models.FileType;
import ru.homyakin.zakupki.models.ParseFile;
import ru.homyakin.zakupki.service.ConsoleInputService;
import ru.homyakin.zakupki.service.ZipService;
import ru.homyakin.zakupki.service.storage.RegionFilesStorage;
import ru.homyakin.zakupki.utils.CommonUtils;
import ru.homyakin.zakupki.web.FtpClient223Fz;

@Component
public class ParserEngine {
    private final static Logger logger = LoggerFactory.getLogger(ParserEngine.class);
    private final FtpClient223Fz ftpClient;
    private final ConsoleInputService consoleInputService;
    private final ZipService zipService;
    private final RegionFilesStorage storage;

    public ParserEngine(
        FtpClient223Fz ftpClient,
        ConsoleInputService consoleInputService,
        ZipService zipService,
        RegionFilesStorage storage
    ) {
        this.ftpClient = ftpClient;
        this.consoleInputService = consoleInputService;
        this.zipService = zipService;
        this.storage = storage;
    }

    public void launch() {
        ftpClient.connect();
        ftpClient.login();
        final var selectedRegions = consoleInputService.selectFromList(ftpClient.getAllRegions());
        final var selectedTypes = consoleInputService.selectFromList(getAllFileTypes());
        final var startDate = consoleInputService.selectStartDate().orElseGet(() -> LocalDate.of(2000, 1, 1));
        final var endDate = consoleInputService.selectEndDate().orElseGet(() -> LocalDate.of(4000, 1, 1));

        var currentDate = startDate;
        while (CommonUtils.isDateInInterval(startDate, endDate, currentDate)) {
            final var nextDate = currentDate.plusMonths(1);
            for (final var region : selectedRegions) {
                for (final var type : selectedTypes) {
                    logger.info("Start parsing {} {}", region, type);
                    final var fileType = FileType.fromString(type).orElseThrow(() -> new IllegalStateException("Unknown file type"));
                    for (final var folder : fileType.getFolders()) {
                        final var files = ftpClient.getFilesInRegionFolder(region, folder, currentDate, nextDate);
                        for (final var file : files) {
                            final var localFile = ftpClient.downloadFile(file, region, folder);
                            if (localFile.isPresent()) {
                                final var unzippedFilePaths = zipService.unzipFile(localFile.get());
                                for (final var filePath: unzippedFilePaths) {
                                    final var parseFile = new ParseFile(filePath, folder);
                                    storage.insert(region, parseFile);
                                }
                            }
                        }
                    }

                }
            }
            currentDate = nextDate;
        }
    }

    private List<String> getAllFileTypes() {
        var fileTypes = FileType.values();
        return Arrays
            .stream(fileTypes)
            .map(FileType::getName)
            .collect(Collectors.toList());
    }
}
