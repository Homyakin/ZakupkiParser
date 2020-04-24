package ru.homyakin.zakupki.service.parser;

import java.util.Optional;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNotice;

public class PurchaseNoticeParser extends MainXmlParser {

    public static Optional<PurchaseNotice> parse(String filePath) {
        return parse(filePath, PurchaseNotice.class);
    }
}