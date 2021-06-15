package BusinessLayer.Inventory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Category {
    private String name;
    private Category supCategory;
    private List<Category> subCategories;
    private List<Product> products;
    private int discount;
    private Date discountDate;

    public Category(String name) {
        this.name = name;
        this.subCategories = new LinkedList<>();
        this.subCategories =  new LinkedList<>();
        this.discount = 0;
        this.discountDate = null;
        this.products = new LinkedList<>();
        this.supCategory = null;
    }

    public void setSupCategory(Category supCategory) {
        this.supCategory = supCategory;
    }

    public Category(String name, Category supCategory, List<Category> subCategories, List<Product> products, int discount, Date discountDate) {
        this.name = name;
        this.supCategory = supCategory;
        this.subCategories = subCategories;
        this.products = products;
        this.discount = discount;
        this.discountDate = discountDate;
    }

    public void addProduct(Product p){
        if(!products.contains(p)) {
            products.add(p);
            if(this.supCategory != null)
                this.supCategory.addProduct(p);
        }
    }
    public void addSup(Category c){
        this.supCategory = c;
    }
    public void addSub(Category c){
        if(!subCategories.contains(c))
            subCategories.add(c);
        //  c.addSup(this);
    }

    public String getName() {
        return name;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getDiscount() {
        Date today = new Date(System.currentTimeMillis());
        if(this.discountDate != null && discountDate.before(today)) {
            discount = 0;
            this.discountDate = null;
        }
        return discount;
    }

    public Date getDiscountDate() {
        return discountDate;
    }

    public void deleteSubCat(Category subC) {
        if(subCategories.contains(subC))
            subCategories.remove(subC);
    }
    public void deleteProd(Product p) {
        if(products.contains(p))
            products.remove(p);
    }

    public void setDiscount(int discount, Date discountDate1) {
        Date today = new Date(System.currentTimeMillis());
        if(this.discountDate == null)
            this.discountDate = new Date();
        if(discountDate1 != null && discountDate1.after(today)) {
            if(discount>0)
                this.discount = discount;
            this.discountDate = discountDate1;
//            for(Category sub: subCategories){
//                sub.setDiscount(this.discount,discountDate1);
//            }
            for(Product p : products){
                p.addDiscount(this.discount);
            }
        }
    }

    public void setDiscountDate(Date discountDate) {
        Date today = new Date(System.currentTimeMillis());
        if(this.discountDate == null)
            this.discountDate = new Date();
        if(discountDate != null && discountDate.after(today)) {
            this.discountDate = discountDate;
        }}

    public void addDiscount(int discount){
        this.discount+=discount;
    }

    public Category getSupCategory() {
        return supCategory;
    }

    public String printCategory() {
        String subCats ="";
        for(Category c : subCategories){
            subCats+=c.getName()+", ";
        }
        if(subCats.length()>1)
            subCats = subCats.substring(0,subCats.length()-2);

        String prods ="";
        for(Product p : products){
            prods+=p.getName()+", ";
        }
        if(prods.length()>1)
            prods = prods.substring(0,prods.length()-2);

        String s;
        if (discountDate == null)
            s = "-";
        else s = discountDate.toString();
        return "------------------------------\n"+"Category:\n" +
                "\nName = '" + name + '\'' +
                "\nSubCategories = [" + subCats +"]"+
                "\nProducts = [" + prods +"]"+
                "\nDiscount = " + discount +
                "\nDiscount Date = " + s+"\n------------------------------\n\n";
    }
}