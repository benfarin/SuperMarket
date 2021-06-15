package BusinessLayer.Suppliers;

import DataLayer.DataHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;


public class SupplierController {
    private HashMap<Integer, Supplier> supplier; // the int key as a id_supplier
    Scanner io = new Scanner(System.in);

    public SupplierController(HashMap<Integer, Supplier> sup) {
        supplier = sup;
    }

    public boolean IsSupplierExistInSystem(int id_sup){
        return supplier.containsKey(id_sup);
    }

    public void AddContact(int id_sup, String contact){

        supplier.get(id_sup).AddContact(contact);



     }
    public void AddSupplierContract(int supplier_id,String days,String location,boolean NeedDelivery,HashMap<Integer,Integer> totalPriceDiscount){
        supplier.get(supplier_id).addContract(days,location,NeedDelivery,totalPriceDiscount);
    }

    public Contract ShowContract(int id_sup) {
        return supplier.get(id_sup).showContract();
    }

    public void DeleteSupplier(int id) {
        supplier.remove(id);
    }

    public void AddSupplier(int id,long company,String name,List<String> Contacts,String payment,String bank) {
        Supplier sup = new Supplier(id, company, name, Contacts, payment, bank);
        supplier.put(id,sup);
    }

    // This method returns a LIST of all products currently available by known suppliers
    public LinkedList<ProductPerSup> getAllProducts(){
        LinkedList<ProductPerSup> result = new LinkedList<>();


        for (Supplier sup: supplier.values()) {
            result.addAll(sup.getProducts().values());
        }
        return result;
    }

    public Supplier ShowSupInformation(int id_sup) {
        return supplier.get(id_sup);
    }

    public List<String> showContacts(int id_sup) {
        return supplier.get(id_sup).showContacts();
    }

}


