package BusinessLayer;

import BusinessLayer.Inventory.*;
import java.util.*;

import BusinessLayer.Suppliers.*;
import BusinessLayer.Workers.ShiftController;
import BusinessLayer.Workers.WorkersController;
import DataLayer.DataHandler;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import  BusinessLayer.Workers.*;
import BusinessLayer.Delivery.*;
import BusinessLayer.Workers.Enum.ShiftField;
import BusinessLayer.Workers.Enum.WorkerField;
public class Facade {
    private ShiftController ShiftCntrl;
    private WorkersController WorkersCntrl;
    static Controller conn;
    private InventoryController invCnt;
    private ReportController repCnt;
    private IncomingOrderController incoming_order_controller;
    private SupplierController supplierController;
    private DataHandler dataHandler;

    public Facade() {

        //initialize();
        this.repCnt = new ReportController();
        this.invCnt = new InventoryController();
        this.dataHandler = new DataHandler(this);
        supplierController = new SupplierController(dataHandler.supplierMapper.getSuppliers());
        incoming_order_controller = new IncomingOrderController(dataHandler.productPerSupMapper.getMapOfAllProducts(), dataHandler.orderMapper.orders, dataHandler.orderMapper.urgentOrders);
        this.conn= new Controller();
        this.ShiftCntrl = new ShiftController();
        this.WorkersCntrl = new WorkersController();
        if(WorkersCntrl.getWorkers().isEmpty())
            initWorkers();
        orderToday();
        if(invCnt.getAllProd().isEmpty() && invCnt.getAllCategories().isEmpty())
            init();

    }

    private void initWorkers() {
        WorkersCntrl.createWorker("hila",205,123,1,100);
        WorkersCntrl.createRole(1,"Manager");
        WorkersCntrl.createRole(2,"Storekeeper");
        WorkersCntrl.createRole(3,"logistic");
        WorkersCntrl.addRoleToWorker(WorkersCntrl.getWorker(205),1);
        WorkersCntrl.createWorker("rotem",206,1,1,100);
        WorkersCntrl.addRoleToWorker(WorkersCntrl.getWorker(206),2);
    }
    public String showAllProds(){
        return invCnt.showAllProds();
    }
    public String showAllCats(){
        return invCnt.showAllCats();
    }

