package BusinessLayer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;

public class Item_Order_Controller {
    // INT is the StoreCode for the product, LIST is for all the products from different suppliers under same code
    private HashMap<Long, List<BusinessLayer.Product>> products;
    private HashMap<Integer, BusinessLayer.OutgoingOrder> orders; // every order is identified by the id (the key) of the supplier

    Scanner io = new Scanner(System.in);

// We create an instance of this controller when we need to CREATE a new order or CHANGE existing order, then it closes.
    public Item_Order_Controller(LinkedList<Product> allProductsList, LinkedList<OutgoingOrder> allOrdersList) {
        products = new HashMap<>();
        //LinkedList<Product> allProductsList = supplierController.getAllProducts();
        for (Product prod: allProductsList ) {
            Long currStoreCode = prod.getStoreCode();
            if (!products.containsKey(currStoreCode)) { // If the product ISN'T in the map of products we create a new list of all similar products from different suppliers
                products.put(currStoreCode, new LinkedList<>());
            }
            products.get(currStoreCode).add(prod);

        }

        orders = new HashMap<>();
        for (OutgoingOrder order: allOrdersList ) {
//            long currOrderID = order
//            if (!products.containsKey(currStoreCode)) { // If the product ISN'T in the map of products we create a new list of all similar products from different suppliers
//                products.put(currStoreCode, new LinkedList<>());
//            }
//            products.get(currStoreCode).add(prod);
//
        }

        //TODO: Fill all the previous orders.
    }

    public void ItemOrderMenu() {
        while (true) {
            System.out.println("*** Item Order menu ***");
            System.out.println("Please select an option:");
            System.out.println("1) Add New Order ");
            System.out.println("2)  Show Orders for a supplier");
            System.out.println("3) Exit ");


            int op = io.nextInt();

            switch (op) {
                case 1: {
                    AddNewOrder();
                    break;
                }
                case 2 : {
                    ShowOrder();
                    break;
                }
                case 3: {
                    return;
                }
                default: {
                    System.out.println("Selection Unrecognized");
                    break;
                }

            }
        }
    }

    private void ShowOrder() {
        System.out.println("Insert an Id Supplier");
        int id = io.nextInt();
        System.out.println(orders.get(id));
    }

    private void AddNewOrder() {
        double min=0;
        int id_supplier_min=0;
        int index = 0 ; 
        System.out.println("enter an ID prod"); // the id may be changein the future  by entering num product, manufacturer, and gramp_roduct and make a hash code convert to this integer
        int id_product = io.nextInt();
        System.out.println("enter an Amount");
        int amount = io.nextInt();
        List<BusinessLayer.Product> prod= products.get(id_product);
        if(prod!=null)
        {
            min=prod.get(0).getSupplier().getContract().getTotalPriceDiscount(amount,prod.get(0).getPrice());
        }
        
        for (int i=1;i<prod.size();i++){
            double suspect_min = prod.get(i).getSupplier().getContract().getTotalPriceDiscount(amount,prod.get(i).getPrice());
            if(min>suspect_min){
                id_supplier_min = prod.get(i).getSupplier().getId_supplier();
                min=suspect_min;
                index = i;
            }
        }
        if(!orders.containsKey(id_supplier_min)){
            BusinessLayer.OutgoingOrder order = new BusinessLayer.OutgoingOrder(id_supplier_min,null);
            orders.put(id_supplier_min,order);
        }
        orders.get(id_supplier_min).AddItem(prod.get(index).getStoreCode(),amount,min);
        
        //TODO: If order exists we don't have to create a new instance of it
        
    }

}
