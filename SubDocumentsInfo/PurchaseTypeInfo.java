package SubDocumentsInfo;

public class PurchaseTypeInfo {
	private Integer code;
	private String name;
	
	public PurchaseTypeInfo(Integer code) {
		this.setCode(code);
	}

	public Integer getCode() { return code; }

	public void setCode(Integer code) { this.code = code; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }
}
