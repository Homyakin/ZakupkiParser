package ru.homyakin.zakupki.utils;

import java.nio.file.Path;
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

    public String extractRegionFromFilePath(String filePath) {
        //Формат типФайла_Регион(может содержать _)_Дата.xml
        var path = Path.of(filePath);
        var words = path.getFileName().toString().split("_");
        StringBuilder region = new StringBuilder(words[1]);
        for (int i = 2; i < words.length; ++i) {
            if (words[i].equals("") || !Character.isDigit(words[i].charAt(0))) {
                region.append("_").append(words[i]);
            } else {
                break;
            }
        }
        return region.toString();
    }
}
