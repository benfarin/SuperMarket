package PresentationLayer.Suppliers;

import BusinessLayer.Facade;
import BusinessLayer.Suppliers.*;
import BusinessLayer.Suppliers.OutgoingOrder;

import java.util.*;

public class SupService {
        public static Scanner io = new Scanner(System.in);

        BusinessLayer.Facade facade;

        public SupService(Facade facade){

            this.facade = facade;

        }

        public void startMenu() {

            while (true) {
                System.out.println(
                        "1)\tSupplier Menu\n" +
                        "2)\tOrder Menu\n" +
                        "3)\tExit"
                );

                int choice = io.nextInt();
                switch (choice) {
                    case 1:
                        SupplierMenu();
                        break;
                    case 2:
                        OrderMenu();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Not a valid option, please try again.\n");
                        break;
                }
            }
        }
        private void OrderMenu() {
            while (true) {
                System.out.println("*** Item Order menu ***");
                System.out.println("Please select an option:");
                System.out.println("1)  Add Product To Existing Weekly Order ");
                System.out.println("2)  Show Order by Id Order");
                System.out.println("3)  Show Order for a Supplier");
                System.out.println("4)  Print supplier serial numbers for existing order");
                System.out.println("5)  Exit ");
                int op = io.nextInt();

                switch (op) {
                    case 1: {
                        AddNewOrder();
                        break;
                    }
                    case 2: {
                        ShowOrderByIdOrder();
                        break;
                    }
                    case 3: {
                        ShowOrderBySupplier();
                        break;
                    }
                    case 4:{
                        System.out.println("Enter supplier ID");
                        Integer supplier_id=io.nextInt();
                        printProductSerialNumber(supplier_id);
                        break;
                    }
                    case 5: {
                        return;
                    }
                    default: {
                        System.out.println("Selection Unrecognized");
                        break;
                    }

                }
            }

        }

        private void  ShowOrderByIdOrder() {
            System.out.println("enter an ID Order");
            long id_order = io.nextLong();
            OutgoingOrder order=facade.ShowOrder(id_order);
            if(order==null){
                System.out.println("Weekly Order #\t"+id_order+"\tDoesn't Exist");
            }
            else
                System.out.println(order);

            order=facade.ShowUrgentOrder(id_order);
            if(order==null)
                System.out.println("Urgent Order #\t"+id_order+"\tDoesn't Exist");
            else
                System.out.println(order);
        }

        private void ShowOrderBySupplier() {
            System.out.println("enter an ID Supplier");
            int id_sup = io.nextInt();
            if(!facade.IsOrderExistInSystem(id_sup)){
                System.out.println("there is no order with id\t"+id_sup);
                return;
            }
            System.out.println(facade.ShowOrderBySupplier(id_sup));
        }

        private void AddNewOrder() {
            System.out.println("enter an ID prod"); // the id may be changein the future  by entering num product, manufacturer, and gramp_roduct and make a hash code convert to this integer
            Long id_product = io.nextLong();
            if (!facade.IsProductExistInSystem(id_product)){
                System.out.println("there is no products with id\t"+id_product);
                return;
            }
            System.out.println("enter an Amount");
            int amount = io.nextInt();
            facade.AddNewOrder(id_product,amount);
        }

        private void SupplierMenu() {

                System.out.println("1) Add contact ");
                System.out.println("2) Show Contract");
                System.out.println("3) Show supplier information");
                System.out.println("4) Show Contacts of supplier ");
                System.out.println("5) Add New Supplier ");
                System.out.println("6) Delete Supplier");
                System.out.println("7) Add a contract to an existing supplier");
//                System.out.println("8) add product to existing supplier");
                System.out.println("8) Back to main Menu");
                int op = io.nextInt();
                switch (op) {
                    case 1: {
                        AddContact();
                        break;
                    }
                    case 2: {
                        showContract();
                        break;
                    }
                    case 3: {
                        ShowSupInformation();
                        break;
                    }
                    case 4: {
                        showContacts();
                        break;
                    }
                    case 5: {
                        AddNewSupplier();
                        break;

                    }
                    case 6: {
                        DeleteSupplier();
                        break;

                    }
                    case 7:{
                        addSupContract();
                        break;
                    }

                    case 8: {
                        return;
                    }


                }
            }

    private void addSupContract() {
        System.out.println("Enter supplier ID to add a contract");
        Integer supplier_id=io.nextInt();
        //Scanner io = new Scanner(System.in);
        System.out.println("Insert Day of Supply:");
        String  days = io.next();
        System.out.println("Insert pickup location:");
        String location=io.useDelimiter("\n").next();
        System.out.println("Is needed delivery? ");
        System.out.println("y/n");
        String delivery = io.next();
        boolean NeedDelivery;
        NeedDelivery=delivery.compareTo("y")==0;
        HashMap<Integer,Integer> totalPriceDiscount = new HashMap<Integer, Integer>();
        System.out.println("Do you want enter a PRICE BASED discount?");
        System.out.println("y/n");
        String y_discount = io.next();
        int amount;
        int precent;

        while (y_discount.compareTo("y")==0){
            System.out.println("enter amount products: ");
            amount= io.nextInt();
            System.out.println("enter the discount percent for amount: "+ amount);
            precent=io.nextInt();
            totalPriceDiscount.put(amount,precent);


            System.out.println("Do you want enter more discounts?");
            System.out.println("y/n");
            y_discount = io.next();

        }
        facade.AddSupplierContract(supplier_id,days,location,NeedDelivery,totalPriceDiscount);
    }
    private void addSupContract(int supId) {

        Integer supplier_id=supId;
        System.out.println("Insert Day of Supply:");
        String  days = io.next();
        System.out.println("Insert pickup location:");
        String location=io.useDelimiter("\n").next();
        System.out.println("Is needed delivery? ");
        System.out.println("y/n");
        String delivery = io.next();
        boolean NeedDelivery;
        NeedDelivery=delivery.compareTo("y")==0;
        HashMap<Integer,Integer> totalPriceDiscount = new HashMap<Integer, Integer>();
        System.out.println("Do you want enter a PRICE BASED discount?");
        System.out.println("y/n");
        String y_discount = io.next();
        int amount;
        int precent;

        while (y_discount.compareTo("y")==0){
            System.out.println("enter amount products: ");
            amount= io.nextInt();
            System.out.println("enter the discount percent for amount: "+ amount);
            precent=io.nextInt();
            totalPriceDiscount.put(amount,precent);


            System.out.println("Do you want enter more discounts?");
            System.out.println("y/n");
            y_discount = io.next();

        }
        facade.AddSupplierContract(supplier_id,days,location,NeedDelivery,totalPriceDiscount);
    }

//    private void AddSupplierContract(Integer supplier_id,String days,String location,boolean NeedDelivery,HashMap<Integer,Integer> totalPriceDiscount) {
//            facade.AddSupplierContract(supplier_id,days,location,NeedDelivery,totalPriceDiscount);
//        }

