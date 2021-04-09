package BusinessLayer;

import java.util.HashMap;

public class Product {
    private String name;
    private Long storeCode;
    private Double price;
    private HashMap<Integer, Double> highAmountDiscount;
    private Long supplierSerialNum;
    private BusinessLayer.Supplier supplier;


    public Product(String name, Long storeCode, Double price, HashMap<Integer, Double> highAmountDiscount, Long supplierSerialNum, BusinessLayer.Supplier supplier) {
        this.name = name;
        this.storeCode = storeCode;
        this.price = price;
        this.highAmountDiscount = highAmountDiscount; //TODO: do the correct assignment here for the new hashmap of discounts AND constructor for empty HighAmountDiscount
        this.supplierSerialNum = supplierSerialNum; // why do we need this?
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

    protected Long getStoreCode() {
        return storeCode;
    }

    protected void setStoreCode(Long storeCode) {
        this.storeCode = storeCode;
    }

    protected Double getPrice() {
        return price;
    }

    protected void setPrice(Double price) {
        this.price = price;
    }

    protected HashMap<Integer, Double> getHighAmountDiscount() {
        return highAmountDiscount;
    }

    protected void setHighAmountDiscount(HashMap<Integer, Double> highAmountDiscount) {
        this.highAmountDiscount = highAmountDiscount;
    }

    protected Long getSupplierSerialNum() {
        return supplierSerialNum;
    }

    protected void setSupplierSerialNum(Long supplierSerialNum) {
        this.supplierSerialNum = supplierSerialNum;
    }

    public BusinessLayer.Supplier getSupplier() {
        return supplier;
    }

    protected void setSupplier(BusinessLayer.Supplier supplier) {
        this.supplier = supplier;
    }


}
