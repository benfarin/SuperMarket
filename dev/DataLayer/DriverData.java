package DataLayer;

import java.sql.PreparedStatement;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import BusinessLayer.Delivery.*;
import BusinessLayer.Delivery.Driver;


public class DriverData {

    public Driver get(int driverId) {
        Driver driver = null;
        String query = "SELECT * FROM Drivers WHERE id = ?";
        int id;
        String name;
        int license;
        try (Connection conn = DAL.connect();
             PreparedStatement st = conn.prepareStatement(query)) {
            st.setInt(1,driverId);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                id = rs.getInt("id");
                name = rs.getString("name");
                license = rs.getInt("license");
                driver = new Driver(id,name,license);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return driver;
    }

    public List<Driver> getAll(){
        String query = "SELECT * FROM Drivers";
        List<Driver> drivers = new LinkedList<>();
        Driver driver1;
        int id;
        String name;
        int license;
        try(Connection conn = DAL.connect();
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(query)){
            while(result.next()){
                id = result.getInt("id");
                name = result.getString("name");
                license = result.getInt("license");
                driver1 = new Driver(id,name,license);
                drivers.add(driver1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return drivers;
    }
    public void save(Driver driver){
        String query = "INSERT INTO Drivers(id,name,license) VALUES(?,?,?)";
        int id = driver.getDriverId();
        String name = driver.getName();
        int license = driver.getLicense();
        try(Connection conn = DAL.connect();
        PreparedStatement st = conn.prepareStatement(query)){
            st.setInt(1,id);
            st.setString(2,name);
            st.setInt(3,license);
            st.executeUpdate();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(Driver driver) {
        String query = "UPDATE Drivers SET name = ? , " +
                "license = ? , " +
                "WHERE id = ?";
        int id = driver.getId();
        String name = driver.getName();
        int license = driver.getLicense();
        try (Connection conn = DAL.connect();
             PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1,name);
            st.setInt(2,license);
            st.setInt(3,id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Driver driver){
        String query = "DELETE FROM Drivers WHERE id = ?";
        try(Connection conn = DAL.connect();
        PreparedStatement st = conn.prepareStatement(query)){
            st.setInt(1,driver.getId());
            st.executeUpdate();
            System.out.println("driver added successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Driver> getAvailableDrivers() {
        List<Driver> ldrivers = new LinkedList<>();
        Driver driver = null;
        String query = "SELECT * FROM Drivers WHERE Availability = ?";
        int id;
        boolean avail=true;
        String name;
        int license;
        try (Connection conn = DAL.connect();
             PreparedStatement st = conn.prepareStatement(query)) {
            st.setBoolean(1,avail);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                id = rs.getInt("id");
                name = rs.getString("name");
                license = rs.getInt("license");
                driver = new Driver(id,name,license);
                ldrivers.add(driver);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ldrivers;
    }

    public List<Driver> getBusyDrivers() {
        List<Driver> ldrivers = new LinkedList<>();
        Driver driver = null;
        String query = "SELECT * FROM Drivers WHERE Availability = ?";
        int id;
        boolean avail=false;
        String name;
        int license;
        try (Connection conn = DAL.connect();
             PreparedStatement st = conn.prepareStatement(query)) {
            st.setBoolean(1,avail);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                id = rs.getInt("id");
                name = rs.getString("name");
                license = rs.getInt("license");
                driver = new Driver(id,name,license);
                ldrivers.add(driver);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ldrivers;
    }
}
