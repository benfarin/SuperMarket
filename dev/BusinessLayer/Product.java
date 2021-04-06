package BusinessLayer;

import java.util.Date;
import java.util.Map;

public class Product {

    private String name;
    private String manufacture;
    private int storeQuantity;
    private int storageQuantity;
    private int discount;
    private Date discountDate;
    private double priceFromSupplier;
    private double priceToCustomer;
    private int defectiveItem;
    private int minimum;
    private Map<Double,Date> priceToCusHistory;
    private Map<Double,Date> priceFromSupHistory;

    public Product(String name, String manufacture, double priceFromSupplier, double priceToCustomer, int minimum) {
        this.name = name;
        this.manufacture = manufacture;
        this.priceFromSupplier = priceFromSupplier;
        this.priceToCustomer = priceToCustomer;
        this.minimum = minimum;
        this.storageQuantity= 0;
        this.storeQuantity = 0;
        this.discount = 0;
        this.discountDate = null;
        this.defectiveItem = 0;
    }


    public double getPriceFromSupplier() {
        return priceFromSupplier;
    }

    public void setPriceFromSupplier(double priceFromSupplier) {
        this.priceFromSupplier = priceFromSupplier;
        Date today = new Date(System.currentTimeMillis());
        priceFromSupHistory.put(priceFromSupplier,today);
    }
    public void setPriceToCustomer(double priceToCustomer) {
        this.priceToCustomer = priceToCustomer;
        Date today = new Date(System.currentTimeMillis());
        priceToCusHistory.put(priceToCustomer,today);
    }

    public double getPriceToCustomer() {
        Date today = new Date(System.currentTimeMillis());
        if(discountDate.after(today))
            discount = 0;
        return priceToCustomer - (discount*priceToCustomer/100);

    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setDiscountDate(Date discountDate) {
        this.discountDate = discountDate;
    }



    public void setDefectiveItem(int defectiveItem) {
        this.defectiveItem = defectiveItem;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public void addStoreQuantity(int add) {
        this.storeQuantity += add;
    }
    public boolean reduceStoreQuantity(int reduce) {
        this.storeQuantity -= reduce;
        return alertQuantity();
    }
    public void addStorageQuantity(int add) {
        this.storageQuantity += add;
    }
    public void reduceStorageQuantity(int reduce) {
        this.storageQuantity -= reduce;
    }
    public boolean alertQuantity(){
        return storageQuantity <= minimum;
    }

    public String printProduct() {
        return "Product:" + "\n" +
                "name='" + name + '\'' + "\n" +
                "manufacture = '" + manufacture + '\'' + "\n" +
                "storeQuantity = " + storeQuantity + "\n" +
                "storageQuantity = " + storageQuantity + "\n" +
                "discount = " + discount + "\n" +
                "discountDate = " + discountDate + "\n" +
                "priceFromSupplier = " + priceFromSupplier + "\n" +
                "priceToCustomer = " + priceToCustomer + "\n" +
                "defectiveItem = " + defectiveItem + "\n";
    }
}
