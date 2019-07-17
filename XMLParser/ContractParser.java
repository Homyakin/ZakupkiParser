package XMLParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.xml.stream.XMLStreamException;

import DocumentsInfo.ContractInfo;
import DocumentsInfo.CustomerInfo;


public class ContractParser
{
	private XMLParser processor;
	private String filePath;
	
	public ContractParser(String filePath) throws XMLStreamException, IOException 
	{
		this.filePath = filePath;
		this.processor = new XMLParser(Files.newInputStream(Paths.get(filePath)));
	}
	
	public static CustomerInfo parseCustomer(XMLParser parser) throws XMLStreamException
	{
		parser.findBlock("customer");
		parser.findBlock("mainInfo");
		String fullName = null, shortName = null, 
				INN = null, KPP = null, OGRN = null;
		CustomerInfo customer = null;
		while(parser.getNextInBlock("mainInfo"))
		{
			parser.getName();
			if("fullName".equals(parser.getName()))
				fullName = parser.getText();
			else if("shortName".equals(parser.getName()))
				shortName = parser.getText();
			else if("inn".equals(parser.getName()))
				INN = parser.getText();
			else if("kpp".equals(parser.getName()))
				KPP = parser.getText();
			else if("ogrn".equals(parser.getName()))
				OGRN = parser.getText();
		}
		customer = new CustomerInfo(INN, KPP, OGRN);
		customer.setName(fullName);
		customer.setShortName(shortName);
		return customer;
	}
	
	public ContractInfo parse() throws XMLStreamException, IOException
	{
		processor.findBlock("contractData");
		processor.findBlock("guid");
		String GUID = processor.getText();
		processor.findBlock("createDateTime");
		LocalDate createDateTime = LocalDate.parse(processor.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		System.out.println(createDateTime);
		CustomerInfo customer = parseCustomer(processor);
		processor.findBlock("contractDate");
		LocalDate contractDate = LocalDate.parse(processor.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
		processor.findBlock("purchaseTypeInfo");
		System.out.println(processor.getName());
		processor.findBlock("code");
		System.out.println(processor.getText());
		processor.findBlock("price");
		System.out.println(processor.getName());
		System.out.println(processor.getText());
		processor.findBlock("currency");
		System.out.println(processor.getName());
		processor.findBlock("name");
		System.out.println(processor.getText());
		
		return null;
	}
}
