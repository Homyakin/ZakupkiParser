package ru.homyakin.documentsinfo;

import java.util.ArrayList;
import java.util.Date;

public class ProtocolInfo {
    private String GUID;
    private String regNum;
    private String type;
    private String purchaseRegNum;
    private String purchaseGUID;
    private String missedContest;
    private String missedReason;
    private Date date;
    private int version;
    private ArrayList<LotInfo> lots;

    public ProtocolInfo(String GUID, Date date, String regNum, int version, String type, String purchaseRegNum,
                        String purchaseGUID, ArrayList<LotInfo> lots, String missedContest, String missedReason) {
        this.GUID = GUID;
        this.date = date;
        this.regNum = regNum;
        this.version = version;
        this.type = type;
        this.purchaseRegNum = purchaseRegNum;
        this.purchaseGUID = purchaseGUID;
        this.lots = new ArrayList<LotInfo>(lots);
        this.missedContest = missedContest;
        this.missedReason = missedReason;
    }

    public ProtocolInfo(ProtocolInfo Protocol) {
        this.GUID = Protocol.GUID;
        this.date = Protocol.date;
        this.regNum = Protocol.regNum;
        this.version = Protocol.version;
        this.type = Protocol.type;
        this.purchaseRegNum = Protocol.purchaseRegNum;
        this.purchaseGUID = Protocol.purchaseGUID;
        this.lots = new ArrayList<LotInfo>(Protocol.lots);
        this.missedContest = Protocol.missedContest;
        this.missedReason = Protocol.missedReason;
    }

    public String getGUID() {
        return GUID;
    }

    public String getRegNum() {
        return regNum;
    }

    public String getType() {
        return type;
    }

    public String getPurchaseRegNum() {
        return purchaseRegNum;
    }

    public String getPurchaseGUID() {
        return purchaseGUID;
    }

    public String getMissedContest() {
        return missedContest;
    }

    public String getMissedReason() {
        return missedReason;
    }

    public int getVersion() {
        return version;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<LotInfo> getLots() {
        return lots;
    }
}
