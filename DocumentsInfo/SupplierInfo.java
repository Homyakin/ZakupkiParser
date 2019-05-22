package DocumentsInfo;

import java.util.Date;
import java.util.EmptyStackException;

public class SupplierInfo 
{
    private String name;
    private String shortName;
    private String INN;
    private String type;
    private boolean provider;
    private boolean nonResident;
    

    public SupplierInfo(String name, boolean nonResident, String type, boolean provider)
    {
        this.setName(name);
        this.setNonResident(nonResident);
        this.setType(type);
        this.setProvider(provider);
    }


	public String getName() { return name; }


	public void setName(String name) { this.name = name; }


	public String getShortName() { return shortName; }


	public void setShortName(String shortName) { this.shortName = shortName; }


	public String getINN() { return INN; }


	public void setINN(String iNN) { INN = iNN; }


	public String getType() { return type; }


	public void setType(String type) { this.type = type; }


	public boolean isProvider() { return provider; }


	public void setProvider(boolean provider) { this.provider = provider; }


	public boolean getNonResident() { return nonResident; }


	public void setNonResident(boolean nonResident) { this.nonResident = nonResident; }
    
}
