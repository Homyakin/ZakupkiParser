package ru.homyakin.zakupki.web;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.config.FtpConfiguration;
import ru.homyakin.zakupki.service.FileSystemService;
import ru.homyakin.zakupki.service.ZipService;
import ru.homyakin.zakupki.web.exceptions.ConnectException;
import ru.homyakin.zakupki.web.exceptions.LoginException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

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
    private final static List<String> parsingFolders = Arrays.asList("purchasePlan");
    private final static String downloadPath = "./zakupki_download";
    private final static FTPClient ftp = new FTPClient();

    private final ZipService zipService;
    private final FileSystemService fileSystemService;
    private final FtpConfiguration ftpConfiguration;

    public FTPClient223fz(ZipService zipService, FileSystemService fileSystemService, FtpConfiguration ftpConfiguration) {
        this.zipService = zipService;
        this.fileSystemService = fileSystemService;
        this.ftpConfiguration = ftpConfiguration;
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
            FTPFile[] namesDirectories = ftp.listDirectories(workspace);
            for (FTPFile n : namesDirectories) {
                if (!n.getName().equals("archive")) {
                    makeDownloadDirectories(workspace + "/" + n.getName());
                    searchInRegions(workspace + "/" + n.getName());
                }
            }
        } catch (IOException e) {
            logger.error("Something went wrong wile listing {}", workspace, e);
        }
    }

    private void makeDownloadDirectories(String workspace) {
        for (String s : parsingFolders) { //TODO make it from searchInRegions
            fileSystemService.makeDirectory(downloadPath + workspace + "/" + s + "/daily/unzip");
        }
    }

    private void searchInRegions(String workspace) {
        try {
            FTPFile[] namesDirectories = ftp.listDirectories(workspace);
            for (FTPFile n : namesDirectories) {
                if (parsingFolders.contains(n.getName())) {
                    searchFiles(workspace + "/" + n.getName() + "/daily", n.getName());
                }
            }
        } catch (IOException e) {
            logger.error("Something went wrong wile listing {}", workspace, e);
        }
    }

    private void searchFiles(String workspace, String folder) {
        logger.info("Start parsing {}", workspace);
        try {
            FTPFile[] files = ftp.listFiles(workspace);
            for (FTPFile remote : files) {
                if (remote.isFile()) {
                    if (downloadFile(downloadPath + workspace + "/" + remote.getName(),
                        workspace + "/" + remote.getName())) {
                        zipService.unzipFile(downloadPath + workspace + "/" + remote.getName(),
                            downloadPath + workspace, folder);
                    } else {
                        logger.error("Unable to download {}", workspace + "/" + remote.getName());
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Something went wrong wile listing {}", workspace, e);
        }

    }

    private boolean downloadFile(String localFilePath, String remoteFilePath) {
        logger.info("Start downloading {}", localFilePath);
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
