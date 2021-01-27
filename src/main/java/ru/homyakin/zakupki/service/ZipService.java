package ru.homyakin.zakupki.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
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

    public ZipService(
        FileSystemService fileSystemService
    ) {
        this.fileSystemService = fileSystemService;
    }

    public List<String> unzipFile(Path unzippingFile) {
        logger.debug("Start unzipping {}", unzippingFile.toString());
        var unzippedFiles = new ArrayList<String>();
        try (var zin = new ZipInputStream(Files.newInputStream(unzippingFile))) {
            ZipEntry entry;
            var unzippingFileName = unzippingFile
                .getFileName()
                .toString()
                .replaceAll("\\.xml\\.zip", ""); //убираем расширение
            while ((entry = zin.getNextEntry()) != null) {
                var fileName = entry.getName();
                var filePath = String.format("%s/unzip/%s/%s", unzippingFile.getParent().toString(), unzippingFileName, fileName);
                Path localFile = fileSystemService.makeFile(filePath);

                OutputStream outputFile = Files.newOutputStream(localFile);
                byte[] buffer = new byte[4096];
                for (int len = zin.read(buffer); len != -1; len = zin.read(buffer)) {
                    outputFile.write(buffer, 0, len);
                }
                outputFile.flush();
                zin.closeEntry();
                outputFile.close();

                unzippedFiles.add(filePath);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Argument error ", e);
        } catch (RuntimeException e) {
            logger.error("Internal error", e);
        } catch (IOException e) {
            logger.error("Error in unzipping process of file {}", unzippingFile.toString(), e);
        }
        return unzippedFiles;
    }
}
