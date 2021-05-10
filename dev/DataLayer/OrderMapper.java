package DataLayer;

import BusinessLayer.Suppliers.OutgoingOrder;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

public class OrderMapper
{
    private Connection con = null;
    public HashMap<Integer, OutgoingOrder> orders; //SupplierID, Order Object

    public OrderMapper() throws SQLException {

        orders=new HashMap<>();

        con=DataHandler.connect();
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM Order");

        while (res.next()) {
            int orderID=res.getInt("oid"); // The orderID is the supplierID
            LocalDate delivery_Date=res.getDate("date").toLocalDate();
            double totalPrice=res.getDouble("totalPrice");

            ResultSet res1 = stmt.executeQuery("SELECT * FROM ItemOrder WHERE oid ="+orderID+";");
            int supplierID = res1.getInt("sid");

            orders.put(orderID, new OutgoingOrder(supplierID, delivery_Date, getOrderItems(orderID), totalPrice));

        }
    }
    // The Key is the product ID and the integer is the amount
    private HashMap<Long, Integer> getOrderItems(int orderID) throws SQLException {
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM Order INNER JOIN OrderItem ON Order.oid=OrderItem.oid WHERE Order.oid=" + orderID + ";");

        HashMap<Long, Integer> itemsToAdd = new HashMap<>();

        while (res.next()) {
            Long productID=res.getLong("pid");
            Integer amount = res.getInt("amount");
            itemsToAdd.put(productID, amount);
        }

        return itemsToAdd;
    }

    public void addNewOrder(int sid, String paymentMethod, String bankAccount) throws SQLException {
        String sql = "INSERT INTO Supplier(sid,name,paymentMethod,bankAccount) VALUES(?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, sid);
            pstmt.setString(2, paymentMethod);
            pstmt.setString(3, bankAccount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addNewItemOrder(int oid, int sid, int pid, int amount) throws SQLException {
        String sql = "INSERT INTO ItemOrder(oid,sid,pid,amount) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, oid);
            pstmt.setInt(2, sid);
            pstmt.setInt(3, pid);
            pstmt.setInt(4, amount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateOrder(int oid, Date date, int totalPrice) throws SQLException { //  update  all the  argument for contract of sid = sid
        String sql = "UPDATE Order SET date = ? , totalPrice = ?  "
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
        String sql = "DELETE FROM Order WHERE oid=?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, oid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteItemOrder(int oid,int sid,int pid,int amount  )throws SQLException{
        String sql = "DELETE FROM Order WHERE oid=? AND sid=? AND pid=?";
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
