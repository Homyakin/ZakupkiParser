package ru.homyakin.service.parser;

import ru.homyakin.documentsinfo.ContractInfo;
import ru.homyakin.documentsinfo.DocumentInfo;
import ru.homyakin.documentsinfo._223fz.contract._1.Contract;
import ru.homyakin.service.parser.interfaces.DocumentParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

public class ContractParser implements DocumentParser {

    @Override
    public DocumentInfo parse(String filePath) throws XMLStreamException, IOException {
        try {
            JAXBContext jc = JAXBContext.newInstance(Contract.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return new ContractInfo((Contract) unmarshaller.unmarshal(new File(filePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
