package DataLayer;

import BusinessLayer.Suppliers.Contract;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class ContractMapper {

    private HashMap<Integer, Contract> contracts;
    private Connection con=null;



    public ContractMapper() throws SQLException {
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
}
