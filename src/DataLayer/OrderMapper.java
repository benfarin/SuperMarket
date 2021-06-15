package DataLayer;

import BusinessLayer.Suppliers.OutgoingOrder;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class OrderMapper
{
    private static Connection con = null;
    public HashMap<Integer, OutgoingOrder> orders; //SupplierID, Order Object
    public HashMap<Integer, OutgoingOrder> urgentOrders;

    public OrderMapper(Connection con) throws SQLException, ParseException {

        orders=new HashMap<>();
        urgentOrders=new HashMap<>();

        this.con =con;
        Statement stmt = this.con.createStatement();

        ResultSet res = stmt.executeQuery("SELECT * FROM Orders");
        int supplierID=-1;

        while (res.next()) {
            supplierID=-1;
            int orderID=res.getInt("oid"); // The orderID is the supplierID

            Date delivery_Date=null;
            //Date dateToChange=res.getDate("date");
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-DD");
            //delivery_Date= (Date) formatter.parse(dateToChange);
            double totalPrice=res.getDouble("totalPrice");
            //LocalDate date = delivery_Date.toLocalDate();
            stmt = this.con.createStatement();
            ResultSet res1 = stmt.executeQuery("SELECT * FROM ItemOrder WHERE oid ="+orderID);

            if(res1.next()) {
                supplierID = res1.getInt("sid");
            }
            if(supplierID!=-1)
                orders.put(orderID, new OutgoingOrder(supplierID, delivery_Date, getOrderItems(orderID), totalPrice));



        }
        // --------- URGENT ORDERS LOAD
         stmt = this.con.createStatement();

         res = stmt.executeQuery("SELECT * FROM UrgentOrders");
         supplierID=-1;

        while (res.next()) {
            supplierID=-1;
            int orderID=res.getInt("oid"); // The orderID is the supplierID

            java.util.Date delivery_Date=null;
            //Date dateToChange=res.getDate("date");
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-DD");
            //delivery_Date= (Date) formatter.parse(dateToChange);
            double totalPrice=res.getDouble("totalPrice");
            //LocalDate date = delivery_Date.toLocalDate();
            stmt = this.con.createStatement();
            ResultSet res1 = stmt.executeQuery("SELECT * FROM ItemOrder WHERE oid ="+orderID);

            if(res1.next()) {
                supplierID = res1.getInt("sid");
            }
            if(supplierID!=-1)
                urgentOrders.put(orderID, new OutgoingOrder(supplierID, delivery_Date, getUrgentOrderItems(orderID), totalPrice));



        }
    }
    // The Key is the product ID and the integer is the amount
    private HashMap<Long, Integer> getOrderItems(int orderID) throws SQLException {
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM Orders INNER JOIN OrderItem ON Orders.oid=OrderItem.oid WHERE Orders.oid=" + orderID + ";");

        HashMap<Long, Integer> itemsToAdd = new HashMap<>();

        while (res.next()) {
            Long productID=res.getLong("pid");
            Integer amount = res.getInt("amount");
            itemsToAdd.put(productID, amount);
        }

        return itemsToAdd;
    }

    private HashMap<Long, Integer> getUrgentOrderItems(int orderID) throws SQLException {
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM UrgentOrders INNER JOIN OrderItem ON UrgentOrders.oid=OrderItem.oid WHERE UrgentOrders.oid=" + orderID + ";");

        HashMap<Long, Integer> itemsToAdd = new HashMap<>();

        while (res.next()) {
            Long productID=res.getLong("pid");
            Integer amount = res.getInt("amount");
            itemsToAdd.put(productID, amount);
        }

        return itemsToAdd;
    }

    public static void addNewOrder(Long oid, java.util.Date date, double totalPrice)  {
        String sql = "INSERT INTO Orders(oid,date,totalPrice) VALUES(?,?,?)";
        Date sqlDate = new Date(date.getTime());
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setLong(1, oid);
            pstmt.setDate(2, sqlDate);
            pstmt.setDouble(3, totalPrice);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addNewItemOrder(Long oid, int sid, Long pid, int amount) {
        String sql = "INSERT INTO ItemOrder(oid,sid,pid,amount) VALUES(?,?,?,?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, oid);
            pstmt.setInt(2, sid);
            pstmt.setLong(3, pid);
            pstmt.setInt(4, amount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addNewUrgentOrder(Long oid, java.util.Date date, double totalPrice)  {
        String sql = "INSERT INTO UrgentOrders(oid,date,totalPrice) VALUES(?,?,?)";
        Date sqlDate = new Date(date.getTime());
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, oid);
            pstmt.setDate(2, sqlDate);
            pstmt.setDouble(3, totalPrice);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void updateOrder(int oid, Date date, int totalPrice) throws SQLException { //  update  all the  argument for contract of sid = sid
        String sql = "UPDATE Orders SET date = ? , totalPrice = ?  "
                + "WHERE oid = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setDate(1,date);
            pstmt.setInt(2, totalPrice);
            pstmt.setInt(3, oid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateItemOrder(int oid, int sid, int pid,int amount) throws SQLException { //  update the amount of item order by the oid = oid ' sid = sid ' pid = pid
        String sql = "UPDATE ItemOrder SET amount = ?  "
                + "WHERE oid = ? AND sid = ? AND pid = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setInt(2, oid);
            pstmt.setInt(3, sid);
            pstmt.setInt(4, pid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteOrder(int oid)throws SQLException{
        String sql = "DELETE FROM Orders WHERE oid=?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, oid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteItemOrder(int oid,int sid,int pid,int amount  )throws SQLException{
        String sql = "DELETE FROM Orders WHERE oid=? AND sid=? AND pid=?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, oid);
            pstmt.setInt(2, sid);
            pstmt.setInt(3, pid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
