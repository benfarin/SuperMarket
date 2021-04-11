package Tests;

import BusinessLayer.Product;
import BusinessLayer.Supplier;
import BusinessLayer.SupplierController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class SupplierControllerTest {
    SupplierController controller;
    @BeforeEach
    public void setUp() {
        controller = new SupplierController(new HashMap<>());
    }
    @Test
    void addSupplier() {
        controller.AddSupplier(1,2,null,null,null,null);
        assertTrue(controller.IsSupplierExistInSystem(1));
    }

        @Test
    void deleteSupplier() {
       controller.AddSupplier(1,2,null,null,null,null);
       controller.DeleteSupplier(1);
       assertTrue(!controller.IsSupplierExistInSystem(1));
        }

    @Test
    void showContacts() {
        controller.AddSupplier(1,1,null,new LinkedList<>(),null,null);
        assertTrue(controller.ShowSupInformation(1).showContacts().size()==0);
        Supplier Sup1 = controller.ShowSupInformation(1);
        Sup1.AddContact("Itzik");
        assertTrue(controller.ShowSupInformation(1).showContacts().size()==1);


    }
}