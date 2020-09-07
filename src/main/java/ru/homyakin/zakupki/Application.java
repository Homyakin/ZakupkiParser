package ru.homyakin.zakupki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.homyakin.zakupki.service.ConsoleInputService;
import ru.homyakin.zakupki.web.FTPClient223fz;
import ru.homyakin.zakupki.web.exceptions.NetworkException;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private final FTPClient223fz ftp;
    private final ConsoleInputService consoleInputService;

    public Application(FTPClient223fz ftp, ConsoleInputService consoleInputService) {
        this.ftp = ftp;
        this.consoleInputService = consoleInputService;
    }

    @Override
    public void run(String... args) {
        try {
            ftp.connect();
            ftp.login();
            consoleInputService.selectRegions();
            consoleInputService.selectFolders();
            consoleInputService.selectTimePeriod();
            ftp.parseFTPServer();
        } catch (NetworkException e) {
            logger.error("Network error: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Internal error", e);
        }
    }

    //TODO add reconnecting to server if something went wrong
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