        private void printProductSerialNumber(Integer supplier_id){
            System.out.println(facade.printProductSerialNumber(supplier_id));
        }

        private void DeleteSupplier() {
            System.out.println("Insert  an ID:");
            int id = io.nextInt();
            if (!facade.IsSupplierExistInSystem(id)) {
                System.out.println("Supplier number:\t" + id + "\t is not Exist in System");
                return;
            }
            facade.DeleteSupplier(id);
            System.out.println("Supplier " + id + " Deleted!");
        }


        private void AddNewSupplier() {
            System.out.println("Insert  an ID:");
            int id = io.nextInt();
            if(facade.IsSupplierExistInSystem(id)){
                System.out.println("Supplier number:\t"+id+"\t is Exist in System");
                return;

            }
            System.out.println("Insert  a Company Num:");
            Long company = io.nextLong();

            System.out.println("Insert  a name:");
            String name = io.next();

            System.out.println("Insert Contact:");
            String contact="";
            System.out.println("\tContact Name:");
            contact=io.useDelimiter("\n").next();
            System.out.println("\tContact Phone Number:");
            contact+=" "+io.next();


            List<String> Contacts = new LinkedList<>();
            Contacts.add(contact);

            System.out.println("Insert Payment Method:");
            String payment = io.next();

            System.out.println("Insert  a Bank Account:");
            String bank = io.next();
            facade.AddNewSupplier(id,company,name,Contacts,payment,bank);

            System.out.println("Supplier was Successfully added, please add a Contract");
            addSupContract(id);

            System.out.println("\nPlease add products for the new supplier "+name+" id #"+id);

            HashMap<Integer,Double> prodsDiscount = new HashMap<>();
            System.out.println("Insert  products name(example: apple,avocado,...):");
            String prodsName = io.next();
            String[] prodsN = prodsName.split(",");
            for(String prod : prodsN){
                System.out.println("what is the price for "+ prod+"?" );
                Double price = io.nextDouble();
                System.out.println("what is the weight of "+ prod+"?" );
                double weight = io.nextDouble();
                System.out.println("what is the serial number for "+ prod+"?" );
                Long serialNum = io.nextLong();
                System.out.println("Do you have discount for " + prod + "? (y/n)");
                String yn = io.next();
                while(yn.equals("y")){
                    System.out.println("what is the amount needed for discount?");
                    int amount = io.nextInt();
                    System.out.println("what is the discount percent?");
                    double percent = io.nextDouble();
                    prodsDiscount.put(amount,percent);
                    System.out.println("Do you want to add another discount for "+ prod+"? (y/n)");
                    yn = io.next();
                }

                facade.addProductPerSup(prod,id,price,weight,serialNum,prodsDiscount);
            }

//            System.out.println("Supplier was Successfully added , you may add a Contract");
        }
        private void showContacts() { //done
            System.out.println("what is the ID Supplier");
            int id_sup = io.nextInt();
            if (!facade.IsSupplierExistInSystem(id_sup)) {
                System.out.println("id number:\t" + id_sup + " isn't exist");
            } else {
                System.out.println(facade.showContacts(id_sup));
            }

        }

        private void ShowSupInformation() { //done
            System.out.println("what is the ID Supplier");
            int id_sup = io.nextInt();
            if (!facade.IsSupplierExistInSystem(id_sup)) {
                System.out.println("id number:\t" + id_sup + " isn't exist");
            } else {
                System.out.println(facade.ShowSupInformation(id_sup));
            }
        }

        private void showContract(){ // done
            System.out.println("what is the ID Supplier");
            int id_sup = io.nextInt();
            if (!facade.IsSupplierExistInSystem(id_sup)) {
                System.out.println("id number:\t" + id_sup + " isn't exist");
            } else {
                System.out.println(facade.ShowContract(id_sup));
            }
        }
        private void AddContact() { //done
            System.out.println("what is the ID Supplier");
            int id_sup= io.nextInt();
            if (facade.IsSupplierExistInSystem(id_sup)) {
                System.out.println("write the contact name:");
                String new_contact = io.useDelimiter("\n").next();
                System.out.println("write the contact phone number:");
                new_contact += " " + io.next();
                facade.AddContact(id_sup, new_contact);
            }
            else {
                System.out.println("id number:\t"+id_sup+" isn't exist");
            }
        }


    }
