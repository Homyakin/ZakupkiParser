package DocumentsInfo;

import java.util.ArrayList;
import java.util.Date;
import DocumentsInfo.LotInfo;
import DocumentsInfo.CustomerInfo;
import DocumentsInfo.PurchaseInfo;
import DocumentsInfo.ProtocolInfo;
import DocumentsInfo.ContractPositionInfo;


public class ContractInfo 
{
    private String regNum;
    private String GUID;
    private String price;
    private String docStatus;
    private String changeContract; // что это?
    private String name;
    private String subject;
    private String modDescr;
    private String hasSubcontractor; //boolean - ?
    private Date startDate;
    private Date endDate;
    private Date pubDate;
    private Date date;
    private Date resumeDate;
    private int version;
    private int lotNum;
    private LotInfo lot;
    private CustomerInfo customer;
    private PurchaseInfo purchase;
    private ProtocolInfo protocol;
    private ArrayList<ContractPositionInfo> positions;

    public ContractInfo(String regNum, String GUID, String price, Date startDate, Date endDate, Date pubDate, 
    		Date date, Date resumeDate, String docStatus, int version, String changeContract, String name,
            String subject, String modDescr, String hasSubcontractor, int lotNum, LotInfo lot, CustomerInfo customer,
            PurchaseInfo purchase, ProtocolInfo protocol, ArrayList<ContractPositionInfo> positions)
    {
        this.regNum = regNum;
        this.GUID = GUID;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pubDate = pubDate;
        this.date = date;
        this.resumeDate = resumeDate;
        this.docStatus = docStatus;
        this.version = version;
        this.changeContract = changeContract;
        this.name = name;
        this.subject = subject;
        this.modDescr = modDescr;
        this.hasSubcontractor = hasSubcontractor;
        this.lotNum = lotNum;
        this.lot = new LotInfo(lot);
        this.customer = new CustomerInfo(customer);
        this.purchase = new PurchaseInfo(purchase);
        this.protocol = new ProtocolInfo(protocol);
        this.positions = new ArrayList<ContractPositionInfo>(positions);
    }
    
    public String getRegNum() { return regNum; }
    public String getGUID() { return GUID; }
    public String getPrice() { return price; }
    public String getDocStatus() { return docStatus; }
    public String getChangeContract() { return changeContract; }
    public String getName() { return name; }
    public String getSubject() { return subject; }
    public String getModDescr() { return modDescr; }  
    public String getHasSubcontractor() { return hasSubcontractor; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public Date getPubDate() { return pubDate; }
    public Date getDate() { return date; }
    public Date getResumDate() { return resumeDate; }
    public int getVersion() { return version; }
    public int getLotNum() { return lotNum;	 }
    public LotInfo getLot() { return lot; }
    public CustomerInfo getCustomer() { return customer; }
    public PurchaseInfo getPurchase() { return purchase; }
    public ProtocolInfo getProtocol() { return protocol; }
    public ArrayList<ContractPositionInfo> getPositions() { return positions; }

}
