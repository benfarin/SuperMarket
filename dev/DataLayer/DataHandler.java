package DataLayer;

import BusinessLayer.Facade;
import BusinessLayer.Inventory.DefectiveReport;
import BusinessLayer.Inventory.Product;
import BusinessLayer.Inventory.StockReport;
import BusinessLayer.Suppliers.Contract;
import BusinessLayer.Suppliers.ProductPerSup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Statement;

public class DataHandler {
    private CategoryMapper catMapper;
    private ProductMapper prodMapper;
    private ReportMapper repMapper;

    private ContractMapper contractMapper;
    private ProductPerSupMapper productPerSupMapper;
    private SupplierMapper supplierMapper;
    private OrderMapper orderMapper;

    public static Connection con;
    private Facade facade;
    public DataHandler(Facade facade) {
        this.facade = facade;
        con = null;
        connect();
        try {
            this.catMapper = new CategoryMapper(con,facade);
            this.prodMapper = new ProductMapper(con,facade);
            this.repMapper = new ReportMapper(con, facade);

            this.contractMapper = new ContractMapper();
            this.productPerSupMapper = new ProductPerSupMapper();
            this.supplierMapper = new SupplierMapper();
            this.orderMapper = new OrderMapper();


        } catch (Exception e) {
            System.out.println("initialize failed!!!!\n" + e.getMessage());
        }

    }
    public static Connection connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:dev/SQL_Inventory_Suppliers.db");
        }

        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("database successfully created");
        return  con;
    }
    //--------------CATEGORIES--------------
    public void addCatToData(String name, String super_cat, int discount, Date discountDate) {
        try {
            catMapper.addCategory(name, super_cat, discount, discountDate);
        } catch (Exception e) {
            System.out.println("got exception from database, try to add category\n" + e.getMessage());
        }
    }

    public void updateDiscounts(String name, int discount, Date discountDate) {
        try {
            catMapper.updateDiscounts(name, discount, discountDate);
        } catch (Exception e) {
            System.out.println("got exception from database, try to update discount\n" + e.getMessage());
        }
    }

    public void updateDiscDate(String name, Date discountDate) {
        try {
            catMapper.updateDiscDate(name, discountDate);
        } catch (Exception e) {
            System.out.println("got exception from database, try to update discount date\n" + e.getMessage());
        }
    }

    public void deleteCategory(String name) {
        try {
            catMapper.deleteCategory(name);
        } catch (Exception e) {
            System.out.println("got exception from database, try to delete category\n" + e.getMessage());
        }
    }

    //....................PRODUCT................
    public void addProduct(int id, String name, String manufacture, String category, int storeQuantity,
                           int storageQuantity, int discount, Date discountDate, double priceFromSupplier,
                           double priceToCustomer, int defectiveItems, int minimum, int orderAmount, Map<Double, Date> priceToCusHistory,
                           Map<Double, Date> priceFromSupHistory) {
        try {
            prodMapper.addProduct(id, name, manufacture, category, storeQuantity,
                    storageQuantity, discount, discountDate, priceFromSupplier,
                    priceToCustomer, defectiveItems, minimum, orderAmount, priceToCusHistory,
                    priceFromSupHistory);
        } catch (Exception e) {
            System.out.println("failed to add product\n" + e.getMessage());
        }
    }

    public void deleteProduct(int id) {
        try {
            prodMapper.deleteProduct(id);
        } catch (Exception e) {
            System.out.println("failed to add product\n" + e.getMessage());
        }
    }

    public void updateManufacture(int id, String manufacture) {
        try {
            prodMapper.updateManufacture(id, manufacture);
        } catch (Exception e) {
            System.out.println("failed to updateManufacture\n" + e.getMessage());
        }
    }

    public void updateCategory(int id, String category) {
        try {
            prodMapper.updateCategory(id, category);
        } catch (Exception e) {
            System.out.println("failed to updateCategory\n" + e.getMessage());
        }
    }

    public void updateName(int id, String name) {
        try {
            prodMapper.updateName(id, name);
        } catch (Exception e) {
            System.out.println("failed to updateName\n" + e.getMessage());
        }
    }

    public void updateStoreQuantity(int id, int storeQuantity) {
        try {
            prodMapper.updateStoreQuantity(id, storeQuantity);
        } catch (Exception e) {
            System.out.println("failed to updateStoreQuantity\n" + e.getMessage());
        }
    }
    public void updateStorageQuantity (int id, int storageQuantity){
        try {
            prodMapper.updateStorageQuantity(id, storageQuantity);
        } catch (Exception e) {
            System.out.println("failed to updateStorageQuantity\n" + e.getMessage());
        }
    }
    public void updateDiscount (int id, int discount,Date discountDate){
        try {
            prodMapper.updateDiscount(id, discount,discountDate);
        } catch (Exception e) {
            System.out.println("failed to updateDiscount\n" + e.getMessage());
        }
    }
    public void updateDiscountDate (int id, Date discountDate){
        try {
            prodMapper.updateDiscountDate(id, discountDate);
        } catch (Exception e) {
            System.out.println("failed to updateDiscountDate\n" + e.getMessage());
        }
    }
    public void updateOrderAmount (int id, int orderAmount){
        try {
            prodMapper.updateOrderAmount(id, orderAmount);
        } catch (Exception e) {
            System.out.println("failed to updateDiscountDate\n" + e.getMessage());
        }
    }
    public void updatePriceFromSupplier (int id, double priceFromSupplier){
        try {
            prodMapper.updatePriceFromSupplier(id, priceFromSupplier);
        } catch (Exception e) {
            System.out.println("failed to updatePriceFromSupplier\n" + e.getMessage());
        }
    }
    public void updatePriceToCustomer (int id, double priceToCustomer){
        try {
            prodMapper.updatePriceToCustomer(id, priceToCustomer);
        } catch (Exception e) {
            System.out.println("failed to updatePriceToCustomer\n" + e.getMessage());
        }
    }
    public void updateDefectiveItems (int id, int defectiveItems){
        try {
            prodMapper.updateDefectiveItems(id, defectiveItems);
        } catch (Exception e) {
            System.out.println("failed to updateDefectiveItems\n" + e.getMessage());
        }
    }
    public void updateMinimum (int id, int minimum){
        try {
            prodMapper.updateMinimum(id, minimum);
        } catch (Exception e) {
            System.out.println("failed to updateMinimum\n" + e.getMessage());
        }
    }
    public void insertPriceFromSupHistoryRecord (int id, double price, Date date){
        try {
            prodMapper.insertPriceFromSupHistoryRecord(id, price,date);
        } catch (Exception e) {
            System.out.println("failed to updatePriceFromSupHistory\n" + e.getMessage());
        }
    }
    public void insertPriceToCusHistoryRecord (int id, double price, Date date){
        try {
            prodMapper.insertPriceToCusHistoryRecord(id, price,date);
        } catch (Exception e) {
            System.out.println("failed to updatePriceToCusHistory\n" + e.getMessage());
        }
    }
    public HashMap<Integer, Product> getProducts(){
       return prodMapper.getProducts();
    }
    //............REPORT.............
    public void deleteStockReport(int id){
        try {
            repMapper.deleteStockReport(id);
        } catch (Exception e) {
            System.out.println("failed to deleteStockReport\n" + e.getMessage());
        }

    }
    public void deleteDefectiveReport(int id){
        try {
            repMapper.deleteDefectiveReport(id);
        } catch (Exception e) {
            System.out.println("failed to deleteDefectiveReport\n" + e.getMessage());
        }
    }
    public void addStockCat(int id, String cat){
        try {
            repMapper.addStockCat(id,cat);
        } catch (Exception e) {
            System.out.println("failed to addStockCat\n" + e.getMessage());
        }
    }
    public void addStock(int id, Date date, List<String> cat){
        try {
            repMapper.addStock(id,date,cat);
        } catch (Exception e) {
            System.out.println("failed to addStockCat\n" + e.getMessage());
        }
    }
    public void addDefective(int id, Date date, List<Integer> prod){
        try {
            repMapper.addDefective(id,date,prod);
        } catch (Exception e) {
            System.out.println("failed to addDefective\n" + e.getMessage());
        }
    }
    public void addDefectiveProd(int id, int prod){
        try {
            repMapper.addDefectiveProd(id,prod);
        } catch (Exception e) {
            System.out.println("failed to addDefectiveProd\n" + e.getMessage());
        }
    }
    public void deleteStockCat(int id, String cat){
        try {
            repMapper.deleteStockCat(id,cat);
        } catch (Exception e) {
            System.out.println("failed to deleteStockCat\n" + e.getMessage());
        }
    }
    public void deleteDefectiveProd(int id, int pid){
        try {
            repMapper.deleteDefectiveProd(id,pid);
        } catch (Exception e) {
            System.out.println("failed to deleteDefectiveProd\n" + e.getMessage());
        }
    }
    public HashMap<Integer, StockReport> getStockReports(){
        return repMapper.getStockReports();
    }
    public HashMap<Integer, DefectiveReport> getDefectiveReports() {
        return repMapper.getDefectiveReports();
    }
}
