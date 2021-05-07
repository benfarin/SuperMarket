package BusinessLayer;

import BusinessLayer.Suppliers.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Facade {
    IncomingOrderController incoming_order_controller;
    SupplierController supplierController;

    public Facade(){
        // Need to get: HashMap<Integer, Supplier>initialSupplierMap, LinkedList<OutgoingOrder> allOrdersList
        initialize();
    }

    private void initialize(){
        Product one = new Product("milk 3%", new Long(123123), 5.9, null, new Long(82723), null);
        Product two = new Product("Honey", new Long(34622), 5.9, null, new Long(34644), null);
        Product three = new Product("Chocolate", new Long(67933), 5.0, null, new Long(122352), null);
        Product four = new Product("Chocolate", new Long(67933), 7.5, null, new Long(2623643), null);
        Product five = new Product("Banana", new Long(57423), 10.3, null, new Long(234), null);
        Product six = new Product("Eggs L", new Long(52321), 22.0, null, new Long(2311), null);

        Supplier moshe = new Supplier(12, new Long(13524), "Moshe Inc", new LinkedList<>(), "Cash", "" );
        Supplier itzik = new Supplier(5, new Long(124), "Itzik & Sons", new LinkedList<>(), "Shotef+60", "1358223" );

        //HashMap<Integer, Supplier>initialSupplierMap, LinkedList<OutgoingOrder> allOrdersList

        HashMap<Integer, Supplier> initialSupplierMap = new HashMap<>();
        LinkedList<OutgoingOrder> allOrdersList = new LinkedList<>();
        initialSupplierMap.put(12, moshe);
        initialSupplierMap.put(5, itzik);

        moshe.getProducts().put(new Long(123123), one);
        moshe.getProducts().put(new Long(67933), three);
        itzik.getProducts().put(new Long(67933), four);
        itzik.getProducts().put(new Long(34622), two);
        itzik.getProducts().put(new Long(57423), five);
        itzik.getProducts().put(new Long(52321), six);
        one.setSupplier(moshe);
        two.setSupplier(itzik);
        three.setSupplier(moshe);
        four.setSupplier(itzik);
        five.setSupplier(itzik);
        six.setSupplier(itzik);

        Contract contractMoshe= new Contract("Friday", false, new HashMap<>());
        contractMoshe.AddPriceDiscount(50, 5);
        Contract contractItzik= new Contract("Sunday", false, new HashMap<>());
        contractItzik.AddPriceDiscount(40, 10);
        itzik.setContract(contractItzik);
        moshe.setContract(contractMoshe);


        supplierController = new SupplierController(initialSupplierMap);
        incoming_order_controller = new IncomingOrderController(supplierController.getAllProducts(), allOrdersList);
    }

    public void AddContact(int id_sup, String new_contact) {
        supplierController.AddContact(id_sup,new_contact);
    }

    public boolean IsSupplierExistInSystem(int id_sup) {
        return supplierController.IsSupplierExistInSystem(id_sup);
    }

    public Contract ShowContract(int id_sup) {
        return supplierController.ShowContract(id_sup);
    }

    public Supplier ShowSupInformation(int id_sup) {
        return supplierController.ShowSupInformation(id_sup);
    }

    public List<String> showContacts(int id_sup) { return supplierController.showContacts(id_sup); }

    public OutgoingOrder ShowOrder(Long id_order){ return incoming_order_controller.ShowOrder(id_order); }

    public boolean IsProductExistInSystem(Long id_product){ return incoming_order_controller.IsProductExistInSystem(id_product); }
    public void AddNewOrder(Long id_product, Integer amount) {
        incoming_order_controller.AddNewOrder(id_product,amount);
    }

    public boolean IsOrderExistInSystem(int id_order) {
        return incoming_order_controller.IsOrderExistInSystem(id_order);
    }

    public OutgoingOrder ShowOrderBySupplier(int id_sup) {
        return  incoming_order_controller.ShowOrderBySupplier(id_sup);
    }

    public void AddNewSupplier(int id, Long company, String name, List<String> contacts, String payment, String bank) {
        supplierController.AddSupplier(id,company,name,contacts,payment,bank);
    }

    public void AddSupplierContract(int supplier_id){
        supplierController.AddSupplierContract(supplier_id);
    }

    public String printProductSerialNumber(Integer supplier_id){
        return incoming_order_controller.printProductsSerialNumbers(supplier_id);
    }

    public void DeleteSupplier(int id) {
        supplierController.DeleteSupplier(id);
    }



}
