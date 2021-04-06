package Entities;

import java.util.HashMap;
import java.time.*;

public class OutgoingOrder {
    private long id;
    private int supplier_id;
    private HashMap items;
    private LocalDate deliveryDate;
    private double totalPrice;
}
