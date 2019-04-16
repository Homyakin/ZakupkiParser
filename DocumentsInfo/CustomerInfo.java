package DocumentsInfo;

class CustomerInfo {
    private String Name;
    private String ShortName;
    private String Address;
    private String INN;
    private String KPP;
    private String OGRN;
    private String OKATO;
    private String OKPO;
    private String EMail;

    public CustomerInfo(String Name, String ShortName, String Address, String INN, String KPP, String OGRN, String OKATO, String OKPO, String EMail)
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
    }
    public CustomerInfo(CustomerInfo Customer)
    {
        this.Name = Customer.Name;
        this.ShortName = Customer.ShortName;
        this.Address = Customer.Address;
        this.INN = Customer.INN;
        this.KPP = Customer.KPP;
        this.OGRN = Customer.OGRN;
        this.OKATO = Customer.OKATO;
        this.OKPO = Customer.OKPO;
        this.EMail = Customer.EMail;
    }
    public String getName()      { return Name;     }
    public String getShortName() { return ShortName;}
    public String getAddress()   { return Address;  }
    public String getINN()       { return INN;      }
    public String getKPP()       { return KPP;      }
    public String getOGRN()      { return OGRN;     }
    public String getOKATO()     { return OKATO;    }
    public String getOKPO()      { return OKPO;     }
    public String getEMail()     { return EMail;    }
}
