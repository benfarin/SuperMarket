package PresentationLayer;

import BusinessLayer.Facade;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
                    setManufacture();
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
        System.out.println(facade.setDefectiveItems(product,quantity));
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
        System.out.println(facade.setMinimum(product,minQuantity));
        // String result = facade.
    }

    private void updatePriceToCustomer() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.print("\nPrice for costumers- ");
        double price = s.nextDouble();
        System.out.println(facade.setPriceToCustomer(product,price));
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
        double price = s.nextDouble();
        System.out.println(facade.setPriceFromSupplier(product,price));
        // String result = facade.
    }

    private void setManufacture() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        System.out.print("\nManufacture name- ");
        String man = s.next();
        //todo : delete + add + edit ?
        System.out.println(facade.setManufacture(product,man));
        // String result = facade.
    }

    private void updateQuantity() {
        while (true) {
            System.out.println("\t\tupdate quantity:\n\n" +
                    "1)\tset store quantity\n" +
                    "2)\tadd store quantity\n" +
                    "3)\treduce store quantity\n" +
                    "4)\tset storage quantity\n" +
                    "5)\tadd storage quantity\n" +
                    "6)\treduce storage quantity\n" +
                    "7)\tExit"
            );

            int choice = s.nextInt();
            switch (choice) {
                case 1:
                    setStoreQuantity();
                    break;
                case 2:
                    addStoreQuantity();
                    break;
                case 3:
                    reduceStoreQuantity();
                    break;
                case 4:
                    setStorageQuantity();
                    break;
                case 5:
                    addStorageQuantity();
                    break;
                case 6:
                    reduceStorageQuantity();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }
    }

    private void addProduct() {
        System.out.print("Product's name- ");
        String prodName = s.next();
        System.out.print("Product's category- ");
        String catName = s.next();
        System.out.print("Product's manufacture- ");
        String manufacture = s.next();
        System.out.print("Product's price from supplier- ");
        double priceFromSupplier = s.nextDouble();
        System.out.print("Product's price from supplier- ");
        double priceToCustomer = s.nextDouble();
        System.out.print("Product's minimum quantity in storage- ");
        int minimum = s.nextInt();
        System.out.println(facade.addProduct(prodName,catName,manufacture,priceFromSupplier,priceToCustomer,minimum));
        // String result = facade.
    }
    public void setStoreQuantity(){
        System.out.print("Product's name- ");
        String prodName = s.next();
        System.out.print("store quantity- ");
        int storeQuantity = s.nextInt();
        System.out.println(facade.setStoreQuantity(prodName,storeQuantity));
    }
    public void setStorageQuantity(){
        System.out.print("Product's name- ");
        String prodName = s.next();
        System.out.print("storage quantity- ");
        int storageQuantity = s.nextInt();
        System.out.println(facade.setStorageQuantity(prodName,storageQuantity));
    }
    public void addStoreQuantity(){
        System.out.print("Product's name- ");
        String prodName = s.next();
        System.out.print("quantity to add- ");
        int add = s.nextInt();
        System.out.println(facade.addStoreQuantity(prodName,add));
    }
    public void addStorageQuantity(){
        System.out.print("Product's name- ");
        String prodName = s.next();
        System.out.print("quantity to add- ");
        int add = s.nextInt();
        System.out.println(facade.addStorageQuantity(prodName,add));
    }
    public void reduceStoreQuantity(){
        System.out.print("Product's name- ");
        String prodName = s.next();
        System.out.print("quantity to reduce- ");
        int add = s.nextInt();
        System.out.println(facade.reduceStoreQuantity(prodName,add));
    }
    public void reduceStorageQuantity(){
        System.out.print("Product's name- ");
        String prodName = s.next();
        System.out.print("quantity to reduce- ");
        int add = s.nextInt();
        System.out.println(facade.reduceStorageQuantity(prodName,add));
    }

    private void categoryMenu(){
        while (true) {
            System.out.println("\t\tCategory menu:\n\n" +
                    "1)\tAdd category\n" +
                    "2)\tadd sub-category\n" +
                    "3)\tdelete sub=category\n" +
                    "4)\tset category discount\n" +
                    "5)\tset category discount date\n" +
                    "6)\tExit"
            );

            int choice = s.nextInt();
            switch (choice) {
                case 1:
                    addCategory();
                    break;
                case 2:
                    addSubCat();
                    break;
                case 3:
                    deleteSubCat();
                    break;
                case 4:
                    setCatDiscount();
                    break;
                case 5:
                    setCatDiscDate();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }
    }
    private void addCategory(){
        System.out.print("Category's name- ");
        String catName = s.next();
        System.out.print("Category's sub-categories(string,string,..)- ");
        String subCat = s.next();
        String[] a = subCat.split(",");
        List<String> subC = new LinkedList<>();
        for(int i = 0; i< a.length; i++)
            subC.add(a[i]);
        System.out.println(facade.addCategory(catName,subC));
    }
    private void addSubCat(){}
    private void deleteSubCat(){}
    private void setCatDiscount(){}
    private void setCatDiscDate(){}
    private void stockReport(){}
    private void defectiveReport(){}
    private void productHistory(){}





}

