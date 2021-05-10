package DataLayer;

import BusinessLayer.Facade;
import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CategoryMapper {

    private HashMap<String, Category> categories;
    private Facade facade;
    Connection con;

    public CategoryMapper(Connection con) throws SQLException {
        this.con = con;
        categories = new HashMap<>();
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM Category");
        while (res.next()) {
            String name = res.getString("name");
            String sup_cat = res.getString("sup_cat");
            Category supCat = facade.getCategory(sup_cat);
            int discount = res.getInt("discount");
            Date discountDate = res.getDate("discountDate");
            categories.put(name, new Category(name, supCat, new LinkedList<>(), new LinkedList<>(), discount, discountDate));
        }
        for (Category c : categories.values()) {
            if (c.getSupCategory() != null) {
                facade.addSub(c.getSupCategory().getName(), c.getName());
            }
        }
    }
    public void addCategory(String name,String super_cat, int discount, Date discountDate) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("INSERT INTO Category (name,super_cat,discount,discountDate) VALUES ("+name+","+super_cat+","+discount+","+discountDate+");");
    }
    public void deleteCategory(String name) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("DELETE FROM Category WHERE name="+name+";");
    }
    public void updateDiscounts(String name, int discount, Date discountDate) throws SQLException {
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Category SET discount =" + discount + ", discountDate =" + discountDate + " WHERE category_name=" + name);
    }

    public void updateDiscDate(String name, Date discountDate) throws SQLException {
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Category SET discountDate =" + discountDate + " WHERE category_name=" + name);
    }
}
