package DocumentsInfo;

import java.util.Date;
import java.util.ArrayList;
import DocumentsInfo.CustomerInfo;
import DocumentsInfo.LotInfo;

public class PurchaseInfo 
{
    private String number;
    private String name;
    private String GUID;
    private String type;
    private String choiceWay;
    private String choiceWayCode;
    private String region;
    private String stage;
    private Date birnDate;
    private Date refreshDate;
    private Date startApplicationDate;
    private Date endApplicationDate;
    private int version;
    private boolean jointPurchase;
    private CustomerInfo customer;
    private ArrayList<LotInfo> lots;

    public PurchaseInfo(String number, String name, String GUID, Date birnDate, Date refreshDate, Date startApplicationDate, Date endApplicationDate, String type, String choiceWay, String choiceWayCode,
                        String region, String stage, int version, boolean jointPurchase, CustomerInfo customer, ArrayList<LotInfo> lots)
    {
        this.number = number;
        this.name = name;
        this.GUID = GUID;
        this.birnDate = birnDate;
        this.refreshDate = refreshDate;
        this.startApplicationDate = startApplicationDate;
        this.endApplicationDate = endApplicationDate;
        this.type = type;
        this.choiceWay = choiceWay;
        this.choiceWayCode = choiceWayCode;
        this.region = region;
        this.stage = stage;
        this.version = version;
        this.jointPurchase = jointPurchase;
        this.lots = new ArrayList<LotInfo>(lots);
    }

    public PurchaseInfo(PurchaseInfo Purchase)
    {
        this.number = Purchase.number;
        this.name = Purchase.name;
        this.GUID = Purchase.GUID;
        this.birnDate = Purchase.birnDate;
        this.refreshDate = Purchase.refreshDate;
        this.startApplicationDate = Purchase.startApplicationDate;
        this.endApplicationDate = Purchase.endApplicationDate;
        this.type = Purchase.type;
        this.choiceWay = Purchase.choiceWay;
        this.choiceWayCode = Purchase.choiceWayCode;
        this.region = Purchase.region;
        this.stage = Purchase.stage;
        this.version = Purchase.version;
        this.jointPurchase = Purchase.jointPurchase;
        this.lots = new ArrayList<LotInfo>(Purchase.lots);
    }
    
    public String getNumber() { return number; }
    public String getName() { return name; }
    public String getGUID() { return GUID; }
    public String getType() { return type; }
    public String getChoiceWay() { return choiceWay; }
    public String getChoiceWayCode() { return choiceWayCode; }
    public String getRegion() { return region; }
    public String getStage() { return stage; }
    public Date getBirnDate() { return birnDate; }
    public Date getRefreshDate() { return refreshDate; }
    public Date getStartApplicationDate() { return startApplicationDate; }
    public Date getEndApplicationDate() { return endApplicationDate; }
    public int getVersion() { return version; }
    public boolean getJointPurchase() { return jointPurchase; }
    public CustomerInfo getCustomer() { return customer; }
    public ArrayList<LotInfo> getLots() { return lots; }
}
