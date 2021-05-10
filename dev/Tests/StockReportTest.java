package Tests;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.StockReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockReportTest {
    private StockReport report;
    private Category dairy;
    private List<Category> categories;


    @BeforeEach
    void setUp() {
        dairy = new Category("Dairy");
        report = new StockReport();
        categories = new LinkedList<>();
        categories.add(dairy);
    }

    @Test
    void addCategory() {
        assertIterableEquals(report.getCategories(),new LinkedList<>());
        report.addCategory(dairy);
        assertIterableEquals(report.getCategories(),categories);
    }
    @Test
    void IDCheck(){
        StockReport report1 = new StockReport();
        assertNotEquals(report1.getID(),report.getID());
    }

    @Test
    void dateCheck() {
        Date today = new Date(System.currentTimeMillis());
        assertEquals(today,report.getDate());
    }
}