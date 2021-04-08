package Tests;

import BusinessLayer.Category;
import BusinessLayer.DefectiveReport;
import BusinessLayer.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class DefectiveReportTest {
    DefectiveReport defReport = new DefectiveReport();

    Category c = new Category("fruit", new LinkedList<>());
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