    public String acceptDelivery(int orderId,Map<Long,Integer> missingProds, Map<Long,Integer> defctiveProds){
        OutgoingOrder order = incoming_order_controller.ShowOrder((long)orderId);
        int quantityToAdd;
        if(order == null){
            order = incoming_order_controller.ShowUrgentOrder((long)orderId);
        }
        if(order == null){
            System.out.println("No such order");
        }
        incoming_order_controller.orderComplete(orderId);
        for(Long prodId : order.getItemsInOrder()){
            Product p = invCnt.getProdByID((prodId.intValue()));
            if(missingProds.containsKey(prodId)){
                int missing =missingProds.get(prodId);
                quantityToAdd = order.getItemsQuanityInOrder(prodId) - missing;
                addStorageQuantity(p.getName(),quantityToAdd);
                AddNewUrgentOrder((long) p.getId(),missing);//send order to supplier
            }
            if(defctiveProds.containsKey(prodId)){
                int defective=defctiveProds.get(prodId) ;
                quantityToAdd = order.getItemsQuanityInOrder(prodId) - defective;
               addStorageQuantity(p.getName(),quantityToAdd);
               setDefectiveItems(p.getName(),defctiveProds.get(prodId));
                AddNewUrgentOrder((long) p.getId(),defective);//send order to supplier
            }
            if(!missingProds.containsKey(prodId) && !defctiveProds.containsKey(prodId)){
                quantityToAdd = order.getItemsQuanityInOrder(prodId);
                addStorageQuantity(p.getName(),quantityToAdd);
            }
        }
        return "Order received, all quantities are up to date\n";
    }
    public int getProdIdByName(String name){
        Product p =invCnt.getProduct(name);
        if(p==null){
            return -1;
        }
        return p.getId();
    }
    public List<String> getProdFromOrder(int orderId){
        OutgoingOrder order = incoming_order_controller.ShowOrder((long)orderId);;
        List<String> prodNames = new LinkedList<>();
        if(order==null) {
            if (incoming_order_controller.ShowUrgentOrder((long)orderId) == null) {
                return null;
            }
            else{
                order = incoming_order_controller.ShowUrgentOrder((long)orderId);
            }
        }
        for(Long idProd : order.getItemsInOrder()){
            prodNames.add(invCnt.getProdByID(idProd.intValue()).getName());
        }
        return prodNames;
    }
    public String addCategory (String name){
        Category c = invCnt.addCategory(name);
        if(c !=null){ // ADD TO DATABASE
            if(c.getSupCategory() != null)
                dataHandler.addCatToData(c.getName(), c.getSupCategory().getName(), c.getDiscount(), c.getDiscountDate());
            dataHandler.addCatToData(c.getName(), null, c.getDiscount(), c.getDiscountDate());
        }
        for(Category cat : c.getSubCategories()){
            cat.addSup(c);
        }
        return "the category " + name + " successfully added\n";

    }
    public Category addCatFromData(String name, Category supCat,int discount,Date discountDate){
    //    Category sup;
//        if(supCat != null) {
//            sup = invCnt.getCategory(supCat);
//        }
//        else
//            sup = null;
        Category c = new Category(name,supCat, new LinkedList<>(), new LinkedList<>(), discount, discountDate);
        invCnt.addCatFromData(c);
        return c;
    }
    public String addProduct (String name,String category, String manufacture, double priceFromSupplier, double priceToCustomer, int minimum){
        if(invCnt.getCategory(category)==null){
            return "Can't add product because category does not exist\n"+"add category first\n";
        }
        Category prodCat = invCnt.getCategory(category);
        Product p = invCnt.addProduct(name,prodCat,manufacture,priceFromSupplier,priceToCustomer,minimum);
        // add to database
        dataHandler.addProduct(p.getId(),p.getName(),p.getManufacture(),p.getCategory().getName(),p.getStoreQuantity(),p.getStorageQuantity(),p.getDiscount(),p.getDiscountDate(),p.getPriceFromSupplier(),p.getPriceToCustomer(),p.getDefectiveItem(),p.getMinimum(),p.getOrderAmount(),p.getPriceToCusHistory(),p.getPriceFromSupHistory());
        return "the product " + name + " successfully added\n";
    }
    public Product addProductFromData (int id, String name, String manufacture, Category category, int storeQuantity, int storageQuantity, int discount, java.util.Date discountDate, double priceFromSupplier, double priceToCustomer, int defectiveItem, int minimum, Map<Double, java.util.Date> priceToCusHistory, Map<Double, java.util.Date> priceFromSupHistory){
        return  invCnt.addProductFromData(id,name, manufacture, category,storeQuantity, storageQuantity, discount, discountDate, priceFromSupplier, priceToCustomer,defectiveItem, minimum, priceToCusHistory, priceFromSupHistory);
    }
    //-------------------------PRODUCT--------------------------
    public void setFirstId (int id) {
        invCnt.setFirstId(id);
    }

