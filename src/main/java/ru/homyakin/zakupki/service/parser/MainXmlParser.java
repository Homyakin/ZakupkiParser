package ru.homyakin.zakupki.service.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Optional;

public class MainXmlParser {
    private final static Logger logger = LoggerFactory.getLogger(MainXmlParser.class);

    protected static <T> Optional<T> parse(String filePath, Class<T> clazz) {
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return Optional.of(clazz.cast(unmarshaller.unmarshal(new File(filePath))));
        } catch (JAXBException e) {
            logger.error("Unable to parse {}", filePath, e);
        } catch (ClassCastException e) {
            logger.error("Can't cast to class {}", clazz.getName());
        }
        return Optional.empty();
    }
}
