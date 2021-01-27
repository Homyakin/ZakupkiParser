package ru.homyakin.zakupki.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.homyakin.zakupki.web.FtpClient223Fz;

@Service
public class ConsoleInputService {
    private final static Logger logger = LoggerFactory.getLogger(ConsoleInputService.class);
    private final FtpClient223Fz ftp;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private boolean skipDate = false;

    public  ConsoleInputService(FtpClient223Fz ftp) {
        this.ftp = ftp;
    }

    public List<String> selectFromList(List<String> list) {
        System.out.println("0: Продолжить");
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(i + 1 + ": " + list.get(i));
        }
        System.out.println("100: Добавить всё");
        int idx = -1;
        Scanner sc = new Scanner(System.in);
        var selectedItems = new ArrayList<String>();
        while (idx != 0) {
            idx = sc.nextInt();
            if (idx == 100) {
                for (String item : list) {
                    if (!selectedItems.contains(item)) {
                        logger.info("Added: {}", item);
                        selectedItems.add(item);
                    }
                }
                break;
            }
            if (idx < 1 || idx > list.size()) {
                continue;
            }
            if (!selectedItems.contains(list.get(idx - 1))) {
                logger.info("Added: {}", list.get(idx - 1));
                selectedItems.add(list.get(idx - 1));
            }
        }
        return selectedItems;
    }

    public Optional<LocalDate> selectStartDate() {
        System.out.println("Введите дату начала парсинга в формате дд-мм-гггг, или введите 0, для полного парсинга");
        return inputDate();
    }

    public Optional<LocalDate> selectEndDate() {
        if (skipDate) return Optional.empty();
        System.out.println("Введите дату конца парсинга в формате дд-мм-гггг");
        return inputDate();
    }

    public Optional<LocalDate> inputDate() {
        Scanner sc = new Scanner(System.in);
        LocalDate date = null;
        while (date == null) {
            var dateString = sc.next();
            if (dateString.equals("0") && !skipDate) {
                skipDate = true;
                return Optional.empty();
            }
            try {
                date = LocalDate.parse(dateString, dateFormat);
            } catch (DateTimeParseException e) {
                System.out.println("Неправильный формат " + e.getMessage());
            }
        }
        return Optional.of(date);
    }
}
