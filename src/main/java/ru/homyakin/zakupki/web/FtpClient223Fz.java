package ru.homyakin.zakupki.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.config.FtpConfiguration;
import ru.homyakin.zakupki.models.FileType;
import ru.homyakin.zakupki.models.Folder;
import ru.homyakin.zakupki.service.FileSystemService;
import ru.homyakin.zakupki.utils.CommonUtils;
import ru.homyakin.zakupki.web.exceptions.ConnectException;
import ru.homyakin.zakupki.web.exceptions.LoginException;

@Component
public class FtpClient223Fz implements FtpClientFz {
    private final static Logger logger = LoggerFactory.getLogger(FtpClient223Fz.class);
    private final static String basicWorkspace = "/out/published";
    private final static String downloadPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/zakupki_download";
    private final static FTPClient ftp = new FTPClient();
    private final FileSystemService fileSystemService;
    private final FtpConfiguration ftpConfiguration;
    private final CommonUtils commonUtils;
    private LocalDate startDate = LocalDate.of(2000, 1, 1);
    private LocalDate endDate = LocalDate.of(4000, 1, 1);

    public FtpClient223Fz(
        FileSystemService fileSystemService,
        FtpConfiguration ftpConfiguration,
        CommonUtils commonUtils
    ) {
        this.fileSystemService = fileSystemService;
        this.ftpConfiguration = ftpConfiguration;
        this.commonUtils = commonUtils;
    }
    
    public List<String> getAllRegions() {
        var list = new ArrayList<String>();
        try {
            var namesDirectories = ftp.listDirectories(basicWorkspace);
            for (var directory : namesDirectories) {
                if (
                    !directory.getName().equals("archive") &&
                        !directory.getName().equals("ast") &&
                        !directory.getName().equals("undefined")
                ) {
                    list.add(directory.getName());
                }
            }
        } catch (IOException e) {
            logger.error("Something went wrong wile listing {}", basicWorkspace, e);
        }
        return list;
    }

    @Override
    public void connect() {
        try {
            ftp.connect(ftpConfiguration.getUrl());
        } catch (IOException e) {
            logger.error("Can't connect to the server", e);
            logger.info("Server reply:{}", Arrays.toString(ftp.getReplyStrings()));
            throw new ConnectException("Unable to connect to server: " + ftpConfiguration.getUrl());
        }
        logger.info("Server reply:{}", Arrays.toString(ftp.getReplyStrings()));
    }

    @Override
    public void login() {
        try {
            if (ftp.login(ftpConfiguration.getLogin(), ftpConfiguration.getPassword())) {
                ftp.enterLocalPassiveMode();
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                logger.info("Successful login to the ftp server");
            } else {
                logger.error("Unable to login to the ftp server");
                throw new LoginException("Unable to log in " + ftpConfiguration.getUrl() +
                    " with login: " + ftpConfiguration.getLogin() + " and password: " + ftpConfiguration.getPassword());
            }
        } catch (IOException e) {
            logger.error("Server error", e);
            throw new ConnectException("Unable to connect to server: " + ftpConfiguration.getUrl());
        }
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<FTPFile> getFilesInRegionFolder(String region, Folder folder) {
        var workspace = String.format("%s/%s/%s/daily", basicWorkspace, region, folder.getName());
        var fileList = new ArrayList<FTPFile>();
        try {
            var files = ftp.listFiles(workspace);
            for (var remoteFile : files) {
                LocalDate date = commonUtils.convertCalendarToLocalDate(remoteFile.getTimestamp());
                if (remoteFile.isFile() && isDateInInterval(date)) {
                    fileList.add(remoteFile);
                }
            }
        } catch (IOException e) {
            logger.error("Something went wrong wile listing {}", workspace, e);
        }
        return fileList;
    }

    public Optional<Path> downloadFile(FTPFile ftpFile, String region, Folder folder) {
        var localPath = String.format("%s/%s/%s/%s", downloadPath, region, folder.getName(), ftpFile.getName());
        var remotePath = String.format("%s/%s/%s/daily/%s", basicWorkspace, region, folder.getName(), ftpFile.getName());
        Path localFile = fileSystemService.makeFile(localPath);
        boolean isDownload = false;
        try(var stream = Files.newOutputStream(localFile)) {
            isDownload = ftp.retrieveFile(remotePath, stream);
        } catch (IOException e) {
            logger.error("Something went wrong wile downloading {}", remotePath, e);
        }
        if (!isDownload) {
            logger.error("Something went wrong wile downloading {}", remotePath);
            return Optional.empty();
        }
        return Optional.of(localFile);
    }

    private boolean isDateInInterval(LocalDate date) {
        return date.isAfter(startDate) && date.isBefore(endDate) || date.equals(startDate) || date.equals(endDate);
    }
}
