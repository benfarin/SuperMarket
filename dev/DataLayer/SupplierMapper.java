package DataLayer;

import BusinessLayer.Suppliers.Supplier;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SupplierMapper {
    private Connection con = null;
    private static HashMap<Integer, Supplier> suppliers;

    public SupplierMapper(Connection con) throws SQLException {

        suppliers=new HashMap<>();

        this.con =con;
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM Supplier");



            while (res.next()) {
                int supplierID = res.getInt("sid");
                String name = res.getString("name");
                String paymentMethod = res.getString("paymentMethod");
                String bankAccount = res.getString("bankAccount");
                long companyID = supplierID;

                suppliers.put(supplierID, new Supplier(supplierID, companyID, name, getContacts(supplierID), paymentMethod, bankAccount, ContractMapper.contracts.get(supplierID)));
            }


    }

    private List<String> getContacts(int supplierID) throws SQLException {

        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM Contacts WHERE sid="+supplierID+";");
        List<String> contacts = new LinkedList<>();

        while(res.next())
        {
            String contactInfo=res.getString("info");
            contacts.add(contactInfo);
        }

        return contacts;
    }

    public static HashMap<Integer, Supplier> getSuppliers() {
        return suppliers;
    }

    public void addSupplier(int sid, String name, String paymentMethod, String bankAccount){
        String sql = "INSERT INTO Supplier(sid,name,paymentMethod,bankAccount) VALUES(?,?,?,?)";
        try  {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, sid);
            pstmt.setString(2, name);
            pstmt.setString(3, paymentMethod);
            pstmt.setString(4, bankAccount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateSupplier(int sid, String name, String paymentMethod, String bankAccount) throws SQLException { //  update  all the  argument for contract of sid = sid
        String sql = "UPDATE Supplier SET name = ? , paymentMethod = ? , bankAccount = ? "
                + "WHERE sid = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, paymentMethod);
            pstmt.setString(3, bankAccount);
            pstmt.setInt(4, sid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateSupplierContact(int sid, String info) throws SQLException { //  update the contact information where sid=sid of the argument
        String sql = "UPDATE Contact SET info = ? "
                + "WHERE sid = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, info);
            pstmt.setInt(2, sid);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addContactToSupplier(int sid,String info)  {
        String sql = "INSERT INTO Contacts(sid,info) VALUES(?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, sid);
            pstmt.setString(2, info);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void deleteSupplier(int sid)  {
        String sql = "DELETE FROM Supplier WHERE sid=?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, sid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "DELETE FROM Contacts WHERE sid=?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, sid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteSupplierContact(int oid, String info) throws SQLException {
        String sql = "DELETE FROM Order WHERE oid=? AND info=?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, oid);
            pstmt.setString(2, info);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
