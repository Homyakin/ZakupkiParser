package SubDocumentsInfo;


public class ContractPositionInfo {
    private String GUID;
    private String Name;
    private String ordinalNumber;
    private OKInfo OKDP;
    private OKInfo OKPD;
    private OKInfo OKPD2;
    private String country;
    private String producerCountry;
    private OKInfo OKEI;
    private String qty;

    public ContractPositionInfo(String ordinalNumber) {
        this.setOrdinalNumber(ordinalNumber);
    }

	public String getGUID() { return GUID; }

	public void setGUID(String gUID) { GUID = gUID; }

	public String getName() { return Name; }

	public void setName(String name) { Name = name; }

	public String getOrdinalNumber() { return ordinalNumber; }

	public void setOrdinalNumber(String ordinalNumber) { this.ordinalNumber = ordinalNumber; }

	public OKInfo getOKDP() { return OKDP; }

	public void setOKDP(OKInfo oKDP) { OKDP = oKDP; }

	public OKInfo getOKPD() { return OKPD; }

	public void setOKPD(OKInfo oKPD) { OKPD = oKPD; }

	public OKInfo getOKPD2() { return OKPD2; }

	public void setOKPD2(OKInfo oKPD2) { OKPD2 = oKPD2; }

	public String getCountry() { return country; }

	public void setCountry(String country) { this.country = country; }

	public String getProducerCountry() { return producerCountry; }

	public void setProducerCountry(String producerCountry) { this.producerCountry = producerCountry; }

	public OKInfo getOKEI() { return OKEI; }

	public void setOKEI(OKInfo oKEI) { OKEI = oKEI; }

	public String getQty() { return qty; }

	public void setQty(String qty) { this.qty = qty; }
    
}
