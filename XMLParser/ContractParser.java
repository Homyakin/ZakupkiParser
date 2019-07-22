package XMLParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.stream.XMLStreamException;

import DocumentsInfo.ContractInfo;
import DocumentsInfo.Currency;
import DocumentsInfo.CustomerInfo;
import DocumentsInfo.PurchaseTypeInfo;
import DocumentsInfo.SupplierInfo;

public class ContractParser
{
	private XMLParser processor;
	private String filePath;
	
	public ContractParser(String filePath) throws XMLStreamException, IOException {
		this.filePath = filePath;
		this.processor = new XMLParser(Files.newInputStream(Paths.get(filePath)));
	}
	
	public CustomerInfo parseCustomer() throws XMLStreamException {
		processor.findStartBlock("mainInfo");
		String fullName = null, shortName = null, 
				INN = null, KPP = null, OGRN = null;
		CustomerInfo customer = null;
		while(processor.getNextInBlock("mainInfo")) {
			if("fullName".equals(processor.getName()))
				fullName = processor.getText();
			else if("shortName".equals(processor.getName()))
				shortName = processor.getText();
			else if("inn".equals(processor.getName()))
				INN = processor.getText();
			else if("kpp".equals(processor.getName()))
				KPP = processor.getText();
			else if("ogrn".equals(processor.getName()))
				OGRN = processor.getText();
			else
				processor.skipBlock();
		}
		customer = new CustomerInfo(INN, KPP, OGRN);
		customer.setName(fullName);
		customer.setShortName(shortName);
		return customer;
	}
	
	public PurchaseTypeInfo parsePurchaseTypeInfo() throws XMLStreamException {
		Integer code = null; 
		String name = null;
		PurchaseTypeInfo purchaseType = null;
		while(processor.getNextInBlock("purchaseTypeInfo")) {
			if("code".equals(processor.getName()))
				code = Integer.parseInt(processor.getText());
			else if("name".equals(processor.getName()))
				name = processor.getText();
		}
		purchaseType = new PurchaseTypeInfo(code);
		purchaseType.setName(name);
		return purchaseType;
	}
	
	public SupplierInfo parseSupplier() throws XMLStreamException
	{
		String name = null, shortName = null, 
				INN = null, type = null;
		boolean provider = false, nonResident = false;
		SupplierInfo supplier = null;
		while(processor.getNextInBlock("supplierInfo"))
		{
			if("name".equals(processor.getName())) {
				name = processor.getText();
			} else if("shortName".equals(processor.getName())) {
				shortName = processor.getText();
			} else if("inn".equals(processor.getName())) {
				INN = processor.getText();
			} else if("type".equals(processor.getName())) {
				type = processor.getText();
			} else if("provider".equals(processor.getName())) {
				if(processor.getText().equals("true"))
					provider = true;
			} else if("nonResident".equals(processor.getName())) {
				if(processor.getText().equals("true"))
					nonResident = true;
			} else {
				processor.skipBlock();
			}
		}
		supplier = new SupplierInfo(name, type, provider, nonResident);
		supplier.setShortName(shortName);
		supplier.setINN(INN);
		return supplier;
	}
	
	public Currency parseCurrency() throws XMLStreamException {
		Currency currency = null;
		String letterCode = null, code = null, digitalCode = null,
				name = null;
		while(processor.getNextInBlock("currency")) {
			if("letterCode".equals(processor.getName())) {
				letterCode = processor.getText();
			} else if("code".equals(processor.getName())) {
				code = processor.getText();
			} else if("digitalCode".equals(processor.getName())) {
				digitalCode = processor.getText();
			} else if("name".equals(processor.getName())) {
				name = processor.getText();
			} else {
				processor.skipBlock();
			}
		}
		currency = new Currency(name);
		currency.setCode(code);
		currency.setDigitalCode(digitalCode);
		currency.setLetterCode(letterCode);
		return currency;
	}
	
	public ContractInfo parseContract() throws XMLStreamException, IOException {
		ContractInfo contract = null;
		processor.findStartBlock("contractData");
		String GUID = null, price = null, rubPrice = null;
		LocalDateTime createDateTime = null;
		CustomerInfo customer = null;
		SupplierInfo supplier = null;
		LocalDate contractDate = null, startExecutionDate = null, endExecutionDate = null;
		PurchaseTypeInfo purchaseType = null;
		Currency currency = null;
		processor.findStartBlock("price");
		System.out.println(processor.getName());
		System.out.println(processor.getText());
		processor.findStartBlock("currency");
		System.out.println(processor.getName());
		processor.findStartBlock("name");
		System.out.println(processor.getText());
		while(processor.getNextInBlock("contractData"))
		{
			if("guid".equals(processor.getName())) {
				GUID = processor.getText();
			} else if("createDateTime".equals(processor.getName())) {
				createDateTime = LocalDateTime.parse(processor.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			} else if("customer".equals(processor.getName())) {
				customer = parseCustomer();
			} else if("contractDate".equals(processor.getName())) {
				contractDate = LocalDate.parse(processor.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
			}else if("supplierInfo".equals(processor.getName())) {
				supplier = parseSupplier();
			}else if("purchaseTypeInfo".equals(processor.getName())) {
				purchaseType = parsePurchaseTypeInfo();
			}else if("price".equals(processor.getName())) {
				price = processor.getText();
			} else if("rubPrice".equals(processor.getName())) { 
				rubPrice = processor.getText();
			} else if("currency".equals(processor.getName())) {
				currency = parseCurrency();
			} else if("startExecutionDate".equals(processor.getName())) {
				startExecutionDate = LocalDate.parse(processor.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
			} else if("endExecutionDate".equals(processor.getName())) {
				endExecutionDate = LocalDate.parse(processor.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
			} else if("contractPositions".equals(processor.getName())) {
				//TODO
			} else {
				processor.skipBlock();
			}
		}
		
		contract = new ContractInfo(GUID, createDateTime, customer, contractDate, purchaseType, price, currency);
		return contract;
	}
}