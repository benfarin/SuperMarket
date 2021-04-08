package BusinessLayer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Product {

    private String name;
    private String manufacture;
    private Category category;
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


    public Product(String name, Category category, String manufacture, double priceFromSupplier, double priceToCustomer, int minimum) {
        priceToCusHistory = new HashMap<>();
        priceFromSupHistory = new HashMap<>();
        this.name = name;
        this.manufacture = manufacture;
        this.priceFromSupplier = priceFromSupplier;
        this.category = category;
        this.priceToCustomer = priceToCustomer;
        this.minimum = minimum;
        this.storageQuantity= 0;
        this.storeQuantity = 0;
        this.discount = 0;
        this.discountDate = null;
        this.defectiveItem = 0;
        category.addProduct(this);
    }


    public double getPriceFromSupplier() {
        return priceFromSupplier;
    }

    public void setPriceFromSupplier(double priceFromSupplier) {
        if(priceFromSupplier > 0) {
            this.priceFromSupplier = priceFromSupplier;
            Date today = new Date(System.currentTimeMillis());
            priceFromSupHistory.put(priceFromSupplier, today);
        }
    }

    public int getDefectiveItem() {
        return defectiveItem;
    }

    public String getManufacture() {
        return manufacture;
    }

    public String getName() {
        return name;
    }

    public void setPriceToCustomer(double priceToCustomer) {
        if (priceToCustomer > 0) {
            this.priceToCustomer = priceToCustomer;
            Date today = new Date();
            priceToCusHistory.put(priceToCustomer, today);
        }
    }

    public double getPriceToCustomer() {
        Date today = new Date(System.currentTimeMillis());
        if(discountDate == null || discountDate.before(today)) {
            discount = 0;
            discountDate = null;
        }
        return priceToCustomer - (discount*priceToCustomer/100);
    }

    public void setDiscount(int discount, Date discountDate) {
        if(discount > 0) {
            Date today = new Date(System.currentTimeMillis());
            if (discountDate.after(today)) {
                this.discount = discount;
                this.discountDate = discountDate;
            }
        }
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public void setDefectiveItem(int defectiveItem) {
        if(defectiveItem > 0) {
            this.defectiveItem = defectiveItem;
        }
    }

    public boolean setMinimum(int minimum) {
        if(minimum > 0 ) {
            this.minimum = minimum;
        }
        return alertStorageQuantity();
    }

    public void addStoreQuantity(int add) {
        this.storeQuantity += add;
    }
    public void reduceStoreQuantity(int reduce) {
        this.storeQuantity -= reduce;
    }
    public void addStorageQuantity(int add) {
        this.storageQuantity += add;
    }
    public boolean reduceStorageQuantity(int reduce) {
        this.storageQuantity -= reduce;
        return alertStorageQuantity();
    }
    public boolean alertStorageQuantity(){
        return storageQuantity <= minimum;
    }

    public void setStoreQuantity(int storeQuantity) {
        this.storeQuantity = storeQuantity;
    }

    public boolean setStorageQuantity(int storageQuantity) {
        this.storageQuantity = storageQuantity;
        return alertStorageQuantity();
    }

    public int getMinimum() {
        return minimum;
    }

    public Category getCategory() {
        return category;
    }

    public int getDiscount() {
        return discount;
    }
    public String displayProdHistory(){
        return this.priceToCusHistory.toString();
    }

    public int getStorageQuantity() {
        return storageQuantity;
    }

    public String printProduct() {
        String s;
        if (discountDate == null)
            s = "-";
        else s = discountDate.toString();
        return "Product:" + "\n" +
                "Name = '" + name + '\'' + "\n" +
                "Category = '" + category.getName() + '\'' + "\n" +
                "Manufacture = '" + manufacture + '\'' + "\n" +
                "Store Quantity = " + storeQuantity + "\n" +
                "Storage Quantity = " + storageQuantity + "\n" +
                "Discount = " + discount + "\n" +
                "Discount Date = " + s + "\n" +
                "Price From Supplier = " + priceFromSupplier + "\n" +
                "Price To Customer = " + priceToCustomer + "\n" +
                "Defective Item = " + defectiveItem + "\n";
    }
}
