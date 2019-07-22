package DocumentsInfo;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

import SubDocumentsInfo.ContractPositionInfo;
import SubDocumentsInfo.CurrencyInfo;
import SubDocumentsInfo.CustomerInfo;
import SubDocumentsInfo.PurchaseTypeInfo;
import SubDocumentsInfo.SupplierInfo;


public class ContractInfo 
{
    private PurchaseTypeInfo purchaseType;
    private String GUID;
    private String price;
    private String rubPrice;
    private CurrencyInfo currency;
    private LocalDateTime createDateTime;
    private LocalDate contractDate;
    private LocalDate startExecutionDate;
    private LocalDate endExecutionDate;
    private CustomerInfo customer;
    private SupplierInfo supplier;
    private List<ContractPositionInfo> positions;

    public ContractInfo(String GUID, LocalDateTime createDateTime, CustomerInfo customer, 
    		LocalDate contractDate, PurchaseTypeInfo purchaseType, String price, CurrencyInfo currency)
    {
        this.setGUID(GUID);
        this.setPrice(price);
        this.setCreateDateTime(createDateTime);
        this.setContractDate(contractDate);
        this.setPurchaseType(purchaseType);
        this.setCurrency(currency);
        this.setCustomer(new CustomerInfo(customer));
    }

	public String getGUID() { return GUID; }

	public void setGUID(String gUID) { GUID = gUID; }

	public String getPrice() { return price; }

	public void setPrice(String price) { this.price = price; }

	public String getRubPrice() { return rubPrice; }

	public void setRubPrice(String rubPrice) { this.rubPrice = rubPrice; }

	public CurrencyInfo getCurrency() { return currency; }

	public void setCurrency(CurrencyInfo currency) { this.currency = currency; }

	public LocalDateTime getCreateDateTime() { return createDateTime; }

	public void setCreateDateTime(LocalDateTime createDateTime) { this.createDateTime = createDateTime; }

	public LocalDate getContractDate() { return contractDate; }

	public void setContractDate(LocalDate contractDate) { this.contractDate = contractDate; }

	public LocalDate getStartExecutionDate() { return startExecutionDate; }

	public void setStartExecutionDate(LocalDate startExecutionDate) { this.startExecutionDate = startExecutionDate; }

	public LocalDate getEndExecutionDate() { return endExecutionDate; }

	public void setEndExecutionDate(LocalDate endExecutionDate) { this.endExecutionDate = endExecutionDate; }

	public CustomerInfo getCustomer() { return customer; }

	public void setCustomer(CustomerInfo customer) { this.customer = customer; }

	public SupplierInfo getSupplier() { return supplier; }

	public void setSupplier(SupplierInfo supplier) { this.supplier = supplier; }

	public List<ContractPositionInfo> getPositions() { return positions; }

	public void setPositions(List<ContractPositionInfo> positions) {this.positions = positions;}

	public PurchaseTypeInfo getPurchaseType() { return purchaseType; }

	public void setPurchaseType(PurchaseTypeInfo purchaseType) { this.purchaseType = purchaseType; }
	

}
