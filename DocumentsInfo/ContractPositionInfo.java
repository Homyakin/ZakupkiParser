package DocumentsInfo;


public class ContractPositionInfo 
{
    private String GUID;
    private String Name;
    private String ordinalNumber;
    private String OKDP;
    private String OKPD;
    private String OKPD2;
    private String OKEI;
    private String Qty;

    public ContractPositionInfo(String ordinalNumber)
    {
        this.setOrdinalNumber(ordinalNumber);
    }

	public String getGUID() { return GUID; }

	public void setGUID(String gUID) { GUID = gUID; }

	public String getName() { return Name; }

	public void setName(String name) { Name = name; }

	public String getOrdinalNumber() { return ordinalNumber; }

	public void setOrdinalNumber(String ordinalNumber) { this.ordinalNumber = ordinalNumber; }

	public String getOKDP() { return OKDP; }

	public void setOKDP(String oKDP) { OKDP = oKDP; }

	public String getOKPD() { return OKPD; }

	public void setOKPD(String oKPD) { OKPD = oKPD; }

	public String getOKPD2() { return OKPD2; }

	public void setOKPD2(String oKPD2) { OKPD2 = oKPD2; }

	public String getOKEI() { return OKEI; }

	public void setOKEI(String oKEI) { OKEI = oKEI; }

	public String getQty() { return Qty; }

	public void setQty(String qty) { Qty = qty; }
    
}
