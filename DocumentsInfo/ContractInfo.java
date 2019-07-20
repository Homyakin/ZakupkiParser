package DocumentsInfo;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

import DocumentsInfo.CustomerInfo;
import DocumentsInfo.ContractPositionInfo;
import DocumentsInfo.PurchaseTypeInfo;

public class ContractInfo 
{
    private PurchaseTypeInfo purchaseType;
    private String GUID;
    private String price;
    private String rubPrice;
    private String currency;
    private LocalDateTime createDateTime;
    private LocalDate contractDate;
    private CustomerInfo customer;
    private SupplierInfo supplier;
    private List<ContractPositionInfo> positions;

    public ContractInfo(String GUID, String price, LocalDateTime createDateTime, LocalDate contractDate, CustomerInfo customer, 
    		PurchaseTypeInfo purchaseType, String currency)
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

	public String getCurrency() { return currency; }

	public void setCurrency(String currency) { this.currency = currency; }

	public LocalDateTime getCreateDateTime() { return createDateTime; }

	public void setCreateDateTime(LocalDateTime createDateTime) { this.createDateTime = createDateTime; }

	public LocalDate getContractDate() { return contractDate; }

	public void setContractDate(LocalDate contractDate) { this.contractDate = contractDate; }

	public CustomerInfo getCustomer() { return customer; }

	public void setCustomer(CustomerInfo customer) { this.customer = customer; }

	public SupplierInfo getSupplier() { return supplier; }

	public void setSupplier(SupplierInfo supplier) { this.supplier = supplier; }

	public List<ContractPositionInfo> getPositions() { return positions; }

	public void setPositions(List<ContractPositionInfo> positions) {this.positions = positions;}

	public PurchaseTypeInfo getPurchaseType() { return purchaseType; }

	public void setPurchaseType(PurchaseTypeInfo purchaseType) { this.purchaseType = purchaseType; }
	

}
