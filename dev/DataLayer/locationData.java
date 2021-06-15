package DataLayer;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import BusinessLayer.Delivery.*;

public class locationData {
    public Location get(String address){
        Location loc  = null;
        String string1 = "SELECT * FROM Locations WHERE address = ?";
        try(Connection conn = DAL.connect();
        PreparedStatement preparedST = conn.prepareStatement(string1)){
            preparedST.setString(1,address);
            ResultSet result = preparedST.executeQuery();
            if(result.next()) {
                loc = new Location(result.getString("Address"), result.getString("phoneNum"), result.getString("ContactName"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return loc;
    }
    public List<Location> getAll(){
        List<Location> locations = new LinkedList<>();
        String sqlQue = "SELECT * FROM Locations";
        try(Connection conn = DAL.connect();
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(sqlQue)){
            while(result.next()){
                locations.add(new Location(result.getString("Address"),result.getString("phoneNum"),result.getString("ContactName")));
            }
        }
        catch(SQLException e){
            e.getMessage();
        }
        return locations;
    }
    public void save(Location loc){
        String query = "INSERT INTO Locations(Address,phoneNumber,ContactName) VALUES (?,?,?)";
        try(Connection conn = DAL.connect();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1,loc.getAddress());
            ps.setString(2,loc.getPhoneNum());
            ps.setString(3,loc.getContactName());
            ps.executeUpdate();
            System.out.println("Locations has been added successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
