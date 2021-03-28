package ru.homyakin.zakupki.service.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainXmlParser {
    private final static Logger logger = LoggerFactory.getLogger(MainXmlParser.class);

    protected static <T> Optional<T> parse(String filePath, Class<T> clazz) {
        try {
            logger.info("Start parsing {}", filePath);
            if (isFileEmpty(filePath)) return Optional.empty();
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return Optional.of(clazz.cast(unmarshaller.unmarshal(new File(filePath))));
        } catch (JAXBException e) {
            logger.error("Unable to parse {}", filePath, e);
            throw new IllegalStateException("Unable to parse");
        } catch (ClassCastException e) {
            logger.error("Can't cast to class {}", clazz.getName(), e);
            throw new IllegalStateException("Unable to cast");
        }
    }

    protected static <T> Optional<T> parse(InputStream inputStream, Class<T> clazz) {
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return Optional.of(clazz.cast(unmarshaller.unmarshal(inputStream)));
        } catch (JAXBException e) {
            logger.error("Unable to parse", e);
            throw new IllegalStateException("Unable to parse");
        }
    }

    private static boolean isFileEmpty(String filePath) {
        try {
            var br = new BufferedReader(new FileReader(filePath));
            if (br.readLine() == null) {
                return true;
            }
        } catch (IOException ignored) {
        }
        return false;
    }
}
