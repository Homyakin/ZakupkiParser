package ru.homyakin.zakupki.service.parser.interfaces;

import ru.homyakin.zakupki.documentsinfo.DocumentInfo;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public interface DocumentParser {
    DocumentInfo parse(String filePath);
}
