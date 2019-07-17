package DocumentsInfo;

import java.util.ArrayList;
import java.util.Date;
import DocumentsInfo.CustomerInfo;
import DocumentsInfo.ContractPositionInfo;


public class ContractInfo 
{
    private String purchaseType;
    private String GUID;
    private String price;
    private String rubPrice;
    private String currency;
    private Date createDateTime;
    private Date contractDate;
    private CustomerInfo customer;
    private SupplierInfo supplier;
    private ArrayList<ContractPositionInfo> positions;

    public ContractInfo(String GUID, String price, Date createDateTime, Date contractDate, CustomerInfo customer, 
    		String purchaseType, String currency)
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

	public Date getCreateDateTime() { return createDateTime; }

	public void setCreateDateTime(Date createDateTime) { this.createDateTime = createDateTime; }

	public Date getContractDate() { return contractDate; }

	public void setContractDate(Date contractDate) { this.contractDate = contractDate; }

	public CustomerInfo getCustomer() { return customer; }

	public void setCustomer(CustomerInfo customer) { this.customer = customer; }

	public SupplierInfo getSupplier() { return supplier; }

	public void setSupplier(SupplierInfo supplier) { this.supplier = supplier; }

	public ArrayList<ContractPositionInfo> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<ContractPositionInfo> positions) {
		this.positions = positions;
	}

	public String getPurchaseType() { return purchaseType; }

	public void setPurchaseType(String purchaseType) { this.purchaseType = purchaseType; }

}
