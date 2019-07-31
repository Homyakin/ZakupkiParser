package XMLParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import DocumentsInfo.ContractInfo;
import SubDocumentsInfo.ContractPositionInfo;
import SubDocumentsInfo.CurrencyInfo;
import SubDocumentsInfo.CustomerInfo;
import SubDocumentsInfo.OKInfo;
import SubDocumentsInfo.PurchaseTypeInfo;
import SubDocumentsInfo.SupplierInfo;

public class ContractParser {
	private XMLParser processor;
	private String filePath;

	public ContractParser(String filePath) throws XMLStreamException, IOException {
		this.filePath = filePath;
		this.processor = new XMLParser(Files.newInputStream(Paths.get(filePath)));
	}

	private CustomerInfo parseCustomer() throws XMLStreamException {
		processor.findStartBlock("mainInfo");
		String fullName = null, shortName = null, INN = null, KPP = null, OGRN = null;
		CustomerInfo customer = null;
		while (processor.getNextInBlock("mainInfo")) {
			if ("fullName".equals(processor.getName()))
				fullName = processor.getText();
			else if ("shortName".equals(processor.getName()))
				shortName = processor.getText();
			else if ("inn".equals(processor.getName()))
				INN = processor.getText();
			else if ("kpp".equals(processor.getName()))
				KPP = processor.getText();
			else if ("ogrn".equals(processor.getName()))
				OGRN = processor.getText();
			else
				processor.skipBlock();
		}
		customer = new CustomerInfo(INN, KPP, OGRN);
		customer.setName(fullName);
		customer.setShortName(shortName);
		return customer;
	}

	private PurchaseTypeInfo parsePurchaseTypeInfo() throws XMLStreamException {
		String code = null;
		String name = null;
		PurchaseTypeInfo purchaseType = null;
		while (processor.getNextInBlock("purchaseTypeInfo")) {
			if ("code".equals(processor.getName()))
				code = processor.getText();
			else if ("name".equals(processor.getName()))
				name = processor.getText();
		}
		purchaseType = new PurchaseTypeInfo(code);
		purchaseType.setName(name);
		return purchaseType;
	}

