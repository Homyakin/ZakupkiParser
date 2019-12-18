package ru.homyakin.zakupki.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.homyakin.zakupki.database.ZakupkiDatabase;
import ru.homyakin.zakupki.documentsinfo.ContractInfo;
import ru.homyakin.zakupki.exceptions.FileIsEmptyException;
import ru.homyakin.zakupki.service.parser.ContractParser;
import ru.homyakin.zakupki.service.parser.interfaces.DocumentParser;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipService {
    private final static Logger logger = LoggerFactory.getLogger(ZipService.class);

    private static ZakupkiDatabase db = new ZakupkiDatabase();
    private Map<String, DocumentParser> parsers = new HashMap<>();

    public ZipService() {
        parsers.put("contract", new ContractParser());
    }

    public void unzipFile(String filePath, String path, String folder) {
        logger.info("Start unzipping {}", filePath);
        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(Paths.get(filePath)))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {

                name = entry.getName();
                Path localFile = Paths.get(path + "/unzip/" + name);
                try {
                    Files.createFile(localFile); //TODO add class for creation files
                } catch (IOException ignored) {
                    //TODO check if error is existing file (make validator class)
                }

                OutputStream fout = Files.newOutputStream(localFile);
                byte[] buffer = new byte[4096];
                for (int len = zin.read(buffer); len != -1; len = zin.read(buffer)) {
                    fout.write(buffer, 0, len);
                }


                fout.flush();
                zin.closeEntry();
                fout.close();
                if ("contract".equals(folder)) {
                    ContractParser contractParser = new ContractParser();
                    ContractInfo contract = (ContractInfo) parsers.get("contract").parse(path + "/unzip/" + name);
                    //db.insertContract(contract);
                }
            }
        } catch (IOException e) {
            logger.error("Error in unzipping process of file {}", filePath, e);
        }
    }
}
