package DocumentsInfo;

class CustomerInfo 
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

    public CustomerInfo(String name, String shortName, String address, String INN, String KPP, String OGRN, String OKATO, String OKPO, String EMail)
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
    }
    
    public CustomerInfo(CustomerInfo Customer)
    {
        this.name = Customer.name;
        this.shortName = Customer.shortName;
        this.address = Customer.address;
        this.INN = Customer.INN;
        this.KPP = Customer.KPP;
        this.OGRN = Customer.OGRN;
        this.OKATO = Customer.OKATO;
        this.OKPO = Customer.OKPO;
        this.EMail = Customer.EMail;
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
}
