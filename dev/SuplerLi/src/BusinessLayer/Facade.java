package BusinessLayer;

import java.util.HashMap;
import java.util.List;

public class Facade {
    BusinessLayer.Item_Order_Controller item_order_controller;
    BusinessLayer.SupplierController supplierController;
    public Facade(){
        item_order_controller = new BusinessLayer.Item_Order_Controller();
        supplierController = new BusinessLayer.SupplierController();
    }

    public void Addcontact(int id_sup, String new_contact) {
        supplierController.Addcontact(id_sup,new_contact);
    }

    public boolean IsSupplierExistInSystem(int id_sup) {
        supplierController.IsSupplierExistInSystem(id_sup);
    }
    public BusinessLayer.Contract ShowContract(int id_sup) {
        return supplierController.ShowContract(id_sup);
    }

    public BusinessLayer.Supplier ShowSupInformation(int id_sup) {
        supplierController.ShowSupInformation(id_sup);
    }

    public List<String> showContacts(int id_sup) {
       return supplierController.showContacts(id_sup);

    }
    public BusinessLayer.OutgoingOrder ShowOrder(int id_order){
        return item_order_controller.ShowOrder(id_order);
    }

    public boolean IsProductExistInSystem(int id_product){
        item_order_controller.IsProductExistInSystem(id_product);
    }
    public void AddNewOrder(int id_product, int amount) {
        item_order_controller.AddNewOrder(id_product,amount);
    }

    public boolean IsOrderExistInSystem(int id_order) {
        return item_order_controller.IsOrderExistInSystem(id_order);
    }

    public BusinessLayer.OutgoingOrder ShowOrderBySupplier(int id_sup) {
        return  item_order_controller.ShowOrderBySupplier(id_sup);
    }

    public void AddNewSupplier(int id, Long company, String name, List<String> contacts, String payment, String bank) {
        supplierController.AddSupplier(id,company,name,contacts,payment,bank);
    }


    //TODO: Implement the facade like this one: https://www.geeksforgeeks.org/facade-design-pattern-introduction/


}
