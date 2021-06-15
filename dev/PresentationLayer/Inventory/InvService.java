package PresentationLayer.Inventory;

import BusinessLayer.Facade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InvService {
    public static Scanner s = new Scanner(System.in);
    //            .useDelimiter("\n");
    Facade facade;

    public InvService(Facade facade){
        this.facade = facade;
    }

    public void startMenu() {
        do {
            System.out.println("\t\tSuper-Li's inventory department\n\n" +
                    "1)\tProduct menu\n" +
                    "2)\tCategory menu\n" +
                    "3)\tStock report menu\n" +
                    "4)\tDefective report\n" +
                    "5)\tReceive Delivery\n"+
                    "6)\tSet periodic order day\n"+
                    "7)\tExit"
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
                    acceptDelivery();
                    break;
                case 6:
                    setDayOfPeriodicOrder();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }
        while (true) ;
    }

    private void setDayOfPeriodicOrder() {
        System.out.println("Insert the number of the requested day (1-sunday, 2-monday,...)- ");
        int day = s.nextInt();
        if(day < 1 || day > 7){
            System.out.println("Invalid day, try again");
        }
        else{
            facade.setOrderDay(day);
            System.out.println("The periodic order's day successfully changed");
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
                    "10)\tDisplay product\n" +
                    "11)\tDisplay product price from suppler history\n" +
                    "12)\tDisplay product price to customer history\n" +
                    "13)\tDelete Product\n" +
                    "14)\tDisplay all products\n" +
                    "15)\tExit"
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
                    break;
                case 10:
                    printProduct();
                    break;
                case 11:
                    productPFSHistory();
                    break;
                case 12:
                    productPTCHistory();
                    break;
                case 13:
                    deleteProduct();
                    break;
                case 14:
                    System.out.println(facade.showAllProds());
                    break;
                case 15:
                    return;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }
    }

    private void deleteProduct() {
        System.out.print("\nProduct's name- ");
        String product = s.next();
        int id = -1;
        id = facade.getProdIDByName(product);
        if(id == -1)
            System.out.println("Product does not exist");
        else{
            facade.deleteProduct(id);
            System.out.println("Product " + product + " successfully deleted");
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
        Date disDate = new Date();
        while (!dateCorrect) {
            try {
                System.out.print("\nDiscount expiration date (DD/MM/YYYY)- ");
                Date exDate = new SimpleDateFormat("dd/MM/yyyy").parse(s.next());
                disDate = exDate;
                dateCorrect = true;
            } catch (ParseException e) {
                System.out.print("\nDate format incorrect, please try again. ");
            }
        }
        System.out.println(facade.setProdDiscount(product,discount,disDate));
        // String result = facade.
    }
    private void acceptDelivery() {
        Map<Long, Integer> missingProds = new HashMap<>();
        Map<Long, Integer> defectiveProds = new HashMap<>();
        ;
        System.out.print("\nEnter order's ID- ");
        int orderId = s.nextInt();
        List<String> prodNames = facade.getProdFromOrder(orderId);
        if (prodNames == null) {
            System.out.print("\nNo such order \n");
            return;
        }
        for (String prodName : prodNames) {
            System.out.print("\nIs there missing products from " + prodName + " write y/n- ");
            String missing = s.next();
            if (missing.equals("y")) {
                System.out.print("\nHow many product are missing from " + prodName + "- ");
                int quantityMissing = s.nextInt();
                int prodId = facade.getProdIdByName(prodName);
                if (prodId != -1)
                    missingProds.put((long) prodId, quantityMissing);
                System.out.print("\nIs there defective products from " + prodName + " write y/n- ");
                String defective = s.next();
                if (defective.equals("y")) {
                    System.out.print("\nHow many product are defective from " + prodName + "- ");
                    int quantityDef = s.nextInt();
                    prodId = facade.getProdIdByName(prodName);
                    if (prodId != -1)
                        defectiveProds.put((long) prodId, quantityDef);
                }
            }
        }
        System.out.println(facade.acceptDelivery(orderId,missingProds,defectiveProds));
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
        System.out.print("Product's price to customer- ");
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
    public void printProduct(){
        System.out.print("Product's name- ");
        String prodName = s.next();
        System.out.println(facade.printProduct(prodName));
    }

    private void categoryMenu(){
        while (true) {
            System.out.println("\t\tCategory menu:\n\n" +
                    "1)\tAdd category\n" +
                    "2)\tadd sub-category\n" +
                    "3)\tdelete sub-category\n" +
                    "4)\tset category discount\n" +
                    "5)\tset category discount date\n" +
                    "6)\tDisplay category\n" +
                    "7)\tdelete category\n" +
                    "8)\tDisplay all categories\n" +
                    "9)\tExit"
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
                    printCategory();
                    break;
                case 7:
                    deleteCategory();
                    break;
                case 8:
                    System.out.println(facade.showAllCats());
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }
    }
    private void deleteCategory(){
        System.out.print("Category's name- ");
        String catName = s.next();
        System.out.println(facade.deleteCategory(catName));
    }
    private void addCategory(){
        System.out.print("Category's name- ");
        String catName = s.next();
        System.out.println(facade.addCategory(catName));

    }
    private void addSubCat(){
        System.out.print("Main category's name- ");
        String mainCat = s.next();
        System.out.print("Sub category's name- ");
        String subCat = s.next();

        System.out.println(facade.addSub(mainCat,subCat));
        facade.addSup(mainCat,subCat);
    }
    private void deleteSubCat(){
        System.out.print("Main category's name- ");
        String mainCat = s.next();
        System.out.print("Sub category's name- ");
        String subCat = s.next();

        System.out.println(facade.deleteSubCat(mainCat,subCat));
        facade.deleteSup(subCat);
    }
    private void printCategory(){
        System.out.print("Category's name- ");
        String catName = s.next();
        System.out.println(facade.printCategory(catName));
    }
    private void setCatDiscount(){
        System.out.print("\nCategory's name- ");
        String catName = s.next();
        System.out.print("\nDiscount percentage- ");
        int discount = s.nextInt();
        boolean dateCorrect = false;
        Date disDate = new Date();
        while (!dateCorrect) {
            try {
                System.out.print("\nDiscount expiration date (DD/MM/YYYY)- ");
                Date exDate = new SimpleDateFormat("dd/MM/yyyy").parse(s.next());
                disDate = exDate;
                dateCorrect = true;
            } catch (ParseException e) {
                System.out.print("\nDate format incorrect, please try again. ");
            }
        }
        System.out.println(facade.setCatDiscount(catName,discount,disDate));
    }
    private void setCatDiscDate(){
        System.out.print("\nCategory's name- ");
        String catName = s.next();
        boolean dateCorrect = false;
        Date disDate = new Date();
        while (!dateCorrect) {
            try {
                System.out.print("\nDiscount expiration date (DD/MM/YYYY)- ");
                Date exDate = new SimpleDateFormat("dd/MM/yyyy").parse(s.next());
                disDate = exDate;
                dateCorrect = true;
            } catch (ParseException e) {
                System.out.print("\nDate format incorrect, please try again. ");
            }
        }
        System.out.println(facade.setCatDisDate(catName,disDate));
    }
    private void stockReport(){
        while (true) {
            System.out.println("\t\tStock report menu:\n\n" +
                    "1)\tadd Stock Report\n" +
                    "2)\tadd category to stock report\n" +
                    "3)\texport stock report\n" +
                    "4)\tExit"
            );

            int choice = s.nextInt();
            switch (choice) {
                case 1:
                    addStockRep();
                    break;
                case 2:
                    addCatToStRep();
                    break;
                case 3:
                    exportStoRep();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }

    }
    private void changeDay(){
        System.out.println("On choose the number of the day you would like to send the report on:\n" +
                "1)\tSunday\n" +
                "2)\tMonday\n" +
                "3)\tTuesday\n" +
                "4)\tWednesday\n" +
                "5)\tThursday\n" +
                "6)\tFriday\n" +
                "7)\tSaturday\n");
        facade.setOrderDay(s.nextInt());
    }
    private void addStockRep(){
        System.out.print("\nWhich categories to include in the report- ");
        String catsNames = s.next();
        String[] a = catsNames.split(",");
        List<String> cats = new LinkedList<>();
        for(int i = 0; i< a.length; i++)
            cats.add(a[i]);
        System.out.println(facade.addStockReport(cats));
    }
    private void addCatToStRep(){
        System.out.print("\nReport's ID number- ");
        int id = s.nextInt();
        System.out.print("\nCategory's name- ");
        String catName = s.next();
        System.out.println(facade.addCatToStRep(id,catName));
    }
    private void exportStoRep(){
        System.out.print("\nReport's ID number- ");
        int id = s.nextInt();
        System.out.println(facade.exportStockReport(id));
    }

    private void defectiveReport(){
        while (true) {
            System.out.println("\t\tStock report menu:\n\n" +
                    "1)\tadd defective Report\n" +
                    "2)\tadd product to defective report\n" +
                    "3)\texport defective report\n" +
                    "4)\tExit"
            );

            int choice = s.nextInt();
            switch (choice) {
                case 1:
                    addDefRep();
                    break;
                case 2:
                    addProdToDefRep();
                    break;
                case 3:
                    exportDefRep();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }
    }
    private void  addDefRep(){
        System.out.print("\nWhich product to include in the report- ");
        String prodsNames = s.next();
        String[] a = prodsNames.split(",");
        List<String> prods = new LinkedList<>();
        for(int i = 0; i< a.length; i++)
            prods.add(a[i]);
        System.out.println(facade.addDefReport(prods));
    }
    private void addProdToDefRep(){
        System.out.print("\nReport's ID number- ");
        int id = s.nextInt();
        System.out.print("\nProduct's name- ");
        String prodName = s.next();
        System.out.println(facade.addProdToDefRep(id,prodName));
    }
    private void exportDefRep(){
        System.out.print("\nReport's ID number- ");
        int id = s.nextInt();
        System.out.println(facade.exportDefReport(id));
    }
    private void productPFSHistory(){
        System.out.print("Product's name- ");
        String prodName = s.next();
        System.out.println(facade.displayPFSHistory(prodName));
    }
    private void productPTCHistory(){
        System.out.print("Product's name- ");
        String prodName = s.next();
        System.out.println(facade.displayPTCHistory(prodName));
    }





}

