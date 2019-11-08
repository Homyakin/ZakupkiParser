package ru.homyakin.service.parser;

import ru.homyakin.documentsinfo.ContractInfo;
import ru.homyakin.documentsinfo.DocumentInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.ContractPositionInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.CurrencyInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.CustomerInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.OKInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.PurchaseTypeInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.SupplierInfo;
import ru.homyakin.exceptions.FileIsEmptyException;
import ru.homyakin.service.parser.XMLParser;
import ru.homyakin.service.parser.interfaces.DocumentParser;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ContractParser implements DocumentParser {
    private XMLParser processor;
    private String filePath;

    public ContractParser(String filePath) throws XMLStreamException, IOException {
        this.filePath = filePath;
        Path file = Paths.get(filePath);
        if (file.toFile().length() == 0) {
            throw new FileIsEmptyException(filePath);
        }
        this.processor = new XMLParser(Files.newInputStream(file));
    }

    private CustomerInfo parseCustomer() throws XMLStreamException {
        processor.findStartBlock("mainInfo");
        String fullName = null, shortName = null, INN = null, KPP = null, OGRN = null;
        CustomerInfo customer;
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
        customer = new CustomerInfo(fullName, shortName, INN, KPP, OGRN);
        return customer;
    }

    private PurchaseTypeInfo parsePurchaseTypeInfo() throws XMLStreamException {
        String code = null;
        String name = null;
        PurchaseTypeInfo purchaseType;
        while (processor.getNextInBlock("purchaseTypeInfo")) {
            if ("code".equals(processor.getName()))
                code = processor.getText();
            else if ("name".equals(processor.getName()))
                name = processor.getText();
        }
        purchaseType = new PurchaseTypeInfo(code, name);
        return purchaseType;
    }

    private SupplierInfo parseSupplier() throws XMLStreamException {
        String name = null, shortName = null, INN = null, type = null;
        boolean provider = false, nonResident = false;
        SupplierInfo supplier;
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
        supplier = new SupplierInfo(name, shortName, INN, type, provider, nonResident);
        return supplier;
    }

    private CurrencyInfo parseCurrency() throws XMLStreamException {
        CurrencyInfo currency;
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
        currency = new CurrencyInfo(letterCode, code, digitalCode, name);
        return currency;
    }

    private OKInfo parseOKInfo(String OKType) throws XMLStreamException {
        //TODO create enum
        OKInfo OKDP;
        String code = null, name = null;
        while (processor.getNextInBlock(OKType)) {
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
        List<ContractPositionInfo> list = new ArrayList<>();
        while (processor.getNextInBlock("contractPositions")) {
            if ("contractPosition".equals(processor.getName())) {
                String GUID = null, name = null, country = null, producerCountry = null;
                BigDecimal qty = null;
                Integer ordinalNumber = null;
                OKInfo OKDP = null, OKPD = null, OKPD2 = null, OKEI = null;
                ContractPositionInfo contractPosition;
                while (processor.getNextInBlock("contractPosition")) {
                    if ("guid".equals(processor.getName())) {
                        GUID = processor.getText();
                    } else if ("name".equals(processor.getName())) {
                        name = processor.getText();
                    } else if ("ordinalNumber".equals(processor.getName())) {
                        ordinalNumber = Integer.parseInt(processor.getText());
                    } else if ("okdp".equals(processor.getName())) {
                        OKDP = parseOKInfo("okdp");
                    } else if ("okpd".equals(processor.getName())) {
                        OKPD = parseOKInfo("okpd");
                    } else if ("okpd2".equals(processor.getName())) {
                        OKPD2 = parseOKInfo("okpd2");
                    } else if ("country".equals(processor.getName())) {
                        //TODO country = processor.getText();
                    } else if ("producerCountry".equals(processor.getName())) {
                        //TODO producerCountry = processor.getText();
                    } else if ("okei".equals(processor.getName())) {
                        OKEI = parseOKInfo("okei");
                    } else if ("qty".equals(processor.getName())) {
                        qty = new BigDecimal(processor.getText());
                    } else {
                        processor.skipBlock();
                    }
                }
                contractPosition = new ContractPositionInfo(GUID, name, ordinalNumber, OKDP, OKPD, OKPD2, OKEI, qty);
                list.add(contractPosition);
            }
        }
        return list;
    }



    public DocumentInfo parse() throws XMLStreamException {
        ContractInfo contract;
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

        contract = new ContractInfo(GUID, createDateTime, customer, contractDate, purchaseType, price, currency,
                rubPrice, startExecutionDate, endExecutionDate, supplier, contractPositions);
        return contract;
    }
}
