package PresentationLayer;

import java.util.HashMap;

public class Product {
    private String name;
    private Long storeCode;
    private Double price;
    private HashMap<Integer, Double> highAmountDiscount;
    private Long supplierSerialNum;
    private Integer id_supplier; // A way for presentation product to show its supplier
}
