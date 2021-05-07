package BusinessLayer.Suppliers;

import java.util.HashMap;

public class Product {
    private String name;
    private Long storeCode;
    private Double price;
    private HashMap<Integer, Double> highAmountDiscount; //  the key is the amount the value is the precent of discount
    private Long supplierSerialNum;
    private Supplier supplier;


    public Product(String name, Long storeCode, Double price, HashMap<Integer, Double> highAmountDiscount, Long supplierSerialNum, Supplier supplier) {
        this.name = name;
        this.storeCode = storeCode;
        this.price = price; // This is how much the SUPPLIER demands for 1 unit of the product
        this.highAmountDiscount = highAmountDiscount; //TODO: do the correct assignment here for the new hashmap of discounts AND constructor for empty HighAmountDiscount
        this.supplierSerialNum = supplierSerialNum;
        this.supplier = supplier;
    }

    public void AddDiscount() { // need be fill by creating new item from Supplier

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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public double GetCheapestPrice(int amount) {
        double min = this.price * amount;
        if (highAmountDiscount != null) {
            for (Integer key : highAmountDiscount.keySet()) {
                if (key <= amount) {
                    if (highAmountDiscount.get(key) * 0.01 * amount * price < min)
                        min = highAmountDiscount.get(key) * 0.01 * amount * price;
                }
            }
        }
        return min;
    }
}
