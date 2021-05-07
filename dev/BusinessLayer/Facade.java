package BusinessLayer;

import BusinessLayer.Inventory.*;

import java.util.Date;
import BusinessLayer.Suppliers.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Facade {
    private InventoryController invCnt;
    private ReportController repCnt;
    IncomingOrderController incoming_order_controller;
    SupplierController supplierController;

    public Facade() {

        initialize();
    }
    public String addCategory (String name, List<String> subCategories){
        invCnt.addCategory(name,subCategories);
        return "the category " + name + " successfully added\n";
    }
    public String addProduct (String name,String category, String manufacture, double priceFromSupplier, double priceToCustomer, int minimum){
        if(invCnt.getCategory(category)==null){
            return "Can't add product because category does not exist\n"+"add category first\n";
        }
        Category prodCat = invCnt.getCategory(category);
        invCnt.addProduct(name,prodCat,manufacture,priceFromSupplier,priceToCustomer,minimum);
        return "the product " + name + " successfully added\n";
    }

    //-------------------------PRODUCT--------------------------
    public Product getProdByID(int id){
        return invCnt.getProdByID(id);
    }
    public String setManufacture(String prodName,String manu){
        if(invCnt.getProduct(prodName) == null){
            return "The product "+prodName+" does not exist\n";
        }
        invCnt.setManufacture(prodName,manu);
        return "set "+ prodName+"'s manufacture to " + manu+"\n";
    }
    public String setPriceFromSupplier(String prodName,double priceFromSupplier){
        if(invCnt.getProduct(prodName) == null){
            return "The product "+prodName+" does not exist\n";
        }
        invCnt.setPriceFromSupplier(prodName,priceFromSupplier);
        return "set "+ prodName+"'s price from supplier to " + priceFromSupplier+"\n";
    }
    public String setPriceToCustomer(String prodName , double priceToCustomer){
        if(invCnt.getProduct(prodName) == null){
            return "The product "+prodName+" does not exist\n";
        }
        invCnt.setPriceToCustomer(prodName,priceToCustomer);
        return "set "+ prodName+"'s price to costumer to " + priceToCustomer+"\n";
    }
    public String setProdDiscount(String prodName,int discount, Date discountDate){
        if(invCnt.getProduct(prodName) == null){
            return "The product "+prodName+" does not exist\n";
        }
        invCnt.setProdDiscount(prodName,discount,discountDate);
        return "set "+ prodName+"'s discount to " +discount+ "% until " + discountDate+"\n";
    }
    public String setDefectiveItems (String prodName, int def){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setDefectiveItems(prodName,def);
        for(DefectiveReport defRep: repCnt.getDefReports()){ // if this prod in any reports it will add the amount
            if(defRep.isProdInRep(prodName)){
                //CALL SUPPLIER
            }

        }
        return "set "+ prodName+"'s defective items to " + def+"\n";
    }
    public String setMinimum(String prodName , int minimum) {
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setMinimum(prodName,minimum);
        return "set "+ prodName+"'s minimum to " + minimum+"\n";
    }
    public String addStoreQuantity(String prodName, int add){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.addStoreQuantity(prodName,add);
        return "added "+ add+" from " + prodName+" store quantity\n";
    }
    public String reduceStoreQuantity(String prodName, int reduce){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        return "Reduced "+ reduce+" from " + prodName+" store quantity\n";

    }
    public String addStorageQuantity(String prodName, int add){
        if(invCnt.getProduct(prodName) == null){
            return "Can't add storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.addStorageQuantity(prodName,add);
        return "added "+ add+" from " + prodName+" storage quantity\n";
    }
    public String reduceStorageQuantity(String prodName, int reduce){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        String s = "";
        if(invCnt.reduceStorageQuantity(prodName,reduce)){
            Product p = invCnt.getProduct(prodName);
            AddNewOrder((long) p.getId(),4*p.getMinimum());
                s = "*** WARNING!!! "+ prodName+"'s storage quantity is under the minimum ***\nsend order to supplier\n";
            }

        return "Reduced "+ reduce+" from " + prodName+" storage quantity\n"+s;
    }
    public String setStoreQuantity(String prodName, int storeQuantity){
        if(invCnt.getProduct(prodName) == null){
            return "Can't set store quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setStoreQuantity(prodName,storeQuantity);
        return "Store quantity changed to " + storeQuantity;
    }
    public String setStorageQuantity(String prodName, int storageQuantity){
        if(invCnt.getProduct(prodName) == null){
            return "Can't set storage quantity because the product "+prodName+" does not exist\n";
        }
        String s ="";
        if(invCnt.setStorageQuantity(prodName,storageQuantity)){
            Product p = invCnt.getProduct(prodName);
            AddNewOrder((long) p.getId(),4*p.getMinimum());
            s = "*** WARNING!!! "+ prodName+"'s storage quantity is under the minimum ***\n";
        }
        return "Storage quantity changed to " + storageQuantity+"\n"+s;
    }
    public String printProduct(String prodName){
        if(invCnt.getProduct(prodName) == null){
            return "The product "+prodName+" does not exist\n";
        }
        return invCnt.printProduct(prodName);
    }
    public String displayPFSHistory(String prodName){
        if(invCnt.getProduct(prodName) == null){
            return "The product "+prodName+" does not exist\n";
        }
        return invCnt.displayPFSHistory(prodName);
    }
    public String displayPTCHistory(String prodName){
        if(invCnt.getProduct(prodName) == null){
            return "The product "+prodName+" does not exist\n";
        }
        return invCnt.displayPTCHistory(prodName);
    }


    //-------------------------CATEGORY--------------------------


    public String addSub(String mainCat,String subC){
        if(invCnt.getCategory(mainCat)==null){
            return "Can't add sub-category because main category "+mainCat+" does not exist\n"+"add main category first\n";
        }
        if(invCnt.getCategory(subC)==null){
            return "Can't add sub-category "+subC+",this category does not exist\n"+"add this category first\n";
        }

        Category subCat = invCnt.getCategory(subC);
        invCnt.addSub(mainCat,subCat);
        return "The sub-category " + subC + " successfully added to " + mainCat+"\n";
    }
    public String deleteSubCat(String mainCat, String subC){
        if(invCnt.getCategory(mainCat)==null){
            return "Can't delete sub-category because main category "+mainCat+" does not exist\n";
        }
        if(invCnt.getCategory(subC)==null){
            return "Can't delete sub-category "+subC+",this category does not exist\n";
        }

        Category subCat = invCnt.getCategory(subC);
        invCnt.deleteSubCat(mainCat,subCat);
        return "The sub-category " + subC + " successfully deleted from " + mainCat+"\n";
    }
    public String deleteProdFromCat(String catName ,String prodName){
        if(invCnt.getCategory(catName)==null){
            return "the category "+catName+" does not exist\n";
        }
        if(invCnt.getProduct(prodName) == null){
            return "Can't delete, the product "+prodName+" does not exist\n";
        }
        invCnt.deleteProd(catName,invCnt.getProduct(prodName));
        return  prodName + " successfully deleted from " + catName+"\n";
    }
    public String setCatDiscount(String catName, int discount, Date discountDate){
        if(invCnt.getCategory(catName)==null){
            return "the category "+catName+" does not exist\n";
        }
        invCnt.setCatDiscount(catName,discount,discountDate);
        return "set " +catName+ "'s discount to "+ discount + " until "+ discountDate.toString()+"\n";
    }
    public String setCatDiscountDate(String catName, Date discountDate){
        if(invCnt.getCategory(catName)==null){
            return "the category "+catName+" does not exist\n";
        }
        invCnt.setCatDiscountDate(catName,discountDate);
        return "set " +catName+ "'s discount date to "+ discountDate.toString()+"\n";
    }
    public String printCategory(String catName) {
        if (invCnt.getCategory(catName) == null)
            return "the category " + catName + " does not exist\n";
        return invCnt.printCategory(catName);
    }
    //-------------------Report--------------------
    public String addStockReport(List<String> categories){
        List<Category> cats = new LinkedList<>();
        String ret = new String();
        for(String s : categories) {
            ret = ret+s+", ";
            Category c = invCnt.getCategory(s);
            if(c == null){
                return "the category "+s+" does not exist\n";
            }
            cats.add(c);
        }
        repCnt.addStockReport(cats);
        if(ret.length() > 1)
            ret = ret.substring(0,ret.length()-2);
        return "Added stock report about the categories: " + ret+"\n";
    }
    public String addDefReport(List<String> products){
        List<Product> prods = new LinkedList<>();
        String ret = new String();
        for(String s : products) {
            ret = ret+s+", ";
            Product p = invCnt.getProduct(s);
            if(p == null){
                return "the product "+s+" does not exist\n";
            }
            prods.add(p);
        }
        DefectiveReport d = repCnt.addDefReport(prods);
        if(ret.length()>1)
            ret = ret.substring(0,ret.length()-2);
        return "Added defective report about the products: " + ret+ "\n this report ID: "+d.getID()+"\n";
    }
    public String addCatToStRep(int id, String category){
        if(repCnt.getStoReport(id) == null)
            return "The report "+id+ " does not exist\n";

        Category c = invCnt.getCategory(category);
        if(c==null)
            return "the category "+category+" does not exist\n";
        repCnt.addCatToStRep(id,c);
        return "Added the category " + category+ " to "+ id + " report\n";
    }
    public String addProdToDefRep(int id, String product){
        if(repCnt.getDefReport(id) == null)
            return "The report "+id+ " does not exist\n";

        Product p = invCnt.getProduct(product);
        if(p==null)
            return "the product "+product+" does not exist\n";
        repCnt.addProdToDefRep(id,p);
        return "Added the product " + product+ " to "+ id + " report\n";
    }
    public String exportStockReport(int id){
        if(repCnt.getStoReport(id) == null)
            return "The report "+id+ " does not exist\n";
       return repCnt.exportReport(id);
    }
    public String exportDefReport(int id){
        if(repCnt.getDefReport(id) == null)
            return "The report "+id+ " does not exist\n";
        return repCnt.exportReport(id);
    }


    private void initialize(){
        ProductPerSup one = new ProductPerSup("milk 3%", new Long(123123), 5.9, null, new Long(82723), null);
        ProductPerSup two = new ProductPerSup("Honey", new Long(34622), 5.9, null, new Long(34644), null);
        ProductPerSup three = new ProductPerSup("Chocolate", new Long(67933), 5.0, null, new Long(122352), null);
        ProductPerSup four = new ProductPerSup("Chocolate", new Long(67933), 7.5, null, new Long(2623643), null);
        ProductPerSup five = new ProductPerSup("Banana", new Long(57423), 10.3, null, new Long(234), null);
        ProductPerSup six = new ProductPerSup("Eggs L", new Long(52321), 22.0, null, new Long(2311), null);

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
//..............INV..............
        this.repCnt = new ReportController();
        this.invCnt = new InventoryController();

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

    public boolean IsProductExistInSystem(Long id_product){ return incoming_order_controller.IsProductExistInSystem((long) id_product); }
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
