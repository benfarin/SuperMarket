package DataLayer;

import BusinessLayer.Suppliers.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SupplierMapper {
    private Connection con = null;
    private static HashMap<Integer, Supplier> suppliers;

    public SupplierMapper() throws SQLException {
        suppliers=new HashMap<>();
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM Supplier");

        while(res.next())
        {
            int supplierID = res.getInt("sid");
            String name = res.getString("name");
            String paymentMethod=res.getString("paymentMethod");
            String bankAccount=res.getString("bankAccount");
            long companyID = supplierID;

            suppliers.put(supplierID, new Supplier(supplierID, companyID, name, getContacts(supplierID),paymentMethod, bankAccount));
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
}
