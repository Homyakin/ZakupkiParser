package FTPfz;

import java.io.IOException;
import java.net.SocketException;

public interface FTPClientFZ {
	// connect to sever
	void connect() throws SocketException, IOException;

	// login to server
	void login() throws IOException;

	// parse server
	public void parseFTPServer() throws IOException;
}
