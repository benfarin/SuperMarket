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
        this.discountDate = new Date();
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

    public void setDiscount(int discount, Date discountDate) {
        this.discount = discount;
        this.discountDate = discountDate;
    }

    public void setDiscountDate(Date discountDate) {
        this.discountDate = discountDate;
    }

    public String printCategory() {
        return "Category:\n" +
                "\nName = '" + name + '\'' +
                "\nSubCategories = " + subCategories +
                "\nProducts = " + products +
                "\nDiscount = " + discount +
                "\nDiscount Date = " + discountDate;
    }
}
