package ru.homyakin.zakupki.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.config.FtpConfiguration;
import ru.homyakin.zakupki.models.FileType;
import ru.homyakin.zakupki.service.FileSystemService;
import ru.homyakin.zakupki.service.ZipService;
import ru.homyakin.zakupki.web.exceptions.ConnectException;
import ru.homyakin.zakupki.web.exceptions.LoginException;

@Component
public class FTPClient223fz implements FTPClientFZ {
    private final static Logger logger = LoggerFactory.getLogger(FTPClient223fz.class);
    private final static String basicWorkspace = "/out/published";
    /*
     * private final static List<String> parsingFolders = Arrays.asList("contract",
     * "contractInfo", "contractCompleting", "purchaseNotice", "purchaseNoticeAE",
     * "purchaseNoticeAE94", "purchaseNoticeAESMBO", "purchaseNoticeEP",
     * "purchaseNoticeIS", "purchaseNoticeOA", "purchaseNoticeOK",
     * "purchaseNoticeZK", "purchaseNoticeZKESMBO", "purchaseProtocol",
     * "purchaseProtocolZK", "purchaseProtocolVK", "purchaseProtocolPAEP",
     * "purchaseProtocolPAAE", "purchaseProtocolPAAE94", "purchaseProtocolOSZ",
     * "purchaseProtocolRZOK", "purchaseProtocolRZ1AE", "purchaseProtocolRZ2AE");
     */
    private final static String downloadPath = "./zakupki_download";
    private final static FTPClient ftp = new FTPClient();
    private final List<String> parsingFolders = new ArrayList<>();
    private final ZipService zipService;
    private final FileSystemService fileSystemService;
    private final FtpConfiguration ftpConfiguration;

    public FTPClient223fz(ZipService zipService, FileSystemService fileSystemService, FtpConfiguration ftpConfiguration) {
        this.zipService = zipService;
        this.fileSystemService = fileSystemService;
        this.ftpConfiguration = ftpConfiguration;
    }

    public FileType[] getAllParsingFolders() {
        return FileType.values();
    }

    public void addParsingFolder(FileType fileType) {
        if (!parsingFolders.contains(fileType.getValue())) {
            parsingFolders.add(fileType.getValue());
            logger.info("Added {}", fileType.getValue());
        }
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

    @Override
    public void parseFTPServer() {
        logger.info("Start parsing in: {}", ftpConfiguration.getUrl());
        searchRegionsDirectories(basicWorkspace);
    }

    private void searchRegionsDirectories(String workspace) {
        try {
            var namesDirectories = ftp.listDirectories(workspace);
            for (var directory : namesDirectories) {
                if (!directory.getName().equals("archive")) {
                    makeDownloadDirectories(workspace + "/" + directory.getName());
                    searchInRegions(workspace + "/" + directory.getName());
                }
            }
        } catch (IOException e) {
            logger.error("Something went wrong wile listing {}", workspace, e);
        }
    }

    private void makeDownloadDirectories(String workspace) {
        for (var folder : parsingFolders) { //TODO make it from searchInRegions
            fileSystemService.makeDirectory(downloadPath + workspace + "/" + folder + "/daily/unzip");
        }
    }

    private void searchInRegions(String workspace) {
        try {
            var namesDirectories = ftp.listDirectories(workspace);
            for (var directory : namesDirectories) {
                if (parsingFolders.contains(directory.getName())) {
                    searchFiles(workspace + "/" + directory.getName() + "/daily", directory.getName());
                }
            }
        } catch (IOException e) {
            logger.error("Something went wrong wile listing {}", workspace, e);
        }
    }

    private void searchFiles(String workspace, String folder) {
        logger.info("Start parsing {}", workspace);
        try {
            var files = ftp.listFiles(workspace);
            for (var remoteFile : files) {
                if (remoteFile.isFile()) {
                    if (downloadFile(downloadPath + workspace + "/" + remoteFile.getName(),
                        workspace + "/" + remoteFile.getName())) {
                        zipService.unzipFile(downloadPath + workspace + "/" + remoteFile.getName(),
                            downloadPath + workspace, folder);
                    } else {
                        logger.error("Unable to download {}", workspace + "/" + remoteFile.getName());
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Something went wrong wile listing {}", workspace, e);
        }

    }

    private boolean downloadFile(String localFilePath, String remoteFilePath) {
        logger.debug("Start downloading {}", localFilePath);
        Path localFile = fileSystemService.makeFile(localFilePath);
        boolean isDownload = false;
        try {
            isDownload = ftp.retrieveFile(remoteFilePath, Files.newOutputStream(localFile));
        } catch (IOException e) {
            logger.error("Something went wrong wile downloading {}", remoteFilePath, e);
        }
        return isDownload;
    }
}
