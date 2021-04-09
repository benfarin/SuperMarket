package BusinessLayer;

import java.util.HashMap;
import java.time.*;

public class OutgoingOrder {
    private  static  Long static_id;
    private Long id;
    private Integer supplier_id;
    private HashMap<Long,Integer> items; //  the key is the id_tem ,  the value is the amount.
    private LocalDate deliveryDate;
    private double totalPrice;

    public  OutgoingOrder(Integer sup,LocalDate delivery_Date){
        id=static_id;
        static_id++;
        supplier_id = sup;
        items = new HashMap<Long, Integer>();
        deliveryDate = delivery_Date;
        totalPrice = 0;
    }
    public void AddItem(Long id_item,int amount , double price){
        if(items.containsKey(id_item)){
            items.put(id_item,items.get(id_item)+amount);
            totalPrice+=price;
        }
        else {
            items.put(id_item,amount);
            totalPrice+=price;
        }
        System.out.println("product: "+id_item+"  was added successfully to order number:  "+id+"by the supplier num: "+ supplier_id);

    }

}
