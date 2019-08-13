import java.io.IOException;
import java.net.SocketException;

import FTPfz.FTPClient223fz;

public class Application {

    public static void main(String[] args) {
        FTPClient223fz ftp = FTPClient223fz.INSTANCE;

        try {
            ftp.connect();
            ftp.login();
            ftp.parseFTPServer();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
