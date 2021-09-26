package ru.homyakin.zakupki;

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

        var selectedRegions = consoleInputService.selectFromList(ftpClient.getAllRegions());
        var selectedTypes = consoleInputService.selectFromList(getAllFileTypes());
        var startDate = consoleInputService.selectStartDate();
        if (startDate.isPresent()) {
            ftpClient.setStartDate(startDate.get());
            ftpClient.setEndDate(consoleInputService.selectEndDate().get()); //всегда не null здесь
        }

        for (var region : selectedRegions) {
            for (var type : selectedTypes) {
                logger.info("Start parsing {} {}", region, type);
                var fileType = FileType.fromString(type).orElseThrow(() -> new IllegalStateException("Unknown file type"));
                for (var folder : fileType.getFolders()) {
                    var files = ftpClient.getFilesInRegionFolder(region, folder);
                    for (var file : files) {
                        var localFile = ftpClient.downloadFile(file, region, folder);
                        if (localFile.isPresent()) {
                            var unzippedFilePaths = zipService.unzipFile(localFile.get());
                            for (var filePath: unzippedFilePaths) {
                                var parseFile = new ParseFile(filePath, folder);
                                storage.insert(region, parseFile);
                            }
                        }
                    }
                }

            }
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
