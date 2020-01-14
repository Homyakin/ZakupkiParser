package ru.homyakin.zakupki.database;

import ru.homyakin.zakupki.documentsinfo._223fz.types.CurrencyType;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RepositoryService {
    public static Integer convertBoolean(Boolean statement) {
        if (statement == null) return null;
        else return statement ? 1 : 0;
    }

    public static String getCurrencyCode(CurrencyType currency) {
        if (currency == null) return null;
        if (currency.getCode() != null) return removeExtraSpaces(currency.getCode());
        else return removeExtraSpaces(currency.getLetterCode());
    }

    public static LocalDate convertFromXMLGregorianCalendarToLocalDate(XMLGregorianCalendar xmlTime) {
        if(xmlTime == null) return null;
        return LocalDate.of(
            xmlTime.getYear(),
            xmlTime.getMonth(),
            xmlTime.getDay()
        );
    }

    public static LocalDateTime convertFromXMLGregorianCalendarToLocalDateTime(XMLGregorianCalendar xmlTime) {
        if(xmlTime == null) return null;
        return LocalDateTime.of(
            xmlTime.getYear(),
            xmlTime.getMonth(),
            xmlTime.getDay(),
            xmlTime.getHour(),
            xmlTime.getMinute(),
            xmlTime.getSecond()
        );
    }

    public static String removeExtraSpaces(String s) {
        if (s == null) return null;
        return s.trim().replaceAll(" +", " ");
    }
}
