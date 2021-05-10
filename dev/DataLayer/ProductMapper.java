package DataLayer;

import BusinessLayer.Facade;
import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.Product;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProductMapper {


    private HashMap<Integer, Product> products;
    private Connection con;
    private Facade facade;

    public ProductMapper(Connection con,Facade facade) throws SQLException {
        this.con = con;
        this.facade = facade;
        products =new HashMap<>();
        Statement stmt1 = this.con.createStatement();
        ResultSet res = stmt1.executeQuery("SELECT * FROM Product");

        while(res.next())
        {
            String name = res.getString("name");
            String category = res.getString("category_name");
            Category category1 = facade.getCategory(category);
            String manufacture = res.getString("manufacture");
            int priceFromSupplier = res.getInt("priceFromSupplier");
            int priceToCustomer = res.getInt("priceToCustomer");
            int minimum = res.getInt("minimum");
            int id = res.getInt("pid");
            int storeQuantity = res.getInt("storeQuantity");
            int storageQuantity = res.getInt("storageQuantity");
            int discount = res.getInt("discount");
            Date discountDate = res.getDate("discountDate");
            int defectiveItem = res.getInt("defectiveItems");
            Statement stmt = this.con.createStatement();
            ResultSet res1 = stmt.executeQuery("SELECT * FROM PriceToCusHistory WHERE pid="+id);
            Map<Double,Date> priceToCusHistory = new HashMap<>();
            while(res1.next())
                priceToCusHistory.put(res1.getDouble("price"),res1.getDate("date"));
            res1 = stmt.executeQuery("SELECT * FROM PriceFromSupHistory WHERE pid="+id);
            Map<Double,Date> priceFromSupHistory = new HashMap<>();
            while(res1.next())
                priceFromSupHistory.put(res1.getDouble("price"),res1.getDate("date"));

            products.put(id,facade.addProductFromData(id, name, manufacture, category1, storeQuantity, storageQuantity, discount, discountDate, priceFromSupplier, priceToCustomer, defectiveItem, minimum, priceToCusHistory, priceFromSupHistory));
        }

        int lastId = 0;
        for (Integer id : products.keySet()) {
            if (id > lastId)
                lastId = id;
        }
        facade.setFirstId(lastId);

    }
    public void addProduct(int id, String name, String manufacture, String category, int storeQuantity,
                           int storageQuantity, int discount, Date discountDate, double priceFromSupplier,
                           double priceToCustomer, int defectiveItems, int minimum, Map<Double, Date> priceToCusHistory,
                           Map<Double, Date> priceFromSupHistory) throws SQLException{
        String sql = "INSERT INTO Product (pid, name,category_name, manufacture, storeQuantity, storageQuantity, discount, discountDate, priceFromSupplier, priceToCustomer, defectiveItems, minimum) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        java.sql.Date sqlDate;
        if(discountDate!=null)
            sqlDate = new java.sql.Date(discountDate.getTime());
        else
            sqlDate = null;
        try (
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, category);
            pstmt.setString(4, manufacture);
            pstmt.setInt(5,storeQuantity);
            pstmt.setInt(6, storageQuantity);
            pstmt.setInt(7, discount);
            pstmt.setDate(8, sqlDate);
            pstmt.setDouble(9,priceFromSupplier);
            pstmt.setDouble(10,priceToCustomer);
            pstmt.setInt(11, defectiveItems);
            pstmt.setInt(12, minimum);


            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//        for(Map.Entry s : priceToCusHistory.entrySet()) {
//            sql = "INSERT INTO PriceToCusHistory (price, date, pid) VALUES(?,?,?)";
//            PreparedStatement pstmt = con.prepareStatement(sql);
//            pstmt.setInt(1, s.getKey());
//            pstmt.setString(2, name);
//            pstmt.setInt(3, id);
//        }
//            res = stmt.executeQuery("INSERT INTO PriceToCusHistory (price, date, pid) VALUES ("+s.getKey()+","+s.getValue()+","+id+")");
//        for(Map.Entry s : priceFromSupHistory.entrySet())
//            res = stmt.executeQuery("INSERT INTO PriceFromSupHistory (price, date, pid) VALUES ("+s.getKey()+","+s.getValue()+","+id+")");

    }

    public void deleteProduct(int id)throws SQLException{
        String sql = "DELETE FROM Product WHERE pid=?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateManufacture (int id, String manufacture) throws SQLException{
        String sql = "UPDATE Product SET manufacture = ? "
                + "WHERE pid = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, manufacture);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateCategory (int id, String category) throws SQLException{
        String sql = "UPDATE Product SET category = ? "
                + "WHERE pid = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, category);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateName (int id, String name) throws SQLException{
        String sql = "UPDATE Product SET name = ? "
                + "WHERE pid = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateStoreQuantity (int id, int storeQuantity) throws SQLException{
        String sql = "UPDATE Product SET storeQuantity = ? "
                + "WHERE pid = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, storeQuantity);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateStorageQuantity (int id, int storageQuantity) throws SQLException{
        String sql = "UPDATE Product SET storageQuantity = ? "
                + "WHERE pid = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, storageQuantity);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateDiscount (int id, int discount,Date discountDate) throws SQLException{
        String sql = "UPDATE Product SET discount = ? , "
                + "discountDate = ? "
                + "WHERE pid = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, discount);
            java.sql.Date sqlDate = new java.sql.Date(discountDate.getTime());
            pstmt.setDate(2, sqlDate);
            pstmt.setInt(3, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void updateDiscountDate (int id, Date discountDate) throws SQLException{
        String sql = "UPDATE Product SET discountDate = ?"
                + "WHERE pid = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            java.sql.Date sqlDate = new java.sql.Date(discountDate.getTime());
            pstmt.setDate(1, sqlDate);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void updateOrderAmount (int id, int orderAmount) throws SQLException{
        String sql = "UPDATE Product SET orderAmount = ? "
                + "WHERE pid = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderAmount);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updatePriceFromSupplier (int id, double priceFromSupplier) throws SQLException{
        String sql = "UPDATE Product SET priceFromSupplier = ? "
                + "WHERE pid = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setDouble(1, priceFromSupplier);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        java.sql.Date sDate = new java.sql.Date(System.currentTimeMillis());
        insertPriceFromSupHistoryRecord(id,priceFromSupplier,sDate);
    }
    public void updatePriceToCustomer (int id, double priceToCustomer) throws SQLException{
        String sql = "UPDATE Product SET priceToCustomer = ? "
                + "WHERE pid = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setDouble(1, priceToCustomer);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        java.sql.Date sDate = new java.sql.Date(System.currentTimeMillis());
        insertPriceToCusHistoryRecord(id,priceToCustomer,sDate);
    }
    public void updateDefectiveItems (int id, int defectiveItems) throws SQLException{
        String sql = "UPDATE Product SET defectiveItems = ? "
                + "WHERE pid = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, defectiveItems);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateMinimum (int id, int minimum) throws SQLException{
        String sql = "UPDATE Product SET minimum = ? "
                + "WHERE pid = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, minimum);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertPriceFromSupHistoryRecord (int id, double price, Date date) throws SQLException{
        String sql = "INSERT INTO PriceFromSupHistory (price, date,pid) VALUES(?,?,?)";
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try (
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setDouble(1, price);
            pstmt.setDate(2, sqlDate);
            pstmt.setInt(3, id);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertPriceToCusHistoryRecord (int id, double price, Date date) throws SQLException{
        String sql = "INSERT INTO PriceToCusHistory (price, date,pid) VALUES(?,?,?)";
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try (
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setDouble(1, price);
            pstmt.setDate(2, sqlDate);
            pstmt.setInt(3, id);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public HashMap<Integer, Product> getProducts() {
        return products;
    }

}
