package ru.homyakin.zakupki.service.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import ru.homyakin.zakupki.models._223fz.contract.Contract;

public class ContractParser extends MainXmlParser {
    private final static Logger logger = LoggerFactory.getLogger(ContractParser.class);
    private final static String XMLNS = "xmlns=\"http://zakupki.gov.ru/223fz/types/1\"";

    public static Optional<Object> parse(String filePath) {
        var parsedObject = parse(filePath, Contract.class, false);
        if (parsedObject.isEmpty()) {
            return Optional.empty();
        }
        // Ecли содерживое xml существует, но его не удалось распарсить, тогда вручную меняем содержимое xml
        if (parsedObject.get() instanceof Contract contract && contract.getBody().getItem().getContractData().getCustomer().getMainInfo() == null) {
            logger.warn("No xmlns in contract");
            try {
                var contractString = Files.readString(
                    Paths.get(URI.create("file://" + changePathIfWindows(filePath)))
                );
                var xml = "";
                /*
                 Два варианта сломанных xml, наличие строки xmlns=""
                 или отсутствие xmlns="http://zakupki.gov.ru/223fz/types/1"
                 */
                if (contractString.contains(XMLNS)) {
                    xml = contractString.replaceAll("xmlns=\"\"","");
                } else {
                    var startIdxOfXmlNs = contractString.indexOf("ns2:contract");
                    var startXml = contractString.substring(0, startIdxOfXmlNs + 12);
                    var xmlns = " " + XMLNS + " ";
                    var endXml = contractString.substring(startIdxOfXmlNs + 13);
                    xml = startXml + xmlns + endXml;
                }
                return parse(new ByteArrayInputStream(xml.getBytes()), Contract.class)
                    .map(it -> it);
            } catch (IOException e) {
                throw new IllegalStateException("Unable to read file", e);
            }
        }
        return parsedObject;
    }

    private static String changePathIfWindows(@NonNull String path) {
        if (path.contains("\\")) {
            return "/" + path.replace("\\", "/");
        }
        return path;
    }
}
