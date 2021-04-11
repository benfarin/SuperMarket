package Tests;

import BusinessLayer.Contract;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContractTest {
    Contract contract;
    @Before
    public void setUp(){
        contract = new Contract("bla bla",true);
        contract.AddPriceDiscount(1000,5);
        contract.AddPriceDiscount(1300,8);
        contract.AddPriceDiscount(2000,11);
        contract.AddPriceDiscount(3000,20);
        contract.AddPriceDiscount(1500,16);
    }
    @Test
    public void getTotalPriceDiscount() {
        double a= contract.getTotalPriceDiscount(4000,10);
        assertEquals(32000,contract.getTotalPriceDiscount(4000,10),0.001);
        assertEquals(500,contract.getTotalPriceDiscount(50,10),0.001);
        assertEquals(22250,contract.getTotalPriceDiscount(2500,10),0.001);

    }
}