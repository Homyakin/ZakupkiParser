package ru.homyakin.zakupki.database;

import ru.homyakin.zakupki.documentsinfo._223fz.types.CurrencyType;

public class RepositoryService {
    public static Integer convertBoolean(Boolean statement) {
        if (statement == null) return null;
        else return statement ? 1 : 0;
    }

    public static String getCurrencyCode(CurrencyType currency) {
        if(currency.getCode() != null) return currency.getCode();
        else return currency.getLetterCode();
    }
}
