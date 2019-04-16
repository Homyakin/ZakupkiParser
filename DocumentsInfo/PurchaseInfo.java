package DocumentsInfo;

import java.util.Date;
import java.util.ArrayList;
import DocumentsInfo.CustomerInfo;
import DocumentsInfo.LotInfo;

public class PurchaseInfo {
    private String Number;
    private String Name;
    private String GUID;
    private String Type;
    private String ChoiceWay;
    private String ChoiceWayCode;
    private String Region;
    private String Stage;
    private Date BirnDate;
    private Date RefreshDate;
    private Date StartApplicationDate;
    private Date EndApplicationDate;
    private int Version;
    private boolean JointPurchase;
    private CustomerInfo Customer;
    private ArrayList<LotInfo> Lots;

    public PurchaseInfo(String Number, String Name, String GUID, Date BirnDate, Date RefreshDate, Date StartApplicationDate, Date EndApplicationDate, String Type, String ChoiceWay, String ChoiceWayCode,
                        String Region, String Stage, int Version, boolean JointPurchase, CustomerInfo Customer, ArrayList<LotInfo> Lots)
    {
        this.Number = Number;
        this.Name = Name;
        this.GUID = GUID;
        this.BirnDate = BirnDate;
        this.RefreshDate = RefreshDate;
        this.StartApplicationDate = StartApplicationDate;
        this.EndApplicationDate = EndApplicationDate;
        this.Type = Type;
        this.ChoiceWay = ChoiceWay;
        this.ChoiceWayCode = ChoiceWayCode;
        this.Region = Region;
        this.Stage = Stage;
        this.Version = Version;
        this.JointPurchase = JointPurchase;
        this.Customer = new CustomerInfo(Customer);
        this.Lots = new ArrayList<LotInfo>(Lots);
    }

    public PurchaseInfo(PurchaseInfo Purchase)
    {
        this.Number = Purchase.Number;
        this.Name = Purchase.Name;
        this.GUID = Purchase.GUID;
        this.BirnDate = Purchase.BirnDate;
        this.RefreshDate = Purchase.RefreshDate;
        this.StartApplicationDate = Purchase.StartApplicationDate;
        this.EndApplicationDate = Purchase.EndApplicationDate;
        this.Type = Purchase.Type;
        this.ChoiceWay = Purchase.ChoiceWay;
        this.ChoiceWayCode = Purchase.ChoiceWayCode;
        this.Region = Purchase.Region;
        this.Stage = Purchase.Stage;
        this.Version = Purchase.Version;
        this.JointPurchase = Purchase.JointPurchase;
        this.Customer = new CustomerInfo(Purchase.Customer);
        this.Lots = new ArrayList<LotInfo>(Purchase.Lots);
    }
    public String getNumber()             { return Number;              }
    public String getName()               { return Name;                }
    public String getGUID()               { return GUID;                }
    public String getType()               { return Type;                }
    public String getChoiceWay()          { return ChoiceWay;           }
    public String getChoiceWayCode()      { return ChoiceWayCode;       }
    public String getRegion()             { return Region;              }
    public String getStage()              { return Stage;               }
    public Date getBirnDate()             { return BirnDate;            }
    public Date getRefreshDate()          { return RefreshDate;         }
    public Date getStartApplicationDate() { return StartApplicationDate;}
    public Date getEndApplicationDate()   { return EndApplicationDate;  }
    public int getVersion()				  { return Version;			    }
    public boolean getJointPurchase()     { return JointPurchase;       }
    public CustomerInfo getCustomer()     { return Customer;            }
    public ArrayList<LotInfo> getLots()   { return Lots;                }
}
