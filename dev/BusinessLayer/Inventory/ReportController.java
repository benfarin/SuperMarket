package BusinessLayer.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ReportController {
    List<DefectiveReport> defReports;
    List<StockReport> stockReports;
    private static int day = 3;


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

    public void setDay(int day){
        this.day = day;
        for (StockReport s : stockReports){
            s.setDay(day);
        }
    }

    public StockReport getStoReport(int id) {
        for (StockReport sRep : stockReports) {
            if (sRep.getID() == id)
                return sRep;
        }
        return null;
    }

    public StockReport addStockReport(List<Category> categories) {
        StockReport sto = new StockReport();
        for (Category c : categories)
            sto.addCategory(c);
        this.stockReports.add(sto);
        return sto;
    }

    public DefectiveReport addDefReport(List<Product> products) {
        DefectiveReport def = new DefectiveReport();
        for (Product p : products)
            def.addProd(p);
        this.defReports.add(def);
        return def;
    }

    public boolean addCatToStRep(int id, Category category) {
        StockReport sRep = getStoReport(id);
        if (sRep != null && !sRep.getCategories().contains(category)){
            sRep.addCategory(category);
        return true;
        }
        return false;
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

    public boolean addProdToDefRep(int id, Product p){
        DefectiveReport defRep = getDefReport(id);
        if (defRep != null && !defRep.getDefectiveProducts().contains(p)){
            defRep.addProd(p);
            return true;}
        return false;
    }
    public int getDay(){
        return day;
    }

    public HashMap<Integer, Integer> sendReport(){
        HashMap<Integer, Integer> report = new HashMap<>();
        List<Category> cat = new ArrayList<>();
        for(StockReport s : stockReports){
             cat = s.getCategories();
        for (Category c : cat) {
            for(Product p: c.getProducts()){
                report.put(p.getId(), p.getOrderAmount());
            }}
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

    public void addDefReport(DefectiveReport re) {
        defReports.add(re);
    }

    public void addStockReport(StockReport re) {
        stockReports.add(re);
    }
}
