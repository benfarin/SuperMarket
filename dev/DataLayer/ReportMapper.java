package DataLayer;

import BusinessLayer.Facade;
import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.DefectiveReport;
import BusinessLayer.Inventory.Product;
import BusinessLayer.Inventory.StockReport;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ReportMapper {

    private HashMap<Integer, StockReport> stockReports;
    private HashMap<Integer, DefectiveReport> defectiveReports;
    Connection con;
    private Facade facade;
    int day;

    public ReportMapper(Connection con, Facade facade) throws SQLException {
        this.con = con;
        this.facade = facade;
        stockReports =new HashMap<>();
        defectiveReports =new HashMap<>();
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
            List<String> c = new LinkedList<>();
            String c_name = "";
            while(res1.next()) {
                stock = true;
                c_name = res1.getString("category_name");
                int day = res1.getInt("day");
                re.setDay(day);
                this.day = day;
                re.addCategory(facade.getCategory(c_name));
                c.add(c_name);
            }
            if (stock) {
                stockReports.put(id, re);
                facade.addStockReportFromData(re);
                stock = false;
                continue;
            }
            else{
                res1 = stmt1.executeQuery("SELECT * FROM DefectiveReport WHERE rid="+id);
                DefectiveReport re1 = new DefectiveReport(id, date);
                List<Integer> p = new LinkedList<>();
                int pid = 0;
                while(res1.next()) {
                    pid = res1.getInt("pid");
                    re1.addProd(facade.getProdByID(pid));
                    p.add(pid);
                }
                defectiveReports.put(id, re1);
                facade.addDefectiveReportFromData(re1);
                stock = false;

            }
        }
    }

    public void updateDay(int day){
        String sql = "UPDATE StockReport SET day = ? WHERE rid > 0";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, day);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteStockReport(int id)throws SQLException{
        String sql = "DELETE FROM Report WHERE rid=?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "DELETE FROM StockReport WHERE rid=?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteDefectiveReport(int id)throws SQLException{
        String sql = "DELETE FROM Report WHERE rid=?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "DELETE FROM DefectiveReport WHERE rid=?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addStockCat(int id, String cat, int day) throws SQLException{
        String sql = "INSERT INTO StockReport (rid, category_name, day) VALUES(?,?,?)";
        try (
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, cat);
            pstmt.setInt(3, day);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addStock(int id, Date date, List<String> cat) throws SQLException{
        String sql = "INSERT INTO Report (rid, date) VALUES(?,?)";
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try (
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setDate(2, sqlDate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        for(String s : cat){
            addStockCat(id, s, facade.getDay());
        }
    }
    public void addDefective(int id, Date date, List<Integer> prod) throws SQLException{
        String sql = "INSERT INTO Report (rid, date) VALUES(?,?)";
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try (
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setDate(2, sqlDate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        for(Integer p : prod){
            addDefectiveProd(id, p);
        }
    }
    public void addDefectiveProd(int id, int prod) throws SQLException{
        String sql = "INSERT INTO DefectiveReport (rid, pid) VALUES(?,?)";
        try (
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, prod);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteStockCat(int id, String cat)throws SQLException{
        String sql = "DELETE FROM StockReport WHERE rid = (?) AND category_name = (?)";
        try (
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, cat);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void deleteDefectiveProd(int id, int pid)throws SQLException{
        String sql = "DELETE FROM DefectiveReport WHERE rid = (?) AND pid = (?)";
        try (
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, pid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public HashMap<Integer, StockReport> getStockReports() {
        return stockReports;
    }
    public HashMap<Integer, DefectiveReport> getDefectiveReports() {
        return defectiveReports;
    }
}


