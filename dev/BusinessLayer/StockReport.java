package BusinessLayer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class StockReport extends Report {
    private List<Category> categories;

    public StockReport(){
        super();
        this.categories = new LinkedList<>();
        Category subCategory1 = new Category("Low-fat", new LinkedList<>());
        Category subCategory2 = new Category("Milk", new LinkedList<>());
        Category subCategory3 = new Category("Dairy-free", new LinkedList<>());
        Category mainCategory = new Category("Dairy", new LinkedList<>());
        mainCategory.addSub(subCategory1);
        mainCategory.addSub(subCategory2);
        mainCategory.addSub(subCategory3);
        Product p = new Product("milk",mainCategory,"Maabarot",2.90,4.10,20);
        Product p1 = new Product("milk low fat",subCategory2,"Maabarot",2.90,4.10,20);

    }

    public void addCategory(Category category){
        if(!categories.contains(category))
            this.categories.add(category);
    }

    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public String exportReport() {
        String s ="";
        for (Category c : categories) {
            s += "Category: "+c.getName()+"\n";
            for (Product p : c.getProducts()){
                s+= p.printProduct()+"\n";
            }
            s+="\n";
        }
        return "StockReport:\n" +
                "ID = " + Id +
                "\nDate = " + date +"\n\n"+ s;
    }
}
