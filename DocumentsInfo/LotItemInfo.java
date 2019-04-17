package DocumentsInfo;

import DocumentsInfo.CustomerInfo;
import java.util.List;

public class LotItemInfo 
{
    private String GUID;
    private String OKDP;
    private String OKPD;
    private String OKPD2;
    private String OKVED;
    private String OKVED2;
    private String OKEI;
    private String OKEIName;
    private String Qnty;
    private int number;
    private List<CustomerInfo> customers;

    public LotItemInfo(String GUID, int number, String OKDP, String OKPD, String OKPD2, String OKVED, String OKVED2, String OKEI, String OKEIName, String Qnty)
    {
        this.GUID = GUID;
        this.number = number;
        this.OKDP = OKDP;
        this.OKPD = OKPD;
        this.OKPD2 = OKPD2;
        this.OKVED = OKVED;
        this.OKVED2 = OKVED2;
        this.OKEI = OKEI;
        this.OKEIName = OKEIName;
        this.Qnty = Qnty;
    }
    
    public String getGUID() { return GUID; }
    public String getOKDP() { return OKDP; }
    public String getOKPD() { return OKPD; }
    public String getOKPD2() { return OKPD2; }
    public String getOKVED() { return OKVED; }
    public String getOKVED2() { return OKVED2; }
    public String getOKEI() { return OKEI; }
    public String getOKEIName() { return OKEIName; }
    public String getQnty() { return Qnty; }
    public int getNumber() { return number; }
    public List<CustomerInfo> getCustomers() { return customers; } 
}
