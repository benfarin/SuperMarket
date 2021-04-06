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

    @Override
    public String exportReport() {
        return "StockReport:\n" +
                "ID = " + Id +
                "\nDate = " + date +
                "\nCategories = " + categories;
    }
}
