package Tests;

import BusinessLayer.Category;
import BusinessLayer.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @BeforeEach
    void setUp() {
        Category c = new Category("fruit",new LinkedList<>());
        Product p = new Product("apple",c,"Maabarot",2.90,4.10,20);
    }

    @Test
    void setPriceFromSupplier() {

    }

    @Test
    void setPriceToCustomer() {
    }

    @Test
    void setDiscount() {
    }

    @Test
    void setManufacture() {
    }

    @Test
    void setDefectiveItem() {
    }

    @Test
    void setMinimum() {
    }

    @Test
    void addStoreQuantity() {
    }

    @Test
    void reduceStoreQuantity() {
    }

    @Test
    void addStorageQuantity() {
    }

    @Test
    void reduceStorageQuantity() {
    }

    @Test
    void setStoreQuantity() {
    }

    @Test
    void setStorageQuantity() {
    }
}