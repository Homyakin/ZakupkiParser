package ru.homyakin.zakupki.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.service.exceptions.FileSystemException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileSystemService {
    private final static Logger logger = LoggerFactory.getLogger(FileSystemService.class);

    public Path makeFile(String filePath) {
        Path localFile = Paths.get(filePath);
        try {
            if (Files.exists(localFile)) {
                Files.delete(localFile);
            }
            Files.createDirectories(localFile.getParent());
            Files.createFile(localFile);
        } catch (IOException e) {
            logger.error("Unable to create file {}", filePath, e);
            throw new RuntimeException(e);
        }
        return localFile;
    }

}
