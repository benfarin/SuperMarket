//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package BusinessLayer.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ReportController {
    List<DefectiveReport> defReports = new LinkedList();
    List<StockReport> stockReports = new LinkedList();
    private static int day = 3;

    public ReportController() {
    }

    public DefectiveReport getDefReport(int id) {
        Iterator var2 = this.defReports.iterator();

        DefectiveReport dRep;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            dRep = (DefectiveReport)var2.next();
        } while(dRep.getID() != id);

        return dRep;
    }

    public void setDay(int day1) {
        ReportController.day = day1;
        Iterator var2 = this.stockReports.iterator();

        while(var2.hasNext()) {
            StockReport s = (StockReport)var2.next();
            s.setDay(day1);
        }
        StockReport.day = day1;
    }

    public StockReport getStoReport(int id) {
        Iterator var2 = this.stockReports.iterator();

        StockReport sRep;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            sRep = (StockReport)var2.next();
        } while(sRep.getID() != id);

        return sRep;
    }

    public StockReport addStockReport(List<Category> categories) {
        StockReport sto = new StockReport();
        Iterator var3 = categories.iterator();

        while(var3.hasNext()) {
            Category c = (Category)var3.next();
            sto.addCategory(c);
        }

        this.stockReports.add(sto);
        return sto;
    }

    public DefectiveReport addDefReport(List<Product> products) {
        DefectiveReport def = new DefectiveReport();
        Iterator var3 = products.iterator();

        while(var3.hasNext()) {
            Product p = (Product)var3.next();
            def.addProd(p);
        }

        this.defReports.add(def);
        return def;
    }

    public boolean addCatToStRep(int id, Category category) {
        StockReport sRep = this.getStoReport(id);
        if (sRep != null && !sRep.getCategories().contains(category)) {
            sRep.addCategory(category);
            return true;
        } else {
            return false;
        }
    }

    public String exportReport(int id) {
        StockReport sRep = this.getStoReport(id);
        if (sRep != null) {
            return sRep.exportReport();
        } else {
            DefectiveReport dRep = this.getDefReport(id);
            return dRep != null ? dRep.exportReport() : "The report does not exist";
        }
    }

    public boolean addProdToDefRep(int id, Product p) {
        DefectiveReport defRep = this.getDefReport(id);
        if (defRep != null && !defRep.getDefectiveProducts().contains(p)) {
            defRep.addProd(p);
            return true;
        } else {
            return false;
        }
    }

    public int getDay() {
        return day;
    }

    public HashMap<Integer, Integer> sendReport() {
        HashMap<Integer, Integer> report = new HashMap();
        new ArrayList();
        Iterator var3 = this.stockReports.iterator();

        while(var3.hasNext()) {
            StockReport s = (StockReport)var3.next();
            List<Category> cat = s.getCategories();
            Iterator var5 = cat.iterator();

            while(var5.hasNext()) {
                Category c = (Category)var5.next();
                Iterator var7 = c.getProducts().iterator();

                while(var7.hasNext()) {
                    Product p = (Product)var7.next();
                    report.put(p.getId(), p.getOrderAmount());
                }
            }
        }

        this.stockReports.clear();
        return report;
    }

    public List<DefectiveReport> getDefReports() {
        return this.defReports;
    }

    public List<StockReport> getStockReports() {
        return this.stockReports;
    }

    public void addDefReport(DefectiveReport re) {
        this.defReports.add(re);
    }

    public void addStockReport(StockReport re) {
        this.stockReports.add(re);
    }
}
