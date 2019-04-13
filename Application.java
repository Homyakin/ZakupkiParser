
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import FTPfz.FTPClient223fz;;


public class Application
{
	static int f = 0;
	static double size = 0;
	
	private static void writeFiles(FileWriter file, FTPClient ftp, String workspace) throws IOException
	{
		FTPFile[] namesFiles = ftp.listFiles(workspace);
		for(FTPFile n: namesFiles)
		{
			if(!n.isDirectory())
			{
				++f;
				size += n.getSize() / 1024;
				//file.write(n.getName() + "\n");
			}
		}
	}
	
	public static void main(String[] args) 
	{          
		FTPClient223fz ftp = FTPClient223fz.INSTANCE;
		
		try
		{
			ftp.connect();
			ftp.login();
			ftp.parseFTPServer();
		} 
		catch (SocketException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		

	}

}
