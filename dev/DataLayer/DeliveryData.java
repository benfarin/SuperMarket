package DataLayer;
import BusinessLayer.Delivery.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DeliveryData {
    public Delivery get(int id){
        Delivery delivery;
        SimpleDateFormat date = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        Date date1;
        int numOfTruck;
        String driverName;
        List<Location> location;
        int truckW;
        int deliveryWeight;
        String query = "SELECT * FROM Deliveries WHERE id = ?";
try(Connection conn = DAL.connect();
    PreparedStatement st = conn.prepareStatement(query)){
    st.setInt(1,id);
    ResultSet result = st.executeQuery();
    if(result.next()){String strdate = result.getString("date1");
    date1 = new SimpleDateFormat("dd/MM/YYYY").parse(strdate);
    numOfTruck = result.getInt("numOfTruck");
    driverName = result.getString("driverName");
    location = (List<Location>) result.getObject("location");
    truckW = result.getInt("truckW");
    deliveryWeight = result.getInt("deliveryWeight");
    delivery = new Delivery(id,date1,numOfTruck,driverName,location,truckW,deliveryWeight);
return delivery;
    }
    else{
        System.out.println("there is no delivery with id "+id);
    }
    } catch (SQLException | ParseException throwables) {
    throwables.printStackTrace();
}
return null;
    }

    public List<Delivery> getAll(){
        String sql = "SELECT * FROM Deliveries";
        Delivery delivery;
        List<Delivery> deliveries = new LinkedList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        Date date;
        int numOfTruck;
        String driverName;
        List<Location> locations;
        int truckW;
        int deliveryWeight;
        int deliveryId;
        try (Connection conn = DAL.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String stringDate = rs.getString("date");
                date = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
                //driverID = rs.getInt("driverId");
                truckW = rs.getInt("truckWeight");
                locations = (List<Location>) rs.getObject("locations");
                deliveryWeight = rs.getInt("deliveryWeight");
                driverName = rs.getString("driverName");
                numOfTruck = rs.getInt("numOfTruck");
                deliveryId = rs.getInt("id");
                delivery = new Delivery(deliveryId,date,numOfTruck,driverName,locations,truckW,deliveryWeight);
                deliveries.add(delivery);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return deliveries;
    }
    public int save(Delivery delivery) {

        String query = "INSERT INTO Deliveries(id,date,numOfTruck,driverName,locations,truckW,DeliveryWeight) VALUES(?, ?, ?, ?, ?, ?, ?)";
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(delivery.getDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH) + 1;
        //String date = sdf.format(delivery.getDate());
        String date = day + "/" + month + "/" + year;

        int licenseNum = delivery.getTruck().getLicenseNum();
        //int driverId = delivery.getDriverID();
        //List<String> logs = delivery.getLogs();
        int truckWeight = delivery.getTruckW();
        List<Location> locations = delivery.getLocations();
        int deliveryWeight = delivery.getDeliveryWeight();
        int numOfTruck = delivery.getNumOfTruck();
        //Map<String, Document> documents = delivery.getDocuments();
        int deliveryID = -1;

        try (Connection conn = DAL.connect();
             PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, date);
            st.setObject(2, locations);
            st.setInt(3, licenseNum);
            st.setInt(4, numOfTruck);
            st.setInt(5, truckWeight);
            st.setInt(6, deliveryWeight);
            st.executeUpdate();
            System.out.println("Delivery has been added successfully");
        } catch (SQLException ignored) {
        }

        query = "SELECT MAX(id) AS LAST FROM Deliveries";

        try (Connection conn = DAL.connect();
             PreparedStatement st = conn.prepareStatement(query)) {

            ResultSet result = st.executeQuery();

            if (result.next()) {
                deliveryID = result.getInt("LAST");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return deliveryID;
    }

    public void update(Delivery delivery){
        String sql = "UPDATE Deliveries SET date = ?," +
                "Locations = ?," +
                "numOfTruck = ? , " +
                "driverName = ? , " +
                "locations = ? , " +
                "truckW = ? , " +
                "deliveryWeight = ? " +
                "WHERE id = ?";
        SimpleDateFormat dt = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(delivery.getDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH) + 1;
        String date = day + "/" + month + "/" + year;
        int numOfTruck = delivery.getNumOfTruck();
        String driverName = delivery.getDriverName();
        List<Location> locations = delivery.getLocations();
        int truckW = delivery.getTruckW();
        int deliveryWeight = delivery.getDeliveryWeight();
        int deliveryID = delivery.getId();
        try (Connection conn = DAL.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date);
            pstmt.setInt(2,numOfTruck);
            pstmt.setString(3, driverName);
            pstmt.setObject(4, locations);
            pstmt.setInt(5,truckW);
            pstmt.setInt(6,deliveryWeight);
            pstmt.executeUpdate();

        } catch (SQLException ignored) {
        }
    }
    public void delete(int id) {
        String sql = "DELETE FROM Deliveries WHERE id = ?";

        try (Connection conn = DAL.connect();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);
            st.executeUpdate();

            System.out.println(("Delivery with ID: " + id + " has been removed."));
        } catch (SQLException e) {
            System.out.println(("No delivery with document ID: " + id + " is found."));
        }
    }
}