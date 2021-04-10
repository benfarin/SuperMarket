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

    /*public void SupplierMenu() {
        while (true) {
            System.out.println("*** Supplier menu ***");
            System.out.println("Please select an option:");
            System.out.println("1) Add New Supplier ");
            System.out.println("2) Delete Supplier");
            System.out.println("3) Show Contract");
            System.out.println("4) Exit ");


            int op = io.nextInt();

            switch (op) {
                case 1: {
                    AddSupplier();
                    break;
                }

                case 2: {
                    DeleteSupplier();
                    break;
                }
                case 3: {
                    System.out.println("Enter supplier ID to see their contract");
                    int supID = io.nextInt();
                    ShowContract(supID);
                    break;
                }

                case 4: {
                    return;
                }
                default: {
                    System.out.println("Selection Unrecognized");
                    break;
                }

            }
        }
    }*/
    public boolean IsSupplierExistInSystem(int id_sup){
        return supplier.containsKey(id_sup);
    }
     public void AddContact(int id_sup, String contact){
            supplier.get(id_sup).AddContact(contact);
     }

    public BusinessLayer.Contract ShowContract(int id_sup) {
        return supplier.get(id_sup).showContract();
    }

    public void DeleteSupplier() {
        System.out.println("Insert a Supplier ID");
        int id = io.nextInt();
        try {
            supplier.remove(id);
        } catch (Exception e) // need be change accordingly the service responses
        {
            System.out.println("Invalid id, Insert again");
            DeleteSupplier();
        }
        System.out.println("Supplier " + id + " Deleted!");
    }

    public void AddSupplier(int id,long company,String name,List<String> Contacts,String payment,String bank) {
        BusinessLayer.Supplier sup = new BusinessLayer.Supplier(id, company, name, Contacts, payment, bank);
        supplier.put(id,sup);
    }
    // This method returns a LIST of all products currently available by known suppliers
    public LinkedList<Product> getAllProducts(){
        LinkedList<Product> result = new LinkedList<>();
        for (Supplier sup: supplier.values()) {
            for (Product prod: sup.getProducts().values() ) {
                result.add(prod);
            }
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


