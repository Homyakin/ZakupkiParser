package ru.homyakin.service;

import ru.homyakin.database.ZakupkiDatabase;
import ru.homyakin.documentsinfo.ContractInfo;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipService {
    private static ZakupkiDatabase db = new ZakupkiDatabase();


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
                    ContractParser contractParser = new ContractParser(path + "/unzip/" + name);
                    ContractInfo contract = contractParser.parseContract();
                    db.insertContract(contract);
                }
            }
        } catch (XMLStreamException e) {
            //TODO check empty file
            if (!e.getMessage().contains("ParseError at [row,col]:[1,1]")) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
