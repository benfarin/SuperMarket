package BusinessLayer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Category {

    private String name;
    private List<Category> subCategories;
    private List<Product> products;
    private int discount;
    private Date discountDate;

    public Category(String name, List<Category> subCategories) {
        this.name = name;
        this.subCategories = new LinkedList<>();
        this.subCategories = subCategories;
        this.discount = 0;
        this.discountDate = null;
        this.products = new LinkedList<>();
    }
    public void addProduct(Product p){
        if(!products.contains(p))
            products.add(p);
    }
    public void addSub(Category c){
        if(!subCategories.contains(c))
            subCategories.add(c);
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
        if(discountDate1 != null && discountDate1.after(today)) {
            this.discount = discount;
            this.discountDate = discountDate1;
        }
    }

    public void setDiscountDate(Date discountDate) {
        Date today = new Date(System.currentTimeMillis());
        if(discountDate != null && discountDate.after(today)) {
        this.discountDate = discountDate;
    }}


    public String printCategory() {
        String sub = "";
        for(Category cat : subCategories){
            sub+= cat.name + ", ";
        }
        sub = sub.substring(0,sub.length()-2);
        String prod = "";
        for(Product p : products){
            prod+= p.getName() + ", ";
        }
        prod = prod.substring(0,prod.length()-2);
        String date;
        if (discountDate == null)
            date = "-";
        else date = discountDate.toString();
        return "Category:\n" +
                "\nName = '" + name + '\'' +
                "\nSubCategories = " + sub +
                "\nProducts = " + prod +
                "\nDiscount = " + discount +
                "\nDiscount Date = " + date;
    }
}
