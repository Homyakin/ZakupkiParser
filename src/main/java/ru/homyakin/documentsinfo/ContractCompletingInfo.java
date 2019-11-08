package ru.homyakin.documentsinfo;

import java.util.Date;

public class ContractCompletingInfo implements DocumentInfo{
    private String GUID;
    private String regNum;
    private String modification;
    private String type;
    private String contractGUID;
    private String contractRegNum;
    private String completed;
    private String hasPenalty; // boolean - ?
    private String penaltyInfo;
    private String cancellationCode;
    private String cancellationName;
    private String rejectionDocCode;
    private String rejectionDocName;
    private String rejectionDocNum;
    private int version;
    private Date date;
    private Date pubDate;
    private Date completionDate;
    private Date rejectionDocDate;

    public ContractCompletingInfo(String GUID, String regNum, int version, String modification, String type,
                                  String contractGUID, String contractRegNum, Date date, Date pubDate, Date completionDate,
                                  Date rejectionDocDate, String completed, String hasPenalty, String penaltyInfo, String cancellationCode,
                                  String cancellationName, String rejectionDocCode, String rejectionDocName, String rejectionDocNum) {
        this.GUID = GUID;
        this.regNum = regNum;
        this.version = version;
        this.modification = modification;
        this.type = type;
        this.contractGUID = contractGUID;
        this.contractRegNum = contractRegNum;
        this.date = date;
        this.pubDate = pubDate;
        this.completionDate = completionDate;
        this.rejectionDocDate = rejectionDocDate;
        this.completed = completed;
        this.hasPenalty = hasPenalty;
        this.penaltyInfo = penaltyInfo;
        this.cancellationCode = cancellationCode;
        this.cancellationName = cancellationName;
        this.rejectionDocCode = rejectionDocCode;
        this.rejectionDocName = rejectionDocName;
        this.rejectionDocNum = rejectionDocNum;
    }

    public String getGUID() {
        return GUID;
    }

    public String getRegNum() {
        return regNum;
    }

    public String getModification() {
        return modification;
    }

    public String getType() {
        return type;
    }

    public String getContractGUID() {
        return contractGUID;
    }

    public String getContractRegNum() {
        return contractRegNum;
    }

    public String getCompleted() {
        return completed;
    }

    public String getHasPenalty() {
        return hasPenalty;
    }

    public String getPenaltyInfo() {
        return penaltyInfo;
    }

    public String getCancellationCode() {
        return cancellationCode;
    }

    public String getCancellationName() {
        return cancellationName;
    }

    public String getRejectionDocCode() {
        return rejectionDocCode;
    }

    public String getRejectionDocName() {
        return rejectionDocName;
    }

    public String getRejectionDocNum() {
        return rejectionDocNum;
    }

    public int getVersion() {
        return version;
    }

    public Date getDate() {
        return date;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public Date getRejectionDocDate() {
        return rejectionDocDate;
    }
}
