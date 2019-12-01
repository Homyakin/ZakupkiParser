package ru.homyakin.zakupki.documentsinfo;

import ru.homyakin.zakupki.documentsinfo._223fz.contract.Contract;
import ru.homyakin.zakupki.documentsinfo._223fz.contract.ContractDataType;
import ru.homyakin.zakupki.documentsinfo._223fz.contract.PositionType;
import ru.homyakin.zakupki.documentsinfo._223fz.contract.SupplierMainType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.CurrencyType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.CustomerMainInfoType;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ContractInfo implements DocumentInfo {

    private ContractDataType contractData;

    public ContractInfo(Contract contract) {
        this.contractData = contract.getBody().getItem().getContractData();
    }

    public String getGUID() {
        return contractData.getGuid();
    }

    public BigDecimal getPrice() {
        return contractData.getPrice();
    }

    public Optional<BigDecimal> getRubPrice() {
        return Optional.ofNullable(contractData.getRubPrice());
    }


    public CurrencyType getCurrency() {
        return contractData.getCurrency();
    }

    public LocalDate getCreateDateTime() {
        return convertFromXMLGregorianCalendartoLocalDate(contractData.getCreateDateTime());
    }

    public LocalDate getContractDate() {
        return convertFromXMLGregorianCalendartoLocalDate(contractData.getContractDate());
    }

    public LocalDate getStartExecutionDate() {

        return convertFromXMLGregorianCalendartoLocalDate(contractData.getStartExecutionDate());
    }

    public LocalDate getEndExecutionDate() {
        return convertFromXMLGregorianCalendartoLocalDate(contractData.getEndExecutionDate());
    }

    public CustomerMainInfoType getCustomer() {
        return contractData.getCustomer().getMainInfo();
    }

    public List<SupplierMainType> getSupplier() {
        return contractData.getSupplierInfo();
    }

    public List<PositionType> getPositions() {
        return contractData.getContractPositions().getContractPosition();
    }

    public ContractDataType.PurchaseTypeInfo getPurchaseType() {
        return contractData.getPurchaseTypeInfo();
    }

    private LocalDate convertFromXMLGregorianCalendartoLocalDate(XMLGregorianCalendar xmlTime) {
        return LocalDate.of(xmlTime.getYear(), xmlTime.getMonth(), xmlTime.getDay());
    }

}
