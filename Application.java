
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


public class Application
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
	     try 
	     {
	                  
	            FileWriter file = new FileWriter("out.txt", true);
	            searchDirectories(file, ftp, workingDirectory);
	     } 
	     catch (IOException ex) 
	     {
	            System.out.println("Oops! Something went wrong.");
	            ex.printStackTrace();
	     }
	     
		
	}

}
