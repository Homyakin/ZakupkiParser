package DocumentsInfo;

import java.util.Date;
import DocumentsInfo.LotInfo;
import java.util.ArrayList;

public class ProtocolInfo {
    private String GUID;
    private Date Date;
    private String RegNum;
    private int Version;
    private String Type;
    private String PurchaseRegNum;
    private String PurchaseGUID;
    private ArrayList<LotInfo> Lots;
    private String MissedContest;
    private String MissedReason;

    public ProtocolInfo(String GUID, Date Date, String RegNum, int Version, String Type, String PurchaseRegNum, String PurchaseGUID, ArrayList<LotInfo> Lots, String MissedContest, String MissedReason)
    {
        this.GUID = GUID;
        this.Date = Date;
        this.RegNum = RegNum;
        this.Version = Version;
        this.Type = Type;
        this.PurchaseRegNum = PurchaseRegNum;
        this.PurchaseGUID = PurchaseGUID;
        this.Lots = new ArrayList<LotInfo>(Lots);
        this.MissedContest = MissedContest;
        this.MissedReason = MissedReason;
    }

    public ProtocolInfo(ProtocolInfo Protocol)
    {
        this.GUID = Protocol.GUID;
        this.Date = Protocol.Date;
        this.RegNum = Protocol.RegNum;
        this.Version = Protocol.Version;
        this.Type = Protocol.Type;
        this.PurchaseRegNum = Protocol.PurchaseRegNum;
        this.PurchaseGUID = Protocol.PurchaseGUID;
        this.Lots = new ArrayList<LotInfo>(Protocol.Lots);
        this.MissedContest = Protocol.MissedContest;
        this.MissedReason = Protocol.MissedReason;
    }
    public String getGUID()             { return GUID;		    }
    public String getRegNum()           { return RegNum;		}
    public String getType()             { return Type;          }
    public String getPurchaseRegNum()   { return PurchaseRegNum;}
    public String getPurchaseGUID()     { return PurchaseGUID;  }
    public String getMissedContest()    { return MissedContest; }
    public String getMissedReason()     { return MissedReason;  }
    public int getVersion()			    { return Version;		}
    public Date getDate()			    { return Date;		    }
    public ArrayList<LotInfo> getLots() { return Lots;          }
}
