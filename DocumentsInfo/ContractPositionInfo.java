package DocumentsInfo;


public class ContractPositionInfo {
    private String GUID;
    private int Number;
    private String OKDP;
    private String OKPD;
    private String OKPD2;
    private String OKEI;
    private String OKEIName;
    private String Qnty;

    public ContractPositionInfo(String GUID, int Number, String OKDP, String OKPD, String OKPD2,
    		String OKEI, String OKEIName, String Qnty)
    {
        this.GUID = GUID;
        this.Number = Number;
        this.OKDP = OKDP;
        this.OKPD = OKPD;
        this.OKPD2 = OKPD2;
        this.OKEI = OKEI;
        this.OKEIName = OKEIName;
        this.Qnty = Qnty;
    }
    public String getGUID()      { return GUID;		}
    public String getOKDP()      { return OKDP;    	}
    public String getOKPD()      { return OKPD;    	}
    public String getOKPD2()     { return OKPD2;   	}
    public String getOKEI()      { return OKEI;    	}
    public String getOKEIName()	 { return OKEIName;	}
    public String getQnty()      { return Qnty;    	}
    public int getNumber()		 { return Number;	}
}
