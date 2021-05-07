package BusinessLayer.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ReportController {
    List<DefectiveReport> defReports;
    List<StockReport> stockReports;


    public ReportController() {
        this.defReports = new LinkedList<>();
        this.stockReports = new LinkedList<>();
    }

    public DefectiveReport getDefReport(int id) {
        for (DefectiveReport dRep : defReports) {
            if (dRep.getID() == id)
                return dRep;
        }
        return null;
    }

    public StockReport getStoReport(int id) {
        for (StockReport sRep : stockReports) {
            if (sRep.getID() == id)
                return sRep;
        }
        return null;
    }

    public void addStockReport(List<Category> categories) {
        StockReport sto = new StockReport();
        for (Category c : categories)
            sto.addCategory(c);
        this.stockReports.add(sto);
    }

    public DefectiveReport addDefReport(List<Product> products) {
        DefectiveReport def = new DefectiveReport();
        for (Product p : products)
            def.addProd(p);
        this.defReports.add(def);
        return def;
    }

    public void addCatToStRep(int id, Category category) {
        StockReport sRep = getStoReport(id);
        if (sRep != null)
            sRep.addCategory(category);
    }

    public String exportReport(int id) {
        StockReport sRep = getStoReport(id);
        if (sRep != null) {
            return sRep.exportReport();
        } else {
            DefectiveReport dRep = getDefReport(id);
            if (dRep != null)
                return dRep.exportReport();
        }
        return "The report does not exist";
    }
    public void addProdToDefRep(int id, Product p){
        DefectiveReport defRep = getDefReport(id);
        defRep.addProd(p);
    }

    public HashMap<Integer, Integer> sendReport(){
        HashMap<Integer, Integer> report = new HashMap<>();
        List<Category> cat = new ArrayList<>();
        for(StockReport s : stockReports)
             cat = s.getCategories();
        for (Category c : cat) {
            for(Product p: c.getProducts()){
                report.put(p.getId(), p.getOrderAmount());
            }
        }
        stockReports.clear();
        return report;
    }
    public List<DefectiveReport> getDefReports() {
        return defReports;
    }

    public List<StockReport> getStockReports() {
        return stockReports;
    }
}
