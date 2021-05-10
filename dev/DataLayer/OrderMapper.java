package DataLayer;

import BusinessLayer.Suppliers.OutgoingOrder;

import java.sql.Connection;
import java.util.HashMap;

public class OrderMapper
{
    private Connection con = null;
    public HashMap<Integer, OutgoingOrder> orders;


}
