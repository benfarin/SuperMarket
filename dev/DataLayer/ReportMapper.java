package DataLayer;

import BusinessLayer.Facade;
import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.DefectiveReport;
import BusinessLayer.Inventory.Product;
import BusinessLayer.Inventory.StockReport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ReportMapper {

        private HashMap<Integer, StockReport> stockReports;
    private HashMap<Integer, DefectiveReport> defectiveReports;
    private Connection con=null;
        private Facade facade;

        public ReportMapper() throws SQLException {
            stockReports =new HashMap<>();
            Statement stmt = this.con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM Report");
            List<Category> categories1;
            boolean stock = false;
            while(res.next())
            {
                int id = res.getInt("rid");
                Date date = res.getDate("date");
                Statement stmt1 = this.con.createStatement();
                ResultSet res1 = stmt1.executeQuery("SELECT * FROM StockReport WHERE pid="+id);
                StockReport re = new StockReport(id, date);
                while(res1.next()) {
                    stock = true;
                    re.addCategory(facade.getCategory(res1.getString("category_name")));
                }
                if (stock) {
                    stockReports.put(id, re);

                }
                else{
                    res1 = stmt1.executeQuery("SELECT * FROM DefectiveReport WHERE pid="+id);
                    DefectiveReport re1 = new DefectiveReport(id, date);
                    while(res1.next()) {
                        re1.addProd(facade.getProdByID(res1.getInt("pid")));
                    }
                    if (stock) {
                        defectiveReports.put(id, re1);

                    }

                }
            }
        }

//        public HashMap<Integer, Integer> setDiscounts(int supplierID) throws SQLException {
//            Statement stmt = this.con.createStatement();
//            ResultSet res = stmt.executeQuery("SELECT * FROM DiscountContract WHERE sid="+supplierID+";");
//            HashMap<Integer, Integer> discountsMap = new HashMap<>();
//
//            while(res.next())
//            {
//                int amount = res.getInt("amount");
//                int discount = res.getInt("discount");
//                discountsMap.put(amount, discount);
//            }
//            return discountsMap;
//        }


    public HashMap<Integer, StockReport> getStockReports() {
        return stockReports;
    }

    public HashMap<Integer, DefectiveReport> getDefectiveReports() {
        return defectiveReports;
    }
}


