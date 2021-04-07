package BusinessLayer;

import java.util.LinkedList;
import java.util.List;

public class ReportController {
    List<Report> reports;


    public ReportController() {
        this.reports = new LinkedList<>();
    }

    public void addStockReport (List<Category> categories){
        StockReport sto = new StockReport();
        for ( Category c : categories)
            sto.addCategory(c);
    }

    public void addDefReport (List<Product> products) {
        DefectiveReport def = new DefectiveReport();
        for (Product p : products)
            def.addProd(p);
    }

}
