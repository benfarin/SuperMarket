package PresentationLayer;

import BusinessLayer.Facade;
import PresentationLayer.Inventory.InvService;
import PresentationLayer.Suppliers.SupService;
import PresentationLayer.Workers.MainService;

import java.util.Scanner;

public class Service {
    private InvService invService;
    private SupService supService;
    private Facade facade;
    private MainService mainService;
    public static Scanner s = new Scanner(System.in);

    public Service(Facade facade,MainService mainService) {
        this.facade = facade;
        this.invService = new InvService(facade);
        this.supService = new SupService(facade);
        this.mainService = mainService;
    }
    public void startMenu() {
        
        do {
            System.out.println("\t\tSuper-Li :)\n\n" +
                    "1)\tInventory menu\n" +
                    "2)\tSupplier menu\n" +
                    "3)\tLogout"
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
                    mainService.startMenu();
                    break;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }
        while (true) ;
    }
}
