package DataLayer;

import BusinessLayer.Facade;
//import BusinessLayer.Inventory.DefectiveReport;
//import BusinessLayer.Inventory.Product;
//import BusinessLayer.Inventory.StockReport;
import BusinessLayer.Inventory.*;


import java.sql.*;
import java.util.*;
import java.util.Date;

public class DataHandler {
    private CategoryMapper catMapper;
    private ProductMapper prodMapper;
    private ReportMapper repMapper;

    public ContractMapper contractMapper;
    public SupplierMapper supplierMapper;
    public ProductPerSupMapper productPerSupMapper;

    public OrderMapper orderMapper;

    private Connection con;
    private Facade facade;
    public DataHandler(Facade facade) {
        this.facade = facade;
        con = null;
        connect();
        try {
            this.catMapper = new CategoryMapper(con,facade);
            this.prodMapper = new ProductMapper(con,facade);
            this.repMapper = new ReportMapper(con, facade);

            this.contractMapper = new ContractMapper(con);
            this.supplierMapper = new SupplierMapper(con);
            this.productPerSupMapper = new ProductPerSupMapper(con);
            this.orderMapper = new OrderMapper(con);

        } catch (Exception e) {
            System.out.println("initialize failed!!!!\n" + e.getMessage());
        }

    }
    public Connection connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:ADSS_Group_I'\'dev'\'SQL_Inventory_Suppliers.db");
        }

        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("database successfully created");
        String sql = "CREATE TABLE IF NOT EXISTS \"Category\" (\n" +
                "\t\"name\"\tTEXT NOT NULL,\n" +
                "\t\"super_cat\"\tTEXT,\n" +
                "\t\"discount\"\tINTEGER,\n" +
                "\t\"discountDate\"\tDATE,\n" +
                "\tPRIMARY KEY(\"name\"),\n" +
                "\tFOREIGN KEY(\"super_cat\") REFERENCES \"Category\"(\"name\") ON UPDATE CASCADE ON DELETE CASCADE\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql ="CREATE TABLE IF NOT EXISTS \"DefectiveReport\" (\n" +
                "\t\"rid\"\tINTEGER,\n" +
                "\t\"pid\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"rid\") REFERENCES \"Report\"(\"rid\"),\n" +
                "\tPRIMARY KEY(\"rid\",\"pid\"),\n" +
                "\tFOREIGN KEY(\"pid\") REFERENCES \"Product\"(\"pid\") ON DELETE RESTRICT ON UPDATE CASCADE\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "CREATE TABLE IF NOT EXISTS \"PriceFromSupHistory\" (\n" +
                "\t\"price\"\tREAL NOT NULL,\n" +
                "\t\"date\"\tDATE,\n" +
                "\t\"pid\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"pid\") REFERENCES \"Product\"(\"pid\") ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tPRIMARY KEY(\"pid\",\"date\")\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "CREATE TABLE IF NOT EXISTS \"PriceToCusHistory\" (\n" +
                "\t\"price\"\tREAL NOT NULL,\n" +
                "\t\"date\"\tDATE,\n" +
                "\t\"pid\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"date\",\"pid\"),\n" +
                "\tFOREIGN KEY(\"pid\") REFERENCES \"Product\"(\"pid\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";

        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS \"Product\" (\n" +
                "\t\"pid\"\tINTEGER,\n" +
                "\t\"name\"\tTEXT NOT NULL,\n" +
                "\t\"category_name\"\tTEXT NOT NULL,\n" +
                "\t\"manufacture\"\tTEXT NOT NULL,\n" +
                "\t\"storeQuantity\"\tINTEGER NOT NULL,\n" +
                "\t\"storageQuantity\"\tINTEGER NOT NULL,\n" +
                "\t\"discount\"\tINTEGER,\n" +
                "\t\"discountDate\"\tDATE,\n" +
                "\t\"priceFromSupplier\"\tREAL NOT NULL,\n" +
                "\t\"priceToCustomer\"\tREAL NOT NULL,\n" +
                "\t\"defectiveItems\"\tINTEGER NOT NULL,\n" +
                "\t\"minimum\"\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"pid\"),\n" +
                "\tFOREIGN KEY(\"category_name\") REFERENCES \"Category\"(\"name\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "CREATE TABLE IF NOT EXISTS \"Report\" (\n" +
                "\t\"rid\"\tINTEGER,\n" +
                "\t\"date\"\tDATE,\n" +
                "\tPRIMARY KEY(\"rid\")\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS \"StockReport\" (\n" +
                "\t\"rid\"\tINTEGER,\n" +
                "\t\"category_name\"\tTEXT,\n" +
                "\t\"day\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"rid\",\"category_name\"),\n" +
                "\tFOREIGN KEY(\"rid\") REFERENCES \"Report\"(\"rid\"),\n" +
                "\tFOREIGN KEY(\"category_name\") REFERENCES \"Category\"(\"name\") ON UPDATE CASCADE ON DELETE RESTRICT\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS \"Contacts\" (\n" +
                "\t\"sid\"\tINTEGER,\n" +
                "\t\"info\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"sid\",\"info\"),\n" +
                "\tFOREIGN KEY(\"sid\") REFERENCES \"Supplier\"(\"sid\") ON UPDATE CASCADE ON DELETE CASCADE\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS \"Contract\" (\n" +
                "\t\"sid\"\tINTEGER,\n" +
                "\t\"days_supply\"\tTEXT,\n" +
                "\t\"need_delivery\"\tINTEGER,\n" +
                "\t\"location\"\tTEXT,\n" +
                "\tFOREIGN KEY(\"sid\") REFERENCES \"Supplier\"(\"sid\") ON UPDATE CASCADE ON DELETE CASCADE,\n" +
                "\tPRIMARY KEY(\"sid\")\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "CREATE TABLE IF NOT EXISTS \"DiscountContract\" (\n" +
                "\t\"sid\"\tINTEGER,\n" +
                "\t\"amount\"\tINTEGER,\n" +
                "\t\"discount\"\tINTEGER NOT NULL,\n" +
                "\tFOREIGN KEY(\"sid\") REFERENCES \"Supplier\"(\"sid\") ON UPDATE CASCADE ON DELETE CASCADE,\n" +
                "\tPRIMARY KEY(\"sid\",\"amount\")\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS \"DiscountProducts\" (\n" +
                "\t\"pid\"\tINTEGER,\n" +
                "\t\"sid\"\tINTEGER,\n" +
                "\t\"amount\"\tINTEGER,\n" +
                "\t\"discount\"\tREAL NOT NULL,\n" +
                "\tFOREIGN KEY(\"pid\") REFERENCES \"Product\"(\"pid\") ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tPRIMARY KEY(\"pid\",\"sid\",\"amount\"),\n" +
                "\tFOREIGN KEY(\"sid\") REFERENCES \"Supplier\"(\"sid\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS \"ItemOrder\" (\n" +
                "\t\"oid\"\tINTEGER,\n" +
                "\t\"sid\"\tINTEGER,\n" +
                "\t\"pid\"\tINTEGER,\n" +
                "\t\"amount\"\tINTEGER,\n" +
                //"\tFOREIGN KEY(\"oid\") REFERENCES \"Orders\"(\"oid\") ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY(\"sid\") REFERENCES \"Supplier\"(\"sid\") ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY(\"pid\") REFERENCES \"Product\"(\"pid\") ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tPRIMARY KEY(\"sid\",\"pid\",\"oid\")\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS \"Orders\" (\n" +
                "\t\"oid\"\tINTEGER,\n" +
                "\t\"date\"\tDATE,\n" +
                "\t\"totalPrice\"\tREAL,\n" +
                "\tPRIMARY KEY(\"oid\")\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS \"UrgentOrders\" (\n" +
                "\t\"oid\"\tINTEGER,\n" +
                "\t\"date\"\tDATE,\n" +
                "\t\"totalPrice\"\tREAL,\n" +
                "\tPRIMARY KEY(\"oid\")\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql =  "CREATE TABLE IF NOT EXISTS \"ProductPerSupplier\" (\n" +
                "\t\"pid\"\tINTEGER,\n" +
                "\t\"price\"\tREAL NOT NULL,\n" +
                "\t\"sid\"\tINTEGER,\n" +
                "\t\"supSerialNum\"\tINTEGER NOT NULL,\n" +
                "\t\"weightPerUnit\"\tREAL NOT NULL,\n" +
                "\tFOREIGN KEY(\"pid\") REFERENCES \"Product\"(\"pid\") ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tPRIMARY KEY(\"pid\",\"sid\"),\n" +
                "\tFOREIGN KEY(\"sid\") REFERENCES \"Supplier\"(\"sid\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS \"Supplier\" (\n" +
                "\t\"sid\"\tINTEGER,\n" +
                "\t\"name\"\tTEXT NOT NULL,\n" +
                "\t\"paymentMethod\"\tTEXT NOT NULL,\n" +
                "\t\"bankAccount\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"sid\")\n" +
                ");";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS Trucks(" +
                "license integer," +
                "model VARCHAR ," +
                "weight integer," +
                "capacity integer," +
                "Availability integer,"+
                "PRIMARY KEY(license));";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS Drivers(" +
                " id integer," +
                " name VARCHAR," +
                " license integer," +
                "Availability integer,"+
                "PRIMARY KEY(id));";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS Locations(" +
                " Address VARCHAR ," +
                " phoneNum VARCHAR," +
                " ContactName VARCHAR," +
                " deliveryId integer ," +
                "FOREIGN KEY (deliveryId) REFERENCES Deliveries(id)," +
                "PRIMARY KEY(Address));";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "CREATE TABLE IF NOT EXISTS Deliveries(" +
                " id integer," +
                " date DATE," +
                " numOfTruck integer," +
                " driverName VARCHAR ," +
                " truckW integer," +
                " deliveryWeight integer," +
                "PRIMARY KEY(id));";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS Document(" +
                " id integer," +
                " driverId integer," +
                " locationAddress VARCHAR," +
                "FOREIGN KEY(driverId) REFERENCES Drivers(id)"+
                "FOREIGN KEY(locationAddress) REFERENCES Locations(Address)"+
                "PRIMARY KEY(id));";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS Workers(" +
                " ID INTEGER," +
                " Name TEXT," +
                " BankAccountNumber INTEGER," +
                " BankNumber INTEGER," +
                " Salary INTEGER," +
                " JoiningDate DATETIME," +
                " LeavingDate DATETIME," +
                "PRIMARY KEY(ID));";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS Constraints(" +
                " IsTemp INTEGER," +
                " Date DATETIME," +
                " ShiftType INTEGER," +
                " WorkerID INTEGER," +
                " ID INTEGER," +
                "FOREIGN KEY(WorkerID) REFERENCES Workers(ID)"+
                "PRIMARY KEY(ID));";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS Roles(" +
                " ID INTEGER," +
                " RoleName TEXT," +
                "PRIMARY KEY(ID));";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS Shifts(" +
                " Date DATETIME," +
                " ManagerID INTEGER," +
                " ShiftType INTEGER," +
                " ID INTEGER," +
                "FOREIGN KEY(ManagerID) REFERENCES Workers(ID)"+
                "PRIMARY KEY(ID));";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS ShiftsRoles(" +
                " ShiftID INTEGER," +
                " RoleID INTEGER," +
                " Amount INTEGER," +
                "FOREIGN KEY(ShiftID) REFERENCES Shifts(ID)"+
                "FOREIGN KEY(RoleID) REFERENCES Roles(ID)"+
                "PRIMARY KEY(ShiftID,RoleID));";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS WorkersRoles(" +
                " WorkerID INTEGER," +
                " RoleID INTEGER," +
                "FOREIGN KEY(WorkerID) REFERENCES Workers(ID)"+
                "FOREIGN KEY(RoleID) REFERENCES Roles(ID)"+
                "PRIMARY KEY(WorkerID,RoleID));";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS WorkersShifts(" +
                " WorkerID INTEGER," +
                " ShiftID INTEGER," +
                " RoleID INTEGER,"+
                "FOREIGN KEY(RoleID) REFERENCES Roles(ID)"+
                "FOREIGN KEY(ShiftID) REFERENCES Shifts(ID)"+
                "FOREIGN KEY(WorkerID) REFERENCES Workers(ID)"+
                "PRIMARY KEY(ShiftID,WorkerID));";
        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql ="PRAGMA foreign_keys = ON;";

        try (
                Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

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
    public int getDay(){
        return repMapper.day;
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
                    priceToCustomer, defectiveItems, minimum, priceToCusHistory,
                    priceFromSupHistory);
        } catch (Exception e) {
            System.out.println("failed to add product\n" + e.getMessage());
        }
    }

    public void addSup (String supCat, String cat){
        try {
            catMapper.addSup(supCat,cat);
        }
        catch (Exception e) {
            System.out.println("failed to add category\n" + e.getMessage());
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
    public void addStockCat(int id, String cat, int day){
        try {
            repMapper.addStockCat(id,cat,day);
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
    public  void updateDay(int day){
            try {
                Statement stmt1 = this.con.createStatement();
                ResultSet res1 = stmt1.executeQuery("SELECT COUNT(*) FROM StockReport");
                res1.next();
                if(res1.getInt(1) != 0) {
                    String sql = "UPDATE StockReport SET day = ? WHERE rid > 0";

                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.setInt(1, day);
                    // update
                    pstmt.executeUpdate();
                }
                else {
                    List<String> cats = new LinkedList<>();
                    cats.add("fruits");
                    addStock(-1, new java.util.Date(System.currentTimeMillis()),cats);

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

    public HashMap<Integer, StockReport> getStockReports(){
        return repMapper.getStockReports();
    }
    public HashMap<Integer, DefectiveReport> getDefectiveReports() {
        return repMapper.getDefectiveReports();
    }

    public void deleteSup(String cat){
        try {
            catMapper.addSup("",cat);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addContact(int id_sup, String contact)
    {
        try {
            supplierMapper.addContactToSupplier(id_sup, contact);
        } catch (Exception e) {
            System.out.println("failed to addContact\n" + e.getMessage());
        }
    }
    public void addNewProductPerSupplier(int pid,double price, int sid, Long supSerialNum,double weight ){
        try {
            productPerSupMapper.addNewProductPerSupplier(pid, price, sid, supSerialNum, weight);
        }catch (Exception e){
            System.out.println("failed to addNewProductPerSupplier\n" + e.getMessage());
        }
    }

    public void addNewHighAmountDiscount(int prodIdByName, int sid, Integer key, Double value) {
        try {
            productPerSupMapper.addNewHighAmountDiscount(prodIdByName, sid, key, value);
        }catch (Exception e){
            System.out.println("failed to addNewHighAmountDiscount\n" + e.getMessage());
        }
    }
}
