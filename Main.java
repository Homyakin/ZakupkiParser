import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


public class Main 
{
	static int f = 0;
	static double size = 0;
	private static void showServerReply(FTPClient ftp)
	{
        String[] replies = ftp.getReplyStrings(); 
        if (replies != null && replies.length > 0)
        {
	        for (String aReply : replies)
	        {
	            System.out.println("SERVER: " + aReply);
	        }
        }
    }
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
	
	private static void searchDirectories(FileWriter file, FTPClient ftp, String workspace) throws IOException
	{
		System.out.println(f);
		System.out.println(size);
		System.out.println(workspace);
		writeFiles(file, ftp, workspace);
		FTPFile[] namesDirectories = ftp.listDirectories(workspace);
		for(FTPFile n: namesDirectories)
		{
			searchDirectories(file, ftp, workspace + "/" + n.getName());
		}
	}
	
	
	
	public static void main(String[] args) 
	{
		System.out.print("Hello, World!");
		 String server = "ftp.zakupki.gov.ru"; //Server can be either host name or IP address.
	     String user = "fz223free";
	     String pass = "fz223free";

	     FTPClient ftp = new FTPClient();
	     try 
	     {
	            ftp.connect(server);
	            showServerReply(ftp);
	            int replyCode = ftp.getReplyCode();
	            if (!FTPReply.isPositiveCompletion(replyCode)) 
	            {
	                System.out.println("Operation failed. Server reply code: " + replyCode);
	                return;
	            }
	            boolean success = ftp.login(user, pass);
	            showServerReply(ftp);
	            if (!success) 
	            {
	                System.out.println("Failed to log into the server");
	                return;
	            } 
	            else
	            {
	                System.out.println("LOGGED IN SERVER");
	            }
	            
	            ftp.enterLocalPassiveMode(); //разрешить обмен данными с сервером
	            String workingDirectory = "./out/published/";
	            FTPFile[] regions = ftp.listDirectories(workingDirectory);
	            FileWriter file = new FileWriter("out.txt", true);
	            searchDirectories(file, ftp, workingDirectory);
	            System.out.println(f);
	            ftp.disconnect();
	     } 
	     catch (IOException ex) 
	     {
	            System.out.println("Oops! Something went wrong.");
	            ex.printStackTrace();
	     }
	     
		
	}

}
