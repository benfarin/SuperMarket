package DataLayer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MapperData {
    public void initialize() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Database.db");
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
            st = "CREATE TABLE IF NOT EXISTS Workers(" +
                    " ID INTEGER," +
                    " Name TEXT," +
                    " BankAccountNumber INTEGER," +
                    " BankNumber INTEGER," +
                    " Salary INTEGER," +
                    " JoiningDate DATETIME," +
                    " LeavingDate DATETIME," +
                    "PRIMARY KEY(ID));";
            statement.execute(st);
            st = "CREATE TABLE IF NOT EXISTS Constraints(" +
                    " IsTemp INTEGER," +
                    " Date DATETIME," +
                    " ShiftType INTEGER," +
                    " WorkerID INTEGER," +
                    " ID INTEGER," +
                    "FOREIGN KEY(WorkerID) REFERENCES Workers(ID)"+
                    "PRIMARY KEY(ID));";
            statement.execute(st);
            st = "CREATE TABLE IF NOT EXISTS Roles(" +
                    " ID INTEGER," +
                    " RoleName TEXT," +
                    "PRIMARY KEY(ID));";
            statement.execute(st);
            st = "CREATE TABLE IF NOT EXISTS Shifts(" +
                    " Date DATETIME," +
                    " ManagerID INTEGER," +
                    " ShiftType INTEGER," +
                    " ID INTEGER," +
                    "FOREIGN KEY(ManagerID) REFERENCES Workers(ID)"+
                    "PRIMARY KEY(ID));";
            statement.execute(st);
            st = "CREATE TABLE IF NOT EXISTS ShiftsRoles(" +
                    " ShiftID INTEGER," +
                    " RoleID INTEGER," +
                    " Amount INTEGER," +
                    "FOREIGN KEY(ShiftID) REFERENCES Shifts(ID)"+
                    "FOREIGN KEY(RoleID) REFERENCES Roles(ID)"+
                    "PRIMARY KEY(ShiftID,RoleID));";
            statement.execute(st);
            st = "CREATE TABLE IF NOT EXISTS WorkersRoles(" +
                    " WorkerID INTEGER," +
                    " RoleID INTEGER," +
                    "FOREIGN KEY(WorkerID) REFERENCES Workers(ID)"+
                    "FOREIGN KEY(RoleID) REFERENCES Roles(ID)"+
                    "PRIMARY KEY(WorkerID,RoleID));";
            statement.execute(st);
            st = "CREATE TABLE IF NOT EXISTS WorkersShifts(" +
                    " WorkerID INTEGER," +
                    " ShiftID INTEGER," +
                    " RoleID INTEGER,"+
                    "FOREIGN KEY(RoleID) REFERENCES Roles(ID)"+
                    "FOREIGN KEY(ShiftID) REFERENCES Shifts(ID)"+
                    "FOREIGN KEY(WorkerID) REFERENCES Workers(ID)"+
                    "PRIMARY KEY(ShiftID,WorkerID));";
            statement.execute(st);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
