package ru.homyakin.zakupki.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.service.exceptions.FileSystemException;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileSystemService {
    private final static Logger logger = LoggerFactory.getLogger(FileSystemService.class);

    public void makeDirectory(String directoryPath) {
        Path dir = Paths.get(directoryPath);
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            logger.error("Unable to create directory {}", dir, e);
            throw new FileSystemException("Unable to create directory");
        }
    }

    public Path makeFile(String filePath) {
        Path localFile = Paths.get(filePath);
        try {
            Files.createFile(localFile);
        } catch (IOException e) {
            if (!(e instanceof FileAlreadyExistsException)) {
                logger.error("Unable to create file {}", filePath, e);
                throw new FileSystemException("Directory for file doesn't exist " + filePath);
            }
        }
        return localFile;
    }

}
