package ru.homyakin.zakupki.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

    public LocalDate convertCalendarToLocalDate(Calendar calendar) {
        return LocalDateTime
            .ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId())
            .toLocalDate();
    }

    public String generateGuid() {
        return UUID.randomUUID().toString();
    }
}