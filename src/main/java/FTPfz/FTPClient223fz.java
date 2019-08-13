package FTPfz;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLStreamException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import Database.ZakupkiDatabase;
import DocumentsInfo.ContractInfo;
import XMLParser.ContractParser;

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
	private static ZakupkiDatabase db = new ZakupkiDatabase();
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
	private final static String downloadPath = "D:/Zakupki";

	@Override
	public void connect() throws SocketException, IOException {
		ftp.connect(SERVER);
		System.out.println("SERVER: " + ftp.getReplyStrings());
	}

	@Override
	public void login() throws IOException {
		if (ftp.login(USER, PASSWD)) {
			System.out.println("LOGGED IN SERVER");
			ftp.enterLocalPassiveMode(); // разрешить обмен данными с сервером
		} else {
			System.out.println("Failed to log into the server");
			return;
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

	// создать директории на локальном диске для загрузки файлов
	private void makeDownloadDirectories(String workspace) throws IOException {
		for (String s : parsingFolders) {
			Path dir = Paths.get(downloadPath + workspace + "/" + s + "/daily/unzip");
			Files.createDirectories(dir);
		}
	}

	// найти необходимые директории на сервере
	private void searchInRegions(String workspace) throws IOException {
		FTPFile[] namesDirectories = ftp.listDirectories(workspace);
		for (FTPFile n : namesDirectories) {
			if (parsingFolders.contains(n.getName())) {
				searchFiles(workspace + "/" + n.getName() + "/daily", n.getName());
			}
		}
	}

	// найти файлы на сервере
	private void searchFiles(String workspace, String folder) throws IOException {
		System.out.println(workspace); // DEBUG
		FTPFile[] files = ftp.listFiles(workspace);
		for (FTPFile remote : files) {
			if (remote.isFile()) {
				if (downloadFile(downloadPath + workspace + "/" + remote.getName(),
						workspace + "/" + remote.getName())) {
					unzipFile(downloadPath + workspace + "/" + remote.getName(), downloadPath + workspace, folder);
				} else {
					System.out.println("Не удалось загрузить " + workspace + "/" + remote.getName());
				}
			}
		}
	}

	// загрузить файлы
	private boolean downloadFile(String localPath, String remotePath) throws IOException {
		Path localFile = Paths.get(localPath);
		try {
			Files.createFile(localFile);
		} catch(IOException e) {
			
		}
		if (!ftp.retrieveFile(remotePath, Files.newOutputStream(localFile))) {
			return false;
		}
		return true;

	}

	// разархивировать файл
	private void unzipFile(String filePath, String path, String folder) {
		try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(Paths.get(filePath)))) {
			ZipEntry entry;
			String name;
			while ((entry = zin.getNextEntry()) != null) {

				name = entry.getName();
				Path localFile = Paths.get(path + "/unzip/" + name);
				try {
					Files.createFile(localFile);
				} catch (IOException e) {
					
				}

				OutputStream fout = Files.newOutputStream(localFile);
				byte[] buffer = new byte[4096];
				for (int len = zin.read(buffer); len != -1; len = zin.read(buffer)) {
					fout.write(buffer, 0, len);
				}
				
				
				
				fout.flush();
				zin.closeEntry();
				fout.close();
				if("contract".equals(folder)) {
					ContractParser contractParser = new ContractParser(path + "/unzip/" + name);
					ContractInfo contract = contractParser.parseContract();
					db.insertContract(contract);
				}
			}
		}catch (XMLStreamException e) {
			if(!e.getMessage().contains("ParseError at [row,col]:[1,1]")) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
