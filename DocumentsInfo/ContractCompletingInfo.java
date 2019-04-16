package DocumentsInfo;

import java.util.Date;

public class ContractCompletingInfo {
    private String GUID;
    private String RegNum;
    private int Version;
    private String Modification;
    private String Type;
    private String ContractGUID;
    private String ContractRegNum;
    private Date Date;
    private Date PubDate;
    private Date CompletionDate;
    private Date RejectionDocDate;
    private String Completed;
    private String HasPenalty;
    private String PenaltyInfo;
    private String CancellationCode;
    private String CancellationName;
    private String RejectionDocCode;
    private String RejectionDocName;
    private String RejectionDocNum;

    public ContractCompletingInfo(String GUID, String RegNum, int Version, String Modification, String Type, String ContractGUID, String ContractRegNum, Date Date, Date PubDate, Date CompletionDate,
            Date RejectionDocDate, String Completed, String HasPenalty, String PenaltyInfo, String CancellationCode, String CancellationName, String RejectionDocCode, String RejectionDocName,
            String RejectionDocNum)
    {
        this.GUID = GUID;
        this.RegNum = RegNum;
        this.Version = Version;
        this.Modification = Modification;
        this.Type = Type;
        this.ContractGUID = ContractGUID;
        this.ContractRegNum = ContractRegNum;
        this.Date = Date;
        this.PubDate = PubDate;
        this.CompletionDate = CompletionDate;
        this.RejectionDocDate = RejectionDocDate;
        this.Completed = Completed;
        this.HasPenalty = HasPenalty;
        this.PenaltyInfo = PenaltyInfo;
        this.CancellationCode = CancellationCode;
        this.CancellationName = CancellationName;
        this.RejectionDocCode = RejectionDocCode;
        this.RejectionDocName = RejectionDocName;
        this.RejectionDocNum = RejectionDocNum;
    }
    public String getGUID()             { return GUID;  			}
    public String getRegNum()           { return RegNum;			}
    public String getModification()     { return Modification;  	}
    public String getType()             { return Type;          	}
    public String getContractGUID()     { return ContractGUID;  	}
    public String getContractRegNum()   { return ContractRegNum;	}
    public String getCompleted()        { return Completed;       	}
    public String getHasPenalty()       { return HasPenalty;      	}
    public String getPenaltyInfo()      { return PenaltyInfo;     	}
    public String getCancellationCode()	{ return CancellationCode;	}
    public String getCancellationName() { return CancellationName;	}
    public String getRejectionDocCode() { return RejectionDocCode;	}
    public String getRejectionDocName() { return RejectionDocName;	}
    public String getRejectionDocNum()  { return RejectionDocNum; 	}
    public int getVersion()				{ return Version;			}
    public Date getDate()               { return Date;              }
    public Date getPubDate()            { return PubDate;           }
    public Date getCompletionDate()     { return CompletionDate;    }
    public Date getRejectionDocDate()   { return RejectionDocDate;  }
}
