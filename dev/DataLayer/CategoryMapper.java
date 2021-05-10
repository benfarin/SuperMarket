package DataLayer;

import BusinessLayer.Facade;
import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.Product;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CategoryMapper {

    private HashMap<String, Category> categories;
    private Facade facade;
    Connection con;

    public CategoryMapper(Connection con,Facade facade) throws SQLException {
        this.con = con;
        this.facade = facade;
        categories = new HashMap<>();
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM Category");
        while (res.next()) {
            String name = res.getString("name");
//            String sup_cat = res.getString("super_cat");
//            Category supCat = facade.getCategory(sup_cat);
            int discount = res.getInt("discount");
            Date discountDate = res.getDate("discountDate");
            facade.addCatFromData(name, null, discount, discountDate);
//            categories.put(name,facade.addCatFromData(name, null, discount, discountDate));
        }
        res = stmt.executeQuery("SELECT * FROM Category");
        while (res.next()) {
            String name = res.getString("name");
            String sup_cat = res.getString("super_cat");
            facade.addSub(sup_cat,name);
            }
        res = stmt.executeQuery("SELECT * FROM Category");
        while (res.next()) {
            String name = res.getString("name");
            Category cat = facade.getCategory(name);
            categories.put(name, cat);
        }


//        for (Category c : categories.values()) {
//            if (c.getSupCategory() != null) {
//                facade.addSub(c.getSupCategory().getName(), c.getName());
//            }
//        }
    }
    public void addCategory(String name,String super_cat, int discount, java.util.Date discountDate) throws SQLException{
        String sql = "INSERT INTO Category(name,super_cat,discount,discountDate) VALUES(?,?,?,?)";
        java.sql.Date sqlDate;
        if(discountDate!=null)
            sqlDate = new java.sql.Date(discountDate.getTime());
        else
            sqlDate = null;
        try (
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, super_cat);
            pstmt.setInt(3, discount);
            pstmt.setDate(4, sqlDate);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // ResultSet res = stmt.executeQuery("INSERT INTO Category(name,super_cat,discount,discountDate) VALUES ("+name+","+super_cat+","+discount+","+discountDate+");");
    }
    public void deleteCategory(String name) throws SQLException{
        String sql = "DELETE FROM Category WHERE name=?";
        try{
            for (Category c : categories.values()) {
                if (c.getSupCategory()!=null) {
                    if (c.getSupCategory().getName().equals(name)) {
                        addSup(null, c.getName());
                    }
                }
            }
//            for (Product p : facade.getCategory(name).getProducts()) {
//                if (c.getSupCategory().getName().equals(name)) {
//                    addSup(null, c.getName());
//                }
//            }
            categories.remove(name);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addSup(String super_cat, String cat) throws SQLException{
        String sql = "UPDATE Category SET super_cat = ? "
                + "WHERE name = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,super_cat);
            pstmt.setString(2,cat);
            pstmt.executeUpdate();
            Category c = facade.getCategory(cat);
            c.setSupCategory(facade.getCategory(super_cat));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateDiscounts(String name, int discount, java.util.Date discountDate) throws SQLException {
        String sql = "UPDATE Category SET discount = ? , "
                + "discountDate = ? "
                + "WHERE name = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, discount);
            java.sql.Date sqlDate = new Date(discountDate.getTime());
            pstmt.setDate(2, sqlDate);
            pstmt.setString(3, name);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateDiscDate(String name, java.util.Date discountDate) throws SQLException {
        String sql = "UPDATE Category SET discountDate = ? "
                + "WHERE name = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            java.sql.Date sqlDate = new Date(discountDate.getTime());
            pstmt.setDate(1, sqlDate);
            pstmt.setString(2, name);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
