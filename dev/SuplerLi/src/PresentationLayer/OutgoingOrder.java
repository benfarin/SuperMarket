package PresentationLayer;

import java.time.LocalDate;
import java.util.HashMap;

public class OutgoingOrder {
    private Long id;
    private Integer supplier_id;
    private HashMap<Integer, String> items;
    private String deliveryDate;
    private Double totalPrice;
}
