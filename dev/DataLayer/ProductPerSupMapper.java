package DataLayer;

import BusinessLayer.Suppliers.ProductPerSup;
import BusinessLayer.Suppliers.Supplier;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ProductPerSupMapper {
    private Connection con = null;
    private static HashMap<Long, LinkedList<ProductPerSup>> products;

    public ProductPerSupMapper(Connection con) throws SQLException {

        products = new HashMap<>();


        this.con = con;
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT DISTINCT pid FROM ProductPerSupplier");

        while (res.next()) {
            Long productID = res.getLong("pid");
            products.put(productID, getProducts(productID));

        }
    }

    public LinkedList<ProductPerSup> getProducts(Long productID) throws SQLException {
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM ProductPerSupplier INNER JOIN Product ON ProductPerSupplier.pid=Product.pid WHERE Product.pid=" + productID + ";");
        LinkedList<ProductPerSup> productsList = new LinkedList<>();

        while (res.next()) {
            int supplierID = res.getInt("sid");
            double price = res.getDouble("price");
            Long supplierSerialNum = res.getLong("supSerialNum");
            String name = res.getString("name");
            Long storeCode = res.getLong("pid");
            ProductPerSup prodToAdd = new ProductPerSup(name, storeCode, price, getHighAmountDiscount(productID, supplierID), supplierSerialNum, getSupplier(supplierID));

            // This line puts the ProductPerSup in the Supplier of BL
            SupplierMapper.getSuppliers().get(supplierID).addProductFromDB(prodToAdd);

            productsList.add(prodToAdd);
        }
        return productsList;
    }

        private HashMap<Integer, Double> getHighAmountDiscount(Long productID, int supplierID) throws SQLException {
            Statement stmt = this.con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM DiscountProducts WHERE sid="+supplierID+" AND pid="+productID);
            HashMap<Integer, Double> discountsMap = new HashMap<>();

            while(res.next())
            {
                int amount = res.getInt("amount");
                double discount = res.getDouble("discount");
                discountsMap.put(amount, discount);
            }
            return discountsMap;
        }

        private Supplier getSupplier( int supplierID) {

            return SupplierMapper.getSuppliers().get(supplierID);

        }

    public void addNewProductPerSupplier(int pid,int price, int sid, int supSerialNum ) throws SQLException {
        String sql = "INSERT INTO ProductPerSupplier(pid,price,sid,supSerialNum) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, pid);
            pstmt.setInt(2, price);
            pstmt.setInt(3, sid);
            pstmt.setInt(4, supSerialNum);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static HashMap<Long, LinkedList<ProductPerSup>> getMapOfAllProducts() {
        return products;
    }
}