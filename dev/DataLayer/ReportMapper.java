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
    private Connection con;
        private Facade facade;

        public ReportMapper(Connection con) throws SQLException {
            this.con = con;
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
                ResultSet res1 = stmt1.executeQuery("SELECT * FROM StockReport WHERE rid="+id);
                StockReport re = new StockReport(id, date);
                while(res1.next()) {
                    stock = true;
                    re.addCategory(facade.getCategory(res1.getString("category_name")));
                }
                if (stock) {
                    stockReports.put(id, re);

                }
                else{
                    res1 = stmt1.executeQuery("SELECT * FROM DefectiveReport WHERE rid="+id);
                    DefectiveReport re1 = new DefectiveReport(id, date);
                    while(res1.next()) {
                        re1.addProd(facade.getProdByID(res1.getInt("pid")));
                    }
                        defectiveReports.put(id, re1);
                        stock = false;


                }
            }
        }

    public void deleteStockReport(int id)throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("DELETE FROM Report WHERE rid ="+id);
        res = stmt.executeQuery("DELETE FROM StockReport WHERE rid ="+id);
    }
    public void deleteDefectiveReport(int id)throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("DELETE FROM Report WHERE rid ="+id);
        res = stmt.executeQuery("DELETE FROM DefectiveReport WHERE rid ="+id);
    }
    public void addStockCat(int id, String cat) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("INSERT INTO StockReport (rid, category_name) VALUES ("+id+","+cat+")");
    }
    public void addStock(int id, Date date, List<String> cat) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("INSERT INTO Report (rid, date) VALUES ("+id+","+date+")");
        for(String s : cat){
            addStockCat(id, s);
        }
    }
    public void addDefective(int id, Date date, List<Integer> prod) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("INSERT INTO Report (rid, date) VALUES ("+id+","+date+")");
        for(Integer p : prod){
            addDefectiveProd(id, p);
        }
    }
    public void addDefectiveProd(int id, int prod) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("INSERT INTO DefectiveReport (rid, pid) VALUES ("+id+","+prod+")");
    }

    public void deleteStockCat(int id, String cat)throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("DELETE FROM StockReport WHERE rid = "+id+" AND category_name = "+cat);
    }
    public void deleteDefectiveProd(int id, int pid)throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("DELETE FROM DefectiveReport WHERE rid = "+id+" AND pid = "+pid);
    }
    public HashMap<Integer, StockReport> getStockReports() {
        return stockReports;
    }
    public HashMap<Integer, DefectiveReport> getDefectiveReports() {
        return defectiveReports;
    }
}


