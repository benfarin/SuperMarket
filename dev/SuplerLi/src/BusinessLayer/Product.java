package BusinessLayer;

import java.util.HashMap;

public class Product {
    private String name;
    private long storeCode;
    private double price;
    private HashMap<Integer, Double> highAmountDiscount;
    private long supplierSerialNum;
    private BusinessLayer.Supplier supplier;


    public Product(String name, long storeCode, double price, HashMap<Integer, Double> highAmountDiscount, BusinessLayer.Supplier supplier, BusinessLayer.Item_Order_Controller itemOrdercontroller) {
        this.name = name;
        this.storeCode = storeCode;
        this.price = price;
        this.highAmountDiscount = highAmountDiscount; //TODO: do the correct assignment here for the new hashmap of discounts AND constructor for empty HighAmountDiscount
        //this.supplierSerialNum = supplierSerialNum; // why do we need this?
        this.supplier = supplier;
    }
    public  void  AddDiscount(){ // need be fill by creating new item from Supplier
        
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

    protected HashMap<Integer, Double> getHighAmountDiscount() {
        return highAmountDiscount;
    }

    protected void setHighAmountDiscount(HashMap<Integer, Double> highAmountDiscount) {
        this.highAmountDiscount = highAmountDiscount;
    }

    protected long getSupplierSerialNum() {
        return supplierSerialNum;
    }

    protected void setSupplierSerialNum(long supplierSerialNum) {
        this.supplierSerialNum = supplierSerialNum;
    }

    public BusinessLayer.Supplier getSupplier() {
        return supplier;
    }

    protected void setSupplier(BusinessLayer.Supplier supplier) {
        this.supplier = supplier;
    }


}
