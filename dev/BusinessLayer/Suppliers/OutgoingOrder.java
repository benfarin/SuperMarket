package BusinessLayer.Suppliers;

import java.util.*;

public class OutgoingOrder {
    private  static  Long static_id= new Long(0);
    private Long id;
    private Integer supplier_id;
    private HashMap<Long,Integer> items; //  the key is the id_tem ,  the value is the amount.
    //TODO: nim: I think Key needs to be the supplierSerialNum (because this order goes to them). Or maybe the product object and value=amount
    private Date deliveryDate;
    private double totalPrice;

    public Integer getSupplier_id() {
        return supplier_id;
    }

    // this constructor is from AddNewOrder
    public OutgoingOrder(Integer sup, Date delivery_Date){
        id=static_id;
        static_id++;
        supplier_id = sup;
        items = new HashMap<Long, Integer>();
        deliveryDate = delivery_Date;
        totalPrice = 0;
    }

    //This is from mappers
    public OutgoingOrder(Integer sup, Date delivery_Date, HashMap<Long, Integer> items, double totalPrice){
        id=static_id; //TODO: Make the IDs count properly from 0 as they are loaded
        static_id++;
        this.supplier_id = sup;
        this.items=items;
        this.deliveryDate = delivery_Date;
        this.totalPrice=totalPrice;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public HashMap<Long, Integer> getItems() { return items; }

    public Long getId() {return id;}

    public void AddItem(Long id_item, int amount , double price){
        if(items.containsKey(id_item)){
            items.put(id_item, items.get(id_item) + amount);
            totalPrice+=price;
        }
        else {
            items.put(id_item,amount);
            totalPrice+=price;
        }
        System.out.println("product #"+id_item+" was added successfully to order #: "+id+" by the supplier #"+ supplier_id);

    }

    public boolean isItemExistInOrder(Long id_item){return items.containsKey(id_item);}



    @Override
    public String toString() {
        return "OutgoingOrder{" +
                "id=" + id +
                ", supplier_id=" + supplier_id +
                ", items=" + items +
                ", deliveryDate=" + deliveryDate +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Set<Long> getItemsInOrder() {
        return items.keySet();
    }

    public int getItemsQuanityInOrder(Long prodId) {
        return items.get(prodId);
    }
}