package XMLParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.stream.XMLStreamException;

public class ContractParser 
{
	private XMLParser parser;
	private String filePath;
	
	public ContractParser(String filePath) throws XMLStreamException, IOException 
	{
		this.filePath = filePath;
		this.parser = new XMLParser(Files.newInputStream(Paths.get(filePath)));
	}
	
	public void parse() throws XMLStreamException
	{
		parser.findBlock("contractData");
		
	}
}
