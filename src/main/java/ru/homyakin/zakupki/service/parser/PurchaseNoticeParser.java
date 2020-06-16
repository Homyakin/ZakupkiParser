package ru.homyakin.zakupki.service.parser;

import java.util.Optional;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNotice;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeAE;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeAE94FZ;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeAESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeEP;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeOA;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeOK;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeZK;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeZKESMBO;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeZPESMBO;

public class PurchaseNoticeParser extends MainXmlParser {

    public static Optional<PurchaseNotice> parse(String filePath) {
        return parse(filePath, PurchaseNotice.class);
    }

    public static Optional<PurchaseNoticeOA> parseOA(String filePath) {
        return parse(filePath, PurchaseNoticeOA.class);
    }

    public static Optional<PurchaseNoticeKESMBO> parseKESMBO(String filePath) {
        return parse(filePath, PurchaseNoticeKESMBO.class);
    }

    public static Optional<PurchaseNoticeEP> parseEP(String filePath) {
        return parse(filePath, PurchaseNoticeEP.class);
    }

    public static Optional<PurchaseNoticeAESMBO> parseAESMBO(String filePath) {
        return parse(filePath, PurchaseNoticeAESMBO.class);
    }

    public static Optional<PurchaseNoticeAE> parseAE(String filePath) {
        return parse(filePath, PurchaseNoticeAE.class);
    }

    public static Optional<PurchaseNoticeAE94FZ> parseAE94(String filePath) {
        return parse(filePath, PurchaseNoticeAE94FZ.class);
    }

    public static Optional<PurchaseNoticeOK> parseOK(String filePath) {
        return parse(filePath, PurchaseNoticeOK.class);
    }

    public static Optional<PurchaseNoticeZK> parseZK(String filePath) {
        return parse(filePath, PurchaseNoticeZK.class);
    }

    public static Optional<PurchaseNoticeZKESMBO> parseZKESMBO(String filePath) {
        return parse(filePath, PurchaseNoticeZKESMBO.class);
    }

    public static Optional<PurchaseNoticeZPESMBO> parseZPESMBO(String filePath) {
        return parse(filePath, PurchaseNoticeZPESMBO.class);
    }
}