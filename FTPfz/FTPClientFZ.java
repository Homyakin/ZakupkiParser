package FTPfz;

import java.io.IOException;
import java.net.SocketException;

public interface FTPClientFZ 
{
	void connect(String server) throws SocketException, IOException;
	void login(String user, String passwd) throws IOException;
	public void parseFTPServer();
}
