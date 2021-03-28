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
        var selectedFolders = consoleInputService.selectFromList(getAllFolders());
        var startDate = consoleInputService.selectStartDate();
        if (startDate.isPresent()) {
            ftpClient.setStartDate(startDate.get());
            ftpClient.setEndDate(consoleInputService.selectEndDate().get()); //всегда не null здесь
        }

        for (var region : selectedRegions) {
            for (var folder : selectedFolders) {
                logger.info("Start parsing {} {}", region, folder);
                var fileType = FileType.fromString(folder).orElseThrow(() -> new IllegalStateException("Unknown file type"));
                var files = ftpClient.getFilesInRegionFolder(region, fileType);
                for (var file : files) {
                    var localFile = ftpClient.downloadFile(file, region, fileType);
                    if (localFile.isPresent()) {
                        var unzippedFilePaths = zipService.unzipFile(localFile.get());
                        for (var filePath: unzippedFilePaths) {
                            var parseFile = new ParseFile(filePath, fileType);
                            storage.insert(region, parseFile);
                        }
                    }
                }
            }
        }
    }

    private List<String> getAllFolders() {
        var folders = ftpClient.getAllParsingFolders();
        return Arrays
            .stream(folders)
            .map(FileType::getValue)
            .collect(Collectors.toList());
    }
}
