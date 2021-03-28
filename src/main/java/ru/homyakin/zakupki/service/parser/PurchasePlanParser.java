package ru.homyakin.zakupki.service.parser;

import java.util.Optional;
import ru.homyakin.zakupki.models._223fz.purchaseplan.PurchasePlan;

public class PurchasePlanParser extends MainXmlParser  {
    public static Optional<PurchasePlan> parse(String filePath) {
        return parse(filePath, PurchasePlan.class);
    }
}
