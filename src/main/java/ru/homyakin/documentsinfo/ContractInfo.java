package ru.homyakin.documentsinfo;

import ru.homyakin.documentsinfo.subdocumentsinfo.ContractPositionInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.CurrencyInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.CustomerInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.PurchaseTypeInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.SupplierInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ContractInfo implements DocumentInfo{
    private PurchaseTypeInfo purchaseType;
    private String GUID;
    private BigDecimal price;
    private Optional<BigDecimal> rubPrice;
    private CurrencyInfo currency;
    private LocalDateTime createDateTime;
    private LocalDate contractDate;
    private Optional<LocalDate> startExecutionDate;
    private Optional<LocalDate> endExecutionDate;
    private CustomerInfo customer;
    private Optional<SupplierInfo> supplier;
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
        this.rubPrice = Optional.of(rubPrice);
        this.startExecutionDate = Optional.ofNullable(startExecutionDate);
        this.endExecutionDate = Optional.ofNullable(endExecutionDate);
        this.supplier = Optional.ofNullable(supplier);
        this.positions = positions;
    }

    public String getGUID() {
        return GUID;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Optional<BigDecimal> getRubPrice() {
        return rubPrice;
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
        return startExecutionDate;
    }

    public Optional<LocalDate> getEndExecutionDate() {
        return endExecutionDate;
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    public Optional<SupplierInfo> getSupplier() {
        return supplier;
    }

    public List<ContractPositionInfo> getPositions() {
        return positions;
    }

    public PurchaseTypeInfo getPurchaseType() {
        return purchaseType;
    }


}
