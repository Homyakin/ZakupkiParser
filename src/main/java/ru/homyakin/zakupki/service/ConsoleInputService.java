package ru.homyakin.zakupki.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import org.springframework.stereotype.Service;
import ru.homyakin.zakupki.web.FTPClient223fz;

@Service
public class ConsoleInputService {
    private final FTPClient223fz ftp;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public  ConsoleInputService(FTPClient223fz ftp) {
        this.ftp = ftp;
    }

    public void selectFolders() {
        var folders = ftp.getAllParsingFolders();
        System.out.println("0: Start");
        for (int i = 0; i < folders.length; ++i) {
            System.out.println(i + 1 + ": " + folders[i].getValue());
        }
        int folder = -1;
        Scanner sc = new Scanner(System.in);
        while (folder != 0) {
            folder = sc.nextInt();
            if (folder < 1 || folder > folders.length) {
                continue;
            }
            ftp.addParsingFolder(folders[folder - 1]);
        }
    }

    public void selectRegions() {
        var regions = ftp.getAllRegions();
        System.out.println("0: Start");
        for (int i = 0; i < regions.size(); ++i) {
            System.out.println(i + 1 + ": " + regions.get(i));
        }
        int folder = -1;
        Scanner sc = new Scanner(System.in);
        while (folder != 0) {
            folder = sc.nextInt();
            if (folder < 1 || folder > regions.size()) {
                continue;
            }
            ftp.addParsingRegion(regions.get(folder - 1));
        }
    }

    public void selectTimePeriod() {
        System.out.println("Введите дату начала парсинга в формате дд-мм-гггг, или введите 0, для полного парсинга");
        Scanner sc = new Scanner(System.in);
        LocalDate start = null;
        while (start == null) {
            var dateString = sc.next();
            if (dateString.equals("0")) {
                return;
            }
            try {
                start = LocalDate.parse(dateString, dateFormat);
            } catch (DateTimeParseException e) {
                System.out.println("Неправильный формат " + e.getMessage());
            }
        }
        ftp.setStartDate(start);
        LocalDate end = null;
        System.out.println("Введите дату конца парсинга в формате дд-мм-гггг");
        while (end == null) {
            var dateString = sc.next();
            try {
                end = LocalDate.parse(dateString, dateFormat);
            } catch (DateTimeParseException e) {
                System.out.println("Неправильный формат " + e.getMessage());
            }
        }
        ftp.setEndDate(end);
    }
}
