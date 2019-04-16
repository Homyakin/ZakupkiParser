package DocumentsInfo;

import DocumentsInfo.LotItemInfo;
import DocumentsInfo.CustomerInfo;
import DocumentsInfo.SupplierInfo;
import java.util.ArrayList;

public class LotInfo {
    private String GUID;
    private String Name;
    private int Number;
    private String NMC;
    private String NMCInfo;
    private boolean JointPurchase;
    private ArrayList<LotItemInfo> LotItems;
    private ArrayList<CustomerInfo> Customers;
    private ArrayList<SupplierInfo> Suppliers;

    public LotInfo(String GUID, String Name, int Number, String NMC, String NMCInfo, boolean JointPurchase, ArrayList<LotItemInfo> LotItems, ArrayList<CustomerInfo> Customers,
                   ArrayList<SupplierInfo> Suppliers)
    {
        this.GUID = GUID;
        this.Name = Name;
        this.Number = Number;
        this.NMC = NMC;
        this.NMCInfo = NMCInfo;
        this.JointPurchase = JointPurchase;
        this.LotItems = new ArrayList<LotItemInfo>(LotItems);
        this.Customers = new ArrayList<CustomerInfo>(Customers);
        this.Suppliers = new ArrayList<SupplierInfo>(Suppliers);
    }

    public LotInfo(LotInfo Lot)
    {
        this.GUID = Lot.GUID;
        this.Name = Lot.Name;
        this.Number = Lot.Number;
        this.NMC = Lot.NMC;
        this.NMCInfo = Lot.NMCInfo;
        this.JointPurchase = Lot.JointPurchase;
        this.LotItems = new ArrayList<LotItemInfo>(Lot.LotItems);
        this.Customers = new ArrayList<CustomerInfo>(Lot.Customers);
        this.Suppliers = new ArrayList<SupplierInfo>(Lot.Suppliers);
    }
    
    public String getGUID()                        { return GUID;	       }
    public String getName()                        { return Name;	       }
    public String getNMC()                         { return NMC;           }
    public String getNMCInfo()                     { return NMCInfo;       }
    public int getNumber()	                       { return Number;        }
    public boolean getJointPurchase() 			   { return JointPurchase; }
    public ArrayList<LotItemInfo> getLotItems()    { return LotItems;      }
    public ArrayList<CustomerInfo> getCustomers()  { return Customers;     }
    public ArrayList<SupplierInfo> getSuppliers()  { return Suppliers;     }
}   
