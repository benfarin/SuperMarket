package BusinessLayer.Suppliers;

import DataLayer.ContractMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Supplier {
    private Integer id_supplier;
    private Long id_company;
    private String name;
    private List<String> contacts;
    private String paymentMethod;
    private String bankAccount;
    private  HashMap<Long, ProductPerSup> products; // do we want hold the product himself? or the id?
    private Contract contract;




    public Supplier(Integer id_supplier, Long id_company, String name, List<String> contacts, String paymentMethod, String bankAccount) {
        this.id_supplier = id_supplier;
        this.id_company = id_company;
        this.name = name;
        this.contacts = contacts;
        this.paymentMethod = paymentMethod;
        this.bankAccount = bankAccount;
        this.contract= null;//new Contract(null,false,new HashMap<Integer,Integer>()); //addContract();
        this.products=new HashMap<Long, ProductPerSup>();
    }
    public Supplier(Integer id_supplier, Long id_company, String name, List<String> contacts, String paymentMethod, String bankAccount, Contract contract) {
        this.id_supplier = id_supplier;
        this.id_company = id_company;
        this.name = name;
        this.contacts = contacts;
        this.paymentMethod = paymentMethod;
        this.bankAccount = bankAccount;
        this.contract= contract;
        this.products=new HashMap<Long, ProductPerSup>();
    }

    public HashMap<Long, ProductPerSup> getProducts() {
        return products;
    }
    public void addProductFromDB(ProductPerSup prod){
        products.put(prod.getStoreCode(), prod);
    }


    public void setContract(Contract newContract) { this.contract = newContract; }

    public int getId_supplier() {
        return id_supplier;
    }

    protected void setId_supplier(int id_supplier) {
        this.id_supplier = id_supplier;
    }

    protected long getId_company() {
        return id_company;
    }

    protected void setId_company(long id_company) {
        this.id_company = id_company;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected List<String> getContacts() {
        return contacts;
    }

    protected void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    protected String getPaymentMethod() {
        return paymentMethod;
    }

    protected void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    protected String getBankAccount() {
        return bankAccount;
    }

    protected void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
/*
    public  void  SupplierMenu() {
        System.out.println("1) Add contact ");
        System.out.println("2) Show Contract");
        System.out.println("3) Show supplier" + id_supplier + " information");
        System.out.println("4) Show Contacts of supplier " + id_supplier);
        System.out.println("5) Back to main Menu");
        Scanner io = new Scanner(System.in);
        int op = io.nextInt();
        switch (op) {
            case 1: {
                Addcontact();
                break;
            }

            case 2: {
                showContract();
                break;
            }
            case 3: {
                System.out.println(this);
            }
            case 4: {
                showContacts();
            }
            case 5: {
                return;
            }


        }
    }
*/

 

    public Contract getContract() { return contract; }

    protected Contract addContract(String days,String location,boolean NeedDelivery,HashMap<Integer,Integer> totalPriceDiscount){
       if( contract!=null) {
            System.out.println("this supplier already has a Contract");
            return contract;
        }
//       else {
//            Scanner io = new Scanner(System.in);
//            Contract new_contract;
//            System.out.println("Insert Day of Supply:");
//            String  days = io.next();
//            System.out.println("Insert pickup location:");
//            String location=io.useDelimiter("\n").next();
//            System.out.println("Is needed delivery? ");
//            System.out.println("y/n");
//            String delivery = io.next();
//            boolean NeedDelivery;
//            NeedDelivery=delivery.compareTo("y")==0;
//            HashMap<Integer,Integer> totalPriceDiscount = new HashMap<Integer, Integer>();
//            System.out.println("Do you want enter a PRICE BASED discount?");
//            System.out.println("y/n");
//            String y_discount = io.next();
//            int amount;
//            int precent;
//
//            while (y_discount.compareTo("y")==0){
//                System.out.println("enter amount products: ");
//                amount= io.nextInt();
//                System.out.println("enter the discount percent for amount: "+ amount);
//                precent=io.nextInt();
//                totalPriceDiscount.put(amount,precent);
//
//
//                System.out.println("Do you want enter more discounts?");
//                System.out.println("y/n");
//                y_discount = io.next();
//
//            }

            Contract new_contract = new Contract(days,NeedDelivery,totalPriceDiscount,location);
            setContract(new_contract);

           ContractMapper.addContract(this.id_supplier, days, NeedDelivery? 1 : 0, totalPriceDiscount, location);
            return new_contract;
        }


    public void AddContact(String new_contact ) {
        contacts.add(new_contact);
    }

    public Contract showContract() {
        return contract;
    }
     public List<String> showContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return "Supplier : " +
                "id_supplier=" + id_supplier +
                ", id_company=" + id_company +
                ", name='" + name + '\'' +
                ", contacts=" + contacts +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", contract=" + contract +
                '}';
    }

}
