package ru.homyakin.service;

import ru.homyakin.database.ZakupkiDatabase;
import ru.homyakin.documentsinfo.ContractInfo;
import ru.homyakin.exceptions.FileIsEmptyException;
import ru.homyakin.service.parser.ContractParser;
import ru.homyakin.service.parser.interfaces.DocumentParser;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipService {
    private static ZakupkiDatabase db = new ZakupkiDatabase();

    private Map<String, DocumentParser> parsers;

    ZipService() {
        parsers.put("contract", new ContractParser());
    }

    public void unzipFile(String filePath, String path, String folder) {

        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(Paths.get(filePath)))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {

                name = entry.getName();
                Path localFile = Paths.get(path + "/unzip/" + name);
                try {
                    Files.createFile(localFile);
                } catch (IOException ignored) {
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
                    db.insertContract(contract);
                }
            }
        } catch (FileIsEmptyException e) {
            //TODO add log
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
