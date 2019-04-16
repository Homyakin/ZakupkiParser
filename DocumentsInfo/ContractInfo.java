package DocumentsInfo;

import java.util.ArrayList;
import java.util.Date;
import DocumentsInfo.LotInfo;
import DocumentsInfo.CustomerInfo;
import DocumentsInfo.PurchaseInfo;
import DocumentsInfo.ProtocolInfo;
import DocumentsInfo.ContractPositionInfo;


public class ContractInfo {
    private String RegNum;
    private String GUID;
    private String Price;
    private Date StartDate;
    private Date EndDate;
    private Date PubDate;
    private Date Date;
    private Date ResumDate;
    private String DocStatus;
    private int Version;
    private String ChangeContract;
    private String Name;
    private String Subject;
    private String ModDescr;
    private String HasSubcontractor;
    private int LotNum;
    private LotInfo Lot;
    private CustomerInfo Customer;
    private PurchaseInfo Purchase;
    private ProtocolInfo Protocol;
    private ArrayList<ContractPositionInfo> Positions;

    public ContractInfo(String RegNum, String GUID, String Price, Date StartDate, Date EndDate, Date PubDate, 
    		Date Date, Date ResumDate, String DocStatus, int Version, String ChangeContract, String Name,
            String Subject, String ModDescr, String HasSubcontractor, int LotNum, LotInfo Lot, CustomerInfo Customer,
            PurchaseInfo Purchase, ProtocolInfo Protocol, ArrayList<ContractPositionInfo> Positions)
    {
        this.RegNum = RegNum;
        this.GUID = GUID;
        this.Price = Price;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.PubDate = PubDate;
        this.Date = Date;
        this.ResumDate = ResumDate;
        this.DocStatus = DocStatus;
        this.Version = Version;
        this.ChangeContract = ChangeContract;
        this.Name = Name;
        this.Subject = Subject;
        this.ModDescr = ModDescr;
        this.HasSubcontractor = HasSubcontractor;
        this.LotNum = LotNum;
        this.Lot = new LotInfo(Lot);
        this.Customer = new CustomerInfo(Customer);
        this.Purchase = new PurchaseInfo(Purchase);
        this.Protocol = new ProtocolInfo(Protocol);
        this.Positions = new ArrayList<ContractPositionInfo>(Positions);
    }
    public String getRegNum()           { return RegNum;			}
    public String getGUID()             { return GUID;  		   	}
    public String getPrice()            { return Price;           	}
    public String getDocStatus()        { return DocStatus; 	   	}
    public String getChangeContract()   { return ChangeContract;  	}
    public String getName()             { return Name;            	}
    public String getSubject()          { return Subject;         	}
    public String getModDescr()         { return ModDescr;        	}  
    public String getHasSubcontractor() { return HasSubcontractor;	}
    public Date getStartDate()	 		{ return StartDate;			}
    public Date getEndDate()    		{ return EndDate;  			}
    public Date getPubDate()   			{ return PubDate;  			}
    public Date getDate()       		{ return Date;     			}
    public Date getResumDate()  		{ return ResumDate;			}
    public int getVersion()				{ return Version;			}
    public int getLotNum()     			{ return LotNum;			}
    public LotInfo getLot()				{ return Lot;				}
    public CustomerInfo getCustomer()	{ return Customer; 			}
    public PurchaseInfo getPurchase()  	{ return Purchase; 			}
    public ProtocolInfo getProtocol()  	{ return Protocol; 			}
    public ArrayList<ContractPositionInfo> getPositions() { return Positions; }

}
