package DataLayer;
import BusinessLayer.Inventory.Product;

import java.sql.*;

public class Data {

    private Connection con;
    public Data(){
        con = null;
        connect();
    }

    public void connect(){
        try {
          //  Class.forName("org.sqlite.JDBC");
//            con = DriverManager.getConnection("jdbc:sqlite:dev/SQL_Inventory_Suppliers.db");
        }

        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("database successfully created");
    }





}

