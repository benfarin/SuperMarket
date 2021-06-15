package BusinessLayer.Inventory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InventoryController {
    List<Product> products;
    List<Category> categories;

    public InventoryController () {
        this.products = new LinkedList<>();
        this.categories = new LinkedList<>();
    }
    public void addCatFromData(Category c){
        if(!categories.contains(c)){
            categories.add(c);
        }
    }
    public List<Product> getAllProd(){
        return products;
    }
    public List<Category> getAllCategories(){
        return categories;
    }
    public String showAllProds(){
        String s ="";
        for (Product p : products){
            s+=p.printProduct();
        }
        return s;
    }
    public String showAllCats(){
        String s ="";
        for (Category c : categories){
            s+=c.printCategory();
        }
        return s;
    }
    public Category addCategory(String name){
        Category cat = new Category(name);
        if(!categories.contains(cat))
            categories.add(cat);
        return cat;
    }
    public Product getProduct (String name) {
        for (Product p : products) {
            if (p.getName().equals(name))
                return p;
        }
        return null;
    }

    public Category getCategory (String name){
        for (Category c : categories){
            if (c.getName().equals(name))
                return c;
        }
        return null;
    }

    public Product addProduct (String name,Category category, String manufacture, double priceFromSupplier, double priceToCustomer, int minimum) {
        Product p = new Product(name,category,manufacture,priceFromSupplier,priceToCustomer,minimum);
        if (!products.contains(p))
            products.add(p);
        return p;
    }
    public Product addProductFromData (int id, String name, String manufacture, Category category, int storeQuantity, int storageQuantity, int discount, Date discountDate, double priceFromSupplier, double priceToCustomer, int defectiveItem, int minimum, Map<Double, Date> priceToCusHistory, Map<Double, Date> priceFromSupHistory) {
        Product p = new Product(id,name,manufacture,category,storeQuantity,storageQuantity,discount,discountDate,priceFromSupplier,priceToCustomer,defectiveItem,minimum,priceToCusHistory,priceFromSupHistory);
        if (!products.contains(p))
            products.add(p);
        p.getCategory().addProduct(p);
        return p;
    }
    public void deleteProduct(int id){
        for(Product p : products){
            if(p.getId() == id)
                products.remove(p);
    }}

    //    public void addCategory (String name, List<Category> subCategories) {
//        Category c = new Category(name,subCategories);
//        if (!categories.contains(c))
//            categories.add(c);
//    }
    public Product getProdByID(int id){
        for (Product p : products) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }
    public void setManufacture(String name, String manu){
        Product p = getProduct(name);
        p.setManufacture(manu);
    }

    public void setPriceFromSupplier(String name,double priceFromSupplier) {
        Product p = getProduct(name);
        p.setPriceFromSupplier(priceFromSupplier);
    }

    public void setPriceToCustomer(String name , double priceToCustomer) {
        Product p = getProduct(name);
        p.setPriceToCustomer(priceToCustomer);
    }

    public void setProdDiscount(String name,int discount, Date discountDate) {
        Product p = getProduct(name);
        p.setDiscount(discount,discountDate);
    }
    public void setDefectiveItems (String name, int def){
        Product p = getProduct(name);
        p.setDefectiveItem(def);
    }
    public void setMinimum(String name , int minimum) {
        Product p = getProduct(name);
        p.setMinimum(minimum);
    }

    public void addStoreQuantity(String name, int add){
        Product p = getProduct(name);
        p.addStoreQuantity(add);
    }

    public void reduceStoreQuantity(String name, int reduce){
        Product p = getProduct(name);
        p.reduceStoreQuantity(reduce);
    }

    public void addStorageQuantity(String name, int add){
        Product p = getProduct(name);
        p.addStorageQuantity(add);
    }

    public boolean reduceStorageQuantity(String name, int reduce){
        Product p = getProduct(name);
        return p.reduceStorageQuantity(reduce);
    }

    public void setStoreQuantity(String name, int storeQuantity){
        Product p = getProduct(name);
        p.setStoreQuantity(storeQuantity);
    }

    public boolean setStorageQuantity(String name, int storageQuantity){
        Product p = getProduct(name);
        return p.setStorageQuantity(storageQuantity);
    }

    public String printProduct(String name){
        Product p = getProduct(name);
        return p.printProduct();

    }
    public void addSub(String name,Category c){
        Category cat = getCategory(name);
        cat.addSub(c);
    }
    public void deleteSubCat(String name, Category subC){
        Category cat = getCategory(name);
        cat.deleteSubCat(subC);
    }
    public void deleteProd(String name ,Product p){
        Category cat = getCategory(name);
        cat.deleteProd(p);
    }
    public void setCatDiscount(String name, int discount,Date discountDate){
        Category cat = getCategory(name);
        cat.setDiscount(discount,discountDate);
    }
    public void setCatDiscountDate(String name, Date discountDate){
        Category cat = getCategory(name);
        cat.setDiscountDate(discountDate);
    }
    public String printCategory(String name){
        Category cat = getCategory(name);
        return cat.printCategory();
    }
    public String displayPFSHistory(String prodName){
        Product p = getProduct(prodName);
        return p.displayPriceFromSupHistory();
    }
    public String displayPTCHistory(String prodName){
        Product p = getProduct(prodName);
        return p.displayPriceToCusHistory();
    }
    public void setFirstId(int lastId) {
        if (!products.isEmpty())
            Product.ID = lastId + 1;
    }
    public void deleteCat(String cat){
        for (Category c: categories) {
            if (c.getName().equals(cat)) {
                categories.remove(c);
                return;
            }
        }
    }
}
