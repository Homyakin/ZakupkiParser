package ru.homyakin.zakupki.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo.ContractInfo;
import ru.homyakin.zakupki.service.parser.ContractParser;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class ZipService {
    private final static Logger logger = LoggerFactory.getLogger(ZipService.class);

    private final FileSystemService fileSystemService;

    public ZipService(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
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
                switch (folder) {
                    case "contract":
                        ContractInfo contract = new ContractInfo(ContractParser.parse(path + "/unzip/" + name)
                            .orElseThrow(() -> new IllegalArgumentException("Contract " + path + " wasn't parsed")));
                        break;
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("Argument error ", e);
        } catch (IOException e) {
            logger.error("Error in unzipping process of file {}", filePath, e);
        }
    }
}
