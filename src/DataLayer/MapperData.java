package DataLayer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MapperData {
    public void initialize() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:SuperLee.db");
            Statement statement = conn.createStatement();
            String st = "CREATE TABLE IF NOT EXISTS Trucks(" +
                    "license integer," +
                    "model VARCHAR ," +
                    "weight integer," +
                    "capacity integer," +
                    "Availability integer,"+
                    "PRIMARY KEY(license));";
            statement.execute(st);
             st = "CREATE TABLE IF NOT EXISTS Drivers(" +
                    " id integer," +
                    " name VARCHAR," +
                    " license integer," +
                     "Availability integer,"+
                    "PRIMARY KEY(id));";
            statement.execute(st);
            st = "CREATE TABLE IF NOT EXISTS Locations(" +
                    " Address VARCHAR ," +
                    " phoneNum VARCHAR," +
                    " ContactName VARCHAR," +
                    " deliveryId integer ," +
                    "FOREIGN KEY (deliveryId) REFERENCES Deliveries(id)," +
                    "PRIMARY KEY(Address));";
            statement.execute(st);

            st = "CREATE TABLE IF NOT EXISTS Deliveries(" +
                    " id integer," +
                    " date DATE," +
                    " numOfTruck integer," +
                    " driverName VARCHAR ," +
                    " truckW integer," +
                    " deliveryWeight integer," +
                    "PRIMARY KEY(id));";
            statement.execute(st);
            st = "CREATE TABLE IF NOT EXISTS Document(" +
                    " id integer," +
                    " driverId integer," +
                    " locationAddress VARCHAR," +
                    "FOREIGN KEY(driverId) REFERENCES Drivers(id)"+
                    "FOREIGN KEY(locationAddress) REFERENCES Locations(Address)"+
                    "PRIMARY KEY(id));";
            statement.execute(st);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}