package FTPfz;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

public class FTPClient223fz implements FTPClientFZ
{

	FTPClient ftp;
	static String SERVER = "ftp.zakupki.gov.ru";
	static String USER = "fz223free";
	static String PASSWD = "fz223free";
	private static String workspace = "ftp.zakupki.gov.ru/out/published/";
	@Override
	public void connect(String server) throws SocketException, IOException 
	{
		ftp.connect(SERVER);
		System.out.println("SERVER: " + ftp.getReplyStrings());
	}

	@Override
	public void login(String user, String passwd) throws IOException 
	{
		if(ftp.login(USER, PASSWD))
		{
			System.out.println("LOGGED IN SERVER");
			ftp.enterLocalPassiveMode(); //разрешить обмен данными с сервером
		}
		else
		{
			System.out.println("Failed to log into the server");
            return;
		}
	}
	
	@Override
	public void parseFTPServer()
	{
		
	}
}
