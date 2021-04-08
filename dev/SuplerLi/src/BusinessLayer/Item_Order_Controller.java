package BusinessLayer;

import java.util.HashMap;
import java.util.Scanner;
import java.util.List;

public class Item_Order_Controller {
    private HashMap<Integer, List<BusinessLayer.Product>> products; // the int key as a id_product
    private HashMap<Long, BusinessLayer.OutgoingOrder> orders; // every order is identified by the id (the key) of the supplier

    Scanner io = new Scanner(System.in);


    public Item_Order_Controller() {
        products = new HashMap<Integer, List<BusinessLayer.Product>>();
        orders = new HashMap<Long,  BusinessLayer.OutgoingOrder>();

        //TODO: Fill products in the constructor here or with new controller
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
        int min=0;
        int id_supplier_min=0;
        System.out.println("enter an ID prod"); // the id may be changein the future  by entering num product, manufacturer, and gramp_roduct and make a hash code convert to this integer
        int id_product = io.nextInt();
        System.out.println("enter an Amount");
        int amount = io.nextInt();
        List<BusinessLayer.Product> prod= products.get(id_product);
        if(prod!=null)
        {
            min=prod.get(0).getSupplier().getContract().getTotalPriceDiscount(amount);
        }
        for (int i=1;i<prod.size();i++){
            int suspect_min = prod.get(i).getSupplier().getContract().getTotalPriceDiscount(amount);
            if(min>suspect_min){
                id_supplier_min = prod.get(i).getSupplier().getId_supplier();
                min=suspect_min;
            }
        }

        //TODO: If order exists we don't have to create a new instance of it

        BusinessLayer.OutgoingOrder order = new OutgoingOrder();
        orders.put(id_supplier_min,order);



    }

}
