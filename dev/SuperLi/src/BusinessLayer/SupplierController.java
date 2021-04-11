package BusinessLayer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;


public class SupplierController {
    private HashMap<Integer, BusinessLayer.Supplier> supplier; // the int key as a id_supplier
    Scanner io = new Scanner(System.in);

    public SupplierController(HashMap<Integer, BusinessLayer.Supplier> sup) {
        supplier = sup;
    }

    public boolean IsSupplierExistInSystem(int id_sup){
        return supplier.containsKey(id_sup);
    }
     public void AddContact(int id_sup, String contact){
            supplier.get(id_sup).AddContact(contact);
     }
    public void AddSupplierContract(int supplier_id){
        supplier.get(supplier_id).addContract();
    }

    public BusinessLayer.Contract ShowContract(int id_sup) {
        return supplier.get(id_sup).showContract();
    }

    public void DeleteSupplier(int id) {
        supplier.remove(id);
    }

    public void AddSupplier(int id,long company,String name,List<String> Contacts,String payment,String bank) {
        BusinessLayer.Supplier sup = new BusinessLayer.Supplier(id, company, name, Contacts, payment, bank);
        supplier.put(id,sup);
    }
    // This method returns a LIST of all products currently available by known suppliers
    public LinkedList<Product> getAllProducts(){
        LinkedList<Product> result = new LinkedList<>();
        for (Supplier sup: supplier.values()) {
            result.addAll(sup.getProducts().values());
        }
        return result;
    }

    public BusinessLayer.Supplier ShowSupInformation(int id_sup) {
        return supplier.get(id_sup);
    }

    public List<String> showContacts(int id_sup) {
        return supplier.get(id_sup).showContacts();
    }

}


