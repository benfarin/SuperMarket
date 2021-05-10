package DataLayer;

import BusinessLayer.Suppliers.ProductPerSup;
import BusinessLayer.Suppliers.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ProductPerSupMapper {
    private Connection con = null;
    private HashMap<Long, LinkedList<ProductPerSup>> products;

    public ProductPerSupMapper(){

    }

    public List<ProductPerSup> getProducts(int productID) throws SQLException {
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM ProductPerSupplier INNER JOIN Product ON ProductPerSupplier.pid=Product.pid WHERE Product.pid=" + productID + ";");
        List<ProductPerSup> productsList = new LinkedList<>();

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

        private HashMap<Integer, Double> getHighAmountDiscount(int productID, int supplierID) throws SQLException {
            Statement stmt = this.con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM DiscountProduct WHERE sid="+supplierID+" AND pid="+productID+";");
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

}