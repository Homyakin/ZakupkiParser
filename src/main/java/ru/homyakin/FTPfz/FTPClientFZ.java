package FTPfz;

import java.io.IOException;
import java.net.SocketException;

public interface FTPClientFZ {
    // connect to sever
    void connect() throws IOException;

    // login to server
    void login() throws IOException;

    // parse server
    void parseFTPServer() throws IOException;
}
