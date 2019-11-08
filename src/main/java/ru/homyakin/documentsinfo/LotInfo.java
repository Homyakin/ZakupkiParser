package ru.homyakin.documentsinfo;

import ru.homyakin.documentsinfo.subdocumentsinfo.CustomerInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.SupplierInfo;

import java.util.ArrayList;

public class LotInfo implements DocumentInfo{
    private String GUID;
    private String name;
    private String NMC;
    private String NMCInfo;
    private boolean jointPurchase;
    private int number;
    private ArrayList<LotItemInfo> lotItems;
    private ArrayList<CustomerInfo> customers;
    private ArrayList<SupplierInfo> suppliers;

    public LotInfo(String GUID, String name, int number, String NMC, String NMCInfo, boolean jointPurchase,
                   ArrayList<LotItemInfo> lotItems, ArrayList<CustomerInfo> customers, ArrayList<SupplierInfo> suppliers) {
        this.GUID = GUID;
        this.name = name;
        this.number = number;
        this.NMC = NMC;
        this.NMCInfo = NMCInfo;
        this.jointPurchase = jointPurchase;
        this.lotItems = new ArrayList<LotItemInfo>(lotItems);
        this.customers = new ArrayList<CustomerInfo>(customers);
        this.suppliers = new ArrayList<SupplierInfo>(suppliers);
    }

    public LotInfo(LotInfo Lot) {
        this.GUID = Lot.GUID;
        this.name = Lot.name;
        this.number = Lot.number;
        this.NMC = Lot.NMC;
        this.NMCInfo = Lot.NMCInfo;
        this.jointPurchase = Lot.jointPurchase;
        this.lotItems = new ArrayList<LotItemInfo>(Lot.lotItems);
        this.customers = new ArrayList<CustomerInfo>(Lot.customers);
        this.suppliers = new ArrayList<SupplierInfo>(Lot.suppliers);
    }

    public String getGUID() {
        return GUID;
    }

    public String getName() {
        return name;
    }

    public String getNMC() {
        return NMC;
    }

    public String getNMCInfo() {
        return NMCInfo;
    }

    public int getNumber() {
        return number;
    }

    public boolean getJointPurchase() {
        return jointPurchase;
    }

    public ArrayList<LotItemInfo> getLotItems() {
        return lotItems;
    }

    public ArrayList<CustomerInfo> getCustomers() {
        return customers;
    }

    public ArrayList<SupplierInfo> getSuppliers() {
        return suppliers;
    }
}
