package Entities;

import java.util.HashMap;

public class Product {
    private String name;
    private long storeCode;
    private double price;
    private HashMap highAmountDiscount;
    private long supplierSerialNum;
    private Supplier supplier;


    public Product(String name, long storeCode, double price, HashMap highAmountDiscount, long supplierSerialNum, Supplier supplier) {
        this.name = name;
        this.storeCode = storeCode;
        this.price = price;
        this.highAmountDiscount = highAmountDiscount;
        this.supplierSerialNum = supplierSerialNum;
        this.supplier = supplier;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected long getStoreCode() {
        return storeCode;
    }

    protected void setStoreCode(long storeCode) {
        this.storeCode = storeCode;
    }

    protected double getPrice() {
        return price;
    }

    protected void setPrice(double price) {
        this.price = price;
    }

    protected HashMap getHighAmountDiscount() {
        return highAmountDiscount;
    }

    protected void setHighAmountDiscount(HashMap highAmountDiscount) {
        this.highAmountDiscount = highAmountDiscount;
    }

    protected long getSupplierSerialNum() {
        return supplierSerialNum;
    }

    protected void setSupplierSerialNum(long supplierSerialNum) {
        this.supplierSerialNum = supplierSerialNum;
    }

    protected Supplier getSupplier() {
        return supplier;
    }

    protected void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }


}
