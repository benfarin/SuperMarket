package DataLayer;

import BusinessLayer.Suppliers.Contract;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ContractMapper {


    public static HashMap<Integer, Contract> contracts;
    private Connection con=null;



    public ContractMapper() throws SQLException {
        con=DataHandler.connect();
        contracts=new HashMap<>();
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM Contract");

        while(res.next())
        {
            int supplierID = res.getInt("sid");
            String days = res.getString("days_supply");
            int needsDelivery = res.getInt("need_delivery");

            boolean delivery=false;
            if(needsDelivery==1)
                delivery=true;

            contracts.put(supplierID, new Contract(days, delivery, setDiscounts(supplierID)));
        }
    }

    public HashMap<Integer, Integer> setDiscounts(int supplierID) throws SQLException {
        Statement stmt = this.con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM DiscountContract WHERE sid="+supplierID+";");
        HashMap<Integer, Integer> discountsMap = new HashMap<>();

        while(res.next())
        {
            int amount = res.getInt("amount");
            int discount = res.getInt("discount");
            discountsMap.put(amount, discount);
        }
        return discountsMap;
    }

    public HashMap<Integer, Contract> getContracts() {
        return contracts;
    }


    public void addContract(int sid, String days_supply, int need_delivery, HashMap<Integer, Integer> totalPriceDiscount) throws SQLException {
        String sql = "INSERT INTO Contract(sid,days_supply,need_delivery) VALUES(?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, sid);
            pstmt.setString(2, days_supply);
            pstmt.setInt(3, need_delivery);
            pstmt.executeUpdate();
            for (Map.Entry<Integer,Integer> discount : totalPriceDiscount.entrySet()){
                addDiscountContract(sid,discount.getKey().intValue(),discount.getValue());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addDiscountContract(int sid, int amount, int discount) throws SQLException {
        String sql = "INSERT INTO DiscountContract(sid,amount,discount) VALUES(?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, sid);
            pstmt.setInt(2, amount);
            pstmt.setInt(3, discount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateContract (int sid, String days_supply, int need_delivery) throws SQLException{ //  update  all the  argument for contract of sid = sid
        String sql = "UPDATE Contract SET days_supply = ? , need_delivery = ? "
                + "WHERE sid = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, days_supply);
            pstmt.setInt(2, need_delivery);
            pstmt.setInt(3, sid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
