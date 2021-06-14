package BusinessLayer.Inventory;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StockReport extends Report {
    static int day = 3;
    private List<Category> categories;

    public StockReport(){
        super();
        this.categories = new LinkedList<>();
    }
    public StockReport(int id, Date date){
        super(id, date);
        this.categories = new LinkedList<>();
    }
    public StockReport(int id, Date date, int day1){
        super(id, date);
        this.categories = new LinkedList<>();
        setDay(day1);
    }

    public int getDay(){
        return day;
    }

    public void addCategory(Category category){
        if(!categories.contains(category))
            this.categories.add(category);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setDay(int day){
        this.day = day;
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
