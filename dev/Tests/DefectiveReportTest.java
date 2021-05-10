package Tests;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.DefectiveReport;
import BusinessLayer.Inventory.Product;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class DefectiveReportTest {
    DefectiveReport defReport = new DefectiveReport();

    Category c = new Category("fruit");
    Product p = new Product("apple", c, "Maabarot", 2.90, 4.10, 20);


    @Test
    void addProd() {
        defReport.addProd(p);
        assertTrue(defReport.isProdInRep("apple"));
    }

    @Test
    void deleteProd() {
        defReport.deleteProd(p);
        assertFalse(defReport.isProdInRep("apple"));
    }
}