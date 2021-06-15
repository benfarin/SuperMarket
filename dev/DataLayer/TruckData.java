package DataLayer;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import BusinessLayer.Delivery.*;

public class TruckData {

//    public Truck get(int lNum){
//
//    }
    public Truck get(int license){
        Truck tr = null;
        String query = "SELECT * FROM Trucks WHERE license = ?";
        int serialNum;
        String model;
        int weight;
        int capacity;
        try(Connection conn = DAL.connect();
        PreparedStatement st = conn.prepareStatement(query)){
            st.setInt(1,license);
            ResultSet result = st.executeQuery();
            while(result.next()){
                serialNum = result.getInt("license");
                model = result.getString("model");
                weight = result.getInt("weight");
                capacity = result.getInt("capacity");
                tr = new Truck(serialNum,model,weight,capacity);
            }
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tr;
    }
    public List<Truck>  getAll(){
        List<Truck> trucks = new LinkedList<>();
        String query = "SELECT * FROM Trucks";
        try (Connection conn = DAL.connect();
             Statement st = conn.createStatement();
             ResultSet result = st.executeQuery(query)) {
            while (result.next()) {
                trucks.add(new Truck(result.getInt("license"), result.getString("model"), result.getInt("weight"), result.getInt("capacity")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return trucks;
    }

    public void save(Truck truck)
    {
        String sql = "INSERT INTO Trucks(serialNumber, weight, maxAllowedWeight, model) VALUES(?,?,?,?)";
        try (Connection conn = DAL.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, truck.getLicenseNum());
            pstmt.setString(2, truck.getModel());
            pstmt.setInt(3, truck.getWeight());
            pstmt.setInt(4, truck.getCapacity());
            pstmt.executeUpdate();
            System.out.println("Truck has been added successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public List<Truck> getAvailableTrucks() {//available trucks --> avail = 1
        List<Truck> ltrucks = new LinkedList<>();
        Truck truck = null;
        String query = "SELECT * FROM Trucks WHERE Availability = ?";
        int licenseNum;
        int avail=1;
        String model;
        int weight;
        int capacity;
        try (Connection conn = DAL.connect();
             PreparedStatement st = conn.prepareStatement(query)) {
            st.setInt(1,avail);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                licenseNum = rs.getInt("license");
                model = rs.getString("model");
                weight = rs.getInt("weight");
                capacity = rs.getInt("capacity");
                truck = new Truck(licenseNum,model,weight,capacity);
                ltrucks.add(truck);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ltrucks;
    }

    public List<Truck> getBusyTrucks() {//busy trucks ==> avail = 0
        List<Truck> ltrucks = new LinkedList<>();
        Truck truck = null;
        String query = "SELECT * FROM Trucks WHERE Availability = ?";
        int licenseNum;
        int avail=0;
        String model;
        int weight;
        int capacity;
        try (Connection conn = DAL.connect();
             PreparedStatement st = conn.prepareStatement(query)) {
            st.setInt(1,avail);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                licenseNum = rs.getInt("license");
                model = rs.getString("model");
                weight = rs.getInt("weight");
                capacity = rs.getInt("capacity");
                truck = new Truck(licenseNum,model,weight,capacity);
                ltrucks.add(truck);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ltrucks;
    }
    }


