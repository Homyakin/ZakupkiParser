package DocumentsInfo;

import java.util.Date;
import java.util.EmptyStackException;

public class SupplierInfo {
    private String Name;
    private String ShortName;
    private String Address;
    private String INN;
    private String KPP;
    private String OGRN;
    private String OKATO;
    private String OKPO;
    private String EMail;
    private String OKPOF;
    private String NonResident;
    private String Code;
    private String NRInfo;
    private String AplNum;
    private String Offer;
    private String Currency;
    private String Accepted;
    private String PreventReason;
    private Date RegDate;
    private boolean Winner;
    private int Place;
    

    public SupplierInfo(String Name, String ShortName, String Address, String INN, String KPP, String OGRN, String OKATO, String OKPO, String EMail, String OKPOF, String NonResident, String Code,
                        String NRInfo, Date RegDate, String Offer, String Currency, String Accepted, String PreventReason, boolean Winner, int Place, String AplNum)
    {
        this.Name = Name;
        this.ShortName = ShortName;
        this.Address = Address;
        this.INN = INN;
        this.KPP = KPP;
        this.OGRN = OGRN;
        this.OKATO = OKATO;
        this.OKPO = OKPO;
        this.EMail = EMail;
        this.OKPOF = OKPOF;
        this.NonResident = NonResident;
        this.Code = Code;
        this.NRInfo = NRInfo;
        this.RegDate = RegDate;
        this.Offer = Offer;
        this.Currency = Currency;
        this.Accepted = Accepted;
        this.PreventReason = PreventReason;
        this.Winner = Winner;
        this.Place = Place;
        this.AplNum = AplNum;
    }
    public String getName()          { return Name;         }
    public String getShortName()     { return ShortName;    }
    public String getAddress()       { return Address;      }
    public String getINN()           { return INN;          }
    public String getKPP()           { return KPP;          }
    public String getOGRN()          { return OGRN;         }
    public String getOKATO()         { return OKATO;        }
    public String getOKPO()          { return OKPO;         }
    public String getEMail()         { return EMail;        }
    public String getOKPOF()         { return OKPOF;        }
    public String getNonResident()   { return NonResident;  }
    public String getCode()          { return Code;         }
    public String getNRInfo()        { return NRInfo;       }
    public String getAplNum()        { return AplNum;       }
    public String getOffer()         { return Offer;        }
    public String getCurrency()      { return Currency;     }
    public String getAccepted()      { return Accepted;     }
    public String getPreventReason() { return PreventReason;}
    public Date getRegDate()         { return RegDate;      }
    public boolean getWinner()       { return Winner;       }
    public int getPlace()            { return Place;        }
}
