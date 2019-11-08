package ru.homyakin.web;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import ru.homyakin.service.ZipService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

//enum implements the pattern Singleton
public enum FTPClient223fz implements FTPClientFZ {
    INSTANCE;

    public static FTPClient223fz getInstance() {
        return INSTANCE;
    }

    private static FTPClient ftp = new FTPClient();
    public final static String SERVER = "ftp.zakupki.gov.ru";
    public final static String USER = "fz223free";
    public final static String PASSWD = "fz223free";
    private final static String basicWorkspace = "/out/published";
    private static ZipService zipService = new ZipService();
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
    private final static List<String> parsingFolders = Arrays.asList("contract");
    private final static String downloadPath = "./zakupki_download";

    @Override
    public void connect() throws IOException {
        ftp.connect(SERVER);
        System.out.println("SERVER: " + Arrays.toString(ftp.getReplyStrings()));
    }

    @Override
    public void login() throws IOException {
        if (ftp.login(USER, PASSWD)) {
            System.out.println("LOGGED IN SERVER");
            ftp.enterLocalPassiveMode();
        } else {
            System.out.println("Failed to log into the server");
        }
    }

    @Override
    public void parseFTPServer() throws IOException {
        searchRegionsDirectories(basicWorkspace);
    }

    private void searchRegionsDirectories(String workspace) throws IOException {

        System.out.println(workspace); // DEBUG
        FTPFile[] namesDirectories = ftp.listDirectories(workspace);
        for (FTPFile n : namesDirectories) {
            if (!n.getName().equals("archive")) {
                makeDownloadDirectories(workspace + "/" + n.getName());
                searchInRegions(workspace + "/" + n.getName());
            }
        }
    }

    private void makeDownloadDirectories(String workspace) throws IOException {
        for (String s : parsingFolders) {
            Path dir = Paths.get(downloadPath + workspace + "/" + s + "/daily/unzip");
            Files.createDirectories(dir);
        }
    }

    private void searchInRegions(String workspace) throws IOException {
        FTPFile[] namesDirectories = ftp.listDirectories(workspace);
        for (FTPFile n : namesDirectories) {
            if (parsingFolders.contains(n.getName())) {
                searchFiles(workspace + "/" + n.getName() + "/daily", n.getName());
            }
        }
    }

    private void searchFiles(String workspace, String folder) throws IOException {
        System.out.println(workspace); // DEBUG
        FTPFile[] files = ftp.listFiles(workspace);
        for (FTPFile remote : files) {
            if (remote.isFile()) {
                if (downloadFile(downloadPath + workspace + "/" + remote.getName(),
                        workspace + "/" + remote.getName())) {
                    zipService.unzipFile(downloadPath + workspace + "/" + remote.getName(),
                            downloadPath + workspace, folder);
                } else {
                    //TODO exception
                    System.out.println("Unable to download " + workspace + "/" + remote.getName());
                }
            }
        }
    }

    private boolean downloadFile(String localPath, String remotePath) throws IOException {
        Path localFile = Paths.get(localPath);
        try {
            Files.createFile(localFile);
        } catch (IOException ignored) {

        }
        return ftp.retrieveFile(remotePath, Files.newOutputStream(localFile));
    }
}
