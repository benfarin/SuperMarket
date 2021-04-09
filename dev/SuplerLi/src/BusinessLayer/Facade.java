package BusinessLayer;

public class Facade {
    BusinessLayer.Item_Order_Controller item_order_controller;
    BusinessLayer.SupplierController supplierController;
    public Facade(){
        item_order_controller = new BusinessLayer.Item_Order_Controller();
        BusinessLayer.SupplierController Sup = new BusinessLayer.SupplierController();
    }
    /*
    private void AddNewItem() {
        Scanner io = new Scanner(System.in);
        System.out.println("Enter an Store Code");
        long store_code = io.nextInt();
        if(products.containsKey(store_code))
        {
            System.out.println(" Store Code: "+ store_code+"  is still exist");
            return;
        }
        else {
            System.out.println("Enter Name");
            String name= io.next();
            System.out.println("Enter Id product"); // the supplier which get here need know the ID product 
            long id= io.nextInt();
            System.out.println("Enter Price");
            double price= io.nextInt();
            System.out.println("Enter ");
            long id= io.nextInt();



        }

    }
    */

    //TODO: Implement the facade like this one: https://www.geeksforgeeks.org/facade-design-pattern-introduction/


}
