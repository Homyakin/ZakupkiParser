package ru.homyakin;

import ru.homyakin.web.FTPClient223fz;

import java.io.IOException;
import java.net.SocketException;

public class Application {

    public static void main(String[] args) {
        FTPClient223fz ftp = FTPClient223fz.INSTANCE;

        try {
            ftp.connect();
            ftp.login();
            ftp.parseFTPServer();
        } catch (SocketException e) {
            //TODO add logs
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
