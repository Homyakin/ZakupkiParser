package ru.homyakin.zakupki.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo.ContractInfo;
import ru.homyakin.zakupki.service.parser.ContractParser;
import ru.homyakin.zakupki.service.parser.interfaces.DocumentParser;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class ZipService {
    private final static Logger logger = LoggerFactory.getLogger(ZipService.class);

    private final FileSystemService fileSystemService;
    private Map<String, DocumentParser> parsers = new HashMap<>();

    public ZipService(FileSystemService fileSystemService, ContractParser contractParser) {
        this.fileSystemService = fileSystemService;
        parsers.put("contract", contractParser);
    }

    public void unzipFile(String filePath, String path, String folder) {
        logger.info("Start unzipping {}", filePath);
        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(Paths.get(filePath)))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                Path localFile = fileSystemService.makeFile(path + "/unzip/" + name);
                OutputStream outputFile = Files.newOutputStream(localFile);
                byte[] buffer = new byte[4096];
                for (int len = zin.read(buffer); len != -1; len = zin.read(buffer)) {
                    outputFile.write(buffer, 0, len);
                }

                outputFile.flush();
                zin.closeEntry();
                outputFile.close();
                if ("contract".equals(folder)) {
                    ContractInfo contract = (ContractInfo) parsers.get("contract").parse(path + "/unzip/" + name);
                }
            }
        } catch (IOException e) {
            logger.error("Error in unzipping process of file {}", filePath, e);
        }
    }
}
