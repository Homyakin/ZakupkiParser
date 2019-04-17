package DocumentsInfo;

import java.util.Date;
import java.util.EmptyStackException;

public class SupplierInfo 
{
    private String name;
    private String shortName;
    private String address;
    private String INN;
    private String KPP;
    private String OGRN;
    private String OKATO;
    private String OKPO;
    private String EMail;
    private String OKPOF;
    private String nonResident;
    private String code;
    private String NRInfo;
    private String aplNum;
    private String offer;
    private String currency;
    private String accepted;
    private String preventReason;
    private Date regDate;
    private boolean winner;
    private int place;
    

    public SupplierInfo(String name, String shortName, String address, String INN, String KPP, String OGRN, String OKATO, String OKPO, String EMail, String OKPOF, String nonResident, String code,
                        String NRInfo, Date regDate, String offer, String currency, String accepted, String preventReason, boolean winner, int place, String aplNum)
    {
        this.name = name;
        this.shortName = shortName;
        this.address = address;
        this.INN = INN;
        this.KPP = KPP;
        this.OGRN = OGRN;
        this.OKATO = OKATO;
        this.OKPO = OKPO;
        this.EMail = EMail;
        this.OKPOF = OKPOF;
        this.nonResident = nonResident;
        this.code = code;
        this.NRInfo = NRInfo;
        this.regDate = regDate;
        this.offer = offer;
        this.currency = currency;
        this.accepted = accepted;
        this.preventReason = preventReason;
        this.winner = winner;
        this.place = place;
        this.aplNum = aplNum;
    }
    
    public String getName() { return name; }
    public String getShortName() { return shortName; }
    public String getAddress() { return address; }
    public String getINN() { return INN; }
    public String getKPP() { return KPP; }
    public String getOGRN() { return OGRN; }
    public String getOKATO() { return OKATO; }
    public String getOKPO() { return OKPO; }
    public String getEMail() { return EMail; }
    public String getOKPOF() { return OKPOF; }
    public String getNonResident() { return nonResident; }
    public String getCode() { return code; }
    public String getNRInfo() { return NRInfo; }
    public String getAplNum() { return aplNum; }
    public String getOffer() { return offer; }
    public String getCurrency() { return currency; }
    public String getAccepted() { return accepted; }
    public String getPreventReason() { return preventReason; }
    public Date getRegDate() { return regDate; }
    public boolean getWinner() { return winner; }
    public int getPlace() { return place; }
}
