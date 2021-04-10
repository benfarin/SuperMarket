package Tests;

import BusinessLayer.OutgoingOrder;
import BusinessLayer.Supplier;
import org.junit.Assert;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class OutgoingOrderTest {

    //Supplier sup = new Supplier(123, new Long(23787124), "Moshe inc.", new LinkedList<String>(),"Cash", "Leumi050124" );
    OutgoingOrder newOrder;

    @org.junit.Before
    public void setUp() throws Exception {
        newOrder = new OutgoingOrder(123, null);
        newOrder.AddItem(new Long(125212), 5, 150);
    }

    @org.junit.Test
    public void addItem() {
       //Assert.assertEquals(5, newOrder.getItems().get(125212));

    }
}