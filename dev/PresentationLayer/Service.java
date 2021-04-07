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

    private void productMenu() {
        while (true) {
            System.out.println("\t\tProduct menu:\n\n" +
                    "1)\tAdd category\n" +
                    "2)\tUpdate product defective quantity\n" +
                    "3)\tUpdate product manufacture\n" +
                    "4)\tUpdate product price from supplier\n" +
                    "5)\tUpdate product category\n" +
                    "6)\tUpdate product price to costumer\n" +
                    "7)\tUpdate product minimum quantity\n" +
                    "8)\tUpdate product discount\n" +
                    "9)\tReduce quantity in storage\n" +
                    "10)\tAdd to quantity in storage\n" +
                    "11)\tAdd to quantity in store\n" +
                    "12)\tReduce quantity in store\n" +
                    "13)\tExit"
            );

            int choice = s.nextInt();
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateDefectiveQuantity();
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
                    reduceStorageQuantity();
                    break;
                case 10:
                    addStorageQuantity();
                    break;
                case 11:
                    addStoreQuantity();
                    break;
                case 12:
                    reduceStoreQuantity();
                    break;
                case 13:
                    return;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }
    }

    private void categoryMenu(){
        while (true) {
            System.out.println("\t\tProduct menu:\n\n" +
                    "1)\tAdd subcategory\n" +
                    "2)\tDelete subcategory\n" +
                    "3)\tDelete product form category\n" +
                    "4)\tSet category discount\n" +
                    "5)\tUpdate category discount expiration date\n" +
                    "6)\tExit"
            );

            int choice = s.nextInt();
            switch (choice) {
                case 1:
                    addSub();
                    break;
                case 2:
                    deleteSubCat();
                    break;
                case 3:
                    deleteProdFromCat();
                    break;
                case 4:
                    setCatDiscount();
                    break;
                case 5:
                    setCatDiscountDate();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }
    }

    private void addSub() {
        System.out.print("\nMain category name- ");
        String mainCat = s.next();
        System.out.println("\nSubcategory name- ");
        String subCat = s.next();
        System.out.println(facade.addSub(mainCat, subCat));
    }
    private void deleteProdFromCat() {
        System.out.print("\nMain category name- ");
        String mainCat = s.next();
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.println(facade.addSub(mainCat, product));
    }
    private void setCatDiscountDate() {
        System.out.print("\nCategory name- ");
        String cat = s.next();
        Date exDate = null;
        while (exDate == null) {
            try {
                System.out.print("\nDiscount expiration date (DD/MM/YYYY)- ");
                exDate = new SimpleDateFormat("dd/MM/yyyy").parse(s.next());
            } catch (ParseException e) {
                System.out.print("\nDate format incorrect, please try again. ");
            }
        }
        System.out.println(facade.setCatDiscountDate(cat,exDate));
    }
    private void setCatDiscount() {
        System.out.print("\nCategory name- ");
        String cat = s.next();
        System.out.print("\nDiscount percentage- ");
        int discount = s.nextInt();
        Date exDate = null;
        while (exDate == null) {
            try {
                System.out.print("\nDiscount expiration date (DD/MM/YYYY)- ");
                exDate = new SimpleDateFormat("dd/MM/yyyy").parse(s.next());
            } catch (ParseException e) {
                System.out.print("\nDate format incorrect, please try again. ");
            }
        }
        System.out.println(facade.setCatDiscount(cat,discount,exDate));

    }
    private void deleteSubCat(){
        System.out.print("\nMain category name- ");
        String mainCat = s.next();
        System.out.println("\nSubcategory name- ");
        String subCat = s.next();
        System.out.println(facade.deleteSubCat(mainCat, subCat));
    }
    private void reduceStoreQuantity() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.println("\nReduce quantity by- ");
        int quantity = s.nextInt();
        System.out.println(facade.reduceStoreQuantity(product, quantity));
    }
    private void addStoreQuantity() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.println("\nAdd to quantity- ");
        int quantity = s.nextInt();
        System.out.println(facade.reduceStoreQuantity(product, quantity));
    }
    private void addStorageQuantity() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.println("\nAdd to quantity- ");
        int quantity = s.nextInt();
        System.out.println(facade.reduceStorageQuantity(product, quantity));
    }
    private void reduceStorageQuantity() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.println("\nReduce quantity by- ");
        int quantity = s.nextInt();
        System.out.println(facade.reduceStorageQuantity(product, quantity));
    }
    private void updateDefectiveQuantity() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.println("\nDefective item count- ");
        int quantity = s.nextInt();
        System.out.println(facade.setDefectiveItems(product, quantity));
    }
    private void updateDiscount(){
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.print("\nDiscount percentage- ");
        int discount = s.nextInt();
        Date exDate = null;
        while (exDate == null) {
            try {
                System.out.print("\nDiscount expiration date (DD/MM/YYYY)- ");
                exDate = new SimpleDateFormat("dd/MM/yyyy").parse(s.next());
            } catch (ParseException e) {
                System.out.print("\nDate format incorrect, please try again. ");
            }
        }
        System.out.println(facade.setProdDiscount(product,discount,exDate));
    }
    private void updateMinimumQuantity() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.print("\nMinimum quantity- ");
        int minQuantity = s.nextInt();
        System.out.println(facade.setMinimum(product, minQuantity));
    }
    private void updatePriceToCustomer() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.print("\nPrice for costumers- ");
        double price = s.nextDouble();
        System.out.println(facade.setPriceToCustomer(product, price));
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
        System.out.print("\nPrice from supplier- ");
        double price = s.nextDouble();
        System.out.println(facade.setPriceFromSupplier(product, price));
    }
    private void updateManufacture() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.print("\nManufacture name- ");
        String man = s.next();
        //todo : delete + add + edit ?
        // String result = facade.
    }

    private void addProduct() {
        System.out.print("Product's name- ");
        String product = s.next();
        System.out.print("\nProduct's category- ");
        String cat = s.next();
        System.out.print("\nManufacture name- ");
        String man = s.next();
        System.out.print("\nPrice from supplier- ");
        double supPrice = s.nextDouble();
        System.out.print("\nPrice for costumers- ");
        double cosPrice = s.nextDouble();
        System.out.print("\nMinimum quantity- ");
        int min = s.nextInt();
        System.out.println(facade.addProduct(product,cat,man,supPrice,cosPrice,min));
    }

    private void stockReport(){
        //todo: System.out.println(facade.);
    }
    private void defectiveReport(){
        //todo: System.out.println(facade.);
    }
    private void productHistory(){
        //todo: System.out.println(facade.);
    }





}

