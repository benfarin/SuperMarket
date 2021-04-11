package Tests;

import BusinessLayer.OutgoingOrder;
import BusinessLayer.Supplier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class OutgoingOrderTest {

    //Supplier sup = new Supplier(123, new Long(23787124), "Moshe inc.", new LinkedList<String>(),"Cash", "Leumi050124" );
    OutgoingOrder newOrder;

    @BeforeEach
    public void setUp() {
        newOrder = new OutgoingOrder(123, null);
    }

    @Test
    public void addItem() {
        newOrder.AddItem(new Long(125212), 5, 150);
        newOrder.AddItem(new Long(123), 5, 170);
        newOrder.AddItem(new Long(123), 6, 150);
        assertTrue(newOrder.getItems().get(new Long(123))==11);
        assertTrue(newOrder.getItems().get(new Long((123)))==11);
        assertTrue(newOrder.getItems().size()==2);
    }

}