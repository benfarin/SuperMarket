package PresentationLayer;

import BusinessLayer.Facade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Service {
    public static Scanner s = new Scanner(System.in);
    Facade facade;

    public Service(){
        this.facade = new Facade();
    }

    public void startMenu() {
        while (true) {
            System.out.println("\t\tSuper-Li's inventory department\n\n" +
                    "1)\tProduct menu\n" +
                    "2)\tCategory menu\n" +
                    "3)\tDisplay stock report\n" +
                    "4)\tDisplay defective report\n" +
                    "5)\tDisplay product price history\n" +
                    "6)\tExit"
            );

            int choice = s.nextInt();
            switch (choice) {
                case 1:
                    productMenu();
                    break;
                case 2:
                    categoryMenu();
                    break;
                case 3:
                    stockReport();
                    break;
                case 4:
                    defectiveReport();
                    break;
                case 5:
                    productHistory();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }
    }
    public void productMenu(){
        while (true) {
            System.out.println("\t\tProduct menu:\n\n" +
                    "1)\tAdd product\n" +
                    "2)\tUpdate product quantity\n" +
                    "3)\tUpdate product manufacture\n" +
                    "4)\tUpdate product price from supplier\n" +
                    "5)\tUpdate product category\n" +
                    "6)\tUpdate product price to costumer\n" +
                    "7)\tUpdate product minimum quantity\n" +
                    "8)\tUpdate product discount\n" +
                    "9)\tUpdate product defective quantity\n" +
                    "10)\tExit"
            );

            int choice = s.nextInt();
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateQuantity();
                    break;
                case 3:
                    updateManufacture();
                    break;
                case 4:
                    updatePriceFromSupplier();
                    break;
                case 5:
                    updateProductCategory();
                    break;
                case 6:
                    updatePriceToCustomer();
                    break;
                case 7:
                    updateMinimumQuantity();
                    break;
                case 8:
                    updateDiscount();
                    break;
                case 9:
                    updateDefectiveQuantity();
                case 10:
                    return;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }
    }

    private void updateDefectiveQuantity() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.println("\nDefective item count- ");
        int quantity = s.nextInt();
       // String result = facade.
    }

    private void updateDiscount(){
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.print("\nDiscount percentage- ");
        int discount = s.nextInt();
        boolean dateCorrect = false;
        while (!dateCorrect) {
            try {
                System.out.print("\nDiscount expiration date (DD/MM/YYYY)- ");
                Date exDate = new SimpleDateFormat("dd/MM/yyyy").parse(s.next());
                dateCorrect = true;
            } catch (ParseException e) {
                System.out.print("\nDate format incorrect, please try again. ");
            }
        }
        // String result = facade.
    }

    private void updateMinimumQuantity() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.print("\nMinimum quantity- ");
        int minQuantity = s.nextInt();
        // String result = facade.
    }

    private void updatePriceToCustomer() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.print("\nPrice for costumers- ");
        int minQuantity = s.nextInt();
        // String result = facade.
    }

    private void updateProductCategory() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.print("\nProduct's category- ");
        String category = s.next();
        //todo : delete + add + edit ?

        // String result = facade.
    }

    private void updatePriceFromSupplier() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.print("\nPrice supplier price- ");
        int minQuantity = s.nextInt();
        // String result = facade.
    }

    private void updateManufacture() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.print("\nManufacture name- ");
        String man = s.next();
        //todo : delete + add + edit ?

        // String result = facade.
    }

    private void updateQuantity() {
        System.out.print("Product's name- ");
        String product = s.next();
        System.out.print("\nQuantity- ");
        int quantity = s.nextInt();
        //todo: add + change ?

        // String result = facade.
    }

    private void addProduct() {
        System.out.print("Product's name- ");
        String product = s.next();
        // String result = facade.
    }

    private void categoryMenu(){}
    private void stockReport(){}
    private void defectiveReport(){}
    private void productHistory(){}





}

