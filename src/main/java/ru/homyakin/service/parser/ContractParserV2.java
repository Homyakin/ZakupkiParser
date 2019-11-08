package ru.homyakin.service.parser;

import ru.homyakin.documentsinfo.DocumentInfo;
import ru.homyakin.service.parser.interfaces.DocumentParser;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class ContractParserV2 implements DocumentParser {

    @Override
    public DocumentInfo parse(String filePath) throws XMLStreamException, IOException {
        return null;
    }
}
