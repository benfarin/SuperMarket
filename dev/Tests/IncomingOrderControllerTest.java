package Tests;

import BusinessLayer.Suppliers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class IncomingOrderControllerTest {
    IncomingOrderController controller;
    Supplier sup1;
    Supplier sup2;
    @BeforeEach
    public void setUp() {
        LinkedList<ProductPerSup> products= new LinkedList<ProductPerSup>();

         sup1 = new Supplier(new Integer(1111),new Long(13),"Itzik",null,"bla bla1","leumi");
         sup2 = new Supplier(new Integer(2222),new Long(20),"Avi",null,"bla bla2","HAPOALIM");
        HashMap<Integer,Double> map1 = new HashMap<>() ;
        map1.put(new Integer(10),new Double(10));// for 10 product there is 10 precent discount
        map1.put(new Integer(20),new Double(12));// for 20 product there is 12 precent discount
        HashMap<Integer,Double> map2 = new HashMap<>() ;
        map2.put(new Integer(30),new Double(10));// for 10 product there is 10 precent discount
        map2.put(new Integer(40),new Double(12));// for 20 product there is 12 precent discount

        ProductPerSup prod1 = new ProductPerSup("prod1",new Long(1),new Double(10),map1,new Long(1),sup1, 15);
        ProductPerSup prod2 = new ProductPerSup("prod2",new Long(1),new Double(20),map2,new Long(2),sup2, 15 );
        ProductPerSup prod3 = new ProductPerSup("prod3",new Long(2),new Double(20),map2,new Long(2),sup2, 15);

        products.add(prod1);
        products.add(prod2); // products now with the same product with different suppliers
        products.add(prod3);

        //controller = new IncomingOrderController(products,null);
    }
    @Test
    void IsProductExistInSystem(){
        assertTrue(controller.IsProductExistInSystem(new Long(1)));//check if we have the list of product number 1 in the controller
        assertTrue(controller.IsProductExistInSystem(new Long(2))); //check if we have the list of product number 2 in the controller

    }
    @Test
    void AddNewOrder(){
        controller.AddNewOrder(new Long(2),10);
        assertTrue(controller.IsOrderExistInSystem(0));// check if order number 0 is exist
        controller.AddNewOrder(new Long(1),12);
        assertTrue(!controller.IsOrderExistInSystem(1)); // check if order number 1 exist in system

    }
    @Test
    void IsOrderExistInSystem() {
        controller.AddNewOrder(new Long(2),10);
        assertTrue(controller.IsOrderExistInSystem(0));// check if order number 0 is exist
    }
    @Test
    void showOrder() {
        HashMap<Integer,Integer> discounts_sup_1 = new HashMap<>();
        discounts_sup_1.put(new Integer(10),20);
        discounts_sup_1.put(new Integer(30),25);
        discounts_sup_1.put(new Integer(50),30);
        Contract contract_sup_1 = new Contract(null,true,discounts_sup_1, "Haifa");
        sup1.setContract(contract_sup_1);
        HashMap<Integer,Integer> discounts_sup_2 = new HashMap<>();
        discounts_sup_2.put(new Integer(13),23);
        discounts_sup_2.put(new Integer(31),25);
        discounts_sup_2.put(new Integer(70),30);
        Contract contract_sup_2 = new Contract(null,true,new HashMap<>(), "Haifa");
        sup2.setContract(contract_sup_2);
        controller.AddNewOrder(new Long(1),46);
        OutgoingOrder order = controller.ShowOrder(new Long(0));
        assertTrue(order!=null);
    }

    @Test
    void showOrderBySupplier() {
        HashMap<Integer,Integer> discounts_sup_1 = new HashMap<>();
        discounts_sup_1.put(new Integer(10),20);
        discounts_sup_1.put(new Integer(30),25);
        discounts_sup_1.put(new Integer(50),30);
        Contract contract_sup_1 = new Contract(null,true,discounts_sup_1, "Maabarot");
        sup1.setContract(contract_sup_1);
        HashMap<Integer,Integer> discounts_sup_2 = new HashMap<>();
        discounts_sup_2.put(new Integer(13),23);
        discounts_sup_2.put(new Integer(31),25);
        discounts_sup_2.put(new Integer(70),30);
        Contract contract_sup_2 = new Contract(null,true,new HashMap<>(), "Maabarot");
        sup2.setContract(contract_sup_2);
        controller.AddNewOrder(new Long(1),46);
        OutgoingOrder order = controller.ShowOrderBySupplier(1111);
        assertTrue(order!=null);

    }
}