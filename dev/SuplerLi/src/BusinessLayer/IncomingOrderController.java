package BusinessLayer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class IncomingOrderController {
    // INT is the StoreCode for the product, LIST is for all the products from different suppliers under same code
    private HashMap<Long, LinkedList<Product>> products;
    private HashMap<Integer, BusinessLayer.OutgoingOrder> orders; // every order is identified by the id (the key) of the supplier


// We create an instance of this controller when we need to CREATE a new order or CHANGE existing order, then it closes.
    public IncomingOrderController(LinkedList<BusinessLayer.Product> allProductsList, LinkedList<BusinessLayer.OutgoingOrder> allOrdersList) {
        products = new HashMap<Long, LinkedList<Product>>();

        for (BusinessLayer.Product prod : allProductsList) {
            Long currStoreCode = prod.getStoreCode();
            if (!products.containsKey(currStoreCode)) { // If the product ISN'T in the map of products we create a new list of all similar products from different suppliers
                products.put(currStoreCode, new LinkedList<>());
            }
            products.get(currStoreCode).add(prod);

        }

        orders = new HashMap<>();
    }
//        for (BusinessLayer.OutgoingOrder order: allOrdersList ) {
//            long currOrderID = order
//            if (!products.containsKey(currStoreCode)) { // If the product ISN'T in the map of products we create a new list of all similar products from different suppliers
//                products.put(currStoreCode, new LinkedList<>());
//            }
//            products.get(currStoreCode).add(prod);
//


        //TODO: Fill all the previous orders.




    public  boolean IsProductExistInSystem(Long id_product){
        return  products.containsKey(id_product);
    }

    /**
     *
     * @param id_product
     * @param amount
     */
     public void AddNewOrder(Long id_product, int amount) { // need add arguments to facade
        double min=0;
        int id_supplier_min=0;
        int index = 0;

        LinkedList<Product> prod = products.get(id_product);
        if(prod!=null) {
            min = prod.getFirst().getSupplier().getContract().getTotalPriceDiscount(amount, prod.getFirst().GetCheapestPrice(amount));
            id_supplier_min=prod.getFirst().getSupplier().getId_supplier();
            for (int i = 1; i < prod.size(); i++) {
                double suspect_min = prod.get(i).getSupplier().getContract().getTotalPriceDiscount(amount,prod.get(i).GetCheapestPrice(amount));
                if (min > suspect_min) {
                    id_supplier_min = prod.get(i).getSupplier().getId_supplier();
                    min = suspect_min;
                    index = i;
                }
            }
            if (!orders.containsKey(id_supplier_min)) {
                BusinessLayer.OutgoingOrder order = new BusinessLayer.OutgoingOrder(id_supplier_min, null);
                orders.put(id_supplier_min, order);
            }
            orders.get(id_supplier_min).AddItem(prod.get(index).getStoreCode(), amount, min);
            //TODO: If order exists we don't have to create a new instance of it
        }
    }
    public boolean IsOrderExistInSystem(int id_order) {
        return orders.containsKey(id_order);
    }

    /**
     * This will return an existing order based on the STATIC auto generated order ID
     * @param id_order
     * @return
     */
    public BusinessLayer.OutgoingOrder ShowOrder(Long id_order){
        for (OutgoingOrder order : orders.values()) {
            if (order.getId().equals(id_order))
                return order;

        }
        return null;
//        for (BusinessLayer.OutgoingOrder order: orders){
//            if (order.IdOrder()==id_order){
//                return order;
//            }
//        }

    }

    public BusinessLayer.OutgoingOrder ShowOrderBySupplier(int id_sup) {
        return orders.get(id_sup);
    }
}
