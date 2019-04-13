package FTPfz;

import java.io.IOException;
import java.net.SocketException;


enum Singleton
{
	INSTANCE;
	public static Singleton getInstance() { return INSTANCE; }
} 


public interface FTPClientFZ 
{
	//connect to sever
	void connect() throws SocketException, IOException;
	//login to server
	void login() throws IOException;
	//pase server
	public void parseFTPServer() throws IOException;
}
