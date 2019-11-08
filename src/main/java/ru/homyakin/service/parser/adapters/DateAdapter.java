package ru.homyakin.service.parser.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String s) {
        //Try is needed because of different formats in xml
        try {
            return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    @Override
    public String marshal(LocalDate localDate) {
        return localDate.toString();
    }
}
