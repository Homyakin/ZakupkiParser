package ru.homyakin.zakupki.service.parser;

import java.util.Optional;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseContract;

public class PurchaseContractParser extends MainXmlParser {
    public static Optional<PurchaseContract> parse(String filePath) {
        return parse(filePath, PurchaseContract.class);
    }
}
