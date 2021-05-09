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
import java.util.Map;
import java.util.Set;

public class ProductMapper {


    private HashMap<Integer, Product> products;
    private Connection con=null;
    private Facade facade;

    public ProductMapper() throws SQLException {
        products =new HashMap<>();
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM Product");

        while(res.next())
        {
            String name = res.getString("name");
            String category = res.getString("category");
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
            int orderAmount = res.getInt("orderAmount");
            ResultSet res1 = stmt.executeQuery("SELECT * FROM PriceToCusHistory WHERE pid="+id);
            Map<Double,Date> priceToCusHistory = new HashMap<>();
            while(res1.next())
                priceToCusHistory.put(res1.getDouble("price"),res1.getDate("date"));
            res1 = stmt.executeQuery("SELECT * FROM PriceFromSupHistory WHERE pid="+id);
            Map<Double,Date> priceFromSupHistory = new HashMap<>();
            while(res1.next())
                priceFromSupHistory.put(res1.getDouble("price"),res1.getDate("date"));

            products.put(id, new Product(id, name, manufacture, category1, storeQuantity, storageQuantity, discount, discountDate, priceFromSupplier, priceToCustomer, defectiveItem, minimum, orderAmount, priceToCusHistory, priceFromSupHistory));
        }
    }

    public void addProduct(int id, String name, String manufacture, String category, int storeQuantity,
                           int storageQuantity, int discount, Date discountDate, double priceFromSupplier,
                           double priceToCustomer, int defectiveItems, int minimum, int orderAmount, Map<Double, Date> priceToCusHistory,
                           Map<Double, Date> priceFromSupHistory) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("INSERT INTO Product (pid, name, category, manufacture, storeQuantity, storageQuantity, discount, discountDate, orderAmount, priceFromSupplier, priceToCustomer, defectiveItems, minimum) " +
                "VALUES ("+id+", "+name+","+category+","+manufacture+", "+storeQuantity+","+storageQuantity+","+discount+","+discountDate+","+orderAmount+","+priceFromSupplier+","+priceToCustomer+","+defectiveItems+","+minimum+")");
        for(Map.Entry s : priceToCusHistory.entrySet())
            res = stmt.executeQuery("INSERT INTO PriceToCusHistory (price, date, pid) VALUES ("+s.getKey()+","+s.getValue()+","+id+")");
        for(Map.Entry s : priceFromSupHistory.entrySet())
            res = stmt.executeQuery("INSERT INTO PriceFromSupHistory (price, date, pid) VALUES ("+s.getKey()+","+s.getValue()+","+id+")");

    }

    public void deleteProduct(int id)throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("DELETE FROM Product WHERE pid ="+id);
    }

    public void updateManufacture (int id, String manufacture) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Product SET manufacture = "+manufacture+" WHERE pid = "+id);
    }
    public void updateCategory (int id, String category) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Product SET category = "+category+" WHERE pid = "+id);
    }
    public void updateName (int id, String name) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Product SET name = "+name+" WHERE pid = "+id);
    }
    public void updateStoreQuantity (int id, int storeQuantity) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Product SET storeQuantity = "+storeQuantity+" WHERE pid = "+id);
    }
    public void updateStorageQuantity (int id, int storageQuantity) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Product SET storageQuantity = "+storageQuantity+" WHERE pid = "+id);
    }
    public void updateDiscount (int id, int discount) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Product SET discount = "+discount+" WHERE pid = "+id);
    }
    public void updateDiscountDate (int id, Date discountDate) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Product SET discountDate = "+discountDate+" WHERE pid = "+id);
    }
    public void updateOrderAmount (int id, int orderAmount) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Product SET orderAmount = "+orderAmount+" WHERE pid = "+id);
    }
    public void updatePriceFromSupplier (int id, int priceFromSupplier) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Product SET priceFromSupplier = "+priceFromSupplier+" WHERE pid = "+id);
    }
    public void updatePriceToCustomer (int id, int priceToCustomer) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Product SET priceToCustomer = "+priceToCustomer+" WHERE pid = "+id);
    }
    public void updateDefectiveItems (int id, int defectiveItems) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Product SET defectiveItems = "+defectiveItems+" WHERE pid = "+id);
    }
    public void updateMinimum (int id, int minimum) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("UPDATE Product SET minimum = "+minimum+" WHERE pid = "+id);
    }
    public void updatePriceFromSupHistory (int id, double price, Date date) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("INSERT INTO PriceFromSupHistory (price, date, pid) VALUES ("+price+","+date+","+id+")");
    }
    public void updatePriceToCusHistory (int id, double price, Date date) throws SQLException{
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("INSERT INTO PriceToCusHistory (price, date, pid) VALUES ("+price+","+date+","+id+")");
    }

    public HashMap<Integer, Product> getProducts() {
        return products;
    }

}
