package ru.homyakin.zakupki.service.parser;

import ru.homyakin.zakupki.documentsinfo._223fz.contract.Contract;

import java.util.Optional;

public class ContractParser extends MainXmlParser {

    public static Optional<Contract> parse(String filePath) {
        return parse(filePath, Contract.class);
    }
}
