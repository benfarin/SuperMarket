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
                ResultSet res1 = stmt.executeQuery("SELECT * FROM PriceToCusHistoryWHERE pid="+id);
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

        public HashMap<Integer, Product> getProducts() {
            return products;
        }

}
