package ru.homyakin.service.parser.interfaces;

import ru.homyakin.documentsinfo.DocumentInfo;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public interface DocumentParser {
    DocumentInfo parse(String filePath) throws XMLStreamException, IOException;
}
