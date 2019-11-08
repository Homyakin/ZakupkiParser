package ru.homyakin.documentsinfo;

import ru.homyakin.documentsinfo.subdocumentsinfo.ContractPositionInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.CurrencyInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.CustomerInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.PurchaseTypeInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.SupplierInfo;
import ru.homyakin.service.parser.adapters.DateAdapter;
import ru.homyakin.service.parser.adapters.DateTimeAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@XmlRootElement(name = "contractData", namespace = "http://zakupki.gov.ru/223fz/contract/1")
public class ContractInfo implements DocumentInfo{
    @XmlElement(name = "guid", namespace = "http://zakupki.gov.ru/223fz/contract/1")
    private String GUID;
    @XmlElement(name = "price", namespace = "http://zakupki.gov.ru/223fz/contract/1")
    private BigDecimal price;
    @XmlElement(name = "rubPrice", namespace = "http://zakupki.gov.ru/223fz/contract/1")
    private BigDecimal rubPrice;
    @XmlElement(name = "createDateTime", namespace = "http://zakupki.gov.ru/223fz/contract/1")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private LocalDateTime createDateTime;
    @XmlElement(name = "contractDate", namespace = "http://zakupki.gov.ru/223fz/contract/1")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private LocalDate contractDate;
    @XmlElement(name = "startExecutionDate", namespace = "http://zakupki.gov.ru/223fz/contract/1")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private LocalDate startExecutionDate;
    @XmlElement(name = "endExecutionDate", namespace = "http://zakupki.gov.ru/223fz/contract/1")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private LocalDate endExecutionDate;
    @XmlElement(name = "customer", namespace = "http://zakupki.gov.ru/223fz/contract/1")
    private CustomerInfo customer;
    @XmlElement(name = "purchaseTypeInfo", namespace = "http://zakupki.gov.ru/223fz/contract/1")
    private PurchaseTypeInfo purchaseType;
    private SupplierInfo supplier;
    private CurrencyInfo currency;
    private List<ContractPositionInfo> positions;

    public ContractInfo(String GUID, LocalDateTime createDateTime, CustomerInfo customer, LocalDate contractDate,
                        PurchaseTypeInfo purchaseType, BigDecimal price, CurrencyInfo currency, BigDecimal rubPrice,
                        LocalDate startExecutionDate, LocalDate endExecutionDate, SupplierInfo supplier,
                        List<ContractPositionInfo> positions) {
        this.GUID = GUID;
        this.price = price;
        this.createDateTime = createDateTime;
        this.contractDate = contractDate;
        this.purchaseType = purchaseType;
        this.currency = currency;
        this.customer = customer;
        this.rubPrice = rubPrice;
        this.startExecutionDate = startExecutionDate;
        this.endExecutionDate = endExecutionDate;
        this.supplier = supplier;
        this.positions = positions;
    }

    public String getGUID() {
        return GUID;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Optional<BigDecimal> getRubPrice() {
        return Optional.ofNullable(rubPrice);
    }


    public CurrencyInfo getCurrency() {
        return currency;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public Optional<LocalDate> getStartExecutionDate() {
        return Optional.ofNullable(startExecutionDate);
    }

    public Optional<LocalDate> getEndExecutionDate() {
        return Optional.ofNullable(endExecutionDate);
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    public Optional<SupplierInfo> getSupplier() {
        return Optional.ofNullable(supplier);
    }

    public List<ContractPositionInfo> getPositions() {
        return positions;
    }

    public PurchaseTypeInfo getPurchaseType() {
        return purchaseType;
    }


}
