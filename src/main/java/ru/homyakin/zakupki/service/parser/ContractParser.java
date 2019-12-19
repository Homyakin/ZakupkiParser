package ru.homyakin.zakupki.service.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo.ContractInfo;
import ru.homyakin.zakupki.documentsinfo.DocumentInfo;
import ru.homyakin.zakupki.documentsinfo._223fz.contract.Contract;
import ru.homyakin.zakupki.service.parser.interfaces.DocumentParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
public class ContractParser implements DocumentParser {
    private final static Logger logger = LoggerFactory.getLogger(ContractParser.class);

    @Override
    public DocumentInfo parse(String filePath) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Contract.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return new ContractInfo((Contract) unmarshaller.unmarshal(new File(filePath)));
        } catch (JAXBException e) {
            logger.error("Unable to parse {}", filePath);
            //TODO add exception
        }
        return null;
    }
}
