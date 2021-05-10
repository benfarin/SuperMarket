package Tests;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product p;
    @BeforeEach
    void setUp() {
        Category c = new Category("fruit");
        p = new Product("apple",c,"Maabarot",2.90,4.10,20);
    }

    @Test
    void setPriceFromSupplier() {
        double price = 3.20;
        p.setPriceFromSupplier(price);
        assertEquals(p.getPriceFromSupplier(), price);
    }

    @Test
    void setPriceToCustomer() {
        double price = 4.20;
        p.setPriceToCustomer(price);
        assertEquals(p.getPriceToCustomer(), price);
    }

    @Test
    void setDiscountInvalidDate() {
        Date invalidDate = new Date(1990);
        p.setDiscount(10,invalidDate);
        assertFalse(p.getDiscount() == 10);
    }
    @Test
    void setDiscountValidDate() {
        Date validDate = new Date(2030);
        p.setDiscount(10,validDate);
        assertNotEquals(10,p.getDiscount());
    }

    @Test
    void setMinimum_NoAlarm() {
        p.setStorageQuantity(10);
        assertFalse(p.setMinimum(p.getStorageQuantity()-1));
    }
    @Test
    void setMinimum_NeedToAlarm() {
        p.setStorageQuantity(10);
        assertTrue(p.setMinimum(p.getStorageQuantity()+1));
    }

    @Test
    void reduceStorageQuantity_NeedToAlarm() {
        int reduce = p.getStorageQuantity() - p.getMinimum();
        assertTrue(p.reduceStorageQuantity(reduce));
    }
    void reduceStorageQuantity_NoAlarm() {
        int reduce = p.getStorageQuantity() - p.getMinimum()-1;
        assertFalse(p.reduceStorageQuantity(reduce));
    }
    @Test
    void setStorageQuan_NeedToAlarm() {
        assertTrue(p.setStorageQuantity(p.getMinimum()-1));
    }
    @Test
    void setStorageQuan_NoAlarm() {
        assertFalse(p.setStorageQuantity(p.getMinimum()+1));
    }
}