package ru.homyakin.zakupki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.homyakin.zakupki.web.exceptions.NetworkException;
import ru.homyakin.zakupki.web.FTPClient223fz;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    //TODO add Spring
    //TODO add reconnecting to server if something went wrong
    public static void main(String[] args) {
        FTPClient223fz ftp = FTPClient223fz.INSTANCE;

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
