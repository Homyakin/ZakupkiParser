package ru.homyakin.documentsinfo;

import ru.homyakin.documentsinfo.subdocumentsinfo.CustomerInfo;

import java.util.ArrayList;
import java.util.Date;

public class PurchaseInfo implements DocumentInfo{
    private String number;
    private String name;
    private String GUID;
    private String type;
    private String choiceWay;
    private String choiceWayCode;
    private String region;
    private String stage;
    private Date birnDate;
    private Date refreshDate;
    private Date startApplicationDate;
    private Date endApplicationDate;
    private int version;
    private boolean jointPurchase;
    private CustomerInfo customer;
    private ArrayList<LotInfo> lots;

    public PurchaseInfo(String number, String name, String GUID, Date birnDate, Date refreshDate,
                        Date startApplicationDate, Date endApplicationDate, String type, String choiceWay, String choiceWayCode,
                        String region, String stage, int version, boolean jointPurchase, CustomerInfo customer,
                        ArrayList<LotInfo> lots) {
        this.number = number;
        this.name = name;
        this.GUID = GUID;
        this.birnDate = birnDate;
        this.refreshDate = refreshDate;
        this.startApplicationDate = startApplicationDate;
        this.endApplicationDate = endApplicationDate;
        this.type = type;
        this.choiceWay = choiceWay;
        this.choiceWayCode = choiceWayCode;
        this.region = region;
        this.stage = stage;
        this.version = version;
        this.jointPurchase = jointPurchase;
        this.lots = new ArrayList<LotInfo>(lots);
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getGUID() {
        return GUID;
    }

    public String getType() {
        return type;
    }

    public String getChoiceWay() {
        return choiceWay;
    }

    public String getChoiceWayCode() {
        return choiceWayCode;
    }

    public String getRegion() {
        return region;
    }

    public String getStage() {
        return stage;
    }

    public Date getBirnDate() {
        return birnDate;
    }

    public Date getRefreshDate() {
        return refreshDate;
    }

    public Date getStartApplicationDate() {
        return startApplicationDate;
    }

    public Date getEndApplicationDate() {
        return endApplicationDate;
    }

    public int getVersion() {
        return version;
    }

    public boolean getJointPurchase() {
        return jointPurchase;
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    public ArrayList<LotInfo> getLots() {
        return lots;
    }
}
