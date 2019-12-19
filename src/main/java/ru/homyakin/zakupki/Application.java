package ru.homyakin.zakupki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.homyakin.zakupki.web.exceptions.NetworkException;
import ru.homyakin.zakupki.web.FTPClient223fz;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private FTPClient223fz ftp;

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    //TODO add Spring
    //TODO add reconnecting to server if something went wrong
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            ftp.connect();
            ftp.login();
            ftp.parseFTPServer();
        } catch (NetworkException e) {
            logger.error("Network error: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
    }
}
