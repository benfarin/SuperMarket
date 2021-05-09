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

public class ProductMapper {


        private HashMap<String, Product> products;
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
                Date discountDate =


                products.put(id, new Product(id, name, manufacture, category, storeQuantity, storageQuantity, discount, discountDate, priceFromSupplier, priceToCustomer, defectiveItem, minimum, orderAmount, priceToCusHistory, priceFromSupHistory);
            }
        }

        public HashMap<Integer, Integer> setDiscounts(int supplierID) throws SQLException {
            Statement stmt = this.con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM DiscountContract WHERE sid="+supplierID+";");
            HashMap<Integer, Integer> discountsMap = new HashMap<>();

            while(res.next())
            {
                int amount = res.getInt("amount");
                int discount = res.getInt("discount");
                discountsMap.put(amount, discount);
            }
            return discountsMap;
        }

//        public HashMap<Integer, Contract> getProducts() {
//            return products;
//        }
    }