    public Product getProdByID(int id){
        return invCnt.getProdByID(id);
    }
    public String setManufacture(String prodName,String manu){
        if(invCnt.getProduct(prodName) == null){
            return "The product "+prodName+" does not exist\n";
        }
        invCnt.setManufacture(prodName,manu);
        //add to database
        dataHandler.updateManufacture(invCnt.getProduct(prodName).getId(),manu);
        return "set "+ prodName+"'s manufacture to " + manu+"\n";
    }
    public String setPriceFromSupplier(String prodName,double priceFromSupplier){
        if(invCnt.getProduct(prodName) == null){
            return "The product "+prodName+" does not exist\n";
        }
        invCnt.setPriceFromSupplier(prodName,priceFromSupplier);
        //database
        dataHandler.updatePriceFromSupplier(invCnt.getProduct(prodName).getId(),priceFromSupplier);
        return "set "+ prodName+"'s price from supplier to " + priceFromSupplier+"\n";
    }
    public String setPriceToCustomer(String prodName , double priceToCustomer){
        if(invCnt.getProduct(prodName) == null){
            return "The product "+prodName+" does not exist\n";
        }
        invCnt.setPriceToCustomer(prodName,priceToCustomer);
        //database
        dataHandler.updatePriceToCustomer(invCnt.getProduct(prodName).getId(),priceToCustomer);
        return "set "+ prodName+"'s price to costumer to " + priceToCustomer+"\n";
    }
    public String setProdDiscount(String prodName,int discount, java.util.Date discountDate){
        if(invCnt.getProduct(prodName) == null){
            return "The product "+prodName+" does not exist\n";
        }
        invCnt.setProdDiscount(prodName,discount,discountDate);
        //database
        dataHandler.updateDiscount(invCnt.getProduct(prodName).getId(),discount,discountDate);
        return "set "+ prodName+"'s discount to " +discount+ "% until " + discountDate+"\n";
    }
    public String setDefectiveItems (String prodName, int def){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setDefectiveItems(prodName,def);
        //database
        dataHandler.updateDefectiveItems(invCnt.getProduct(prodName).getId(),def);
        return "set "+ prodName+"'s defective items to " + def+"\n";
    }
    public String setMinimum(String prodName , int minimum) {
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setMinimum(prodName,minimum);
        //database
        dataHandler.updateMinimum(invCnt.getProduct(prodName).getId(),minimum);
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
        Product p =invCnt.getProduct(prodName);
        if(p == null){
            return "Can't add storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.addStorageQuantity(prodName,add);
        dataHandler.updateStorageQuantity(p.getId(),p.getStorageQuantity());
        return "added "+ add+" from " + prodName+" storage quantity\n";
    }
    public String reduceStorageQuantity(String prodName, int reduce){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        String s = "";
        if(invCnt.reduceStorageQuantity(prodName,reduce)){
            Product p = invCnt.getProduct(prodName);
            AddNewUrgentOrder((long) p.getId(),4*p.getMinimum());
            s = "*** WARNING!!! "+ prodName+"'s storage quantity is under the minimum ***\nsend order to supplier\n";
        }

        return "Reduced "+ reduce+" from " + prodName+" storage quantity\n"+s;
    }
    public String setStoreQuantity(String prodName, int storeQuantity){
        if(invCnt.getProduct(prodName) == null){
            return "Can't set store quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setStoreQuantity(prodName,storeQuantity);
        //database
        dataHandler.updateStoreQuantity(invCnt.getProduct(prodName).getId(),storeQuantity);
        return "Store quantity changed to " + storeQuantity;
    }
    public String setStorageQuantity(String prodName, int storageQuantity){
        if(invCnt.getProduct(prodName) == null){
            return "Can't set storage quantity because the product "+prodName+" does not exist\n";
        }
        String s ="";
        if(invCnt.setStorageQuantity(prodName,storageQuantity)){
            Product p = invCnt.getProduct(prodName);
            AddNewUrgentOrder((long) p.getId(),4*p.getMinimum());
            s = "*** WARNING!!! "+ prodName+"'s storage quantity is under the minimum ***\n";
        }
        //database
        dataHandler.updateStorageQuantity(invCnt.getProduct(prodName).getId(),storageQuantity);
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
    public void deleteProduct(int id){
        dataHandler.deleteProduct(id);
        invCnt.deleteProduct(id);
    }

    //-------------------------CATEGORY--------------------------

    public String deleteCategory(String catName){
        if(invCnt.getCategory(catName)==null){
            return "Can't delete "+catName+" this category does not exist\n";
        }
        for (Category c: invCnt.getCategory(catName).getSubCategories())
            dataHandler.addSup(null,c.getName());
        for(Product p :invCnt.getCategory(catName).getProducts())
            deleteProduct(p.getId());
        invCnt.deleteCat(catName);
        dataHandler.deleteCategory(catName);
        return "The category "+ catName +" was deleted";
    }
    public String addSub(String mainCat,String subC){
        if(invCnt.getCategory(mainCat)==null){
            return "Can't add sub-category because main category "+mainCat+" does not exist\n"+"add main category first\n";
        }
        if(invCnt.getCategory(subC)==null){
            return "Can't add sub-category "+subC+",this category does not exist\n"+"add this category first\n";
        }

        Category subCat = invCnt.getCategory(subC);
        invCnt.addSub(mainCat,subCat);
        //dataHandler.addSup(mainCat,subC);
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
    public String setCatDiscount(String catName, int discount, java.util.Date discountDate){
        if(invCnt.getCategory(catName)==null){
            return "the category "+catName+" does not exist\n";
        }
        invCnt.setCatDiscount(catName,discount,discountDate);
        // update database
        dataHandler.updateDiscounts(catName,discount,discountDate);
        return "set " +catName+ "'s discount to "+ discount + " until "+ discountDate.toString()+"\n";
    }

    public String setCatDisDate(String catName,java.util.Date disDate){
        if(invCnt.getCategory(catName)==null){
            return "the category "+catName+" does not exist\n";
        }
        invCnt.setCatDiscountDate(catName,disDate);
        // update database
        dataHandler.updateDiscDate(catName,disDate);
        return "set " +catName+ "'s discount until "+ disDate.toString()+"\n";
    }

    public String setCatDiscountDate(String catName, Date discountDate){
        if(invCnt.getCategory(catName)==null){
            return "the category "+catName+" does not exist\n";
        }
        invCnt.setCatDiscountDate(catName,discountDate);
        // update data base
        dataHandler.updateDiscDate(catName,discountDate);
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
        StockReport sto = repCnt.addStockReport(cats);
        if(ret.length() > 1)
            ret = ret.substring(0,ret.length()-2);
        //database
        List<String> catNames = new LinkedList<>();
        for(Category c : sto.getCategories()){
            catNames.add(c.getName());
        }
        dataHandler.addStock(sto.getID(),sto.getDate(),catNames);
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
        //database
        List<Integer> prodID = new LinkedList<>();
        for(Product p : d.getDefectiveProducts()){
            prodID.add(p.getId());
        }
        dataHandler.addDefective(d.getID(),d.getDate(),prodID);
        return "Added defective report about the products: " + ret+ "\n this report ID: "+d.getID()+"\n";
    }
    public String addCatToStRep(int id, String category){
        if(repCnt.getStoReport(id) == null)
            return "The report "+id+ " does not exist\n";

        Category c = invCnt.getCategory(category);
        if(c==null)
            return "the category "+category+" does not exist\n";
        repCnt.addCatToStRep(id,c);
        //database
        dataHandler.addStockCat(id,category,repCnt.getDay());
        return "Added the category " + category+ " to "+ id + " report\n";
    }
    public String addProdToDefRep(int id, String product){
        if(repCnt.getDefReport(id) == null)
            return "The report "+id+ " does not exist\n";

        Product p = invCnt.getProduct(product);
        if(p==null)
            return "the product "+product+" does not exist\n";
        repCnt.addProdToDefRep(id,p);
        //database
        dataHandler.addDefectiveProd(id,p.getId());
        return "Added the product " + product+ " to "+ id + " report\n";
    }

    public void orderToday(){
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(System.currentTimeMillis()));
            boolean dayIsHere;
            switch (repCnt.getDay()) {
                case 1:
                    dayIsHere = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
                    break;
                case 2:
                    dayIsHere = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
                    break;
                case 3:
                    dayIsHere = cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
                    break;
                case 4:
                    dayIsHere = cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
                    break;
                case 5:
                    dayIsHere = cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY;
                    break;
                case 6:
                    dayIsHere = cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
                    break;
                case 7:
                    dayIsHere = cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
                    break;
                default:
                    dayIsHere = false;
                    break;
            }
            if(dayIsHere)
                sendOrder();
    }
    public void sendOrder(){
        HashMap<Integer, Integer> order = new HashMap<>();
        order = repCnt.sendReport();

        for(Integer i : order.keySet())
            if(order.get(i) > 0){
                //todo
                incoming_order_controller.AddNewOrder(Long.valueOf(i),order.get(i));
            }
    }
    public String setOrderDay(int day){
        if(day < 8 && day > 0) {
            repCnt.setDay(day);
            dataHandler.updateDay(day);
            return "Day successfully changed";
        }
        return "Error changing day";
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


    public void addProductPerSup(String prodName,int sid,double price,double weight,Long serialNum,HashMap<Integer,Double> prodsDiscount){
        ProductPerSup p = new ProductPerSup(prodName,(long)getProdIdByName(prodName),price,prodsDiscount,(long)serialNum,supplierController.ShowSupInformation(sid),weight);
        supplierController.ShowSupInformation(sid).addProductFromDB(p);
        incoming_order_controller.addProd(p);
       dataHandler.addNewProductPerSupplier(getProdIdByName(prodName),price,sid,serialNum,weight);
        for(Map.Entry<Integer, Double> entry : prodsDiscount.entrySet()) {
            dataHandler.addNewHighAmountDiscount(getProdIdByName(prodName), sid, entry.getKey(), entry.getValue());
        }
    }
    public void AddContact(int id_sup, String new_contact)  {
        supplierController.AddContact(id_sup,new_contact);
        dataHandler.supplierMapper.addContactToSupplier(id_sup, new_contact);
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
    public OutgoingOrder ShowUrgentOrder(Long id_order){ return incoming_order_controller.ShowUrgentOrder(id_order); }

    public boolean IsProductExistInSystem(Long id_product){ return incoming_order_controller.IsProductExistInSystem((long) id_product); }

    public void AddNewOrder(Long id_product, Integer amount) {

        incoming_order_controller.AddNewOrder(id_product,amount);

    }

    public void AddNewUrgentOrder(Long id_product, Integer amount) {

        incoming_order_controller.AddNewUrgentOrder(id_product,amount);

    }

    public boolean IsOrderExistInSystem(int id_order) {
        return incoming_order_controller.IsOrderExistInSystem(id_order);
    }

    public OutgoingOrder ShowOrderBySupplier(int id_sup) {
        return  incoming_order_controller.ShowOrderBySupplier(id_sup);
    }

    public void AddNewSupplier(int id, Long company, String name, List<String> contacts, String payment, String bank) {
        supplierController.AddSupplier(id,company,name,contacts,payment,bank);
        dataHandler.supplierMapper.addSupplier(id, name, payment, bank);
        dataHandler.supplierMapper.addContactToSupplier(id, contacts.get(0));
    }

    public void AddSupplierContract(int supplier_id,String days,String location,boolean NeedDelivery,HashMap<Integer,Integer> totalPriceDiscount){
        supplierController.AddSupplierContract(supplier_id,days,location,NeedDelivery,totalPriceDiscount);
    }

    public String printProductSerialNumber(Integer supplier_id){
        return incoming_order_controller.printProductsSerialNumbers(supplier_id);
    }

    public void DeleteSupplier(int id) {
        supplierController.DeleteSupplier(id);
        dataHandler.supplierMapper.deleteSupplier(id);
        //dataHandler.contractMapper.deleteContract(id); This isn't needed because cascade works

    }

    public Category getCategory(String name) {
        return invCnt.getCategory(name);
    }

    public void addSup(String supCat, String cat){
       // invCnt.addSup(supCat, cat);
        dataHandler.addSup(supCat,cat);
    }
    public void deleteSup(String cat){
        dataHandler.deleteSup(cat);
    }


    public void addStockReportFromData(StockReport re) {
        repCnt.setDay(re.getDay());
        repCnt.addStockReport(re);
    }

    public void addDefectiveReportFromData(DefectiveReport re1) {
        repCnt.addDefReport(re1);
    }

    public int getProdIDByName(String product) {
        return invCnt.getProduct(product).getId();
    }

    public int getDay() {
        return repCnt.getDay();
    }

//    public void deleteProduct(int id) {
//        Product p = invCnt.getProdByID(id);
//        dataHandler.deleteProduct(id);
//    }
//
//    public void setOrderDay(int nextInt) {
//        repCnt.setDay(nextInt);
//    }
    public void addDriver(Driver d){
        conn.addDriver(d);
    }
    public void addTruck(Truck tr){
        conn.addTruck(tr);
    }
    public void addDelivery(Delivery dl){
        conn.addDelivery(dl);
    }
    public void addLocation(Location loc){
        conn.addLocation(loc);
    }

    public void addDocument(Document doc){
        conn.addDocument(doc);
    }

    public Driver getDriver(int id){
        return conn.getDriver(id);
    }
    public Truck getTruck(int license){
        return conn.getTruck(license);
    }
    public Delivery getDelivery(int id){
        return conn.getDelivery(id);
    }
    public Location getLocation(String address){
        return conn.getLocation(address);
    }
    public Document getDocument(int id){
        return conn.getDocument(id);
    }
    public List<Driver> getAllDrivers(){
        return conn.getAllDrivers();
    }
    public List<Truck> getAllTrucks(){
        return conn.getAllTrucks();
    }
    public List<Delivery> getAllDeliveries(){
        return conn.getAllDeliveries();
    }

    public List<Driver> getAllDriversA() {
        return conn.getAllDriversA();
    }

    public List<Driver> getAllDriversB() {
        return conn.getAllDriversB();
    }

    public List<Truck> getAllTrucksA() {
        return conn.getAllTrucksA();

    }

    public List<Truck> getAllTrucksB() {
        return conn.getAllTrucksB();
    }

    public List<Location> getAllLocations() {
        return conn.getAllLocations();
    }
    public void createShift(Date shiftDate, int managerID, int shiftType, HashMap<Integer, Integer> workers, HashMap<Integer, Integer> roles){
        ShiftCntrl.createShift(shiftDate, managerID, shiftType, workers, roles);
    }

    public <T> void updateShift(Shift shift, ShiftField field, T newValue){
        ShiftCntrl.updateShift(shift, field, newValue);
    }
    public List<Shift> getWorkerShifts(Worker w){

        return ShiftCntrl.getWorkerShifts(w);
    }

    public Shift getShift(Date date,int shiftType) {
        return ShiftCntrl.getShift(date, shiftType);
    }
    public void printShift(Shift s) {
        ShiftCntrl.printShift(s);
    }

    public void deleteShift(Shift shift){
        ShiftCntrl.deleteShift(shift);
    }

    public HashMap<Integer, Integer> getShiftWorkers(Shift sh) {
        return ShiftCntrl.getShiftWorkers(sh);
    }

    public void addWorkerToShift(Shift sh, int workerId, int roleId) {
        ShiftCntrl.addWorkerToShift(sh, workerId, roleId);
    }

    public void removeWorkerFromShift(Shift sh, int id) {
        ShiftCntrl.removeWorkerFromShift(sh, id);
    }

    public HashMap<Integer, Integer> getShiftRoles(Shift sh) {
        return ShiftCntrl.getShiftRoles(sh);
    }

    public void addRoleToShift(Shift sh, int id, int amount) {
        ShiftCntrl.addRoleToShift(sh, id, amount);
    }

    public void removeRoleToShift(Shift sh, int id, int amount) {
        ShiftCntrl.removeRoleToShift(sh, id, amount);
    }

    public boolean checkIfshiftExist(Date date, int shiftType) {
        return ShiftCntrl.checkIfshiftExist(date, shiftType);
    }

    public void init() {

        addCategory("fruits");
        addCategory("tropic");
        addSub("fruits","tropic");
        addSup("fruits","tropic");
        addProduct("apple","fruits","maabarot",3.44,4.33,20);
        addProduct("avocado","tropic","Tnuva",5.6,6.20,10);
        addProduct("pineapple","tropic","maadanot",5.40,8.9,10);
        List<String> contacts = new LinkedList<>();
        contacts.add("dani");
        AddNewSupplier(123,(long)2,"yossi",contacts,"cash","1");
        HashMap<Integer,Double> prodsDisc = new HashMap<>();
        prodsDisc.put(10,  10.0);
        addProductPerSup("apple",123,5,3,(long)3,prodsDisc);
        AddSupplierContract(123,"monday","beer sheva",true,new HashMap<>());
        //addStockReport(new LinkedList<>());
    }
    public void createWorker(String name, int id, int bankAccountNumber, int bankNumber, int salary){
        WorkersCntrl.createWorker(name, id, bankAccountNumber, bankNumber, salary);
    }

    public <T> void updateWorker(int id, WorkerField field, T newValue){
        WorkersCntrl.updateWorker(id, field, newValue);
    }
    public boolean isWorker(int id) {
        return WorkersCntrl.isWorker(id);
    }
    public Worker getWorker(int id) {
        return WorkersCntrl.getWorker(id);
    }
    public void createRole(int id, String name ) {
        WorkersCntrl.createRole(id, name);
    }
    public Role getRole(int id) {
        return WorkersCntrl.getRole(id);
    }
    public void printRole(Role role) {
        WorkersCntrl.printRole(role);
    }
    public boolean workerHasRole(Worker w,int roleid) {
        return WorkersCntrl.workerHasRole(w, roleid);
    }
    public void printWorker(Worker w) {
        WorkersCntrl.printWorker(w);
    }
    public Constraint getConstraintByShift(Worker w,Shift s) {
        return WorkersCntrl.getConstraintByShift(w, s);
    }
    public void removeConstraint(Worker w,Shift s) {
        WorkersCntrl.removeConstraint(w, s);

    }
    public HashMap<Integer,Constraint> getWorkerConstraints(Worker w){
        return WorkersCntrl.getWorkerConstraints(w);
    }

    public List<Role> getWorkerRoles(Worker w){
        return WorkersCntrl.getWorkerRoles(w);
    }
    public void printConstrint(Constraint c) {
        WorkersCntrl.printConstrint(c);
    }
    public String getWorkerName(Worker w) {
        return WorkersCntrl.getWorkerName(w);
    }

    public void deleteWorker(int id){
        WorkersCntrl.deleteWorker(id);
    }

    public void addConstraintToWorker(Worker w, Constraint c){
        WorkersCntrl.addConstraintToWorker(w, c);
    }

    public void addRoleToWorker(Worker w, int roleid) {
        WorkersCntrl.addRoleToWorker(w, roleid);
    }
    public void removeRoleFromWorker(Worker w,int roleid) {
        WorkersCntrl.removeRoleFromWorker(w, roleid);
    }

    public boolean isRole(int roleId) {
        return WorkersCntrl.isRole(roleId);
    }

    public HashMap<Integer, Worker> getWorkers() {
        return WorkersCntrl.getWorkers();
    }

    public HashMap<Integer, Role> getRoles() {
        return WorkersCntrl.getRoles();
    }
}
