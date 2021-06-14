package BusinessLayer.Suppliers;

import DataLayer.OrderMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class IncomingOrderController {
    // INT is the StoreCode for the product, LIST is for all the products from different suppliers under same code
    private HashMap<Long, LinkedList<ProductPerSup>> products;
    // TODO: We need to add UrgentOrders. this orders will be Weekly Orders
    private HashMap<Integer, OutgoingOrder> orders; // every order is identified by the id (the key) of the supplier
    private HashMap<Integer, OutgoingOrder> urgentOrders;


// We create an instance of this controller when we need to CREATE a new order or CHANGE existing order, then it closes.
    public IncomingOrderController(HashMap<Long, LinkedList<ProductPerSup>> allProducts, HashMap<Integer, OutgoingOrder> allOrders, HashMap<Integer, OutgoingOrder> urgentOrders) {
       /* OLD CREATION OF ALL PRODUCTS MAP BEFORE WE HAD SQL, FROM A LIST OF ALL PRODUCTS

       products = new HashMap<Long, LinkedList<ProductPerSup>>();

        for (ProductPerSup prod : allProductsList) {
            Long currStoreCode = prod.getStoreCode();
            if (!products.containsKey(currStoreCode)) { // If the product ISN'T in the map of products we create a new list of all similar products from different suppliers
                products.put(currStoreCode, new LinkedList<>());
            }
            products.get(currStoreCode).add(prod);

        }
*/
       this.products=allProducts;
        orders = allOrders;
        this.urgentOrders=urgentOrders;
    }
//        for (BusinessLayer.Suppliers.OutgoingOrder order: allOrdersList ) {
//            long currOrderID = order
//            if (!products.containsKey(currStoreCode)) { // If the product ISN'T in the map of products we create a new list of all similar products from different suppliers
//                products.put(currStoreCode, new LinkedList<>());
//            }
//            products.get(currStoreCode).add(prod);
//

    public OutgoingOrder getOrder(int idOrder) {
        if(!orders.containsKey(idOrder)){
            return null;
        }
        return orders.get(idOrder);
    }


    //TODO: Fill all the previous orders.


    public String printProductsSerialNumbers(Integer supplier_id){
        int prodCounter=0;
        String s="** Order of supplier #"+supplier_id+" **\n";
        s+="Supplier Serial Number \t Product Name \t SuperLi Store Code\n";

        if (orders.get(supplier_id)==null || orders==null){
            s="Order doesn't exist";
            return s;
        }

        for(Long productStoreCode : orders.get(supplier_id).getItems().keySet()){
            for(ProductPerSup prod : products.get(productStoreCode)){
                if (prod.getSupplier().getId_supplier()==supplier_id)
                    prodCounter++;
                    s+=prodCounter+". "+prod.getSupplierSerialNum()+"\t"+prod.getName()+"\t"+prod.getStoreCode()+"\n";
            }
        }


        return s;
    }

    public  boolean IsProductExistInSystem(Long id_product){
        return  products.containsKey(id_product);
    }

    /**
     *
     * @param id_product
     * @param amount
     * @returns the supplier ID so I can store the order or -1 if it wasn't a new order
     */


    public void AddNewUrgentOrder(Long id_product, int amount) { // need add arguments to facade
        double min=0;
        int id_supplier_min=0;
        int index = 0;


        LinkedList<ProductPerSup> prod = products.get(id_product);
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
            if (!urgentOrders.containsKey(id_supplier_min)) {
                OutgoingOrder order = new OutgoingOrder(id_supplier_min, null);

                urgentOrders.put(id_supplier_min, order);

                OrderMapper.addNewUrgentOrder(order.getId(), order.getDeliveryDate() , order.getTotalPrice());
            }
            OutgoingOrder existingOrder=urgentOrders.get(id_supplier_min);

            existingOrder.AddItem(prod.get(index).getStoreCode(), amount, min);
            OrderMapper.addNewItemOrder(existingOrder.getId(), id_supplier_min, prod.get(index).getStoreCode(), amount);


        }

    }

     public void AddNewOrder(Long id_product, int amount) { // need add arguments to facade
        double min=0;
        int id_supplier_min=0;
        int index = 0;


        LinkedList<ProductPerSup> prod = products.get(id_product);
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
                OutgoingOrder order = new OutgoingOrder(id_supplier_min, new Date());

                orders.put(id_supplier_min, order);

                OrderMapper.addNewOrder(order.getId(), order.getDeliveryDate() , order.getTotalPrice());
            }
            OutgoingOrder existingOrder=orders.get(id_supplier_min);

            existingOrder.AddItem(prod.get(index).getStoreCode(), amount, min);
            OrderMapper.addNewItemOrder(existingOrder.getId(), id_supplier_min, prod.get(index).getStoreCode(), amount);
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
    public OutgoingOrder ShowOrder(Long id_order){
        for (OutgoingOrder order : orders.values()) {
            if (order.getId().equals(id_order))
                return order;

        }
        return null;
//        for (BusinessLayer.Suppliers.OutgoingOrder order: orders){
//            if (order.IdOrder()==id_order){
//                return order;
//            }
//        }

    }

    public OutgoingOrder ShowOrderBySupplier(int id_sup) {
        return orders.get(id_sup);
    }
}
