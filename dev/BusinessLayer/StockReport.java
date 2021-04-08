package BusinessLayer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class StockReport extends Report {
    private List<Category> categories;

    public StockReport(){
        super();
        this.categories = new LinkedList<>();
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
