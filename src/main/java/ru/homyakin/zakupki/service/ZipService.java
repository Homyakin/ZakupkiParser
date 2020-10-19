package ru.homyakin.zakupki.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models.FileType;
import ru.homyakin.zakupki.models.ParseFile;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import ru.homyakin.zakupki.service.storage.RegionFilesStorage;

@Component
public class ZipService {
    private final static Logger logger = LoggerFactory.getLogger(ZipService.class);

    private final FileSystemService fileSystemService;
    private final RegionFilesStorage storage;

    public ZipService(
        FileSystemService fileSystemService,
        RegionFilesStorage storage
    ) {
        this.fileSystemService = fileSystemService;
        this.storage = storage;
    }

    public void unzipFile(String filePath, String path, String folder, String region) {
        logger.debug("Start unzipping {}", filePath);
        try (var zin = new ZipInputStream(Files.newInputStream(Paths.get(filePath)))) {
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
                var file = new ParseFile(
                    path + "/unzip/" + name,
                    FileType.fromString(folder).orElseThrow(() -> new IllegalArgumentException("Illegal folder name"))
                );
                storage.insert(region, file);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Argument error ", e);
        } catch (RuntimeException e) {
            logger.error("Internal error", e);
        } catch (IOException e) {
            logger.error("Error in unzipping process of file {}", filePath, e);
        }
    }
}
