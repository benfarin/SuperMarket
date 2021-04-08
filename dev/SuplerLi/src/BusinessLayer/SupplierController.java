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

    public void SupplierMenu() {
        while (true) {
            System.out.println("*** Supplier menu ***");
            System.out.println("Please select an option:");
            System.out.println("1) Add New Supplier ");
            System.out.println("2) Delete Supplier");
            System.out.println("3) Supplier Menu");
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
                    ShowContract();
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
    }

    private void ShowContract() {
        int id_sup;
        System.out.println("Enter an ID Supplier");
        id_sup = io.nextInt();
        try {
            System.out.println(supplier.get(id_sup).getContract());
        } catch (Exception e) { // need be change accordingly the service responses
            System.out.println("ID supplier  " + id_sup + " is not exist");
            ShowContract();
        }

    }

    private void DeleteSupplier() {
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

    private void AddSupplier() {
        System.out.println("Insert  an ID:");
        int id = io.nextInt();

        System.out.println("Insert  a Company Num:");
        int company = io.nextInt();

        System.out.println("Insert  a name:");
        String name = io.next();

        System.out.println("Insert Contact:");
        String contact = io.next();
        List<String> Contacts = new LinkedList<>();
        Contacts.add(contact);

        System.out.println("Insert Payment Method:");
        String payment = io.next();

        System.out.println("Insert  a Bank Account:");
        String bank = io.next();

        BusinessLayer.Supplier sup = new BusinessLayer.Supplier(id, company, name, Contacts, payment, bank);
        supplier.put(id,sup);
        System.out.println("Supplier was Successfully added , you may add a Contract");
    }
}