	private SupplierInfo parseSupplier() throws XMLStreamException {
		String name = null, shortName = null, INN = null, type = null;
		boolean provider = false, nonResident = false;
		SupplierInfo supplier = null;
		while (processor.getNextInBlock("supplierInfo")) {
			if ("name".equals(processor.getName())) {
				name = processor.getText();
			} else if ("shortName".equals(processor.getName())) {
				shortName = processor.getText();
			} else if ("inn".equals(processor.getName())) {
				INN = processor.getText();
			} else if ("type".equals(processor.getName())) {
				type = processor.getText();
			} else if ("provider".equals(processor.getName())) {
				if (processor.getText().equals("true"))
					provider = true;
			} else if ("nonResident".equals(processor.getName())) {
				if (processor.getText().equals("true"))
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

	private CurrencyInfo parseCurrency() throws XMLStreamException {
		CurrencyInfo currency = null;
		String letterCode = null, code = null, digitalCode = null, name = null;
		while (processor.getNextInBlock("currency")) {
			if ("letterCode".equals(processor.getName())) {
				letterCode = processor.getText();
			} else if ("code".equals(processor.getName())) {
				code = processor.getText();
			} else if ("digitalCode".equals(processor.getName())) {
				digitalCode = processor.getText();
			} else if ("name".equals(processor.getName())) {
				name = processor.getText();
			} else {
				processor.skipBlock();
			}
		}
		currency = new CurrencyInfo(name);
		currency.setCode(code);
		currency.setDigitalCode(digitalCode);
		currency.setLetterCode(letterCode);
		return currency;
	}

	private OKInfo parseOKInfo() throws XMLStreamException {
		OKInfo OKDP = null;
		String code = null, name = null;
		while (processor.getNextInBlock("okdp")) {
			if ("code".equals(processor.getName())) {
				code = processor.getText();
			} else if ("name".equals(processor.getName())) {
				name = processor.getText();
			} else {
				processor.skipBlock();
			}
		}
		OKDP = new OKInfo(code);
		OKDP.setName(name);
		return OKDP;
	}

	private List<ContractPositionInfo> parseContractPositions() throws XMLStreamException {
		List<ContractPositionInfo> list = new ArrayList<ContractPositionInfo>();
		while (processor.getNextInBlock("contractPositions")) {
			if ("contractPosition".equals(processor.getName())) {
				String GUID = null, name = null, country = null, producerCountry = null;
				BigDecimal qty = null;
				Integer ordinalNumber = null;
				OKInfo OKDP = null, OKPD = null, OKPD2 = null, OKEI = null;
				ContractPositionInfo contractPosition = null;
				while (processor.getNextInBlock("contractPosition")) {
					if ("guid".equals(processor.getName())) {
						GUID = processor.getText();
					} else if ("name".equals(processor.getName())) {
						name = processor.getText();
					} else if ("ordinalNumber".equals(processor.getName())) {
						ordinalNumber = Integer.parseInt(processor.getText());
					} else if ("okdp".equals(processor.getName())) {
						OKDP = parseOKInfo();
					} else if ("okpd".equals(processor.getName())) {
						OKPD = parseOKInfo();
					} else if ("okpd2".equals(processor.getName())) {
						OKPD2 = parseOKInfo();
					} else if ("country".equals(processor.getName())) {
						//TODO country = processor.getText();
					} else if ("producerCountry".equals(processor.getName())) {
						//TODO producerCountry = processor.getText();
					} else if ("okei".equals(processor.getName())) {
						OKEI = parseOKInfo();
					} else if ("qty".equals(processor.getName())) {
						qty = new BigDecimal(processor.getText());
					} else {
						processor.skipBlock();
					}
				}
				contractPosition = new ContractPositionInfo(ordinalNumber);
				contractPosition.setGUID(GUID);
				contractPosition.setName(name);
				contractPosition.setOKDP(OKDP);
				contractPosition.setOKPD(OKPD);
				contractPosition.setOKPD2(OKPD2);
				contractPosition.setOKEI(OKEI);
				contractPosition.setQty(qty);
				contractPosition.setCountry(country);
				contractPosition.setProducerCountry(producerCountry);

				list.add(contractPosition);
			}
		}
		return list;
	}

	public ContractInfo parseContract() throws XMLStreamException, IOException {
		ContractInfo contract = null;
		processor.findStartBlock("contractData");
		String GUID = null;
		BigDecimal price = null;
		BigDecimal rubPrice = null;
		LocalDateTime createDateTime = null;
		CustomerInfo customer = null;
		SupplierInfo supplier = null;
		LocalDate contractDate = null, startExecutionDate = null, endExecutionDate = null;
		PurchaseTypeInfo purchaseType = null;
		CurrencyInfo currency = null;
		List<ContractPositionInfo> contractPositions = null;
		while (processor.getNextInBlock("contractData")) {
			if ("guid".equals(processor.getName())) {
				GUID = processor.getText();
			} else if ("createDateTime".equals(processor.getName())) {
				createDateTime = LocalDateTime.parse(processor.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			} else if ("customer".equals(processor.getName())) {
				customer = parseCustomer();
			} else if ("contractDate".equals(processor.getName())) {
				String date = processor.getText();
				try {
					contractDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
				} catch (DateTimeParseException e) {
					contractDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
				}
			} else if ("supplierInfo".equals(processor.getName())) {
				supplier = parseSupplier();
			} else if ("purchaseTypeInfo".equals(processor.getName())) {
				purchaseType = parsePurchaseTypeInfo();
			} else if ("price".equals(processor.getName())) {
				price = new BigDecimal(processor.getText());
			} else if ("rubPrice".equals(processor.getName())) {
				rubPrice = new BigDecimal(processor.getText());
			} else if ("currency".equals(processor.getName())) {
				currency = parseCurrency();
			} else if ("startExecutionDate".equals(processor.getName())) {
				String date = processor.getText();
				try {
					startExecutionDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
				} catch (DateTimeParseException e) {
					startExecutionDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
				}
			} else if ("endExecutionDate".equals(processor.getName())) {
				String date = processor.getText();
				try {
					endExecutionDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
				} catch (DateTimeParseException e) {
					endExecutionDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
				}
			} else if ("contractPositions".equals(processor.getName())) {
				contractPositions = parseContractPositions();
			} else {
				processor.skipBlock();
			}
		}

		contract = new ContractInfo(GUID, createDateTime, customer, contractDate, purchaseType, price, currency);
		contract.setRubPrice(rubPrice);
		contract.setSupplier(supplier);
		contract.setStartExecutionDate(startExecutionDate);
		contract.setEndExecutionDate(endExecutionDate);
		contract.setPositions(contractPositions);
		return contract;
	}
}
