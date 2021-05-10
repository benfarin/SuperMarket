package Tests;

import BusinessLayer.Inventory.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    private static Category mainCategory;
    private static Category subCategory1;
    private static Category subCategory2;
    private static Category subCategory3;
    static List<Category> catList;


    @BeforeAll
    static void setUp() {
        subCategory1 = new Category("Low-fat");
        subCategory2 = new Category("Milk");
        subCategory3 = new Category("Dairy-free");
        mainCategory = new Category("Dairy");
        catList = new LinkedList<>();
        catList.add(subCategory1);
        catList.add(subCategory2);

    }

    @Test
    void addSub() {
        assertIterableEquals(mainCategory.getSubCategories(),new LinkedList<>());
        mainCategory.addSub(subCategory1);
        mainCategory.addSub(subCategory2);
        assertIterableEquals(mainCategory.getSubCategories(),catList);
    }

    @Test
    void deleteSubCat() {
        assertIterableEquals(mainCategory.getSubCategories(),catList);
        mainCategory.deleteSubCat(subCategory3);
        assertIterableEquals(mainCategory.getSubCategories(),catList);
        mainCategory.deleteSubCat(subCategory1);
        mainCategory.deleteSubCat(subCategory2);
        assertIterableEquals(mainCategory.getSubCategories(),new LinkedList<>());
    }

    @Test
    void setDiscount() throws ParseException {
        assertEquals(mainCategory.getDiscount(), 0);
        Date exDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/03/2022");
        Date exDate1 = new SimpleDateFormat("dd/MM/yyyy").parse("10/03/2020");
        mainCategory.setDiscount(10, exDate);
        assertEquals(mainCategory.getDiscount(), 10);
        mainCategory.setDiscount(15, exDate1);
        assertEquals(mainCategory.getDiscount(), 10);
        mainCategory.setDiscount(15, exDate);
        assertEquals(mainCategory.getDiscount(), 15);
    }

    @Test
    void setDiscountDate() throws ParseException {
        Date exDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/03/2022");
        Date exDate1 = new SimpleDateFormat("dd/MM/yyyy").parse("10/03/2020");
        assertEquals(mainCategory.getDiscountDate(), exDate);
        mainCategory.setDiscountDate(exDate1);
        assertEquals(mainCategory.getDiscountDate(), exDate);
    }

}