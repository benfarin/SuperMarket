package PresentationLayer;

import BusinessLayer.Facade;
import PresentationLayer.Inventory.InvService;
import PresentationLayer.Suppliers.SupService;

import java.util.Scanner;

public class Service {
    private InvService invService;
    private SupService supService;
    private Facade facade;
    public static Scanner s = new Scanner(System.in);

    public Service() {
        this.facade = new Facade();
        this.invService = new InvService(facade);
        this.supService = new SupService(facade);
    }
    public void startMenu() {
        
        do {
            System.out.println("\t\tSuper-Li :)\n\n" +
                    "1)\tInventory menu\n" +
                    "2)\tSupplier menu\n" +
                    "3)\tExit"
            );
            int choice = s.nextInt();
            switch (choice) {
                case 1:
                    invService.startMenu();
                    break;
                case 2:
                    supService.startMenu();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }
        while (true) ;
    }
}
