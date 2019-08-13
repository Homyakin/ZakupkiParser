package SubDocumentsInfo;

import java.math.BigDecimal;

public class ContractPositionInfo {
	private String GUID;
	private String Name;
	private Integer ordinalNumber;
	private OKInfo OKDP;
	private OKInfo OKPD;
	private OKInfo OKPD2;
	private String country; // TODO country - complicated structure
	private String producerCountry; // TODO boolean - ?
	private OKInfo OKEI;
	private BigDecimal qty;

	public ContractPositionInfo(Integer ordinalNumber) {
		this.setOrdinalNumber(ordinalNumber);
	}

	public String getGUID() {
		return GUID;
	}

	public void setGUID(String gUID) {
		GUID = gUID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Integer getOrdinalNumber() {
		return ordinalNumber;
	}

	public void setOrdinalNumber(Integer ordinalNumber) {
		this.ordinalNumber = ordinalNumber;
	}

	public OKInfo getOKDP() {
		return OKDP;
	}

	public void setOKDP(OKInfo oKDP) {
		OKDP = oKDP;
	}

	public OKInfo getOKPD() {
		return OKPD;
	}

	public void setOKPD(OKInfo oKPD) {
		OKPD = oKPD;
	}

	public OKInfo getOKPD2() {
		return OKPD2;
	}

	public void setOKPD2(OKInfo oKPD2) {
		OKPD2 = oKPD2;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProducerCountry() {
		return producerCountry;
	}

	public void setProducerCountry(String producerCountry) {
		this.producerCountry = producerCountry;
	}

	public OKInfo getOKEI() {
		return OKEI;
	}

	public void setOKEI(OKInfo oKEI) {
		OKEI = oKEI;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

}
